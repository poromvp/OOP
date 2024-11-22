

import java.util.Scanner;

public class ChayChuongTrinh {
    public static void main(String[] args) {
        int choice;
        Store sieuthi = new Store();
        Scanner scanner = new Scanner(System.in);
        do {
            // Hiển thị menu
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ SIÊU THỊ MINI");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Quản lý sản phẩm");
            System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Quản lý khách hàng");
            System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Quản lý đơn hàng");
            System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Quản lý nhân sự");
            System.out.printf("%-20s| %-2s %-35s |\n","", "5.", "Quản lý hóa đơn");
            System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","|========================================|\n");
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
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ SẢN PHẨM");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xem danh sách sản phẩm");
            System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Sửa sản phẩm (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "5.", "Xóa sản phẩm (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "6.", "Tìm kiếm sản phẩm");
            System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.print("Nhập tên sản phẩm: ");
                    String name = scanner.nextLine();
                    System.out.print("Nhập giá sản phẩm: ");
                    double price = Double.parseDouble(scanner.nextLine());
                    // Product product = new Product(name, price); // Tạo sản phẩm
                    // store.addProduct(product);
                    System.out.println("Đã thêm sản phẩm.");
                    break;
                case 2:
                    // store.listProducts();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void manageCustomer(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ KHÁCH HÀNG");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xem danh sách KHÁCH HÀNG");
            System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Sửa khách hàng (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "5.", "Xóa khách hàng (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "6.", "Tìm kiếm khách hàng");
            System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","|========================================|\n");
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
                    // store.listUserAccounts();
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
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ ĐƠN HÀNG");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xem danh sách ĐƠN HÀNG");
            System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Sửa ĐƠN HÀNG (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "5.", "Xóa ĐƠN HÀNG (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "6.", "Tìm kiếm ĐƠN HÀNG");
            System.out.printf("%-20s| %-2s %-35s |\n","", "7.", "Thống kê ĐƠN HÀNG");
            System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    
                    break;
                case 2:
                    store.xuatOrder();
                    break;
                case 4:
                    store.xuatOrder();
                    store.editOrder(scanner);
                    break;
                case 7:
                    store.thongkeOrder(scanner);
                    break;
                case 0:
                    System.out.println("Đã thoát");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void manageStaffs(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ NHÂN SỰ");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xem danh sách NHÂN SỰ");
            System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Sửa NHÂN SỰ (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "5.", "Xóa NHÂN SỰ (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "6.", "Tìm kiếm NHÂN SỰ");
            System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","|========================================|\n");
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

    private static void manageReceipt(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-8s %-29s |\n","","" ,"QUẢN LÝ HÓA ĐƠN");
            System.out.printf("%-20s%s","","|========================================|\n");
            System.out.printf("%-20s| %-2s %-35s |\n","", "1.", "Nhập n phần tử mới đầu tiên");
            System.out.printf("%-20s| %-2s %-35s |\n","", "2.", "Xem danh sách HÓA ĐƠN");
            System.out.printf("%-20s| %-2s %-35s |\n","", "3.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s| %-2s %-35s |\n","", "4.", "Sửa NHÂN SỰ (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "5.", "Xóa NHÂN SỰ (theo mã)");
            System.out.printf("%-20s| %-2s %-35s |\n","", "6.", "Tìm kiếm NHÂN SỰ");
            System.out.printf("%-20s| %-2s %-35s |\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","|========================================|\n");
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