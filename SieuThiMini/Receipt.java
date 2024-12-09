import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Date;

import java.util.Random;

public class Receipt implements QLFile {
    public Transaction giaodich;
    public Discount discount;
    public String maHoaDon;
    public Manager NV;
    public Receipt() {
        NV = new Manager();
        giaodich = new Transaction();
        discount = new Discount();
    }

    public Receipt(String maHoaDon, Transaction giaodich) {
        this.maHoaDon = maHoaDon;
        this.giaodich = giaodich;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void inHoaDon(String staffID) {
        int i = 0;
        System.out.printf("╔══════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%28s%-20s%18s║\n", " ", getMaHoaDon(), " ");
        System.out.printf("╠══════════════════════════════════════════════════════════════════╣\n");
        System.out.printf("║  Ngày thanh toán: %45s  ║\n", giaodich.donhang.getOrderDate());
        System.out.printf("║  Th.Ngân: %-55s║\n", NV.getName());
        System.out.printf("║  Tên KhH: %-15s%38s  ║\n", giaodich.donhang.customer.getName(),
                "Mã KhH: " + giaodich.donhang.customer.getCustomerID());
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        System.out.printf("║  Tên Sản Phẩm           SL        Giá          Tổng Tiền         ║\n");

        for (Product pr : giaodich.donhang.product) {
            System.out.printf("║  %-20s   %-5d    %,-10d   %,-19.2f║\n", pr.getName(), pr.getQuantity(), pr.getPrice(),
                    (double) pr.getPrice() * pr.getQuantity());
            if (i < giaodich.donhang.product.length - 1) {
                System.out.printf("║  ..............................................................  ║\n");
            }
            i++;
        }
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        // Cập nhật lại phần tính toán tổng tiền
        System.out.printf("║  Thành Tiền                                   %,-19.2f║\n",
                giaodich.donhang.calculateTotalAmount());
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        double tien_giam_theo_point=giaodich.donhang.customer.isLoyaltyPoints(giaodich.donhang);
            System.out.printf("║  Sau Khi Giảm Theo Point                      %,-19.2f║\n",
                    tien_giam_theo_point);
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        if (discount.getDiscountPercentage() != 0) { // Nếu có chương trình giảm giá
            double tien_sau_khi_giam_gia = tien_giam_theo_point
                    * (1.0 - discount.getDiscountPercentage() / 100);
            System.out.printf("║  %-23s                      %,-19.2f║\n", discount.getName(),
                    tien_sau_khi_giam_gia);
            System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
            System.out.printf("║  Tổng Thanh Toán (Có VAT)                     %,-19.2f║\n",
                    tien_sau_khi_giam_gia + Order.calculateVAT(
                            tien_sau_khi_giam_gia));
            System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
            System.out.printf("║  %-64s║\n", giaodich.phuongThucThanhToan.xuLyThanhToan());
            System.out.printf("║  Tiền Thối Lại                                %,-19.2f║\n",
                    giaodich.phuongThucThanhToan.getSoTien() - (tien_sau_khi_giam_gia + Order.calculateVAT(
                            tien_sau_khi_giam_gia)));
        } else {
            double so_tien_sau_VAT = tien_giam_theo_point
                    + Order.calculateVAT(tien_giam_theo_point);
            System.out.printf("║  Tổng Thanh Toán (Có VAT)                     %,-19.2f║\n", so_tien_sau_VAT);
            System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
            System.out.printf("║  %-64s║\n", giaodich.phuongThucThanhToan.xuLyThanhToan());
            System.out.printf("║  Tiền Thối Lại                                %,-19.2f║\n",
                    giaodich.phuongThucThanhToan.getSoTien() -
                            so_tien_sau_VAT);
        }
        System.out.printf("╚══════════════════════════════════════════════════════════════════╝\n");
    }

    public static String generateRandomString(int length, String charSet) {
        Random random = new Random();
        StringBuilder randomString = new StringBuilder(length);

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(charSet.length());
            randomString.append(charSet.charAt(index));
        }

        return randomString.toString();
    }

