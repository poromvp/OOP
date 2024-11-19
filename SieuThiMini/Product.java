package SieuThiMini;

import java.util.Scanner;

public class Product {
    public String productID;
    public String name;
    public int price;
    public String category;
    public int quantity;
    public String supplier;

    //constructor

    public Product() {
    }

    public Product(String productID, String name, int price, String category, int quantity, String supplier) {
        this.productID = productID;
        this.name = name;
        this.price = price;
        this.category = category;
        this.quantity = quantity;
        this.supplier = supplier;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
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
        System.out.println(productID+" "+name+" "+price+" "+category+" "+quantity+" "+supplier );
    }
}
