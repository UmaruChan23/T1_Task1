import entity.Department;
import entity.Employee;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Loader {

    private final ArrayList<Employee> employeeList = new ArrayList<>();

    public Loader(String path) {
        try {
            List<String> strings = Files.readAllLines(Paths.get(path));
            for(int i = 1; i < strings.size(); i++) {
                String string = strings.get(i);
                addEmployee(string.split(","));
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    private void addEmployee(String[] string) {
        Employee employee;
        if(string.length > 2) {
            employee = new Employee(
                    string[0],
                    Double.parseDouble(string[1]),
                    Department.valueOf(string[2]));
            employeeList.add(employee);
        }
    }

    public void print() {
        employeeList.forEach(System.out::println);
    }
}
