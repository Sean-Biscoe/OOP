package cas; // Package declaration

//Class representing a credit card payment method, implementing the PaymentMethod interface
public class CreditCardPayment implements PaymentMethod {
    // Private instance variables to store card number and security code
    private int cardNumber;
    private int securityCode;

    // Constructor to initialize a CreditCardPayment object with card number and security code
    public CreditCardPayment(int cardNumber, int securityCode) throws IllegalArgumentException {
        // Setting cardNumber and securityCode using setter methods to ensure encapsulation
        this.setCardNumber(cardNumber);
        this.setSecurityCode(securityCode);
    }

    // Method to process a payment with the given amount
    @Override
    public boolean processPayment(double amount) throws IllegalStateException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        // Additional validation checks can be added here
        // Simulating payment processing by returning true
       
        return true;
    }
    
    // Method to check if a given card number is valid
    public static boolean isValidCardNumber(int cardNumber) {
        try {
            Integer.parseInt(String.valueOf(cardNumber));
            // Perform validation logic for credit card number
            // For example, check length and range
            return String.valueOf(cardNumber).length() == 6 && cardNumber > 0 && cardNumber < 1000000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Method to check if a given security code is valid
    public static boolean isValidSecurityCode(int securityCode) {
        try {
            Integer.parseInt(String.valueOf(securityCode));
            // Perform validation logic for security code
            // For example, check length and range
            return String.valueOf(securityCode).length() == 3 && securityCode > 0 && securityCode < 1000;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    // Override toString() method to return a string representation of the CreditCardPayment object
    @Override
    public String toString() {
        return "Credit Card Payment";
    }

    // Getter method to retrieve the card number
	public int getCardNumber() {
		return cardNumber;
	}

	// Setter method to set the card number
	public void setCardNumber(int cardNumber) {
		this.cardNumber = cardNumber;
	}

	// Getter method to retrieve the security code
	public int getSecurityCode() {
		return securityCode;
	}

	// Setter method to set the security code
	public void setSecurityCode(int securityCode) {
		this.securityCode = securityCode;
	}

    // Method to process a payment with the given amount and address (not implemented yet)
	@Override
	public Receipt processPayment(double amount, Address fullAddress) {
		return null;
	}
}