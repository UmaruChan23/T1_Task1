import employeeLoaders.EmployeeCSVFileLoader;
import entity.Department;
import entity.Employee;

import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        EmployeeCSVFileLoader loader = new EmployeeCSVFileLoader();
        try {
            loader.readDataFromFile(args[0]);
        } catch (IOException ex) {
            System.out.println("Не удалось найти файл");
            System.exit(1);
        }
        for(Department department: Department.getDepartmentMap().values()) {
            System.out.println(department.getName());
            for(Employee employee: department.getEmployeeList()) {
                System.out.println(employee);
            }
        }
    }
}