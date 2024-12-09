
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
    
    public double calculateTotalAmount() { // Tính tổng số tiền của sản phẩm
        double total = 0.0;
        for (Product product : product) { // vòng lặp for each duyệt từng sản phẩm có kdl là Products
            if (product != null) {
                total += product.getPrice() * product.getQuantity();
            }
        }
        return total;
    }

    public static double getVat() {
        return VAT;
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
        
        //System.out.print("Nhập Mã Khách Hàng: ");
        //int cusId = Integer.parseInt(scanner.nextLine());
        int cusId = Customer.kiemTraMaKhachHang(scanner);
        if(orderList[i].customer.getCustomerById(cusId) == null){
            System.out.printf("%30sMã khách hàng này không tồn tại....\n", " ");
            System.out.printf("%30sTiến hành tạo khách hàng mới\n", " ");
            System.out.print("Nhập tên: ");
            String name=scanner.nextLine();
            //System.out.print("Nhập số điện thoại: ");
            String sdt = Customer.kiemTraSoDienThoai(scanner);
            orderList[i].customer = new Customer(
                cusId,
                name,
                sdt,
                0);
            }
            else{
                orderList[i].customer = new Customer(
                    orderList[i].customer.getCustomerById(cusId).getCustomerID(),
                    orderList[i].customer.getCustomerById(cusId).getName(),
                    orderList[i].customer.getCustomerById(cusId).getContactNumber(),
                    orderList[i].customer.getCustomerById(cusId).getLoyaltyPoints());
                }
                
                System.out.print("Bao nhiêu sản phẩm ?: "); // bởi vì 1 đơn hàng có nhiều sản phẩm
                int n = Integer.parseInt(scanner.nextLine());
                while (n < 0) {
                    System.out.printf("%30sKhông hợp lệ, hãy nhập lại\n", " ");
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
            if(Product.getProductById(id).getProductID().equals("SP000")){
                System.out.printf("%30sMã Sản Phẩm Không Hợp Lệ, Hãy Nhập Lại\n", " ");
                j--;
                continue;
            }
            if (Product.getProductById(id).getQuantity() == 0) {
                System.out.printf("%30sSản phẩm này đã hết hàng, vui vòng nhập mã SP khác\n", " ");
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
                            System.out.printf("%30sSố lượng bạn nhập không được < 0, hãy nhập lại\n", " ");
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
                .println("Mã sản phẩm mà bạn nhập đã có trước đó rồi, tiến hành cộng dồn số lượng mua vào sản phẩm trước....");
                j--;
                System.out.println("Hiện tại đang có : " + (orderList[i].product[j].getQuantity() + sl));
                orderList[i].product[j].setQuantity(orderList[i].product[j].getQuantity() + sl);
                continue;
            }
            orderList[i].product[j].setQuantity(sl); // set số lượng mua
        }
        orderList[i].customer.setLoyaltyPoints(orderList[i].customer.calculateLoyaltyPoints(orderList[i]));
        //Customer.getCustomerById(orderList[i].customer.getCustomerID()).setLoyaltyPoints(n);
        //orderList[i].customer.writeToFile("customers.txt");
        //orderList[i].customer.capnhatlaiCustomers(cusId, orderList[i].customer.getLoyaltyPoints());

        // Tăng điểm tích lũy cho khách hàng
        if (orderList[i].getCustomer() != null) {
            orderList[i].getCustomer().setLoyaltyPoints(
                orderList[i].getCustomer().calculateLoyaltyPoints(orderList[i])
            );
            System.out.println("Điểm tích lũy của khách hàng đã được cập nhật.");
        }

        return orderList;
    }

}