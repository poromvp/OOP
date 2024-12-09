import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Scanner;

public class Discount implements QLFile{
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
        // Tiêu đề bảng
        System.out.printf("%-53s╔══════════════════════════════════════════════════════════════════════════════════╗\n", " ");
        System.out.printf("%-53s║               DANH SÁCH CHƯƠNG TRÌNH KHUYẾN MÃI                                  ║\n", " ");
        System.out.printf("%-53s╚══════════════════════════════════════════════════════════════════════════════════╝\n\n", " ");
        
        // Tiêu đề các cột
        System.out.printf("%40s╔═══════════╦════════════════════════════╦═══════════════╦════════════════════╦════════════════════╗\n", " ");
        System.out.printf("%40s║ Mã CTKM   ║  Tên Chương Trình          ║ %% Giảm Giá    ║ Ngày Bắt Đầu       ║ Ngày Kết Thúc      ║\n", " ");
        
        // Dữ liệu các chương trình khuyến mãi
        for (Discount discount : discounts) {
            if (discount != null) {
                System.out.printf("%40s╠═══════════╬════════════════════════════╬═══════════════╬════════════════════╬════════════════════╣\n", " ");
                System.out.printf("%40s║ %-10s║ %-27s║ %-14s║ %-19s║ %-19s║\n", 
                    " ", 
                    discount.discountID, 
                    discount.name, 
                    discount.discountPercentage + "%", 
                    DATE_FORMAT.format(discount.startDate), 
                    DATE_FORMAT.format(discount.endDate)
                );
            }
        }
        
