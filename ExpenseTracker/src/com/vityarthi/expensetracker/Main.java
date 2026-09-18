/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.vityarthi.expensetracker;
import model.model;
import BudgetExceededException.budget;
import BudgetService.services;
import ExpenseAnalyticsService.analysis;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

 
public class Main {
    private static final String FILE_NAME = "history.txt";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
    
    Scanner scanner = new Scanner(System.in);
        List<model> transactions = loadHistory();
        services budgetService = new services();
        analysis analyticsService = new analysis();

        // Configure default budget limits/aims
        budgetService.setCategoryLimit("FOOD", 150.0);
        budgetService.setCategoryLimit("RENT", 500.0);

        while (true) {
            System.out.println("\n==========================================");
            System.out.println("   EXPENSE TRACKER & BUDGET MANAGER       ");
            System.out.println("==========================================");
            System.out.println("1. Add Expense / Income");
            System.out.println("2. Check Balance & Payment History");
            System.out.println("3. Exit");
            System.out.print("Select an option (1-3): ");

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("\n--- Add New Transaction ---");
                System.out.print("Enter Type (1 for INCOME, 2 for EXPENSE): ");
                String typeChoice = scanner.nextLine().trim();
                String type = typeChoice.equals("1") ? "INCOME" : "EXPENSE";

                System.out.print("Enter Description (e.g., Dinner, Salary): ");
                String description = scanner.nextLine().trim();

                System.out.print("Enter Category (e.g., FOOD, RENT, SALARY): ");
                String category = scanner.nextLine().trim().toUpperCase();

                System.out.print("Enter Amount: $");
                double amount = 0;
                try {
                    amount = Double.parseDouble(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Invalid amount entered!");
                    continue;
                }

                String id = "TXN" + (transactions.size() + 1);

                if (type.equalsIgnoreCase("EXPENSE")) {
                    double currentTotal = analyticsService.getCategoryTotal(transactions, category);
                    try {
                        budgetService.checkBudgetThreshold(category, currentTotal, amount);
                    } catch (budget e) {
                        System.err.println("\n[BUDGET POPUP ALERT] " + e.getMessage());
                        System.out.print("Do you still want to log this expense? (y/n): ");
                        String confirm = scanner.nextLine().trim();
                        if (!confirm.equalsIgnoreCase("y")) {
                            System.out.println("Transaction cancelled.");
                            continue;
                        }
                    }
                }

                model record = new model(id, description, amount, category, type, LocalDate.now());
                transactions.add(record);
                saveRecordToFile(record);
                System.out.println("Successfully recorded: " + record);

            } else if (choice.equals("2")) {
                System.out.println("\n--- Payment History & Balance Overview ---");
                if (transactions.isEmpty()) {
                    System.out.println("No transaction records found.");
                } else {
                    for (model t : transactions) {
                        System.out.println(t);
                    }
                }

                double income = analyticsService.calculateTotalIncome(transactions);
                double expense = analyticsService.calculateTotalExpense(transactions);
                double balance = income - expense;

                System.out.println("------------------------------------------");
                System.out.println("Total Income  : $" + income);
                System.out.println("Total Expense : $" + expense);
                System.out.println("Net Balance   : $" + balance);
                System.out.println("------------------------------------------");

            } else if (choice.equals("3")) {
                System.out.println("Exiting Application. Goodbye!");
                break;
            } else {
                System.out.println("Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void saveRecordToFile(model record) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME, true))) {
            writer.println(record.getId() + "," + record.getDescription() + "," + record.getAmount() + "," + 
                           record.getCategory() + "," + record.getType() + "," + record.getDate());
        } catch (IOExceptio1n e) {
            System.err.println("Could not save transaction to file: " + e.getMessage());
        }
    }

    private static List<model> loadHistory() {
        List<model> list = new ArrayList<>();
        File file = new File(FILE_NAME);
        if (!file.exists()) return list;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    list.add(new model(parts[0], parts[1], Double.parseDouble(parts[2]), 
                             parts[3], parts[4], LocalDate.parse(parts[5])));
                }
            }
        } catch (Exception e) {
            System.err.println("Error reading history file: " + e.getMessage());
        }
        return list;
    }
}

