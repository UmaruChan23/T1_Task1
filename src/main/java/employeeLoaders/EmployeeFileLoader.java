package employeeLoaders;

import entity.Department;

import java.util.Map;

public interface EmployeeFileLoader {
    Map<String, Department> readDataFromFile(String path);
}
