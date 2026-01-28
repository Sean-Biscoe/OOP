package cas; // Package declaration

//Class representing an address
public class Address {
		// Private instance variables to store address components
	    private String houseNumber;
	    private String city;
	    private String postCode;

	    // Constructor to initialize an Address object with provided parameters
	    public Address(String houseNumber, String city, String postCode) {
	    	// Setting houseNumber, city, and postCode using setter methods to ensure encapsulation
	        this.setnumber(houseNumber);
	        this.setcity(city);
	        this.setpostCode(postCode);
	    }

	    // Getter method to retrieve the house number
	    public String getnumber() {
	        return houseNumber;
	    }

	    // Getter method to retrieve the city
	    public String getCity() {
	        return city;
	    }

	    // Getter method to retrieve the post code
	    public String getpostCode() {
	        return postCode;
	    }
	    
	    // Setter method to set the house number
	    public void setnumber(String houseNumber) {
	    	this.houseNumber = houseNumber;
	    }
	    
	    // Setter method to set the city
	    public void setcity(String city) {
	    	this.city = city;
	    }
	    
	    // Setter method to set the post code
	    public void setpostCode(String postCode) {
	    	this.postCode = postCode;
	    }
	    
	    // Override toString() method to return a string representation of the Address object
	    @Override
	    public String toString() {
	        return houseNumber + ", " + city + ", " + postCode + ".";
	     }
}
