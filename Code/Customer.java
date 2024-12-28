import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Customer extends Person {
    private double receipt;
    private List<Order> orders;
    private String schoolId;
    private double budget;
    private CanteenInventory canteen;
    private Map<String, Integer> orderedItems = new HashMap<>();
    public Customer(String name, int age, String schoolId, Gender gender, double budget, CanteenInventory canteen) {
        super(name, age, gender);
        this.schoolId = schoolId;
        this.receipt = 0;
        this.orders = new ArrayList<>();
        this.budget = budget;
        canteen.addCustomer(this);
    }   
    public void addToReceipt(String itemName, int quantity, double amount) {
        this.receipt += amount;
        orderedItems.put(itemName, orderedItems.getOrDefault(itemName, 0) + quantity);
    }
    public double getReceipt() {
        return this.receipt;
    }   
    public void clearReceipt() {
        this.receipt = 0;
    }
    public String getSchoolId() {
        return this.schoolId;
    }
    public double getBudget() {
        return this.budget;
    }
    public void setBudget(double budget) {
        this.budget = budget;
    }
    public Map<String, Integer> getOrderedItems() {
        return orderedItems;
    }

    

    
    

}
