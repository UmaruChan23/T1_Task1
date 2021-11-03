package controller;

import employeeLoaders.EmployeeCSVFileLoader;
import employeeLoaders.EmployeeFileLoader;
import entity.Department;
import entity.Organization;
import entity.Transfer;
import transfer.TransferService;

import java.util.ArrayList;
import java.util.Iterator;
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
        transferService.getPossibleTransfers( departments,
                "C:\\Users\\fmatorin\\IdeaProjects\\Task1\\src\\main\\resources\\result.txt");
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }
}