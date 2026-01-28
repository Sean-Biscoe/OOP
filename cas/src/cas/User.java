package cas; // Package declaration

//Class representing a user
public class User {
	// Private instance variables to store user information
	private int userID; // Unique identifier for the user
    private Usernames username; // Username of the user
    private String name; // Name of the user
    private String houseNumber; // House number of the user's address
    private String postcode; // Postcode of the user's address
    private String city; // City of the user's address
    private String role; // Role of the user (e.g., admin, customer)

    // Constructor to initialize a User object with provided parameters
    public User(int userID, Usernames username, String name, String houseNumber, String postcode, String city, String role) {
    	// Initializing instance variables with provided values
    	this.userID = userID;
        this.username = username;
        this.name = name;
        this.houseNumber = houseNumber;
        this.postcode = postcode;
        this.city = city;
        this.role = role;
    }

    // Getter methods to retrieve user attributes
    public int getUserID() {
        return this.userID;
    }

    public Usernames getUsername() {
        return this.username;
    }

    public String getName() {
        return this.name;
    }

    public String getHouseNumber() {
        return this.houseNumber;
    }

    public String getPostcode() {
        return this.postcode;
    }

    public String getCity() {
        return this.city;
    }

    public String getRole() {
        return this.role;
    }
}





