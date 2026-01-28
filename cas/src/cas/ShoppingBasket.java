package cas; // Package declaration

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Class representing a shopping basket
public class ShoppingBasket {
    private HashMap<Product, Integer> items; // Using Product as key and quantity as value
    
    // Constructor to initialize a ShoppingBasket object with items and associated customer
    public ShoppingBasket(HashMap<Product, Integer> items, Customer user) {
    	this.items = items;
		  
	}
    
    // Getter for items
    public HashMap<Product, Integer> getItems() {
        return items;
    }
    

    // Setter for items
    public void setItems(HashMap<Product, Integer> items) {
        this.items = items;
    }

    // Method to add a mouse item to the shopping basket
	public void addItem(Mouse item, Customer customer, int quantity) {
    	
        List<Product> products = customer.viewAllProducts();
    	
        if (products.contains(item)) {
            int initial_quantity = items.get(item);
            items.put(item, initial_quantity + quantity);
        } else {
            items.put(item, quantity);
        }
    }
    
	// Method to add a keyboard item to the shopping basket
    public void addItem(Keyboard item, Customer customer, int quantity) {
    	List<Product> products = customer.viewAllProducts();
    	
        if (products.contains(item)) {
            int initial_quantity = items.get(item);
            items.put(item, initial_quantity + quantity);
        } else {
            items.put(item, quantity);
        }
    }

    // Method to remove an item from the shopping basket
    public void removeItem(Mouse item) {
        items.remove(item);
    }
    
    // Method to remove an item from the shopping basket
    public void removeItem(Keyboard item) {
        items.remove(item);
    }
    

    // Method to clear all items from the shopping basket
    public void clearBasket() {
        items.clear();
    }

    // Method to get the total price of all items in the shopping basket
    public double getTotalPrice() {
        double totalPrice = 0.0;
        for (HashMap.Entry<Product, Integer> entry : items.entrySet()) {
            totalPrice += entry.getKey().getRetailPrice() * entry.getValue(); // Multiply price by quantity
        }
        return totalPrice;
    }
    
    // Method to update the quantity of a product in stock after changing the quantity in the shopping basket
    public List<Product> changeQuantity(List<Product> stock, int barcode, int quantity) {
    
         for (Product product : stock ) {
                if (product.getBarcode() == barcode) {
                    // Update the quantity in the basket
                    int newQuantity = product.getQuantityInStock() - quantity;
                    
                    product.setQuantityInStock(newQuantity);
                    // remove old product from stock list
                    int index = stock.indexOf(product);
                    stock.remove(index);
                    // add updated product to stock list
                    stock.add(index, product);
                    
                  

                    // Update the quantity in the stock file
                    updateStockFile(stock);
                   
                    return stock; // Exit the method once quantity is updated
                }
            }
            // If the product with the specified barcode is not found
            
			return stock;
		
        }
   
    // Method to update the stock file with the updated stock list
    public static void updateStockFile(List<Product> stock) {
        File stockFile = new File("Stock.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(stockFile))) {
            // Iterate over the list of products
            for (Product product : stock) {
            	/*if (product.getCategory() == ProductCategory.KEYBOARD) {
            		Keyb
            		writer.write(Keyboard.)
            	}
            	else {
            		
            	}
            		*/
                // Write product information to the file
                writer.write(product.toString()); // Assuming toString() method of Product class formats the product information properly
                writer.newLine(); // Add a new line after each product
            }
            
        } catch (IOException e) {
     
        }
    
    }
    
    // Method to add quantity of a product to stock
    public List<Product> addQuantity(List<Product> stock, int barcode, int quantity) {
        
        for (Product product : stock ) {
               if (product.getBarcode() == barcode) {
                   // Update the quantity in the basket
                   int newQuantity = product.getQuantityInStock() + quantity;
                   
                   product.setQuantityInStock(newQuantity);
                   // remove old product from stock list
                   int index = stock.indexOf(product);
                   stock.remove(index);
                   // add updated product to stock list
                   stock.add(index, product);
     
                   

                   // Update the quantity in the stock file
                   updateStockFile(stock);
                  
                   return stock; // Exit the method once quantity is updated
               }
           }
           // If the product with the specified barcode is not found
        
			return stock;
		
       }
    
    
    // Method to display items in the shopping basket
    public List<String> displayItems() {
    	List<String> itemList = new ArrayList<>();
        for (HashMap.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            
            //return "\n"+ "barcode = " + product.getBarcode() +  ", category = " + product.getCategory() +", brand = '" + product.getBrand() + '\'' + ", color = '" + product.getColor() + '\'' +
             //       ", connectivity = " + product.getConnectivity() + ", quantity = " + quantity +
             //        ", retailPrice = " + product.getRetailPrice() + "\n\n";
            String itemString = "barcode = " + product.getBarcode() +  ", category = " + product.getCategory() +
                    ", brand = '" + product.getBrand() + '\'' + ", color = '" + product.getColor() + '\'' +
                    ", connectivity = " + product.getConnectivity() + ", quantity = " + quantity +
                    ", retailPrice = " + product.getRetailPrice() + "\n";

            itemList.add(itemString);
        
        }
        return itemList;
		//return null;
    }
    
    
}