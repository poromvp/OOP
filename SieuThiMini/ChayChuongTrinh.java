
import java.util.Scanner;

public class ChayChuongTrinh {
    public static void main(String[] args) {
        int choice;
        Store sieuthi = new Store();
        Scanner scanner = new Scanner(System.in);
        System.out.println("╔════════════════════════════════════════╗");
        System.out.println("║               DANG NHAP                ║");
        System.out.println("╚════════════════════════════════════════╝");
        System.out.println("        Nhap vao ma nhan vien:");
        String staffID=scanner.nextLine();
        System.out.println("        Nhap vao mat khau:");
        String passWord=scanner.nextLine();
        int login= sieuthi.login(staffID,passWord);
        if (login==2){
            //Menu cho nhap kho
            System.out.println(Store.getAccountById(staffID).getName());
            do {
                // Hiển thị menu
                System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ SIÊU THỊ MINI");
                System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Quản lý sản phẩm");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Quản lý nhập hàng.");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                System.out.print("Nhập lựa chọn của bạn: ");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manageProducts(scanner, sieuthi);
                        break;
                    case 2:
                        manageImport(scanner,sieuthi,staffID);
                        break;
                    case 0:
                        System.out.println("Đã thoát chương trình.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, hãy nhập lại.");
                        break;
                }
            } while (choice != 0);
        } else if (login==1) {
            //Menu cho nhan vien ban hang
            do {
                // Hiển thị menu
                System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ SIÊU THỊ MINI");
                System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Quản lý bán hàng");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                System.out.print("Nhập lựa chọn của bạn: ");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manageInvoice(scanner, sieuthi, staffID);
                        break;
                    case 0:
                        System.out.println("Đã thoát chương trình.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, hãy nhập lại.");
                        break;
                }
            } while (choice != 0);

        }
        else if(login==3){
            do {
                // Menu cho quan ly (full chuc nang)
                System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ SIÊU THỊ MINI");
                System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Quản lý sản phẩm");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Quản lý nhập hàng.");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Quản lý khách hàng");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Quản lý đơn hàng");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Quản lý nhân sự");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "6.", "Quản lý hóa đơn");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "7.", "Quản lý CTKM");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "8.", "Thống kê");
                System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                System.out.print("Nhập lựa chọn của bạn: ");

                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1:
                        manageProducts(scanner, sieuthi);
                        break;
                    case 2:
                        manageImport(scanner,sieuthi,staffID);
                        break;
                    case 3:
                        manageCustomer(scanner, sieuthi);
                        break;
                    case 4:
                        manageOrders(scanner, sieuthi);
                        break;
                    case 5:
                        manageStaffs(scanner, sieuthi);
                        break;
                    case 6:
                        manageInvoice(scanner, sieuthi,staffID);
                        break;
                    case 7:
                        manageDiscounnt(scanner, sieuthi);
                        break;
                    case 8:
                        // Thống kê
                        manageThongKe(scanner, sieuthi);
                        break;
                    case 0:
                        System.out.println("Đã thoát chương trình.");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, hãy nhập lại.");
                        break;
                }
            } while (choice != 0);
        }
        else {
            System.out.println("Khong the dang nhap !!!");
            return;
        }

    }

    private static void manageProducts(Scanner scanner, Store store) {
        int choice;
        //store.readFileProduct();
        do {
            System.out.printf("%-20s%s","","╔═══════════════════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-44s ║\n","","" ,"QUẢN LÝ SẢN PHẨM");
            System.out.printf("%-20s%s","","╠═══════════════════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "1.", "Thêm mới sản phẩm.");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "2.", "Xem danh sách sản phẩm.");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "3.", "Sửa sản phẩm (theo mã).");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "4.", "Xóa sản phẩm (theo mã).");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "5.", "Tìm kiếm sản phẩm (theo tên,loại,nhà cung cấp).");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "6.", "Thêm loại sản phẩm.");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "7.", "Thêm nhà cung cấp sản phẩm.");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "8.", "Xóa loại sản phẩm.");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "9.", "Xóa nhà cung cấp sản phẩm.");
            System.out.printf("%-20s║ %-3s %-49s ║\n","", "10.","Sửa loại sản phẩm (theo mã).");
            System.out.printf("%-20s║ %-3s %-49s ║\n","", "11.","Sửa nhà cung cấp sản phẩm (theo mã).");
            System.out.printf("%-20s║ %-2s %-50s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚═══════════════════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 0:
                    System.out.println("Da thoat quan li san pham.");
                    break;
                case 1:
                    store.addProduct(scanner);
                    store.writeFileProduct();
                    break;
                case 2:
                    store.productDetail();
                    break;
                case 3:
                    store.updateProduct(scanner);
                    store.writeFileProduct();
                    break;
                case 4:
                    store.removeProduct(scanner);
                    store.writeFileProduct();
                    break;
                case 5:
                    store.findProduct(scanner);
                    break;
                case 6:
                    store.addCategory(scanner);
                    store.writeFileCategory();
                    break;
                case 7:
                    store.addSupplier(scanner);
                    store.writeFileSupplier();
                    break;
                case 8:
                    store.removeCategory(scanner);
                    store.writeFileCategory();
                    break;
                case 9:
                    store.removeSupplier(scanner);
                    store.writeFileSupplier();
                    break;
                case 10:
                    store.updateCategory(scanner);
                    store.writeFileCategory();
                    break;
                case 11:
                    store.updateSupplier(scanner);
                    store.writeFileSupplier();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
                    break;
            }
        } while (choice != 0);
    }

    private static void manageImport(Scanner scanner, Store store,String staffID) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ NHẬP HÀNG");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm mới đơn nhập hàng.");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xuất đơn nhập hàng.");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Tìm đơn nhập hàng (theo mã).");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Xóa đơn nhập hàng.");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {

                case 1:
                    store.addImport(scanner,staffID);
                    store.writeFileImport();
                    store.writeFileImportDetai();
                    store.writeFileProduct();
                    break;
                case 2:
                    store.outImport(scanner);
                    break;
                case 3:
                    store.findImport(scanner);
                    break;
                case 4:
                    store.removeImport(scanner);
                    store.writeFileImport();
                    store.writeFileImportDetai();
                    break;
                case 5:
                    store.statisticImportByTotal(scanner);
                    break;
                case 6:
                    store.statisticImportByQuantity(scanner);
                    break;
