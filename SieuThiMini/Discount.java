import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // Constructor có tham số
    public Discount(int discountID, String name, double discountPercentage, Date startDate, Date endDate) {
        this.discountID = discountID;
        this.name = name;
        this.discountPercentage = discountPercentage;
        this.startDate = startDate;
        this.endDate = endDate;
    }
    public Discount() {

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
        System.out.println("Danh sách chương trình khuyến mãi:");
        for (Discount discount : discounts) {
            if (discount != null) {
                System.out.println("==================================");
                System.out.println("Mã chương trình khuyến mãi: " + discount.discountID);
                System.out.println("Tên chương trình khuyến mãi: " + discount.name);
                System.out.println("Phần trăm giảm giá: " + discount.discountPercentage + "%");
                System.out.println("Ngày bắt đầu chương trình: " + DATE_FORMAT.format(discount.startDate));
                System.out.println("Ngày kết thúc chương trình: " + DATE_FORMAT.format(discount.endDate));
            }
        }
    }

    // Phương thức thêm chương trình khuyến mãi mới từ bàn phím
    public static Discount[] addDiscounts(Discount[] discounts) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.print("Nhập số lượng chương trình khuyến mãi cần thêm: ");
        int n = Integer.parseInt(scanner.nextLine());
    
        // Tạo một mảng mới với kích thước đủ lớn
        Discount[] updatedDiscounts = new Discount[discounts.length + n];
        System.arraycopy(discounts, 0, updatedDiscounts, 0, discounts.length);
    
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhập thông tin cho chương trình khuyến mãi thứ " + (i + 1) + ":");
    
            // Nhập thông tin chương trình khuyến mãi mới
            System.out.print("Nhập mã khuyến mãi: ");
            int discountID = Integer.parseInt(scanner.nextLine());
    
            System.out.print("Nhập tên chương trình khuyến mãi: ");
            String name = scanner.nextLine();
    
            System.out.print("Nhập phần trăm giảm giá: ");
            double discountPercentage = Integer.parseInt(scanner.nextLine());
    
            System.out.print("Nhập ngày bắt đầu (dd-MM-yyyy): ");
            String startDateStr = scanner.nextLine();
            Date startDate = null;
            try {
                startDate = DATE_FORMAT.parse(startDateStr);
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày bắt đầu. Bỏ qua chương trình này.");
                i--; // Lùi lại để nhập lại chương trình khuyến mãi
                continue;
            }
    
            System.out.print("Nhập ngày kết thúc (dd-MM-yyyy): ");
            String endDateStr = scanner.nextLine();
            Date endDate = null;
            try {
                endDate = DATE_FORMAT.parse(endDateStr);
            } catch (ParseException e) {
                System.out.println("Lỗi định dạng ngày kết thúc. Bỏ qua chương trình này.");
                i--; // Lùi lại để nhập lại chương trình khuyến mãi
                continue;
            }
    
            // Tạo đối tượng mới và thêm vào mảng
            Discount newDiscount = new Discount(discountID, name, discountPercentage, startDate, endDate);
            updatedDiscounts[discounts.length + i] = newDiscount;
    
            System.out.println("Đã thêm chương trình khuyến mãi: " + name);
        }
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
            System.out.println("Đã xóa chương trình khuyến mãi: " + removeID);
        } else {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + removeID);
        }
        return updatedDiscounts;
    }

    // Phương thức tìm kiếm chương trình khuyến mãi theo ID
    public static Discount[] searchDiscounts(Scanner scanner, Discount[] discounts) {
        System.out.println("\n--- TÌM KIẾM CHƯƠNG TRÌNH KHUYẾN MÃI ---");
        System.out.println("Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí):");
    
        System.out.print("Mã khuyến mãi: ");
        String discountIDStr = scanner.nextLine();
        int discountID = discountIDStr.isEmpty() ? 0 : Integer.parseInt(discountIDStr);
    
        System.out.print("Tên khuyến mãi: ");
        String name = scanner.nextLine();
        if (name.isEmpty()) {
            name = null;
        }
    
        System.out.print("Phần trăm giảm giá: ");
        String discountPercentageStr = scanner.nextLine();
        double discountPercentage = discountPercentageStr.isEmpty() ? 0 : Double.parseDouble(discountPercentageStr);
    
        System.out.print("Ngày bắt đầu (dd-MM-yyyy): ");
        String startDateStr = scanner.nextLine();
        Date startDate = null;
        if (!startDateStr.isEmpty()) {
            try {
                startDate = DATE_FORMAT.parse(startDateStr);
            } catch (ParseException e) {
                System.out.println("Ngày bắt đầu không hợp lệ.");
            }
        }
    
        System.out.print("Ngày kết thúc (dd-MM-yyyy): ");
        String endDateStr = scanner.nextLine();
        Date endDate = null;
        if (!endDateStr.isEmpty()) {
            try {
                endDate = DATE_FORMAT.parse(endDateStr);
            } catch (ParseException e) {
                System.out.println("Ngày kết thúc không hợp lệ.");
            }
        }
    
        // Lọc danh sách khuyến mãi theo các tiêu chí
        Discount[] filteredDiscounts = new Discount[discounts.length];
        int count = 0;
    
        for (Discount discount : discounts) {
            if (isDiscountMatch(discount, discountID, name, discountPercentage, startDate, endDate)) {
                filteredDiscounts[count++] = discount;
            }
        }
    
        Discount[] result = Arrays.copyOf(filteredDiscounts, count);
    
        // Hiển thị kết quả
        if (result.length == 0) {
            System.out.println("Không tìm thấy chương trình khuyến mãi nào khớp với tiêu chí.");
        } else {
            System.out.println("\nDanh sách chương trình khuyến mãi tìm thấy:");
            for (Discount discount : result) {
                discount.displayDetails(); // Hiển thị chi tiết mỗi Discount
            }
        }
    
        // Đảm bảo luôn trả về một mảng Discount[]
        return result;
    }
    
    private static boolean isDiscountMatch(Discount discount, int discountID, String name, double discountPercentage, Date startDate, Date endDate) {
        if (discountID != 0 && discount.getDiscountID() != discountID) {
            return false;
        }
        if (name != null && !discount.getName().toLowerCase().contains(name.toLowerCase())) {
            return false;
        }
        if (discountPercentage != 0 && discount.getDiscountPercentage() != discountPercentage) {
            return false;
        }
        if (startDate != null && discount.getStartDate().before(startDate)) {
            return false;
        }
        if (endDate != null && discount.getEndDate().after(endDate)) {
            return false;
        }
        return true;
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

    // Phương thức sửa thông tin chương trình khuyến mãi theo ID
    public static Discount[] updateDiscountByID(Discount[] discounts, int discountID) {
        Scanner scanner = new Scanner(System.in);

        // Tìm kiếm chương trình khuyến mãi theo ID
        Discount discountToUpdate = null;
        for (Discount discount : discounts) {
            if (discount.getDiscountID() == discountID) {
                discountToUpdate = discount;
                break;
            }
        }

        if (discountToUpdate != null) {
            // Hiển thị thông tin hiện tại của chương trình khuyến mãi
            System.out.println("Thông tin chương trình khuyến mãi hiện tại:");
            System.out.println("Mã chương trình khuyến mãi: " + discountToUpdate.getDiscountID());
            System.out.println("Tên chương trình khuyến mãi: " + discountToUpdate.getName());
            System.out.println("Phần trăm giảm giá: " + discountToUpdate.getDiscountPercentage() + "%");
            System.out.println("Ngày bắt đầu: " + DATE_FORMAT.format(discountToUpdate.getStartDate()));
            System.out.println("Ngày kết thúc: " + DATE_FORMAT.format(discountToUpdate.getEndDate()));

            // Nhập thông tin mới (hoặc giữ nguyên nếu để trống)
            System.out.println("Nhập thông tin mới (nhấn Enter để giữ nguyên):");

            System.out.print("Tên chương trình khuyến mãi mới: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                discountToUpdate.setName(newName);
            }

            System.out.print("Phần trăm giảm giá mới: ");
            String newDiscountPercentageStr = scanner.nextLine();
            if (!newDiscountPercentageStr.trim().isEmpty()) {
                try {
                    double newDiscountPercentage = Double.parseDouble(newDiscountPercentageStr);
                    discountToUpdate.setDiscountPercentage(newDiscountPercentage);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: phần trăm giảm giá không hợp lệ.");
                }   
            }

            System.out.print("Ngày bắt đầu mới: (dd-MM-yyyy): ");
            String newStartDateStr = scanner.nextLine();
            if (!newStartDateStr.trim().isEmpty()) {
                try {
                    Date newStartDate = DATE_FORMAT.parse(newStartDateStr);
                    discountToUpdate.setStartDate(newStartDate);
                } 
                catch (ParseException e) {
                System.out.println("Lỗi: Ngày bắt đầu không hợp lệ.");
                }
            }

            System.out.print("Ngày kết thúc mới: (dd-MM-yyyy): ");
            String newEndDateStr = scanner.nextLine();
            if (!newEndDateStr.trim().isEmpty()) {
                try {
                    Date newEndDate = DATE_FORMAT.parse(newEndDateStr);
                    discountToUpdate.setEndDate(newEndDate);
                } catch (ParseException e) {
                    System.out.println("Lỗi: Ngày kết thúc không hợp lệ.");
                }
            }

            System.out.println("Thông tin chương trình khuyến mãi đã được cập nhật.");
        } 
        else {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + discountID);
        }

        return discounts; // Trả về danh sách đã được cập nhật
    }

    // Thêm phương thức hiển thị chi tiết khuyến mãi
    public void displayDetails() {
        System.out.println("==================================");
        System.out.println("Mã chương trình khuyến mãi: " + this.discountID);
        System.out.println("Tên chương trình khuyến mãi: " + this.name);
        System.out.println("Phần trăm giảm giá: " + this.discountPercentage + "%");
        System.out.println("Ngày bắt đầu: " + DATE_FORMAT.format(this.startDate));
        System.out.println("Ngày kết thúc: " + DATE_FORMAT.format(this.endDate));
    }

    public double applyDiscount(double total) {
        return 0;
    }
}


