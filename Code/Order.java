import java.util.Map;



public class Order {
    private OrderStatus status;
    private Customer customer;
    private double totalPrice;
    private Map<String, Integer> orderItems;
    private PaymentMethod paymentMethod;

}
