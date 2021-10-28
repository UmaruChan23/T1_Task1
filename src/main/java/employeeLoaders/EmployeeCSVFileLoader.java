package employeeLoaders;

import entity.Department;
import entity.Employee;
import exception.EmployeeDataFormatException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EmployeeCSVFileLoader implements EmployeeFileLoader{

    @Override
    public void readDataFromFile(String path) throws IOException {
        Path employeeFilePath = Paths.get(path);
        InputStream is = Files.newInputStream(employeeFilePath);
        InputStreamReader isReader = new InputStreamReader(is);
        BufferedReader bufferedReader = new BufferedReader(isReader);
        String line;
        int lineIndex = 1;
        while ((line = bufferedReader.readLine()) != null) {
            if(lineIndex == 1) {
                lineIndex++;
                continue;
            }
            String[] data = line.split(",");
            uploadEmployeeData(data, lineIndex);
            lineIndex++;
        }
    }

    @Override
    public void uploadEmployeeData(String[] employeeData, int lineIndex) {
        String departmentName = "";
        try{
            if(employeeData.length < 3) throw new EmployeeDataFormatException("Строка " + lineIndex +
                                                                        " содержит недостаточно данных");
            if(employeeData[0].isEmpty() || employeeData[0].matches(".*[!@#$%^&*\\d]"))
                throw new EmployeeDataFormatException("имя сотрудника содетжит некорректные" +
                        "символы или отсутствует в строке " + lineIndex);

            if(employeeData[2].isEmpty() || employeeData[2].matches(".*[!@#$%^&*]"))
                throw new EmployeeDataFormatException("Название департамента содетжит некорректные" +
                        "символы или отсутствует в строке " + lineIndex);

            departmentName = employeeData[2];

            if(Double.parseDouble(employeeData[1]) < 0) throw new EmployeeDataFormatException("Значение " +
                    "зарплаты некорректно в строке " + lineIndex);
        } catch (NumberFormatException ex) {
            System.out.println("Невозможно преобразовать значение зарплаты в число " +
                    "в строке " + lineIndex);
        } catch (EmployeeDataFormatException ex) {
            System.out.println(ex.getMessage());
        }
        Department.getDepartmentMap().putIfAbsent(departmentName, new Department(departmentName));
        Department
                .getDepartmentMap()
                .get(departmentName)
                .getEmployeeList()
                .add(new Employee(employeeData[0], Double.parseDouble(employeeData[1])));
    }
}
