package ExpenseAnalyticsService;

import model.model;
import java.util.List;

public class analysis {

    public double calculateTotalIncome(List<model> transactions) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("INCOME"))
                .mapToDouble(model::getAmount).sum();
    }

    public double calculateTotalExpense(List<model> transactions) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE"))
                .mapToDouble(model::getAmount).sum();
    }

    public double getCategoryTotal(List<model> transactions, String category) {
        return transactions.stream()
                .filter(t -> t.getType().equalsIgnoreCase("EXPENSE") && t.getCategory().equalsIgnoreCase(category))
                .mapToDouble(model::getAmount).sum();
    }
}