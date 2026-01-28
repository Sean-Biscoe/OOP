package cas; // Package declaration

import javax.swing.*;
import java.util.Date;

import javax.swing.table.DefaultTableModel;

//import cas.ShoppingBasket.ProductQuantityPair;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

//Definition of the UserSelectionGUI class, which extends the JFrame class
public class UserSelectionGUI extends JFrame {
    
	// Declaration of instance variables
	private JComboBox<String> userComboBox; // ComboBox for selecting users, holding String items
    private final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP); // Tabbed pane for organizing content

    // Constructor for the UserSelectionGUI class
    public UserSelectionGUI() {
    	// Constructor body will be defined elsewhere in the code
        setTitle("User Selection"); 	// Setting the title of the JFrame to "User Selection"
        setSize(1000, 600); 	// Setting the size of the JFrame to 1000 pixels wide and 600 pixels tall
        setDefaultCloseOperation(EXIT_ON_CLOSE); 	// Setting the default close operation for the JFrame to EXIT_ON_CLOSE
        setLocationRelativeTo(null); 	// Setting the location of the JFrame to be centered on the screen

        // Create JComboBox
        userComboBox = new JComboBox<>();
        userComboBox.setPreferredSize(new Dimension(200, 30));

        // Read users from file and populate JComboBox
        List<User> users = getUsersFromFile("UserAccounts.txt");
        for (User user : users) {
            userComboBox.addItem(user.getName());
        }

        // Add JComboBox to content pane
        getContentPane().add(userComboBox, BorderLayout.NORTH); // Adding JComboBox to the content pane at the BorderLayout.NORTH position

        JButton btnNewButton = new JButton("Select User"); // Creating a new JButton with the text "Select User"
        
     // Adding an ActionListener to the button using lambda expression
        btnNewButton.addActionListener(e -> {
            String selectedUserName = (String) userComboBox.getSelectedItem(); // Retrieving the selected item (user name) from the JComboBox
            User selectedUser = findUserByName(selectedUserName, users); // Finding the User object corresponding to the selected user name
            
            if (selectedUser != null) {  // Checking if a valid User object is found
                openTabsForUser(selectedUser); // Opening tabs for the selected user
            }
        });
        getContentPane().add(btnNewButton, BorderLayout.SOUTH);   // Adding the JButton to the content pane at the BorderLayout.SOUTH position
        getContentPane().add(tabbedPane, BorderLayout.CENTER);	  // Adding the JTabbedPane to the content pane at the BorderLayout.CENTER position
    }

 // Method to read user data from a file and create a list of User objects
    private List<User> getUsersFromFile(String filename) {
        List<User> users = new ArrayList<>();	// Initialize an empty list to store User objects
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            
            while ((line = reader.readLine()) != null) { // Read each line from the file until the end of the file is reached
                String[] fields = line.split(", ");  	// Split the line by comma and space to extract individual fields
                
                // Check if the line contains exactly 7 fields (assuming each field represents user data)
                if (fields.length == 7) {
                	// Parse each field to create a User object
                    int userID = Integer.parseInt(fields[0]);
                    Usernames username = Usernames.valueOf(fields[1].toUpperCase());
                    String name = fields[2];
                    String houseNumber = fields[3];
                    String postcode = fields[4];
                    String city = fields[5];
                    String role = fields[6];

                    // Create a new User object using the extracted data and add it to the list
                    User user = new User(userID, username, name, houseNumber, postcode, city, role);
                    users.add(user);
                } else {
                	// If the line does not contain exactly 7 fields, print an error message
                    System.err.println("Invalid user account data: " + line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); // Print the stack trace if an IOException occurs during file reading
        }
        return users; // Return the list of User objects
    }
    
    
    

    // Method to open tabs corresponding to the selected user
    private void openTabsForUser(User selectedUser) {
    	
        // Clear existing tabs
        tabbedPane.removeAll();


        // Add tabs based on user role
        if (selectedUser.getRole().equalsIgnoreCase("customer")) {
            JPanel customerPanel = new JPanel();
            customerPanel.add(new JLabel("Customer Panel"));
            tabbedPane.addTab("Customer", customerPanel);
            
            

            // Add additional tabs for customer methods
            JPanel method1Panel = new JPanel();
            method1Panel.add(new JLabel("View all Products"));
            method1Panel.setLayout(new BorderLayout());

            // Create a table to display all products
            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);
            table.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(table);
            method1Panel.add(scrollPane, BorderLayout.CENTER);
            

            // Add column headers
            model.addColumn("Barcode");
            model.addColumn("Category");
            model.addColumn("Brand");
            model.addColumn("Color");
            model.addColumn("Connectivity");
            model.addColumn("Quantity");
            model.addColumn("Retail Price");
            
            // Creating a new Customer instance based on the selectedUser's information
            Customer customerUser = new Customer(selectedUser.getUserID(), selectedUser.getUsername(), selectedUser.getName(), selectedUser.getHouseNumber(), selectedUser.getPostcode(), selectedUser.getCity());
            // Creating an Address instance for the user using their house number, postcode, and city
            Address addressOfUser =  new Address(selectedUser.getHouseNumber(), selectedUser.getPostcode(),selectedUser.getCity());
            
            Date objDate= new Date(); // Creating a Date object to represent the current date and time
            
            HashMap<Product, Integer> products = new HashMap<>(); // Initializing a HashMap to store products and their quantities in the shopping basket
            ShoppingBasket basket = new ShoppingBasket(products, customerUser); // Creating a new ShoppingBasket instance for the customer user, passing the products HashMap and customerUser
            		
            // Populate table with product data
            List<Product> allProducts = customerUser.viewAllProducts(); // Assuming this method exists
            updateTable(allProducts, model);
            // Sort by retail price (ascending order)
            
            JPanel searchPanel = new JPanel();  // Add add to basket button next to search button
            JTextField searchField = new JTextField(10);  // Creating a JTextField for user input to search for a product by barcode
            JButton searchButton = new JButton("Search");   // Creating a JButton to trigger the search operation
            // Adding an ActionListener to the searchButton to perform the search operation when clicked
            searchButton.addActionListener(e -> searchProduct(Integer.parseInt(searchField.getText()),customerUser));
            
            // Adding components to the searchPanel
            searchPanel.add(new JLabel("Search by Barcode:"));
            searchPanel.add(searchField);
            searchPanel.add(searchButton);
            

            // Create a shopping basket text area
            
        
            JLabel basketLabel = new JLabel();
            StringBuilder basketText = new StringBuilder();
            
             // Add drop-down option
            String[] quantityOptions = new String[16];
            for (int i = 0; i < quantityOptions.length; i++) {
                quantityOptions[i] = String.valueOf(i + 1);
            }
            
            JComboBox<String> quantityComboBox = new JComboBox<>(quantityOptions);
            searchPanel.add(quantityComboBox);
            JButton addToBasketButton = new JButton("Add to Basket");
            
            addToBasketButton.addActionListener(e -> {
                int barcode = Integer.parseInt(searchField.getText());
                int quantity = Integer.parseInt((String) quantityComboBox.getSelectedItem());
                List<Product> UpdatedProducts = addProductToBasket(barcode, quantity, customerUser, basket, products);
                updateTable(UpdatedProducts, model);
                updateShoppingTable(basket, basketText,basketLabel);
            });
            searchPanel.add(addToBasketButton);
            
         

            // Add the search panel to the bottom of the method1Panel
            method1Panel.add(searchPanel, BorderLayout.SOUTH);
            
            tabbedPane.addTab("View all products", method1Panel);
            

            JPanel method2Panel = new JPanel();
            method2Panel.add(new JLabel("Shopping Basket"));
            
            
            basketLabel.setName("Shopping Basket");
            
            
            method2Panel.add(new JScrollPane(basketLabel), BorderLayout.CENTER);
            
            method2Panel.add(basketLabel,BorderLayout.CENTER);
            
            
            
            

            // Create buttons for adding and removing products from the basket
            JButton removeButton = new JButton("Remove from Basket");
            removeButton.addActionListener(e -> {
            	
            	updateTable(allProducts, model );
            	removeItem(basket,basketText,basketLabel, customerUser);
            	
            });
            JButton paybutton = new JButton("Pay now");
            
            // Add buttons to a panel
            paybutton.addActionListener(e -> {
            	tabbedPane.setSelectedIndex(3);
            });
            
            
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(removeButton);
            buttonPanel.add(paybutton);
            
            // Add the button panel to the bottom of the customerMethod2Panel
            method2Panel.add(buttonPanel, BorderLayout.SOUTH);
            method2Panel.add(paybutton, BorderLayout.SOUTH);
            
            
            tabbedPane.addTab("Shopping Basket", method2Panel);

            
            
            
            
            JPanel paymentPanel = new JPanel();
            paymentPanel.setLayout(new BorderLayout());

            // Components for entering payment details
            

            // Payment method selection (e.g., credit card, cash, etc.)
            JLabel paymentMethodLabel = new JLabel("Payment Method:");
            JComboBox<String> paymentMethodComboBox = new JComboBox<>(new String[]{"Credit Card", "Pay Pal"});

            // Button to process payment
            JButton processPaymentButton = new JButton("Select payment method");
            processPaymentButton.addActionListener(e -> {
            	String paymentType = (String) paymentMethodComboBox.getSelectedItem();
            	double amount = calculatePrice( basket, customerUser);
            	popUp(paymentType, amount, addressOfUser, objDate, basket, basketText,  basketLabel);
            	
                
            });

            // Add components to the payment panel
            
            paymentPanel.add(paymentMethodLabel, BorderLayout.CENTER);
            paymentPanel.add(paymentMethodComboBox, BorderLayout.CENTER);
            paymentPanel.add(processPaymentButton, BorderLayout.SOUTH);

            // Add the payment panel to the tabbed pane
            tabbedPane.addTab("Payment", paymentPanel);


           
            
        } else if (selectedUser.getRole().equalsIgnoreCase("admin")) {
            JPanel adminPanel = new JPanel();
            adminPanel.add(new JLabel("Admin Panel"));
            tabbedPane.addTab("Admin", adminPanel);

            // Add additional tabs for admin methods
            JPanel adminMethod1Panel = new JPanel();
            adminMethod1Panel.setLayout(new BorderLayout());

            // Create a table to display all products
            DefaultTableModel model = new DefaultTableModel();
            JTable table = new JTable(model);
            table.setEnabled(false);
            JScrollPane scrollPane = new JScrollPane(table);
            adminMethod1Panel.add(scrollPane, BorderLayout.CENTER);

            // Add column headers
            model.addColumn("Barcode");
            model.addColumn("Category");
            model.addColumn("Brand");
            model.addColumn("Color");
            model.addColumn("Connectivity");
            model.addColumn("Quantity");
            model.addColumn("Retail Price");
            model.addColumn("Original Price");
            
            //Creates instance of admin to use in program
            Admin adminUser = new Admin(selectedUser.getUserID(), selectedUser.getUsername(), selectedUser.getName(), selectedUser.getHouseNumber(), selectedUser.getPostcode(), selectedUser.getCity());
            
            // Populate table with product data
            List<Product> allProducts = adminUser.viewAllProducts(); // Assuming this method exists
            
            // Sort by retail price (ascending order)
            Collections.sort(allProducts, Comparator.comparing(Product::getRetailPrice));
            for (Product product : allProducts) {
            	model.addRow(new Object[]{
                        product.getBarcode(),
                        product.getCategory(),
                        product.getBrand(),
                        product.getColor(),
                        product.getConnectivity(),
                        product.getQuantityInStock(),
                        product.getRetailPrice(),
                        product.getOriginalCost(),
                        
                });
            }

            
            //Add View all products to tab
            tabbedPane.addTab("View all Products", adminMethod1Panel);

            // Creating a new JPanel with a GridLayout to organize components in a grid layout
            JPanel adminMethod2Panel = new JPanel(new GridLayout(0, 2));
            
            
            
            // Creating JTextFields for various input fields
            JTextField barcodeField = new JTextField();    // Text field for entering the barcode of the product
            JTextField brandField = new JTextField();		// Text field for entering the brand of the product
            JTextField colorField = new JTextField();		// Text field for entering the colour of the product
            JComboBox<ConnectivityType> connectivityField = new JComboBox<>(ConnectivityType.values()); // ComboBox for selecting connectivity type
            JTextField quantityInStockField = new JTextField();		 // Text field for entering the quantity of the product in stock
            JTextField originalCostField = new JTextField();		// Text field for entering the original cost of the product
            JTextField retailPriceField = new JTextField();			// Text field for entering the retail price of the product
            JComboBox<MouseType> mouseTypeField = new JComboBox<>(MouseType.values());   // ComboBox for selecting mouse type       
            JTextField numberOfButtonsField = new JTextField();		// Text field for entering the number of buttons (for mouse products)
            JComboBox<KeyboardType> keyboardTypeField = new JComboBox<>(KeyboardType.values()); // ComboBox for selecting keyboard type
            JComboBox<KeyboardLayout> layoutField = new JComboBox<>(KeyboardLayout.values());   // ComboBox for selecting keyboard layout
            
            
            
            // Adding JLabels and corresponding input components to the adminMethod2Panel
            adminMethod2Panel.add(new JLabel("Barcode:"));	 			
            adminMethod2Panel.add(barcodeField);			 			
            
            adminMethod2Panel.add(new JLabel("Brand:"));				
            adminMethod2Panel.add(brandField);							
            
            adminMethod2Panel.add(new JLabel("Color:")); 				
            adminMethod2Panel.add(colorField);							
            
            adminMethod2Panel.add(new JLabel("Connectivity:"));			
            adminMethod2Panel.add(connectivityField);					
            
            adminMethod2Panel.add(new JLabel("Quantity in Stock:")); 	
            adminMethod2Panel.add(quantityInStockField);				
            
            adminMethod2Panel.add(new JLabel("Original Cost:"));		
            adminMethod2Panel.add(originalCostField);					
            
            adminMethod2Panel.add(new JLabel("Retail Price:"));			
            adminMethod2Panel.add(retailPriceField);					
            
            adminMethod2Panel.add(new JLabel("Mouse Type:"));			
            adminMethod2Panel.add(mouseTypeField);						
            
            adminMethod2Panel.add(new JLabel("Number of Buttons:"));	
            adminMethod2Panel.add(numberOfButtonsField);				
            
            adminMethod2Panel.add(new JLabel("Keyboard Type:"));		
            adminMethod2Panel.add(keyboardTypeField);					
            
            adminMethod2Panel.add(new JLabel("Layout:"));				
            adminMethod2Panel.add(layoutField);							



            // Button to add a new mouse product
            JButton addMouseProductButton = new JButton("Add Mouse Product");
            addMouseProductButton.addActionListener(e -> {
            	try {
	            		// Parsing input values from the text fields and combo boxes
		                int barcode = Integer.parseInt(barcodeField.getText());
		                String brand = brandField.getText();
		                String color = colorField.getText();
		                ConnectivityType connectivity = (ConnectivityType) connectivityField.getSelectedItem();
		                int quantityInStock = Integer.parseInt(quantityInStockField.getText());
		                double originalCost = Double.parseDouble(originalCostField.getText());
		                double retailPrice = Double.parseDouble(retailPriceField.getText());
		                MouseType mouseType = (MouseType) mouseTypeField.getSelectedItem();
		                int numberOfButtons = Integer.parseInt(numberOfButtonsField.getText());
		                
		                // Calling the method to add a new mouse product to the stock
		                adminUser.addProductToStock(adminUser.viewAllProducts(), barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, mouseType, numberOfButtons);
		               
            	} catch (NumberFormatException ex) {
            		// Displaying an error message if there's an exception while parsing numeric values
                    JOptionPane.showMessageDialog(adminMethod2Panel, "Error: Please enter valid numeric values for Barcode, Quantity in Stock, Original Cost, and Retail Price.");
                }
            	
            });
            
            adminMethod2Panel.add(addMouseProductButton);

            // Button to add a new keyboard product
            JButton addKeyboardProductButton = new JButton("Add Keyboard Product");
            addKeyboardProductButton.addActionListener(e -> {
            	try {
            			// Parsing input values from the text fields and combo boxes
		                int barcode = Integer.parseInt(barcodeField.getText());
		                String brand = brandField.getText();
		                String color = colorField.getText();
		                ConnectivityType connectivity = (ConnectivityType) connectivityField.getSelectedItem();
		                int quantityInStock = Integer.parseInt(quantityInStockField.getText());
		                double originalCost = Double.parseDouble(originalCostField.getText());
		                double retailPrice = Double.parseDouble(retailPriceField.getText());
		                KeyboardType keyboardType = (KeyboardType) keyboardTypeField.getSelectedItem();
		                KeyboardLayout layout = (KeyboardLayout) layoutField.getSelectedItem();
		                
		                // Calling the method to add a new mouse product to the stock
		                adminUser.addProductToStock(adminUser.viewAllProducts(), barcode, brand, color, connectivity, quantityInStock, originalCost, retailPrice, keyboardType, layout);
            	} catch (NumberFormatException ex) {
            		// Displaying an error message if there's an exception while parsing numeric values
                    JOptionPane.showMessageDialog(adminMethod2Panel, "Error: Please enter valid numeric values for Barcode, Quantity in Stock, Original Cost, and Retail Price.");
                }
            });
            //Adds KeyboardProductButton to admin panel
            adminMethod2Panel.add(addKeyboardProductButton);
            tabbedPane.addTab("Add new Product", adminMethod2Panel);
        }
    }
    
    
    
    
    /**
     * Adds a product to the shopping basket based on the given barcode and quantity.
     *
     * @param barcode      The barcode of the product to be added.
     * @param quantity     The quantity of the product to be added.
     * @param customerUser The customer user for whom the product is being added.
     * @param basket       The shopping basket to which the product will be added.
     * @param products     The HashMap containing all available products.
     * @return             A list of all products after the operation.
     */

    private List<Product> addProductToBasket(int barcode, int quantity, Customer customerUser, ShoppingBasket basket, HashMap<Product, Integer> products) {
    	// Retrieve all products available to the customer
    	List<Product> allProducts = customerUser.viewAllProducts();
    	
        Product product = getProductByBarcode(barcode, customerUser);
        

        // Check if the product exists
        if ((product != null) & (checkQuantity(product, quantity))) {
        	 
        	ProductCategory category = product.getCategory();
           
			// Check if the product is a mouse 
            if (category == ProductCategory.MOUSE) {
            	// Cast product to Mouse and add it to the basket
                Mouse mouseProduct = (Mouse) product;
                
                // Update the quantity of the product in the basket and add the item to the basket
                basket.changeQuantity(allProducts, barcode, quantity);
                basket.addItem(mouseProduct, customerUser, quantity);
                
            // Check if the product is a mouse  
            } else if (category == ProductCategory.KEYBOARD) {
            	// Cast product to Keyboard and add it to the basket
                Keyboard keyboardProduct = (Keyboard) product;
                
                
               // Update the quantity of the product in the basket and add the item to the basket
                basket.changeQuantity(allProducts, barcode, quantity);
                basket.addItem(keyboardProduct, customerUser, quantity);
                
                   
                
            } else {
                // Display an error message if the product is neither a mouse nor a keyboard
                JOptionPane.showMessageDialog(this, "Invalid product type", "Error", JOptionPane.ERROR_MESSAGE);
                return allProducts;
            }

            // Display a message indicating success
            JOptionPane.showMessageDialog(this, "Product added to basket:\n" + "barcode=" + product.getBarcode() +", brand='" + product.getBrand()  + ", color=" + product.getColor() + ", connectivity=" + product.getConnectivity() + ", quantityInStock=" + quantity +", originalCost=" + product.getOriginalCost() + ", retailPrice=" + product.getRetailPrice() + ", category=" + category);
        } else {
            // Display an error message if the product does not exist
            JOptionPane.showMessageDialog(this, "Product not found with barcode: " + barcode + " or not enough in stock", "Error", JOptionPane.ERROR_MESSAGE);
        }
        // Return the list of all products after the operation
		return allProducts;
        
        
    }

    
    
    

    /**
     * Retrieves a product from the list of all products based on the given barcode.
     *
     * @param barcode       The barcode of the product to retrieve.
     * @param customerUser  The customer user whose products are being searched.
     * @return              The product with the specified barcode, or null if not found.
     */
	private Product getProductByBarcode(int barcode,Customer customerUser) {
		// Retrieve all products available to the customer
		List<Product> allProducts = customerUser.viewAllProducts();
		
		 // Iterate over each product in the list
		for (Product product : allProducts) {
        	
        		if (product.getBarcode() == barcode) {
        			// Add the found product to the shopping basket table
        			return product; // Exit the method after finding the product
        		}
        	}
		// If the product with the specified barcode is not found, return null
		return null;
    }

	
	
        
	/**
	 * Finds a user from the list of users based on the given username.
	 *
	 * @param selectedUserName  The username of the user to find.
	 * @param users             The list of users to search through.
	 * @return                  The user with the specified username, or null if not found.
	 */

	private User findUserByName(String selectedUserName, List<User> users) {
		// Iterate over each user in the list
        for (User user : users) {
        	// Check if the user's name matches the specified username
            if (user.getName().equals(selectedUserName)) {
            	 // Return the user if found
                return user;
            }
        }
        // If no user with the specified username is found, return null
        return null;
    }
	
	
	
	
	
	/**
	 * Searches for a product based on the given barcode within the customer's available products.
	 * Displays a message dialog with the product information if found.
	 *
	 * @param barcode       The barcode of the product to search for.
	 * @param customerUser  The customer user object containing the list of available products.
	 */
    private void searchProduct(int barcode, Customer customerUser) {
    	// Get the list of all products available to the customer
        List<Product> allProducts = customerUser.viewAllProducts();

        // Search for the product with the specified barcode
        for (Product product : allProducts) {
     
        	if (barcode <100000 || barcode>999999) {
        		JOptionPane.showMessageDialog(this, "Please ensure your barcode is exactly 6 digits long", "BARCODE LENGTH ERROR", JOptionPane.WARNING_MESSAGE);
        		break;
        	}
        	else {
        		if (product.getBarcode() == barcode) {
        			JOptionPane.showMessageDialog(this, product.toString(), "Product Found", JOptionPane.INFORMATION_MESSAGE);
        			// Add the found product to the shopping basket table
        			return; // Exit the method after finding the product
        		}
        	}
        }

        // If the product with the specified barcode is not found
        JOptionPane.showMessageDialog(this, "Product with barcode " + barcode + " not found.", "Product Not Found", JOptionPane.WARNING_MESSAGE);
    }
    
    
    
    
    
    /**
     * Checks if the specified quantity of a product is available in stock.
     *
     * @param product  The product to check the quantity for.
     * @param quantity The quantity to check if available in stock.
     * @return True if the specified quantity is available in stock, otherwise false.
     */
    
    public boolean checkQuantity(Product product, int quantity) { 
    	   // Calculate the new quantity after deducting the specified quantity
           int newQuantity = product.getQuantityInStock() - quantity;
           
           // Check if the new quantity is greater than or equal to zero
           if (newQuantity >= 0) {
             
               return true;
           }
            else {
               return false;
            }
    }
    
    
    

    /**
     * Updates the table with the latest product information.
     *
     * @param allProducts The list of all products to be displayed in the table.
     * @param model       The DefaultTableModel object representing the table model.
     */
    public void updateTable(List<Product> allProducts, DefaultTableModel model ) {
    	// Clear the existing rows in the table model
    	model.setRowCount(0);
  
    	// Sort the products by retail price before adding them to the table
    	Collections.sort(allProducts, Comparator.comparing(Product::getRetailPrice));
    	
    	// Iterate through each product and add its information to the table model
        for (Product product : allProducts) {
        	model.addRow(new Object[]{
                    product.getBarcode(),
                    product.getCategory(),
                    product.getBrand(),
                    product.getColor(),
                    product.getConnectivity(),
                    product.getQuantityInStock(),
                    product.getRetailPrice(),
                    
            });
        }
    }
    
    
    
    
    /**
     * Updates the shopping basket table with the latest product information.
     *
     * @param basket      The ShoppingBasket object representing the user's shopping basket.
     * @param basketText  The StringBuilder object to store the text representation of the shopping basket.
     * @param basketLabel The JLabel object representing the shopping basket display.
     */
    public void updateShoppingTable(ShoppingBasket basket, StringBuilder basketText, JLabel basketLabel) {
    	  
    	List<String> itemLines = basket.displayItems(); // Get the list of product string representations
    	basketText.setLength(0);
        // Append each item line to the StringBuilder with a newline character
        for (String itemLine : itemLines) {
            basketText.append(itemLine).append("\n");

        }
   

        // Set the text of the JLabel with the updated StringBuilder content
        basketLabel.setText(basketText.toString());
    }
    
    
    
    
    /**
     * Removes items from the shopping basket and updates the display accordingly.
     *
     * @param basket       The ShoppingBasket object representing the user's shopping basket.
     * @param basketText   The StringBuilder object to store the text representation of the shopping basket.
     * @param basketLabel  The JLabel object representing the shopping basket display.
     * @param customerUser The Customer object representing the current user.
     */
    public void removeItem(ShoppingBasket basket,StringBuilder basketText, JLabel basketLabel, Customer customerUser) {

    	// Retrieve the list of product string representations from the shopping basket
    	List<String> itemLines = basket.displayItems();
    	for (String item : itemLines) {
    	    // Split the item string by ","
    	    String[] parts = item.split(", ");
    
    	    // Extract the barcode and quantity from the parts array
    	    String barcode = parts[0].split("=")[1].trim(); // Extract barcode and trim whitespace
    	    int barcode1 = Integer.parseUnsignedInt(barcode);
    	    String quantity = parts[5].split("=")[1].trim();// Extract quantity and trim whitespace
    	    int quantity1 = Integer.parseUnsignedInt(quantity);
    	    List<Product> allProducts = customerUser.viewAllProducts();
    	    basket.addQuantity(allProducts, barcode1, quantity1);
    	}
    	
    	 // Clear the StringBuilder and update the basket display label  
    	 basketText.setLength(0);
    	 basketLabel.setText(basketText.toString());
    	 // Clear the shopping basket as all items are removed
    	 basket.clearBasket();
    	
    }
    
    
    
    /**
     * Calculates the total price of all items in the shopping basket.
     *
     * @param basket       The ShoppingBasket object representing the user's shopping basket.
     * @param customerUser The Customer object representing the current user.
     * @return The total price of all items in the shopping basket.
     */
    
    public double calculatePrice(ShoppingBasket basket, Customer customerUser) {
    	// Retrieve the list of product string representations from the shopping basket
    	List<String> itemLines = basket.displayItems();
	
    	// Initialize the total amount variable
		double amount = 0;
    	for (String item : itemLines) {
    	  
    		// Split the item string by ","
    	    String[] parts = item.split(", ");
    	    
    	    
    	    // Extract the price and quantity from the parts array
    	    
    	    String quantity = parts[5].split("=")[1].trim();// Extract quantity and trim whitespace
    	    int quantity1 = Integer.parseUnsignedInt(quantity);
    	    String price = parts[6].split("=")[1].trim();// Extract quantity and trim whitespace
    	    double price1 = Double.parseDouble(price);
    	   
    	    amount += quantity1*price1;
    	    // Print or use the barcode and quantity as needed
    	
    	}
    	// Return the total calculated amount
    	return amount;	
    }
    
    
    
    /**
     * Displays a payment pop-up window based on the selected payment type.
     * Allows users to enter payment details and proceed with payment.
     * Generates a receipt and updates the shopping basket after successful payment.
     *
     * @param paymentType     The selected payment type ("Credit Card" or "PayPal").
     * @param amount          The total amount to be paid.
     * @param addressOfUser   The address of the user making the payment.
     * @param objDate         The current date for the receipt.
     * @param basket          The ShoppingBasket object representing the user's shopping basket.
     * @param basketText      The StringBuilder containing the text representation of the shopping basket.
     * @param basketLabel     The JLabel to display the shopping basket text.
     */
    
    public void popUp(String paymentType, double amount, Address addressOfUser, Date objDate, ShoppingBasket basket, StringBuilder basketText, JLabel basketLabel) {
    	
    	// If payment type is Credit Card
    	if(paymentType == "Credit Card") {
    		
    		
    			JFrame CreditCardFrame = new JFrame(); //Creates Credit Card JFrame
    			//Creates fields for user to type values in
    			JTextField cardNumber = new JTextField();
    			JTextField securityCode = new JTextField();
    			
    			JPanel panel = new JPanel(new GridLayout(0, 1)); // Create a panel with a grid layout
    			
    			//Creates buttons for payment and check details
    			JButton Paybutton = new JButton("Pay");
    			JButton CheckDetails = new JButton("Check Details");
    			
    			// Add labels and input fields for card number and security code to the panel
    			panel.add(new JLabel("The amount you have to pay is: £"+amount));
    			panel.add(new JLabel("Press the check email button first before paying"));
    			panel.add(new JLabel("Enter your credit card number in:"));
    			panel.add(cardNumber);
    			panel.add(new JLabel("Enter your security code in:"));
    			panel.add(securityCode);
    			
    			// Action listener for the "Check Details" button
    			CheckDetails.addActionListener(e -> {
    				checkDetails(cardNumber.getText(), securityCode.getText());
                });
    			
    			// Action listener for the "Pay" button
    			Paybutton.addActionListener(e -> {
    				generateReceipt(addressOfUser, paymentType, cardNumber.getText(), amount, objDate);
    				removeAfterPayment(basket, basketText, basketLabel);
                });
    			  
    			// Add buttons to the panel
    			panel.add(CheckDetails);
    			panel.add(Paybutton);
    			
    			// Add the panel to the credit card frame and set its size and visibility
    			CreditCardFrame.add(panel);
    			CreditCardFrame.setSize(600, 600);
    			CreditCardFrame.setVisible(true);
    			
    			
    	}
    	else {
    			// If payment type is PayPal
    			JFrame PaypalFrame = new JFrame();  	 // Create a new JFrame for PayPal payment
    			JTextField emailAddress = new JTextField();		// Create text field for email address
    			JPanel panel = new JPanel(new GridLayout(0, 1));   // Create a panel with a grid layout
    			
    			// Creates buttons for payment and checking details
    			JButton Paybutton = new JButton("Pay");
    			JButton CheckEmail = new JButton("Check Email");
    			
    			// Add labels and input field for email address to the panel
    			panel.add(new JLabel("The amount you have to pay is: £"+amount));
    			panel.add(new JLabel("Press the check email button first before paying"));
    			panel.add(new JLabel("Enter your email address in:"));
    			panel.add(emailAddress);
    			
    			// Action listener for the "Check Email" button
    			CheckEmail.addActionListener(e -> {
    				checkemailaddress(emailAddress.getText());
                });
    			
    			// Action listener for the "Pay" button
    			Paybutton.addActionListener(e -> {
    				generateReceipt(addressOfUser, paymentType, emailAddress.getText(), amount, objDate);
    				removeAfterPayment(basket, basketText, basketLabel);
                });
    			
    			// Add buttons to the panel
    			panel.add(CheckEmail);
    			panel.add(Paybutton);
    			
    			// Add the panel to the PayPal frame and set its size and visibility
    			PaypalFrame.add(panel);
    			PaypalFrame.setSize(600, 600);
    			PaypalFrame.setVisible(true);
    			
    	}
    }
    
    
    
    /**
     * Checks the validity of the entered credit card number and security code.
     * Displays appropriate messages to inform the user of the validation result.
     *
     * @param cardNumber   The entered credit card number as a string.
     * @param securityCode The entered security code as a string.
     */
    
    private void checkDetails(String CardNumber, String SecurityCode) {
    	// Convert the entered card number and security code strings to integers
    	int Cardnum = Integer.parseUnsignedInt(CardNumber);
    	int SecCode = Integer.parseUnsignedInt(SecurityCode);
    	
    	// Check the validity of the credit card number using a helper method from CreditCardPayment class
    	if (CreditCardPayment.isValidCardNumber(Cardnum)) {
    		// If the card number is valid, display an information message
    		JOptionPane.showMessageDialog(this, "This CreditCard number: " + Cardnum + " is valid. Please continue by pressing pay", "Correct creditcard number", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else {
    		// If the card number is invalid, display a warning message
    		JOptionPane.showMessageDialog(this, "This CreditCard number: " + Cardnum + " is incorrect", "Incorrect credit card number", JOptionPane.WARNING_MESSAGE);
    	}
    	
    	
    	// Check the validity of the security code using a helper method from CreditCardPayment class
    	if (CreditCardPayment.isValidSecurityCode(SecCode)) {
    		 // If the security code is valid, display an information message
    		JOptionPane.showMessageDialog(this, "This Security code: " + SecCode + " is valid. Please continue by pressing pay", "Correct Security code", JOptionPane.INFORMATION_MESSAGE);
    	}
    	else {
    		 // If the security code is invalid, display a warning message
    		JOptionPane.showMessageDialog(this, "This Security code: " + SecCode + " is incorrect", "Incorrect Security code", JOptionPane.WARNING_MESSAGE);
    	}
    }

    
    
    /**
     * Checks the validity of the entered email address.
     * Displays appropriate messages to inform the user of the validation result.
     *
     * @param email The entered email address as a string.
     */
    
	public void checkemailaddress(String email) {
		// Check the validity of the email address using a valid email checker method from PayPalPayment class
    	if (PayPalPayment.isValidEmail(email)) {
    		// If the email address is valid, display an information message
    		JOptionPane.showMessageDialog(this, "This email: " + email + " is valid. Please continue by pressing pay", "Correct email", JOptionPane.INFORMATION_MESSAGE);
		}
    	
		else {
			// If the email address is invalid, display a warning message
			JOptionPane.showMessageDialog(this, "This email: " + email + " is incorrect", "Incorrect email", JOptionPane.WARNING_MESSAGE);
		}
    }
	
	
	
	/**
	 * Generates a receipt based on the provided parameters and displays it in a JFrame.
	 * The receipt includes payment details, delivery address, and payment method.
	 *
	 * @param address    The delivery address of the purchase.
	 * @param paymentType The payment method used (e.g., "PayPal", "Credit Card").
	 * @param emailCard   The email address or card number associated with the payment.
	 * @param amount      The total amount paid for the purchase.
	 * @param TodayDate   The current date of the transaction.
	 */
	
	public void generateReceipt(Address address, String paymentType, String emailCard, double amount, Date TodayDate) {

		// Create a panel to hold the receipt content
		JPanel receiptPanel = new JPanel();
		
		// Create a frame to display the receipt
		JFrame ReceiptPanel = new JFrame();
		
		// Initialize the receipt template with default error message
		String receiptTemplate;
		
		// Determine the payment method and construct the receipt accordingly
        if (paymentType == ("Pay Pal")) {
            receiptTemplate = "£" + amount + " paid by PayPal using " + emailCard + " on " + TodayDate + ", and the delivery address is " + address;
        
        } else if (paymentType ==("Credit Card")) {
            receiptTemplate = "£" + amount + " paid by Credit Card using " + emailCard + " on " + TodayDate + ", and the delivery address is " + address;
        
        } else {
            receiptTemplate = "Error: Unknown payment method";
        }
        
        // Add the receipt template as a JLabel to the receipt panel
        receiptPanel.add(new JLabel(receiptTemplate));
        
        // Add the receipt panel to the JFrame
        ReceiptPanel.add(receiptPanel);
        
        // Set the size and make the receipt panel visible
        ReceiptPanel.setSize(900, 200);
        ReceiptPanel.setVisible(true);
		
	}
    
	
	
	
	/**
	 * Clears the shopping basket after a successful payment and updates the display.
	 *
	 * @param basket       The shopping basket to be cleared.
	 * @param basketText   The StringBuilder containing the text representation of the basket.
	 * @param basketLabel  The JLabel used to display the basket contents.
	 */
	
	public void removeAfterPayment(ShoppingBasket basket,StringBuilder basketText, JLabel basketLabel) {
    
		 // Clear the StringBuilder holding the basket text
    	 basketText.setLength(0);
    	 
    	 // Update the text of the JLabel to reflect the cleared basket
    	 basketLabel.setText(basketText.toString());
    	 
    	 // Clear the contents of the shopping basket
    	 basket.clearBasket();
    	
    }
	
	
	
	/**
	 * The main entry point of the application.
	 * It creates and displays the UserSelectionGUI.
	 * The GUI is initialized within the Event Dispatch Thread (EDT) for Swing components.
	 *
	 * @param args Command-line arguments (not used in this application).
	 */
	
    public static void main(String[] args) {
    	// Schedule the creation and display of the GUI within the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
        	
        	 // Create an instance of the UserSelectionGUI
            UserSelectionGUI gui = new UserSelectionGUI();
            
            // Set the GUI visible
            gui.setVisible(true);
        });
    }
}
