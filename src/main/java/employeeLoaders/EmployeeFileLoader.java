package employeeLoaders;

import entity.Organization;

import java.io.IOException;

public interface EmployeeFileLoader {
    void readDataFromFile(String path, Organization organization) throws IOException;
}
