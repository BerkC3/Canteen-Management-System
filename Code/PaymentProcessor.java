import java.util.Scanner;

public class PaymentProcessor {
    private final Scanner scanner;
    private final CanteenInventory canteen;
    private final Customer customer;
    private final MenuManager menuManager;

    public PaymentProcessor(Scanner scanner, CanteenInventory canteen, Customer customer) {
        this.scanner = scanner;
        this.canteen = canteen;
        this.customer = customer;
        this.menuManager = new MenuManager();
    }

    public void processPayment() {
        menuManager.displayPaymentOptions();
        int paymentChoice = scanner.nextInt();
        
        if (paymentChoice == 4) {
            cancelTransaction();
            return;
        }

        PaymentMethod paymentMethod = getPaymentMethod(paymentChoice);
        processPaymentTransaction(paymentMethod);
    }

    private void cancelTransaction() {
        canteen.rollbackTransaction();
        customer.getOrderedItems().clear();
        customer.clearReceipt();
        customer.getOrderedItems().clear();
        System.out.println("Transaction cancelled. Stock quantities reverted.");
    }

    private PaymentMethod getPaymentMethod(int choice) {
        return switch (choice) {
            case 1 -> PaymentMethod.CREDIT_CARD;
            case 2 -> PaymentMethod.DEBIT_CARD;
            case 3 -> PaymentMethod.CASH;
            default -> PaymentMethod.CASH;
        };
    }

    private void processPaymentTransaction(PaymentMethod method) {
        if(method == PaymentMethod.CASH) {
            if (customer.getBudget() >= customer.getReceipt()) {
                completeTransaction();
            } else {
                System.out.println("Insufficient payment amount.");
            }
        } else {
            System.out.println("Enter a card number: ");
            scanner.nextLine(); // Clear the buffer
            String cardNumber = scanner.nextLine();
            if (cardNumber.length() == 16) {    
                completeTransaction();
            } else {
                System.out.println("Invalid card number.");
                cancelTransaction();
            }
        }
    }

    private void completeTransaction() {
        customer.setBudget(customer.getBudget() - (int)customer.getReceipt());
        canteen.confirmTransaction();
        customer.clearReceipt();
        customer.getOrderedItems().clear();
        System.out.println("Payment successful!");
    }
}