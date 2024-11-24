
import java.util.Scanner;
import java.io.*;


public class Product {
    public String productID;
    public String name;
    public int price;
    public String categoryId;
    public int quantity;
    public String supplierId;
    public static Product[] productList = new Product[100];
    private static int cnt = 0;

    //constructor

    public Product() {
    }

    public Product(String productID, String name, int price, String categoryId, int quantity, String supplierId) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.categoryId = categoryId;
        this.quantity = quantity;
        this.supplierId = supplierId;
    }
    // get, set

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    private boolean checkIDProduct(String id){
        if(id.length()!=5){
            return false;
        }
        if(!id.startsWith("SP")){
            return false;
        }
        for(int i=2;i<5;i++){
            if(!Character.isDigit(id.charAt(i))){
                return false;
            }
        }
        return true;
    }
    //Kiem tra id co bi trung khong
    private boolean checkDuplicateID(String id, Product[] productList) {
        for (Product product : productList) {
            if (product.getProductID().equals(id)) {
                return true; // Trùng ID
            }
        }
        return false; // Không trùng
    }

    public void addProduct(Product a){
        Scanner sc = new Scanner(System.in);
        int check =0;
        do{
            System.out.println("Nhap product id: ");
            String tmp= sc.nextLine();
            if(!checkIDProduct(tmp) || checkDuplicateID(tmp,productList)){
                System.out.println("Ban da nhap sai id. Vui long nhap lai");
            }
            else {
                a.setProductID(tmp);
                check=1;
            }
        } while(check ==0);
        System.out.println("Nhap ten san pham: ");
        a.setName(sc.nextLine());
        System.out.println("Nhap gia san pham: ");
        a.setPrice(sc.nextInt());
        sc.nextLine();
        System.out.println("Nhap ma loai san pham");
        a.setCategoryId(sc.nextLine());
        System.out.println("Nhap so luong san pham");
        a.setQuantity(sc.nextInt());
        sc.nextLine();
        System.out.println("Nhap ma nha cung cap cho san pham: ");
        a.setSupplierId(sc.nextLine());
        cnt ++;
    }

    //Doc san pham tu file
    public void readProductsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 6 && cnt< 100) { // Kiểm tra nếu mảng chưa đầy
                    Product product = new Product(
                            parts[0],                // productID
                            parts[1],                // name
                            Integer.parseInt(parts[2]), // price
                            parts[3],                // categoryId
                            Integer.parseInt(parts[4]), // quantity
                            parts[5]                 // supplier
                    );
                    productList[cnt] = product;
                    cnt++; // Di chuyển đến vị trí tiếp theo trong mảng
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    //Xoa san pham
    public void deleteProduct(Product[] productList, String productID) {
        int size = productList.length;
        int indexToDelete = -1;

        // Tìm chỉ mục sản phẩm cần xóa
        for (int i = 0; i < size; i++) {
            if (productList[i] != null && productList[i].getProductID().equals(productID)) {
                indexToDelete = i;
                break;
            }
        }

        //Xóa sản phẩm và dịch chuyển các phần tử còn lại
        if (indexToDelete != -1) {
            for (int i = indexToDelete; i < size - 1; i++) {
                productList[i] = productList[i + 1];
            }
            productList[size - 1] = null; // Đặt phần tử cuối cùng thành null
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + productID);
        }
    }

    //Tim kiem
    public void Find(String name) {
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            if (productList[i] != null && productList[i].getName().toLowerCase().contains(name.toLowerCase())) {
                // In thông tin chi tiết sản phẩm nếu tìm thấy kết quả gần đúng
                productList[i].getDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm . " );
        }
    }




    public void restock(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Ban muon tang hay giam so luong ? (Tang:1,Giam:0)");
        int check= sc.nextInt();
        if(check==1){
            System.out.println("Nhap vao so luong san pham muon tang: ");
            int add = sc.nextInt();
            this.quantity+=add;
            System.out.println("Da dieu chinh so luong san pham thanh cong.");
        } else if (check==0) {
            System.out.println("Nhap vao so luong san pham muon giam: ");
            int sub = sc.nextInt();
            this.quantity-=sub;
            System.out.println("Da dieu chinh so luong san pham thanh cong.");
        }
        else {
            System.out.println("Ban da nhap sai.");
        }
    }
    public void updatePrice(){
        Scanner sc = new Scanner(System.in);
        System.out.println("Gia moi cua san pham la: ");
        int tmp = sc.nextInt();
        setPrice(tmp);
        System.out.println("Da dieu chinh gia thanh cong.");
    }
    public void getDetails(){
        System.out.printf("%-20s %-20s %-20d %-20d %-20s %-20s",
                productID,
                name,
                price,
                quantity,
                Category.getCategoryById(categoryId).getCategoryName(),
                Supplier.getSupplierById(supplierId).getSupplierName());
        System.out.println();
    }
}
