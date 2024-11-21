import java.text.SimpleDateFormat;
import java.text.DecimalFormat;

public class Receipt {
    private int receiptId;
    private Transaction transaction;

    public Receipt(int receiptId, Transaction transaction) {
        this.receiptId = receiptId;
        this.transaction = transaction;
    }

    public void print() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        DecimalFormat df = new DecimalFormat("#,###");

        System.out.println("========================================");
        System.out.println("             SIÊU THỊ MINI              ");
        System.out.println("            HÓA ĐƠN BÁN HÀNG            ");
        System.out.println("========================================");
        System.out.println(String.format("Mã Hóa Đơn: %d", receiptId));
        System.out.println(String.format("Mã Giao Dịch: %d", transaction.getId()));
        System.out.println(String.format("Thời Gian: %s", sdf.format(transaction.getDate())));
        System.out.println("----------------------------------------");
        System.out.println(String.format("%-20s %5s %10s %10s", "Tên Sản Phẩm", "SL", "Đơn Giá", "Thành Tiền"));
        System.out.println("----------------------------------------");

        Item[] items = transaction.getItems();
        for (Item item : items) {
            System.out.println(String.format("%-20s %5d %10s %10s",
                    item.getName(),
                    item.getQuantity(),
                    df.format(item.getPrice()),
                    df.format(item.getTotalPrice())));
        }

        System.out.println("----------------------------------------");
        System.out.println(String.format("%-30s %10s", "TỔNG CỘNG:", df.format(transaction.getTotal())));
        System.out.println(String.format("%-30s %10s", "SỐ TIỀN KHÁCH ĐƯA:", df.format(transaction.getCustomerPaid())));
        System.out.println(String.format("%-30s %10s", "TIỀN TRẢ LẠI:", df.format(transaction.getChange())));
        System.out.println("========================================");
        System.out.println("    CẢM ƠN QUÝ KHÁCH! HẸN GẶP LẠI!     ");
        System.out.println("========================================");
    }
    public int getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
