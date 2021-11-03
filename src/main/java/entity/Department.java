package entity;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Department {
    private final String name;
    private final List<Employee> employeeList;

    public Department(String name) {
        this.name = name;
        this.employeeList = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Employee> getEmployeeList() {
        return employeeList;
    }

    public BigDecimal getAverageSalary() {
        if(employeeList.isEmpty()) {
            return BigDecimal.ZERO;
        }
        return getTotalSalary().divide(new BigDecimal(employeeList.size()), 2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalSalary() {
        BigDecimal totalSalary = BigDecimal.ZERO;
        for(Employee employee: employeeList) {
            totalSalary = totalSalary.add(employee.getSalary());
        }
        return totalSalary;
    }
}
