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
    
    public void displayOrderDetails() {
        System.out.println("===== Chi Tiet Don Hang =====");
        System.out.println("Order ID: " + orderId);
        System.out.println("Order Date: " + orderDate);
        //System.out.println("Mã khách hàng: "+ customer);
        product.getDetails();     
    }
}
