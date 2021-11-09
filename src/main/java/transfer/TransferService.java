package transfer;

import entity.Department;
import entity.Employee;
import entity.Transfer;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TransferService {

    public void getPossibleTransfers(List<Department> departments, String path) {
        //int n = departments.size(),
        //k = 2;
        //BigInteger combinations = factorial(n).divide( factorial(n - k).multiply(factorial(k)) );
        for (int i = 0; i < departments.size(); i++) {
            for (int j = i +1; j < departments.size(); j++) {
                transferBetweenTwo(departments.get(i), departments.get(j), path);
            }
        }
    }

    private BigInteger factorial(int n) {
        if (n <= 1) {
            return BigInteger.ONE;
        } else {
            return BigInteger.valueOf(n).multiply(factorial(n - 1));
        }
    }

    public void transferBetweenTwo(Department firstDepartment, Department secondDepartment, String outputPath) {
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

                if (transferList.size() > 0) {
                    resultSalary = resultSalary.divide(new BigDecimal(transferList.size()), 2, RoundingMode.HALF_UP);
                } else {
                    continue;
                }

                //сравниваем получившееся среднее значение зарплаты с текущими по департаментам
                if (resultSalary.compareTo(fromDepartment.getAverageSalary()) < 0 &&
                        resultSalary.compareTo(destinationDepartment.getAverageSalary()) > 0) {
                    write(getPossibleAverageSalaryChanges(fromDepartment,
                            destinationDepartment,
                            transferList), outputPath);
                    //transfers.add(getPossibleAverageSalaryChanges(fromDepartment, destinationDepartment, transferList));
                }
            }
        }
    }

    private void write(Transfer transfer, String path) {
        Path filePath = Paths.get(path);
        List<String> line = new ArrayList<>();
        line.add(transfer.toString());
        try {
            Files.write(filePath, line,
                    StandardCharsets.UTF_8,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println("Не удалось произвести запись в файл" + path);
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
