package cas; // Package declaration

//Class representing a PayPal payment method, implementing the PaymentMethod interface
public class PayPalPayment implements PaymentMethod {
    // Private instance variable to store the email address associated with the PayPal account
    private String emailAddress;
    
    // Constructor to initialize a PayPalPayment object with the provided email address
    public PayPalPayment(String emailAddress) {
        // Setting the email address using a setter method to ensure encapsulation
        this.setEmailAddress(emailAddress);
    }

    // Method to process a PayPal payment with the given amount
    @Override
    public boolean processPayment(double amount) {
        // Check if the amount is valid (greater than zero)
        if (amount <= 0) {
            throw new IllegalArgumentException("Invalid amount: " + amount);
        }
        // Simulating payment processing by returning true
        
        return true;
    }

    // Method to validate an email address
    public static boolean isValidEmail(String emailAddress) {
        // Basic email format validation
        // You can enhance this with more sophisticated validation if needed
        return emailAddress.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");
    }

    // Override toString() method to return a string representation of the PayPalPayment object
    @Override
    public String toString() {
        return "PayPal Payment";
    }

    // Method to process a PayPal payment with the given amount and address (not implemented yet)
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		return null;
	}

    // Getter method to retrieve the email address
	public String getEmailAddress() {
		return emailAddress;
	}

    // Setter method to set the email address
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
}