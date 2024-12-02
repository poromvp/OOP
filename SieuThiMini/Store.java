import java.util.Scanner;
import java.text.NumberFormat;
import java.util.Locale;

public class Store {
    public Transaction[] transactions; // danh sách giao dịch
    public Order[] orderList; // danh sách đơn hàng
    public Customer[] customers; // danh sách khách hàng
    public Discount[] discounts; // danh sách chương trình khuyến mãi
    public Receipt[] receipts;
    public Manager managers;
    public Department departments;
    public Cashier cashiers;
    public InventoryManager IvenProduct;
    public InventoryManager OrderProduct;

    public Store() {
        readFileProduct();
        Order or = new Order();
        orderList = or.readFromFile("SieuThiMini\\donhang.txt");
        
        Customer cus=new Customer();
        customers = cus.readFromFile("SieuThiMini\\customers.txt");
        
        Discount dis=new Discount();
        discounts = dis.readFromFile("SieuThiMini\\discount.txt");

        Receipt rc=new Receipt();
        receipts =rc.readFromFile("SieuThiMini\\hoadon.txt");

        managers = new Manager();
        departments = new Department();
        cashiers = new Cashier();
        IvenProduct = new InventoryManager();
        OrderProduct = new InventoryManager();
        managers.readFromFile("SieuThiMini\\dsnv.txt");
        departments = new Department();
        departments.readFromFile("SieuThiMini\\DepartmentList.txt");

    }

