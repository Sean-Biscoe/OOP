package cas; // Package declaration

import java.util.ArrayList; // Importing necessary Java utility classes and interfaces
import java.util.List;
import java.util.HashMap;
import java.util.Collections;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

//Admin class extending User class
public class Admin extends User {
	 // Constructor for Admin class
	 public Admin(int userID, Usernames username, String name, String houseNumber, String postcode, String city) {
		 // Calling superclass constructor (User) to initialize admin's attributes
		 super(userID, username, name, houseNumber, postcode, city, "admin");
	 }
	 
	 // Method to view all products from a file and return them as a list
	 public List<Product> viewAllProducts() {
		    List<Product> products = new ArrayList<>(); // Initializing a list to hold products
		    try (BufferedReader reader = new BufferedReader(new FileReader("stock.txt"))) {
		        String line;
		        while ((line = reader.readLine()) != null) { // Reading the stock file line by line
		            String[] fields = line.split(", "); // Splitting each line into fields using comma and space as delimiter
		            if (fields.length >= 9) { // Checking if there are enough fields to represent a product
		                int barcode = Integer.parseInt(fields[0]); // Parsing barcode from the first field
		                ProductCategory category = ProductCategory.valueOf(fields[1].toUpperCase()); // Parsing product category from the second field
		                String brand = fields[2]; // Getting brand from the third field
		                String color = fields[3]; // Getting color from the fourth field
		                ConnectivityType connectivity = ConnectivityType.valueOf(fields[4].toUpperCase()); // Parsing connectivity type from the fifth field
		                int quantityInStock = Integer.parseInt(fields[5]); // Parsing quantity in stock from the sixth field
		                double originalCost = Double.parseDouble(fields[6]); // Parsing original cost from the seventh field
		                double retailPrice = Double.parseDouble(fields[7]); // Parsing retail price from the eighth field
		       
		                // Check if it's a Keyboard or Mouse based on the category
		                if (category == ProductCategory.KEYBOARD) {
		                    KeyboardType keyboardType = KeyboardType.valueOf(fields[8].toUpperCase()); // Parsing keyboard type from the ninth field
		                    KeyboardLayout layout = KeyboardLayout.valueOf(fields[9]); // Parsing keyboard layout from the tenth field
		                    // Creating a new Keyboard object and adding it to the products list
		                    Keyboard keyboard = new Keyboard(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, keyboardType, layout);
		                    products.add(keyboard);
		                } else if (category == ProductCategory.MOUSE) {
		                    MouseType mouseType = MouseType.valueOf(fields[8].toUpperCase()); // Parsing mouse type from the ninth field
		                    int numberOfButtons = Integer.parseInt(fields[9]); // Parsing number of buttons from the tenth field
		                    // Creating a new Mouse object and adding it to the products list
		                    Mouse mouse = new Mouse(barcode, brand, color, connectivity, quantityInStock, retailPrice, originalCost, mouseType, numberOfButtons);
		                    products.add(mouse);
		                }
		            }
		        }
		    } catch (IOException e) {
		        e.printStackTrace(); // Printing the stack trace if an exception occurs during file reading
		    }
		    return products; // Returning the list of products
		}


	// Method to add a mouse product to the existing product list (stock)
	 public void addProductToStock(List<Product> stock, int barcode, String brand, String color,
             ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice,
              MouseType mouseType, int numberOfButtons) {
		 // Check if the product already exists in the stock
		 	if (isProductInStock(stock, barcode)) {
		 		System.out.println("Product with the same barcode already exists in the stock.");
		 		return;
	}
	
	// Create a new mouse product and add it to the stock
		Mouse newMouse = new Mouse(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice,
				mouseType, numberOfButtons);
	    stock.add(newMouse);
	    saveStockToFile(stock); // Save the updated stock to file
	}
	
	// Method to add a keyboard product to the existing product list (stock)
	public void addProductToStock(List<Product> stock, int barcode, String brand, String color,
	             ConnectivityType connectivity, int quantityInStock, double originalCost, double retailPrice,
	             KeyboardType keyboardType, KeyboardLayout layout) {
			// Check if the product already exists in the stock
			if (isProductInStock(stock, barcode)) {
				
				return;
		}
	
		// Create a new keyboard product and add it to the stock
		Keyboard newKeyboard = new Keyboard(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice,
			 keyboardType, layout);
		stock.add(newKeyboard);
		saveStockToFile(stock); // Save the updated stock to file
	}
	
	// Method to check if a product already exists in the stock
	private boolean isProductInStock(List<Product> stock, int barcode) {
		// Check if the product already exists in the stock
		for (Product existingProduct : stock) {
			if (existingProduct.getBarcode() == barcode) {
				return true;
			}
		}
		return false;
	}

	// Method to save the updated stock to a file
	private void saveStockToFile(List<Product> stock) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("stock.txt", false))) {
	        for (Product product : stock) {
	            writer.write(productToString(product)); // Write each product to the file
	            writer.newLine();
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); // Printing the stack trace if an exception occurs during file writing
	    }
	}

	// Method to convert a product object to a string for writing to file
	 private String productToString(Product product) {
		    // Convert the product object to a string representation for writing to file
		    if (product instanceof Mouse) {
		        Mouse mouse = (Mouse) product;
		        return String.format("%d, %s, %s, %s, %s, %d, %.2f, %.2f, %s, %d", product.getBarcode(), product.getCategory(),
		                product.getBrand(), product.getColor(), product.getConnectivity(), product.getQuantityInStock(),
		                product.getOriginalCost(), product.getRetailPrice(), mouse.getType(), mouse.getNumberOfButtons());
		    } else if (product instanceof Keyboard) {
		        Keyboard keyboard = (Keyboard) product;
		        return String.format("%d, %s, %s, %s, %s, %d, %.2f, %.2f, %s, %s", product.getBarcode(), product.getCategory(),
		                product.getBrand(), product.getColor(), product.getConnectivity(), product.getQuantityInStock(),
		                product.getOriginalCost(), product.getRetailPrice(), keyboard.getType(), keyboard.getLayout());
		    } else {
		        return ""; // Handle other types of products if necessary
		    }
		}
}