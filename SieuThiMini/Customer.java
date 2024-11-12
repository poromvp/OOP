package Do_An_OOp;
public class Customer {
    private int customerID;
    private int loyaltyPoints;
    private String name;
    private String contactNumber;

    public Customer() {

    }

    public Customer(int customerID, int loyaltyPoints, String name, String contactNumber) {
        this.customerID = customerID;
        this.loyaltyPoints = loyaltyPoints;
        this.name = name;
        this.contactNumber = contactNumber;
    }

    public int getCustomerID(int customerID) {
        return customerID;
    }

    public int getLoyaltyPoints(int loyaltyPoints) {
        return loyaltyPoints;
    }

    public String getName(String name) {
        return name;
    }

    public String getContactNumber(String contactNumber) {
        return contactNumber;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setLoyaltyPoints(int loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public void buyProduct() {

    }

    public void returnProduct() {

    }

    public void earnLoyaltyPoints() {
        
    }

}