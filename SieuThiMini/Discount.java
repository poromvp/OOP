import java.util.Date;
public class Discount {
    // Thuộc tính
    private int discountID; // Mã chương trình khuyến mãi
    private String name; // Tên chương trình khuyến mãi
    private double discountPercentage; 
    private Date startDate; // Ngày bắt đầu
    private Date endDate; // Ngày kết thúc

    // Constructor
    public Discount(int discountID, String name, double discountPercentage, Date startDate, Date endDate) {
        this.discountID = discountID;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Phương thức Getter và Setter
    public int getDiscountID() {
        return discountID;
    }

    public void setDiscountID(int discountID) {
        this.discountID = discountID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(double discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

  
    
}