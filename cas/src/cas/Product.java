package cas; // Package declaration

//Abstract class representing a product
public abstract class Product {
    // Private instance variables to store product information
	private int barcode;
	private String brand;
	private String color;
	private ConnectivityType connectivity;
	private int quantityInStock;
	private double originalCost; //Not visible to user
	private double retailPrice;
	private ProductCategory category;
	
    // Constructor to initialize a Product object with provided parameters
	public Product(int barcde, String brnd, String colr, ConnectivityType connect, int quant, double original, double retail, ProductCategory category){
        // Initializing instance variables with provided values
		this.barcode = barcde;
		this.brand = brnd;
		this.color = colr;
		this.connectivity = connect;
		this.quantityInStock = quant;
		this.originalCost = original;
		this.retailPrice = retail;
		this.category = category;
	}
	
    // Getter methods to retrieve product attributes
	public int getBarcode() {
		return this.barcode;
	}
	public String getBrand() {
		return this.brand;
	}
	public String getColor() {
		return this.color;
	}
	public ConnectivityType getConnectivity() {
		return this.connectivity;
	}
	
	public int getQuantityInStock() {
		return this.quantityInStock;
	}
	
    // Setter method to set the quantity in stock
	public void setQuantityInStock(int quantityInStock){
		this.quantityInStock = quantityInStock;
	}

    // Getter method to retrieve the original cost (not visible to user)
    public double getOriginalCost() {
        return this.originalCost;
    }
    
    // Getter method to retrieve the retail price
    public double getRetailPrice() {
        return this.retailPrice;
	}
    
    // Getter method to retrieve the product category
    public ProductCategory getCategory() {
    	return this.category;
    }
    
    // Override toString() method to return a string representation of the Product object
    @Override
    public String toString() {
        return "Product{" + "barcode=" + barcode +", brand='" + brand + '\'' + ", color='" + color + '\'' +
                ", connectivity=" + connectivity + ", quantityInStock=" + quantityInStock +
                ", originalCost=" + originalCost + ", retailPrice=" + retailPrice + ", category=" + category +
                '}';
    }
}