    public static Receipt[] taogiaodich(Receipt[] receipts, Order or, Scanner scanner) {
        receipts = Arrays.copyOf(receipts, receipts.length + 1);
        int i = receipts.length - 1;
        receipts[i] = new Receipt();

        System.out.print("Nhập mã nhân viên: ");
        String idNV=scanner.nextLine();
        while(Manager.getManagerbyID(idNV)==null){
            System.out.println("Mã Nhân Viên Này Không Có Trong Danh Sách, Hãy Nhập Lại!");
            idNV=scanner.nextLine();
        }
        receipts[i].NV=new Manager(Manager.getManagerbyID(idNV).getStaffID(),Manager.getManagerbyID(idNV).getName(),Manager.getManagerbyID(idNV).getRole(),Manager.getManagerbyID(idNV).getSalary(),Manager.getManagerbyID(idNV).getContactNum());

        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        receipts[i].setMaHoaDon("HD" + Receipt.generateRandomString(length, charSet));
        receipts[i].giaodich.donhang = new Order(
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getOrderId(),
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getOrderDate(),
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getCustomer(),
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getProductList());

        System.out.print("Phuong thuc thanh toan?\n 1.Thẻ/Chuyển Khoản \t 2.Tiền mặt\n→ ");
        int pt = 0;
        boolean isValid = false;
        while (!isValid) {
            try {
                pt = Integer.parseInt(scanner.nextLine());
                if (pt == 1 || pt == 2) {
                    isValid = true; // Đúng giá trị mong muốn
                } else {
                    System.out.print("Không hợp lệ, hãy nhập lại: ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Lỗi: Vui lòng nhập số (1 hoặc 2): "); // Xử lý trường hợp nhập chữ, như là abxcasdf
            }
        }

        String daytmp = receipts[i].giaodich.donhang.getOrderDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày
        Date date;
        try {
            date = sdf.parse(daytmp); // Chuyển đổi từ String thành Date
        } catch (ParseException e) {
            date = null;
            System.out.println("Lỗi không định dạng được ngày: " + e.getMessage());
        }
        if (receipts[i].discount.getDiscountByDay(date) == null) { // nếu đối tượng này null tức là không nằm trong tg
                                                                   // discount nên
            receipts[i].discount.setDiscountPercentage(0); // phần trăm giảm = 0
        } else {
            receipts[i].discount = receipts[i].discount.getDiscountByDay(date);
        }
        // Tiền hàng sau khi giảm = (Tổng tiền hàng sau khi giảm theo point) * (1 - Phần trăm giảm) + Tiền VAT
        double tien,
                tien_hang_sau_khi_giam = (receipts[i].giaodich.donhang.customer.isLoyaltyPoints(receipts[i].giaodich.donhang)
                        )
                        * (1.0 - receipts[i].discount.getDiscountPercentage() / 100.0);
        tien_hang_sau_khi_giam += Order.calculateVAT(tien_hang_sau_khi_giam);

        if (pt == 1) {
            System.out.print("Nhập STK: ");
            String cardId = scanner.nextLine();
            tien = tien_hang_sau_khi_giam;
            receipts[i].giaodich.phuongThucThanhToan = new CardPayment(tien, cardId); // Đa hình
        } else {
            System.out.printf("Tổng tiền là: %,.2f\n", tien_hang_sau_khi_giam);
            System.out.println("Nhập tiền khách đưa: ");
            tien = Double.parseDouble(scanner.nextLine());
            while (tien < tien_hang_sau_khi_giam) {
                System.out.printf("Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền hàng (%,.2f), hãy nhập lại: ",
                        tien_hang_sau_khi_giam);
                tien = Double.parseDouble(scanner.nextLine());
            }
            receipts[i].giaodich.phuongThucThanhToan = new CashPayment(tien); // Đa hình
        }
        receipts[i].giaodich.getPhuongThucThanhToan().setSoTien(tien);

        return receipts;
    }

    public static void nhaphoadon(Scanner scanner, Receipt receipt) {
        System.out.print("Nhập mã đơn hàng để tạo hóa đơn và tính tiền: ");
        String orId = scanner.nextLine();
        while (receipt.giaodich.donhang.getOrderbyID(orId) == null) {
            System.out.print("Mã đơn hàng mà bạn nhập không có, hãy nhập lại: ");
            orId = scanner.nextLine();
        }
        receipt.giaodich.donhang = receipt.giaodich.donhang.getOrderbyID(orId);

        System.out.print("Phuong thuc thanh toan?\n 1.Thẻ/Chuyển Khoản \t 2.Tiền mặt\n→");
        int pt = Integer.parseInt(scanner.nextLine());
        while (pt != 1 && pt != 2) {
            System.out.print("Không hợp lệ, hãy nhập lại: ");
            pt = Integer.parseInt(scanner.nextLine());
        }

        String daytmp = receipt.giaodich.donhang.getOrderDate();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày
        Date date;
        try {
            date = sdf.parse(daytmp); // Chuyển đổi từ String thành Date
        } catch (ParseException e) {
            date = null;
            System.out.println("Lỗi không định dạng được ngày: " + e.getMessage());
        }
        if (receipt.discount.getDiscountByDay(date) == null) {
            receipt.discount.setDiscountPercentage(0);
        } else {
            receipt.discount = receipt.discount.getDiscountByDay(date);
        }
        double tien, tien_hang_sau_khi_giam = (receipt.giaodich.donhang.calculateTotalAmount()
                + Order.calculateVAT(receipt.giaodich.donhang.calculateTotalAmount()))
                * (1.0 - receipt.discount.getDiscountPercentage() / 100.0);

        if (pt == 1) {
            System.out.print("Nhập STK: ");
            String cardId = scanner.nextLine();
            System.out.println("Nhập số tiền khách chuyển: ");
            tien = Double.parseDouble(scanner.nextLine());
            while (tien < tien_hang_sau_khi_giam) {
                System.out.print("Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền hàng (" + tien_hang_sau_khi_giam
                        + "), hãy nhập lại: ");
                tien = Double.parseDouble(scanner.nextLine());
            }
            receipt.giaodich.phuongThucThanhToan = new CardPayment(tien, cardId); // Đa hình
        } else {
            System.out.println("Nhập tiền khách đưa: ");
            tien = Double.parseDouble(scanner.nextLine());
            while (tien < tien_hang_sau_khi_giam) {
                System.out.print("Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền hàng(" + tien_hang_sau_khi_giam
                        + "), hãy nhập lại: ");
                tien = Double.parseDouble(scanner.nextLine());
            }
            receipt.giaodich.phuongThucThanhToan = new CashPayment(tien); // Đa hình
        }
        receipt.giaodich.getPhuongThucThanhToan().setSoTien(tien);
    }

    public static Receipt[] themhoadon(Receipt[] receipts, Scanner scanner, Order[] orderList) {
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        System.out.print("Bạn muốn thêm bao nhiêu hóa đơn?: ");
        int n = Integer.parseInt(scanner.nextLine());
        receipts = Arrays.copyOf(receipts, receipts.length + n);
        for (int j = receipts.length - n; j < receipts.length; j++) {
            receipts[j] = new Receipt();
            receipts[j].setMaHoaDon("HD" + Receipt.generateRandomString(length, charSet));
            nhaphoadon(scanner, receipts[j]);
        }
        System.out.println("Đã thêm hóa đơn thành công");
        return receipts;
    }

    public static Receipt[] xoahoadon(Receipt[] receipts, Scanner scanner) {
        System.out.println("Nhập mã hóa đơn bạn muốn xóa: ");
        boolean flag = false; // Tạo lính canh để kiểm tra nếu sau khi duyệt mà nó còn false thì sẽ cho nhập
                              // lại cho đúng
        byte choice = 0, so_lan_thu = 0;
        do {
            so_lan_thu++;
            String temp = scanner.nextLine();
            int pos = 0; // Vị trí của đơn hàng trong danh sách
            for (int i = 0; i < receipts.length; i++) {
                if (receipts[i].getMaHoaDon().equals(temp)) {
                    pos = i;
                    flag = true;
                    break;
                }
            }
            if (flag) {
                if (pos == receipts.length - 1) {
                    receipts = Arrays.copyOf(receipts, receipts.length - 1);
                    System.out.println("Đã xóa hóa đơn");
                } else {
                    for (int i = pos; i < receipts.length - 1; i++) {
                        receipts[i] = receipts[i + 1];
                    }
                    receipts = Arrays.copyOf(receipts, receipts.length - 1);
                    System.out.println("Đã xóa hóa đơn");
                }
                break;
            }
            if (so_lan_thu > 2) {
                System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                break;
            }
            System.out.print(
                    "Mã Hóa Đơn Mà Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Xóa Hóa Đơn Không?\n "
                            + "1.Có    0.Không");
            do {
                System.out.print("\n→ ");
                choice = Byte.parseByte(scanner.nextLine());
                switch (choice) {
                    case 1:
                        System.out.println("Vậy Hãy Nhập Lại Mã Hóa Đơn Chính Xác");
                        System.out.print("→ ");
                        break;
                    case 0:
                        System.out.println("Đã thoát!");
                        flag = true;
                        break;
                    default:
                        System.out.println("Không hợp lệ hãy nhập lại!");
                        break;
                }
            } while (choice != 0 && choice != 1);
        } while (!flag);
        return receipts;
    }

    public static Receipt[] suahoadon(Receipt[] receipts, String editID, Scanner scanner, String staffID) {
        // Tìm kiếm hóa đơn theo ID
        Receipt hoadon = null;
        for (Receipt rc : receipts) {
            if (rc.getMaHoaDon().equals(editID)) {
                hoadon = rc;
                break;
            }
        }

        if (hoadon != null) {
            hoadon.inHoaDon(staffID);
            System.out.println("\nNhấn Enter để giữ nguyên thông tin hiện tại.");

            // Cập nhật mã hóa đơn
            System.out.print("Mã hóa đơn: ");
            String receiptId = scanner.nextLine();
            if (!receiptId.trim().isEmpty()) {
                hoadon.setMaHoaDon(receiptId);
            }

            // Cập nhật mã đơn hàng
            System.out.print("Mã đơn hàng: ");
            String orderId = scanner.nextLine();
            if (!orderId.trim().isEmpty()) {
                if (hoadon.giaodich.donhang.getOrderbyID(orderId) == null) {
                    hoadon.giaodich.donhang = new Order();
                    hoadon.giaodich.donhang.setOrderId("NONE");
                    hoadon.giaodich.donhang.setOrderDate("NONE");
                    hoadon.giaodich.donhang.customer.setCustomerID(0);
                    hoadon.giaodich.donhang.customer.setLoyaltyPoints(0);
                    hoadon.giaodich.donhang.customer.setName("NONE");
                    hoadon.giaodich.donhang.customer.setContactNumber("NONE");
                    hoadon.giaodich.donhang.product = new Product[1];
                    hoadon.giaodich.donhang.product[0] = new Product();
                    hoadon.giaodich.phuongThucThanhToan = new CashPayment(0);
                } else {
                    hoadon.giaodich.donhang = hoadon.giaodich.donhang.getOrderbyID(orderId);
                }
            }

            // Cập nhật phương thức thanh toán và tiền khách đưa/chuyển
            System.out.println("Chọn phương thức thanh toán:");
            System.out.println("1. Thẻ/Chuyển khoản");
            System.out.println("2. Tiền mặt");
            System.out.print("Nhấn Enter để giữ nguyên phương thức thanh toán hiện tại hoặc chọn 1 hoặc 2: ");
            String tmpInput = scanner.nextLine();

            double tien_hang_sau_khi_giam = (hoadon.giaodich.donhang.calculateTotalAmount()
                    + Order.calculateVAT(hoadon.giaodich.donhang.calculateTotalAmount()))
                    * (1.0 - hoadon.discount.getDiscountPercentage() / 100.0);
            if (!tmpInput.trim().isEmpty()) {
                byte tmp = Byte.parseByte(tmpInput);
                if (tmp == 1) { // Thẻ/Chuyển khoản
                    System.out.print("Số tiền khách chuyển: ");
                    String newMoney = scanner.nextLine();

                    double newTien;
                    if (newMoney.trim().isEmpty()) {
                        newTien = hoadon.giaodich.getPhuongThucThanhToan().getSoTien();
                    } else {

                        newTien = Double.parseDouble(newMoney);
                        while (newTien < tien_hang_sau_khi_giam) {
                            System.out.print("Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền hàng("
                                    + tien_hang_sau_khi_giam + "), hãy nhập lại: ");
                            newTien = Double.parseDouble(scanner.nextLine());
                        }
                    }

                    System.out.print("Số thẻ: ");
                    String newidthe = scanner.nextLine();

                    String soThe;
                    if (newidthe.trim().isEmpty()) {
                        if (hoadon.giaodich.getPhuongThucThanhToan() instanceof CardPayment) {
                            soThe = ((CardPayment) hoadon.giaodich.getPhuongThucThanhToan()).getSoThe();
                        } else {
                            soThe = "UNKNOWN";
                        }
                    } else {
                        soThe = newidthe;
                    }

                    hoadon.giaodich.phuongThucThanhToan = new CardPayment(newTien, soThe);

                } else if (tmp == 2) { // Tiền mặt
                    System.out.print("Số tiền khách đưa: ");
                    String newMoney = scanner.nextLine();

                    double newTien;
                    if (newMoney.trim().isEmpty()) {
                        newTien = hoadon.giaodich.getPhuongThucThanhToan().getSoTien();
                    } else {
                        newTien = Double.parseDouble(newMoney);
                        while (newTien < tien_hang_sau_khi_giam) {
                            System.out.print("Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền hàng("
                                    + tien_hang_sau_khi_giam + "), hãy nhập lại: ");
                            newTien = Double.parseDouble(scanner.nextLine());
                        }
                    }

                    hoadon.giaodich.phuongThucThanhToan = new CashPayment(newTien);
                }
            }
        } else {
            System.out.println("Không tìm thấy hóa đơn với mã: " + editID);
        }
        System.out.println("Đã cập nhật thông tin");
        return receipts; // Trả về danh sách khách hàng sau khi cập nhật
    }

    public static void locHoaDon(Scanner scanner, Receipt[] receipts, String staffID) {
        System.out.println();
        System.out.printf("%20s╔═════════════════════════════════════════════════════════════════╗\n", "");
        System.out.printf("%20s║                        TÌM KIẾM HÓA ĐƠN                         ║\n", "");
        System.out.printf("%20s║      Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí)       ║\n", "");
        System.out.printf("%20s╚═════════════════════════════════════════════════════════════════╝\n", "");

        System.out.print("Mã hóa đơn: ");
        String maHoaDon = scanner.nextLine();
        if (maHoaDon.isEmpty())
            maHoaDon = null;

        System.out.print("Mã đơn hàng: "); //
        String orId = scanner.nextLine();
        if (orId.isEmpty())
            orId = null;

        System.out.printf("Mã Nhân Viên: ");
        String idNV = scanner.nextLine();
        if (idNV.isEmpty())
            idNV = null;

        System.out.print("Ngày thanh toán chính xác (dd/mm/yyyy): ");
        String orderDate = scanner.nextLine();
        if (orderDate.isEmpty())
            orderDate = null;

        System.out.print("Hoặc từ ngày (dd/mm/yyyy): "); // **Thêm mới**
        String startDate = scanner.nextLine();
        if (startDate.isEmpty())
            startDate = null;

        System.out.print("Hoặc đến ngày (dd/mm/yyyy): "); // **Thêm mới**
        String endDate = scanner.nextLine();
        if (endDate.isEmpty())
            endDate = null;
        
        System.out.print("Tên khách hàng: ");
        String nameCustomer = scanner.nextLine();
        if (nameCustomer.isEmpty())
            nameCustomer = null;

        System.out.print("Mã khách hàng: ");
        String in = scanner.nextLine();
        int idcus;
        if (in.isEmpty())
            idcus = 0;
        else {
            idcus = Integer.parseInt(in);
        }

        System.out.print("Tên sản phẩm: ");
        String nameProduct = scanner.nextLine();
        if (nameProduct.isEmpty())
            nameProduct = null;

        System.out.print("Số lượng sản phẩm: ");
        in = scanner.nextLine();
        int quantityProduct;
        if (in.isEmpty())
            quantityProduct = 0;
        else {
            quantityProduct = Integer.parseInt(in);
        }

        System.out.print("Giá sản phẩm: ");
        int priceProduct;
        in = scanner.nextLine();
        if (in.isEmpty())
            priceProduct = 0;
        else {
            priceProduct = Integer.parseInt(in);
        }

        System.out.print("Thanh toán bằng\n 1.thẻ/chuyển khoản\t2.Tiền mặt\n");
        String pthuc_thanh_toan = scanner.nextLine();
        if (pthuc_thanh_toan.isEmpty())
            pthuc_thanh_toan = null;

        String maSoThe = null;
        double tienKhachChuyen = -1; // Giá trị mặc định không kiểm tra tiền chuyển
        double tienKhachDua = -1; // Giá trị mặc định không kiểm tra tiền đưa

        if ("1".equals(pthuc_thanh_toan)) {
            System.out.print("Nhập mã số thẻ: ");
            maSoThe = scanner.nextLine();
            if (maSoThe.isEmpty()) {
                maSoThe = null; // Nếu không nhập, coi như không cần so sánh mã thẻ
            }

            // Cho phép nhập tiền khách chuyển (hoặc không nhập)
            System.out.print("Nhập số tiền khách chuyển: ");
            String tienChuyenInput = scanner.nextLine();
            if (!tienChuyenInput.isEmpty()) {
                tienKhachChuyen = Double.parseDouble(tienChuyenInput);
            }

        } else if ("2".equals(pthuc_thanh_toan)) {
            // Cho phép nhập tiền khách đưa (hoặc không nhập)
            System.out.print("Nhập số tiền khách đưa: ");
            String tienDuaInput = scanner.nextLine();
            if (!tienDuaInput.isEmpty()) {
                tienKhachDua = Double.parseDouble(tienDuaInput);
            }
        }

        Receipt[] filteredInvoices = new Receipt[receipts.length];
        int count = 0;

        for (Receipt rc : receipts) {
            if (isInvoiceMatch(rc, maHoaDon, orderDate, nameCustomer, nameProduct, quantityProduct, priceProduct,
                    tienKhachDua, orId, idcus, pthuc_thanh_toan, maSoThe, tienKhachChuyen,startDate, endDate, idNV)) {
                filteredInvoices[count++] = rc;
            }
        }

        Receipt[] result = Arrays.copyOf(filteredInvoices, count);

        if (result.length == 0) {
            System.out.println("Không tìm thấy hóa đơn nào khớp với tiêu chí.");
        } else {
            System.out.println("\nDanh sách hóa đơn:\n");
            for (Receipt rc : result) {
                rc.inHoaDon(staffID);
            }
        }
    }

    private static boolean isInvoiceMatch(Receipt receipt, String maHoaDon, String orderDate,
            String nameCustomer, String nameProduct, int quantityProduct, int priceProduct, double tienKhachDua,
            String orId, int idcus, String pthuc_thanh_toan, String masothe, double tienKhachChuyen, String startDate, String endDate, String idNV) {
        if (maHoaDon != null && !receipt.maHoaDon.equals(maHoaDon))
            return false;
        if (orderDate != null && !receipt.giaodich.donhang.getOrderDate().equals(orderDate))
            return false;
        if (nameCustomer != null && !receipt.giaodich.donhang.customer.getName().equalsIgnoreCase(nameCustomer))
            return false;
        if (orId != null && !receipt.giaodich.donhang.getOrderId().equalsIgnoreCase(orId))
            return false;
        if (idcus != 0 && receipt.giaodich.donhang.customer.getCustomerID() != idcus)
            return false;
        if (idNV != null && !receipt.NV.getStaffID().equalsIgnoreCase(idNV))
            return false;

        if (pthuc_thanh_toan != null) {
            if (pthuc_thanh_toan.equals("1")) { // Thẻ/Chuyển khoản
                if (!(receipt.giaodich.phuongThucThanhToan instanceof CardPayment)) {
                    return false; // Nếu phương thức thanh toán không phải là thẻ/chuyển khoản
                }
                if (masothe != null
                        && !((CardPayment) receipt.giaodich.phuongThucThanhToan).getSoThe().equals(masothe)) {
                    return false; // So sánh mã thẻ
                }
                // Kiểm tra tiền khách chuyển (nếu có)
                if (tienKhachChuyen != -1 && receipt.giaodich.phuongThucThanhToan.getSoTien() != tienKhachChuyen) {
                    return false;
                }
            } else if (pthuc_thanh_toan.equals("2")) { // Tiền mặt
                if (!(receipt.giaodich.phuongThucThanhToan instanceof CashPayment)) {
                    return false; // Nếu phương thức thanh toán không phải là tiền mặt
                }
                // Kiểm tra tiền khách đưa (nếu có)
                if (tienKhachDua != -1 && receipt.giaodich.phuongThucThanhToan.getSoTien() != tienKhachDua) {
                    return false;
                }
            } else {
                return false; // Nếu lựa chọn phương thức thanh toán không hợp lệ
            }
        }
        if (startDate != null || endDate != null) { // **Thêm mới**
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date receiptDate = sdf.parse(receipt.giaodich.donhang.getOrderDate());
        
                if (startDate != null) {
                    Date start = sdf.parse(startDate);
                    if (receiptDate.before(start)) {
                        return false;
                    }
                }
        
                if (endDate != null) {
                    Date end = sdf.parse(endDate);
                    if (receiptDate.after(end)) {
                        return false;
                    }
                }
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày tháng: " + e.getMessage());
                return false;
            }
        }
        
        // Kiểm tra sản phẩm
        boolean productMatch = false;
        for (Product pr : receipt.giaodich.donhang.product) {
            if (nameProduct != null && !pr.getName().equalsIgnoreCase(nameProduct))
                continue;
            if (quantityProduct != 0 && pr.getQuantity() != quantityProduct)
                continue;
            if (priceProduct != 0 && pr.getPrice() != priceProduct)
                continue;

            productMatch = true;
            break;
        }

        if (!productMatch)
            return false;

        return true;
    }

