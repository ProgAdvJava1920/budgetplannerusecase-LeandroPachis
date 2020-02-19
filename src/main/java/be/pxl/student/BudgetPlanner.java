package be.pxl.student;

import be.pxl.student.entity.Account;
import be.pxl.student.util.BudgetPlannerFeeder;
import be.pxl.student.util.BudgetPlannerImporter;

public class BudgetPlanner {
    public static void main(String[] args) {
        BudgetPlannerImporter plannerFeeder = new BudgetPlannerImporter("src\\main\\resources\\account_payments.csv", new Account());
        plannerFeeder.readFile();
    }
}
