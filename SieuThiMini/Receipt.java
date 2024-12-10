import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.Date;

import java.util.Random;

public class Receipt implements QLFile {

    public Transaction giaodich;
    public Discount discount;
    public String maHoaDon;
    public String MaNV;

    public Receipt() {
        this.giaodich = new Transaction();
        this.discount = new Discount();
    }

    public Receipt(String maHoaDon, Transaction giaodich) {
        this.maHoaDon = maHoaDon;
        this.giaodich = giaodich;
    }

    public String getMaNV() {
        return MaNV;
    }

    public void setMaNV(String maNV) {
        MaNV = maNV;
    }

    public void setMaHoaDon(String maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getMaHoaDon() {
        return maHoaDon;
    }

    public void inHoaDon() {
        int i = 0;
        System.out.printf("╔══════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%28s%-20s%18s║\n", " ", getMaHoaDon(), " ");
        System.out.printf("╠══════════════════════════════════════════════════════════════════╣\n");
        System.out.printf("║  Ngày thanh toán: %45s  ║\n", giaodich.donhang.getOrderDate());
        System.out.printf("║  Th.Ngân: %-55s║\n", Store.getAccountById(MaNV).getName());
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
        double tien_giam_theo_point = giaodich.donhang.customer.isLoyaltyPoints(giaodich.donhang);
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

    public static Receipt[] taogiaodich(Receipt[] receipts, Order or, Scanner scanner, String idNV) {
        receipts = Arrays.copyOf(receipts, receipts.length + 1);
        int i = receipts.length - 1;
        receipts[i] = new Receipt();

        // lấy mã nhân viên hiện tại đang làm hóa đơn
        receipts[i].MaNV = Store.getAccountById(idNV).getAccount();

        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 8;
        receipts[i].setMaHoaDon("HD" + Receipt.generateRandomString(length, charSet));
        receipts[i].giaodich.donhang = new Order(
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getOrderId(),
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getOrderDate(),
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getCustomer(),
                receipts[i].giaodich.donhang.getOrderbyID(or.getOrderId()).getProductList());

        System.out.print("Phương thức thanh toán?\n1.Thẻ/Chuyển Khoản \t 2.Tiền mặt\n→ ");
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
        // Tiền hàng sau khi giảm = (Tổng tiền hàng sau khi giảm theo point) * (1 - Phần
        // trăm giảm) + Tiền VAT
        double tien,
                tien_hang_sau_khi_giam = (receipts[i].giaodich.donhang.customer
                        .isLoyaltyPoints(receipts[i].giaodich.donhang))
                        * (1.0 - receipts[i].discount.getDiscountPercentage() / 100.0);
        tien_hang_sau_khi_giam += Order.calculateVAT(tien_hang_sau_khi_giam);

        if (pt == 1) {
            System.out.print("Nhập STK: ");
            String cardId = scanner.nextLine();
            tien = tien_hang_sau_khi_giam;
            receipts[i].giaodich.phuongThucThanhToan = new CardPayment(tien, cardId); // Đa hình
        } else {
            System.out.printf("Tổng tiền là: %,.2fđ\n", tien_hang_sau_khi_giam);
            System.out.println("Nhập tiền khách đưa: ");
            tien = Double.parseDouble(scanner.nextLine());
            while (tien < tien_hang_sau_khi_giam) {
                System.out.printf("Số tiền khách đưa phải lớn hơn hoặc bằng tổng tiền hàng (%,.2fđ), hãy nhập lại: ",
                        tien_hang_sau_khi_giam);
                tien = Double.parseDouble(scanner.nextLine());
            }
            receipts[i].giaodich.phuongThucThanhToan = new CashPayment(tien); // Đa hình
        }
        receipts[i].giaodich.getPhuongThucThanhToan().setSoTien(tien);
        receipts[i].inHoaDon(); //xuất hóa đơn ra
        receipts[i].inHoaDonToFile(); //ghi thêm hóa đơn vào file receipt để khi xóa sản phẩm thì hóa đơn cũng sẽ không thay đổi
        return receipts;
    }

    public static Receipt[] xoahoadon(Receipt[] receipts, Scanner scanner, Order[] orders) {
        System.out.println("Nhập mã hóa đơn bạn muốn xóa: ");
        boolean flag = false; // Tạo lính canh để kiểm tra nếu sau khi duyệt mà nó còn false thì sẽ cho nhập
                              // lại cho đúng
        byte choice = 0, so_lan_thu = 0;

        String[] mang_hoa_don = docFileTraVeString(receipts.length); //đọc file receipt.txt để tiến hành cập nhật lại mảng

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
                    orders=Arrays.copyOf(orders, orders.length-1); //xóa mảng order
                    mang_hoa_don = Arrays.copyOf(mang_hoa_don, mang_hoa_don.length - 1); //xóa mảng chuỗi hóa đơn
                    receipts = Arrays.copyOf(receipts, receipts.length - 1); //xóa mảng hóa đơn
                    ghiFile(mang_hoa_don); //sau khi xóa mảng chuỗi hóa đơn thì ghi đè vào lại

                    Order tam=new Order();
                    tam.xoaNoiDungFile("donhang.txt");
                    for(int i=0;i<orders.length;i++){ //cập nhật lại mảng donhang
                        orders[i].writeToFile("donhang.txt");
                    }

                    System.out.println("Đã xóa hóa đơn");
                } else {
                    for (int i = pos; i < receipts.length - 1; i++) {
                        orders[i] = orders[i+1];
                        mang_hoa_don[i] = mang_hoa_don[i + 1];
                        receipts[i] = receipts[i + 1];
                    }
                    orders=Arrays.copyOf(orders, orders.length-1);//xóa mảng order
                    mang_hoa_don = Arrays.copyOf(mang_hoa_don, mang_hoa_don.length - 1);//xóa mảng chuỗi hóa đơn
                    receipts = Arrays.copyOf(receipts, receipts.length - 1);//xóa mảng hóa đơn
                    ghiFile(mang_hoa_don);//sau khi xóa mảng chuỗi hóa đơn thì ghi đè vào lại

                    Order tam=new Order();
                    tam.xoaNoiDungFile("donhang.txt");
                    for(int i=0;i<orders.length;i++){ //cập nhật lại mảng donhang
                        orders[i].writeToFile("donhang.txt");
                    }
                    System.out.println("Đã xóa hóa đơn");
                }
                break;
            }
            if (so_lan_thu > 2) {
                System.out.println("Bạn đã nhập sai quá nhiều lần. Đang thoát...");
                break;
            }
            System.out.println(
                    "╔══════════════════════════════════════════════════════════════════════════════════════════╗");
            System.out.println(
                    "║ Mã Hóa Đơn Mà Bạn Nhập Không Có Trong Danh Sách, Bạn Có Muốn Tiếp Tục Xóa Hóa Đơn Không? ║");
            System.out.println(
                    "║                                                                                          ║");
            System.out.println(
                    "║                          1. CÓ                        0. KHÔNG                           ║");
            System.out.println(
                    "╚══════════════════════════════════════════════════════════════════════════════════════════╝");

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

    public static void locHoaDon(Scanner scanner, Receipt[] receipts) {
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

        System.out.print("Số lượng mua: ");
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

        System.out.print("Thanh toán bằng: \t1.Thẻ/Chuyển khoản\t2.Tiền mặt\n");
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
                    tienKhachDua, orId, idcus, pthuc_thanh_toan, maSoThe, tienKhachChuyen, startDate, endDate, idNV)) {
                filteredInvoices[count++] = rc;
            }
        }

        Receipt[] result = Arrays.copyOf(filteredInvoices, count);

        if (result.length == 0) {
            System.out.println("Không Tìm Thấy Hóa Đơn Nào Khớp Với Tiêu Chí.");
        } else {
            System.out.println("\nDanh Sách Hóa Đơn Khớp Với Tiêu Chí:\n");
            for (Receipt rc : result) {
                rc.inHoaDon();
            }
        }
    }

    private static boolean isInvoiceMatch(Receipt receipt, String maHoaDon, String orderDate,
            String nameCustomer, String nameProduct, int quantityProduct, int priceProduct, double tienKhachDua,
            String orId, int idcus, String pthuc_thanh_toan, String masothe, double tienKhachChuyen, String startDate,
            String endDate, String idNV) {
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
        if (idNV != null && !Store.getAccountById(receipt.getMaNV()).getAccount().equalsIgnoreCase(idNV))
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
                receipts[i].MaNV = parts[1];
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
            writer.write(getMaHoaDon() + ";" + MaNV
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
                    "Mã Hóa Đơn:\t" + getMaHoaDon() + "\nTên Nhân Viên:\t" + Store.getAccountById(MaNV).getName()
                            + "\nNgày Giao Dịch:\t" + giaodich.donhang.getOrderDate()
                            + "\nPhuong Thuc Thanh Toan: \n" + giaodich.phuongThucThanhToan.xuLyThanhToan());
            writer.newLine();

        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    public void inHoaDonToFile() { //ghi hóa đơn vào file chuỗi
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt", true))) {
            int i = 0;
            writer.write("╔══════════════════════════════════════════════════════════════════╗\n");
            writer.write(String.format("║%28s%-20s%18s║\n", " ", getMaHoaDon(), " "));
            writer.write("╠══════════════════════════════════════════════════════════════════╣\n");
            writer.write(String.format("║  Ngày thanh toán: %45s  ║\n", giaodich.donhang.getOrderDate()));
            writer.write(String.format("║  Th.Ngân: %-55s║\n", Store.getAccountById(MaNV).getName()));
            writer.write(String.format("║  Tên KhH: %-15s%38s  ║\n",
                    giaodich.donhang.customer.getName(),
                    "Mã KhH: " + giaodich.donhang.customer.getCustomerID()));
            writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");
            writer.write("║  Tên Sản Phẩm           SL        Giá          Tổng Tiền         ║\n");

            for (Product pr : giaodich.donhang.product) {
                writer.write(String.format("║  %-20s   %-5d    %,-10d   %,-19.2f║\n",
                        pr.getName(), pr.getQuantity(), pr.getPrice(),
                        (double) pr.getPrice() * pr.getQuantity()));
                if (i < giaodich.donhang.product.length - 1) {
                    writer.write("║  ..............................................................  ║\n");
                }
                i++;
            }

            writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");
            writer.write(String.format("║  Thành Tiền                                   %,-19.2f║\n",
                    giaodich.donhang.calculateTotalAmount()));
            writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");

            double tien_giam_theo_point = giaodich.donhang.customer.isLoyaltyPoints(giaodich.donhang);
            writer.write(String.format("║  Sau Khi Giảm Theo Point                      %,-19.2f║\n",
                    tien_giam_theo_point));
            writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");

            if (discount.getDiscountPercentage() != 0) { // Nếu có chương trình giảm giá
                double tien_sau_khi_giam_gia = tien_giam_theo_point * (1.0 - discount.getDiscountPercentage() / 100);
                writer.write(String.format("║  %-23s                      %,-19.2f║\n",
                        discount.getName(), tien_sau_khi_giam_gia));
                writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");
                writer.write(String.format("║  Tổng Thanh Toán (Có VAT)                     %,-19.2f║\n",
                        tien_sau_khi_giam_gia + Order.calculateVAT(tien_sau_khi_giam_gia)));
                writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");
                writer.write(String.format("║  %-64s║\n", giaodich.phuongThucThanhToan.xuLyThanhToan()));
                writer.write(String.format("║  Tiền Thối Lại                                %,-19.2f║\n",
                        giaodich.phuongThucThanhToan.getSoTien()
                                - (tien_sau_khi_giam_gia + Order.calculateVAT(tien_sau_khi_giam_gia))));
            } else {
                double so_tien_sau_VAT = tien_giam_theo_point + Order.calculateVAT(tien_giam_theo_point);
                writer.write(
                        String.format("║  Tổng Thanh Toán (Có VAT)                     %,-19.2f║\n", so_tien_sau_VAT));
                writer.write("║  ══════════════════════════════════════════════════════════════  ║\n");
                writer.write(String.format("║  %-64s║\n", giaodich.phuongThucThanhToan.xuLyThanhToan()));
                writer.write(String.format("║  Tiền Thối Lại                                %,-19.2f║\n",
                        giaodich.phuongThucThanhToan.getSoTien() - so_tien_sau_VAT));
            }

            writer.write("╚══════════════════════════════════════════════════════════════════╝\n" +
                    ">------------------------------------------------------------------<\n");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào file: " + e.getMessage());
        }
    }

    public static void docVaInFile() { //đọc từng chuỗi và in hóa đơn ra 
        try (BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }
    }

    public static String[] docFileTraVeString(int n) { // đọc lại file và tách nó và trả về mảng chuỗi các hóa đơn
        try (BufferedReader reader = new BufferedReader(new FileReader("receipt.txt"))) {
            StringBuilder temp = new StringBuilder();
            String line;

            // Đọc toàn bộ nội dung file
            while ((line = reader.readLine()) != null) {
                temp.append(line).append("\n");
            }
            String chuoi = temp.toString();

            String ngan_cach = ">------------------------------------------------------------------<";

            String[] mang_hoa_don = chuoi.split(ngan_cach);
            mang_hoa_don=Arrays.copyOf(mang_hoa_don, n);
            return mang_hoa_don; // Trả về mảng String[]

        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
            return new String[0];
        }
    }

    public static void ghiFile(String[] mangHoaDon) { // ghi đè lên file chuỗi hóa đơn để cập nhật lại mỗi khi xóa 
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("receipt.txt"))) {
            // Lặp qua từng phần tử trong mảng và ghi vào file
            for (int i = 0; i < mangHoaDon.length; i++) {
                writer.write(mangHoaDon[i]);
                // Nếu không phải phần tử cuối, thêm dấu phân cách vào sau
                writer.write(">------------------------------------------------------------------<");
            }
            writer.write("\n");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi file: " + e.getMessage());
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
