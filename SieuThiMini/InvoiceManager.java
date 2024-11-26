import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

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
    public void updateReceipt(int receiptId, Scanner scanner) {
        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                System.out.print("Nhập ngày mới (yyyy-MM-dd): ");
                String newDateStr = scanner.nextLine();
                System.out.print("Nhập số tiền khách đưa mới: ");
                double newCustomerPaid = Double.parseDouble(scanner.nextLine());

                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date newDate = sdf.parse(newDateStr);
                    Transaction newTransaction = new Transaction(receiptId, newDate);
                    newTransaction.setCustomerPaid(newCustomerPaid);

                    System.out.print("Nhập số lượng sản phẩm mới: ");
                    int newItemCount = Integer.parseInt(scanner.nextLine());
                    for (int j = 0; j < newItemCount; j++) {
                        System.out.print("Nhập tên sản phẩm mới: ");
                        String itemName = scanner.nextLine();
                        System.out.print("Nhập giá sản phẩm mới: ");
                        double itemPrice = Double.parseDouble(scanner.nextLine());
                        System.out.print("Nhập số lượng sản phẩm mới: ");
                        int itemQuantity = Integer.parseInt(scanner.nextLine());
                        Item item = new Item(itemName, itemPrice, itemQuantity);
                        newTransaction.addItem(item);
                    }

                    receipts[i] = new Receipt(receiptId, newTransaction);
                    System.out.println("Đã cập nhật hóa đơn.");
                } catch (ParseException e) {
                    System.out.println("Ngày không hợp lệ.");
                }
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
                System.out.println("Đã xóa hóa đơn với mã: " + receiptId);
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
    //
      // tạo hóa đơn mới
    public void createReceipt(){
        Scanner scanner = new Scanner(System.in);

        System.out.print("Nhập mã hóa đơn: ");
        int id = Integer.parseInt(scanner.nextLine());
        System.out.print("Nhập ngày (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateStr);
            Transaction transaction = new Transaction(id, date);
            double customerPaid = 0;
            transaction.setCustomerPaid(customerPaid);

            System.out.print("Nhập số lượng sản phẩm: ");
            int itemCount = Integer.parseInt(scanner.nextLine());
            for (int i = 0; i < itemCount; i++) {
                System.out.print("Nhập tên sản phẩm: ");
                String itemName = scanner.nextLine();
                System.out.print("Nhập giá sản phẩm: ");
                double itemPrice = Double.parseDouble(scanner.nextLine());
                System.out.print("Nhập số lượng sản phẩm: ");
                int itemQuantity = Integer.parseInt(scanner.nextLine());
                Item item = new Item(itemName, itemPrice, itemQuantity);
                transaction.addItem(item);
                System.out.print("Tổng số tiền: ");
                double totalAmount = transaction.getTotal();
                System.out.print("Nhập số tiền khách đưa: ");
                customerPaid = Double.parseDouble(scanner.nextLine());
            }

            Receipt receipt = new Receipt(id, transaction);
            addReceipt(receipt);
            System.out.println("Đã thêm hóa đơn mới.");
        } catch (ParseException e) {
            System.out.println("Ngày không hợp lệ.");
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