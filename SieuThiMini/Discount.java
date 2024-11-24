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
        System.out.print("Nhap ma khuyen mai: ");
        int discountID = scanner.nextInt();
        scanner.nextLine();  // Đọc bỏ dòng newline sau khi nhập int

        System.out.print("Nhap ten chuong trinh khuyen mai: ");
        String name = scanner.nextLine();

        System.out.print("Nhap phan tram giam gia: ");
        double discountPercentage = scanner.nextDouble();

        System.out.print("Nhap ngay bat dau (dd-MM-yyyy): ");
        String startDateStr = scanner.next();
        Date startDate = null;
        try {
            startDate = DATE_FORMAT.parse(startDateStr);
        } catch (ParseException e) {
            System.out.println("Loi dinh dang bat dau.");
            return discounts;
        }

        System.out.print("Nhap ngay ket thuc (dd-MM-yyyy): ");
        String endDateStr = scanner.next();
        Date endDate = null;
        try {
            endDate = DATE_FORMAT.parse(endDateStr);
        } catch (ParseException e) {
            System.out.println("Loi dinh dang ngay ket thuc.");
            return discounts;
        }

        // Tạo đối tượng mới và thêm vào danh sách
        Discount newDiscount = new Discount(discountID, name, discountPercentage, startDate, endDate);

        // Tạo mảng mới với kích thước lớn hơn
        Discount[] updatedDiscounts = new Discount[discounts.length + 1];
        System.arraycopy(discounts, 0, updatedDiscounts, 0, discounts.length);
        updatedDiscounts[discounts.length] = newDiscount;

        System.out.println("Da them chuong trinh khuyen mai: " + name);
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
            System.out.println("Da xoa chuong trinh khuyen mai voi ma: " + removeID);
        } else {
            System.out.println("Khong tim thay chuong trinh khuyen mai voi ma: " + removeID);
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
                    System.out.println("Dong khong hop le: " + line);
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
                    System.out.println("Loi chuyen doi du lieu: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Loi khi doc file: " + e.getMessage());
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
            System.out.println("Danh sach da duoc ghi vao file.");
        } catch (IOException e) {
            System.out.println("Loi khi ghi file: " + e.getMessage());
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
            System.out.println("Thong tin chuong trinh khuyen mai hien tai:");
            System.out.println("Ma khuyen mai: " + discountToUpdate.getDiscountID());
            System.out.println("Ten chuong trinh: " + discountToUpdate.getName());
            System.out.println("Phan tram giam gia: " + discountToUpdate.getDiscountPercentage() + "%");
            System.out.println("Ngay bat dau: " + DATE_FORMAT.format(discountToUpdate.getStartDate()));
            System.out.println("Ngày ket thuc: " + DATE_FORMAT.format(discountToUpdate.getEndDate()));

            // Nhập thông tin mới (hoặc giữ nguyên nếu để trống)
            System.out.println("Nhap thong tin moi (nhan Enter de giu nguyen):");

            System.out.print("Ten chuong trinh moi: ");
            String newName = scanner.nextLine();
            if (!newName.trim().isEmpty()) {
                discountToUpdate.setName(newName);
            }

            System.out.print("Phan tram giam gia moi: ");
            String newDiscountPercentageStr = scanner.nextLine();
            if (!newDiscountPercentageStr.trim().isEmpty()) {
                try {
                    double newDiscountPercentage = Double.parseDouble(newDiscountPercentageStr);
                    discountToUpdate.setDiscountPercentage(newDiscountPercentage);
                } catch (NumberFormatException e) {
                    System.out.println("Loi: Phan tram giam gia khong hop le.");
                }   
            }

            System.out.print("Ngay bat dau moi: (dd-MM-yyyy): ");
            String newStartDateStr = scanner.nextLine();
            if (!newStartDateStr.trim().isEmpty()) {
                try {
                    Date newStartDate = DATE_FORMAT.parse(newStartDateStr);
                    discountToUpdate.setStartDate(newStartDate);
                } 
                catch (ParseException e) {
                System.out.println("Loi: Ngay bat dau khong hop le.");
                }
            }

            System.out.print("Ngay ket thuc moi: (dd-MM-yyyy): ");
            String newEndDateStr = scanner.nextLine();
            if (!newEndDateStr.trim().isEmpty()) {
                try {
                    Date newEndDate = DATE_FORMAT.parse(newEndDateStr);
                    discountToUpdate.setEndDate(newEndDate);
                } catch (ParseException e) {
                    System.out.println("Loi: Ngay ket thuc khong hop le.");
                }
            }

            System.out.println("Thong tin chuong trinh khuyen mai da duoc cap nhat.");
        } 
        else {
            System.out.println("Khong tim thay chuong trinh khuyen mai voi ma: " + discountID);
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
            System.out.println("========= MENU CHUONG TRINH KHUYEN MAI =========");
            System.out.println("1. Xem danh sach khuyen mai");
            System.out.println("2. Them chuong trinh khuyen mai moi");
            System.out.println("3. Tim kiem chuong trinh khuyen mai theo ID");
            System.out.println("4. Xoa chuong trinh khuyen mai theo ID");
            System.out.println("5. Sua chuong trinh khuyen mai theo ID");
            System.out.println("0. Thoat (tu dong ghi vao file)");
            System.out.println("===============================================");
            System.out.print("Nhap lua chon cua ban: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Đọc bỏ dòng newline sau khi nhập int

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
                    System.out.print("Nhap ma chuong trinh khuyen mai can tim: ");
                    int searchID = scanner.nextInt();
                    Discount foundDiscount = Discount.searchDiscountByID(discounts, searchID);
                    if (foundDiscount != null) {
                        System.out.println("Thong tin chuong trinh khuyen mai tim thay:");
                        System.out.println("Ma: " + foundDiscount.getDiscountID());
                        System.out.println("Ten: " + foundDiscount.getName());
                        System.out.println("Phan tram giam: " + foundDiscount.getDiscountPercentage() + "%");
                        System.out.println("Ngay bat dau: " + Discount.DATE_FORMAT.format(foundDiscount.getStartDate()));
                        System.out.println("Ngay ket thuc: " + Discount.DATE_FORMAT.format(foundDiscount.getEndDate()));
                    } else {
                        System.out.println("Khong tim thay chuong trinh khuyen mai voi ma: " + searchID);
                    }
                    break;

                case 4:
                    // Xóa chương trình khuyến mãi
                    System.out.print("Nhap ma chuong trinh khuyen mai can xoa: ");
                    int removeID = scanner.nextInt();
                    discounts = Discount.removeDiscountByID(discounts, removeID);
                    break;

                case 5:
                    // Sửa thông tin chương trình khuyến mãi
                    System.out.print("Nhap ma chuong trinh khuyen mai can sua: ");
                    int updateID = scanner.nextInt();
                    discounts = Discount.updateDiscountByID(discounts, updateID);
                    break;

                case 0:
                    // Thoát chương trình
                    System.out.println("Cam on ban da su dung chuong trinh!");
                    break;
                default:
                    System.out.println("Lua chon khong hop le. Vui long nhap lai.");
            }
            System.out.println(); // Dòng trống để tách các thao tác
        } while (choice != 0);

        // Ghi danh sách khách hàng vào file khi kết thúc chương trình
        Discount.writeToFile(fileName, discounts);
        scanner.close();
    }
}
