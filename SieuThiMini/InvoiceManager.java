import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Date;

public class InvoiceManager {
    private Receipt[] receipts;
    private int count;

    public InvoiceManager(int size) {
        receipts = new Receipt[size];
        count = 0;
    }

    // Thêm hóa đơn mới
    public void addReceipt(Receipt receipt) {
        if (count < receipts.length) {
            receipts[count++] = receipt;
        } else {
            System.out.println("Không thể thêm hóa đơn: Đã đạt giới hạn.");
        }
    }

    // Xuất danh sách hóa đơn
    public void printReceipts() {
        for (int i = 0; i < count; i++) {
            receipts[i].print();
        }
    }

    // Sửa hóa đơn
    public void updateReceipt(int receiptId, Receipt newReceipt) {
        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                receipts[i] = newReceipt;
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
    }

    // Xóa hóa đơn
    public void deleteReceipt(int receiptId) {
        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                for (int j = i; j < count - 1; j++) {
                    receipts[j] = receipts[j + 1];
                }
                receipts[--count] = null;
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
    }

    // Thống kê hóa đơn
    public void printStatistics() {
        double totalAmount = 0;
        for (int i = 0; i < count; i++) {
            totalAmount += receipts[i].getTransaction().getTotal();
        }
        System.out.println("Tổng số hóa đơn: " + count);
        System.out.println("Tổng số tiền: " + totalAmount);
    }

    // Thống kê đơn hàng theo thời gian
    public void sortByDate(boolean ascending) {
        Arrays.sort(receipts, 0, count, Comparator.comparing(r -> r.getTransaction().getDate()));
        if (!ascending) {
            reverseArray(receipts, count);
        }
    }

    // Thống kê đơn hàng theo tổng số tiền
    public void sortByTotalAmount(boolean ascending) {
        Arrays.sort(receipts, 0, count, Comparator.comparing(r -> r.getTransaction().getTotal()));
        if (!ascending) {
            reverseArray(receipts, count);
        }
    }

    // Thống kê đơn hàng theo số lượng
    public void sortByQuantity(boolean ascending) {
        Arrays.sort(receipts, 0, count, Comparator.comparing(r -> Arrays.stream(r.getTransaction().getItems()).mapToInt(Item::getQuantity).sum()));
        if (!ascending) {
            reverseArray(receipts, count);
        }
    }

    // Thống kê đơn hàng theo mã đơn hàng
    public void sortByReceiptId(boolean ascending) {
        Arrays.sort(receipts, 0, count, Comparator.comparing(Receipt::getReceiptId));
        if (!ascending) {
            reverseArray(receipts, count);
        }
    }

    private void reverseArray(Receipt[] array, int length) {
        for (int i = 0; i < length / 2; i++) {
            Receipt temp = array[i];
            array[i] = array[length - 1 - i];
            array[length - 1 - i] = temp;
        }
    }
    // Phương thức menu
    //        Khởi tạo trong chạy chương trình InvoiceManager manager = new InvoiceManager(100);
    //        Thêm hóa đơn
    //        manager.addReceipt(new Receipt(...));
    //        Gọi phương thức showMenu từ InvoiceManager
    //        manager.showMenu();
    public void showMenu() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n--- MENU THỐNG KÊ ĐƠN HÀNG ---");
            System.out.println("1. Thống kê đơn hàng theo thời gian (ngày/tháng/năm) mới, cũ");
            System.out.println("2. Thống kê đơn hàng theo tổng số tiền giảm dần, tăng dần");
            System.out.println("3. Thống kê đơn hàng theo quantity giảm dần, tăng dần");
            System.out.println("4. Thống kê đơn hàng theo mã đơn hàng tăng dần, giảm dần");
            System.out.print("Chọn lựa chọn (1-4): ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Chọn thứ tự (1: mới -> cũ, 2: cũ -> mới): ");
                    int order1 = scanner.nextInt();
                    sortByDate(order1 == 2);
                    printReceipts();
                    break;
                case 2:
                    System.out.print("Chọn thứ tự (1: giảm dần, 2: tăng dần): ");
                    int order2 = scanner.nextInt();
                    sortByTotalAmount(order2 == 2);
                    printReceipts();
                    break;
                case 3:
                    System.out.print("Chọn thứ tự (1: giảm dần, 2: tăng dần): ");
                    int order3 = scanner.nextInt();
                    sortByQuantity(order3 == 2);
                    printReceipts();
                    break;
                case 4:
                    System.out.print("Chọn thứ tự (1: tăng dần, 2: giảm dần): ");
                    int order4 = scanner.nextInt();
                    sortByReceiptId(order4 == 1);
                    printReceipts();
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        }
    }

    // Đọc hóa đơn từ file
    public void loadReceiptsFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            while ((line = br.readLine()) != null) {
                if (line.equals("---")) {
                    continue;
                }
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                Date date = sdf.parse(parts[1]);
                double totalAmount = Double.parseDouble(parts[2]);
                double customerPaid = Double.parseDouble(parts[3]);

                Transaction transaction = new Transaction(id, date);
                transaction.setCustomerPaid(customerPaid);
                while ((line = br.readLine()) != null && !line.equals("---")) {
                    parts = line.split(",");
                    String itemName = parts[0];
                    double itemPrice = Double.parseDouble(parts[1]);
                    int itemQuantity = Integer.parseInt(parts[2]);
                    Item item = new Item(itemName, itemPrice, itemQuantity);
                    transaction.addItem(item);
                }
                Receipt receipt = new Receipt(id, transaction);
                addReceipt(receipt);
            }
        } catch (IOException | ParseException e) {
                e.printStackTrace();
        }
    }
}