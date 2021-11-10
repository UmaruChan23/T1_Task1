package entity;

import java.math.BigDecimal;
import java.util.List;

public class Transfer {
    private final Department destinationDepartment;
    private final Department fromDepartment;
    private final BigDecimal destinationDepartmentBeforeTransfer;
    private final BigDecimal fromDepartmentBeforeTransfer;
    private final BigDecimal destinationDepartmentAfterTransfer;
    private final BigDecimal fromDepartmentAfterTransfer;
    private final List<Employee> employeeTransferList;

    public Transfer(Department destinationDepartment,
                    Department fromDepartment,
                    BigDecimal destinationDepartmentBeforeTransfer,
                    BigDecimal fromDepartmentBeforeTransfer,
                    BigDecimal destinationDepartmentAfterTransfer,
                    BigDecimal fromDepartmentAfterTransfer,
                    List<Employee> employeeTransferList) {
        this.destinationDepartment = destinationDepartment;
        this.fromDepartment = fromDepartment;
        this.destinationDepartmentBeforeTransfer = destinationDepartmentBeforeTransfer;
        this.fromDepartmentBeforeTransfer = fromDepartmentBeforeTransfer;
        this.destinationDepartmentAfterTransfer = destinationDepartmentAfterTransfer;
        this.fromDepartmentAfterTransfer = fromDepartmentAfterTransfer;
        this.employeeTransferList = employeeTransferList;
    }

    public Department getDestinationDepartment() {
        return destinationDepartment;
    }

    public Department getFromDepartment() {
        return fromDepartment;
    }

    public BigDecimal getDestinationDepartmentBeforeTransfer() {
        return destinationDepartmentBeforeTransfer;
    }

    public BigDecimal getFromDepartmentBeforeTransfer() {
        return fromDepartmentBeforeTransfer;
    }

    public BigDecimal getDestinationDepartmentAfterTransfer() {
        return destinationDepartmentAfterTransfer;
    }

    public BigDecimal getFromDepartmentAfterTransfer() {
        return fromDepartmentAfterTransfer;
    }

    public List<Employee> getEmployeeTransferList() {
        return employeeTransferList;
    }

    @Override
    public String toString() {
        return "Трансфер" +
                " в департамент " + destinationDepartment +
                " из департамента " + fromDepartment +
                " cотрудников " + employeeTransferList + "\n" +
                "    Средняя зарплата департамента " + destinationDepartment + "\n" +
                "     до трансфера " + destinationDepartmentBeforeTransfer + "\n" +
                "     после трансфера " + destinationDepartmentAfterTransfer + "\n" +
                "    Средняя зарплата департамента " + fromDepartment + "\n" +
                "     до трансфера " + fromDepartmentBeforeTransfer + "\n" +
                "     после трансфера " + fromDepartmentAfterTransfer + "\n";
    }
}
