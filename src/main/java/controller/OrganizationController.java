package controller;

import employeeLoaders.EmployeeCSVFileLoader;
import employeeLoaders.EmployeeFileLoader;
import entity.Department;
import entity.Organization;
import transfer.TransferService;

import java.util.ArrayList;
import java.util.List;

public class OrganizationController {
    private Organization organization;

    //выходной параметр 1
    public static void main(String[] args) {
        OrganizationController organizationController = new OrganizationController();
        organizationController.setOrganization(new Organization("T1"));
        EmployeeFileLoader loader = new EmployeeCSVFileLoader();
        try {
            if(args.length > 0) {
                organizationController.organization.getDepartmentMap().putAll(
                        loader.readDataFromFile(args[0])
                );
            }
        } catch (Exception ex) {
            System.out.println("Произошла ошибка при обработке файла " + args[0]);
            return;
        }
        TransferService transferService = new TransferService();
        List<Department> departments = new ArrayList<>(organizationController.organization.getDepartmentMap().values());
        if (args.length > 1) {
            transferService.getPossibleTransfers( departments, args[1]);
        } else {
            System.out.println("Файл для записи отсутствует");
        }
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }
}