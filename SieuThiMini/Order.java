
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Random;
import java.util.Date;

public class Order implements QLFile {
    public String orderId; // mã đơn hàng
    public String orderDate; // ngày lập đơn hàng
    public Customer customer; // khách hàng
    public Product[] product; // danh sách các sản phẩm

    public double totalAmount; // Tổng số tiền của đơn hàng
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public static double getVat() {
        return VAT;
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

    public double calculateTotalAmount() { // Tính tổng số tiền của sản phẩm
        double total = 0.0;
        for (Product product : product) { // vòng lặp for each duyệt từng sản phẩm có kdl là Products
            if (product != null) {
                total += product.getPrice() * product.getQuantity();
            }
        }
        return total;
    }

    public static double calculateVAT(double amount) {
        return amount * VAT;
    }

    public Order getOrderbyID(String id) { // trả ra 1 bộ dữ liệu trong file (1 dòng)
        Order[] orderList = readFromFile("donhang.txt");
        int tmp = 0;
        boolean flag = false;
        for (int i = 0; i < orderList.length; i++) {
            if (orderList[i] != null && orderList[i].getOrderId().equals(id)) {
                tmp = i;
                flag = true;
                break;
            }
        }
        if (flag == false) {
            return null;
        } else {
            return orderList[tmp];
        }
    }

    public String generateRandomString(int length, String charSet) { // hàm tạo chuỗi ngẫu nhiên từ chat GPT
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());
            randomString.append(charSet.charAt(index));
        }

