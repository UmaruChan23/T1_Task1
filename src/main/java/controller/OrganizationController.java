package controller;

import employeeLoaders.EmployeeCSVFileLoader;
import employeeLoaders.EmployeeFileLoader;
import entity.Department;
import entity.Employee;
import entity.Organization;
import entity.Transfer;
import transfer.TransferService;

import java.io.IOException;
import java.util.ArrayList;

public class OrganizationController {
    private Organization organization;

    //выходной параметр 1
    public static void main(String[] args) {
        OrganizationController organizationController = new OrganizationController();
        organizationController.setOrganization(new Organization("T1"));
        EmployeeFileLoader loader = new EmployeeCSVFileLoader();
        try {
            if(args.length > 0) {
                loader.readDataFromFile(args[0], organizationController.organization);
            }
        } catch (IOException ex) {
            System.out.println("Не удалось найти файл");
            return;
        }
        TransferService transferService = new TransferService();
        ArrayList<Transfer> transfers = transferService.getPossibleTransfers(organizationController.organization.getDepartmentMap().get("IT"),
                organizationController.organization.getDepartmentMap().get("HR"));
        for(Transfer transfer: transfers) {
            System.out.println(transfer);
        }
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }
}