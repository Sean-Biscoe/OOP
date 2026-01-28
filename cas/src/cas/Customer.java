package cas; // Package declaration

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Customer extends User {
    
    

    public Customer(int userID, Usernames username, String name, String houseNumber, String postcode, String city) {
        super(userID, username, name, houseNumber, postcode, city, "customer");
        
    }

    // Method to view all available products from a file and return them as a list
    // sorted ascending by retail price
    public List<Product> viewAllProducts() {
	    List<Product> products = new ArrayList<>(); // Initializing a list to hold products
	    try (BufferedReader reader = new BufferedReader(new FileReader("stock.txt"))) {
	    	// Reading the stock file line by line
	        String line;
	        while ((line = reader.readLine()) != null) {
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
	       
	                // Check if it's a Keyboard or Mouse based on category
	                if (category == ProductCategory.KEYBOARD) {
	                    KeyboardType keyboardType = KeyboardType.valueOf(fields[8].toUpperCase()); // Parsing keyboard type from the ninth field
	                    KeyboardLayout layout = KeyboardLayout.valueOf(fields[9]); // Parsing keyboard layout from the tenth field
	                    // Creating a new Keyboard object and adding it to the products list
	                    Keyboard keyboard = new Keyboard(barcode, brand, color, connectivity, quantityInStock, retailPrice, originalCost, keyboardType, layout);
	                    products.add(keyboard);
	                } else if (category == ProductCategory.MOUSE) {
	                    MouseType mouseType = MouseType.valueOf(fields[8].toUpperCase()); // Parsing mouse type from the ninth field
	                    int numberOfButtons = Integer.parseInt(fields[9]); // Parsing number of buttons from the tenth field
	                    // Creating a new Mouse object and adding it to the products list
	                    Mouse mouse = new Mouse(barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, mouseType, numberOfButtons);
	                    products.add(mouse);
	                }
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace(); // Printing the stack trace if an exception occurs during file reading
	    }
	    return products; // Returning the list of products
	
    }

}