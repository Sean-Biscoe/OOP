package cas; // Package declaration

// Interface representing a payment method
public interface PaymentMethod {
    // Method to process a payment with the given amount and address, returning a receipt
    public abstract Receipt processPayment(double amount, Address fullAddress);


    // Method to process a payment with the given amount, returning a boolean indicating success or failure
    boolean processPayment(double amount) throws IllegalStateException;
}
