public class Order {
    public int orderId;
    public String orderDate;
    public Customer customer;
    public Product[] productList;
    public double totalAmount; //Tổng số tiền
    public Order(){}
    public Order(int orderId, String orderDate, Customer customer, Product[] productList, double totalAmount) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.customer = customer;
        this.productList = productList;
        this.totalAmount = totalAmount;
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

    public void addProduct(Product product, int index){ //index là số lượng cần thêm

    }
}
