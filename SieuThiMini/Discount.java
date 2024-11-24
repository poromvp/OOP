import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Discount {
    // Thuộc tính
    private int discountID;
    private String name;
    private double discountPercentage;
    private Date startDate;
    private Date endDate;

    // Định dạng ngày
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // Constructor có tham số
    public Discount(int discountID, String name, double discountPercentage, Date startDate, Date endDate) {
        this.discountID = discountID;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getter và Setter
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

    // Phương thức xuất danh sách khuyến mãi
    public static void outputDiscounts(Discount[] discounts) {
        System.out.println("Danh sach chuong trinh khuyen mai:");
        for (Discount discount : discounts) {
            if (discount != null) {
                System.out.println("==================================");
                System.out.println("Ma khuyen mai: " + discount.discountID);
                System.out.println("Ten chuong trinh khuyen mai: " + discount.name);
                System.out.println("Phan tram giam gia: " + discount.discountPercentage + "%");
                System.out.println("Ngay bat dau: " + DATE_FORMAT.format(discount.startDate));
                System.out.println("Ngay ket thuc: " + DATE_FORMAT.format(discount.endDate));
            }
        }
    }

    // Phương thức thêm chương trình khuyến mãi mới từ bàn phím
    public static Discount[] addDiscount(Discount[] discounts) {
        Scanner scanner = new Scanner(System.in);

        // Nhập thông tin chương trình khuyến mãi mới
        System.out.print("Nhập mã khuyến mãi: ");
        int discountID = scanner.nextInt();
        scanner.nextLine();  // Đọc bỏ dòng newline sau khi nhập int

        System.out.print("Nhập tên chương trình khuyến mãi: ");
        String name = scanner.nextLine();

        System.out.print("Nhập phần trăm giảm giá: ");
        double discountPercentage = scanner.nextDouble();

        System.out.print("Nhập ngày bắt đầu (dd-MM-yyyy): ");
        String startDateStr = scanner.next();
        Date startDate = null;
        try {
            startDate = DATE_FORMAT.parse(startDateStr);
        } catch (ParseException e) {
            System.out.println("Lỗi định dạng ngày bắt đầu.");
            return discounts;
        }

        System.out.print("Nhập ngày kết thúc (dd-MM-yyyy): ");
        String endDateStr = scanner.next();
        Date endDate = null;
        try {
            endDate = DATE_FORMAT.parse(endDateStr);
        } catch (ParseException e) {
            System.out.println("Lỗi định dạng ngày kết thúc.");
            return discounts;
        }

        // Tạo đối tượng mới và thêm vào danh sách
        Discount newDiscount = new Discount(discountID, name, discountPercentage, startDate, endDate);

        // Tạo mảng mới với kích thước lớn hơn
        Discount[] updatedDiscounts = new Discount[discounts.length + 1];
        System.arraycopy(discounts, 0, updatedDiscounts, 0, discounts.length);
        updatedDiscounts[discounts.length] = newDiscount;

        System.out.println("Đã thêm chương trình khuyến mãi: " + name);
        return updatedDiscounts;   
    }

    // Phương thức xóa chương trình khuyến mãi theo ID
    public static Discount[] removeDiscountByID(Discount[] discounts, int removeID) {
        // Tạo mảng mới có kích thước nhỏ hơn 1
        Discount[] updatedDiscounts = new Discount[discounts.length - 1];
        boolean found = false;
        int index = 0;

        for (int i = 0; i < discounts.length; i++) {
            if (discounts[i].getDiscountID() != removeID) {
                updatedDiscounts[index++] = discounts[i];
            } else {
                found = true;
            }
        }

        if (found) {
            System.out.println("Đã xóa chương trình khuyến mãi với mã: " + removeID);
        } else {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + removeID);
        }
        return updatedDiscounts;
    }

    // Phương thức tìm kiếm chương trình khuyến mãi theo ID
    public static Discount searchDiscountByID(Discount[] discounts, int searchID) {
        for (Discount discount : discounts) {
            if (discount.getDiscountID() == searchID) {
                return discount;
            }
        }
        return null;
    }

    // Phương thức đọc danh sách từ file
    public static Discount[] readFromFile(String fileName) {
        Discount[] discounts = new Discount[0];
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] parts = line.split(",");
                if (parts.length != 5) {
                    System.out.println("Dòng không hợp lệ: " + line);
                    continue;
                }
                try {
                    int discountID = Integer.parseInt(parts[0].trim());
                    String name = parts[1].trim();
                    double discountPercentage = Double.parseDouble(parts[2].trim());
                    Date startDate = DATE_FORMAT.parse(parts[3].trim());
                    Date endDate = DATE_FORMAT.parse(parts[4].trim());
                    Discount newDiscount = new Discount(discountID, name, discountPercentage, startDate, endDate);
                    
                    // Thêm đối tượng mới vào mảng
                    Discount[] newDiscounts = new Discount[discounts.length + 1];
                    System.arraycopy(discounts, 0, newDiscounts, 0, discounts.length);
                    newDiscounts[discounts.length] = newDiscount;
                    discounts = newDiscounts;
                } catch (NumberFormatException | ParseException e) {
                    System.out.println("Lỗi chuyển đổi dữ liệu: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        }
        return discounts;
    }

    // Phương thức ghi danh sách vào file
    public static void writeToFile(String fileName, Discount[] discounts) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (Discount discount : discounts) {
                writer.write(discount.discountID + "," + discount.name + "," + discount.discountPercentage + ","
                        + DATE_FORMAT.format(discount.startDate) + "," + DATE_FORMAT.format(discount.endDate));
                writer.newLine();
            }
            System.out.println("Danh sách đã được ghi vào file.");
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    // Hàm main
    public static void main(String[] args) {
        // Đọc danh sách khuyến mãi từ file
        String fileName = "discount.txt";
        Discount[] discounts = readFromFile(fileName);

        // Xuất danh sách ban đầu
        outputDiscounts(discounts);

        // Thêm chương trình khuyến mãi mới từ bàn phím
        discounts = addDiscount(discounts);
        
        // Ghi danh sách sau khi thêm vào file
        writeToFile(fileName, discounts);

        // Tìm kiếm chương trình khuyến mãi
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập mã chương trình khuyến mãi cần tìm: ");
        int searchID = scanner.nextInt();
        Discount foundDiscount = searchDiscountByID(discounts, searchID);
        if (foundDiscount != null) {
            System.out.println("Tìm thấy chương trình khuyến mãi với mã: " + foundDiscount.name);
        } else {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + searchID);
        }

        // Xóa chương trình khuyến mãi
        System.out.print("Nhập mã chương trình khuyến mãi cần xóa: ");
        int removeID = scanner.nextInt();
        discounts = removeDiscountByID(discounts, removeID);

        // Ghi danh sách sau khi xóa vào file
        writeToFile(fileName, discounts);
        scanner.close();
    }
}
