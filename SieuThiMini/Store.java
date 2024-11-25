import java.util.Arrays;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Store{
    public Staff[] staffList;                   // danh sách nhân viên
    public Transaction[] transactions;          // danh sách giao dịch
    public Order[] orderList;                   // danh sách đơn hàng
    public Customer[] customers;                // danh sách khách hàng
    public Discount[] discounts;                 // danh sách chương trình khuyến mãi

    
    public Store(){
        String filepath=null;
        Order order=new Order();
        orderList=order.readFromFile(filepath);
        customers = Customer.readFromFile("customers.txt");
        discounts = Discount.readFromFile("discount.txt");
    }
    public Store(Staff[] staffList,
        Transaction[] transactions) {
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

    public void timkiem(Scanner scanner){
        Order.loc(scanner, orderList);
    }

    public void thongkeOrder(Scanner scanner){
        Order.statisticalOrders(scanner,orderList);
    }
    /* các thao tác cho ds đơn đặt hàng END*/

    /* Các thao tác cho danh sách khách hàng START */
    // Chức năng thứ 2 trong menu

    public void xuatDanhSachKhachHang() {
        Customer.outputCustomer(customers);
    }

    // Chức năng thứ 3 trong menu
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
        Customer.addCustomer(customers, new Customer(newID, newName, newContact, newPoints));
    } 

    // Chức năng thứ 4 trong menu
    public void capNhatKhachHang(Scanner scanner) {
        System.out.print("Nhap ma khach hang de cap nhat: ");
        int updateID = scanner.nextInt();
        Customer.updateCustomerByID(customers, updateID);
    }

    // Chức năng thứ 5 trong menu
    public void xoaKhachHang(Scanner scanner) {
        System.out.print("Nhap ma khach hang de xoa: ");
        int deleteID = scanner.nextInt();
        Customer.removeCustomerByID(customers, deleteID);
    }
    // Chức năng thứ 6 trong menu
    public void timKhachHang(Scanner scanner) {
        System.out.print("Nhap ma khach hang de tim kiem: ");
        int searchID = scanner.nextInt();
        Customer foundCustomer = Customer.findCustomerByID(customers, searchID);
        if (foundCustomer != null) {
            System.out.println("Thong tin khach hang:");
            System.out.println("Ma khach hanh: " + foundCustomer.getCustomerID());
            System.out.println("Ten khach hang: " + foundCustomer.getName());
            System.out.println("So dien thoai: " + foundCustomer.getContactNumber());
            System.out.println("Diem tich luy: " + foundCustomer.getLoyaltyPoints());
        }
    }

    /* Các thao tác cho danh sách khách hàng END */

    /* Các thao tác cho danh sách chương trình khuyến mãi START */
    // Chức năng 2
    public void xuatDanhSachChuongTrinhKhuyenMai() {
        Discount.outputDiscounts(discounts);
    }

    // Chức năng 3
    public void themChuongTrinhKhuyenMai() {
        Discount.addDiscount(discounts);
    }

    // Chức năng 4
    public void capNhatChuongTrinhKhuyenMai(Scanner scanner) {
        System.out.print("Nhap ma chuong trinh khuyen mai can sua: ");
        int updateID = scanner.nextInt();
        Discount.updateDiscountByID(discounts, updateID);
    }

    // Chức năng 5
    public void xoaChuongTrinhKhuyenMai(Scanner scanner) {
        System.out.print("Nhap ma chuong trinh khuyen mai can xoa: ");
        int removeID = scanner.nextInt();
        Discount.removeDiscountByID(discounts, removeID);
    }

    // Chức năng 6
    public void timKiemChuongTrinhKhuyenMai(Scanner scanner) {
        System.out.print("Nhap ma chuong trinh khuyen mai can tim: ");
        int searchID = scanner.nextInt();
        Discount foundDiscount = Discount.searchDiscountByID(discounts, searchID);
        if (foundDiscount != null) {
            System.out.println("Thong tin chuong trinh khuyen mai tim thay:");
            System.out.println("Ma: " + foundDiscount.getDiscountID());
            System.out.println("Ten: " + foundDiscount.getName());
            System.out.println("Phan tram giam: " + foundDiscount.getDiscountPercentage() + "%");
            System.out.println("Ngay bat dau: " + Discount.DATE_FORMAT.format(foundDiscount.getStartDate()));
            System.out.println("Ngay ket thuc: " + Discount.DATE_FORMAT.format(foundDiscount.getEndDate()));
        } else {
            System.out.println("Khong tim thay chuong trinh khuyen mai voi ma: " + searchID);
        }
    }
    /* Các thao tác cho danh sách chương trình khuyến mãi END */


    
}
