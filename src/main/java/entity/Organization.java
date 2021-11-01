package entity;

import java.util.HashMap;
import java.util.Map;

public class Organization {
    private String name;
    private final Map<String, Department> departmentMap;

    public Organization(String name) {
        this.name = name;
        this.departmentMap = new HashMap<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Map<String, Department> getDepartmentMap() {
        return departmentMap;
    }


}
