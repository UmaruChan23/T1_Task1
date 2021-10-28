package employeeLoaders;

import java.io.IOException;

public interface EmployeeFileLoader {
    void readDataFromFile(String path) throws IOException;
    void uploadEmployeeData(String[] employeeData, int LineIndex);
}
