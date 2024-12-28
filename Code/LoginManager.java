import java.util.Scanner;
public class LoginManager {
    private final Scanner scanner;
    private final CanteenInventory canteen;

    public LoginManager(Scanner scanner, CanteenInventory canteen) {
        this.scanner = scanner;
        this.canteen = canteen;
    }

    public void handleCustomerLogin(CustomerUI customerUI) {
        System.out.print("Enter your school ID: ");
        String schoolId = scanner.nextLine();
        
        Customer customer = canteen.hasCustomer(schoolId) ? 
            handleExistingCustomer(schoolId) : 
            handleNewCustomer(schoolId);
            
        new CustomerUI(scanner, canteen, customer).handleUserInput();
    }

    private Customer handleExistingCustomer(String schoolId) {
        Customer customer = canteen.getCustomer(schoolId);
        System.out.println("Welcome back, " + customer.getName() + "!");
        return customer;
    }

    private Customer handleNewCustomer(String schoolId) {
        Customer newCustomer = registerNewCustomer(schoolId);
        System.out.println("Registration successful!");
        return newCustomer;
    }

    private Customer registerNewCustomer(String schoolId) {
        System.out.println("Customer not found. Please register:");
        
        String name = getCustomerName();
        int age = getCustomerAge();
        Gender gender = getCustomerGender();
        int budget = getCustomerBudget();
        
        return new Customer(name, age, schoolId, gender, budget, canteen);
    }

    private String getCustomerName() {
        System.out.print("Enter name: ");
        return scanner.nextLine();
    }

    private int getCustomerAge() {
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        return age;
    }

    private Gender getCustomerGender() {
        System.out.println("Select gender (1:MALE, 2:FEMALE): ");
        int genderChoice = scanner.nextInt();
        scanner.nextLine();
        return (genderChoice == 1) ? Gender.MALE : Gender.FEMALE;
    }

    private int getCustomerBudget() {
        System.out.print("Enter budget: "); 
        int budget = scanner.nextInt();
        scanner.nextLine();
        return budget;
    }

    public void handleManagerLogin(ManagerUI managerUI) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        
        if (canteen.isValidManager(username, password)) {
            new ManagerUI(scanner, canteen).handleUserInput();
        } else {
            System.out.println("Invalid credentials. Access denied.");
        }
    }
}