    public Store(Staff[] staffList, Transaction[] transactions) {
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



    /* các thao tác cho ds đơn đặt hàng START */
    public void xuatOrder() {
        System.out.printf("%-53s╔═══════════════════════════════════════╗\n", " ");
        System.out.printf("%-53s║           CHI TIẾT ĐƠN HÀNG           ║\n"," ");
        System.out.printf("%-53s╚═══════════════════════════════════════╝\n\n", " ");
        for (Order or : orderList) {
            or.displayOrderDetails();
        }
    }

    public void addOrder(Scanner scanner) { // thêm đơn hàng
        orderList = Order.add(scanner, orderList);
        System.out.println("Thêm Đơn Hàng Mới Thành Công!");
    }

    public void removeOrder(Scanner scanner) { // xóa đơn hàng theo mã
        orderList = Order.xoa(scanner, orderList);
    }

    public void editOrder(Scanner scanner) {
        System.out.print("Nhập mã đơn hàng cần chỉnh sửa: ");
        String temp = scanner.nextLine();
        boolean flag = false; // dùng lính canh để lặp lại chương trình nếu nhập sai
        byte so_lan_thu = 0; // nếu số lần nhập sai quá nhiều thì sẽ break
        byte choice = 0; // Lụa chọn có tiếp tục chỉnh sửa không hay thoát
        do {
            so_lan_thu++;
            for (int i = 0; i < orderList.length; i++) {
                if (orderList[i].orderId.equals(temp)) {
                    orderList[i].edit(scanner, orderList[i].product, orderList, i); // phương thức chỉnh sửa của class
                                                                                    // order
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                if (so_lan_thu > 2) {
                    System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                    break;
                }
                System.out.println(
                        "Mã Đơn Hàng Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Chỉnh Sửa Không?\n 1.Có  0.Không");
                do {
                    System.out.print("→ ");
                    choice = Byte.parseByte(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Vậy Hãy Nhập Lại Mã Đơn Hàng Chính Xác");
                            System.out.print("→ ");
                            temp = scanner.nextLine();
                            break;
                        case 0:
                            System.out.println("Đã thoát!");
                            flag = true;
                            break;
                        default:
                            System.out.println("Không hợp lệ hãy nhập lại!");
                            break;
                    }
                } while (choice != 0 && choice != 1);
            }
        } while (flag != true);
    }

    public void sapxepdonhang(){
        Order.sapxepngay(orderList);
    }

    public void timkiem(Scanner scanner) { // Tìm kiếm đơn hàng theo nhiều khóa
        Order.loc(scanner, orderList);
    }

    public void thongkeDoanhThu() {
        byte i = 0;
        NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US); // chuyển định dạng xuất có , ngăn cách
                                                                               // hàng nghìn, trăm, triệu
        for (double mang : Order.thongkeQUY(orderList)) { // Quý 1: từ tháng 1 tới tháng 3
            String formattedAmount = numberFormat.format(mang); // Quý 2: từ tháng 4 tới tháng 6
            System.out.println("Quý " + (i + 1) + ": " + formattedAmount + " VND"); // Quý 3: từ tháng 7 tới tháng 9
            i++; // Quý 4: từ tháng 10 tới tháng 12
        }
    }

    public void thongkeSpBanChay(Scanner scanner) {
        System.out.print("Bạn Muốn Xem Top Bao Nhiêu Sản Phẩm Bán Chạy Nhất: ");
        int n = Integer.parseInt(scanner.nextLine());
        System.out.println("╔══════════════════════╦══════════════════════╗");
        System.out.println("║     Tên Sản Phẩm     ║     Số Lượng Bán     ║");
        System.out.println("╠══════════════════════╬══════════════════════╣");
        for (int i = 0; i < n; i++) {
            System.out.printf("║   %-19s║           %-11s║\n", Order.thongkeBanChay(orderList)[i].getName(),
                    Order.thongkeBanChay(orderList)[i].getQuantity());
            if (i < n - 1) {
                System.out.println("╠══════════════════════╬══════════════════════╣");
            }
        }
        System.out.println("╚══════════════════════╩══════════════════════╝");
    }


    public void ghifileord(){
        Order temp=new Order();
        String filename="SieuThiMini\\donhang.txt";
        temp.xoaNoiDungFile(filename);
        for(Order or : orderList){
            or.writeToFile(filename);
        }
        System.out.println("Đã ghi vào file donhang.txt");
    }
    /* các thao tác cho ds đơn đặt hàng END */



    /* Các thao tác cho danh sách khách hàng START */

    // Chức năng thứ 1 trong menu
    public void themKhachHang(Scanner scanner) {
        customers = Customer.addCustomers(customers, scanner);
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
        Customer.updateCustomerByID(customers, updateID, scanner);
    }

    // Chức năng thứ 5 trong menu
    public void timKhachHang(Scanner scanner) {
        Customer.searchCustomers(scanner, customers);
    }

    // Thống kê Khách hàng mua nhiều nhất (theo điểm tích lũy)
    public void thongKeCustomerBanNhieuNhat(Scanner scanner) {
        System.out.print("Bạn muốn xem top bao nhiêu khách hàng có lượt mua nhiều nhất: ");
        int n = Integer.parseInt(scanner.nextLine());
    
        // Đảm bảo n không vượt quá số lượng khách hàng thực tế
        n = Math.min(n, customers.length);
    
        System.out.println("╔══════════════════════╦══════════════════════╗");
        System.out.println("║     Tên Khách Hàng   ║     Điểm Tích Lũy    ║");
        System.out.println("╠══════════════════════╬══════════════════════╣");
    
        for (int i = 0; i < n; i++) {
            System.out.printf("║   %-19s║           %-11s║\n",
                Customer.rankCustomersByLoyaltyPointsWithFile(customers)[i].getName(),
                Customer.rankCustomersByLoyaltyPointsWithFile(customers)[i].getLoyaltyPoints());
            if (i < n - 1) {
                System.out.println("╠══════════════════════╬══════════════════════╣");
            }
        }
    
        System.out.println("╚══════════════════════╩══════════════════════╝");
    }

    /* Các thao tác cho danh sách khách hàng END */



    
    /* Các thao tác cho danh sách chương trình khuyến mãi START */
    // Chức năng 1: Thêm chương trình khuyến mãi
    public void themChuongTrinhKhuyenMai(Scanner scanner) {
        discounts = Discount.addDiscounts(discounts,scanner); // Cập nhật danh sách
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
        Discount.updateDiscountByID(discounts, updateID, scanner); // Cập nhật danh sách
    }

    // Chức năng 5: Tìm kiếm chương trình khuyến mãi
    public void timKiemChuongTrinhKhuyenMai(Scanner scanner) {
        Discount.searchDiscounts(scanner, discounts);
    }

    public void ghifilectkm(){
        Discount temp =new Discount();
        String filename = "SieuThiMini\\discount.txt";
        temp.xoaNoiDungFile(filename);
        for(Discount dis: discounts){
            dis.writeToFile(filename);
        }
        System.out.println("Đã ghi vào file discount.txt");
    }

    public void ghifilecus(){
        Customer temp =new Customer();
        String filename = "SieuThiMini\\customers.txt";
        temp.xoaNoiDungFile(filename);
        for(Customer cus: customers){
            cus.writeToFile(filename);
        }
        System.out.println("Đã ghi vào file customers.txt");
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
    public void writeFileProduct(){
        Product.writeProductsToFile("SieuThiMini\\product.txt");
    }
    public void writeFileCategory(){
        Category.writeCategoryFromFile("SieuThiMini\\category.txt");
    }
    public void writeFileSupplier(){
        Supplier.writeSupplierFromFile("SieuThiMini\\supplier.txt");
    }
    // Xuat danh sach cac san pham
    public void productDetail() {
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                "Product ID", "Name", "Price", "Quantity", "Category", "Supplier");
        System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                "-------------------", "-------------------", "-------------------",
                "-------------------", "-------------------", "-------------------");
        for (int i = 0; i < Product.getCnt(); i++) {
            Product.productList[i].getDetails();
        }
    }

