import java.util.Arrays;
import java.util.Scanner;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
public class Store{
    public Transaction[] transactions;              // danh sách giao dịch
    public Order[] orderList;                       // danh sách đơn hàng
    public Customer[] customers;                    // danh sách khách hàng
    public Discount[] discounts;                    // danh sách chương trình khuyến mãi

    
    public Store(){
        Order order=new Order();
        String filepath=null;
        orderList=order.readFromFile(filepath);
        customers = Customer.readFromFile("C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\customers.txt");
        discounts = Discount.readFromFile("C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\discount.txt");
    }
    public Store(Staff[] staffList,
        Transaction[] transactions) {
        this.transactions = transactions;
        this.discounts = new Discount[0]; // Khởi tạo danh sách trống
    }

    public Transaction[] getTransactions() {
        return transactions;
    }
    public void setTransactions(Transaction[] transactions) {
        this.transactions = transactions;
    }
    /*thao tác nhân viên start */
    public void xuatDSNV(){
        Manager Mana = new Manager();
        Mana.outStaff();
    }

    public void themNV (){
        Manager Manb = new Manager();
        Manb.addStaff();
    }
    
    public void xoaNV (){
        Manager Manc = new Manager();
        Manc.removeStaff("dsnv.txt");
    }

    public void suaNV (){
        Manager Mand= new Manager();
        Mand.ChangeInFo();
    }

    public void timNV(){
        Manager Mane= new Manager();
        Mane.searchStaffByCriteria();
    }
    
    /*thao tác nhân viên end */


    // update Nhân
    public Discount[] getDiscounts() {
        return discounts;
    }

    public void setDiscounts(Discount[] discounts) {
        this.discounts = discounts;
    }

    public Customer[] getCustomers() {
        return customers;
    }

    public void setCustomers(Customer[] customers) {
        this.customers = customers;
    }
    // 


    /* các thao tác cho ds đơn đặt hàng START*/
    public void xuatOrder(){
        for(Order or:orderList){
            or.displayOrderDetails();
        }
    }


    public void addOrder(Scanner scanner){ //thêm đơn hàng
        orderList=Order.add(scanner, orderList);
        System.out.println("Thêm Đơn Hàng Mới Thành Công!");
    }

    public void removeOrder(Scanner scanner){ //xóa đơn hàng theo mã
        orderList=Order.xoa(scanner, orderList);
    }

