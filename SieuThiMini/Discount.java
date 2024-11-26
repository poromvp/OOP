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
    public static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

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
            System.out.println("Đã xóa chương trình khuyến mãi: " + removeID);
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

    public static void main(String[] args) {
        // Đọc danh sách khuyến mãi từ file
        String fileName = "discount.txt";
        Discount[] discounts = Discount.readFromFile(fileName);

        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("========= MENU CHƯƠNG TRÌNH KHUYẾN MÃI =========");
            System.out.println("1. Xem danh sách khuyến mãi");
            System.out.println("2. Thêm chương trình khuyến mãi mới");
            System.out.println("3. Tìm kiếm chương trình khuyến mãi theo ID");
            System.out.println("4. Xóa chương trình khuyến mãi theo ID");
            System.out.println("5. Sửa chương trình khuyến mãi theo ID");
            System.out.println("0. Thoát (Tự động ghi vào file)");
            System.out.println("===============================================");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    // Xuất danh sách khuyến mãi
                    Discount.outputDiscounts(discounts);
                    break;

                case 2:
                    // Thêm chương trình khuyến mãi mới
                    discounts = Discount.addDiscount(discounts);
                    break;

                case 3:
                    // Tìm kiếm chương trình khuyến mãi
                    System.out.print("Nhập mã chương trình khuyến mãi cần tìm: ");
                    int searchID = Integer.parseInt(scanner.nextLine());
                    Discount foundDiscount = Discount.searchDiscountByID(discounts, searchID);
                    if (foundDiscount != null) {
                        System.out.println("Thông tin chương trình khuyến mãi tìm thấy:");
                        System.out.println("Mã chương trình khuyến mãi: " + foundDiscount.getDiscountID());
                        System.out.println("Tên chương trình khuyến mãi: " + foundDiscount.getName());
                        System.out.println("Phần trăm giảm giá: " + foundDiscount.getDiscountPercentage() + "%");
                        System.out.println("Ngày bắt đầu: " + Discount.DATE_FORMAT.format(foundDiscount.getStartDate()));
                        System.out.println("Ngày kết thúc: " + Discount.DATE_FORMAT.format(foundDiscount.getEndDate()));
                    } else {
                        System.out.println("Không tìm thấy chương trình khuyến mãi với mã: " + searchID);
                    }
                    break;

                case 4:
                    // Xóa chương trình khuyến mãi
                    System.out.print("Nhập mã chương trình khuyến mãi cần xóa: ");
                    int removeID = Integer.parseInt(scanner.nextLine());
                    discounts = Discount.removeDiscountByID(discounts, removeID);
                    break;

                case 5:
                    // Sửa thông tin chương trình khuyến mãi
                    System.out.print("Nhập mã chương trình khuyến mãi cần sửa: ");
                    int updateID = Integer.parseInt(scanner.nextLine());
                    discounts = Discount.updateDiscountByID(discounts, updateID);
                    break;

                case 0:
                    // Thoát chương trình
                    System.out.println("Cảm ơn bạn đã sử dụng chương trình");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn lại.");
            }
            System.out.println(); // Dòng trống để tách các thao tác
        } while (choice != 0);

        // Ghi danh sách khách hàng vào file khi kết thúc chương trình
        Discount.writeToFile(fileName, discounts);
        scanner.close();
    }
}


