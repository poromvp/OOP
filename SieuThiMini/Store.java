import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Store {
    public String storeName, address, phoneNumber;
    public Staff[] staffList;                   // danh sách nhân viên
    public Product[] inventory;                 // danh sách sản phẩm
    public Transaction[] transactions;          // danh sách giao dịch
    public LoyaltyProgram[] loyaltyProgram;     // danh sách khuyến mãi cho khách hàng thân thiết 
    public Store(){
        String filepath="dsnv.txt";
        int n=0;
        try (BufferedReader br= new BufferedReader(new FileReader(filepath))){
            String Line;
            while ((Line= br.readLine())!=null){
                n++;
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        staffList=new Staff[n];
        int i=0;
        try (BufferedReader br= new BufferedReader(new FileReader(filepath))){
            String Line;
            while ((Line= br.readLine())!=null){
                staffList[i] = new Staff();
                String [] parts= Line.split(" ");
                staffList[i].setStaffID(parts[0]);
                staffList[i].setName(parts[1] +" "+ parts[2]+ " "+ parts[3]);
                staffList[i].setSalary(Integer.parseInt(parts[4]));
                staffList[i].setRole(parts[5]);
                i++;
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }

    }
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
    

    /* các thao tác với staffList START */
    public void addStaff(int index){ //index là vị trí muốn thêm vào
        if (index >= 0 && index < staffList.length) {
            Staff staff=new Staff();
            staffList=Arrays.copyOf(staffList, staffList.length+1);
            for(int i=staffList.length-1;i>index;i--) {
               staffList[i]=staffList[i-1];
            }
            staffList[index]=staff;
            staff.inputStaff();
        } else {
            System.out.println("Vi tri vuot qua kich thuoc mang");
        } 
    }

    public void xuatStaff(){
        for(int i=0;i<staffList.length;i++){
            staffList[i].displayStaffInfo();
        }
    }

    public void addProduct(Product product, int index){
        if (index >= 0 && index < inventory.length) {
            inventory[index] = product;
        } else {
            System.out.println("Vi tri vuot qua kich thuoc mang");
        }
    }
    /* các thao tác với staffList END */

}
