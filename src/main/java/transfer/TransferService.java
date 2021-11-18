package transfer;

import entity.Department;
import entity.Employee;
import entity.Transfer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {

    private int transferIndex = 1;

    public void getPossibleTransfers(List<Department> departments, String path) {
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i +1; j < departments.size(); j++) {
                transferBetweenTwo(departments.get(j), departments.get(i), path);
            }
        }
    }

    public void transferBetweenTwo(Department fromDepartment, Department destinationDepartment, String outputPath) {
        File file = new File(outputPath);
        FileWriter writer;
        try{
            writer = new FileWriter(file, true);
        } catch (IOException ex) {
            System.out.println("Не удалось открыть файл " + outputPath);
            return;
        }

        if (fromDepartment.getEmployeeList().size() != 0) {
            //перебор всех возможных комбинаций сотрудников
            for (int[] combination : getPossibleCombinations(fromDepartment.getEmployeeList().size(),
                    fromDepartment.getEmployeeList().size())) {
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
                    try {
                        writer.write(transferIndex + ") " +getPossibleAverageSalaryChanges(fromDepartment,
                                destinationDepartment,
                                transferList) + "\n");
                        ++transferIndex;
                    } catch (IOException ex) {
                        System.out.println("Не удалось провести запись в указанный файл");
                    }
                }
            }
        }
        try {
            writer.close();
        } catch (IOException ex) {
            System.out.println("Поток на запись не закрыт");
        }
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
                dest,
                from,
                dest.getAverageSalary(),
                from.getAverageSalary(),
                getSalaryChanges(dest, employees, true),
                getSalaryChanges(from, employees, false),
                employees);
        return transfer;
    }

    //Генератор двоичных последовательностей.
    //Создает набор всех двоичных чисел от 1 до 2^n, где n - число сотрудников
    private ArrayList<int[]> getPossibleCombinations(int elementsNumber, int blockSize) {
        ArrayList<int[]> combinations = new ArrayList<>();
        if (elementsNumber > 0) {
            String str = String.format("%1$" + blockSize + "s", Integer.toBinaryString(elementsNumber))
                    .replace(' ', '0');
            int[] numArr = Arrays.stream(str.split("")).mapToInt(Integer::parseInt).toArray();
            combinations.add(numArr);
            combinations.addAll(getPossibleCombinations(elementsNumber - 1, blockSize));
        }
        return combinations;
    }
}