    public void editOrder(Scanner scanner){
        System.out.print("Nhập mã đơn hàng cần chỉnh sửa: ");
        String temp=scanner.nextLine();
        boolean flag=false; //dùng lính canh để lặp lại chương trình nếu nhập sai
        byte so_lan_thu=0; // nếu số lần nhập sai quá nhiều thì sẽ break 
        byte choice=0; // Lụa chọn có tiếp tục chỉnh sửa không hay thoát
        do{
            so_lan_thu++;
            for(int i=0;i<orderList.length;i++){
                if(orderList[i].orderId.equals(temp)){            
                    orderList[i].edit(scanner, orderList[i].product, orderList, i); //phương thức chỉnh sửa của class order
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
                do{
                    System.out.print("→ ");
                    choice=Byte.parseByte(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Vậy Hãy Nhập Lại Mã Đơn Hàng Chính Xác");
                            System.out.print("→ ");
                            temp=scanner.nextLine();
                            break;
                        case 0:
                            System.out.println("Đã thoát!");
                            flag=true;
                            break;
                        default:
                            System.out.println("Không hợp lệ hãy nhập lại!");
                            break;
                    }
                }while(choice!=0 && choice!=1);
            }
        }while(flag!=true);
    }

    public void timkiem(Scanner scanner){ //Tìm kiếm đơn hàng theo nhiều khóa
        Order.loc(scanner, orderList);
    }

    public void thongkeOrder(Scanner scanner){
        Order.statisticalOrders(scanner,orderList);
    }
    /* các thao tác cho ds đơn đặt hàng END*/

    /* Các thao tác cho danh sách khách hàng START */

    // Chức năng thứ 1 trong menu
    public void themKhachHang(Scanner scanner) {
        customers = Customer.addCustomers(customers);
    } 

    // Chức năng thứ 2 trong menu
    public void xuatDanhSachKhachHang() {
        Customer.outputCustomer(customers);
    }

    // Chức năng thứ 3 trong menu
    public void xoaKhachHang(Scanner scanner) {
        System.out.print("Nhập mã khách hàng để xóa: ");
        int deleteID = Integer.parseInt(scanner.nextLine());
        customers = Customer.removeCustomerByID(customers, deleteID);
    }

    // Chức năng thứ 4 trong menu
    public void capNhatKhachHang(Scanner scanner) {
        System.out.print("Nhập mã khách hàng để cập nhật: ");
        int updateID = Integer.parseInt(scanner.nextLine());
        Customer.updateCustomerByID(customers, updateID);
    } 

    // Chức năng thứ 5 trong menu
    public void timKhachHang(Scanner scanner) {
        Customer.searchCustomers(scanner, customers);
    } 

    /* Các thao tác cho danh sách khách hàng END */

    /* Các thao tác cho danh sách chương trình khuyến mãi START */
     // Chức năng 1: Thêm chương trình khuyến mãi
     public void themChuongTrinhKhuyenMai() {
        discounts = Discount.addDiscounts(discounts); // Cập nhật danh sách
    }

    // Chức năng 2: Xuất danh sách chương trình khuyến mãi
    public void xuatDanhSachChuongTrinhKhuyenMai() {
        Discount.outputDiscounts(discounts);
    }

    
    // Chức năng 3: Xóa chương trình khuyến mãi
    public void xoaChuongTrinhKhuyenMai(Scanner scanner) {
        System.out.print("Nhập mã chương trình khuyến mãi cần xóa: ");
        int removeID = Integer.parseInt(scanner.nextLine());
        discounts = Discount.removeDiscountByID(discounts, removeID); // Cập nhật danh sách
    }

    // Chức năng 4: Cập nhật chương trình khuyến mãi
    public void capNhatChuongTrinhKhuyenMai(Scanner scanner) {
        System.out.print("Nhập mã chương trình khuyến mãi cần sửa: ");
        int updateID = Integer.parseInt(scanner.nextLine());
        Discount.updateDiscountByID(discounts, updateID); // Cập nhật danh sách
    }

    // Chức năng 5: Tìm kiếm chương trình khuyến mãi
    public void timKiemChuongTrinhKhuyenMai(Scanner scanner) {
        Discount.searchDiscounts(scanner, discounts);
    }

    /* Các thao tác cho danh sách chương trình khuyến mãi END */
    /* Cac thao tac voi Product START */
    //Doc tu file
    public void readFileProduct(){
        Category.readCategoryFromFile("C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\category.txt");
        Supplier.readSupplierFromFile("C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\supplier.txt");
        Product.readProductsFromFile("C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\product.txt");
        System.out.println("Đã thêm "+Product.getCnt()+" sản phẩm.");
    }
    //Xuat danh sach cac san pham
    public void productDetail(){
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                "Product ID", "Name", "Price", "Quantity", "Category", "Supplier");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                "-------------------", "-------------------", "-------------------",
                "-------------------", "-------------------", "-------------------");
        for (int i=0;i<Product.getCnt();i++){
            Product.productList[i].getDetails();
        }
    }
    //Them san pham
    public void addProduct(Scanner scanner){
        System.out.println("So phan tu ban muon them la: ");
        int n=Integer.parseInt(scanner.nextLine());
        for (int i=0;i<n;i++){
            Product.addProduct();
        }
    }
    //Sua san pham
    public void updateProduct(Scanner scanner){
        System.out.println("Nhap vao id san pham muon sua.");
        String ud= scanner.nextLine();
        Product.upDateProduct(ud);
    }
    public void removeProduct(Scanner scanner){
        System.out.println("Nhap vao id san pham muon xoa.");
        String rm= scanner.nextLine();
        Product.deleteProduct(rm);
    }
    public void findProduct(Scanner scanner){
        System.out.println("Nhap tu khoa muon tim kiem");
        String find= scanner.nextLine();
        Product.Find(find);
    }
    /* Các thao tác giao dịch Start */
    InvoiceManager invoice = new InvoiceManager();
    Receipt receipt =new Receipt();
    Order order = new Order();
    //khởi tạo 1 giao dịch mới

    public void taoGiaoDichMoi() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chọn phương thức thanh toán:");
        System.out.println("1. Tiền mặt");
        System.out.println("2. Thẻ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine();
        Cashier cashier = new Cashier();
        if (paymentMethod == 1) {
            System.out.print("Nhập số tiền khách đưa: ");
            double customerPaid = scanner.nextDouble();
            scanner.nextLine();

            Transaction transaction = new Transaction(Integer.parseInt(order.getOrderId()), new SimpleDateFormat("dd/MM/yyyy").parse(order.getOrderDate()));
            for (Product product : order.getProductList()) {
                Item item = new Item(product.name, product.price, product.quantity);
                transaction.addItem(item);
            }
            transaction.setCustomerPaid(customerPaid);

            invoice.addReceipt(new Receipt(Integer.parseInt(order.getOrderId()), transaction,cashier));
            System.out.println("Giao dịch đã được tạo thành công bằng tiền mặt!");
        } else if (paymentMethod == 2) {
            CardPayment cardPayment = new CardPayment();
            cardPayment.inputCardDetails();

            Transaction transaction = new Transaction(Integer.parseInt(order.getOrderId()), new SimpleDateFormat("dd/MM/yyyy").parse(order.getOrderDate()));
            for (Product product : order.getProductList()) {
                Item item = new Item(product.name, product.price, product.quantity);
                transaction.addItem(item);
            }
            transaction.setCustomerPaid(order.getTotalAmount() + Order.calculateVAT(order.getTotalAmount()));

            invoice.addReceipt(new Receipt(Integer.parseInt(order.getOrderId()), transaction,cashier));
            System.out.println("Giao dịch đã được tạo thành công bằng thẻ!");
        } else {
            System.out.println("Phương thức thanh toán không hợp lệ.");
        }
    }
    }

    // Sửa hóa đơn
    public void suaHoaDon(){
        invoice.editReceiptById();
    }
    // Xóa hóa đơn
    public void xoaHoaDon(){
        invoice.deleteReceiptById();
    }
   // Xuất hóa đơn + in biên lai
    public void xuatHoaDon(Scanner scanner) {
        invoice.exportinvoice();
    }

    // Tìm kiếm hóa đơn
    public void timKiemHoaDon() {
    invoice.searchAndPrintReceipt();
}

    /* Các thao tác giao dịch End */
}






























