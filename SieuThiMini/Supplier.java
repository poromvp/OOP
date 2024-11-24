

public class Supplier {
    public String supplierID;
    public String supplierName;
    public Product[] supplierProduct;
    private int supplierCount;

    public Supplier() {
    }

    public Supplier(String supplierID, String supplierName, Product[] supplierProduct) {
        this.supplierID = supplierID;
        this.supplierName = supplierName;
        this.supplierProduct = supplierProduct;
    }

    public String getSupplierID() {
        return supplierID;
    }

    public void setSupplierID(String supplierID) {
        this.supplierID = supplierID;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Product[] getSupplierProduct() {
        return supplierProduct;
    }

    public void setSupplierProduct(Product[] supplierProduct) {
        this.supplierProduct = supplierProduct;
    }

    public void getSupplierDetail(){
        System.out.println(supplierID+" "+supplierName+" : ");
        for(int i=0;i<supplierCount;i++){
            System.out.println(supplierProduct[i]);
        }
    }
    public void removeSupplier() {
        this.supplierID = null;
        this.supplierName = null;
        this.supplierProduct = new Product[0];
        this.supplierCount = 0;
    }
}
