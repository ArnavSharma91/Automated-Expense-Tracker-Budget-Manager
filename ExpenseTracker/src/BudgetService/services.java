/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BudgetService;
import BudgetExceededException.budget;
import java.util.HashMap;
import java.util.Map;

 
public class services {
    private Map<String, Double> categoryLimits = new HashMap<>();

    public void setCategoryLimit(String category, double limit) {
        categoryLimits.put(category.toUpperCase(), limit);
    }

    public void checkBudgetThreshold(String category, double currentCategoryTotal, double newAmount) throws budget {
        String key = category.toUpperCase();
        if (categoryLimits.containsKey(key)) {
            double limit = categoryLimits.get(key);
            if (currentCategoryTotal + newAmount > limit) {
                throw new budget("Warning: Adding this transaction ($" + newAmount + 
                    ") exceeds your budget limit of $" + limit + " for category: " + category);
            }
        }
    }
    
}
