import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Random;

public class Receipt implements QLFile {
    private Transaction giaodich;
    private String maHoaDon;

    public Receipt() {
        giaodich=new Transaction();
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
    public void inHoaDon() {
        int i = 0;
        // Tăng độ rộng các cột cho vừa với dữ liệu00        2
        System.out.printf("╔══════════════════════════════════════════════════════════════════╗\n");
        System.out.printf("║%28s%-20s%18s║\n", " ", getMaHoaDon(), " ");
        System.out.printf("╠══════════════════════════════════════════════════════════════════╣\n");
        System.out.printf("║  Ngày thanh toán: %45s  ║\n", giaodich.donhang.getOrderDate());
        System.out.printf("║  Th.Ngân: %-55s║\n", giaodich.getTenNhanVien());
        System.out.printf("║  Tên KhH: %-55s║\n", giaodich.donhang.customer.getName());
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        System.out.printf("║  Tên Sản Phẩm           SL        Giá          Tổng Tiền         ║\n");
        
        for (Product pr : giaodich.donhang.product) {
            System.out.printf("║  %-20s   %-5d    %-10d   %-19.2f║\n", pr.getName(), pr.getQuantity(), pr.getPrice(), (double) pr.getPrice() * pr.getQuantity());
            if (i < giaodich.donhang.product.length - 1) {
                System.out.printf("║  ..............................................................  ║\n");
            }
            i++;
        }
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        
        // Cập nhật lại phần tính toán tổng tiền
        System.out.printf("║  Thành Tiền                                   %-19.2f║\n", giaodich.donhang.calculateTotalAmount());
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        System.out.printf("║  Tổng Thanh Toán                              %-19.2f║\n", Order.calculateVAT(giaodich.donhang.calculateTotalAmount()));
        System.out.printf("║  ══════════════════════════════════════════════════════════════  ║\n");
        System.out.printf("║  %-64s║\n", giaodich.phuongThucThanhToan.xuLyThanhToan());
        //System.out.printf("║  Tiền Khách Đưa                               %-19.2f║\n", getTienKhachDua());
        System.out.printf("║  Tiền Thối Lại                                %-19.2f║\n", giaodich.phuongThucThanhToan.getSoTien() - Order.calculateVAT(giaodich.donhang.calculateTotalAmount()));
        System.out.printf("╚══════════════════════════════════════════════════════════════════╝\n");
    }
    
    

    public void luuHoaDon() {
        try (FileWriter writer = new FileWriter("hoadonn.txt", true)) {
            writer.write(this.toString() + "\n");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi hóa đơn: " + e.getMessage());
        }
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

    public static Receipt[] themhoadon(Receipt[] receipts, Scanner scanner, Order[] orderList){
        String charSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        int length = 10;
        System.out.print("Bạn muốn thêm bao nhiêu hóa đơn?: ");
        int n = Integer.parseInt(scanner.nextLine());
        receipts = Arrays.copyOf(receipts, receipts.length + n);
        for(int j=receipts.length-n;j<receipts.length;j++){
            receipts[j]=new Receipt();
            System.out.print("Nhập ngày của đơn hàng để lấy dữ liệu tính toán: ");
            String date=scanner.nextLine();
            for(int i=0;i<orderList.length;i++){
                if(date.equals(orderList[i].getOrderDate())){
                    int temp=0;
                    receipts[j].setMaHoaDon(Receipt.generateRandomString(length, charSet));
                    receipts[j].giaodich.donhang.setOrderDate(date);
                    System.out.print("Nhập tên nhân viên: ");
                    receipts[j].giaodich.setTenNhanVien(scanner.nextLine());
                    receipts[j].giaodich.donhang.customer.setName(orderList[i].customer.getName());
                    receipts[j].giaodich.donhang.product = new Product[orderList[i].product.length];
                    for(Product pr : orderList[i].product){
                        receipts[j].giaodich.donhang.product[temp]=new Product();
                        receipts[j].giaodich.donhang.product[temp].setName(orderList[i].product[temp].getName());
                        receipts[j].giaodich.donhang.product[temp].setQuantity(orderList[i].product[temp].getQuantity());
                        receipts[j].giaodich.donhang.product[temp].setPrice(orderList[i].product[temp].getPrice());
                        temp++;
                    }
                    System.out.print("Phuong thuc thanh toan?\n 1.Thẻ/Chuyển Khoản \t 2.Tiền mặt\n→");
                    int pt=Integer.parseInt(scanner.nextLine());
                    while(pt!=1 && pt!=2){
                        System.out.print("Không hợp lệ, hãy nhập lại: ");
                        pt=Integer.parseInt(scanner.nextLine());
                    }
                    Double tien;
                    if(pt==1){
                        System.out.print("Nhập STK: ");
                        String cardId=scanner.nextLine();
                        System.out.println("Nhập số tiền khách chuyển: ");
                        tien=Double.parseDouble(scanner.nextLine());
                        while(tien<Order.calculateVAT(receipts[j].giaodich.donhang.calculateTotalAmount())){
                            System.out.print("Số tiền khách đưa phải lớn hơn tổng tiền hàng, hãy nhập lại: ");
                            tien=Double.parseDouble(scanner.nextLine());
                        }
                        
                        receipts[j].giaodich.phuongThucThanhToan=new CardPayment(tien, cardId); //Đa hình 
                    }
                    else{
                        System.out.println("Nhập tiền khách đưa: ");
                        tien=Double.parseDouble(scanner.nextLine());
                        while(tien<Order.calculateVAT(receipts[j].giaodich.donhang.calculateTotalAmount())){
                            System.out.print("Số tiền khách đưa phải lớn hơn tổng tiền hàng, hãy nhập lại: ");
                            tien=Double.parseDouble(scanner.nextLine());
                        }

                        receipts[j].giaodich.phuongThucThanhToan=new CashPayment(tien); //Đa hình
                    }
                    receipts[j].giaodich.getPhuongThucThanhToan().setSoTien(tien);
                    break;
                }
            }
        }
        System.out.println("Đã thêm hóa đơn thành công");
        return receipts;
    }

    public static Receipt[] xoahoadon(Receipt[] receipts, Scanner scanner){
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

    public static Receipt[] suahoadon(Receipt[] receipts, String editID, Scanner scanner) {
        // Tìm kiếm hóa đơn theo ID
        Receipt hoadon = null;
        for (Receipt rc : receipts) {
            if (rc.getMaHoaDon().equals(editID)) {
                hoadon = rc;
                break;
            }
        }

        if (hoadon != null) {
            hoadon.inHoaDon();
            System.out.println("\nNhấn Enter để giữ nguyên thông tin hiện tại.");

            // Cập nhật ngày thanh toán
            System.out.print("Ngày: ");
            String newday = scanner.nextLine();
            if (!newday.trim().isEmpty()) {
                hoadon.giaodich.donhang.setOrderDate(newday);
            }

            // Cập nhật tên nhân viên
            System.out.print("Tên nhân viên: ");
            String newNV = scanner.nextLine();
            if (!newNV.trim().isEmpty()) {
                hoadon.giaodich.setTenNhanVien(newNV);
            }

            // Cập nhật tên khách hàng
            System.out.print("Tên khách hàng: ");
            String newName = scanner.nextLine();
            if (!newNV.trim().isEmpty()) {
                hoadon.giaodich.donhang.customer.setName(newName);
            }
            int i=0;
            for(Product pr:hoadon.giaodich.donhang.product){
                // Cập nhật tên sản phẩm
                System.out.print("Tên sản phẩm "+(i+1)+": ");
                String newSP = scanner.nextLine();
                if (!newSP.trim().isEmpty()) {
                    pr.setName(newSP);
                }
                
                // Cập nhật số lượng sản phẩm
                System.out.print("Số lượng sản phẩm "+(i+1)+": ");
                String newQ = scanner.nextLine();
                if (!newQ.trim().isEmpty()) {
                    try {
                        int newquantity = Integer.parseInt(newQ);
                        pr.setQuantity(newquantity);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Số lượng bạn nhập không hợp lệ, giữ nguyên");
                    }
                }

                // Cập nhật giá tiền sản phẩm
                System.out.print("Giá sản phẩm "+(i+1)+": ");
                String newPrice = scanner.nextLine();
                if (!newPrice.trim().isEmpty()) {
                    try {
                        int newprice = Integer.parseInt(newPrice);
                        pr.setPrice(newprice);
                    } catch (NumberFormatException e) {
                        System.out.println("Lỗi: Số tiền bạn nhập không hợp lệ, giữ nguyên");
                    }
                }
                i++;
            }

            // Cập nhật tiền khách đưa
            System.out.print("Tiền khách đưa: ");
            String newMoney = scanner.nextLine();
            if (!newMoney.trim().isEmpty()) {
                try {
                    Double newTien = Double.parseDouble(newMoney);
                    hoadon.giaodich.getPhuongThucThanhToan().setSoTien(newTien);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Tiền khách đưa không hợp lệ, giữ nguyên");
                }
            }


            System.out.println("\nHóa đơn đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy hóa đơn với mã: " + editID);
        }

        return receipts; // Trả về danh sách khách hàng sau khi cập nhật
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

        System.out.print("Ngày đặt hàng (dd/mm/yyyy): ");
        String orderDate = scanner.nextLine();
        if (orderDate.isEmpty())
            orderDate = null;

        System.out.print("Tên nhân viên: ");
        String tenNV = scanner.nextLine();
        if (tenNV.isEmpty())
            tenNV = null;

        System.out.print("Tên khách hàng: ");
        String nameCustomer = scanner.nextLine();
        if (nameCustomer.isEmpty())
            nameCustomer = null;

        System.out.print("Tên sản phẩm: ");
        String nameProduct = scanner.nextLine();
        if (nameProduct.isEmpty())
            nameProduct = null;

        System.out.print("Số lượng sản phẩm: ");
        String in = scanner.nextLine();
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

        System.out.print("Số tiền khách đưa: ");
        in = scanner.nextLine();
        double tienKhachDua;
        if (in.isEmpty())
            tienKhachDua = -1; // -1 biểu thị không kiểm tra tiêu chí này
        else {
            tienKhachDua = Double.parseDouble(in);
        }
        
        Receipt[] filteredInvoices = new Receipt[receipts.length];
        int count = 0;

        for (Receipt rc : receipts) {
            if (isInvoiceMatch(rc, maHoaDon, orderDate, tenNV, nameCustomer, nameProduct, quantityProduct, priceProduct, tienKhachDua)) {
                filteredInvoices[count++] = rc;
            }
        }

        Receipt[] result = Arrays.copyOf(filteredInvoices, count);

        if (result.length == 0) {
            System.out.println("Không tìm thấy hóa đơn nào khớp với tiêu chí.");
        } else {
            System.out.println("\nDanh sách hóa đơn:\n");
            for (Receipt rc : result) {
                rc.inHoaDon();
            }
        }
    }

    private static boolean isInvoiceMatch(Receipt receipt, String maHoaDon, String orderDate, String tenNV,
                                          String nameCustomer, String nameProduct, int quantityProduct, int priceProduct, double tienKhachDua) {
        if (maHoaDon != null && !receipt.maHoaDon.equals(maHoaDon))
            return false;
        if (orderDate != null && !receipt.giaodich.donhang.getOrderDate().equals(orderDate))
            return false;
        if (tenNV != null && !receipt.giaodich.getTenNhanVien().equalsIgnoreCase(tenNV))
            return false;
        if (nameCustomer != null && !receipt.giaodich.donhang.customer.getName().equalsIgnoreCase(nameCustomer))
            return false;
        if (tienKhachDua != -1 && receipt.giaodich.phuongThucThanhToan.getSoTien() != tienKhachDua)
            return false;
        
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
    public Receipt[] readFromFile(String filename) {
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
                receipts[i].giaodich.donhang.setOrderDate(parts[1]);
                receipts[i].giaodich.setTenNhanVien(parts[2]);
                receipts[i].giaodich.donhang.customer.setName(parts[3]);
                receipts[i].giaodich.phuongThucThanhToan=new CashPayment(Double.parseDouble(parts[5]));
                String[] partPro = parts[4].split("\\|");
                receipts[i].giaodich.donhang.product=new Product[partPro.length];
                for (int j = 0; j < partPro.length; j++) {
                    receipts[i].giaodich.donhang.product[j]=new Product();
                    String[] chitietSP = partPro[j].split(",");
                    receipts[i].giaodich.donhang.product[j].setName(chitietSP[0]);
                    receipts[i].giaodich.donhang.product[j].setQuantity(Integer.parseInt(chitietSP[1]));
                    receipts[i].giaodich.donhang.product[j].setPrice(Integer.parseInt(chitietSP[2]));
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
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lichsugiaodich.txt", false))) {
            // Mở file ở chế độ ghi đè nhưng không ghi gì cả
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
    }

    @Override
    public void writeToFile(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {

            int i = 0;
            writer.write(getMaHoaDon() + ";"
                    + giaodich.donhang.getOrderDate()
                    + ";" + giaodich.getTenNhanVien()
                    + ";" + giaodich.donhang.customer.getName()
                    + ";");
            for (Product pr : giaodich.donhang.product) {
                writer.write(pr.getName() + "," + pr.getQuantity() + "," + pr.getPrice());
                if (i < giaodich.donhang.product.length - 1) {
                    writer.write("|");
                }
                i++;
            }
            writer.write(";" + giaodich.getPhuongThucThanhToan().getSoTien());
            writer.newLine();
            
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
        
        //ghi vào file lichsugiaodich.txt để lưu lại lịch sử giao dịch
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("lichsugiaodich.txt", true))) {
            
            writer.write(
                    "Mã Hóa Đơn:\t" +getMaHoaDon() 
                    + "\nNgày Giao Dịch: \t" + giaodich.donhang.getOrderDate()
                    + "\nTên Nhân Viên: \t" + giaodich.getTenNhanVien()
                    + "\nPhuong Thuc Thanh Toan: \n"+giaodich.phuongThucThanhToan.xuLyThanhToan());
                    writer.newLine();
                    
                } catch (IOException e) {
                    System.out.println("Lỗi khi ghi file: " + e.getMessage());
                }
    }
}