//                case 7:
//                    store.averageStatistic(scanner);
//                    break;
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
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ KHÁCH HÀNG");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách KHÁCH HÀNG");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Xóa khách hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa khách hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Tìm kiếm khách hàng");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
               
                case 1:
                    // Thêm mới 1 phần tử, k phần tử
                    store.themKhachHang(scanner);
                    break;
                case 2:
                    store.xuatDanhSachKhachHang();
                    break;
                case 3:
                    // Xóa khách hàng (theo mã)
                    store.xoaKhachHang(scanner);
                    break;
                case 4:
                    // Sửa khách hàng (theo mã)
                    store.capNhatKhachHang(scanner);
                    break;
                case 5:
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
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ CTKM");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Thêm mới 1 phần tử, k phần tử");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xem danh sách CTKM");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Xóa CTKM");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa CTKM");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Tìm kiếm CTKM");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                
                case 1:
                    // Thêm mới 1 phần tử, k phần tử
                    store.themChuongTrinhKhuyenMai(scanner);
                    break;
                case 2:
                    // Xem danh sách chương trình khuyến mãi
                    store.xuatDanhSachChuongTrinhKhuyenMai();
                    break;
                case 3:
                    // Xóa chương trình khuyến mãi (theo mã)
                    store.xoaChuongTrinhKhuyenMai(scanner);
                    break;
                case 4:
                    // Sửa chương trình khuyến mãi (theo mã)
                    store.capNhatChuongTrinhKhuyenMai(scanner);
                    break;
                case 5:
                    // Tìm kiếm chương trình khuyến mãi (theo mã)
                    store.timKiemChuongTrinhKhuyenMai(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void manageThongKe(Scanner scanner, Store store) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔══════════════════════════════════════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-20s %-51s ║\n",""," " ,"Thống kê");
            System.out.printf("%-20s%s","","╠══════════════════════════════════════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "1.", "Thống kê doanh thu theo quý");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "2.", "Thống kê theo top các sản phẩm bán chạy nhất");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "3.", "Thống kê khách hàng có lượt mua nhiều nhất");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "4.", "Thống kê theo nhân viên xuất sắc nhất");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "5.", "Thong ke san pham nhap vao nhieu nhat (theo tong tien nhap)");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "6.", "Thong ke san pham nhap vao nhieu nhat (theo so luong san pham)");
            System.out.printf("%-20s║ %-2s %-69s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚══════════════════════════════════════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                
                case 1:
                    store.thongkeDoanhThu(scanner);
                    break;
                case 2:
                    store.thongkeSpBanChay(scanner);
                    break;
                case 3:
                    store.thongKeCustomerBanNhieuNhat(scanner);
                    break;
                case 4:
                    store.thongKeNhanVien();
                    break;
                case 5:
                    store.statisticImportByTotal(scanner);
                    break;
                case 6:
                    store.statisticImportByQuantity(scanner);
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);
    }

    private static void manageStaffs(Scanner scanner, Store store) {
        int choice;
        int subchoice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ NHÂN SỰ");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Quản lý nhân viên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Quản lý tài khoản nhân viên");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Quản lý ca làm");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    do{
                        System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "xuất danh sách nhân viên");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Thêm danh sách nhân viên");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Xoá nhân viên ra khỏi danh sách");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa thông tin nhân viên (theo mã)");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Tìm kiếm Nhân viên");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                        System.out.print("Lựa chọn của bạn: ");
                        subchoice = Integer.parseInt(scanner.nextLine());

                        switch (subchoice) {
                            case 1:
                            store.xuatNV();
                            break;
                            case 2: 
                            store.ThemNV();
                            break;
                            case 3:
                            store.XoaNV(); 
                            break;
                            case 4:
                            store.SuaNV();
                            break;
                            case 5: 
                            store.TimNV();
                            break;
                            case 0:
                            break;
                            default:
                            System.out.println("Lựa chọn không hợp lệ.");
                        }
                    } while (subchoice != 0);
                    break;

                case 2:
                    do {
                        System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Xuất danh sách tài khoản");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Thêm tài khoản ");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Sửa tài khoản ");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Tìm kiếm tài khoản");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                        System.out.print("Lựa chọn của bạn: ");
                        subchoice = Integer.parseInt(scanner.nextLine());
                    switch (subchoice) {
                    case 1:
                        store.XuatTK();
                        break;
                    case 2: 
                        store.ThemTK();
                        break;
                    case 3: 
                        store.SuaTK();
                        break;
                    case 4: 
                        store.TimTK();
                        break;
                    case 0:
                        break;
                        default:
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    } while (subchoice != 0);
                    break;
                    
                case 3:
                    do {
                        System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "xuất danh sách ca làm");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Thêm thu ngân vào danh sách");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Xoá thu ngân khỏi danh sách");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Sửa thông tin ca làm ");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Tìm kiếm thu ngân trong danh sách");
                        System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
                        System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
                        System.out.print("Lựa chọn của bạn: ");
                        subchoice = Integer.parseInt(scanner.nextLine());
                    switch (subchoice) {
                    case 1:
                        store.xuatThuNgan();
                        break;
                    case 2: 
                        store.themThuNgan();
                        break;
                    case 3:
                        store.xoaThuNgan();
                        break;
                    case 4: 
                        store.suaThuNgan();
                        break;
                    case 5: 
                        store.timThuNgan();
                        break;
                    case 0:
                        break;
                        default:
                        System.out.println("Lựa chọn không hợp lệ.");
                    }
                    } while (subchoice != 0);
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
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Sắp xếp ĐƠN HÀNG mới nhất");
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
                    //store.xuatOrder();
                    store.editOrder(scanner);
                    break;
                case 4:
                    store.removeOrder(scanner);
                    break;
                case 5:
                    store.sapxepdonhang();
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

    private static void manageInvoice(Scanner scanner, Store store, String staffID) {
        int choice;
        do {
            System.out.printf("%-20s%s","","╔════════════════════════════════════════╗\n");
            System.out.printf("%-20s║ %-8s %-29s ║\n","","" ,"QUẢN LÝ BÁN HÀNG");
            System.out.printf("%-20s%s","","╠════════════════════════════════════════╣\n");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "1.", "Tạo giao dịch mới");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "2.", "Xuất hóa đơn");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "3.", "Xóa hóa đơn");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "4.", "Tìm kiếm hóa đơn");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "5.", "Xem lịch sử giao dịch");
            System.out.printf("%-20s║ %-2s %-35s ║\n","", "0.", "Thoát");
            System.out.printf("%-20s%s","","╚════════════════════════════════════════╝\n");
            System.out.print("Lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    store.GiaoDichMoi(scanner);
                    break;
                case 2:
                    store.xuatHoaDon(staffID);
                    break;
                case 3:
                    store.xoaHoaDon(scanner);
                    break;
                case 4:
                    store.timHoaDon(scanner,staffID);
                    break;
                case 5:
                    store.xem();
                    break;
                case 0:
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } while (choice != 0);


    }
}