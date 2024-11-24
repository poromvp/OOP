

import java.util.Date;

public class Transaction {
    private int id;
    private Date date;
    private double totalAmount;
    private Item[] items;
    private int itemCount;
    private double customerPaid;
    public Transaction(int id, Date date) {
        this.id = id;
        this.date = date;
        this.totalAmount = 0.0;
        this.items = new Item[100];
        this.itemCount = 0;
        this.customerPaid = 0.0;
    }

    public void addItem(Item item) {
        if (itemCount < items.length) {
            items[itemCount++] = item;
            totalAmount += item.getTotalPrice();
        } else {
            System.out.println("Không thể thêm sản phẩm: Đã đạt giới hạn.");
        }
    }


    public void setCustomerPaid(double customerPaid) {
        this.customerPaid = customerPaid;
    }


    public double getCustomerPaid() {
        return customerPaid;
    }


    public double getChange() {
        return customerPaid - totalAmount;
    }


    public double getTotal() {
        return totalAmount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public Item[] getItems() {
        Item[] currentItems = new Item[itemCount];
        System.arraycopy(items, 0, currentItems, 0, itemCount);
        return currentItems;
    }

    public void setItems(Item[] items) {
        this.items = items;
        this.itemCount = items.length;
        this.totalAmount = 0.0;
        for (Item item : items) {
            this.totalAmount += item.getTotalPrice();
        }
    }
}
