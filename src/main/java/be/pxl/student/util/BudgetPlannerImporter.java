package be.pxl.student.util;

import be.pxl.student.BudgetPlanner;
import be.pxl.student.entity.Account;
import be.pxl.student.entity.Payment;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Util class to import csv file
 */
public class BudgetPlannerImporter {
    /* Constructor
     * Reader
     * Create objects method
     * List
     * JUnit
     */
    private String fileName;
    private Account account;

    public BudgetPlannerImporter(String fileName, Account account) {
        this.fileName = fileName;
        this.account = account;
    }

    public Account readFile() {
        Path path = Paths.get(fileName);
        List<Payment> payments = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine();
            line = reader.readLine();
            this.account = createAccount(line.split(","));
            while (line != null) {
                Payment payment = createPayment(line.split(","));
                payments.add(payment);
                System.out.println(payment.toString());
                line = reader.readLine();
            }
            this.account.setPayments(payments);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.account;
    }

    private Account createAccount(String[] lines) {
        Account account = new Account();
        account.setName(lines[0]);
        account.setIBAN(lines[1]);
        return account;
    }

    private Payment createPayment(String[] lines) {
        // Account name,Account IBAN,Counteraccount IBAN,Transaction date,Amount,Currency,Detail
        float amount = Float.parseFloat(lines[4]);
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);
        LocalDateTime date = LocalDateTime.parse(lines[3], dateFormat);
        String currency = lines[5];
        String details = lines[6];
        return new Payment(date, amount, currency, details);
    }
}
