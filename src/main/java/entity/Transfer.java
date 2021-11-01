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
        return "Transfer{" +
                "destinationDepartment=" + destinationDepartment +
                ", fromDepartment=" + fromDepartment +
                ", destinationDepartmentBeforeTransfer=" + destinationDepartmentBeforeTransfer +
                ", fromDepartmentBeforeTransfer=" + fromDepartmentBeforeTransfer +
                ", destinationDepartmentAfterTransfer=" + destinationDepartmentAfterTransfer +
                ", fromDepartmentAfterTransfer=" + fromDepartmentAfterTransfer +
                ", employeeTransferList=" + employeeTransferList +
                '}';
    }
}
