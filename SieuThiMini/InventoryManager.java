import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class InventoryManager extends Staff {
    
    // Tạo ngẫu nhiên productID
    public static String generateProductID() {
        Random a= new Random();
        String number = String.valueOf(a.nextInt(900)+100);
        return "SP"+number;
    }
    Scanner sc = new Scanner(System.in);

    // Đọc dữ liệu từ file Inventory
    @Override
    public Product[] readFromFile(String filepath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            Product[] InVenList = new Product[0];

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String id = parts[0];
                    String name = parts[1];
                    int SL = Integer.parseInt(parts[2]);
                    Product newInvenProduct = new Product(id, name, SL);
                    InVenList = Arrays.copyOf(InVenList, InVenList.length + 1);
                    InVenList[InVenList.length - 1] = newInvenProduct;
                } else if (parts.length > 3) {
                    String id = parts[0];
                    String name = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 1));
                    int SL = Integer.parseInt(parts[parts.length - 1]);
                    Product newInvenProduct = new Product(id, name, SL);
                    InVenList = Arrays.copyOf(InVenList, InVenList.length + 1);
                    InVenList[InVenList.length - 1] = newInvenProduct;
                }
            }
            return InVenList;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new Product[0];
        }
    }

    public void writeToFile(String fileName, Product[] InVenList) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Product InvenProduct : InVenList) {
                writer.write(InvenProduct.getProductID() + " " + InvenProduct.getName() + " " + InvenProduct.getQuantity());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void getdetail() {
        Product[] temp = readFromFile("SieuThiMini\\Inventory.txt");
        if (temp == null || temp.length == 0) {
            System.out.println("Không có dữ liệu tồn kho");
            return;
        }
        System.out.println("Danh sách tồn kho");
        System.out.println("╔════════════════════╤════════════════════╤════════════╗");
        System.out.printf("║ %-18s │ %-18s │ %-10s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.println("╠════════════════════╪════════════════════╪════════════╣");
    
        for (Product InvenProduct : temp) {
            System.out.printf("║ %-18s │ %-18s │ %-10d ║\n",
                    InvenProduct.getProductID(),
                    InvenProduct.getName(),
                    InvenProduct.getQuantity());
        }
        System.out.println("╚════════════════════╧════════════════════╧════════════╝");
        System.out.println("");
    }
    
    public void getdetailOrder() {
        Product[] temp = readFromFile("SieuThiMini\\OrderInventory.txt");
        if (temp == null || temp.length == 0) {
            System.out.println("Không có dữ liệu nhập hàng");
            return;
        }
        System.out.println("Danh sách nhập hàng");
        System.out.println("╔════════════════════╤════════════════════╤════════════╗");
        System.out.printf("║ %-18s │ %-18s │ %-10s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.println("╠════════════════════╪════════════════════╪════════════╣");
    
        for (Product invenProduct : temp) {
            System.out.printf("║ %-18s │ %-18s │ %-10d ║\n",
                    invenProduct.getProductID(),
                    invenProduct.getName(),
                    invenProduct.getQuantity());
        }
        System.out.println("╚════════════════════╧════════════════════╧════════════╝");
        System.out.println("");
    }
    
    @Override
    public void add() {
        getdetail();


        Product[] InvenProduct = readFromFile("SieuThiMini\\Inventory.txt");

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

            String id = InventoryManager.generateProductID();
            if(!Product.checkIDProduct(id)){
                System.out.print("mã sản phẩm bị lỗi");
                id = InventoryManager.generateProductID();
            }

            System.out.print("Nhập sản phẩm bạn muốn thêm vào kho: ");
            String name = sc.nextLine();

            System.out.print("Nhập Số lượng sản phẩm đó: ");
            String SL = sc.nextLine();

            Product newInvenProduct = new Product(id, name, Integer.parseInt(SL));

            if (vitri > InvenProduct.length) {
                InvenProduct = Arrays.copyOf(InvenProduct, InvenProduct.length + 1);
                InvenProduct[InvenProduct.length - 1] = newInvenProduct;
            } else {
                InvenProduct = Arrays.copyOf(InvenProduct, InvenProduct.length + 1);
                for (int i = InvenProduct.length - 1; i > vitri - 1; i--) {
                    InvenProduct[i] = InvenProduct[i - 1];
                }
                InvenProduct[vitri - 1] = newInvenProduct;
            }
            System.out.println("=================================================================");
        }
        writeToFile("SieuThiMini\\Inventory.txt", InvenProduct);
        System.out.println("Thêm sản phẩm thành công!");
        getdetail();

    }
    public void addOrder() {
        // Hiển thị danh sách Nhập hàng hiện tại
        getdetailOrder();
        
        // Khởi tạo scanner để nhập dữ liệu
        
        // Đọc danh sách nhập hàng hiện tại từ file
        Product[] InvenProduct = readFromFile("SieuThiMini\\OrderInventory.txt");
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
            
            String id = InventoryManager.generateProductID(); // mã sản phẩm ngẫu nhiên
            int k=0;
            while(k<InvenProduct.length){
                if(InvenProduct[k].getProductID().equals(id)){
                    id=InventoryManager.generateProductID();
                }
                k++;
            }

            // Nhập thông tin nhập hàng mới
            System.out.print("Nhập sản phẩm bạn muốn thêm vào danh sách: ");
            String name = sc.nextLine();
            
            System.out.print("Nhập Số lượng sản phẩm đó: ");
            String SL = sc.nextLine();
            
            // Tạo nhập hàng mới
            Product newInvenProduct = new Product(id, name, Integer.parseInt(SL));
            
            // Kiểm tra nếu vitri lớn hơn hoặc bằng kích thước mảng, thêm vào cuối mảng
            if (vitri > InvenProduct.length) {
                InvenProduct = Arrays.copyOf(InvenProduct, InvenProduct.length + 1);
                InvenProduct[InvenProduct.length - 1] = newInvenProduct;
            } else {
                // Mở rộng mảng departments để chứa nhập hàng mới
                InvenProduct = Arrays.copyOf(InvenProduct, InvenProduct.length + 1); // Mở rộng mảng
                for (int i = InvenProduct.length - 1; i > vitri - 1; i--) {
                    InvenProduct[i] = InvenProduct[i - 1]; // Di chuyển các phần tử phía sau
                }
                // Thêm nhập hàng mới vào mảng tại vị trí vitri - 1
                InvenProduct[vitri - 1] = newInvenProduct;
            }
            System.out.println("=================================================================");
        }
        // Ghi lại dữ liệu vào file
        writeToFile("SieuThiMini\\OrderInventory.txt", InvenProduct);
    
        // Hiển thị danh sách nhập hàng sau khi cập nhật
        System.out.println("Danh sách nhập hàng sau khi cập nhật: ");
        getdetailOrder();
    }
    
    @Override
    public void remove() {
        // Hiển thị danh sách kho
        getdetail();
        
        // Nhập mã kho muốn xóa
        System.out.print("Nhập mã sản phẩm bạn muốn xoá: ");
        String IDremove = sc.nextLine();
        
        // Đọc danh sách kho từ file
        Product[] InvenProducts = readFromFile("SieuThiMini\\Inventory.txt");

        
        // Duyệt qua mảng departments và đếm số lượng kho cần giữ lại
        int newSize = 0;
        for (Product InvenProduct : InvenProducts) {
            if (!InvenProduct.getProductID().equals(IDremove)) {
                newSize++; // Nếu kho không phải là kho cần xóa, tăng kích thước mảng
            }
        }
        
        if (newSize == InvenProducts.length) {
            System.out.println("Không tìm thấy kho với mã: " + IDremove);
        } else {
            // Tạo một mảng mới với kích thước giảm đi 1
            Product[] updatedInvenProduct = new Product[newSize];
            int index = 0;
    
            // Duyệt qua mảng departments và sao chép các kho không bị xóa vào mảng mới
            for (Product InvenProduct : InvenProducts) {
                if (!InvenProduct.getProductID().equals(IDremove)) {
                    updatedInvenProduct[index++] = InvenProduct;
                }
            }
    
            // Ghi lại danh sách mới vào file
            writeToFile("SieuThiMini\\Inventory.txt", updatedInvenProduct);
            System.out.println("kho với mã " + IDremove + " đã được xoá.");
        }
    
        // Hiển thị lại danh sách kho sau khi xoá
        getdetail();
    }

    public void removeOrder() {
        // Hiển thị danh sách kho
        getdetailOrder();

        // Nhập mã kho muốn xóa
        System.out.print("Nhập mã sản phẩm bạn muốn xoá: ");
        String IDremove = sc.nextLine();

        // Đọc danh sách kho từ file
        Product[] InvenProducts = readFromFile("SieuThiMini\\OrderInventory.txt");


        // Duyệt qua mảng departments và đếm số lượng kho cần giữ lại
        int newSize = 0;
        for (Product InvenProduct : InvenProducts) {
            if (!InvenProduct.getProductID().equals(IDremove)) {
                newSize++; // Nếu kho không phải là kho cần xóa, tăng kích thước mảng
            }
        }

        if (newSize == InvenProducts.length) {
            System.out.println("Không tìm thấy nhập hàng với mã: " + IDremove);
        } else {
            // Tạo một mảng mới với kích thước giảm đi 1
            Product[] updatedInvenProduct = new Product[newSize];
            int index = 0;

            // Duyệt qua mảng departments và sao chép các kho không bị xóa vào mảng mới
            for (Product InvenProduct : InvenProducts) {
                if (!InvenProduct.getProductID().equals(IDremove)) {
                    updatedInvenProduct[index++] = InvenProduct;
                }
            }

            // Ghi lại danh sách mới vào file
            writeToFile("SieuThiMini\\OrderInventory.txt", updatedInvenProduct);
            System.out.println("Nhập hàng với mã " + IDremove + " đã được xoá.");
        }

        // Hiển thị lại danh sách kho sau khi xoá
        getdetailOrder();
    }

    @Override
    public void ChangeInFo() {
        // Hiển thị danh sách kho hiện tại
        getdetail();

        // Khởi tạo scanner để nhập dữ liệu

        // Đọc danh sách kho hiện tại từ file
       Product[] InvenProduct= readFromFile("SieuThiMini\\Inventory.txt");

        System.out.print("Nhập mã sản phẩm bạn muốn thay đổi thông tin: ");
        String Id = sc.nextLine();

        boolean found = false;

        // Duyệt qua mảng departments để tìm kho cần thay đổi thông tin
        for (int i = 0; i < InvenProduct.length; i++) {
            if (InvenProduct[i].getProductID().equals(Id)) {
                // kho được tìm thấy, tiến hành sửa thông tin
                found = true;

                System.out.println("Nhập thông tin mới cho sản phẩm (bỏ qua nếu không muốn thay đổi)");

                // Cập nhật tên kho
                System.out.print("Nhập tên sản phẩm mới: ");
                String Name = sc.nextLine();
                if (!Name.isEmpty()) {
                    InvenProduct[i].setName(Name); // Nếu không để trống, cập nhật tên kho mới
                }

                // Cập nhật tên người quản lý
                System.out.print("Nhập số lượng mới: ");
                String SL = sc.nextLine();
                if (!SL.isEmpty()) {
                    InvenProduct[i].setQuantity(Integer.parseInt(SL)); // Nếu không để trống, cập nhật tên người quản lý mới
                }

                System.out.println("Thông tin kho đã được cập nhật.");
                break; // Thoát khỏi vòng lặp khi tìm thấy kho
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy kho với mã: " + Id);
        }

        // Cập nhật lại dữ liệu vào file
        writeToFile("SieuThiMini\\Inventory.txt", InvenProduct);

        // Hiển thị lại danh sách kho sau khi cập nhật
        getdetail();
    }
    public void ChangeInFoOrder() {
        // Hiển thị danh sách kho hiện tại
        getdetailOrder();

        // Khởi tạo scanner để nhập dữ liệu

        // Đọc danh sách nhập hàng hiện tại từ file
        Product[] InvenProduct= readFromFile("SieuThiMini\\OrderInventory.txt");

        System.out.print("Nhập mã sản phẩm bạn muốn thay đổi thông tin: ");
        String Id = sc.nextLine();

        boolean found = false;

        // Duyệt qua mảng để tìm danh sách nhập hàng cần thay đổi thông tin
        for (int i = 0; i < InvenProduct.length; i++) {
            if (InvenProduct[i].getProductID().equals(Id)) {
                // nhập hàng được tìm thấy, tiến hành sửa thông tin
                found = true;

                System.out.println("Nhập thông tin mới cho danh sách nhập hàng (bỏ qua nếu không muốn thay đổi)");

                // Cập nhật tên kho
                System.out.print("Nhập tên sản phẩm mới: ");
                String Name = sc.nextLine();
                if (!Name.isEmpty()) {
                    InvenProduct[i].setName(Name); // Nếu không để trống, cập nhật tên sản phẩm mới
                }

                // Cập nhật tên người quản lý
                System.out.print("Nhập số lượng mới: ");
                String SL = sc.nextLine();
                if (!SL.isEmpty()) {
                    InvenProduct[i].setQuantity(Integer.parseInt(SL)); // Nếu không để trống, cập nhật tên người quản lý mới
                }

                System.out.println("Thông tin kho đã được cập nhật.");
                break; // Thoát khỏi vòng lặp khi tìm thấy kho
            }
        }

        if (!found) {
            System.out.println("Không tìm thấy sản phẩm với mã: " + Id);
        }

        // Cập nhật lại dữ liệu vào file
        writeToFile("SieuThiMini\\OrderInventory.txt", InvenProduct);

        // Hiển thị lại danh sách kho sau khi cập nhật
        getdetailOrder();
    }

    @Override
    public void search() {
        getdetail();
        Scanner sc = new Scanner(System.in);
        Product[] InvenProducts = readFromFile("SieuThiMini\\Inventory.txt");


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
        System.out.println("╔════════════════════╤════════════════════╤════════════╗");
        System.out.printf("║ %-18s │ %-18s │ %-10s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.println("╠════════════════════╪════════════════════╪════════════╣");


        // Duyệt qua mảng và tìm sản phẩm thỏa mãn ít nhất một tiêu chí
        for (Product InvenProduct : InvenProducts) {
            boolean match = false;

            // Kiểm tra mã sản phẩm nếu có
            if (!ID.isEmpty() && InvenProduct.getProductID().equalsIgnoreCase(ID)) {
                match = true;
            }

            // Kiểm tra tên sản phẩm nếu có
            if (!Name.isEmpty() && InvenProduct.getName().toLowerCase().contains(Name.toLowerCase())) {
                match = true;
            }

            // Kiểm tra Sản phẩm nếu có
            if (!SL.isEmpty() && InvenProduct.getQuantity()==Integer.parseInt(SL)) {
                match = true;
            }

            // Nếu có ít nhất một tiêu chí trùng khớp
            if (match) {
                found = true;
                // In ra thông tin sản phẩm
                System.out.printf("║ %-18s │ %-18s │ %-10s ║\n",
                    InvenProduct.getProductID(),
                    InvenProduct.getName(),
                    InvenProduct.getQuantity());

            }
        }

        // Nếu không tìm thấy sản phẩm nào nào
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm thỏa mãn điều kiện tìm kiếm.");
        }

        // Đường viền cuối bảng
        System.out.println("╚════════════════════╧════════════════════╧════════════╝");
    }

    public void searchOrder() {

        getdetailOrder();
        Scanner sc = new Scanner(System.in);
        Product[] InvenProducts = readFromFile("SieuThiMini\\OrderInventory.txt");

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
        System.out.println("╔════════════════════╤════════════════════╤════════════╗");
        System.out.printf("║ %-18s │ %-18s │ %-10s ║\n", "Mã sản phẩm", "Tên sản phẩm", "Số lượng");
        System.out.println("╠════════════════════╪════════════════════╪════════════╣");
        // Duyệt qua mảng và tìm sản phẩm thỏa mãn ít nhất một tiêu chí
        for (Product InvenProduct : InvenProducts) {
            boolean match = false;

            // Kiểm tra mã sản phẩm nếu có
            if (!ID.isEmpty() && InvenProduct.getProductID().equalsIgnoreCase(ID)) {
                match = true;
            }

            // Kiểm tra tên sản phẩm nếu có
            if (!Name.isEmpty() && InvenProduct.getName().toLowerCase().contains(Name.toLowerCase())) {
                match = true;
            }

            // Kiểm tra Sản phẩm nếu có
            if (!SL.isEmpty() && InvenProduct.getQuantity()==Integer.parseInt(SL)) {
                match = true;
            }

            // Nếu có ít nhất một tiêu chí trùng khớp
            if (match) {
                found = true;
                // In ra thông tin sản phẩm
                System.out.printf("║ %-18s │ %-18s │ %-10s ║\n",
                    InvenProduct.getProductID(),
                    InvenProduct.getName(),
                    InvenProduct.getQuantity());

            }
        }

        // Nếu không tìm thấy sản phẩm nào nào
        if (!found) {
            System.out.println("Không tìm thấy sản phẩm thỏa mãn điều kiện tìm kiếm.");
        }

        // Đường viền cuối bảng
        System.out.println("╚════════════════════╧════════════════════╧════════════╝");
    }
}