    // Them san pham
    public void addProduct(Scanner scanner) {
        System.out.println("So phan tu ban muon them la: ");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            Product.addProduct();
        }
    }

    // Sua san pham
    public void updateProduct(Scanner scanner) {
        System.out.println("Nhap vao id san pham muon sua (Id co dang SP___).");
        String ud = scanner.nextLine();
        Product.upDateProduct(ud);
    }

    public void removeProduct(Scanner scanner) {
        System.out.println("Nhap vao id san pham muon xoa (Id co dang SP___).");
        String rm = scanner.nextLine();
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
    //Them loai san pham va nha cung cap
    public void addCategory(Scanner scanner){
        System.out.println("So loai san pham ban muon them la: ");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            Category.addCategory();
        }
    }
    public void addSupplier(Scanner scanner){
        System.out.println("So nha cung cap ban muon them la: ");
        int n = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < n; i++) {
            Supplier.addSupplier();
        }
    }
    //Xoa loai san pham va nha cung cap
    public void removeCategory(Scanner scanner){
        System.out.println("Nhap vao id loai san pham muon xoa (Id co dang CT___).");
        String rm = scanner.nextLine();
        Category.deleteCategory(rm);
    }
    public void removeSupplier(Scanner scanner){
        System.out.println("Nhap vao id nha cung cap muon xoa (Id co dang SL___).");
        String rm = scanner.nextLine();
        Supplier.deleteSupplier(rm);
    }
    //Sua loai san pham va nha cung cap
    public void updateCategory(Scanner scanner){
        System.out.println("Nhap vao id loai san pham muon sua (Id co dang CT___).");
        String ud = scanner.nextLine();
        Category.updateCategory(ud);
    }
    public void updateSupplier(Scanner scanner){
        System.out.println("Nhap vao id nha cung cap san pham muon sua (Id co dang SL___).");
        String ud = scanner.nextLine();
        Supplier.updateSupplier(ud);
    }



    /* Các thao tác cho HÓA ĐƠN START */
    public void xuatHoaDon(){
        for(Receipt rc: receipts){
            rc.inHoaDon();
        }
    }

    public void themHoaDon(Scanner scanner){
        receipts=Receipt.themhoadon(receipts, scanner, orderList);
    }

    public void xoaHoaDon(Scanner scanner){
        receipts=Receipt.xoahoadon(receipts, scanner);
    }

    public void suaHoaDon(Scanner scanner){
        System.out.print("Nhập id hóa đơn mà bạn muốn chỉnh sửa: ");
        receipts=Receipt.suahoadon(receipts, scanner.nextLine(), scanner);
    }

    public void timHoaDon(Scanner scanner){
        Receipt.locHoaDon(scanner, receipts);
    }

    public void ghihoadon(){
        String filename="SieuThiMini\\hoadon.txt";
        Receipt.xoaNoiDungFile(filename);
        Receipt.xoaNoiDungFilelichsugiaodich("SieuThiMini\\lichsugiaodich.txt");
        for(Receipt rc:receipts){
            rc.writeToFile(filename);
        }
        System.out.println("Đã ghi vào file hoadon.txt và lichsugiaodich.txt");
    }
    /* các thao tác cho hóa đơn END */
}
