public class Order{
    public String orderId;
    public String orderDate;
    public Customer customer;
    public Product[] productList;
    public double totalAmount; //Tổng số tiền
    private static final double VAT=0.1; //Thuế VAT 10%
    public Order(){}
    public Order(String orderId, String orderDate, Customer customer, int productCapacity) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.productList = new Product[productCapacity];
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
    public Product[] getProductList() {
        return productList;
    }
    public void setProductList(Product[] productList) {
        this.productList = productList;
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
 
    public void addProduct(Product product, int index) {
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
    }
    
    public void displayOrderDetails() {
        System.out.println("===== Order Details =====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        /*System.out.println("Customer Information: ");
        if (customer != null) {
            customer.displayCustomerDetails(); // Gọi phương thức hiển thị thông tin khách hàng
        } else {
            System.out.println("No customer information available.");
        }
        
        System.out.println("Product List:");
        if (productList != null && productList.length > 0) {
            for (int i = 0; i < productList.length; i++) {
                if (productList[i] != null) {
                    System.out.println((i + 1) + ". " + productList[i].getProductName() + " - Price: $" + productList[i].getPrice());
                } else {
                    System.out.println((i + 1) + ". No product in this slot.");
                }
            }
        } else {
            System.out.println("No products in the order.");
        }
        
        System.out.println("Total Amount Before VAT: $" + calculateTotalAmount());
        System.out.println("VAT (10%): $" + calculateVAT(calculateTotalAmount()));
        System.out.println("Total Amount After VAT: $" + (calculateTotalAmount() + calculateVAT(calculateTotalAmount())));
        System.out.println("==========================");
        */
    }
}