        return randomString.toString();
    }

    public void nhapdonhang(Scanner scanner, Product[] product, Order[] orderList) {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 7;
        setOrderId("ORD" + generateRandomString(length, charSet)); // Tạo mã đơn hàng ngẫu nhiên, không cần nhập

        // ngay lap don hang
        Date date = new Date(); // khởi tạo ngày lập đơn hàng là ngày hiện tại
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(date);
        setOrderDate(formattedDate);

        System.out.print("Nhập Mã Khách Hàng: ");
        int cusId = Integer.parseInt(scanner.nextLine());
        while (customer.getCustomerById(cusId) == null) {
            System.out.println("Mã khách hàng này không tồn tại, hãy nhập lại!");
            cusId = Integer.parseInt(scanner.nextLine());
        }
        customer = new Customer(
                customer.getCustomerById(cusId).getCustomerID(),
                customer.getCustomerById(cusId).getName(),
                customer.getCustomerById(cusId).getContactNumber(),
                customer.getCustomerById(cusId).getLoyaltyPoints());

        String id; // mã sp
        int sl = 0;
        for (int I = 0; I < product.length; I++) {
            System.out.println();
            System.out.print("Nhập Mã Sản Phẩm Thứ " + (I + 1) + ": ");
            id = scanner.nextLine();
            if (!Product.checkIDProduct(id)) {
                System.out.printf("%30sMã Sản Phẩm Phải Bắt Đầu Từ SP... Và Có Độ Dài Là 5 Ký Tự, Hãy Nhập Lại\n", " ");
                I--;
                continue;
            }

            if (Product.getProductById(id).getQuantity() == 0) {
                System.out.println("Sản phẩm này đã hết hàng, hãy nhập lại mã sản phẩm khác");
                I--;
                continue;
            }

            // không dùng product[I]=Produc.getbyID(id) bởi vì nó sẽ tham chiếu đến địa chỉ
            // này,
            // nó sẽ làm thay đổi lun cái mảng của ngta chứ kp cái mảng sp riêng trong đơn
            // hàng này,
            // chứ nó không sao chép, nên là khởi tạo lại như thế này mới đúng
            if (checkx2IDpro(id, product, I)) { // Nếu không trùng thì sẽ tạo mới, còn không thì ko tạo
                product[I] = new Product(Product.getProductById(id).getProductID(),
                        Product.getProductById(id).getName(),
                        Product.getProductById(id).getPrice(),
                        Product.getProductById(id).getQuantity(),
                        Product.getProductById(id).getCategoryId(),
                        Product.getProductById(id).getSupplierId());
            }

            System.out.print("Nhập Số Lượng Mua: ");
            sl = Integer.parseInt(scanner.nextLine());
            while (sl > Product.getProductById(id).getQuantity() || sl < 0) {
                if (sl < 0) {
                    System.out.println("Số lượng bạn nhập không được < 0, hãy nhập lại");
                }
                if (sl > Product.getProductById(id).getQuantity()) {
                    System.out.println(
                            "Số lượng " + sl + " vượt quá " + product[I].getQuantity() + " trong kho, hãy nhập lại: ");
                }
                sl = Integer.parseInt(scanner.nextLine());
            }
            Product.getProductById(id).setQuantity(Product.getProductById(id).getQuantity() - sl);
            Product.writeProductsToFile("product.txt");
            if (!checkx2IDpro(id, product, I)) {
                System.out
                        .println("Mã sản phẩm mà bạn nhập đã có trước đó rồi, tiến hành cộng dồn số lượng mua vào....");
                I--;
                System.out.println("Hiện tại đang có : " + (product[I].getQuantity() + sl));
                product[I].setQuantity(product[I].getQuantity() + sl);
                continue;
            }
            product[I].setQuantity(sl);
        }
    }

    public static Order[] add(Scanner scanner, Order[] orderList) { // thêm đơn hàng
        System.out.print("Bạn muốn thêm bao nhiêu đơn hàng ?: ");
        int n = Integer.parseInt(scanner.nextLine());
        orderList = Arrays.copyOf(orderList, orderList.length + n);
        byte cnt = 0; // Dùng để xuất cho người nhập dễ theo dõi là đang hỏi bao nhiêu sản phẩm của
                      // đơn hàng nào
        for (int i = orderList.length - n; i < orderList.length; i++) {
            cnt++;
            orderList[i] = new Order();
            System.out.print("\nĐơn Hàng Thứ " + (cnt) + " Bao nhiêu sản phẩm ?: "); // bởi vì 1 đơn hàng có nhiều sản
                                                                                     // phẩm
            orderList[i].product = new Product[Integer.parseInt(scanner.nextLine())];
            for (int j = 0; j < orderList[i].product.length; j++) {
                orderList[i].product[j] = new Product();
            }
            orderList[i].nhapdonhang(scanner, orderList[i].product, orderList);
        }
        return orderList;
    }

    // Kiem tra format id
    protected boolean checkIDOrder(String id) {
        if (id.length() != 10) {
            return false;
        }
        if (!id.startsWith("ORD")) {
            return false;
        }
        return true;
    }

    // Kiem tra id co bi trung khong
    protected boolean checkDuplicateOrdID(String id, Order[] orderList, int vitrior) {
        for (int i = 0; i < vitrior; i++) {
            if (id != null && orderList[i].getOrderId().equals(id)) {
                return false; // Trùng ID
            }
        }
        return true; // Không trùng
    } // vitrior để lưu vị trí hiện tại của cái đơn hàng trong mảng
      // để khi nếu người dùng muốn chỉnh sửa đơn hàng, mà lại nhập lại mã cũ, thì vẫn
      // lưu đc

    protected boolean checkx2IDpro(String id, Product[] product, int vitrior) { // kiểm tra trùng của id sản phẩm của
                                                                                // đơn hàng
        for (int i = 0; i < vitrior; i++) {
            if (id != null && product[i].getProductID().equals(id)) {
                return false; // Trùng ID
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
            System.out.printf("%55s║   2. Mã Sản Phẩm%16s║\n", " ", " ");
            System.out.printf("%55s║   3. Mã Khách Hàng%14s║\n", " ", " ");
            System.out.printf("%55s║   0. Thoát và Lưu%15s║\n", " ", " ");
            System.out.printf("%55s╚═════════════════════════════════╝\n", " ");
            System.out.printf("%55s→ ", " ");
            i = Byte.parseByte(scanner.nextLine());
            switch (i) {
                case 1:
                    System.out.printf("%40sNhập Mã Đơn Hàng MỚI\n", " ");
                    System.out.printf("%40s→ ", " ");
                    String id = scanner.nextLine();
                    while (!checkIDOrder(id) || !checkDuplicateOrdID(id, orderList, orderList.length)) {
                        if (id.equals(orderId)) {
                            break;
                        }
                        if (!checkIDOrder(id)) {
                            System.out.printf(
                                    "%30sMã Đơn Hàng Phải Bắt Đầu Từ ORD... Và Có Độ Dài Là 10 Ký Tự, Hãy Nhập Lại\n",
                                    " ");
                            System.out.printf("%30s→ ", " ");
                        }
                        if (!checkDuplicateOrdID(id, orderList, orderList.length)) {
                            System.out.printf("%30sĐã Có Mã Đơn Hàng Này Trước Đó Rồi, Hãy Nhập Lại\n", " ");
                            System.out.printf("%30s→ ", " ");
                        }
                        id = scanner.nextLine();
                    }
                    setOrderId(id);
                    break;
                case 2:
                    System.out.printf("%40sBạn Muốn Chỉnh Sửa Mã Của Sản Phẩm Thứ Mấy?\n", " ");
                    System.out.printf("%40s→ ", " ");
                    index = Integer.parseInt(scanner.nextLine());
                    while (index > product.length || index <= 0) {
                        System.out.printf("%40sKhông hợp lệ, hãy nhập lại\n", " ");
                        System.out.printf("%40s→ ", " ");
                        index = Integer.parseInt(scanner.nextLine());
                    }
                    System.out.printf("%40sNhập Mã Sản Phẩm MỚI\n", " ");
                    System.out.printf("%40s→ ", " ");
                    id = scanner.nextLine();
                    while (!Product.checkIDProduct(id) || !checkx2IDpro(id, product, product.length)
                            || Product.getProductById(id).getQuantity() == 0
                            || Product.getProductById(id).getCategoryId().equals("SLOOO")) {
                        if (id.equals(product[index - 1].getProductID())) {
                            break;
                        }
                        if (!Product.checkIDProduct(id)) {
                            System.out.printf(
                                    "%30sMã Sản Phẩm Phải Bắt Đầu Từ SP... Và Có Độ Dài Là 5 Ký Tự, Hãy Nhập Lại\n",
                                    " ");
                            System.out.printf("%30s→ ", " ");
                        }
                        if (!checkx2IDpro(id, product, product.length)) { // dò tất cả sp trong đơn hàng xem có trùng mã
                                                                          // kh
                            System.out.printf("%30sĐã Có Mã Sản Phẩm Này Trước Đó Rồi, Hãy Nhập Lại\n", " ");
                            System.out.printf("%30s→ ", " ");
                        }
                        if (Product.getProductById(id).getQuantity() == 0
                                && !Product.getProductById(id).getCategoryId().equals("SLOOO")) {
                            System.out.printf("%30Mã sản phẩm này hiện tại không có (số lượng = 0), hãy nhập lại\n",
                                    " ");
                            System.out.printf("%30s→ ", " ");
                            // Nếu sản phẩm này hết hàng, (phải bỏ qua trường hợp là dòng đầu trong file,
                            // nếu thế thì nó sẽ xuất tiếp dòng dưới)
                        }
                        if (Product.getProductById(id).getCategoryId().equals("SLOOO")) { // nếu là dòng mặc định trong
                                                                                          // file product của Bảo thì
                                                                                          // bắt nhập lại
                            System.out.printf("%30Sản phẩm này hiện không tồn tại trong kho, hãy nhập lại\n", " ");
                            System.out.printf("%30s→ ", " ");
                        }
                        id = scanner.nextLine();
                    }

                    Product.getProductById(product[index - 1].getProductID())
                            .setQuantity(Product.getProductById(product[index - 1].getProductID()).getQuantity()
                                    + product[index - 1].getQuantity());// Đổi sản phẩm thì phải trả lại, mà trả lại thì
                                                                        // là tăng số lượng lại vào trong kho

                    product[index - 1].setProductID(id);
                    product[index - 1].setName(Product.getProductById(id).getName());
                    product[index - 1].setPrice(Product.getProductById(id).getPrice());
                    product[index - 1].setCategoryId(Product.getProductById(id).getCategoryId());
                    product[index - 1].setSupplierId(Product.getProductById(id).getSupplierId());

                    System.out.printf("%40sĐã Chỉnh Sửa Mã Sản Phẩm Thành Công, Hãy Nhập Lại Số Lượng Mua Mới\n", " ");
                    System.out.printf("%40s→ ", " ");
                    int sl = Integer.parseInt(scanner.nextLine());
                    while (sl < 0 || sl > Product.getProductById(id).getQuantity()) {
                        if (sl < 0) {
                            System.out.printf("%30sKhông hợp lệ, hãy nhập lại\n", " ");
                            System.out.printf("%30s→ ", " ");
                        }
                        if (sl > Product.getProductById(id).getQuantity()) {
                            System.out.printf("%30Số lượng lớn hơn trong kho (%d), hãy nhập lại\n", " ",
                                    Product.getProductById(id).getQuantity());
                            System.out.printf("%30s→ ", " ");
                        }
                        sl = Integer.parseInt(scanner.nextLine());
                    }
                    // Giảm số lượng trong kho lại sau khi nhập
                    Product.getProductById(id).setQuantity(Product.getProductById(id).getQuantity() - sl);
                    Product.writeProductsToFile("product.txt"); // cập nhật, ghi lại vào file
                    product[index - 1].setQuantity(sl); // set số lượng mua mới
                    break;
                case 3:
                    System.out.printf("%40sNhập Mã Khách Hàng MỚI\n", " ");
                    System.out.printf("%40s→ ", " ");
                    int cusId = Integer.parseInt(scanner.nextLine());
                    if (customer.getCustomerById(cusId) == null) {
                        customer.setCustomerID(0);
                        customer.setLoyaltyPoints(0);
                        customer.setName("NONE");
                        customer.setContactNumber("NONE");
                    } else {
                        customer = customer.getCustomerById(cusId);
                    }
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
        byte choice = 0, so_lan_thu = 0;
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
            if (so_lan_thu > 2) {
                System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                break;
            }
            System.out.print(
                    "Mã Đơn Hàng Mà Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Xóa Đơn Hàng Không?\n "
                            + "1.Có    0.Không");
            do {
                System.out.print("\n→ ");
                choice = Byte.parseByte(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Vậy Hãy Nhập Lại Mã Đơn Hàng Chính Xác");
                        System.out.print("→ ");
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
        } while (!flag);
        return orderList;
    }

    public void displayOrderDetails() {
        System.out.printf(
                "%20s╔═════════════════╦══════════════════════╦═════════════════════╦══════════════════╦═══════════════╦═════════════════╗\n",
                " ");
        System.out.printf(
                "%20s║   Mã Đơn Hàng   ║    Ngày Đặt Hàng     ║ Mã K.Hàng           ║  Tên Khách Hàng  ║ Số Điện Thoại ║  Điểm Tích Lũy  ║\n",
                " ");
        System.out.printf(
                "%20s╠═════════════════╬══════════════════════╬═════════════════════╬══════════════════╬═══════════════╬═════════════════╣\n",
                " ");
        System.out.printf("%20s║ %-16s║ %-21s║ %-20d║ %-17s║ %-14s║ %-16s║\n",
                " ", orderId, orderDate, customer.getCustomerID(), customer.getName(), customer.getContactNumber(),
                customer.getLoyaltyPoints());
        System.out.printf(
                "%20s╠═════════════════╬══════════════════════╬═════════════════════╬══════════════════╬═══════════════╬═════════════════╣\n",
                " ");
        System.out.printf(
                "%20s║   Mã Sản Phẩm   ║     Tên Sản Phẩm     ║  Loại               ║   Giá Sản Phẩm   ║   Số Lượng    ║ Nhà Cung Cấp    ║\n",
                " ");
        for (int i = 0; i < product.length; i++) {
            System.out.printf(
                    "%20s╠═════════════════╬══════════════════════╬═════════════════════╬══════════════════╬═══════════════╬═════════════════╣\n",
                    " ");
            System.out.printf("%20s║ %d. %-13s║ %-21s║ %-20s║ %-17s║ %-14s║ %-16s║\n",
                    " ", (i + 1), product[i].getProductID(), product[i].getName(),
                    Category.getCategoryById(product[i].getCategoryId()).getCategoryName(),
                    product[i].getPrice(), product[i].getQuantity(),
                    Supplier.getSupplierById(product[i].getSupplierId()).getSupplierName());
        }
        System.out.printf(
                "%20s╚═════════════════╩══════════════════════╩═════════════════════╩══════════════════╩═══════════════╩═════════════════╝\n\n",
                " ");
    }

    public static void loc(Scanner scanner, Order[] orderList) {
        System.out.println();
        System.out.printf("%20s╔═════════════════════════════════════════════════════════════════╗\n", "");
        System.out.printf("%20s║                        TÌM KIẾM ĐƠN HÀNG                        ║\n", "");
        System.out.printf("%20s║      Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí)       ║\n", "");
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

        System.out.print("Số lượng mua: ");
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

        System.out.print("Từ ngày (dd/mm/yyyy): "); // **Thêm mới**
        String startDate = scanner.nextLine();
        if (startDate.isEmpty())
            startDate = null;

        System.out.print("Đến ngày (dd/mm/yyyy): "); // **Thêm mới**
        String endDate = scanner.nextLine();
        if (endDate.isEmpty())
            endDate = null;

        Order[] filteredOrders = new Order[orderList.length]; // Tạo mảng với kích thước tối đa là độ dài của mảng
                                                              // orders
        int count = 0; // Biến đếm số đơn hàng thỏa mãn điều kiện

        for (Order order : orderList) {
            if (isOrderMatch(order, orderID, orderDate, productID, productName, price, productCatelo, productQuanti,
                    suppli, order.product, cusID, cusName, phone, point, startDate, endDate)) {
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
            int cusID, String cusName, String phone, int point, String startDate, String endDate) {
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
        if (startDate != null || endDate != null) { // **Thêm mới**
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date orderDateObj = sdf.parse(order.getOrderDate());

                if (startDate != null) {
                    Date start = sdf.parse(startDate);
                    if (orderDateObj.before(start)) { // Nếu ngày đặt hàng trước ngày bắt đầu
                        return false;
                    }
                }

                if (endDate != null) {
                    Date end = sdf.parse(endDate);
                    if (orderDateObj.after(end)) { // Nếu ngày đặt hàng sau ngày kết thúc
                        return false;
                    }
                }
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày tháng: " + e.getMessage());
                return false;
            }
        }

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

    public static double[] thongkeQUY(Order[] orderList, int n) {
        double[] mangQUY = new double[4]; // Mảng lưu doanh thu 4 quý, mặc định giá trị ban đầu là 0.0

        for (Order or : orderList) {
            String[] ngaythangnam = or.getOrderDate().split("/"); // Định dạng ngày là "dd/mm/yyyy"
            int month = Integer.parseInt(ngaythangnam[1]); // Lấy tháng từ chuỗi ngày
            int year = Integer.parseInt(ngaythangnam[2]); // Lấy năm

            if (year == n) { // Kiểm tra xem đơn hàng có thuộc năm cần thống kê không
                int QUY = (month - 1) / 3; // Xác định quý (0: Q1, 1: Q2, 2: Q3, 3: Q4)
                if (QUY >= 0 && QUY < 4) {
                    mangQUY[QUY] += or.calculateTotalAmount();
                }
            }
        }

        return mangQUY;
    }

    public static Product[] thongkeBanChay(Order[] orderList) {
        Product[] mangSP = new Product[100];
        int index = 0;
        for (Order or : orderList) {
            for (Product pr : or.getProductList()) {
                boolean ton_tai = false;
                for (int i = 0; i < index; i++) {
                    if (pr.getProductID().equals(mangSP[i].getProductID())) {
                        mangSP[i].setQuantity(mangSP[i].getQuantity() + pr.getQuantity());
                        ton_tai = true;
                        break;
                    }
                }
                if (!ton_tai) {
                    mangSP[index] = new Product(pr.getProductID(), pr.getName(), pr.getPrice(), pr.getQuantity(),
                            pr.getCategoryId(),
                            pr.getSupplierId());
                    index++;
                }
            }
        }
        for (int i = 0; i < index - 1; i++) {
            for (int j = i + 1; j < index; j++) {
                if (mangSP[i].getQuantity() < mangSP[j].getQuantity()) {
                    Product temp = new Product();
                    temp = mangSP[j];
                    mangSP[j] = mangSP[i];
                    mangSP[i] = temp;
                }
            }
        }
        return Arrays.copyOf(mangSP, index);
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
         */// In ra danh sách hóa đơn với khung viền══════════════════════════════════
        System.out.printf("%20s╔═════════════════╦══════════════════╗\n", "");
        System.out.printf("%20s║ %-15s ║ %-16s ║\n", "", "Mã Đơn Hàng", "Ngày Lập Đơn");
        System.out.printf("%20s╠═════════════════╬══════════════════╣\n", "");

        for (int j = 0; j < orderList.length; j++) {
            System.out.printf("%20s║ %-15s ║ %-16s ║\n", "", orderList[vitri[j]].getOrderId(),
                    orderList[vitri[j]].getOrderDate());
        }

        System.out.printf("%20s╚═════════════════╩══════════════════╝\n", "");
    }

    @Override
    public Order[] readFromFile(String filePath) {
        /* TẢI DANH SÁCH ĐƠN HÀNG - Kiệt */
        Order[] orderList;
        int i, n;
        n = 0;
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
                orderList[i].customer = new Customer(
                        customer.getCustomerById(Integer.parseInt(parts[2])).getCustomerID(),
                        customer.getCustomerById(Integer.parseInt(parts[2])).getName(),
                        customer.getCustomerById(Integer.parseInt(parts[2])).getContactNumber(),
                        customer.getCustomerById(Integer.parseInt(parts[2])).getLoyaltyPoints());
                String[] productParts = parts[3].split("\\|"); // tách các sản phẩm cùng thông tin của nó ra
                orderList[i].product = new Product[productParts.length];
                for (int j = 0; j < productParts.length; j++) {
                    String[] ThongTinProduct = productParts[j].split(",");// tác các phần của sản phẩm như id, tên,...
                    orderList[i].product[j] = new Product(
                            Product.getProductById(ThongTinProduct[0]).getProductID(),
                            Product.getProductById(ThongTinProduct[0]).getName(),
                            Product.getProductById(ThongTinProduct[0]).getPrice(),
                            Product.getProductById(ThongTinProduct[0]).getQuantity(),
                            Product.getProductById(ThongTinProduct[0]).getCategoryId(),
                            Product.getProductById(ThongTinProduct[0]).getSupplierId());
                    orderList[i].product[j].setQuantity(Integer.parseInt(ThongTinProduct[1]));
                }
                i++;
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return orderList;
    }

    public void xoaNoiDungFile(String filePath) { // xóa để khi chạy ghi thì nó sẽ không bị nhân bản các dữ liệu lên
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Mở file ở chế độ ghi đè nhưng không ghi gì cả
            writer.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
    }

    @Override
    public void writeToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            int i = 0;
            writer.write(orderId + ";"
                    + orderDate
                    + ";" + customer.getCustomerID() + ";");
            for (Product pr : this.product) {
                writer.write(pr.getProductID()
                        + "," + pr.getQuantity());
                if (i < product.length - 1) {
                    writer.write("|");
                }
                i++;
            }
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public static Order[] capnhatlaiOrders(Order[] orderList, String id) {
        int pos = 0; // Vị trí của đơn hàng trong danh sách
        for (int i = 0; i < orderList.length; i++) {
            if (orderList[i].getOrderId().equals(id)) {
                pos = i;
                break;
            }
        }
        if (pos == orderList.length - 1) {
            orderList = Arrays.copyOf(orderList, orderList.length - 1);
        } else {
            for (int i = pos; i < orderList.length - 1; i++) {
                orderList[i] = orderList[i + 1];
            }
            orderList = Arrays.copyOf(orderList, orderList.length - 1);
            System.out.println("Đã xóa đơn hàng vi tri " + (pos + 1));
        }
        return orderList;
    }

    public static Order[] themgiaodich(Scanner scanner, Order[] orderList) { // thêm đơn hàng
        /*
         * System.out.print("Bạn muốn thêm bao nhiêu giao dịch ?: ");
         * int n = Integer.parseInt(scanner.nextLine());
         * while(n<0){
         * System.out.println("Không hợp lệ, hãy nhập lại");
         * n=Integer.parseInt(scanner.nextLine());
         * }
         */
        orderList = Arrays.copyOf(orderList, orderList.length + 1);
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 7;
        int i = orderList.length - 1;
        orderList[i] = new Order();
        orderList[i].setOrderId("ORD" + orderList[i].generateRandomString(length, charSet));

        // ngay lap don hang
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String formattedDate = formatter.format(date);
        orderList[i].setOrderDate(formattedDate);

        System.out.print("Nhập Mã Khách Hàng: ");
        int cusId = Integer.parseInt(scanner.nextLine());
        if(orderList[i].customer.getCustomerById(cusId) == null){
            System.out.println("Mã khách hàng này không tồn tại....");
            System.out.println("Tiến hành tạo khách hàng mới");
            System.out.print("Nhập tên: ");
            String name=scanner.nextLine();
            System.out.print("Nhập số điện thoại: ");
            String sdt=scanner.nextLine();
            orderList[i].customer = new Customer(
                cusId,
                name,
                sdt,
                0);
                System.out.println("Tạo khách hàng mới thành công!");
            }
            else{
                orderList[i].customer = new Customer(
                    orderList[i].customer.getCustomerById(cusId).getCustomerID(),
                    orderList[i].customer.getCustomerById(cusId).getName(),
                    orderList[i].customer.getCustomerById(cusId).getContactNumber(),
                    orderList[i].customer.getCustomerById(cusId).getLoyaltyPoints());
                }
                
                System.out.print(" Bao nhiêu sản phẩm ?: "); // bởi vì 1 đơn hàng có nhiều sản phẩm
                int n = Integer.parseInt(scanner.nextLine());
                while (n < 0) {
                    System.out.println("Không hợp lệ, hãy nhập lại");
                    n = Integer.parseInt(scanner.nextLine());
                }
        orderList[i].product = new Product[n];
        
        for (int j = 0; j < orderList[i].product.length; j++) {
            System.out.println("Nhập Mã Sản Phẩm Thứ " + (j + 1));
            String id = scanner.nextLine();
            if (!Product.checkIDProduct(id)) {
                System.out.printf("%30sMã Sản Phẩm Phải Bắt Đầu Từ SP... Và Có Độ Dài Là 5 Ký Tự, Hãy Nhập Lại\n", " ");
                j--;
                continue;
            }
            
            if (Product.getProductById(id).getQuantity() == 0) {
                System.out.println("Sản phẩm này đã hết hàng, vui vòng nhập mã SP khác");
                j--;
                continue;
            }
            
            // không dùng product[I]=Produc.getbyID(id) bởi vì nó sẽ tham chiếu đến địa chỉ
            // này,
            // nó sẽ làm thay đổi lun cái mảng của ngta chứ kp cái mảng sp riêng trong đơn
            // hàng này,
            // chứ nó không sao chép, nên là khởi tạo lại như thế này mới đúng

            if (orderList[i].checkx2IDpro(id, orderList[i].product, j)) {
                orderList[i].product[j] = new Product(Product.getProductById(id).getProductID(),
                Product.getProductById(id).getName(),
                        Product.getProductById(id).getPrice(),
                        Product.getProductById(id).getQuantity(),
                        Product.getProductById(id).getCategoryId(),
                        Product.getProductById(id).getSupplierId());
                    }

                    System.out.print("Nhập Số Lượng Mua: ");
                    int sl = Integer.parseInt(scanner.nextLine());
                    while (sl > Product.getProductById(id).getQuantity() || sl < 0) {
                        if (sl < 0) {
                            System.out.println("Số lượng bạn nhập không được < 0, hãy nhập lại");
                        }
                        if (sl > Product.getProductById(id).getQuantity()) {
                            System.out.println("Số lượng " + sl + " vượt quá " + orderList[i].product[j].getQuantity()
                            + " trong kho, hãy nhập lại: ");
                        }
                        sl = Integer.parseInt(scanner.nextLine());
            }

            Product.getProductById(id).setQuantity(Product.getProductById(id).getQuantity() - sl); // cập nhật lại số
            // lượng trong kho
            Product.writeProductsToFile("product.txt");
            if (!orderList[i].checkx2IDpro(id, orderList[i].product, j)) {
                System.out
                .println("Mã sản phẩm mà bạn nhập đã có trước đó rồi, tiến hành cộng dồn số lượng mua vào....");
                j--;
                System.out.println("Hiện tại đang có : " + (orderList[i].product[j].getQuantity() + sl));
                orderList[i].product[j].setQuantity(orderList[i].product[j].getQuantity() + sl);
                continue;
            }
            orderList[i].product[j].setQuantity(sl); // set số lượng mua
        }
        orderList[i].customer.setLoyaltyPoints(orderList[i].customer.calculateLoyaltyPoints(orderList[i]));
        orderList[i].customer.writeToFile("customers.txt");
        return orderList;
    }
}