import java.io.*;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class InventoryManager extends Staff {
    private String ProductID; // Danh sách sản phẩm trong kho
    private String ProductName; // Danh sách sản phẩm đặt về
    private int Count;   // Số lượng sản phẩm hiện tại

    public InventoryManager(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public InventoryManager() {
    }

    public InventoryManager(String staffID, String name, String role, Double salary, String contactNum,
                            String productID, String productName, int count) {
        super(staffID, name, role, salary, contactNum);
        ProductID = productID;
        ProductName = productName;
        Count = count;
    }

    public InventoryManager(String productID, String productName, int count) {
        ProductID = productID;
        ProductName = productName;
        Count = count;
    }

    public String getProductID() {
        return ProductID;
    }

    public void setProductID(String productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getCount() {
        return Count;
    }

    public void setCount(int count) {
        Count = count;
    }

    // Tạo ngẫu nhiên productID
    public static String generateRandomString(int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();

        StringBuilder randomString = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            randomString.append(characters.charAt(index));
        }

        return randomString.toString();
    }

    // Đọc dữ liệu từ file Inventory
    @Override
    public InventoryManager[] readFromFile(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            InventoryManager[] IvenProducts = new InventoryManager[0];

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    int SL = Integer.parseInt(parts[2]);
                    InventoryManager newIvenProduct = new InventoryManager(id, name, SL);
                    IvenProducts = Arrays.copyOf(IvenProducts, IvenProducts.length + 1);
                    IvenProducts[IvenProducts.length - 1] = newIvenProduct;
                } else if (parts.length > 3) {
                    String id = parts[0];
                    String name = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
                    int SL = Integer.parseInt(parts[parts.length - 1]);
                    InventoryManager newIvenProduct = new InventoryManager(id, name, SL);
                    IvenProducts = Arrays.copyOf(IvenProducts, IvenProducts.length + 1);
                    IvenProducts[IvenProducts.length - 1] = newIvenProduct;
                }
            }
            return IvenProducts;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new InventoryManager[0];
        }
    }

    public void writeToFile(String fileName, InventoryManager[] IvenProducts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (InventoryManager IvenProduct : IvenProducts) {
                writer.write(IvenProduct.getProductID() + " " + IvenProduct.getProductName() + " " + IvenProduct.getCount());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void getdetail() {
        InventoryManager[] temp = readFromFile("SieuThiMini\\Inventory.txt");
        if (temp == null || temp.length == 0) {
            System.out.println("Không có dữ liệu nhân viên");
            return;
        }
        System.out.println("Danh sách tồn kho");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");
        System.out.printf("║ %-10s │ %-15s │ %-20s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");

        for (InventoryManager IvenProduct : temp) {
            System.out.printf("║ %-10s │ %-15s │ %-20s ║\n",
                    IvenProduct.getProductID(),
                    IvenProduct.getProductName(),
                    IvenProduct.getCount());
        }
        System.out.printf("╚════════════╧═══════════════╧════════════════════════════╝\n");
        System.out.println("");
    }

    public void getdetailOrder() {
        InventoryManager[] temp = readFromFile("SieuThiMini\\OrderInventory.txt");
        if (temp == null || temp.length == 0) {
            System.out.println("Không có dữ liệu nhân viên");
            return;
        }
        System.out.println("Danh sách nhập hàng");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");
        System.out.printf("║ %-10s │ %-15s │ %-20s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");

        for (InventoryManager IvenProduct : temp) {
            System.out.printf("║ %-10s │ %-15s │ %-20s ║\n",
                    IvenProduct.getProductID(),
                    IvenProduct.getProductName(),
                    IvenProduct.getCount());
        }
        System.out.printf("╚════════════╧═══════════════╧════════════════════════════╝\n");
        System.out.println("");
    }

    @Override
    public void add() {
        getdetail();

        Scanner sc = new Scanner(System.in);

        InventoryManager[] IvenProduct = readFromFile("SieuThiMini\\Inventory.txt");

        System.out.print("Số lượng hàng trong kho bạn muốn thêm vào: ");
        int n = Integer.parseInt(sc.nextLine());
        while(n<=0){
            System.out.print("Số lượng không hợp lệ !!! vui lòng nhập lại: ");
            n =Integer.parseInt(sc.nextLine());
        }
        System.out.println("=================================================================");

        for(int j=0; j<n; j++){

            System.out.print("Nhập vị trí dòng bạn muốn thêm vào danh sách:");
            int vitri = Integer.parseInt(sc.nextLine());

            String id = InventoryManager.generateRandomString(8);

            System.out.print("Nhập sản phẩm bạn muốn thêm vào kho: ");
            String name = sc.nextLine();

            System.out.print("Nhập Số lượng sản phẩm đó: ");
            String SL = sc.nextLine();

            InventoryManager newIvenProduct = new InventoryManager(id, name, Integer.parseInt(SL));

            if (vitri > IvenProduct.length) {
                IvenProduct = Arrays.copyOf(IvenProduct, IvenProduct.length + 1);
                IvenProduct[IvenProduct.length - 1] = newIvenProduct;
            } else {
                IvenProduct = Arrays.copyOf(IvenProduct, IvenProduct.length + 1);
                for (int i = IvenProduct.length - 1; i > vitri - 1; i--) {
                    IvenProduct[i] = IvenProduct[i - 1];
                }
                IvenProduct[vitri - 1] = newIvenProduct;
            }
            System.out.println("=================================================================");
        }
        writeToFile("SieuThiMini\\Inventory.txt", IvenProduct);
        System.out.println("Thêm sản phẩm thành công!");
        getdetail();

    }
    public void addOrder() {
        // Hiển thị danh sách Nhập hàng hiện tại
        getdetailOrder();
        
        // Khởi tạo scanner để nhập dữ liệu
        Scanner sc = new Scanner(System.in);
        
        // Đọc danh sách nhập hàng hiện tại từ file
        InventoryManager[] IvenProduct = readFromFile("SieuThiMini\\OrderInventory.txt");
        System.out.print("số lượng sản phẩm bạn muốn thêm vào danh sách nhập hàng: ");
        int n =Integer.parseInt(sc.nextLine());
        while(n<=0){
            System.out.print("Số lượng thêm không hợp lệ !!! vui lòng nhập lại: ");
            n = Integer.parseInt(sc.nextLine());
        }
        System.out.println("=================================================================");

        for(int j=0; j<n; j++){
        
            // Hỏi vị trí dòng cần thêm
            System.out.print("Nhập vị trí dòng bạn muốn thêm vào danh sách:");
            int vitri = Integer.parseInt(sc.nextLine());
            
            String id = InventoryManager.generateRandomString(vitri); // mã sản phẩm ngẫu nhiên
        
            // Nhập thông tin nhập hàng mới
            System.out.print("Nhập sản phẩm bạn muốn thêm vào danh sách: ");
            String name = sc.nextLine();
            
            System.out.print("Nhập Số lượng sản phẩm đó: ");
            String SL = sc.nextLine();
            
            // Tạo nhập hàng mới
            InventoryManager newIvenProduct = new InventoryManager(id, name, Integer.parseInt(SL));
            
            // Kiểm tra nếu vitri lớn hơn hoặc bằng kích thước mảng, thêm vào cuối mảng
            if (vitri > IvenProduct.length) {
                IvenProduct = Arrays.copyOf(IvenProduct, IvenProduct.length + 1);
                IvenProduct[IvenProduct.length - 1] = newIvenProduct;
            } else {
                // Mở rộng mảng departments để chứa nhập hàng mới
                IvenProduct = Arrays.copyOf(IvenProduct, IvenProduct.length + 1); // Mở rộng mảng
                for (int i = IvenProduct.length - 1; i > vitri - 1; i--) {
                    IvenProduct[i] = IvenProduct[i - 1]; // Di chuyển các phần tử phía sau
                }
                // Thêm nhập hàng mới vào mảng tại vị trí vitri - 1
                IvenProduct[vitri - 1] = newIvenProduct;
            }
            System.out.println("=================================================================");
        }
        // Ghi lại dữ liệu vào file
        writeToFile("SieuThiMini\\OrderInventory.txt", IvenProduct);
    
        // Hiển thị danh sách nhập hàng sau khi cập nhật
        System.out.println("Danh sách nhập hàng sau khi cập nhật: ");
        getdetailOrder();
    }
    
    @Override
    public void remove() {
        // Hiển thị danh sách kho
        getdetail();
        
        // Nhập mã kho muốn xóa
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã sản phẩm bạn muốn xoá: ");
        String IDremove = sc.nextLine();
        
        // Đọc danh sách kho từ file
        InventoryManager[] IvenProducts = readFromFile("SieuThiMini\\Inventory.txt");
        
        // Kiểm tra nếu không tìm thấy kho
        boolean found = false;
        
        // Duyệt qua mảng departments và đếm số lượng kho cần giữ lại
        int newSize = 0;
        for (InventoryManager IvenProduct : IvenProducts) {
            if (!IvenProduct.getProductID().equals(IDremove)) {
                newSize++; // Nếu kho không phải là kho cần xóa, tăng kích thước mảng
            }
        }
        
        if (newSize == IvenProducts.length) {
            System.out.println("Không tìm thấy kho với mã: " + IDremove);
        } else {
            // Tạo một mảng mới với kích thước giảm đi 1
            InventoryManager[] updatedIvenProduct = new InventoryManager[newSize];
            int index = 0;
    
            // Duyệt qua mảng departments và sao chép các kho không bị xóa vào mảng mới
            for (InventoryManager IvenProduct : IvenProducts) {
                if (!IvenProduct.getProductID().equals(IDremove)) {
                    updatedIvenProduct[index++] = IvenProduct;
                }
            }
    
            // Ghi lại danh sách mới vào file
            writeToFile("SieuThiMini\\Inventory.txt", updatedIvenProduct);
            System.out.println("kho với mã " + IDremove + " đã được xoá.");
        }
    
        // Hiển thị lại danh sách kho sau khi xoá
        getdetail();
    }

    public void removeOrder() {
        // Hiển thị danh sách kho
        getdetail();

        // Nhập mã kho muốn xóa
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã sản phẩm bạn muốn xoá: ");
        String IDremove = sc.nextLine();

        // Đọc danh sách kho từ file
        InventoryManager[] IvenProducts = readFromFile("SieuThiMini\\OrderInventory.txt");

        // Kiểm tra nếu không tìm thấy kho
        boolean found = false;

        // Duyệt qua mảng departments và đếm số lượng kho cần giữ lại
        int newSize = 0;
        for (InventoryManager IvenProduct : IvenProducts) {
            if (!IvenProduct.getProductID().equals(IDremove)) {
                newSize++; // Nếu kho không phải là kho cần xóa, tăng kích thước mảng
            }
        }

        if (newSize == IvenProducts.length) {
            System.out.println("Không tìm thấy nhập hàng với mã: " + IDremove);
        } else {
            // Tạo một mảng mới với kích thước giảm đi 1
            InventoryManager[] updatedIvenProduct = new InventoryManager[newSize];
            int index = 0;

            // Duyệt qua mảng departments và sao chép các kho không bị xóa vào mảng mới
            for (InventoryManager IvenProduct : IvenProducts) {
                if (!IvenProduct.getProductID().equals(IDremove)) {
                    updatedIvenProduct[index++] = IvenProduct;
                }
            }

            // Ghi lại danh sách mới vào file
            writeToFile("SieuThiMini\\OrderInventory.txt", updatedIvenProduct);
            System.out.println("Nhập hàng với mã " + IDremove + " đã được xoá.");
        }

        // Hiển thị lại danh sách kho sau khi xoá
        getdetail();
    }

    @Override
    public void ChangeInFo() {
        // Hiển thị danh sách kho hiện tại
        getdetail();

        // Khởi tạo scanner để nhập dữ liệu
        Scanner sc = new Scanner(System.in);

        // Đọc danh sách kho hiện tại từ file
        InventoryManager[] IvenProduct= readFromFile("SieuThiMini\\Inventory.txt");

        System.out.print("Nhập mã sản phẩm bạn muốn thay đổi thông tin: ");
        String Id = sc.nextLine();

        boolean found = false;

        // Duyệt qua mảng departments để tìm kho cần thay đổi thông tin
        for (int i = 0; i < IvenProduct.length; i++) {
            if (IvenProduct[i].getProductID().equals(Id)) {
                // kho được tìm thấy, tiến hành sửa thông tin
                found = true;

                System.out.println("Nhập thông tin mới cho sản phẩm (bỏ qua nếu không muốn thay đổi)");

                // Cập nhật tên kho
                System.out.print("Nhập tên sản phẩm mới: ");
                String Name = sc.nextLine();
                if (!Name.isEmpty()) {
                    IvenProduct[i].setProductName(Name); // Nếu không để trống, cập nhật tên kho mới
                }

                // Cập nhật tên người quản lý
                System.out.print("Nhập số lượng mới: ");
                String SL = sc.nextLine();
                if (!SL.isEmpty()) {
                    IvenProduct[i].setCount(Integer.parseInt(SL)); // Nếu không để trống, cập nhật tên người quản lý mới
                }

                System.out.println("Thông tin kho đã được cập nhật.");
                break; // Thoát khỏi vòng lặp khi tìm thấy kho
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy kho với mã: " + Id);
        }

        // Cập nhật lại dữ liệu vào file
        writeToFile("SieuThiMini\\Inventory.txt", IvenProduct);

        // Hiển thị lại danh sách kho sau khi cập nhật
        getdetail();
    }
    public void ChangeInFoOrder() {
        // Hiển thị danh sách kho hiện tại
        getdetail();

        // Khởi tạo scanner để nhập dữ liệu
        Scanner sc = new Scanner(System.in);

        // Đọc danh sách nhập hàng hiện tại từ file
        InventoryManager[] IvenProduct= readFromFile("SieuThiMini\\OrderInventory.txt");

        System.out.print("Nhập mã sản phẩm bạn muốn thay đổi thông tin: ");
        String Id = sc.nextLine();

        boolean found = false;

        // Duyệt qua mảng để tìm danh sách nhập hàng cần thay đổi thông tin
        for (int i = 0; i < IvenProduct.length; i++) {
            if (IvenProduct[i].getProductID().equals(Id)) {
                // nhập hàng được tìm thấy, tiến hành sửa thông tin
                found = true;

                System.out.println("Nhập thông tin mới cho danh sách nhập hàng (bỏ qua nếu không muốn thay đổi)");

                // Cập nhật tên kho
                System.out.print("Nhập tên sản phẩm mới: ");
                String Name = sc.nextLine();
                if (!Name.isEmpty()) {
                    IvenProduct[i].setProductName(Name); // Nếu không để trống, cập nhật tên sản phẩm mới
                }

                // Cập nhật tên người quản lý
                System.out.print("Nhập số lượng mới: ");
                String SL = sc.nextLine();
                if (!SL.isEmpty()) {
                    IvenProduct[i].setCount(Integer.parseInt(SL)); // Nếu không để trống, cập nhật tên người quản lý mới
                }

                System.out.println("Thông tin kho đã được cập nhật.");
                break; // Thoát khỏi vòng lặp khi tìm thấy kho
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy sản phẩm với mã: " + Id);
        }

        // Cập nhật lại dữ liệu vào file
        writeToFile("SieuThiMini\\Inventory.txt", IvenProduct);

        // Hiển thị lại danh sách kho sau khi cập nhật
        getdetail();
    }

    @Override
    public void search() {
        Scanner sc = new Scanner(System.in);
        InventoryManager[] IvenProducts = readFromFile("SieuThiMini\\Inventory.txt");

        // Yêu cầu nhập các tiêu chí tìm kiếm
        System.out.println("Nhập tiêu chí tìm kiếm (Có thể bỏ qua một số tiêu chí bằng cách nhấn Enter)");

        // Tiêu chí tìm kiếm theo mã sản phẩm
        System.out.print("Nhập mã sản phẩm (hoặc nhấn Enter để bỏ qua): ");
        String ID = sc.nextLine().trim();

        // Tiêu chí tìm kiếm theo tên sản phẩm
        System.out.print("Nhập tên sản phẩm (hoặc nhấn Enter để bỏ qua): ");
        String Name = sc.nextLine().trim();

        // Tiêu chí tìm kiếm theo số lượng sản phẩm
        System.out.print("Nhập số lượng (hoặc nhấn Enter để bỏ qua): ");
        String SL = sc.nextLine().trim();

        // Kiểm tra nếu không có tiêu chí nào được nhập
        boolean found = false;

        // In ra tiêu đề bảng
        System.out.printf("╔════════════╤══════════════╤══════════════════════════════╤══════════════════╗\n");
        System.out.printf("║ %-10s │ %-15s │ %-20s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╪══════════════════╣\n");

        // Duyệt qua mảng và tìm sản phẩm thỏa mãn ít nhất một tiêu chí
        for (InventoryManager IvenProduct : IvenProducts) {
            boolean match = false;

            // Kiểm tra mã sản phẩm nếu có
            if (!ID.isEmpty() && IvenProduct.getProductID().equalsIgnoreCase(ID)) {
                match = true;
            }

            // Kiểm tra tên sản phẩm nếu có
            if (!Name.isEmpty() && IvenProduct.getProductName().toLowerCase().contains(Name.toLowerCase())) {
                match = true;
            }

            // Kiểm tra Sản phẩm nếu có
            if (!SL.isEmpty() && IvenProduct.getCount()==Integer.parseInt(SL)) {
                match = true;
            }

            // Nếu có ít nhất một tiêu chí trùng khớp
            if (match) {
                found = true;
                // In ra thông tin sản phẩm
                System.out.printf("║ %-10s │ %-15s │ %-20s ║\n",
                    IvenProduct.getProductID(),
                    IvenProduct.getProductName(),
                    IvenProduct.getCount());

            }
        }

        // Nếu không tìm thấy sản phẩm nào nào
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm thỏa mãn điều kiện tìm kiếm.");
        }

        // Đường viền cuối bảng
        System.out.printf("╚════════════╧══════════════╧══════════════════════════════╧══════════════════╝\n");
    }

    public void searchOrder() {
        Scanner sc = new Scanner(System.in);
        InventoryManager[] IvenProducts = readFromFile("SieuThiMini\\OrderInventory.txt");

        // Yêu cầu nhập các tiêu chí tìm kiếm
        System.out.println("Nhập tiêu chí tìm kiếm (Có thể bỏ qua một số tiêu chí bằng cách nhấn Enter)");

        // Tiêu chí tìm kiếm theo mã sản phẩm
        System.out.print("Nhập mã sản phẩm (hoặc nhấn Enter để bỏ qua): ");
        String ID = sc.nextLine().trim();

        // Tiêu chí tìm kiếm theo tên sản phẩm
        System.out.print("Nhập tên sản phẩm (hoặc nhấn Enter để bỏ qua): ");
        String Name = sc.nextLine().trim();

        // Tiêu chí tìm kiếm theo số lượng sản phẩm
        System.out.print("Nhập số lượng (hoặc nhấn Enter để bỏ qua): ");
        String SL = sc.nextLine().trim();

        // Kiểm tra nếu không có tiêu chí nào được nhập
        boolean found = false;

        // In ra tiêu đề bảng
        System.out.printf("╔════════════╤══════════════╤══════════════════════════════╤══════════════════╗\n");
        System.out.printf("║ %-10s │ %-15s │ %-20s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╪══════════════════╣\n");

        // Duyệt qua mảng và tìm sản phẩm thỏa mãn ít nhất một tiêu chí
        for (InventoryManager IvenProduct : IvenProducts) {
            boolean match = false;

            // Kiểm tra mã sản phẩm nếu có
            if (!ID.isEmpty() && IvenProduct.getProductID().equalsIgnoreCase(ID)) {
                match = true;
            }

            // Kiểm tra tên sản phẩm nếu có
            if (!Name.isEmpty() && IvenProduct.getProductName().toLowerCase().contains(Name.toLowerCase())) {
                match = true;
            }

            // Kiểm tra Sản phẩm nếu có
            if (!SL.isEmpty() && IvenProduct.getCount()==Integer.parseInt(SL)) {
                match = true;
            }

            // Nếu có ít nhất một tiêu chí trùng khớp
            if (match) {
                found = true;
                // In ra thông tin sản phẩm
                System.out.printf("║ %-10s │ %-15s │ %-20s ║\n",
                    IvenProduct.getProductID(),
                    IvenProduct.getProductName(),
                    IvenProduct.getCount());

            }
        }

        // Nếu không tìm thấy sản phẩm nào nào
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm thỏa mãn điều kiện tìm kiếm.");
        }

        // Đường viền cuối bảng
        System.out.printf("╚════════════╧══════════════╧══════════════════════════════╧══════════════════╝\n");
    }
}

