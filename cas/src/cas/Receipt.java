package cas; // Package declaration

import java.util.Date; // Importing Date class from java.util package

// Class representing a receipt
public class Receipt {
    // Private instance variables to store receipt information
    private Date TodayDate; // Date when the receipt was generated
    private double amountPaid; // Amount paid in the transaction
    private Address address; // Address associated with the transaction

    // Constructor to initialize a Receipt object with provided parameters
    public Receipt(Date TodayDate, double amountPaid, Address address) {
        // Initializing instance variables with provided values
        this.TodayDate = TodayDate;
        this.amountPaid = amountPaid;
        this.address = address;
    }

    // Getters to retrieve receipt attributes
    public Date getTodayDate() {
        return TodayDate;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public Address getAddress() {
        return address;
    }
}

