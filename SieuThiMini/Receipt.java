

public class Receipt {
    private int receiptId;
    private Transaction transaction;

    public Receipt(int receiptId, Transaction transaction) {
        this.receiptId = receiptId;
        this.transaction = transaction;
    }

    public void print() {
        System.out.println("Receipt ID: " + receiptId);
        System.out.println("Transaction ID: " + transaction.getId());
        System.out.println("Date: " + transaction.getDate());
        System.out.println("Items:");
        Item[] items = transaction.getItems();
        for (int i = 0; i < items.length; i++) {
            System.out.println(" - " + items[i].getName() + ": đ" + items[i].getPrice());
        }
        System.out.println("Total Amount: đ" + transaction.getTotal());
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
