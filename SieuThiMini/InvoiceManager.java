import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Date;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class InvoiceManager {
    private Receipt[] receipts;
    private int count;

    public InvoiceManager(int size) {
        receipts = new Receipt[size];
        count = 0;
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

    // Xuất danh sách hóa đơn
    public void exportinvoice(Scanner scanner) {
        Discount discount = new Discount();
        System.out.print("Nhập mã hóa đơn: ");
        int receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Receipt receipt = null;
        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                receipt = receipts[i];
                break;
            }
        }

        if (receipt == null) {
            System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
            return;
        }

        System.out.print("Nhập mã chương trình giảm giá: ");
        int discountId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Discount discount = null;
        for (Discount d : discounts) {
            if (d != null && d.getDiscountID() == discountId) {
                discount = d;
                break;
            }
        }

        if (discount != null) {
            double discountAmount = discount.applyDiscount(receipt.getTransaction().getTotal());
            receipt.getTransaction().setTotal(receipt.getTransaction().getTotal() - discountAmount);
            System.out.println("Đã áp dụng giảm giá: " + discountAmount);
        } else {
            System.out.println("Không tìm thấy chương trình giảm giá với mã: " + discountId);
        }

    // Sửa hóa đơn
    public void editReceiptById() {
            scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần sửa: ");
            receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        for (int i = 0; i < count; i++) {
            if (receipts[i].getReceiptId() == receiptId) {
                receipt = receipts[i];
                Transaction transaction = receipt.getTransaction();

                System.out.print("Nhập mã giao dịch mới (hiện tại: " + transaction.getId() + "): ");
                int newTransactionId = scanner.nextInt();
                scanner.nextLine(); // Consume newline
                transaction.setId(newTransactionId);

                System.out.print("Nhập ngày giao dịch mới (dd/MM/yyyy) (hiện tại: " + new SimpleDateFormat("dd/MM/yyyy").format(transaction.getDate()) + "): ");
                String newDateString = scanner.nextLine();
                Date newDate = new SimpleDateFormat("dd/MM/yyyy").parse(newDateString);
                transaction.setDate(newDate);

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
    }

    // Xóa hóa đơn
   

    public void deleteReceipt( int receiptId) {
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
}
    public void deleteReceiptById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần xóa: ");
        int receiptId = scanner.nextInt();
        deleteReceipt(receiptId);
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
        Scanner scanner= new Scanner(System.in);
        System.out.print("Nhập mã hóa đơn cần tìm: ");
        int receiptId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Receipt receipt = searchReceiptById(receiptId);
        if (receipt != null) {
            receipt.print();
        } else {
            System.out.println("Không tìm thấy hóa đơn với mã: " + receiptId);
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
public void main() {
}