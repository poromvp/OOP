
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
        for (int i = orderList.length - n; i < orderList.length; i++) {
            orderList[i] = new Order();
            System.out.print("Bao nhiêu sản phẩm ?: "); // bởi vì 1 đơn hàng có nhiều sản phẩm
            orderList[i].product = new Product[Integer.parseInt(scanner.nextLine())];
            for (int j = 0; j < orderList[i].product.length; j++) {
                orderList[i].product[j] = new Product();
            }
            orderList[i].nhapdonhang(scanner, orderList[i].product);
        }
        return orderList;
    }

    public void edit(Scanner scanner, Product[] product) {
        int i = 1, index = 0; // i là choice, index dùng để chỉnh sửa đúng vị trí của ds sản phẩm
        do {
            displayOrderDetails();
            System.out.println("Ban muốn chỉnh sửa cái nào?");
            System.out.println("1. Mã Đơn Hàng");
            System.out.println("2. Ngày Lập Đơn Hàng");
            System.out.println("3. Mã Sản Phẩm");
            System.out.println("4. Tên Sản Phẩm");
            System.out.println("5. Giá Tiền");
            System.out.println("6. Loại Sản Phẩm");
            System.out.println("7. Số lượng");
            System.out.println("8. Nhà Cung Cấp");
            System.out.println("9. Mã Khách Hàng");
            System.out.println("10. Tên Khách Hàng");
            System.out.println("11. Số Điện Thoại");
            System.out.println("12. Điểm Tích Lũy");
            System.out.println("0. Thoát và Lưu");
            i = Byte.parseByte(scanner.nextLine());
            switch (i) {
                case 1:
                    System.out.print("--> ");
                    setOrderId(scanner.nextLine());
                    break;
                case 2:
                    System.out.print("--> ");
                    setOrderDate(scanner.nextLine());
                    break;
                case 3:
                    System.out.print("Bạn Muốn Chỉnh Sửa Mã Của Sản Phẩm Thứ Mấy? --> ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index - 1].setProductID(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Bạn Muốn Chỉnh Sửa Tên Của Sản Phẩm Thứ Mấy? --> ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index - 1].setName(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Bạn Muốn Chỉnh Sửa Giá Của Sản Phẩm Thứ Mấy? --> ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index - 1].setPrice(Integer.parseInt(scanner.nextLine()));
                    break;
                case 6:
                    System.out.print("Bạn Muốn Chỉnh Sửa Loại Của Sản Phẩm Thứ Mấy? --> ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index - 1].setCategoryId(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Bạn Muốn Chỉnh Sửa Số Lượng Của Sản Phẩm Thứ Mấy? --> ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index - 1].setQuantity(Integer.parseInt(scanner.nextLine()));
                    break;
                case 8:
                    System.out.print("Bạn Muốn Chỉnh Sửa Nhà Cung Cấp Của Sản Phẩm Thứ Mấy? --> ");
                    index = Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index - 1].setSupplierId(scanner.nextLine());
                    break;
                case 9:
                    System.out.print("--> ");
                    customer.setCustomerID(Integer.parseInt(scanner.nextLine()));
                    break;
                case 10:
                    System.out.print("--> ");
                    customer.setName(scanner.nextLine());
                    break;
                case 11:
                    System.out.print("--> ");
                    customer.setContactNumber(scanner.nextLine());
                    break;
                case 12:
                    System.out.print("--> ");
                    customer.setLoyaltyPoints(Integer.parseInt(scanner.nextLine()));
                    break;
                case 0:
                    System.out.println("Đã Cập Nhật Giá Trị Mới");
                    break;
                default:
                    System.out.println("Lỗi, hãy nhập lại!");
                    break;
            }
        } while (i != 0);
    }

    public static Order[] xoa(Scanner scanner, Order[] orderList) {
        System.out.print("Bạn muốn xóa đơn hàng nào ? (Nhập mã đơn hàng): ");
        boolean flag = false; // Tạo lính canh để kiểm tra nếu sau khi duyệt mà nó còn false thì sẽ cho nhập
                              // lại cho đúng
        do {
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
            System.out.print("Mã Đơn Hàng Mà Bạn Nhập Không Có Trong Danh Sách, Hãy Nhập Lại: ");
        } while (!flag);
        return orderList;
    }

    public void displayOrderDetails() {
        System.out.printf("\n\n%40s╔════════════════════ CHI TIẾT ĐƠN HÀNG ════════════════════╗\n", " ");
        System.out.printf("%40s║%-20sMã đơn hàng:%-27s║\n","","", orderId);
        System.out.printf("%40s║   Ngày lập đơn:%41s  ║\n","",orderDate);
        System.out.printf("%40s║   Mã Khách Hàng: %38s   ║\n", " ", customer.getCustomerID());
        System.out.printf("%40s║   Tên Khách Hàng: %37s   ║\n", " ", customer.getName());
        System.out.printf("%40s║   Số Điện Thoại: %38s   ║\n", " ", customer.getContactNumber());
        System.out.printf("%40s║   Điểm Tích Lũy: %38s   ║\n", " ", customer.getLoyaltyPoints());
        System.out.printf("%40s║───────────────────────────────────────────────────────────║\n","");
        System.out.printf("%40s║   Mã     Tên sản phẩm     SL        Giá          Tổng     ║\n", " ");

        if (product != null) {
            for (int j = 0; j < product.length; j++) {
                System.out.printf("%40s║                                                           ║\n","");
                System.out.printf("%40s║%d %-8s%-17s%,-8d%,-12d%,-12d║\n",
                "",
                (j+1),
                product[j].getProductID(),
                product[j].getName(),
                product[j].getQuantity(),
                product[j].getPrice(),
                (product[j].getPrice()*product[j].getQuantity()));
            }
        }
        System.out.printf("%40s║───────────────────────────────────────────────────────────║\n","");
        System.out.printf("%40s║  Thành tiền (chưa VAT): %31.2f   ║\n","", calculateTotalAmount());
        System.out.printf("%40s║───────────────────────────────────────────────────────────║\n","");
        System.out.printf("%40s║  TỔNG THANH TOÁN (CÓ VAT): %28.2f   ║\n","", (calculateTotalAmount() + calculateVAT(calculateTotalAmount())));
        System.out.printf("%40s╚═══════════════════════════════════════════════════════════╝\n","");
    }

    public static void loc(Scanner scanner, Order[] orderList) {
        System.out.println("\n--- TÌM KIẾM ĐƠN HÀNG ---");
        System.out.println("Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí):");

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

        System.out.print("Nhập Số Điện Thoại: ");
        String phone = scanner.nextLine();

        System.out.print("Nhập Điểm Tích Lũy: ");
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

    public static boolean isOrderMatch(Order order, String orderID, String orderDate, String productID, String productName, int price, String productCatelo, int productQuanti, String suppli, Product[] product, int cusID, String cusName, String phone, int point) {
        //Kiểm tra nếu người dùng KO bỏ qua tiêu chí (!=null, !=0) thì kiểm tra tiếp nếu KO trùng khớp (!equal(false)) thì nó sẽ trả về giá trị false để hàm if ở trên kia kiểm trả và vì hàm if trên đó chỉ kiểm tra true nên sẽ không thêm đơn hàng vào mảng
        if (orderID != null && !order.getOrderId().equals(orderID)) return false;
        if (orderDate != null && !order.getOrderDate().equals(orderDate)) return false;
        if (cusID != 0 && order.customer.getCustomerID()!=cusID) return false;
        if (cusName != null && !order.customer.getName().equals(cusName)) return false;
        if (phone != null && !order.customer.getContactNumber().equals(phone)) return false;
        if (point != 0 && order.customer.getLoyaltyPoints()!=point) return false;

        boolean san_pham_thu_may=false;
        for(int i=0;i<product.length;i++){
    //Nếu 1 sản phẩm trong đơn hàng có tiêu chí thì nó sẽ break để kiểm đơn hàng tiếp theo luôn để tiết kiệm thời gian
    //Và nếu ko có 1 tiêu chí thì nó sẽ continute qua sản phẩm khác,khỏi phải kiểm tra các tiêu chí khác để tiết kiệm thời gian
            if (productID != null && !order.product[i].getProductID().equalsIgnoreCase(productID)) continue;
            if (productName != null && !order.product[i].getName().equalsIgnoreCase(productName)) continue;
            if (price != 0 && order.product[i].getPrice()!=price) continue;
            if (productCatelo != null && !order.product[i].getCategoryId().equalsIgnoreCase(productCatelo)) continue;
            if (productQuanti != 0 && order.product[i].getQuantity()!=productQuanti) continue;
            if (suppli != null && !order.product[i].getSupplierId().equalsIgnoreCase(suppli)) continue;
            san_pham_thu_may=true;
            break;
        }
        if(!san_pham_thu_may) return false;
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
                // Sắp xếp theo thời gian
                Arrays.sort(orderList, Comparator.comparing(Order::getOrderDate));
                System.out.printf("%-15s%-20s\n", "Mã Đơn Hàng", "Ngày Lập Đơn");
                for (Order order : orderList) {
                    System.out.printf("%-15s%-20s\n", order.getOrderId(), order.getOrderDate());
                }
                break;
                
            case 2:
                // Sắp xếp theo tổng tiền (giảm dần và tăng dần)
                Arrays.sort(orderList, Comparator.comparingDouble(Order::getTotalAmount).reversed());
                System.out.printf("%-15s%-15s\n", "Tổng Tiền", "Mã Đơn Hàng");
                for (Order order : orderList) {
                    System.out.printf("%-15.2f%-15s\n", order.getTotalAmount(), order.getOrderId());
                }
                break;
                
            case 3:
                // Sắp xếp theo quantity (giảm dần và tăng dần)
                Arrays.sort(orderList, Comparator.comparingInt(o -> Arrays.stream(((Order) o).getProductList())
                    .mapToInt(Product::getQuantity).sum()).reversed());
                System.out.printf("%-15s%-20s%-15s\n", "Số Lượng", "Mã Đơn Hàng", "Tổng Tiền");
                for (Order order : orderList) {
                    int totalQuantity = Arrays.stream(order.getProductList())
                        .mapToInt(Product::getQuantity).sum();
                    System.out.printf("%-15d%-20s%-15.2f\n", totalQuantity, order.getOrderId(), order.getTotalAmount());
                }
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

    public void nhapdonhang(Scanner scanner, Product[] product) {
        System.out.print("Nhập Mã Đơn Hàng: ");
        setOrderId(scanner.nextLine());
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
            System.out.print("Nhập Mã Sản Phẩm: ");
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
        filePath = "C:\\Users\\Dell\\OneDrive\\Desktop\\Java\\OOP_DOAN\\SieuThiMini\\donhang.txt";
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
