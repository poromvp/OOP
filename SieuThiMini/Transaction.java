import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Transaction {
    private int id;
    private Date date;
    private double totalAmount;
    private List<Item> items;

    public Transaction(int id, Date date){
        this.id = id;
        this.date=date;
        this.totalAmount= 0.0;
        this.items = new ArrayList<>();
    }

    public void addItem(Item item){
        items.add(item);
        totalAmount += item.getPrice();
    }

    public void removeItem(Item item){
        if(items.remove(item)){
            totalAmount -= item.getPrice();
        }
    }

    public double getTotal(){
        return totalAmount;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<Item> getItems() {
        return this.items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    
}
