/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.io.Serializable;
import java.time.LocalDate;

public class model implements Serializable {
    private String id;
    private String description;
    private double amount;
    private String category;
    private String type; // "INCOME" or "EXPENSE"
    private LocalDate date;

    public model(String id, String description, double amount, String category, String type, LocalDate date) {
        this.id = id;
        this.description = description;
        this.amount = amount;
        this.category = category;
        this.type = type;
        this.date = date;
    }

    public String getId() { return id; }
    public String getDescription() { return description; }
    public double getAmount() { return amount; }
    public String getCategory() { return category; }
    public String getType() { return type; }
    public LocalDate getDate() { return date; }

    @Override
    public String toString() {
        return String.format("[%s] %s | %s | $%.2f | Category: %s", date, id, description, amount, category);
    }
}
    

