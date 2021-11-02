package transfer;

import entity.Department;
import entity.Employee;
import entity.Transfer;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {

    public ArrayList<Transfer> getPossibleTransfers(Department firstDepartment, Department secondDepartment) {
        //результирующий список
        ArrayList<Transfer> transfers = new ArrayList<>();
        Department fromDepartment; //департамент с большей средней зарплатой
        Department destinationDepartment; //департамент с меньшей средней зарплатой
        if (firstDepartment.getAverageSalary().compareTo(secondDepartment.getAverageSalary()) >= 0) {
            fromDepartment = firstDepartment;
            destinationDepartment = secondDepartment;
        } else {
            fromDepartment = secondDepartment;
            destinationDepartment = firstDepartment;
        }

        if (fromDepartment.getEmployeeList().size() != 0) {
            //перебор всех возможных комбинаций сотрудников
            for (int[] combination : getPossibleCombinations(fromDepartment.getEmployeeList().size())) {
                ArrayList<Employee> transferList = new ArrayList<>();
                for (int i = 0; i < combination.length; i++) {
                    //добавление сотрудников в соответствии с текущей комбинацией
                    if (combination[i] != 0) {
                        transferList.add(fromDepartment.getEmployeeList().get(i));
                    }
                }

                //Средняя зарплата в текущем списке
                BigDecimal resultSalary = new BigDecimal(0);
                for (Employee employee : transferList) {
                    resultSalary = resultSalary.add(employee.getSalary());
                }
                resultSalary = resultSalary.divide(new BigDecimal(transferList.size()), 2, RoundingMode.HALF_UP);
                //сравниваем получившееся среднее значение зарплаты с текущими по департаментам
                if (resultSalary.compareTo(fromDepartment.getAverageSalary()) < 0 &&
                        resultSalary.compareTo(destinationDepartment.getAverageSalary()) > 0) {
                    transfers.add(getPossibleAverageSalaryChanges(fromDepartment, destinationDepartment, transferList));
                }
            }
        }
        return transfers;
    }

    private BigDecimal getSalaryChanges(Department department, List<Employee> employees, boolean add) {
        BigDecimal salary = department.getTotalSalary();
        for (Employee employee : employees) {
            salary = salary.add(employee.getSalary().multiply(new BigDecimal((add ? 1 : -1))));
        }
        salary = salary.divide(new BigDecimal(department.getEmployeeList().size()
                + employees.size() * (add ? 1 : -1)), 2, RoundingMode.HALF_UP);
        return salary;
    }

    private Transfer getPossibleAverageSalaryChanges(Department from,
                                                     Department dest,
                                                     ArrayList<Employee> employees) {
        Transfer transfer = new Transfer(
                from,
                dest,
                from.getAverageSalary(),
                dest.getAverageSalary(),
                getSalaryChanges(from, employees, false),
                getSalaryChanges(dest, employees, true),
                employees);
        return transfer;
    }

    //Генератор двоичных последовательностей.
    //Создает набор всех двоичных чисел от 1 до 2^n, где n - число сотрудников
    private ArrayList<int[]> getPossibleCombinations(int elementsNumber) {
        ArrayList<int[]> combinations = new ArrayList<>();
        int[] elements = new int[elementsNumber];
        outer:
        while (true) {
            int i = elementsNumber - 1;
            while (elements[i] == 1) {
                elements[i] = 0;
                i--;
                if (i < 0) break outer;
            }
            elements[i]++;
            combinations.add(Arrays.copyOf(elements, elements.length));
        }
        return combinations;
    }
}
