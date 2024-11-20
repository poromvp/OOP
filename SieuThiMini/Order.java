public class Order {
    public int orderId;
    public String orderDate;
    public Customer customer;
    public Product[] productList;
    public double totalAmount; //Tổng số tiền
    private static final double VAT=0.1; //Thuế VAT 10%
    public Order(){}
    public Order(int orderId, String orderDate, Customer customer, int productCapacity) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.productList = new Product[productCapacity];
        this.totalAmount = 0.0;
    }

    public int getOrderId() {
        return orderId;
    }
    public void setOrderId(int orderId) {
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
    
}
