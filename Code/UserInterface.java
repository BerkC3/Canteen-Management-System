import java.util.Scanner;

public class UserInterface {
    private final Scanner scanner;
    private final CanteenInventory canteen;
    private boolean appRunning;
    private final LoginManager loginManager;
    private final MenuManager menuManager;

    public UserInterface() {
        this.scanner = new Scanner(System.in);
        this.canteen = new CanteenInventory();
        this.appRunning = true;
        this.loginManager = new LoginManager(scanner, canteen);
        this.menuManager = new MenuManager();
        initializeData();
    }

    private void initializeData() {
        Manager manager = new Manager("John Smith", 35, Gender.MALE, "operations_head", "ops123secure", canteen);
        Manager manager2 = new Manager("Sarah Chen", 42, Gender.FEMALE, "inventory_manager", "inv456control", canteen);
        Manager manager3 = new Manager("Michael Rodriguez", 38, Gender.MALE, "shift_supervisor", "shift789lead", canteen);
        Manager manager4 = new Manager("Berk Cuce", 38, Gender.MALE, "admin", "password", canteen);

        Customer customer1 = new Customer("Emily Chen", 19, "STU123456", Gender.FEMALE, 150, canteen);
        Customer customer2 = new Customer("Michael Smith", 20, "STU234567", Gender.MALE, 200, canteen);
        Customer customer3 = new Customer("Sarah Johnson", 18, "STU345678", Gender.FEMALE, 100, canteen);
        Customer customer4 = new Customer("David Rodriguez", 21, "STU456789", Gender.MALE, 175, canteen);
        Customer customer5 = new Customer("Jessica Lee", 19, "STU567890", Gender.FEMALE, 125, canteen);
        Customer customer6 = new Customer("Marcus Williams", 20, "STU678901", Gender.MALE, 250, canteen);
        Customer customer7 = new Customer("Sophia Patel", 18, "STU789012", Gender.FEMALE, 150, canteen);
        Customer customer8 = new Customer("James Wilson", 22, "STU890123", Gender.MALE, 300, canteen);
        Customer customer9 = new Customer("Olivia Brown", 19, "STU901234", Gender.FEMALE, 175, canteen);
        Customer customer10 = new Customer("Alexander Kim", 21, "STU012345", Gender.MALE, 225, canteen);

        canteen.addItem("Burger", 25, ItemType.FOOD, 8.99, 10);
        canteen.addItem("Chicken Nuggets", 40, ItemType.FOOD, 6.99, 8);
        canteen.addItem("Chicken Tenders", 35, ItemType.FOOD, 7.99, 12);
        canteen.addItem("Chicken Wings", 30, ItemType.FOOD, 7.99, 15);
        canteen.addItem("Fried Potatoes", 35, ItemType.FOOD, 4.99, 8);
        canteen.addItem("Hot Dog", 35, ItemType.FOOD, 5.99, 5);
        canteen.addItem("Kebab", 20, ItemType.FOOD, 9.99, 12);
        canteen.addItem("Noodles", 35, ItemType.FOOD, 7.99, 10);
        canteen.addItem("Pasta", 25, ItemType.FOOD, 8.99, 15);
        canteen.addItem("Pizza", 15, ItemType.FOOD, 12.99, 20);
        canteen.addItem("Rice", 40, ItemType.FOOD, 3.99, 15);
        canteen.addItem("Salad", 20, ItemType.FOOD, 6.99, 5);
        canteen.addItem("Sandwich", 30, ItemType.FOOD, 6.99, 5);
        canteen.addItem("Soup", 25, ItemType.FOOD, 4.99, 8);

        canteen.addItem("Coffee", 60, ItemType.BEVERAGE, 3.49, 3);
        canteen.addItem("Coke", 50, ItemType.BEVERAGE, 2.49, 1);
        canteen.addItem("Fanta", 50, ItemType.BEVERAGE, 2.49, 1);
        canteen.addItem("Juice", 45, ItemType.BEVERAGE, 2.99, 2);
        canteen.addItem("Lemonade", 45, ItemType.BEVERAGE, 2.99, 3);
        canteen.addItem("Milk", 40, ItemType.BEVERAGE, 1.99, 1);
        canteen.addItem("Mineral Water", 80, ItemType.BEVERAGE, 1.99, 1);
        canteen.addItem("Sprite", 50, ItemType.BEVERAGE, 2.49, 1);
        canteen.addItem("Tea", 70, ItemType.BEVERAGE, 2.49, 4);
        canteen.addItem("Water", 100, ItemType.BEVERAGE, 1.49, 1);

        canteen.addItem("Brownie", 40, ItemType.DESSERT, 3.49, 5);
        canteen.addItem("Cake", 20, ItemType.DESSERT, 5.99, 8);
        canteen.addItem("Chocolate", 40, ItemType.DESSERT, 3.99, 1);
        canteen.addItem("Churros", 35, ItemType.DESSERT, 3.99, 6);
        canteen.addItem("Cupcake", 50, ItemType.DESSERT, 2.99, 3);
        canteen.addItem("Donut", 45, ItemType.DESSERT, 2.99, 3);
        canteen.addItem("Ice Cream", 30, ItemType.DESSERT, 4.99, 2);
        canteen.addItem("Macaron", 30, ItemType.DESSERT, 4.99, 3);
        canteen.addItem("Milkshake", 25, ItemType.DESSERT, 4.99, 5);
        canteen.addItem("Pancakes", 25, ItemType.DESSERT, 5.99, 8);
        canteen.addItem("Pie", 15, ItemType.DESSERT, 5.99, 7);
        canteen.addItem("Waffles", 25, ItemType.DESSERT, 5.99, 7);
    }

    public void start() {
        while (appRunning) {
            menuManager.displayMainMenu();
            System.out.print("Select interface: ");
            int interfaceChoice = scanner.nextInt();
            scanner.nextLine();

            processMainMenuChoice(interfaceChoice);
        }
        scanner.close();
    }

    private void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1 -> loginManager.handleCustomerLogin(new CustomerUI(scanner, canteen, null));
            case 2 -> loginManager.handleManagerLogin(new ManagerUI(scanner, canteen));
            case 3 -> {
                System.out.println("Exiting application...");
                appRunning = false;
            }
            default -> System.out.println("Invalid choice.");
        }
    }

    public static void main(String[] args) {
        UserInterface ui = new UserInterface();
        ui.start();
    }
}

