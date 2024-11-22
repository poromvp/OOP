import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
public class Store implements QLFile{
    public Staff[] staffList;                   // danh sách nhân viên
    public Product[] inventory;                 // danh sách sản phẩm
    public Transaction[] transactions;          // danh sách giao dịch
    public LoyaltyProgram[] loyaltyProgram;     // danh sách khuyến mãi cho khách hàng thân thiết 
    public Order[] orderList;                   // danh sách đơn hàng
    public Store(){
        String filepath=null;
        readFromFile(filepath);
    }
    public Store(Staff[] staffList, Product[] inventory,
        Transaction[] transactions, LoyaltyProgram[] loyaltyProgram) {
        this.staffList = staffList;
        this.inventory = inventory;
        this.transactions = transactions;
        this.loyaltyProgram = loyaltyProgram; 
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

    /* các thao tác cho ds đơn đặt hàng START*/
    public void xuatOrder(){
        for(int i=0;i<orderList.length;i++){
            orderList[i].displayOrderDetails();
        }
    }
    /* các thao tác cho ds đơn đặt hàng END*/

    @Override
    public void readFromFile(String filePath){
        /* Tải danh sách đơn hàng - Kiệt */
        filePath="C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\donhang.txt";
        int i, n;
        n=0;
        try (BufferedReader br= new BufferedReader(new FileReader(filePath))){
            String Line;
            while ((Line= br.readLine())!=null){
                n++;
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }

        orderList=new Order[n];
        i=0;
        try (BufferedReader br= new BufferedReader(new FileReader(filePath))){
            String Line;
            while ((Line=br.readLine())!=null){
                orderList[i] = new Order();
                String [] parts= Line.split(";");
                orderList[i].setOrderId(parts[0]);
                orderList[i].setOrderDate(parts[1]);
                i++;
            }
            br.close();

        } catch (IOException e){
            e.printStackTrace();
        }
        /* Tải danh sách đơn hàng - Kiệt end */
    }
    @Override
    public void writeToFile(String filePath){
        System.out.println();
    }
}
