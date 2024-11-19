public class Store {
    public String storeName, address, phoneNumber;
    public Staff[] staffList;                   // danh sách nhân viên
    public Product[] inventory;                 // danh sách sản phẩm
    public Transaction[] transactions;          // danh sách giao dịch
    public LoyaltyProgram[] loyaltyProgram;     // danh sách khuyến mãi cho khách hàng thân thiết 

    public Store(){}
    public Store(String storeName, String address, String phoneNumber, Staff[] staffList, Product[] inventory,
        Transaction[] transactions, LoyaltyProgram[] loyaltyProgram) {
        this.storeName = storeName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.staffList = staffList;
        this.inventory = inventory;
        this.transactions = transactions;
        this.loyaltyProgram = loyaltyProgram;
    }
    public String getStoreName() {
        return storeName;
    }
    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public Staff[] getStaffList() {
        return staffList;
    }
    public void setStaffList(Staff[] staffList) {
        this.staffList = staffList;
    }
    public Product[] getInventory() {
        return inventory;
    }
    public void setInventory(Product[] inventory) {
        this.inventory = inventory;
    }
    public Transaction[] getTransactions() {
        return transactions;
    }
    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
    public LoyaltyProgram[] getLoyaltyProgram() {
        return loyaltyProgram;
    }
    public void setLoyaltyProgram(LoyaltyProgram[] loyaltyProgram) {
        this.loyaltyProgram = loyaltyProgram;
    }
    
    public void addStaff(Staff staff, int index){ //index là vị trí muốn thêm vào
        if (index >= 0 && index < staffList.length) {
            staffList[index] = staff; 
        } else {
            System.out.println("Vi tri vuot qua kich thuoc mang");
        }
    }
    public void addProduct(Product product, int index){
        if (index >= 0 && index < inventory.length) {
            inventory[index] = product;
        } else {
            System.out.println("Vi tri vuot qua kich thuoc mang");
        }
    }
}
