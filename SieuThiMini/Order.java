
import java.util.Scanner;

public class Order{
    public String orderId; //mã đơn hàng
    public String orderDate; //ngày lập đơn hàng
    public Customer customer; //khách hàng
    public Product[] product;   //danh sách các sản phẩm
    public double totalAmount; //Tổng số tiền
    private static final double VAT=0.1; //Thuế VAT 10%
    public Order(){
        customer=new Customer(); //khởi tạo khách hàng cho hóa đơn
    }
    public Order(String orderId, String orderDate, Customer customer, Product[] product) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.product=product;
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

    public double calculateTotalAmount() { //Tính tổng số tiền của sản phẩm
        double total = 0.0;
        for (Product product : product) { // vòng lặp for each duyệt từng sản phẩm có kdl là Products
            if (product != null) {
                total += product.getPrice(); 
            }
        }
        return total;
    }
    public void edit(Scanner scanner, Product[] product){
        int i=1, index=0; //i là choice, index dùng để chỉnh sửa đúng vị trí của ds sản phẩm
        do{
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
            i=Byte.parseByte(scanner.nextLine());
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
                    index=Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index-1].setProductID(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("Bạn Muốn Chỉnh Sửa Tên Của Sản Phẩm Thứ Mấy? --> ");
                    index=Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index-1].setName(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("Bạn Muốn Chỉnh Sửa Giá Của Sản Phẩm Thứ Mấy? --> ");
                    index=Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index-1].setPrice(Integer.parseInt(scanner.nextLine()));
                    break;
                case 6:
                    System.out.print("Bạn Muốn Chỉnh Sửa Loại Của Sản Phẩm Thứ Mấy? --> ");
                    index=Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index-1].setCategoryId(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("Bạn Muốn Chỉnh Sửa Số Lượng Của Sản Phẩm Thứ Mấy? --> ");
                    index=Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index-1].setQuantity(Integer.parseInt(scanner.nextLine()));
                    break;
                case 8:
                    System.out.print("Bạn Muốn Chỉnh Sửa Nhà Cung Cấp Của Sản Phẩm Thứ Mấy? --> ");
                    index=Integer.parseInt(scanner.nextLine());
                    System.out.print("--> ");
                    product[index-1].setSupplierId(scanner.nextLine());
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
        }while(i!=0);
    }
    
    public void displayOrderDetails() {
        System.out.println("===== Chi Tiet Don Hang =====");
        System.out.println("Mã đơn hàng: " + orderId);
        System.out.println("Ngày: " + orderDate);
        System.out.println("Tên Khách Hàng: "+customer.getName());
        System.out.println("Điểm Tích Lũy: "+customer.getLoyaltyPoints());
        System.out.println("Mã Khách Hàng: "+customer.getCustomerID());
        System.out.println("Số Điện Thoại: "+customer.getContactNumber());
        System.out.println("Danh sách sản phẩm:");
        if (product != null) {
            for(int j=0;j<product.length;j++){
                System.out.print((j+1)+". ");
                product[j].getDetails();
            }
        }
        System.out.println("Tổng tiền (chưa VAT): " + calculateTotalAmount());
        System.out.println("Tổng tiền (có VAT): " + (calculateTotalAmount() + calculateVAT(calculateTotalAmount()))); 
    }

    public void nhapdonhang(Scanner scanner, Product[] product){
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
        for(Product pro: product){
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
}
