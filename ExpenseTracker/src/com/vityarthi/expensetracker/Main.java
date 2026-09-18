/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package com.vityarthi.expensetracker;
import model.model;
import BudgetExceededException.budget;
import BudgetService.services;
import ExpenseAnalyticsService.analysis;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author drapo
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        List<model> transactions = new ArrayList<>();
        services budgetService = new services();
        analysis analyticsService = new analysis();

        // Set spending limit for Food category
        budgetService.setCategoryLimit("FOOD", 150.0);

        // Add initial entries
        transactions.add(new model("TXN001", "Salary", 2000.0, "INCOME", "INCOME", LocalDate.now()));
        transactions.add(new model("TXN002", "Groceries", 100.0, "FOOD", "EXPENSE", LocalDate.now()));

        System.out.println("--- Expense Tracker System ---");

        // Verify exception throwing logic
        try {
            double currentFoodTotal = analyticsService.getCategoryTotal(transactions, "FOOD");
            double newExpense = 60.0;
            
            budgetService.checkBudgetThreshold("FOOD", currentFoodTotal, newExpense);
            transactions.add(new model("TXN003", "Dinner", newExpense, "FOOD", "EXPENSE", LocalDate.now()));
            System.out.println("Transaction added successfully.");
        } catch (budget e) {
            System.err.println("BUDGET ALERT: " + e.getMessage());
        }

        // Summary calculations
        System.out.println("\n--- Summary Report ---");
        System.out.println("Total Income: $" + analyticsService.calculateTotalIncome(transactions));
        System.out.println("Total Expense: $" + analyticsService.calculateTotalExpense(transactions));
    }
       
    }
    

