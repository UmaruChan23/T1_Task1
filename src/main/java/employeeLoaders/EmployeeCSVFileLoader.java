package employeeLoaders;

import entity.Department;
import entity.Employee;
import exception.EmployeeDataFormatException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Map;

public class EmployeeCSVFileLoader implements EmployeeFileLoader {

    @Override
    public Map<String, Department> readDataFromFile(String path) {
        Map<String, Department> departmentMap = new HashMap<>();
        try (BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader
                        (new FileInputStream(path)))) {
            String line;
            int lineIndex = 1;
            while ((line = bufferedReader.readLine()) != null) {
                if (lineIndex == 1) {
                    lineIndex++;
                    continue;
                }
                String[] data = line.split(",");
                uploadEmployeeData(data, lineIndex, departmentMap);
                lineIndex++;
            }
        } catch (IOException ex) {
            System.out.println("Не удалось прочитать файл " + path);
        }
        return departmentMap;
    }

    private void uploadEmployeeData(String[] employeeData, int lineIndex, Map<String, Department> departmentMap) {
        if (isValidString(employeeData, lineIndex)) {
            departmentMap.putIfAbsent(employeeData[2].trim(), new Department(employeeData[2].trim()));
            departmentMap
                    .get(employeeData[2].trim())
                    .getEmployeeList()
                    .add(new Employee(employeeData[0], new BigDecimal(employeeData[1].trim())));
        }
    }

    private boolean isValidString(String[] employeeData, int lineIndex) {
        try {
            if (employeeData.length < 3) {
                throw new EmployeeDataFormatException("Строка " + lineIndex +
                        " содержит недостаточно данных");
            }
            if (employeeData[0].trim().isEmpty() || employeeData[0].matches(".*[!@#$%^&*><|/\\d]+.*")) {
                throw new EmployeeDataFormatException("имя сотрудника содетжит некорректные" +
                        "символы или отсутствует в строке " + lineIndex);
            }

            if (employeeData[2].trim().isEmpty() || employeeData[2].matches(".*[!@#$%^&*><|/]+.*")) {
                throw new EmployeeDataFormatException("Название департамента содетжит некорректные" +
                        "символы или отсутствует в строке " + lineIndex);
            }

            String[] salary = employeeData[1].split("\\.");
            if (salary.length > 2) {
                NumberFormat format = new DecimalFormat("#.######");
                salary[1] = format.format(salary[1]);
            }

            if (new BigDecimal(employeeData[1]).compareTo(BigDecimal.ZERO) < 0 &&
                    salary[0].length() > 0 &&
                    salary[1].length() < 3) throw new EmployeeDataFormatException("Значение " +
                    "зарплаты некорректно в строке " + lineIndex);
        } catch (NumberFormatException ex) {
            System.out.println("Невозможно преобразовать значение зарплаты в число " +
                    "в строке " + lineIndex);
            return false;
        } catch (EmployeeDataFormatException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }
}
