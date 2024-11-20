import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class InventoryManager extends Staff {
    private Product[] products; // Danh sách sản phẩm
    private int productCount;   // Số lượng sản phẩm hiện tại

    // Constructor
    public InventoryManager(int staffID, String name, String role, double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public InventoryManager() {
    }
    
    public InventoryManager(int staffID, String name, String role, double salary, String contactNum, Product[] products,
            int productCount, int capacity) {
        super(staffID, name, role, salary, contactNum);
        this.products = new Product[100];
        this.productCount = productCount;
    }

    public InventoryManager(Product[] products, int productCount, int capacity) {
        this.products = products;
        this.productCount = productCount;
    }

    public Product[] getProducts() {
        return products;
    }

    public void setProducts(Product[] products) {
        this.products = products;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    // tạo ngẫu nhiên productid
    public static String generateRandomString(int length) {
        // Các ký tự có thể xuất hiện trong chuỗi ngẫu nhiên
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            // Chọn ngẫu nhiên một ký tự từ mảng characters
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString(); // Trả về chuỗi ngẫu nhiên
    }

    //đọc dữ liệu từ file Inventory
    @Override
    public void readFromFile(String filepath){
        filepath="Inventory.txt";
         try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            int i=0;
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                if (parts.length >= 3) {
                    // Lấy các trường từ mảng parts
                    String id = parts[0];
                    String name = parts[0];
                    productCount = Integer.parseInt(parts[3]);
                    products[i].setProductID(id);
                    products[i].setName(name);
                    products[i].setQuantity(productCount);
                }
                i++;
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi khi phân tích dữ liệu: " + e.getMessage());
        }
    }
    // them san pham vao trong danh sach kho hang
    public void writeToFile (String filepath){
        Scanner sc= new Scanner(System.in);
        filepath ="Inventory.txt";

        int length = 9;
        String randomString = generateRandomString(length);

        System.out.println("San pham ban muon them vao kho: ");
        String Name =  sc.nextLine();

        System.out.println("So luong ban them vao Kho: ");
        String SL=sc.nextLine();

        String newInventory= randomString + " " + Name + " " + SL; 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            bw.write(newInventory);
            bw.newLine();
            bw.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        } 
    }
    // them san pham muon dat hang ve
    public void OrderInventory (){
        String filepath = "OrderInventory.txt";
        Scanner sc= new Scanner(System.in);

        int length = 9;
        String randomString = generateRandomString(length);

        System.out.println("Ban muon dat mat hang nao cho kho: ");
        String name = sc.nextLine();

        System.out.println("So luong ban muon nhap ve: ");
        String Sl = sc.nextLine();

        String newOrder = randomString + " " + name + " " + Sl;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            bw.write(newOrder);
            bw.newLine();
            bw.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        } 


    }

}


    