    @Override
    public Receipt[] readFromFile(String filename) { // file hoadon.txt có định dạng là
                                                     // mã hóa đơn;mã đơn hàng;phương thức thanh toán(card\cash);số
                                                     // tiền;số thẻ (hoặc để trống nếu là cash)
        Receipt[] receipts;
        int n = 0, i = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String Line;
            while ((Line = br.readLine()) != null) {
                n++;
            }
            br.close();
            Line = Line + "line"; // Để cho nó không hiện broblem isn't used nữa
        } catch (IOException e) {
            e.printStackTrace();
        }
        receipts = new Receipt[n];
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String Line;
            while ((Line = br.readLine()) != null) {
                receipts[i] = new Receipt();
                String[] parts = Line.split(";");
                receipts[i].maHoaDon = parts[0];
                receipts[i].NV=new Manager(Manager.getManagerbyID(parts[1]).getStaffID(),Manager.getManagerbyID(parts[1]).getName(),Manager.getManagerbyID(parts[1]).getRole(), Manager.getManagerbyID(parts[1]).getSalary(), Manager.getManagerbyID(parts[1]).getContactNum());
                receipts[i].giaodich.donhang = new Order(parts[2],
                        receipts[i].giaodich.donhang.getOrderbyID(parts[2]).getOrderDate(),
                        receipts[i].giaodich.donhang.getOrderbyID(parts[2]).getCustomer(),
                        receipts[i].giaodich.donhang.getOrderbyID(parts[2]).getProductList());

                if (parts[3].equals("Card")) {
                    receipts[i].giaodich.phuongThucThanhToan = new CardPayment(Double.parseDouble(parts[4]), parts[5]);
                } else if (parts[3].equals("Cash")) {
                    receipts[i].giaodich.phuongThucThanhToan = new CashPayment(Double.parseDouble(parts[4]));
                }

                String daytmp = receipts[i].giaodich.donhang.getOrderDate();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Định dạng ngày
                Date date;
                try {
                    date = sdf.parse(daytmp); // Chuyển đổi từ String thành Date
                } catch (ParseException e) {
                    date = null;
                    System.out.println("Lỗi không định dạng được ngày: " + e.getMessage());
                }

                if (receipts[i].discount.getDiscountByDay(date) == null) { // kiểm tra xem ngày có nằm trong thời gian
                                                                           // discount ko
                    System.out.println("Khong duoc giam gia");
                    receipts[i].discount.setDiscountPercentage(0);
                } else {
                    receipts[i].discount = receipts[i].discount.getDiscountByDay(date);
                }

                i++;

            }
            br.close();
            Line = Line + "line"; // Để cho nó không hiện broblem isn't used nữa
        } catch (IOException e) {
            e.printStackTrace();
        }
        return receipts;
    }

    public static void xoaNoiDungFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Mở file ở chế độ ghi đè nhưng không ghi gì cả
            writer.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
    }

    public static void xoaNoiDungFilelichsugiaodich(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Mở file ở chế độ ghi đè nhưng không ghi gì cả
            writer.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
    }

    @Override
    public void writeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(getMaHoaDon()+";"+NV.getStaffID()
                    + ";" + giaodich.donhang.getOrderId()
                    + ";");
            if (giaodich.getPhuongThucThanhToan() instanceof CardPayment) {
                writer.write("Card" + ";" + giaodich.getPhuongThucThanhToan().getSoTien() + ";"
                        + ((CardPayment) giaodich.getPhuongThucThanhToan()).getSoThe());
            } else {
                writer.write("Cash" + ";" + giaodich.getPhuongThucThanhToan().getSoTien());
            }
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }

        // ghi vào file lichsugiaodich.txt để lưu lại lịch sử giao dịch
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lichsugiaodich.txt", true))) {

            writer.write(
                    "Mã Hóa Đơn:\t" + getMaHoaDon() + "\nTên Nhân Viên: " +NV.getName()
                            + "\nNgày Giao Dịch: \t" + giaodich.donhang.getOrderDate()
                            + "\nPhuong Thuc Thanh Toan: \n" + giaodich.phuongThucThanhToan.xuLyThanhToan());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public static void xemlichsugiaodich() {
        int kichthuoc = 0;
        try (BufferedReader br = new BufferedReader(new FileReader("lichsugiaodich.txt"))) {
            String Line;
            while ((Line = br.readLine()) != null) {
                kichthuoc++;
            }
            br.close();
            Line = Line + "line"; // Để cho nó không hiện broblem isn't used nữa
        } catch (IOException e) {
            e.printStackTrace();
        }

        String[] ds = new String[kichthuoc];

        try (BufferedReader br = new BufferedReader(new FileReader("lichsugiaodich.txt"))) {
            String Line;
            int i = 0;
            while ((Line = br.readLine()) != null) {
                ds[i] = Line;
                i++;
            }
            br.close();
            Line = Line + "line"; // Để cho nó không hiện broblem isn't used nữa
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.printf("%40s\n\n", "LỊCH SỬ GIAO DỊCH");
        int i = 0;
        for (String ls : ds) {
            i++;
            System.out.println(ls);
            if (i % 5 == 0) {
                System.out.println();
            }
        }
    }
}
