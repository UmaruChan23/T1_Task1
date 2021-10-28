package entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* Класс для хранения данных о департаменте и списке сотрудников
 * @name - название департамента
 * @employeeList - список сотрудников в департаменте
 * @departmentMap - список всех департаментов
*/
public class Department {
    private final String name;
    private final List<Employee> employeeList;
    private final static Map<String, Department> departmentMap = new HashMap<>();

    public Department(String name) {
        this.name = name;
        this.employeeList = new ArrayList<>();
    }

    public static void addDepartment(Department department) {
        if(!departmentMap.containsKey(department.name)){
            departmentMap.put(department.name, department);
        }
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public static Map<String, Department> getDepartmentMap() {
        return departmentMap;
    }
}
