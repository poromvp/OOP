
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
    }
