
import java.util.Arrays;

public class Category {
    public String categoryID;
    public String categoryName;
    public Product[] productsList;
    private int productCount;

    public Category() {
    }

    public Category(String categoryID, String categoryName, Product[] productsList) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
        this.productsList = productsList;
    }

    public String getCategoryID() {
        return categoryID;
    }

    public void setCategoryID(String categoryID) {
        this.categoryID = categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public Product[] getProductsList() {
        return productsList;
    }

    public void setProductsList(Product[] productsList) {
        this.productsList = productsList;
    }

    public void addProduct(Product product) {
        if (productCount == productsList.length) {
            // Tăng kích thước mảng khi cần
            productsList = Arrays.copyOf(productsList, productsList.length * 2);
        }
        productsList[productCount++] = product;
    }

    public void removeProduct(String productID) {
        for (int i = 0; i < productCount; i++) {
            if (productsList[i].getProductID().equals(productID)) {
                // Dịch chuyển các phần tử sau sản phẩm bị xóa lên trước
                System.arraycopy(productsList, i + 1, productsList, i, productCount - i - 1);
                productsList[--productCount] = null; // Giảm số lượng sản phẩm và xóa phần tử cuối
                break;
            }
        }
    }
    public void getAllProducts() {
        System.out.println("Cac san pham co loai "+categoryName+"la :");
        for(int i=0;i<productCount;i++){
            productsList[i].getDetails();
        }
    }
    public void removeCategory() {
        this.categoryID = null;
        this.categoryName = null;
        this.productsList = new Product[0];
        this.productCount = 0;
    }
}