        // Đóng bảng
        System.out.printf("%40s╚═══════════╩════════════════════════════╩═══════════════╩════════════════════╩════════════════════╝\n\n", " ");
    }
    
    
    // Phương thức thêm chương trình khuyến mãi mới từ bàn phím
    /*
    public static Discount[] addDiscounts(Discount[] discounts, Scanner scanner) {
    
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
    */

    // Phương thức xóa chương trình khuyến mãi theo ID
    public static Discount[] removeDiscountByID(Discount[] discounts, int removeID) {
        int count = 0;
        // Đếm số lượng chương trình không bị xóa
        for (Discount discount : discounts) {
            if (discount != null && discount.getDiscountID() != removeID) {
                count++;
            }
        }
    
        // Nếu không tìm thấy chương trình nào để xóa
        if (count == discounts.length) {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + removeID);
            return discounts;  // Trả về mảng gốc nếu không có gì thay đổi
        }
    
        // Tạo mảng mới với kích thước phù hợp
        Discount[] updatedDiscounts = new Discount[count];
        int index = 0;
    
        // Sao chép các chương trình không bị xóa vào mảng mới
        for (Discount discount : discounts) {
            if (discount != null && discount.getDiscountID() != removeID) {
                updatedDiscounts[index++] = discount;
            }
        }
    
        System.out.println("Đã xóa chương trình khuyến mãi với mã: " + removeID);
        return updatedDiscounts;
    }

    // Phương thức tìm kiếm chương trình khuyến mãi theo ID
    public static void searchDiscounts(Scanner scanner, Discount[] discounts) {
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
            Discount.outputDiscounts(result); // Hiển thị chi tiết mỗi Discount
            ; 
        }
    }
    
    private static boolean isDiscountMatch(Discount discount, int discountID, String name, double discountPercentage, Date startDate, Date endDate) {
        if (discountID != 0 && discount.getDiscountID() != discountID) {
            return false; // Không khớp mã khuyến mãi
        }
        if (name != null && !discount.getName().equalsIgnoreCase(name)) {
            return false; // Không khớp tên khuyến mãi
        }
        if (discountPercentage != 0 && discount.getDiscountPercentage() != discountPercentage) {
            return false; // Không khớp phần trăm giảm giá
        }
        if (startDate != null && !discount.getStartDate().equals(startDate)) {
            return false; // Không khớp ngày bắt đầu
        }
        if (endDate != null && !discount.getEndDate().equals(endDate)) {
            return false; // Không khớp ngày kết thúc
        }
        return true; // Khớp tất cả tiêu chí
    }
    
    @Override
    // Phương thức đọc danh sách từ file
    public Discount[] readFromFile(String fileName) {
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

    public void xoaNoiDungFile(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, false))) {
            // Mở file ở chế độ ghi đè nhưng không ghi gì cả
            writer.close();
        } catch (IOException e) {
            System.out.println("Lỗi khi xóa dữ liệu trong file: " + e.getMessage());
        }
    }

    @Override
    // Phương thức ghi danh sách vào file
    public void writeToFile(String fileName) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName,true))) {
                writer.write(discountID + "," + name + "," + discountPercentage + ","
                        + DATE_FORMAT.format(startDate) + "," + DATE_FORMAT.format(endDate));
                writer.newLine();
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }
    
    // Phương thức sửa thông tin chương trình khuyến mãi theo ID
    /*public static Discount[] updateDiscountByID(Discount[] discounts, int discountID, Scanner scanner) {
        
        // Tìm kiếm chương trình khuyến mãi theo ID
        Discount discountToUpdate = null;
        for (Discount discount : discounts) {
            if (discount.getDiscountID() == discountID) {
                discountToUpdate = discount;
                break;
            }
        }
        
        if (discountToUpdate != null) {
            // Hiển thị thông tin chương trình khuyến mãi
            System.out.printf(
                    "%40s╔═══════════╦═════════════════════════╦═══════════════╦════════════════╦════════════════╗\n",
                    " ");
            System.out.printf(
                    "%40s║  Mã CTKM  ║      Tên Chương Trình   ║ %% Giảm Giá    ║ Ngày Bắt Đầu   ║  Ngày Kết Thúc ║\n",
                    " ");
            System.out.printf(
                    "%40s╠═══════════╬═════════════════════════╬═══════════════╬════════════════╬════════════════╣\n", " ");
            System.out.printf(
                    "%40s║ %-10s║ %-24s║ %-14s║ %-15s║ %-15s║\n",
                    " ",
                    discountToUpdate.getDiscountID(),
                    discountToUpdate.getName(),
                    discountToUpdate.getDiscountPercentage() + "%",
                    DATE_FORMAT.format(discountToUpdate.getStartDate()),
                    DATE_FORMAT.format(discountToUpdate.getEndDate())
            );
            System.out.printf(
                    "%40s╚═══════════╩═════════════════════════╩═══════════════╩════════════════╩════════════════╝\n\n",
                    " ");
        
            System.out.println("\nNhấn Enter để giữ nguyên thông tin hiện tại.");
        
            // Cập nhật tên chương trình
            System.out.print("Tên chương trình khuyến mãi mới: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                discountToUpdate.setName(newName);
            }
        
            // Cập nhật phần trăm giảm giá
            System.out.print("Phần trăm giảm giá mới: ");
            String newDiscountPercentageStr = scanner.nextLine();
            if (!newDiscountPercentageStr.trim().isEmpty()) {
                try {
                    double newDiscountPercentage = Double.parseDouble(newDiscountPercentageStr);
                    discountToUpdate.setDiscountPercentage(newDiscountPercentage);
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Phần trăm giảm giá không hợp lệ. Giữ nguyên giá trị hiện tại.");
                }
            }
        
            // Cập nhật ngày bắt đầu
            System.out.print("Ngày bắt đầu mới (dd-MM-yyyy): ");
            String newStartDateStr = scanner.nextLine();
            if (!newStartDateStr.trim().isEmpty()) {
                try {
                    Date newStartDate = DATE_FORMAT.parse(newStartDateStr);
                    discountToUpdate.setStartDate(newStartDate);
                } catch (ParseException e) {
                    System.out.println("Lỗi: Ngày bắt đầu không hợp lệ. Giữ nguyên giá trị hiện tại.");
                }
            }
        
            // Cập nhật ngày kết thúc
            System.out.print("Ngày kết thúc mới (dd-MM-yyyy): ");
            String newEndDateStr = scanner.nextLine();
            if (!newEndDateStr.trim().isEmpty()) {
                try {
                    Date newEndDate = DATE_FORMAT.parse(newEndDateStr);
                    discountToUpdate.setEndDate(newEndDate);
                } catch (ParseException e) {
                    System.out.println("Lỗi: Ngày kết thúc không hợp lệ. Giữ nguyên giá trị hiện tại.");
                }
            }
        
            System.out.println("\nThông tin chương trình khuyến mãi đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + discountID);
        }
        return discounts; // Trả về danh sách đã được cập nhật
    } */
        
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
    
    public Discount getDiscountByDay(Date id) {
        Discount[] discount = readFromFile("discount.txt");
        for (Discount discountObj : discount) {
            if (discountObj != null) {
                Date startDate = discountObj.getStartDate();
                Date endDate = discountObj.getEndDate();
    
                // Kiểm tra nếu id nằm trong khoảng thời gian từ startDate đến endDate
                if ((id.equals(startDate) || id.after(startDate)) && (id.equals(endDate) || id.before(endDate))) {
                    return discountObj;  // Trả về chương trình khuyến mãi nếu ngày nằm trong khoảng
                }
            }
        }
        return null;  // Nếu không tìm thấy chương trình khuyến mãi hợp lệ
    }

    // Phương thức kiểm tra phần trăm giảm giá không được là số âm
    public static double validateDiscountPercentage(Scanner scanner) {
        double percentage;
        while (true) {
            System.out.print("Nhập phần trăm giảm giá (không được âm): ");
            try {
                percentage = Double.parseDouble(scanner.nextLine());
                if (percentage >= 0) {
                    break;
                } else {
                    System.out.println("Lỗi: Phần trăm giảm giá không được âm. Vui lòng nhập lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Giá trị không hợp lệ. Vui lòng nhập một số.");
            }
        }
        return percentage;
    }
    
    // Phương thức kiểm tra nhập vào id không được trùng
    public static int validateUniqueDiscountID(Discount[] discounts, Scanner scanner) {
        int id;
        while (true) {
            System.out.print("Nhập mã khuyến mãi: ");
            try {
                id = Integer.parseInt(scanner.nextLine());
                boolean exists = false;
                for (Discount discount : discounts) {
                    if (discount != null && discount.getDiscountID() == id) {
                        exists = true;
                        break;
                    }
                }
                if (!exists) {
                    break;
                } else {
                    System.out.println("Lỗi: Mã khuyến mãi đã tồn tại. Vui lòng nhập mã khác.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Giá trị không hợp lệ. Vui lòng nhập một số nguyên.");
            }
        }
        return id;
    }
    
    public static Date validateDate(String message, Scanner scanner) {
        while (true) {
            System.out.print(message);
            String dateStr = scanner.nextLine();
            try {
                return DATE_FORMAT.parse(dateStr);
            } catch (ParseException e) {
                System.out.println("Lỗi: Định dạng ngày không hợp lệ. Vui lòng nhập lại (dd-MM-yyyy).");
            }
        }
    }
    
    // Phương thức kiểm tra ngày bắt đầu không được sau ngày kết thúc
    public static Date[] validateDateRange(Scanner scanner) {
        Date startDate, endDate;
        while (true) {
            startDate = validateDate("Nhập ngày bắt đầu (dd-MM-yyyy): ", scanner);
            endDate = validateDate("Nhập ngày kết thúc (dd-MM-yyyy): ", scanner);
            if (!endDate.before(startDate)) {
                break;
            } else {
                System.out.println("Lỗi: Ngày kết thúc không được trước ngày bắt đầu. Vui lòng nhập lại.");
            }
        }
        return new Date[]{startDate, endDate};
    }
    
    // Phương thức cập nhật chương trình khuyến mãi sau khi có ràng buộc
    public static Discount[] updateDiscountByID(Discount[] discounts, int discountID, Scanner scanner) {
        // Tìm kiếm chương trình khuyến mãi theo ID
        Discount discountToUpdate = null;
        for (Discount discount : discounts) {
            if (discount.getDiscountID() == discountID) {
                discountToUpdate = discount;
                break;
            }
        }
    
        if (discountToUpdate != null) {
            // Hiển thị thông tin chương trình khuyến mãi
            System.out.println("\nThông tin chương trình khuyến mãi hiện tại:");
            discountToUpdate.displayDetails();
            System.out.println("\nNhấn Enter để giữ nguyên thông tin hiện tại.");
    
            // Cập nhật tên chương trình
            System.out.print("Tên chương trình khuyến mãi mới: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                discountToUpdate.setName(newName);
            }
    
            // Cập nhật phần trăm giảm giá với ràng buộc
            System.out.print("Phần trăm giảm giá mới: ");
            String newDiscountPercentageStr = scanner.nextLine();
            if (!newDiscountPercentageStr.trim().isEmpty()) {
                try {
                    double newDiscountPercentage = Double.parseDouble(newDiscountPercentageStr);
                    if (newDiscountPercentage >= 0) {
                        discountToUpdate.setDiscountPercentage(newDiscountPercentage);
                    } else {
                        System.out.println("Lỗi: Phần trăm giảm giá không được âm. Giữ nguyên giá trị hiện tại.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Lỗi: Phần trăm giảm giá không hợp lệ. Giữ nguyên giá trị hiện tại.");
                }
            }
    
            // Cập nhật ngày bắt đầu và kết thúc với ràng buộc
            System.out.print("Ngày bắt đầu mới (dd-MM-yyyy): ");
            String newStartDateStr = scanner.nextLine();
            if (!newStartDateStr.trim().isEmpty()) {
                try {
                    Date newStartDate = DATE_FORMAT.parse(newStartDateStr);
                    discountToUpdate.setStartDate(newStartDate);
                } catch (ParseException e) {
                    System.out.println("Lỗi: Ngày bắt đầu không hợp lệ. Giữ nguyên giá trị hiện tại.");
                }
            }
    
            System.out.print("Ngày kết thúc mới (dd-MM-yyyy): ");
            String newEndDateStr = scanner.nextLine();
            if (!newEndDateStr.trim().isEmpty()) {
                try {
                    Date newEndDate = DATE_FORMAT.parse(newEndDateStr);
                    if (newEndDate.after(discountToUpdate.getStartDate())) {
                        discountToUpdate.setEndDate(newEndDate);
                    } else {
                        System.out.println("Lỗi: Ngày kết thúc phải sau ngày bắt đầu. Giữ nguyên giá trị hiện tại.");
                    }
                } catch (ParseException e) {
                    System.out.println("Lỗi: Ngày kết thúc không hợp lệ. Giữ nguyên giá trị hiện tại.");
                }
            }
    
            System.out.println("\nThông tin chương trình khuyến mãi đã được cập nhật thành công.");
        } else {
            System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + discountID);
        }
        return discounts; // Trả về danh sách đã được cập nhật
    }

    // Phương thức thêm chương trình khuyến mãi sau khi có ràng buộc
    public static Discount[] addDiscounts(Discount[] discounts, Scanner scanner) {
        System.out.print("Nhập số lượng chương trình khuyến mãi cần thêm: ");
        int n = Integer.parseInt(scanner.nextLine());
    
        // Tạo một mảng mới với kích thước đủ lớn
        Discount[] updatedDiscounts = new Discount[discounts.length + n];
        System.arraycopy(discounts, 0, updatedDiscounts, 0, discounts.length);
    
        for (int i = 0; i < n; i++) {
            System.out.println("\nNhập thông tin cho chương trình khuyến mãi thứ " + (i + 1) + ":");
    
            // Ràng buộc ID không trùng
            int discountID = validateUniqueDiscountID(updatedDiscounts, scanner);
    
            // Nhập tên chương trình khuyến mãi
            System.out.print("Nhập tên chương trình khuyến mãi: ");
            String name = scanner.nextLine();
    
            // Ràng buộc phần trăm giảm giá không âm
            double discountPercentage = validateDiscountPercentage(scanner);
    
            // Ràng buộc ngày bắt đầu và kết thúc hợp lệ
            Date[] dateRange = validateDateRange(scanner);
            Date startDate = dateRange[0];
            Date endDate = dateRange[1];
    
            // Tạo đối tượng mới và thêm vào mảng
            Discount newDiscount = new Discount(discountID, name, discountPercentage, startDate, endDate);
            updatedDiscounts[discounts.length + i] = newDiscount;
    
            System.out.println("Đã thêm chương trình khuyến mãi: " + name);
        }
        return updatedDiscounts;
    }
}


