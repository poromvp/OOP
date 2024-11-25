import java.util.Arrays;
import java.util.Scanner;
public class Store{
    public Staff[] staffList;                   // danh sách nhân viên
    public Transaction[] transactions;          // danh sách giao dịch
    public Order[] orderList;                   // danh sách đơn hàng
    public Customer[] customers;                // danh sách khách hàng
    public Discount[] discount;                 // danh sách chương trình khuyến mãi

    
    public Store(){
        String filepath=null;
        Order order=new Order();
        orderList=order.readFromFile(filepath);
        customers = Customer.readFromFile("customers.txt");
    }
    public Store(Staff[] staffList,
        Transaction[] transactions, LoyaltyProgram[] loyaltyProgram) {
        this.staffList = staffList;
        this.transactions = transactions;
    }
    public Staff[] getStaffList() {
        return staffList;
    }
    public void setStaffList(Staff[] staffList) {
        this.staffList = staffList;
    }
    public Transaction[] getTransactions() {
        return transactions;
    }
    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
    

    /* các thao tác với staffList START */
    
    /* các thao tác với staffList END */

    /* các thao tác cho ds đơn đặt hàng START*/
    public void xuatOrder(){
        for(Order or:orderList){
            or.displayOrderDetails();
        }
    }

    public void addOrder(Scanner scanner){ //thêm đơn hàng
        orderList=Order.add(scanner, orderList);
    }

    public void removeOrder(Scanner scanner){ //xóa đơn hàng theo mã
        orderList=Order.xoa(scanner, orderList);
    }

    public void editOrder(Scanner scanner){
        System.out.print("Nhập mã đơn hàng cần chỉnh sửa: ");
        String temp=scanner.nextLine();
        boolean flag=false; //dùng lính canh để lặp lại chương trình nếu nhập sai
        byte so_lan_thu=0; // nếu số lần nhập sai quá nhiều thì sẽ break 
        do{
            so_lan_thu++;
            for(int i=0;i<orderList.length;i++){
                if(orderList[i].orderId.equals(temp)){
                    orderList[i].edit(scanner, orderList[i].product); //phương thức chỉnh sửa của class order
                    flag=true;
                    break;
                }
            }
            if(!flag){
                if(so_lan_thu>2){
                    System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                    break;
                }
                System.out.println("Mã Đơn Hàng Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Chỉnh Sửa Không?\n 1.Có  0.Không");
                byte choice=Byte.parseByte(scanner.nextLine());
                if(choice==1){
                    System.out.println("Vậy Hãy Nhập Lại Mã Đơn Hàng Chính Xác");
                    System.out.print("--> ");
                    temp=scanner.nextLine();
                }
                else{
                    System.out.println("Đã thoát!");
                    flag=true;
                }
            }
        }while(flag!=true);
    }

    public void thongkeOrder(Scanner scanner){
        Order.loc(scanner, orderList);
    }

    public void timkiem(Scanner scanner){
        Order.statisticalOrders(scanner,orderList);
    }
    /* các thao tác cho ds đơn đặt hàng END*/

    /* Các thao tác cho danh sách khách hàng */
    public void themKhachHang(Scanner scanner) {
        System.out.println("Them khach hang moi:");
        System.out.print("Nhap ma khach hang: ");
        int newID = scanner.nextInt();
        scanner.nextLine(); // Xóa bỏ dòng trống
        System.out.print("Nhap ten khach hang: ");
        String newName = scanner.nextLine();
        System.out.print("Nhap so dien thoai: ");
        String newContact = scanner.nextLine();
        System.out.print("Nhap diem tich luy: ");
        int newPoints = scanner.nextInt();
        customers = Customer.addCustomer(customers, new Customer(newID, newName, newContact, newPoints));
    } 

    public void xuatDanhSachKhachHang() {
        Customer.output(customers);
    }

    public void timKhachHang (Scanner scanner) {
        System.out.print("Nhap ma khach hang de tim kiem: ");
            int searchID = Integer.parseInt(scanner.nextLine());
            Customer foundCustomer = Customer.findCustomerByID(customers, searchID);
            if (foundCustomer != null) {
                System.out.println("Thong tin khach hang:");
                System.out.println("Ma khach hanh: " + foundCustomer.getCustomerID());
                System.out.println("Ten khach hang: " + foundCustomer.getName());
                System.out.println("So dien thoai: " + foundCustomer.getContactNumber());
                System.out.println("Diem tich luy: " + foundCustomer.getLoyaltyPoints());
            }
    }

    /* Các thao tác cho danh sách khách hàng END*/


    
}
