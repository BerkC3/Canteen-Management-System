import java.util.Scanner;

public class ManagerUI {
    private final Scanner scanner;
    private final CanteenInventory canteen;
    private final MenuManager menuManager;
    
    public ManagerUI(Scanner scanner, CanteenInventory canteen) {
        this.scanner = scanner;
        this.canteen = canteen;
        this.menuManager = new MenuManager();
    }

    public void handleUserInput() {
        boolean running = true;
        while (running) {
            menuManager.displayManagerMenu();
            int choice = getUserChoice();
            running = processManagerChoice(choice);
        }
    }

    private int getUserChoice() {
        System.out.print("\nEnter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    private boolean processManagerChoice(int choice) {
        return switch (choice) {
            case 1 -> { canteen.displayAllInventory(); yield true; }
            case 2 -> { handleItemTypeView(); yield true; }
            case 3 -> { handleAddItem(); yield true; }
            case 4 -> { handleUpdateStock(); yield true; }
            case 5 -> { handleUpdatePrice(); yield true; }
            case 6 -> false;
            default -> { System.out.println("Invalid choice. Please try again."); yield true; }
        };
    }

    private void handleItemTypeView() {
        ItemType selectedType = menuManager.getSelectedItemType(scanner);
        canteen.displayItemsByType(selectedType);
    }

    private void handleAddItem() {
        System.out.print("Enter item name: ");
        String itemName = scanner.nextLine().toLowerCase();
        System.out.print("Enter quantity: ");
        int quantity = scanner.nextInt();
        System.out.print("Enter price: ");
        double price = scanner.nextDouble();
        ItemType type = menuManager.getSelectedItemType(scanner);
        System.out.print("Enter prepare time: ");
        int prepareTime = scanner.nextInt();
        canteen.addItem(itemName, quantity, type, price, prepareTime);
    }

    private void handleUpdateStock() {
        System.out.print("Enter item name to update: ");
        String updateItemName = scanner.nextLine().toLowerCase();
        System.out.print("Enter quantity to add (use negative for removal): ");
        int updateQuantity = scanner.nextInt();

        if(canteen.hasItem(updateItemName) && (updateQuantity < (-1 * canteen.getStock(updateItemName)))){
            System.out.println("Invalid quantity. Terminating process.");
            return;
        }
        canteen.updateStock(updateItemName, updateQuantity);
    }

    private void handleUpdatePrice() {
        System.out.print("Enter item name: ");
        String priceItemName = scanner.nextLine().toLowerCase();
        System.out.print("Enter new price: ");
        double newPrice = scanner.nextDouble();
        canteen.setPrice(priceItemName, newPrice);
    }
}