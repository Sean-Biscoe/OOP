package cas; // Package declaration

//Class representing a mouse, extending the Product class
public class Mouse extends Product {
	// Private instance variables to store mouse type and number of buttons
    private MouseType type;
    private int numberOfButtons;

    // Constructor to initialize a Mouse object with provided parameters
    public Mouse(int barcode, String brand, String color, ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice, MouseType type, int numberOfButtons) {
    	// Calling superclass constructor (Product) to initialize mouse attributes
    	super(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, ProductCategory.MOUSE);
    	// Setting mouse type and number of buttons using setter methods to ensure encapsulation
    	this.setType(type);
        this.setNumberOfButtons(numberOfButtons);
    }

    // Getter method to retrieve the mouse type
	public MouseType getType() {
		return type;
	}

    // Setter method to set the mouse type
	public void setType(MouseType type) {
		this.type = type;
	}

    // Getter method to retrieve the number of buttons
	public int getNumberOfButtons() {
		return numberOfButtons;
	}

    // Setter method to set the number of buttons
	public void setNumberOfButtons(int numberOfButtons) {
		this.numberOfButtons = numberOfButtons;
	}
	
	// Override toString() method to return a string representation of the Mouse object
	@Override
    public String toString() {
		// Returning a string concatenation of mouse attributes
        return getBarcode() +", " + getCategory() + ", " +getBrand() + ", " + getColor()  +
                ", " + getConnectivity() + ", " + getQuantityInStock() +
                ", " + getOriginalCost() + ", " + getOriginalCost() +
                ", " + getType() + ", " + getNumberOfButtons();

	}
}  