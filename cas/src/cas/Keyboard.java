package cas; // Package declaration

//Class representing a keyboard, extending the Product class
public class Keyboard extends Product {
    // Private instance variables to store keyboard type and layout
    private KeyboardType type;
    private KeyboardLayout layout;

    // Constructor to initialize a Keyboard object with provided parameters
    public Keyboard(int barcode, String brand, String color, ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice, KeyboardType type, KeyboardLayout layout) {
        // Calling superclass constructor (Product) to initialize keyboard attributes
    	super(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, ProductCategory.KEYBOARD);
        // Setting keyboard type and layout using setter methods to ensure encapsulation
    	this.setType(type);
        this.setLayout(layout);
    }

    // Getter method to retrieve the keyboard type
	public KeyboardType getType() {
		return type;
	}

	// Setter method to set the keyboard type
	public void setType(KeyboardType type) {
		this.type = type;
	}

    // Getter method to retrieve the Keyboard layout
	public KeyboardLayout getLayout() {
		return layout;
	}

	// Setter method to set the keyboard layout	
	public void setLayout(KeyboardLayout layout) {
		this.layout = layout;
	}
	
    // Override toString() method to return a string representation of the Keyboard object
	@Override
    public String toString() {
        // Returning a string concatenation of keyboard attributes
        return getBarcode() +", " + getCategory() + ", " +getBrand() + ", " + getColor()  +
                ", " + getConnectivity() + ", " + getQuantityInStock() +
                ", " + getOriginalCost() + ", " + getOriginalCost() +
                ", " + getType() + ", " + getLayout();
	}
}