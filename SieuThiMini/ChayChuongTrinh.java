

import java.util.Scanner;

public class ChayChuongTrinh {
    public static void main(String[] args) {
        int choice;
        Store sieuthi = new Store();
        Scanner scanner = new Scanner(System.in);
        do {
            // Hiển thị menu
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ SIÊU THỊ MINI");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Quản lý sản phẩm");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Quản lý khách hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Quản lý đơn hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Quản lý nhân sự");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Quản lý hóa đơn");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Quản lý CTKM");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Nhập lựa chọn của bạn: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageProducts(scanner, sieuthi);
                    break;
                case 2:
                    manageCustomer(scanner, sieuthi);
                    break;
                case 3:
                    manageOrders(scanner, sieuthi);
                    break;
                case 4:
                    manageStaffs(scanner, sieuthi);
                    break;
                case 5:
                    manageReceipt(scanner, sieuthi);
                    break;
                case 6:
                    manageDiscounnt(scanner, sieuthi);
                case 0:
                    System.out.println("Đã thoát chương trình.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ, hãy nhập lại.");
                    break;
            }
        } while (choice != 0);

        scanner.close();
    }

    private static void manageProducts(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ SẢN PHẨM");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách sản phẩm");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa sản phẩm (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Xóa sản phẩm (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Tìm kiếm sản phẩm");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    Category.readCategoryFromFile("category.txt");
                    Supplier.readSupplierFromFile("supplier.txt");
                    Product.readProductsFromFile("product.txt");
                    System.out.println("Đã thêm "+Product.getCnt()+" sản phẩm.");
                    break;
                case 2:
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                            "Product ID", "Name", "Price", "Quantity", "Category", "Supplier");
                    System.out.printf("%-20s %-20s %-20s %-20s %-20s %-20s\n",
                            "-------------------", "-------------------", "-------------------",
                            "-------------------", "-------------------", "-------------------");
                    for (int i=0;i<Product.getCnt();i++){
                        Product.productList[i].getDetails();
                    }
                    break;
                case 3:
                    System.out.println("So phan tu ban muon them la: ");
                    int n=Integer.parseInt(scanner.nextLine());
                    for (int i=0;i<n;i++){
                        Product.addProduct();
                    }
                    break;
                case 4:
                    System.out.println("Nhap vao id san pham muon sua.");
                    String ud= scanner.nextLine();
                    Product.upDateProduct(ud);
                case 5:
                    System.out.println("Nhap vao id san pham muon xoa.");
                    String rm= scanner.nextLine();
                    Product.deleteProduct(rm);
                    break;
                case 6:
                    System.out.println("Nhap tu khoa muon tim kiem");
                    String find= scanner.nextLine();
                    Product.Find(find);
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private static void manageCustomer(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ KHÁCH HÀNG");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách KHÁCH HÀNG");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa khách hàng (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Xóa khách hàng (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Tìm kiếm khách hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên người dùng: ");
                    String username = scanner.nextLine();
                    System.out.print("Nhập mật khẩu: ");
                    String password = scanner.nextLine();
                    // UserAccount account = new UserAccount(username, password); // Tạo tài khoản
                    // store.addUserAccount(account);
                    System.out.println("Đã thêm tài khoản.");
                    break;
                case 2:
                     store.xuatDanhSachKhachHang();
                    break;
                case 3:
                    // Thêm mới 1 phần tử, k phần tử
                    break;
                case 4:
                    // Sửa khách hàng (theo mã)
                    break;
                case 5:
                    // Xóa khách hàng (theo mã)
                    break;
                case 6:
                    // Tìm kiếm khách hàng (theo mã)
                    store.timKhachHang(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void manageDiscounnt(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ CHƯƠNG TRÌNH KHUYẾN MÃI");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách CTKM");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa CTKM (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Xóa CTKM (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Tìm kiếm CTKM (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên người dùng: ");
                    String username = scanner.nextLine();
                    System.out.print("Nhập mật khẩu: ");
                    String password = scanner.nextLine();
                    // UserAccount account = new UserAccount(username, password); // Tạo tài khoản
                    // store.addUserAccount(account);
                    System.out.println("Đã thêm tài khoản.");
                    break;
                case 2:
                    // Xem danh sách chương trình khuyến mãi
                    store.xuatDanhSachChuongTrinhKhuyenMai();
                    break;
                case 3:
                    // Thêm mới 1 phần tử, k phần tử
                    break;
                case 4:
                    // Sửa chương trình khuyến mãi (theo mã)
                    break;
                case 5:
                    // Xóa chương trình khuyến mãi (theo mã)
                    break;
                case 6:
                    // Tìm kiếm chương trình khuyến mãi (theo mã)
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void manageOrders(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ ĐƠN HÀNG");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách ĐƠN HÀNG");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Sửa ĐƠN HÀNG (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Xóa ĐƠN HÀNG (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Thống kê ĐƠN HÀNG");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Tìm kiếm ĐƠN HÀNG");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    store.addOrder(scanner);
                    break;
                case 2:
                    store.xuatOrder();
                    break;
                case 3:
                    store.xuatOrder();
                    store.editOrder(scanner);
                    break;
                case 4:
                    store.removeOrder(scanner);
                    break;
                case 5:
                    store.thongkeOrder(scanner);
                    break;
                case 6:
                    store.timkiem(scanner);
                    break;
                case 0:
                    System.out.println("Đã thoát");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private static void manageStaffs(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ NHÂN SỰ");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Quản lý nhân viên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Quản lý phòng ban");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Quản lý ca làm");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Quản lý kho hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    do{
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm nhân viên");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xóa nhân viên");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.print("Lựa chọn của bạn: ");
                        choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                            String filepath = "dsnv.txt";
                            Staff Mana = new Manager();
                            //Mana.readFromFile(filepath);
                            //Mana.writeToFile(filepath);
                            break;
                            case 2: 
                            filepath = "dsnv.txt";
                            Staff Manb = new Manager();
                            //Manb.readFromFile(filepath);
                            ((Manager)Manb).removeStaff();
                            break;
                            case 0:
                            break;
                            default:
                            System.out.println("Lựa chọn không hợp lệ.");
                        }
                    } while (choice != 0);
                    break;

                case 2:
                    do {
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm nhân viên vào phòng ban");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xóa nhân viên ra khỏi phòng ban");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.print("Lựa chọn của bạn: ");
                        choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                    case 1:
                        System.out.println("danh sach hien tai: ");
                        String filepath = "DepartmentStaffList.txt";
                        Staff DepA = new Department();
                        //DepA.readFromFile(filepath);
                        //DepA.writeToFile(filepath);
                        break;
                    case 2: 
                        filepath = "DepartmentStaffList.txt";
                        Staff DepB= new Department();
                        //DepB.readFromFile(filepath);
                        ((Department)DepB).removeStaffFromDepartment();
                        break;
                    case 0:
                        break;
                        default:
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    } while (choice != 0);
                    break;
                    
                case 3:
                    do {
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Sắp xếp lịch làm");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xóa lịch làm");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.print("Lựa chọn của bạn: ");
                        choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                    case 1:
                        String filepath= "CashierList.txt";
                        Staff CasA = new Cashier();
                        CasA.readFromFile(filepath);
                        CasA.writeToFile(filepath);
                        break;
                    case 2: 
                        filepath= "CashierList.txt";
                        Staff CasB= new Cashier();
                        CasB.readFromFile(filepath);
                        ((Cashier)CasB).removeShift();
                        break;
                    case 0:
                        break;
                        default:
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    } while (choice != 0);
                    break;
                
                case 4:     
                    do {
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Hàng trong kho");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Đặt hàng");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.print("Lựa chọn của bạn: ");
                        choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                    case 1:
                        do {
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm hàng trong kho");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xóa hàng trong kho");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                            System.out.print("Lựa chọn của bạn: ");
                            choice = Integer.parseInt(scanner.nextLine());
                        switch (choice) {
                            case 1:
                            String filepath = "Inventory.txt";
                            Staff Ivenadd = new InventoryManager();
                            //Ivenadd.readFromFile(filepath);
                            //Ivenadd.writeToFile(filepath);
                            break;
                            case 2:
                            filepath = "Inventory.txt";
                            Staff Ivenrev = new InventoryManager();
                            //Ivenrev.readFromFile(filepath);
                            ((InventoryManager)Ivenrev).removeIvenProduct();
                            break;
                            case 0:
                                 break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ.");
                            }
                        } while (choice != 0);
                        break;

                    case 2: 
                        do {
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm hàng trong danh sách đặt hàng");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xóa hàng trong danh sách đặt hàng");
                            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                            System.out.print("Lựa chọn của bạn: ");
                            choice = Integer.parseInt(scanner.nextLine());
                            switch (choice) {
                            case 1:
                            String filepath = "OrderInventory.txt";
                            Staff IvenOrderadd = new InventoryManager();
                            //((InventoryManager)IvenOrderadd).readFromFileOrder(filepath);
                            ((InventoryManager)IvenOrderadd).OrderInventory();
                            break;
                            case 2:
                            filepath = "OrderInventory.txt";
                            Staff IvenOrderrev = new InventoryManager();
                            //((InventoryManager)IvenOrderrev).readFromFileOrder(filepath);
                            ((InventoryManager)IvenOrderrev).removeOrderProduct();
                            break;
                            case 0:
                                 break;
                            default:
                                System.out.println("Lựa chọn không hợp lệ.");
                            }
                        } while (choice != 0);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    }while(choice!=0);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }



    private static void manageReceipt(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ HÓA ĐƠN");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách HÓA ĐƠN");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa NHÂN SỰ (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Xóa NHÂN SỰ (theo mã)");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Tìm kiếm NHÂN SỰ");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Nhập tổng số tiền: ");
                    double totalAmount = Double.parseDouble(scanner.nextLine());
                    // Order order = new Order(totalAmount); // Tạo đơn hàng
                    // store.addOrder(order);
                    System.out.println("Đã thêm đơn hàng.");
                    break;
                case 2:
                    // store.listOrders();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }
}