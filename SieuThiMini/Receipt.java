import java.util.List;


public class Receipt {
    private int receiptId;
    private Transaction transaction;
    private List<Item> items;


    public Receipt(int receiptId, Transaction transaction){
        this.receiptId = receiptId;
        this.transaction = transaction;
        this.items = transaction.getItems();

    }
    public void print() {
        System.out.println("Receipt ID: " + receiptId);
        System.out.println("Transaction ID: " + transaction.getId());
        System.out.println("Date: " + transaction.getDate());
        System.out.println("Items:");
        for (Item item : items) {
            System.out.println(" - " + item.getName() + ": đ" + item.getPrice());
        }
        System.out.println("Total Amount: đ" + transaction.getTotal());
    }


    public int getReceiptId() {
        return this.receiptId;
    }

    public void setReceiptId(int receiptId) {
        this.receiptId = receiptId;
    }

    public Transaction getTransaction() {
        return this.transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        this.items= transaction.getItems();
    }

 


}
