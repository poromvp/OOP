import java.util.Arrays;
import java.util.Scanner;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Store{
    public Transaction[] transactions;              // danh sách giao dịch
    public Order[] orderList;                       // danh sách đơn hàng
    public Customer[] customers;                    // danh sách khách hàng
    public Discount[] discounts;                    // danh sách chương trình khuyến mãi
    public Manager managers;
    public Department departments;
    public Cashier cashiers;
    public InventoryManager IvenProduct;
    public InventoryManager OrderProduct;

    
    public Store(){
        Order order=new Order();
        String filepath=null;
        orderList=order.readFromFile(filepath);
        customers = Customer.readFromFile("SieuThiMini\\customers.txt");
        discounts = Discount.readFromFile("SieuThiMini\\discount.txt");
        managers = new Manager();

        departments = new Department();
        cashiers = new Cashier();
        IvenProduct = new InventoryManager();
        OrderProduct = new InventoryManager();
        managers.readFromFile("SieuThiMini\\dsnv.txt");
        departments = new Department();
        departments.readFromFile("SieuThiMini\\DepartmentList.txt");

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

    /* thao tác nhân viên start */
    public void xuatNV() {
        managers.getdetail();
    }

    public void ThemNV() {
        managers.add();
    }

    public void XoaNV() {
        managers.remove();
    }

    public void SuaNV() {
        managers.ChangeInFo();
    }

    public void TimNV() {
        managers.search();
    }

    public void XuatPBan() {
        departments.getdetail();
    }

    public void ThemQLPB() {
        departments.add();
    }

    public void XoaPB() {
        departments.remove();
    }

    public void SuaPB() {
        departments.ChangeInFo();
    }

    public void TimPB() {
        departments.search();
    }

    public void xuatThuNgan() {
        cashiers.getdetail();
    }

    public void themThuNgan() {
        cashiers.add();
    }

    public void xoaThuNgan() {
        cashiers.remove();
    }

    public void timThuNgan() {
        cashiers.search();
    }

    public void suaThuNgan() {
        cashiers.ChangeInFo();
    }

    public void xuatKho() {
        IvenProduct.getdetail();
    }

    public void themKho() {
        IvenProduct.add();
    }

    public void xoaKho() {
        IvenProduct.remove();
    }

    public void suaKho() {
        IvenProduct.ChangeInFo();
    }

    public void timKho() {
        IvenProduct.search();
    }

    public void xuatNhapKho() {
        OrderProduct.getdetailOrder();
    }

    public void themNhapKho() {
        OrderProduct.addOrder();
    }

    public void xoaNhapKho() {
        OrderProduct.removeOrder();
    }

    public void suaNhapKho() {
        OrderProduct.ChangeInFoOrder();
    }

    public void timNhapKho() {
        OrderProduct.searchOrder();
    }
    /* thao tác nhân viên end */


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

    
    // Thống kê nhân viên suất sắc nhất theo tháng/năm
    public void thongKeNhanVien() {
        cashiers.statisticBestCashier();
    }
    

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

    public void thongkeDoanhThu(){
        byte i=0;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US); //chuyển định dạng xuất có , ngăn cách hàng nghìn, trăm, triệu
        for(double mang : Order.thongkeQUY(orderList)){             //Quý 1: từ tháng 1 tới tháng 3
            String formattedAmount = numberFormat.format(mang);     //Quý 2: từ tháng 4 tới tháng 6
            System.out.println("Quý "+(i+1)+": "+formattedAmount+" VND");  //Quý 3: từ tháng 7 tới tháng 9
            i++;                                                    //Quý 4: từ tháng 10 tới tháng 12
        }
    }

    public void thongkeSpBanChay(Scanner scanner){
        System.out.print("Bạn Muốn Xem Top Bao Nhiêu Sản Phẩm Bán Chạy Nhất: ");
        int n=Integer.parseInt(scanner.nextLine());
        System.out.println("╔══════════════════════╦══════════════════════╗");
        System.out.println("║     Tên Sản Phẩm     ║     Số Lượng Bán     ║");
        System.out.println("╠══════════════════════╬══════════════════════╣");
        for(int i=0;i<n;i++){
            System.out.printf("║   %-19s║           %-11s║\n",Order.thongkeBanChay(orderList)[i].getName(),Order.thongkeBanChay(orderList)[i].getQuantity());
            if (i < n - 1) {
                System.out.println("╠══════════════════════╬══════════════════════╣");
            }
        }
        System.out.println("╚══════════════════════╩══════════════════════╝");
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

    // Thống kê Khách hàng mua nhiều nhất (theo điểm tích lũy)
    public void thongKeCustomer() {
        Customer.rankCustomersByLoyaltyPointsWithFile(customers);
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
        Category.readCategoryFromFile("SieuThiMini\\category.txt");
        Supplier.readSupplierFromFile("SieuThiMini\\supplier.txt");
        Product.readProductsFromFile("SieuThiMini\\product.txt");
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
        int choice;
        String find="";
        do {
            System.out.println("------TIM KIEM SAN PHAM------");
            System.out.println("0.Thoat.");
            System.out.println("1.Tim kiem theo ten. ");
            System.out.println("2.Tim kiem theo loai. ");
            System.out.println("3.Tim kiem theo nha cung cap. ");
            choice = new Scanner(System.in).nextInt();
            switch (choice) {
                case 0:
                    System.out.println("Thoat chinh sua.");
                    break;
                case 1:
                    System.out.println("Nhap tu khoa muon tim kiem: ");
                     find= scanner.nextLine();
                    Product.findById(find);
                    break;
                case 2:
                    System.out.println("Nhap ten loai san pham muon tim kiem: ");
                    find= scanner.nextLine();
                    Product.findByCategory(find);
                    break;
                case 3:
                    System.out.println("Nhap ten nha cung cap muon tim kiem");
                    find= scanner.nextLine();
                    Product.findBySupplier(find);
                    break;
                default:
                    System.out.println("Lua chon sai. Vui long chon lai.");
            }
        } while (choice != 0);

    }
    /* Cac thao tac voi Product END */
    /* Các thao tác giao dịch Start */
    InvoiceManager invoice = new InvoiceManager();
    
    // Khởi tạo 1 giao dịch mới
    public void taoGiaoDichMoi() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Chọn phương thức thanh toán:");
        System.out.println("1. Tiền mặt");
        System.out.println("2. Thẻ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        Cashier cashier = new Cashier();  
    
        Order order = new Order(); 
    
        try {
            // Tạo giao dịch mới từ Order
            Transaction transaction = new Transaction(
                Integer.parseInt(order.getOrderId()), 
                new SimpleDateFormat("dd/MM/yyyy").parse(order.getOrderDate())
            );
    
            // Thêm các sản phẩm từ đơn hàng vào giao dịch
            for (Product product : order.getProductList()) {
                Item item = new Item(product.name, product.price, product.quantity);
                transaction.addItem(item);
            }
          
    
            // Xử lý thanh toán
            if (paymentMethod == 1) {
                System.out.print("Nhập số tiền khách đưa: ");
                double customerPaid = scanner.nextDouble();
                transaction.setCustomerPaid(customerPaid);
                // Tạo hóa đơn cho thanh toán tiền mặt
                Receipt receipt = new Receipt(Integer.parseInt(order.getOrderId()), transaction, cashier);
                invoice.addReceipt(receipt);  
                System.out.println("Giao dịch đã được tạo thành công bằng tiền mặt!");
            } else if (paymentMethod == 2) {
                // Thanh toán thẻ
                CardPayment cardPayment = new CardPayment();
                cardPayment.inputCardDetails();  
                transaction.setCustomerPaid(order.getTotalAmount() + Order.calculateVAT(order.getTotalAmount()));
                // Tạo hóa đơn cho thanh toán thẻ
                Receipt receipt = new Receipt(Integer.parseInt(order.getOrderId()), transaction, cashier);
                invoice.addReceipt(receipt);
                System.out.println("Giao dịch đã được tạo thành công bằng thẻ!");
            } else {
                System.out.println("Phương thức thanh toán không hợp lệ.");
            }
    
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        scanner.close();
    }
    
    // Sửa hóa đơn
    public void suaHoaDon() {
        invoice.editReceiptById();
    }

    // Xóa hóa đơn
    public void xoaHoaDon() {
        invoice.deleteReceiptById();
    }

    // Xuất hóa đơn + in biên lai
    public void xuatHoaDon() {
        invoice.exportInvoice();
    }

    // Tìm kiếm hóa đơn
    public void timKiemHoaDon() {
        invoice.searchAndPrintReceipt();
    }

   

       /*
        Gọi trong chạy chương trình
        var.taoGiaoDichMoi();
        var.suaHoaDon();
        var.xoaHoaDon();
        var.xuatHoaDon();
        var.timKiemHoaDon();
        */
        
    
    /* Các thao tác giao dịch End */
}






























