public class Test {
    private CanteenInventory canteen;
    private Customer customer;
    private Manager manager;
    private ClearConsole clearConsole;
    private int testsPassed = 0;
    private int totalTests = 0;

    public static void main(String[] args) {
        Test tests = new Test();
        tests.runAllTests();
    }

    private void setUp() {
        canteen = new CanteenInventory();
        // Initialize test data
        customer = new Customer("Test User", 20, "TEST123", Gender.MALE, 100, canteen);
        manager = new Manager("Test Manager", 35, Gender.MALE, "test_manager", "test123", canteen);
        
        // Add test items
        canteen.addItem("burger", 10, ItemType.FOOD, 5.99, 8);
        canteen.addItem("cola", 20, ItemType.BEVERAGE, 1.99, 1);
    }

    private void runAllTests() {
        // Run all test methods
        testInventoryManagement();
        testCustomerOperations();
        testManagerAuthentication(); 
        testItemAvailability();
        testStockManagement();
        testCustomerRegistration();
        testInvalidPurchase();
        testCustomerBasket();
        clearConsole.clearConsole();
        // Print test results
        System.out.println("\n=== Test Results ===");
        System.out.println("Tests passed: " + testsPassed + "/" + totalTests);
        System.out.println("Success rate: " + (testsPassed * 100.0 / totalTests) + "%");
    }

    private void testInventoryManagement() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            canteen.addItem("pizza", 5, ItemType.FOOD, 8.99, 15);
            assert canteen.hasItem("pizza") : "Item should exist after adding";
            assert canteen.getStock("pizza") == 5 : "Initial stock should be 5";

            canteen.updateStock("pizza", 3);
            assert canteen.getStock("pizza") == 8 : "Stock should be 8 after update";

            canteen.setPrice("pizza", 9.99);
            assert Math.abs(canteen.getPrice("pizza") - 9.99) < 0.01 : "Price should be updated to 9.99";
            
            System.out.println("++ Inventory management test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Inventory management test failed: " + e.getMessage());
        }
    }

    private void testCustomerOperations() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            assert customer.getBudget() == 100 : "Initial budget should be 100";

            canteen.sellItem("burger", 2);
            customer.addToReceipt("burger", 2, 11.98);
            assert customer.getReceipt() == 11.98 : ("Receipt should be 11.98");
            customer.setBudget(customer.getBudget() - customer.getReceipt());
            assert customer.getBudget() == 88.02 : ("Budget should be 88.02 after purchase");

            System.out.println("++ Customer operations test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Customer operations test failed: " + e.getMessage());
        }
    }

    private void testManagerAuthentication() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            assert canteen.isValidManager("test_manager", "test123") : "Valid manager should be authenticated";
            assert !canteen.isValidManager("wrong_user", "wrong_pass") : "Invalid manager should not be authenticated";
            
            System.out.println("++ Manager authentication test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Manager authentication test failed: " + e.getMessage());
        }
    }

    private void testItemAvailability() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            assert canteen.hasItem("burger") : "Burger should be available";
            assert !canteen.hasItem("nonexistent_item") : "Nonexistent item should not be available";
            
            System.out.println("++ Item availability test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Item availability test failed: " + e.getMessage());
        }
    }

    private void testStockManagement() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            assert canteen.getStock("burger") == 10 : "Initial burger stock should be 10";

            canteen.sellItem("burger", 3);
            assert canteen.getStock("burger") == 7 : ("Stock should be 7 after selling 3");

            canteen.updateStock("burger", 5);
            assert canteen.getStock("burger") == 12 : ("Stock should be 12 after adding 5");
            
            System.out.println("++ Stock management test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Stock management test failed: " + e.getMessage());
        }
    }

    private void testCustomerRegistration() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            String testId = "TEST456";
            Customer newCustomer = new Customer("New User", 22, testId, Gender.FEMALE, 150, canteen);
            assert canteen.hasCustomer(testId) : "Customer should be registered";
            assert canteen.getCustomer(testId).getName().equals("New User") : "Customer name should match";
            
            System.out.println("++ Customer registration test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Customer registration test failed: " + e.getMessage());
        }
    }

    private void testInvalidPurchase() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            int initialStock = canteen.getStock("burger");
            
            // Test purchasing more than available stock
            canteen.sellItem("burger", initialStock + 1);
            assert canteen.getStock("burger") == initialStock : "Stock should not change when attempting to purchase more than available";
            
            // Test purchasing with zero quantity
            canteen.sellItem("burger", 0);
            assert canteen.getStock("burger") == initialStock : "Stock should not change when attempting to purchase zero items";
            
            // Test purchasing nonexistent item
            canteen.sellItem("nonexistent_item", 1);
            assert canteen.getStock("burger") == initialStock : "Stock should not change when attempting to purchase nonexistent item";
            
            System.out.println("++ Invalid purchase test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Invalid purchase test failed: " + e.getMessage());
        }
    }


    private void testCustomerBasket() {
        setUp(); // Reset state before test
        try {
            totalTests++;
            customer.clearReceipt();
            assert customer.getReceipt() == 0 : ("Initial basket should be empty, got " + customer.getReceipt());

            canteen.sellItem("burger", 1);
            customer.addToReceipt("burger", 1, 5.99);
            assert Math.abs(customer.getReceipt() - 5.99) < 0.01 : ("Receipt should be 5.99, got " + customer.getReceipt());

            customer.clearReceipt();
            assert customer.getReceipt() == 0 : ("Basket should be empty after clearing, got " + customer.getReceipt()) ;
            
            System.out.println("++ Customer basket test passed");
            testsPassed++;
        } catch (AssertionError e) {
            System.out.println("-- Customer basket test failed: " + e.getMessage());
        }
    }
}