import java.util.Scanner;

public class MenuManager {
    public void displayMainMenu() {
        System.out.println("Welcome to Canteen Management System");
        System.out.println("1. Customer Interface");
        System.out.println("2. Manager Interface");
        System.out.println("3. Exit");
    }

    public void displayCustomerMenu() {
        System.out.println("\n=== Customer Menu ===");
        System.out.println("1. View all items");    
        System.out.println("2. View items by type");
        System.out.println("3. Purchase item");
        System.out.println("4. Show basket");
        System.out.println("5. Pay");
        System.out.println("6. Exit");
    }

    public void displayManagerMenu() {
        System.out.println("\n=== Manager Menu ===");
        System.out.println("1. View all inventory");
        System.out.println("2. View items by type");
        System.out.println("3. Add new item");
        System.out.println("4. Update stock");
        System.out.println("5. Update price");
        System.out.println("6. Exit");
    }

    public void displayPaymentOptions() {
        System.out.println("Select payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card"); 
        System.out.println("3. Cash");
        System.out.println("4. Exit");
    }

    public ItemType getSelectedItemType(Scanner scanner) {
        System.out.println("Select item type (1:FOOD, 2:BEVERAGE, 3:DESSERT): ");
        int typeChoice = scanner.nextInt();
        return switch (typeChoice) {
            case 1 -> ItemType.FOOD;
            case 2 -> ItemType.BEVERAGE;
            case 3 -> ItemType.DESSERT;
            default -> ItemType.FOOD;
        };
    }
}