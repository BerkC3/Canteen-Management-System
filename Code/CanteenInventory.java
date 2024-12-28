import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CanteenInventory {
    private Map<String, Integer> stock;
    private Map<String, ItemType> itemType;
    private Map<String, StockStatus> stockStatus;
    private Map<String, Double> prices;
    private HashMap<String, String> managerUsernamePassword;
    private List<Customer> customers;
    private Map<String, Integer> tempStock;
    private Map<String, Integer> prepareTime;
    private boolean isSuccess = false;

    public CanteenInventory() {
        this.stock = new HashMap<>();
        this.itemType = new HashMap<>();
        this.stockStatus = new HashMap<>();
        this.prices = new HashMap<>();
        this.managerUsernamePassword = new HashMap<>();
        this.customers = new ArrayList<>();
        this.tempStock = new HashMap<>();
        this.prepareTime = new HashMap<>();
        this.isSuccess = false;
    }
    public void addManager(String username, String password) {
        this.managerUsernamePassword.put(username, password);
    } 
    public boolean isValidManager(String username, String password) {
        return this.managerUsernamePassword.containsKey(username) && this.managerUsernamePassword.get(username).equals(password);
    }  
    public void addItem(String itemName, int quantity, ItemType itemType, double price, int prepareTime) {
        itemName = itemName.toLowerCase();
        this.stock.put(itemName, quantity);
        this.itemType.put(itemName, itemType);
        this.stockStatus.put(itemName, StockStatus.IN_STOCK);
        this.prices.put(itemName, price);
        this.prepareTime.put(itemName, prepareTime);
    }
    public int getPrepareTime(String itemName, int quantity) {
        itemName = itemName.toLowerCase();
        return this.prepareTime.get(itemName) * quantity;
    }

    public void updateStock(String itemName, int quantity) {
        itemName = itemName.toLowerCase();
        if (!this.stock.containsKey(itemName)) {
            System.out.println("Item " + itemName + " does not exist in inventory");
            return;
        }
        this.stock.put(itemName, this.stock.get(itemName) + quantity);
        this.stockStatus.put(itemName, this.stock.get(itemName) > 0 ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK); 
    }

    public void sellItem(String itemName, int quantity) {
        itemName = itemName.toLowerCase();
        if (!this.stock.containsKey(itemName)) {
            System.out.println("Item " + itemName + " does not exist in inventory");
            return;
        }
        else if (this.stock.get(itemName) < quantity) {
            System.out.println("Not enough stock for " + itemName);
            return;
        }  
        else{
            System.out.println("You ordered " + quantity + " porsion of " + itemName + " with the price of " + String.format("%.2f", this.getPrice(itemName)*quantity) + " dollars total.");
            this.stock.put(itemName, this.stock.get(itemName) - quantity);
            this.stockStatus.put(itemName, this.stock.get(itemName) > 0 ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK);
            this.isSuccess = true;
        }
        if (!tempStock.containsKey(itemName)) {
            tempStock.put(itemName, this.stock.get(itemName));
        }
    }
    public boolean isSaleSuccess() {
        return this.isSuccess;
    }
    public void rollbackTransaction() {
        for (Map.Entry<String, Integer> entry : tempStock.entrySet()) {
            String itemName = entry.getKey();
            this.stock.put(itemName, entry.getValue());
            this.stockStatus.put(itemName, entry.getValue() > 0 ? StockStatus.IN_STOCK : StockStatus.OUT_OF_STOCK);
        }
        tempStock.clear();
    }

    public void confirmTransaction() {
        tempStock.clear();
    }

    public boolean processPayment(Customer customer, PaymentMethod paymentMethod) {
        boolean paymentSuccess = false;
        
        switch(paymentMethod) {
            case CASH:
                paymentSuccess = true;
                break;
            case CREDIT_CARD:
            case DEBIT_CARD:
                break;
        }

        if (!paymentSuccess) {
            customer.clearReceipt();
            rollbackTransaction();
            System.out.println("Payment failed. Transaction cancelled.");
        } else {
            confirmTransaction();
            System.out.println("Payment successful!");
        }
        
        return paymentSuccess;
    }

    public void setPrice(String itemName, double price) {
        itemName = itemName.toLowerCase();
        if (!this.prices.containsKey(itemName)) {
            System.out.println("Item " + itemName + " does not exist in inventory");
            return;
        }
        this.prices.put(itemName, price);
    }
    
    public void displayAllInventory() {
        System.out.println("\nFull Inventory:");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s %-10s %-10s $%-8s%n", "Item", "Quantity", "Status", "Price");
        System.out.println("----------------------------------------");
        for (Map.Entry<String, Integer> entry : this.stock.entrySet()) {
            String itemName = entry.getKey();
            int quantity = entry.getValue();
            StockStatus status = stockStatus.get(itemName);
            double price = prices.get(itemName);
            System.out.printf("%-20s %-10d %-10s $%-8.2f%n", 
                itemName, 
                quantity,
                status,
                price);
        }
        System.out.println("----------------------------------------");
    }

    public void displayAllAvailableItems() {
        for (Map.Entry<String, StockStatus> entry : this.stockStatus.entrySet()) {
            if (entry.getValue() == StockStatus.IN_STOCK) {
                System.out.printf("Item: %-15s Available: %-5d Price: $%.2f%n", 
                    entry.getKey(),
                    this.stock.get(entry.getKey()),
                    this.prices.get(entry.getKey()));
            }
        }
    }

    public void displayItemsByType(ItemType type) {
        System.out.println("\nItems of type " + type + ":");
        System.out.println("----------------------------------------");
        System.out.printf("%-20s %-10s %-10s $%-8s%n", "Item", "Quantity", "Status", "Price");
        System.out.println("----------------------------------------");
        for (Map.Entry<String, ItemType> entry : itemType.entrySet()) {
            if (entry.getValue() == type) {
                String itemName = entry.getKey();
                System.out.printf("%-20s %-10d %-10s $%-8.2f%n",
                    itemName,
                    stock.get(itemName), 
                    stockStatus.get(itemName),
                    prices.get(itemName));
            }
        }
        System.out.println("----------------------------------------");
    }

    public void displayAvailableItemsByType(ItemType type) {
        System.out.println("\nAvailable items of type " + type + ":");
        for (Map.Entry<String, StockStatus> entry : stockStatus.entrySet()) {
            if (entry.getValue() == StockStatus.IN_STOCK && itemType.get(entry.getKey()) == type) {
                System.out.printf("Item: %-15s Available: %-5d Price: $%.2f%n", 
                    entry.getKey(),
                    stock.get(entry.getKey()),
                    prices.get(entry.getKey()));    
            }
        }
    }   
    public boolean hasCustomer(String schoolId) {
        for (Customer customer : this.customers) {
            if (customer.getSchoolId().equals(schoolId)) {
                return true;
            }
        }
        return false;
    }   
    public Customer getCustomer(String schoolId) {
        for (Customer customer : this.customers) {
            if (customer.getSchoolId().equals(schoolId)) {
                return customer;
            }
        }
        return null;
    }
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }   
    public double getPrice(String itemName) {
        itemName = itemName.toLowerCase();
        if (!this.prices.containsKey(itemName)) {
            System.out.println("Item " + itemName + " does not exist in inventory");
            return 0.0;
        }
        return this.prices.get(itemName);
    }   
    public boolean hasItem(String itemName) {
        itemName = itemName.toLowerCase();
        return this.stock.containsKey(itemName);
    }   
    public int getStock(String itemName) {
        itemName = itemName.toLowerCase();
        return this.stock.get(itemName);
    }
}
