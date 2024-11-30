import java.util.Scanner;
import java.io.*;


public class Product {
    public String productID;
    public String name;
    public int price;
    public String categoryId;
    public int quantity;
    public String supplierId;
    public static Product[] productList = new Product[10];
    public static int cnt = 0;

    //constructor

    public Product() {
    }

    public Product(String productID, String name, int price,int quantity, String categoryId,  String supplierId) {
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
//        if(!Category.checkIDCategory(categoryId)||!Supplier.checkDuplicateID(categoryId)){
//            System.out.println("Id nhap vao bi sai, san pham se co id mat dinh.");
//            return;
//        }
//        else this.categoryId=categoryId;
        this.categoryId=categoryId;
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
//        if(!Supplier.checkIDSupplier(supplierId)||!Supplier.checkDuplicateID(supplierId)){
//            System.out.println("Id nhap vao bi sai, san pham se co id mat dinh.");
//            return;
//        }
//        else this.supplierId=supplierId;
        this.supplierId=supplierId;
    }

    public static Product[] getProductList() {
        return productList;
    }

    public static void setProductList(Product[] productList) {
        Product.productList = productList;
    }

    public static int getCnt() {
        return cnt;
    }

    public static void setCnt(int cnt) {
        Product.cnt = cnt;
    }
    public static Product getProductById(String id){
        int tmp = 0;
        int size = productList.length;
        for (int i = 0; i < size; i++) {
            if (productList[i] != null && productList[i].getProductID().equals(id)) {
                tmp = i;
                break;
            }
        }
        return productList[tmp];
    }
    //Kiem tra format id
    protected static  boolean checkIDProduct(String id){
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
    protected static boolean checkDuplicateID(String id) {
        
        for (Product product : productList) {
            if (product!= null && product.getProductID().equals(id)) {
                return false; // Trùng ID
            }
        }
        return true; // Không trùng
    }

    public static void addProduct(){
        if (cnt == productList.length) {
            // Mở rộng mảng khi đạt giới hạn
            Product[] newProductList = new Product[productList.length * 2];
            System.arraycopy(productList, 0, newProductList, 0, productList.length);
            productList = newProductList;
        }
        Product a = new Product();
        Scanner sc = new Scanner(System.in);
        int check =0;
        do{
            System.out.println("Nhap product id (co dang SP___): ");
            String tmp= sc.nextLine();
            if(!checkIDProduct(tmp) || !checkDuplicateID(tmp)){
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
        try {
            a.setPrice(Integer.parseInt(sc.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("Gia nhap vao phai la mot so nguyen hop le.");
            return;
        }
        System.out.println("Nhap ma loai san pham");
        a.setCategoryId(sc.nextLine());
        System.out.println("Nhap so luong san pham: ");
        try {
            a.setQuantity(Integer.parseInt(sc.nextLine()));
        } catch (NumberFormatException e) {
            System.out.println("So luong nhap vao phai la mot so nguyen hop le.");
            return;
        }
        System.out.println("Nhap ma nha cung cap cho san pham: ");
        a.setSupplierId(sc.nextLine());
        productList[cnt++]=a;
        System.out.println("Da them san pham thanh cong.");
    }

    //Doc san pham tu file
    public static void readProductsFromFile(String filePath) {
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (cnt == productList.length) {
                    // Mở rộng mảng khi đạt giới hạn
                    Product[] newProductList = new Product[productList.length * 2];
                    System.arraycopy(productList, 0, newProductList, 0, productList.length);
                    productList = newProductList;
                }
                String[] parts = line.split(",");
                if (parts.length == 6 ) { // Kiểm tra nếu mảng chưa đầy
                    Product product = new Product(
                            parts[0],                // productID
                            parts[1],                // name
                            Integer.parseInt(parts[2]), // price
                            Integer.parseInt(parts[3]), // quantity
                            parts[4],                // categoryId
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
    public static void writeProductsToFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (int i = 0; i < cnt; i++) {
                Product product = productList[i];
                if (product != null) {
                    // Ghi thông tin sản phẩm vào file, cách nhau bởi dấu phẩy
                    writer.write(product.getProductID() + "," +
                            product.getName() + "," +
                            product.getPrice() + "," +
                            product.getQuantity() + "," +
                            product.getCategoryId()+ "," +
                            product.getSupplierId());
                    writer.newLine(); // Xuống dòng cho sản phẩm tiếp theo
                }
            }
            System.out.println("Đã ghi sản phẩm vào file thành công.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    //Xoa san pham
    public static void deleteProduct( String productID) {
        int indexToDelete = -1;

        // Tìm chỉ mục sản phẩm cần xóa
        for (int i = 0; i < cnt; i++) {
            if (productList[i] != null && productList[i].getProductID().equals(productID)) {
                indexToDelete = i;
                break;
            }
        }

        //Xóa sản phẩm và dịch chuyển các phần tử còn lại
        if (indexToDelete != -1) {
            for (int i = indexToDelete; i < cnt - 1; i++) {
                productList[i] = productList[i + 1];
            }
            productList[--cnt] = null; // Đặt phần tử cuối cùng thành null
            System.out.println("Đã xóa sản phẩm thành công.");
        } else {
            System.out.println("Không tìm thấy sản phẩm với ID: " + productID);
        }
    }

    //Tim kiem
    public static void findById(String name) {
        boolean found = false;
        for (int i = 0; i < cnt; i++) {
            if (productList[i] != null && productList[i].getName().toLowerCase().contains(name.toLowerCase())) {
                // In thông tin chi tiết sản phẩm nếu tìm thấy kết quả gần đúng
                productList[i].getDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm !!! " );
        }
    }
    public static void findBySupplier(String name) {
        boolean found = false;
        String resultSupplier="";
        for (int i = 0; i < Supplier.cnt; i++) {
            if (Supplier.supplierList[i] != null && Supplier.supplierList[i].getSupplierName().equalsIgnoreCase(name)) {
                //Tra ve id supplier
                resultSupplier=Supplier.supplierList[i].getSupplierID();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay nha cung cap !!! " );
        }
        else{
            int cnt =0;
            for(int i=0;i<Product.cnt;i++){
                if(productList[i].getSupplierId().equals(resultSupplier)){
                    productList[i].getDetails();
                    cnt++;
                }
            }
            if(cnt==0){
                System.out.println("Khong tim thay san pham nao thuoc nha cung cap tren.");
            }
        }
    }
    public static void findByCategory(String name) {
        boolean found = false;
        String resultCategory ="";
        for (int i = 0; i < Category.cnt; i++) {
            if (Category.categoryList[i] != null && Category.categoryList[i].getCategoryName().equalsIgnoreCase(name)) {
                //Tra ve id category
                resultCategory =Category.categoryList[i].getCategoryID();
                found = true;
            }
        }
        if (!found) {
            System.out.println("Khong tim thay loai san pham !!! " );
        }
        else{
            int cnt =0;
            for(int i=0;i<Product.cnt;i++){
                if(productList[i].getCategoryId().equals(resultCategory)){
                    productList[i].getDetails();
                    cnt++;
                }
            }
            if(cnt==0){
                System.out.println("Khong tim thay san pham nao thuoc loai tren.");
            }
        }
    }
    //xuat thong tin
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
    // chinh sua
    public static void upDateProduct(String id) {
        int choice;
        int index = -1;
        for (int i = 0; i <= cnt; i++) {
            if (productList[i].getProductID().equals(id)) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            Scanner sc = new Scanner(System.in);

            do {
                System.out.println("------ CHINH SUA SAN PHAM------");
                System.out.println("0.Thoat.");
                System.out.println("1.Chinh sua id san pham. ");
                System.out.println("2.Chinh sua ten san pham. ");
                System.out.println("3.Chinh sua gia san pham. ");
                System.out.println("4.Chinh sua so luong san pham. ");
                System.out.println("5.Chinh sua id loai san pham. ");
                System.out.println("6.Chinh sua id nha cung cap san pham. ");
                System.out.println("Lua chon cua ban: ");
                try {
                    choice = Integer.parseInt(sc.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("Lua chon phai la mot so nguyen hop le.");
                    return;
                }
                switch (choice) {
                    case 0:
                        System.out.println("Thoat chinh sua.");
                        break;
                    case 1:
                        System.out.println("Nhap vao id moi (co dang SP___): ");
                        String new_id = sc.nextLine();
                        if (!checkIDProduct(new_id) || !checkDuplicateID(new_id)){
                            System.out.println("Id nhap vao bi sai !!!");
                        }
                        else {
                            productList[index].setProductID(new_id);
                            System.out.println("Da chinh sua id thanh cong.");
                        }
                        break;
                    case 2:
                        System.out.println("Nhap vao ten moi: ");
                        productList[index].setName(sc.nextLine());
                        System.out.println("Da chinh sua ten thanh cong.");
                        break;
                    case 3:
                        System.out.println("Nhap vao gia moi: ");
                        try {
                            int p = Integer.parseInt(sc.nextLine());
                            if (p < 0) {
                                System.out.println("Gia phai lon hon 0 !!!");
                            } else {
                                productList[index].setPrice(p);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Gia nhap vao phai la mot so nguyen hop le.");
                        }
                        break;
                    case 4:
                        System.out.println("Nhap vao so luong moi: ");
                        try {
                            int x = Integer.parseInt(sc.nextLine());
                            if (x < 0) {
                                System.out.println("So luong san pham phai lon hon 0 !!!");
                            } else {
                                productList[index].setQuantity(x);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("So luong nhap vao phai la mot so nguyen hop le.");
                        }
                        break;
                    case 5:
                        System.out.println("Nhap vao id loai moi (co dang CT___): ");
                        String new_ct = sc.nextLine();
                        if (!Category.checkIDCategory(new_ct) || !Category.checkDuplicateID(new_ct)){
                            System.out.println("Id nhap vao bi sai !!!");
                        }
                        else {
                            productList[index].setCategoryId(new_ct);
                            System.out.println("Da chinh sua id thanh cong.");
                        }
                        break;
                    case 6:
                        System.out.println("Nhap vao id nha cung cap moi (co dang SL___): ");
                        String new_sl = sc.nextLine();
                        if (!Supplier.checkIDSupplier(new_sl) || !Supplier.checkDuplicateID(new_sl)){
                            System.out.println("Id nhap vao bi sai !!!");
                        }
                        else {
                            productList[index].setSupplierId(new_sl);
                            System.out.println("Da chinh sua id thanh cong.");
                        }
                        break;
                    default:
                        System.out.println("Lua chon sai. Vui long chon lai.");
                }
            } while (choice != 0);

        } else {
            System.out.println("Khong tim thay id san pham.");
            return;
        }
    }
}