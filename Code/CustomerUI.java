import java.util.Map;
import java.util.Scanner;

public class CustomerUI {
    private final Scanner scanner;
    private final CanteenInventory canteen;
    private final Customer customer;
    private final MenuManager menuManager;
    private final PaymentProcessor paymentProcessor;

    public CustomerUI(Scanner scanner, CanteenInventory canteen, Customer customer) {
        this.scanner = scanner;
        this.canteen = canteen;
        this.customer = customer;
        this.menuManager = new MenuManager();
        this.paymentProcessor = new PaymentProcessor(scanner, canteen, customer);
    }
    public void handleUserInput() {
        boolean running = true;
        while (running) {
            menuManager.displayCustomerMenu();
            int choice = getUserChoice();
            running = processCustomerChoice(choice);
        }
    }

    private int getUserChoice() {
        System.out.print("\nEnter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private boolean processCustomerChoice(int choice) {
        return switch (choice) {
            case 1 -> { canteen.displayAllAvailableItems(); yield true; }
            case 2 -> { handleItemTypeView(); yield true; }
            case 3 -> { handlePurchase(); yield true; }
            case 4 -> { showBasket(); yield true; }
            case 5 -> { handlePayment(); yield true; }
            case 6 -> false;
            default -> { System.out.println("Invalid choice. Please try again."); yield true; }
        };
    }

    private void showBasket() {
        if (customer.getReceipt() == 0) {
            System.out.println("Your basket is empty.");
        } else {
            System.out.println("\n=== Your Basket ===");
            Map<String, Integer> orderedItems = customer.getOrderedItems();
            for (Map.Entry<String, Integer> entry : orderedItems.entrySet()) {
                String itemName = entry.getKey();
                int quantity = entry.getValue();
                double itemTotal = canteen.getPrice(itemName) * quantity;
                System.out.printf("%s x%d - $%.2f%n", itemName, quantity, itemTotal);
            }
            System.out.println("\nTotal amount: $" + String.format("%.2f", customer.getReceipt()));
        }
    }

    private void handleItemTypeView() {
        ItemType selectedType = menuManager.getSelectedItemType(scanner);
        canteen.displayAvailableItemsByType(selectedType);
    }

    private void handlePurchase() {
        System.out.print("Enter item name to purchase: ");
        String itemName = scanner.nextLine().toLowerCase();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        if (quantity <=0){
            System.out.println("Invalid quantity. Terminating purchase.");
            return;
        }
        processPurchase(itemName, quantity);
    }

    private void processPurchase(String itemName, int quantity) {
        if (canteen.hasItem(itemName)) {    
            canteen.sellItem(itemName, quantity);            
            if (canteen.isSaleSuccess()){
                customer.addToReceipt(itemName, quantity, canteen.getPrice(itemName) * quantity);
            }
        } else {
            System.out.println("Item not found.");
        }
    }

    private void handlePayment() {
        displayPaymentInfo();
        paymentProcessor.processPayment();
    }

    private void displayPaymentInfo() {
        System.out.println("You have " + String.format("%.2f", (double)customer.getBudget()) + " dollars cash left.");
        System.out.println("You need to pay " + String.format("%.2f", (double)customer.getReceipt()) + " dollars.");
    }
}

