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
            double projectedTotal = currentCategoryTotal + newAmount;

            // Warning pops up when reaching 80% or exceeding the limit/aim
            if (projectedTotal >= limit) {
                throw new budget("CRITICAL ALERT: Expense of $" + newAmount + " EXCEEDS your limit/aim of $" + limit + " for " + category + "!");
            } else if (projectedTotal >= (0.8 * limit)) {
                System.out.println(">>> WARNING: You are getting very close to your aim/limit of $" + limit + " for category: " + category + "! (Current + New = $" + projectedTotal + ")");
            }
        }
    }
}