
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

public class Order implements QLFile {
    public String orderId; // mã đơn hàng
    public String orderDate; // ngày lập đơn hàng
    public Customer customer; // khách hàng
    public Product[] product; // danh sách các sản phẩm
    public double totalAmount; // Tổng số tiền
    private static final double VAT = 0.1; // Thuế VAT 10%

    public Order() {
        customer = new Customer(); // khởi tạo khách hàng cho hóa đơn
    }

    public Order(String orderId, String orderDate, Customer customer, Product[] product) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.product = product;
        this.totalAmount = calculateTotalAmount();
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product[] getProductList() {
        return product;
    }

    public void setProductList(Product[] product) {
        this.product = product;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static double calculateVAT(double amount) {
        return amount * VAT;
    }

    public int tongQuantity() {
        int sum = 0;
        for (Product product : product) { // vòng lặp for each duyệt từng sản phẩm có kdl là Products
            if (product != null) {
                sum += product.getQuantity();
            }
        }
        return sum;
    }

    public double calculateTotalAmount() { // Tính tổng số tiền của sản phẩm
        double total = 0.0;
        for (Product product : product) { // vòng lặp for each duyệt từng sản phẩm có kdl là Products
            if (product != null) {
                total += product.getPrice();
            }
        }
        return total;
    }

    public static Order[] add(Scanner scanner, Order[] orderList) { // thêm đơn hàng
        System.out.print("Bạn muốn thêm bao nhiêu đơn hàng ?: ");
        int n = Integer.parseInt(scanner.nextLine());
        orderList = Arrays.copyOf(orderList, orderList.length + n);
        byte cnt=0; //Dùng để xuất cho người nhập dễ theo dõi là đang hỏi bao nhiêu sản phẩm của đơn hàng nào
        for (int i = orderList.length - n; i < orderList.length; i++) {
            cnt++;
            orderList[i] = new Order();
            System.out.print("\nĐơn Hàng Thứ "+(cnt)+" Bao nhiêu sản phẩm ?: "); // bởi vì 1 đơn hàng có nhiều sản phẩm
            orderList[i].product = new Product[Integer.parseInt(scanner.nextLine())];
            for (int j = 0; j < orderList[i].product.length; j++) {
                orderList[i].product[j] = new Product();
            }
            orderList[i].nhapdonhang(scanner, orderList[i].product, orderList,i);
        }
        return orderList;
    }

    
    //Kiem tra format id
    protected boolean checkIDOrder(String id){
        if(id.length()!=10){
            return false;
        }
        if(!id.startsWith("ORD")){
            return false;
        }
        return true;
    }
    //Kiem tra id co bi trung khong
    protected boolean checkDuplicateOrdID(String id, Order[] orderList, int vitrior) {
        for(int i=0;i<vitrior;i++){
            if (id!= null && orderList[i].getOrderId().equals(id)) {
                return false; // Trùng ID
                }
        }
        return true; // Không trùng
    }                           //vitrior để lưu vị trí hiện tại của cái đơn hàng trong mảng
                            //để khi nếu người dùng muốn chỉnh sửa đơn hàng, mà lại nhập lại mã cũ, thì vẫn lưu đc
    
    
    protected boolean checkx2IDpro(String id, Product[] product, int vitrior) { //kiểm tra trùng của id sản phẩm của đơn hàng
        for(int i=0;i<product.length;i++){
            if (id!= null && product[i].getProductID().equals(id) && vitrior!=i) {
                return false; // Trùng ID
                }
        }
        return true; // Không trùng
    } 

    protected boolean checknamepro(String name, Product[] product, int vitrior){
        for(int i=0;i<product.length;i++){
            if (name!= null && product[i].getName().equals(name) && vitrior!=i) {
                return false; // Trùng tên trong cùng 1 đơn hàng
                }
        }
        return true; // Không trùng
    }
     

    public void edit(Scanner scanner, Product[] product, Order[] orderList, int vitrior) {
        int i = 1, index = 0; // i là choice, index dùng để chỉnh sửa đúng vị trí của ds sản phẩm
        do {
            displayOrderDetails();
            System.out.printf("%55s╔═════════════════════════════════╗\n", " ");
            System.out.printf("%55s║   Bạn muốn chỉnh sửa cái nào?   ║\n", " ");
            System.out.printf("%55s╠═════════════════════════════════╣\n", " ");
            System.out.printf("%55s║   1. Mã Đơn Hàng%16s║\n", " ", " ");
            System.out.printf("%55s║   2. Ngày Lập Đơn Hàng%10s║\n", " ", " ");
            System.out.printf("%55s║   3. Mã Sản Phẩm%16s║\n", " ", " ");
            System.out.printf("%55s║   4. Tên Sản Phẩm%15s║\n", " ", " ");
            System.out.printf("%55s║   5. Giá Tiền%19s║\n", " ", " ");
            System.out.printf("%55s║   6. Mã Loại Sản Phẩm%11s║\n", " ", " ");
            System.out.printf("%55s║   7. Số Lượng%19s║\n", " ", " ");
            System.out.printf("%55s║   8. Mã Nhà Cung Cấp%12s║\n", " ", " ");
            System.out.printf("%55s║   9. Mã Khách Hàng%14s║\n", " ", " ");
            System.out.printf("%55s║  10. Tên Khách Hàng%13s║\n", " ", " ");
            System.out.printf("%55s║  11. Số Điện Thoại%14s║\n", " ", " ");
            System.out.printf("%55s║  12. Điểm Tích Lũy%14s║\n", " ", " ");
            System.out.printf("%55s║   0. Thoát và Lưu%15s║\n", " ", " ");
            System.out.printf("%55s╚═════════════════════════════════╝\n", " ");
            System.out.printf("%55s→ "," ");
            i = Byte.parseByte(scanner.nextLine());
            switch (i) {
                case 1:
                    System.out.printf("%40sNhập Mã Đơn Hàng MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    String id=scanner.nextLine();
                    while(!checkIDOrder(id)){
                        System.out.printf("%30sMã Đơn Hàng Phải Bắt Đầu Từ ORD... Và Có Độ Dài Là 10 Ký Tự, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    while(!checkDuplicateOrdID(id,orderList,vitrior)){
                        System.out.printf("%30sĐã Có Mã Đơn Hàng Này Trước Đó Rồi, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    setOrderId(id);
                    break;
                case 2:
                    System.out.printf("%40sNhập Ngày Lập Đơn Hàng MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    setOrderDate(scanner.nextLine());
                    break;
                case 3:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Mã Của Sản Phẩm Thứ Mấy?\n"," ");
                    System.out.printf("%40s→ "," ");
                    index = Integer.parseInt(scanner.nextLine());
                    while(index>product.length || index<=0){
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n"," ");
                        System.out.printf("%40s→ "," ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Mã Sản Phẩm MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    id=scanner.nextLine();
                    while(!Product.checkIDProduct(id)){
                        System.out.printf("%30sMã Sản Phẩm Phải Bắt Đầu Từ SP... Và Có Độ Dài Là 5 Ký Tự, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    while(!checkx2IDpro(id,product,index-1)){
                        System.out.printf("%30sĐã Có Mã Sản Phẩm Này Trước Đó Rồi, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    product[index - 1].setProductID(id);
                    break;
                case 4:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Tên Của Sản Phẩm Thứ Mấy?\n"," ");
                    System.out.printf("%40s→ "," ");
                    index = Integer.parseInt(scanner.nextLine());
                    while(index>product.length || index<=0){
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n"," ");
                        System.out.printf("%40s→ "," ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Tên Sản Phẩm MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    id=scanner.nextLine();
                    while(!checknamepro(id, product, index-1)){
                        System.out.printf("%30sĐã Có Tên Sản Phẩm Này Trước Đó Rồi, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    product[index - 1].setName(id);
                    break;
                case 5:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Giá Của Sản Phẩm Thứ Mấy?\n"," ");
                    System.out.printf("%40s→ "," ");
                    index = Integer.parseInt(scanner.nextLine());
                    while(index>product.length || index<=0){
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n"," ");
                        System.out.printf("%40s→ "," ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Giá Sản Phẩm MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    product[index - 1].setPrice(Integer.parseInt(scanner.nextLine()));
                    break;
                case 6:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Mã Loại Của Sản Phẩm Thứ Mấy?\n"," ");
                    System.out.printf("%40s→ "," ");
                    index = Integer.parseInt(scanner.nextLine());
                    while(index>product.length || index<=0){
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n"," ");
                        System.out.printf("%40s→ "," ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Mã Loại Sản Phẩm MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    id=scanner.nextLine();
                    while(!Category.checkIDCategory(id)){
                        System.out.printf("%30sMã Loại Sản Phẩm Phải Bắt Đầu Từ CT... Và Có Độ Dài Là 5 Ký Tự, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    product[index - 1].setCategoryId(id);
                    break;
                case 7:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Số Lượng Của Sản Phẩm Thứ Mấy?\n"," ");
                    System.out.printf("%40s→ "," ");
                    index = Integer.parseInt(scanner.nextLine());
                    while(index>product.length || index<=0){
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n"," ");
                        System.out.printf("%40s→ "," ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Số Lượng Sản Phẩm MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    product[index - 1].setQuantity(Integer.parseInt(scanner.nextLine()));
                    break;
                case 8:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Mã Nhà Cung Cấp Của Sản Phẩm Thứ Mấy?\n"," ");
                    System.out.printf("%40s→ "," ");
                    index = Integer.parseInt(scanner.nextLine());
                    while(index>product.length || index<=0){
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n"," ");
                        System.out.printf("%40s→ "," ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Mã Nhà Cung Cấp Sản Phẩm MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    id=scanner.nextLine();
                    while(!Supplier.checkIDSupplier(id)){
                        System.out.printf("%30sMã Nhà Cung Cấp Phải Bắt Đầu Từ SL... Và Có Độ Dài Là 5 Ký Tự, Hãy Nhập Lại\n"," ");
                        System.out.printf("%30s→ "," ");
                        id=scanner.nextLine();
                    }
                    product[index - 1].setSupplierId(id);
                    break;
                case 9:
                    System.out.printf("%40sNhập Mã Khách Hàng MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    customer.setCustomerID(Integer.parseInt(scanner.nextLine()));
                    break;
                case 10:
                    System.out.printf("%40sNhập Tên Khách Hàng MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    customer.setName(scanner.nextLine());
                    break;
                case 11:
                    System.out.printf("%40sNhập Số Điện Thoại MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    customer.setContactNumber(scanner.nextLine());
                    break;
                case 12:
                    System.out.printf("%40sNhập Điểm Tích Lũy MỚI\n"," ");
                    System.out.printf("%40s→ "," ");
                    customer.setLoyaltyPoints(Integer.parseInt(scanner.nextLine()));
                    break;
                case 0:
                    System.out.println("Đã Cập Nhật Giá Trị Mới");
                    break;
                default:
                    System.out.println("Lựa chọn khong hợp lệ, hãy nhập lại!");
                    break;
            }
        } while (i != 0);
    }

    public static Order[] xoa(Scanner scanner, Order[] orderList) {
        System.out.print("Bạn muốn xóa đơn hàng nào ? (Nhập mã đơn hàng): ");
        boolean flag = false; // Tạo lính canh để kiểm tra nếu sau khi duyệt mà nó còn false thì sẽ cho nhập
                              // lại cho đúng
        byte choice=0, so_lan_thu=0;
        do {
            so_lan_thu++;
            String temp = scanner.nextLine();
            int pos = 0; // Vị trí của đơn hàng trong danh sách
            for (int i = 0; i < orderList.length; i++) {
                if (orderList[i].getOrderId().equals(temp)) {
                    pos = i;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                if (pos == orderList.length - 1) {
                    orderList = Arrays.copyOf(orderList, orderList.length - 1);
                    System.out.println("Đã xóa đơn hàng");
                } else {
                    for (int i = pos; i < orderList.length - 1; i++) {
                        orderList[i] = orderList[i + 1];
                    }
                    orderList = Arrays.copyOf(orderList, orderList.length - 1);
                    System.out.println("Đã xóa đơn hàng");
                }
                break;
            }
            if(so_lan_thu>2){
                System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                break;
            }
            System.out.print("Mã Đơn Hàng Mà Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Xóa Đơn Hàng Không?\n "
            +"1.Có    0.Không");
            do{
                System.out.print("\n→ ");
                choice=Byte.parseByte(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Vậy Hãy Nhập Lại Mã Đơn Hàng Chính Xác");
                        System.out.print("→ ");
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
        } while (!flag);
        return orderList;
    }

    public void displayOrderDetails() {
        System.out.printf("\n\n%40s╔════════════════════ CHI TIẾT ĐƠN HÀNG ════════════════════╗\n", " ");
        System.out.printf("%40s║%-20sMã đơn hàng:%-27s║\n", "", "", orderId);
        System.out.printf("%40s║   Ngày lập đơn:%41s  ║\n", "", orderDate);
        System.out.printf("%40s║   Mã Khách Hàng: %38s   ║\n", " ", customer.getCustomerID());
        System.out.printf("%40s║   Tên Khách Hàng: %37s   ║\n", " ", customer.getName());
        System.out.printf("%40s║   Số Điện Thoại: %38s   ║\n", " ", customer.getContactNumber());
        System.out.printf("%40s║   Điểm Tích Lũy: %38s   ║\n", " ", customer.getLoyaltyPoints());
        System.out.printf("%40s║───────────────────────────────────────────────────────────║\n", "");
        System.out.printf("%40s║   Mã     Tên sản phẩm     SL        Giá          Tổng     ║\n", " ");

        if (product != null) {
            for (int j = 0; j < product.length; j++) {
                System.out.printf("%40s║                                                           ║\n", "");
                System.out.printf("%40s║%d %-8s%-17s%,-8d%,-12d%,-12d║\n",
                        "",
                        (j + 1),
                        product[j].getProductID(),
                        product[j].getName(),
                        product[j].getQuantity(),
                        product[j].getPrice(),
                        (product[j].getPrice() * product[j].getQuantity()));
            }
        }
        System.out.printf("%40s║───────────────────────────────────────────────────────────║\n", "");
        System.out.printf("%40s║  Thành tiền (chưa VAT): %31.2f   ║\n", "", calculateTotalAmount());
        System.out.printf("%40s║───────────────────────────────────────────────────────────║\n", "");
        System.out.printf("%40s║  TỔNG THANH TOÁN (CÓ VAT): %28.2f   ║\n", "",
                (calculateTotalAmount() + calculateVAT(calculateTotalAmount())));
        System.out.printf("%40s╚═══════════════════════════════════════════════════════════╝\n", "");
    }

    public static void loc(Scanner scanner, Order[] orderList) {
        System.out.println();
        System.out.printf("%20s╔═════════════════════════════════════════════════════════════════╗\n", "");
        System.out.printf("%20s║                        TÌM KIẾM ĐƠN HÀNG                        ║\n","");
        System.out.printf("%20s║      Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí)       ║\n","");
        System.out.printf("%20s╚═════════════════════════════════════════════════════════════════╝\n", "");

        System.out.print("Mã đơn hàng: ");
        String orderID = scanner.nextLine();
        if (orderID.isEmpty())
            orderID = null;

        System.out.print("Ngày đặt hàng (dd/mm/yy): ");
        String orderDate = scanner.nextLine();
        if (orderDate.isEmpty())
            orderDate = null;

        System.out.print("Nhập Mã Khách Hàng: ");
        String in = scanner.nextLine();
        int cusID;
        if (in.isEmpty())
            cusID = 0;
        else {
            cusID = Integer.parseInt(in);
        }

        System.out.print("Nhập Tên Khách Hàng: ");
        String cusName = scanner.nextLine();
        if (cusName.isEmpty())
            cusName = null;

        System.out.print("Nhập Số Điện Thoại: ");
        String phone = scanner.nextLine();
        if (phone.isEmpty())
            phone = null;

        System.out.print("Nhập Điểm Tích Lũy: ");
        in = scanner.nextLine();
        int point;
        if (in.isEmpty())
            point = 0;
        else {
            point = Integer.parseInt(in);
        }

        System.out.print("Mã sản phẩm: ");
        String productID = scanner.nextLine();
        if (productID.isEmpty())
            productID = null;

        System.out.print("Tên sản phẩm: ");
        String productName = scanner.nextLine();
        if (productName.isEmpty())
            productName = null;

        System.out.print("Giá tiền của 1 sản phẩm: ");
        int price;
        in = scanner.nextLine();
        if (in.isEmpty())
            price = 0;
        else {
            price = Integer.parseInt(in);
        }

        System.out.print("Loại sản phẩm: ");
        String productCatelo = scanner.nextLine();
        if (productCatelo.isEmpty())
            productCatelo = null;

        System.out.print("Số lượng: ");
        int productQuanti;
        in = scanner.nextLine();
        if (in.isEmpty())
            productQuanti = 0;
        else {
            productQuanti = Integer.parseInt(in);
        }

        System.out.print("Nhà cung cấp: ");
        String suppli = scanner.nextLine();
        if (suppli.isEmpty())
            suppli = null;

        Order[] filteredOrders = new Order[orderList.length]; // Tạo mảng với kích thước tối đa là độ dài của mảng
                                                              // orders
        int count = 0; // Biến đếm số đơn hàng thỏa mãn điều kiện

        for (Order order : orderList) {
            if (isOrderMatch(order, orderID, orderDate, productID, productName, price, productCatelo, productQuanti,
                    suppli, order.product, cusID, cusName, phone, point)) {
                filteredOrders[count++] = order; // Thêm đơn hàng vào mảng
            }
        }
        Order[] result = new Order[count];
        result = Arrays.copyOf(filteredOrders, count);

        if (result.length == 0) {
            System.out.println("Không tìm thấy đơn hàng nào khớp với tiêu chí.");
        } else {
            System.out.println("\nDanh sách đơn hàng:\n");
            for (Order order : result) {
                order.displayOrderDetails();
            }
        }
    }

    public static boolean isOrderMatch(Order order, String orderID, String orderDate, String productID,
            String productName, int price, String productCatelo, int productQuanti, String suppli, Product[] product,
            int cusID, String cusName, String phone, int point) {
        // Kiểm tra nếu người dùng KO bỏ qua tiêu chí (!=null, !=0) thì kiểm tra tiếp
        // nếu KO trùng khớp (!equal(false)) thì nó sẽ trả về giá trị false để hàm if ở
        // trên kia kiểm trả và vì hàm if trên đó chỉ kiểm tra true nên sẽ không thêm
        // đơn hàng vào mảng
        if (orderID != null && !order.getOrderId().equals(orderID))
            return false;
        if (orderDate != null && !order.getOrderDate().equals(orderDate))
            return false;
        if (cusID != 0 && order.customer.getCustomerID() != cusID)
            return false;
        if (cusName != null && !order.customer.getName().equalsIgnoreCase(cusName))
            return false;//
        if (phone != null && !order.customer.getContactNumber().equals(phone))
            return false;//
        if (point != 0 && order.customer.getLoyaltyPoints() != point)
            return false;

        boolean san_pham_thu_may = false;
        for (int i = 0; i < product.length; i++) {
            // Nếu 1 sản phẩm trong đơn hàng có tiêu chí thì nó sẽ break để kiểm đơn hàng
            // tiếp theo luôn để tiết kiệm thời gian
            // Và nếu ko có 1 tiêu chí thì nó sẽ continute qua sản phẩm khác,khỏi phải kiểm
            // tra các tiêu chí khác để tiết kiệm thời gian
            if (productID != null && !order.product[i].getProductID().equalsIgnoreCase(productID))
                continue;
            if (productName != null && !order.product[i].getName().equalsIgnoreCase(productName))
                continue;
            if (price != 0 && order.product[i].getPrice() != price)
                continue;
            if (productCatelo != null && !order.product[i].getCategoryId().equalsIgnoreCase(productCatelo))
                continue;
            if (productQuanti != 0 && order.product[i].getQuantity() != productQuanti)
                continue;
            if (suppli != null && !order.product[i].getSupplierId().equalsIgnoreCase(suppli))
                continue;
            san_pham_thu_may = true;
            break;
        }
        if (!san_pham_thu_may)
            return false;
        return true;
    }

    public static void statisticalOrders(Scanner scanner, Order[] orderList) {
        // Menu lựa chọn
        System.out.println("\n--- MENU THỐNG KÊ ĐƠN HÀNG ---");
        System.out.println("1. Thống kê đơn hàng theo thời gian (ngày/tháng/năm) mới, cũ");
        System.out.println("2. Thống kê đơn hàng theo tổng số tiền giảm dần, tăng dần");
        System.out.println("3. Thống kê đơn hàng theo quantity giảm dần, tăng dần");
        System.out.println("4. Thống kê đơn hàng theo mã đơn hàng tăng dần, giảm dần");
        System.out.print("Chọn lựa chọn (1-4): ");

        int choice = Integer.parseInt(scanner.nextLine());

        // Xử lý theo từng lựa chọn
        switch (choice) {
            case 1:
                sapxepngay(orderList); // xếp theo mới nhất trước
                break;

            case 2:
                // Sắp xếp theo tổng tiền (giảm dần và tăng dần)
                sapxeptien(orderList);
                break;

            case 3:
                // Sắp xếp theo quantity (giảm dần và tăng dần)
                sapxepSL(orderList);
                break;

            case 4:
                // Sắp xếp theo mã đơn hàng (alpha beta)
                Arrays.sort(orderList, Comparator.comparing(Order::getOrderId));
                System.out.printf("%-15s%-15s\n", "Mã Đơn Hàng", "Tổng Tiền");
                for (Order order : orderList) {
                    System.out.printf("%-15s%-15.2f\n", order.getOrderId(), order.getTotalAmount());
                }
                break;

            default:
                System.out.println("Lựa chọn không hợp lệ.");
                break;
        }
    }

    public static void sapxepngay(Order[] orderList) {
        int[] a = new int[orderList.length];
        int[] vitri = new int[orderList.length];
        int i = 0;
        for (Order or : orderList) {
            String[] part = or.getOrderDate().split("/");
            a[i] = Integer.parseInt(part[0]) * 1000000 + Integer.parseInt(part[1]) * 10000 + Integer.parseInt(part[2]);
            vitri[i] = i;
            i++;
        }
        for (int j = 0; j < a.length - 1; j++) {
            for (int y = j + 1; y < a.length; y++) {
                if (a[j] % 10000 > a[y] % 10000) {
                    int temp = a[j];
                    a[j] = a[y];
                    a[y] = temp;
                    temp = vitri[j];
                    vitri[j] = vitri[y];
                    vitri[y] = temp;
                }
            }
        }
        /*
         * for(int j=0;j<a.length;j++){
         * System.out.printf("%d %d\n",a[j],vitri[j]);
         * }
         */
        for (int j = 0; j < a.length - 1; j++) {
            int y = j + 1;
            while (a[j] % 10000 == a[y] % 10000 && y < a.length) {
                if (a[j] % 1000000 / 10000 > a[y] % 1000000 / 10000) {
                    int temp = a[j];
                    a[j] = a[y];
                    a[y] = temp;
                    temp = vitri[j];
                    vitri[j] = vitri[y];
                    vitri[y] = temp;
                }
                y++;
            }
        }
        /*
         * System.out.println("thang");
         * for(int j=0;j<a.length;j++){
         * System.out.printf("%d %d\n",a[j],vitri[j]);
         * }
         */
        for (int j = 0; j < a.length - 1; j++) {
            int y = j + 1;
            while (a[j] % 1000000 == a[y] % 1000000 && y < a.length) {
                if (a[j] / 1000000 > a[y] / 1000000) {
                    int temp = a[j];
                    a[j] = a[y];
                    a[y] = temp;
                    temp = vitri[j];
                    vitri[j] = vitri[y];
                    vitri[y] = temp;
                }
                y++;
            }
        }
        /*
         * System.out.println("day");
         * for(int j=0;j<a.length;j++){
         * System.out.printf("%d %d\n",a[j],vitri[j]);
         * }
         */System.out.printf("%-15s%-20s\n", "Mã Đơn Hàng", "Ngày Lập Đơn");
        for (int j = 0; j < orderList.length; j++) {
            System.out.printf("%-15s%-20s\n", orderList[vitri[j]].getOrderId(), orderList[vitri[j]].getOrderDate());
        }
    }

    public static void sapxeptien(Order[] orderList) {
        double[] a = new double[orderList.length];
        int[] vitri = new int[orderList.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = orderList[i].calculateTotalAmount();
            vitri[i] = i;
        }
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    double temp = a[j];
                    a[j] = a[i];
                    a[i] = temp;
                    int t = vitri[j];
                    vitri[j] = vitri[i];
                    vitri[i] = t;
                }
            }
        }
        System.out.printf("%-15s%-15s\n", "Tổng Tiền", "Mã Đơn Hàng");
        for (int i = 0; i < orderList.length; i++) {
            System.out.printf("%-15.2f%-15s\n", orderList[vitri[i]].calculateTotalAmount(),
                    orderList[vitri[i]].getOrderId());
        }
    }

    public static void sapxepSL(Order[] orderList) {
        int[] a = new int[orderList.length];
        int[] vitri = new int[orderList.length];
        for (int i = 0; i < a.length; i++) {
            a[i] = orderList[i].tongQuantity();
            vitri[i] = i;
        }
        for (int i = 0; i < a.length - 1; i++) {
            for (int j = i + 1; j < a.length; j++) {
                if (a[i] > a[j]) {
                    int temp = a[j];
                    a[j] = a[i];
                    a[i] = temp;
                    int t = vitri[j];
                    vitri[j] = vitri[i];
                    vitri[i] = t;
                }
            }
        }
        System.out.printf("%-15s%-20s%-15s\n", "Số Lượng", "Mã Đơn Hàng", "Tổng Tiền");
        for (int i = 0; i < orderList.length; i++) {
            System.out.printf("%-15d%-20s%-15.2f\n", orderList[vitri[i]].tongQuantity(),
                    orderList[vitri[i]].getOrderId(), orderList[vitri[i]].calculateTotalAmount());
        }
    }

    public void nhapdonhang(Scanner scanner, Product[] product, Order[] orderList, int vitrior) {
        byte dem = 0;
        System.out.print("Nhập Mã Đơn Hàng: ");
        String id=scanner.nextLine();
        while(!checkIDOrder(id)){
            System.out.printf("%30sMã Đơn Hàng Phải Bắt Đầu Từ ORD... Và Có Độ Dài Là 10 Ký Tự, Hãy Nhập Lại\n"," ");
            System.out.printf("%30s→ "," ");
            id=scanner.nextLine();
        }
        while(!checkDuplicateOrdID(id,orderList,vitrior)){
            System.out.printf("%30sĐã Có Mã Đơn Hàng Này Trước Đó Rồi, Hãy Nhập Lại\n"," ");
            System.out.printf("%30s→ "," ");
            id=scanner.nextLine();
        }
        setOrderId(id);
        
        System.out.print("Nhập Ngày Lập Đơn Hàng (dd/mm/yy): ");
        setOrderDate(scanner.nextLine());
        System.out.print("Nhập Mã Khách Hàng: ");
        customer.setCustomerID(Integer.parseInt(scanner.nextLine()));
        System.out.print("Nhập Tên Khách Hàng: ");
        customer.setName(scanner.nextLine());
        System.out.print("Nhập Số Điện Thoại: ");
        customer.setContactNumber(scanner.nextLine());
        System.out.print("Nhập Điểm Tích Lũy: ");
        customer.setLoyaltyPoints(Integer.parseInt(scanner.nextLine()));
        for (Product pro : product) {
            dem++;
            System.out.println();
            System.out.print("Nhập Mã Sản Phẩm Thứ " + dem + ": ");
            pro.setProductID(scanner.nextLine());
            System.out.print("Nhập Tên Sản Phẩm: ");
            pro.setName(scanner.nextLine());
            System.out.print("Nhập Giá Tiền: ");
            pro.setPrice(Integer.parseInt(scanner.nextLine()));
            System.out.print("Nhập Loại Sản Phẩm: ");
            pro.setCategoryId(scanner.nextLine());
            System.out.print("Nhập Số Lượng: ");
            pro.setQuantity(Integer.parseInt(scanner.nextLine()));
            System.out.print("Nhập Nhà Cung Cấp: ");
            pro.setSupplierId(scanner.nextLine());
        }
    }

    @Override
    public Order[] readFromFile(String filePath) {
        /* TẢI DANH SÁCH ĐƠN HÀNG - Kiệt */
        Order[] orderList;
        int i, n;
        n = 0;
        filePath = "C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\donhang.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String Line;
            while ((Line = br.readLine()) != null) {
                n++;
            }
            br.close();
            Line = Line + "line"; // Để cho nó không hiện broblem isn't used nữa :)))
        } catch (IOException e) {
            e.printStackTrace();
        } // Đoạn này đọc số dòng để lưu số lượng đơn hàng có trong danh sách

        orderList = new Order[n];
        i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String Line;
            // Cấu trúc của file gồm 3 thành phần: thông tin đơn hàng; thông tin khách hàng;
            // thông tin sản phẩm
            // trong thông tin khách hàng, mỗi thuộc tính cách nhau bằng 1 dấu ","
            // trong thông tin sản phẩm, cũng cách nhau 1 dấu "," và do 1 đơn hàng có nhiều
            // sản phẩm khác nhau
            // nên trong 1 hóa đơn sẽ chứa 1 mảng các sản phẩm, các sản phẩm và gồm thông
            // tin của nó cách nhau bằng 1 dấu "|"
            while ((Line = br.readLine()) != null) {
                orderList[i] = new Order();
                String[] parts = Line.split(";");
                orderList[i].setOrderId(parts[0]);
                orderList[i].setOrderDate(parts[1]);
                String[] customerParts = parts[2].split(","); // lưu các phần của khách hàng như id, tên, ...
                orderList[i].customer.setCustomerID(Integer.parseInt(customerParts[0]));
                orderList[i].customer.setLoyaltyPoints(Integer.parseInt(customerParts[1]));
                orderList[i].customer.setName(customerParts[2]);
                orderList[i].customer.setContactNumber(customerParts[3]);
                String[] productParts = parts[3].split("\\|"); // tách các sản phẩm cùng thông tin của nó ra
                orderList[i].product = new Product[productParts.length];
                for (int j = 0; j < productParts.length; j++) {
                    orderList[i].product[j] = new Product();
                    String[] ThongTinProduct = productParts[j].split(",");// tác các phần của sản phẩm như id, tên,...
                    orderList[i].product[j].setProductID(ThongTinProduct[0]);
                    orderList[i].product[j].setName(ThongTinProduct[1]);
                    orderList[i].product[j].setPrice(Integer.parseInt(ThongTinProduct[2]));
                    orderList[i].product[j].setCategoryId(ThongTinProduct[3]);
                    orderList[i].product[j].setQuantity(Integer.parseInt(ThongTinProduct[4]));
                    orderList[i].product[j].setSupplierId(ThongTinProduct[5]);
                }
                i++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void writeToFile(String filePath) {

    }
}
