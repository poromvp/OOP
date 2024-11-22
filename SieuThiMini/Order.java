import java.util.Scanner;

public class Order{
    public String orderId;
    public String orderDate;
    public Customer customer;
    public Product product;
    public double totalAmount; //Tổng số tiền
    private static final double VAT=0.1; //Thuế VAT 10%
    public Order(){
        this.product = new Product();
    }
    public Order(String orderId, String orderDate, Customer customer, Product product) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.product=product;
        this.totalAmount = 0.0;
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
    public Product getProduct() {
        return product;
    }
    public void setProductList(Product product) {
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
 
    /*public void addProduct(Product product, int index) {
        if (index >= 0 && index < productList.length) {
            productList[index] = product;       // thêm sản phẩm vào vị trí index cụ thể
            totalAmount += product.getPrice(); // Cập nhật tổng số tiền ngay khi thêm sản phẩm
        } else {
            System.out.println("Vi tri vuot qua kich thuoc mang");
        }
    }

    public double calculateTotalAmount() { //Tính tổng số tiền của sản phẩm
        double total = 0.0;
        for (Product product : productList) { // vòng lặp for each duyệt từng sản phẩm có kdl là Products
            if (product != null) {
                total += product.getPrice(); 
            }
        }
        return total;
    }*/
    public void edit(Scanner scanner){
        int i=1;
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
                    System.out.print("--> ");
                    product.setProductID(scanner.nextLine());
                    break;
                case 4:
                    System.out.print("--> ");
                    product.setName(scanner.nextLine());
                    break;
                case 5:
                    System.out.print("--> ");
                    product.setPrice(Integer.parseInt(scanner.nextLine()));
                    break;
                case 6:
                    System.out.print("--> ");
                    product.setCategory(scanner.nextLine());
                    break;
                case 7:
                    System.out.print("--> ");
                    product.setQuantity(Integer.parseInt(scanner.nextLine()));
                    break;
                case 8:
                    System.out.print("--> ");
                    product.setSupplier(scanner.nextLine());
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
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        //System.out.println("Mã khách hàng: "+ customer);
        product.getDetails();     
    }
}
