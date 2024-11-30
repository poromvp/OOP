import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.Date;

public class InvoiceManager {
    private Receipt[] receipts;
    private int count;
    private Discount[] discounts;
    private Cashier cashier;

    public InvoiceManager(int size) {
        receipts = new Receipt[size];
        count = 0;
        discounts = new Discount[10];
    }

    public InvoiceManager() {

    }

    // Thêm hóa đơn mới
    public void addReceipt(Receipt receipt) {
        if (count < receipts.length) {
            receipts[count++] = receipt;
        } else {
            System.out.println("Không thể thêm hóa đơn: Đã đạt giới hạn.");
        }
    }

    // Xuất hóa đơn

    public void exportInvoice() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xuất: ");
        int receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Receipt receipt = searchReceiptById(receiptId);
        if (receipt != null) {
            receipt.print();
        } else {
            System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
        }

    }

    // Sửa hóa đơn
    public void editReceiptById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần sửa: ");
        int receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                Receipt receipt = receipts[i];
                Transaction transaction = receipt.getTransaction();

                System.out.print("Nhập mã giao dịch mới (hiện tại: " + transaction.getId() + "): ");
                int newTransactionId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                transaction.setId(newTransactionId);
                System.out.print("Nhập ngày giao dịch mới (dd/MM/yyyy) (hiện tại: "
                        + new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate()) + "): ");
                String newDateString = scanner.nextLine();

                Date newDate = null;
                try {
                    newDate = new SimpleDateFormat("dd/MM/yyyy").parse(newDateString);
                    System.out.println("Ngày đã parse: " + newDate);
                } catch (ParseException e) {
                    System.out.println("Lỗi định dạng ngày: " + e.getMessage());
                    e.printStackTrace();
                }

                if (newDate != null) {
                    transaction.setDate(newDate);
                    System.out.println("Ngày giao dịch mới đã được cập nhật!");
                } else {
                    System.out.println("Ngày giao dịch không được cập nhật do lỗi định dạng.");
                }

                System.out.print("Nhập số tiền khách đưa mới (hiện tại: " + transaction.getCustomerPaid() + "): ");
                double newCustomerPaid = scanner.nextDouble();
                transaction.setCustomerPaid(newCustomerPaid);

                System.out.println("Sửa thông tin sản phẩm:");
                Item[] items = transaction.getItems();
                for (int j = 0; j < items.length; j++) {
                    System.out.print("Tên sản phẩm (hiện tại: " + items[j].getName() + "): ");
                    String newName = scanner.nextLine();
                    items[j].setName(newName);

                    System.out.print("Giá sản phẩm (hiện tại: " + items[j].getPrice() + "): ");
                    double newPrice = scanner.nextDouble();
                    items[j].setPrice(newPrice);

                    System.out.print("Số lượng sản phẩm (hiện tại: " + items[j].getQuantity() + "): ");
                    int newQuantity = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    items[j].setQuantity(newQuantity);
                }

                System.out.println("Hóa đơn đã được sửa thành công.");

                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
        scanner.close();
    }

    public void deleteReceiptById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        int receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        scanner.close();

        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                for (int j = i; j < count - 1; j++) {
                    receipts[j] = receipts[j + 1];
                }
                receipts[--count] = null;
                System.out.println("Hóa đơn đã được xóa thành công.");
                return;
            }
        }
        System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);

    }

    // tìm kiếm hóa đơn
    public Receipt searchReceiptById(int receiptId) {
        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                return receipts[i];
            }
        }
        return null;
    }

    public void searchAndPrintReceipt() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        int receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Receipt receipt = searchReceiptById(receiptId);
        if (receipt != null) {
            receipt.print();
        } else {
            System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
        }
        scanner.close();
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
                Receipt receipt = new Receipt(id, transaction, cashier);
                addReceipt(receipt);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }
}