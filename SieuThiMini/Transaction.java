import java.util.Date;

public class Transaction {
    private int id;
    private Date date;
    private double totalAmount;
    private Item[] items;
    private int itemCount;

    public Transaction(int id, Date date) {
        this.id = id;
        this.date = date;
        this.totalAmount = 0.0;
        this.items = new Item[100];
        this.itemCount = 0;
    }

    public void addItem(Item item) {
        if (itemCount < items.length) {
            items[itemCount++] = item;
            totalAmount += item.getPrice();
        } else {
            System.out.println("Can't add more items.");
        }
    }

    public void removeItem(Item item) {
        for (int i = 0; i < itemCount; i++) {
            if (items[i].equals(item)) {
                totalAmount -= items[i].getPrice();
                // Shift items to fill the gap
                for (int j = i; j < itemCount - 1; j++) {
                    items[j] = items[j + 1];
                }
                items[--itemCount] = null;
                break;
            }
        }
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
        this.totalAmount = 0;
        for (Item item : items) {
            this.totalAmount += item.getPrice();
        }
    }
}
