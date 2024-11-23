import java.io.*;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class InventoryManager extends Staff {
    private Product[] IvenProducts; // Danh sách sản phẩm trong kho
    private Product[] OrderProducts; // danh sách sản phẩm đặt về
    private int productCount;   // Số lượng sản phẩm hiện tại
    private int countA;
    private int countB;

    public InventoryManager(String staffID, String name, String role, Double salary, String contactNum, int count) {
        super(staffID, name, role, salary, contactNum);
    }

    public InventoryManager() {
    }

    public InventoryManager(String staffID, String name, String role, Double salary, String contactNum,
            Product[] ivenProducts, Product[] orderProducts, int productCount, int count) {
        super(staffID, name, role, salary, contactNum);
        IvenProducts = ivenProducts;
        OrderProducts = orderProducts;
        this.productCount = productCount;
        this.countA = countA;
    }

    public InventoryManager(Product[] ivenProducts, Product[] orderProducts, int productCount, int count) {
        IvenProducts = ivenProducts;
        OrderProducts = orderProducts;
        this.productCount = productCount;
        this.countB = countB;
    }

    public Product[] getIvenProducts() {
        return IvenProducts;
    }

    public void setIvenProducts(Product[] ivenProducts) {
        IvenProducts = ivenProducts;
    }

    public Product[] getOrderProducts() {
        return OrderProducts;
    }

    public void setOrderProducts(Product[] orderProducts) {
        OrderProducts = orderProducts;
    }

    public int getProductCount() {
        return productCount;
    }

    public void setProductCount(int productCount) {
        this.productCount = productCount;
    }

    public int getCountA() {
        return countA;
    }

    public void setCount(int countA) {
        this.countA = countA;
    }

    public int getCountB() {
        return countB;
    }

    public void setCountB(int countB) {
        this.countB = countB;
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
        filepath="D:\\DoAnOOP\\OOP\\SieuThiMiNi\\Inventory.txt";
         try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            countA = 0;
            Product[] IvenProducts = new Product[100];
            for (int i=0; i<IvenProducts.length; i++){
                IvenProducts[i] = new Product();
            }
            
            System.out.printf("%-10s| %-12s| %-10s %n", 
            "Mã sản phẩm","Tên sản phẩm", "Số lượng");

            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                if (parts.length >= 3) {
                    // Lấy các trường từ mảng parts
                    String id = parts[0];
                    String name = parts[1];
                    productCount = Integer.parseInt(parts[2]);
                    IvenProducts[countA].setProductID(id);
                    IvenProducts[countA].setName(name);
                    IvenProducts[countA].setQuantity(productCount);
                }
                
                System.out.printf("%-10s| %-12s| %-10s %n", 
                IvenProducts[countA].getProductID(),IvenProducts[countA].getName(),IvenProducts[countA].getQuantity() );
                countA++;
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi khi phân tích dữ liệu: " + e.getMessage());
        }
    }

    public void readFromFileOrder(String filepath){
        filepath="D:\\DoAnOOP\\OOP\\SieuThiMiNi\\OrderInventory.txt";
         try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            countB = 0;

            Product [] OrderProducts = new Product[100];
            for (int i =0; i<OrderProducts.length; i++){
                OrderProducts[i]= new Product();
            }
            System.out.printf("%-10s| %-12s| %-10s %n", 
            "Mã sản phẩm","Tên sản phẩm", "Số lượng");
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                if (parts.length >= 3) {
                    // Lấy các trường từ mảng parts
                    String id = parts[0];
                    String name = parts[1];
                    productCount = Integer.parseInt(parts[2]);
                    OrderProducts[countB].setProductID(id);
                    OrderProducts[countB].setName(name);
                    OrderProducts[countB].setQuantity(productCount);
                }
                
                System.out.printf("%-10s| %-12s| %-10s %n", 
                OrderProducts[countB].getProductID(),OrderProducts[countB].getName(),OrderProducts[countB].getQuantity() );
                countB++;
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
        filepath ="D:\\DoAnOOP\\OOP\\SieuThiMiNi\\Inventory.txt";
        File inputFile = new File(filepath);
        String filetemp = "temp.txt";
        File temp = new File(filetemp);

        System.out.println("nhập vị trí dòng bạn muốn thêm vào danh sách");
        int vitri = sc.nextInt();
        sc.nextLine();

        int length = 9;
        String productidString = generateRandomString(length);

        System.out.println("San pham ban muon them vao kho: ");
        String Name =  sc.nextLine();

        System.out.println("So luong ban them vao Kho: ");
        String SL=sc.nextLine();

        String newInventory= productidString + " " + Name + " " + SL; 
        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
        ){
            String line;
            int currentLine = 0; // dòng đang thao tác hiện tại
            while ((line = br.readLine())!=null){
                //chèn dòng mới vào vị trí mong muốn
                if(currentLine==vitri-1){
                    bw.write(newInventory);
                   bw.newLine();
                }
               bw.write(line);
               bw.newLine();
               currentLine++;
            }
            // nếu vị trí muốn thêm lớn hơn số dòng hiện có thì sẽ thêm mới một dòng ở cuối
            if (vitri >= currentLine){
            bw.write(newInventory);
            bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()){
            System.out.println("bị lỗi !!!");
            return;
        }

        if(!temp.renameTo(inputFile)){
            System.out.println("không thể đổi tên file");
        }else {
            System.out.println("đã chèn dòng mới vào");
        }

        readFromFile(filepath);
}

    public void removeIvenProduct(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhập tên sản phẩm muốn xóa: ");
        String NameProductRemove = sc.nextLine();
        String filepath ="D:\\DoAnOOP\\OOP\\SieuThiMiNi\\Inventory.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader( new FileReader(filepath))){
            String line;
            while ((line= br.readLine())!=null){
                String[] parts = line.split(" ");
                if(!NameProductRemove.equals(parts[2])){
                    content.append(line).append(System.lineSeparator());            // đọc file và lưu lại các dòng không chứa mã nhân viên cần xoá;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    
        try(FileWriter fw = new FileWriter(filepath)){
            fw.write(content.toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        readFromFile(filepath);
    }

    // them san pham muon dat hang ve
    public void OrderInventory (){
        String filepath = "D:\\DoAnOOP\\OOP\\SieuThiMiNi\\OrderInventory.txt";
        Scanner sc= new Scanner(System.in);
        File inputFile = new File(filepath);
        String filetemp = "temp.txt";
        File temp = new File(filetemp);

        System.out.println("nhập vị trí dòng bạn muốn thêm vào danh sách");
        int vitri = sc.nextInt();
        sc.nextLine();

        int length = 9;
        String ProductOrderStringID = generateRandomString(length);

        System.out.println("Ban muon dat mat hang nao cho kho: ");
        String name = sc.nextLine();

        System.out.println("So luong ban muon nhap ve: ");
        String Sl = sc.nextLine();

        String newOrder = ProductOrderStringID + " " + name + " " + Sl;
        try (
            BufferedReader br = new BufferedReader(new FileReader(inputFile));
            BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
        ){
            String line;
            int currentLine = 0; // dòng đang thao tác hiện tại
            while ((line = br.readLine())!=null){
                //chèn dòng mới vào vị trí mong muốn
                if(currentLine==vitri-1){
                    bw.write(newOrder);
                   bw.newLine();
                }
               bw.write(line);
               bw.newLine();
               currentLine++;
            }
            // nếu vị trí muốn thêm lớn hơn số dòng hiện có thì sẽ thêm mới một dòng ở cuối
            if (vitri >= currentLine){
            bw.write(newOrder);
            bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()){
            System.out.println("bị lỗi !!!");
            return;
        }

        if(!temp.renameTo(inputFile)){
            System.out.println("không thể đổi tên file");
        }else {
            System.out.println("đã chèn dòng mới vào");
        }

        readFromFileOrder(filepath);
}

    // xóa sản phẩm muốn đặt về
    public void removeOrderProduct(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhập tên sản phẩm muốn xóa: ");
        String NameProductOrderRemove = sc.nextLine();
        String filepath = "D:\\DoAnOOP\\OOP\\SieuThiMiNi\\OrderInventory.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader( new FileReader(filepath))){
            String line;
            while ((line= br.readLine())!=null){
                String[] parts = line.split(" ");
                if(!NameProductOrderRemove.equals(parts[2])){
                    content.append(line).append(System.lineSeparator());            // đọc file và lưu lại các dòng không chứa tên sản phẩm cần xóa
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    
        try(FileWriter fw = new FileWriter(filepath)){
            fw.write(content.toString());
        } catch (IOException e){
            e.printStackTrace();
        }

        readFromFileOrder(filepath);
    }
}



    