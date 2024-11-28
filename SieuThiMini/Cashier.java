import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Cashier extends Staff {
    private String CashierID;
    private String CashierName;
    private String role;
    private String shift;
    private int numWorkingDays;

    // Constructor mặc định
    public Cashier() {
    }

    // Constructor với tất cả thông tin
    public Cashier(String CashierID, String CashierName, String role, String shift, int numWorkingDays) {
        this.CashierID = CashierID;
        this.CashierName = CashierName;
        this.role = role;
        this.shift = shift;
        this.numWorkingDays = numWorkingDays;
    }

    // Getter và Setter
    public String getCashierID() {
        return CashierID;
    }

    public void setCashierID(String CashierID) {
        this.CashierID = CashierID;
    }

    public String getCashierName() {
        return CashierName;
    }

    public void setCashierName(String CashierName) {
        this.CashierName = CashierName;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public int getNumWorkingDays() {
        return numWorkingDays;
    }

    public void setNumWorkingDays(int numWorkingDays) {
        this.numWorkingDays = numWorkingDays;
    }

    @Override
    // Phương thức đọc thông tin thu ngân từ file
    public Cashier[] readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Cashier[] cashiers = new Cashier[0]; // Bắt đầu với một mảng rỗng

            // Đọc từng dòng trong file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");

                // Kiểm tra độ dài mảng parts (số phần tử trong dòng dữ liệu)
                if (parts.length >= 5) { // 5 phần tử cho CashierID, CashierName, role, shift, numWorkingDays
                    String cashierID = parts[0];
                    String cashierName = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 3));
                    String role = parts[parts.length - 3];
                    String shift = parts[parts.length - 2];
                    int numWorkingDays = Integer.parseInt(parts[parts.length - 1]);

                    // Tạo một đối tượng Cashier mới với các tham số đầy đủ
                    Cashier newCashier = new Cashier(cashierID, cashierName, role, shift, numWorkingDays);

                    // Mở rộng mảng cashiers và thêm Cashier mới vào
                    cashiers = Arrays.copyOf(cashiers, cashiers.length + 1);
                    cashiers[cashiers.length - 1] = newCashier;
                }
            }

            return cashiers;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new Cashier[0]; // Trả về mảng rỗng nếu có lỗi
        }
    }

    // Phương thức ghi lại dữ liệu vào file
    public void writeToFile(String fileName, Cashier[] cashiers) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Duyệt qua mảng các đối tượng Cashier và ghi thông tin vào file
            for (Cashier cashier : cashiers) {
                writer.write(cashier.getCashierID() + " " + cashier.getCashierName() + " " + cashier.getRole() + " " + cashier.getShift() + " " + cashier.getNumWorkingDays());
                writer.newLine(); // Thêm dòng mới sau mỗi Cashier
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    // Phương thức in thông tin thu ngân dưới dạng bảng
    public void getdetail() {
        Cashier[] cashiers = readFromFile("SieuThiMini\\CashierList.txt");
        System.out.println("╔════════════╤══════════════════════════════════════╤═════════════════╤════════════╤══════════════════╗");
        System.out.println("║ Mã NV      │ Họ Tên                              │ Vai trò         │ Ca Làm     │ Ngày Công       ║");
        System.out.println("╠════════════╪══════════════════════════════════════╪═════════════════╪════════════╪══════════════════╣");

        for (Cashier cashier : cashiers) {
            System.out.printf("║ %-10s │ %-35s │ %-15s │ %-10s │ %-15d ║\n",
                    cashier.getCashierID(),
                    cashier.getCashierName(),
                    cashier.getRole(),
                    cashier.getShift(),
                    cashier.getNumWorkingDays());
        }

        System.out.println("╚════════════╧═══════════════════════════════════════╧═════════════════╧════════════╧══════════════════╝");
    }

    // Phương thức thêm một Cashier mới vào danh sách
    public void add() {
        // Hiển thị danh sách thu ngân hiện tại
        Cashier[] cashiers = readFromFile("CashierList.txt");
        getdetail();;;

        // Khởi tạo scanner để nhập dữ liệu
        Scanner sc = new Scanner(System.in);

        // Nhập thông tin thu ngân mới
        System.out.print("Nhập mã thu ngân mới: ");
        String cashierID = sc.nextLine();

        System.out.print("Nhập tên thu ngân mới: ");
        String cashierName = sc.nextLine();

        System.out.print("Nhập vai trò thu ngân mới: ");
        String role = sc.nextLine();

        System.out.print("Nhập ca làm việc của thu ngân mới: ");
        String shift = sc.nextLine();

        System.out.print("Nhập số ngày công của thu ngân mới: ");
        int numWorkingDays = Integer.parseInt(sc.nextLine());

        // Tạo một đối tượng Cashier mới
        Cashier newCashier = new Cashier(cashierID, cashierName, role, shift, numWorkingDays);

        // Thêm thu ngân mới vào danh sách và ghi lại vào file
        cashiers = Arrays.copyOf(cashiers, cashiers.length + 1);
        cashiers[cashiers.length - 1] = newCashier;
        writeToFile("CashierList.txt", cashiers);

        // In danh sách thu ngân sau khi cập nhật
        System.out.println("Danh sách thu ngân sau khi cập nhật: ");
        getdetail();
    }

    @Override
    // Phương thức xóa một Cashier khỏi danh sách
    public void remove() {
        // Hiển thị danh sách thu ngân
        Cashier[] cashiers = readFromFile("CashierList.txt");
        getdetail();

        // Khởi tạo scanner để nhập mã thu ngân cần xóa
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã thu ngân bạn muốn xóa: ");
        String cashierID = sc.nextLine();

        // Duyệt qua mảng cashiers để tìm và xóa thu ngân
        boolean found = false;
        int newSize = 0;
        for (Cashier cashier : cashiers) {
            if (!cashier.getCashierID().equals(cashierID)) {
                newSize++;
            }
        }

        if (newSize == cashiers.length) {
            System.out.println("Không tìm thấy thu ngân với mã: " + cashierID);
        } else {
            // Tạo mảng mới và sao chép các thu ngân không bị xóa
            Cashier[] updatedCashiers = new Cashier[newSize];
            int index = 0;

            for (Cashier cashier : cashiers) {
                if (!cashier.getCashierID().equals(cashierID)) {
                    updatedCashiers[index++] = cashier;
                }
            }

            // Ghi lại dữ liệu vào file
            writeToFile("CashierList.txt", updatedCashiers);
            System.out.println("Thu ngân với mã " + cashierID + " đã được xóa.");
        }

        // In danh sách thu ngân sau khi xóa
        System.out.println("Danh sách thu ngân sau khi xóa: ");
        getdetail();
    }

    @Override
    // Phương thức thay đổi thông tin thu ngân
    public void ChangeInFo() {
        // Hiển thị danh sách thu ngân
        Cashier[] cashiers = readFromFile("CashierList.txt");
        getdetail();

        // Khởi tạo scanner để nhập thông tin thu ngân cần sửa
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã thu ngân bạn muốn thay đổi thông tin: ");
        String cashierID = sc.nextLine();

        boolean found = false;
        for (Cashier cashier : cashiers) {
            if (cashier.getCashierID().equals(cashierID)) {
                found = true;
                System.out.println("Thu ngân tìm thấy: " + cashier.getCashierName());

                // Nhập thông tin mới
                System.out.print("Nhập tên thu ngân mới: ");
                cashier.setCashierName(sc.nextLine());

                System.out.print("Nhập vai trò thu ngân mới: ");
                cashier.setRole(sc.nextLine());

                System.out.print("Nhập ca làm việc thu ngân mới: ");
                cashier.setShift(sc.nextLine());

                System.out.print("Nhập số ngày công thu ngân mới: ");
                cashier.setNumWorkingDays(Integer.parseInt(sc.nextLine()));

                break;
            }
        }

        if (found) {
            // Ghi lại dữ liệu vào file
            writeToFile("CashierList.txt", cashiers);
            System.out.println("Thông tin thu ngân đã được cập nhật.");
        } else {
            System.out.println("Không tìm thấy thu ngân với mã: " + cashierID);
        }

        // In danh sách thu ngân sau khi cập nhật
        getdetail();
    }

    @Override
    public void search() {
        // Đọc dữ liệu từ file
        Cashier[] cashiers = readFromFile("CashierList.txt");
    
        // Khởi tạo scanner để nhập từ bàn phím
        Scanner sc = new Scanner(System.in);
        
        // Hỏi người dùng chọn tìm kiếm theo mã hay tên
        System.out.println("Bạn muốn tìm kiếm theo (1) Mã thu ngân, (2) Tên thu ngân?");
        int choice = Integer.parseInt(sc.nextLine());
    
        boolean found = false;
    
        // Nếu tìm kiếm theo mã thu ngân
        if (choice == 1) {
            System.out.print("Nhập mã thu ngân: ");
            String cashierID = sc.nextLine();
    
            // Duyệt qua mảng các thu ngân và tìm theo mã
            for (Cashier cashier : cashiers) {
                if (cashier.getCashierID().equals(cashierID)) {
                    System.out.println("Thu ngân tìm thấy:");
                    System.out.println("Mã NV: " + cashier.getCashierID());
                    System.out.println("Tên: " + cashier.getCashierName());
                    System.out.println("Vai trò: " + cashier.getRole());
                    System.out.println("Ca làm: " + cashier.getShift());
                    System.out.println("Số ngày công: " + cashier.getNumWorkingDays());
                    found = true;
                    break;
                }
            }
        }
        // Nếu tìm kiếm theo tên thu ngân
        else if (choice == 2) {
            System.out.print("Nhập tên thu ngân: ");
            String cashierName = sc.nextLine().toLowerCase(); // Chuyển tên nhập vào thành chữ thường
    
            // Duyệt qua mảng các thu ngân và tìm theo tên
            for (Cashier cashier : cashiers) {
                if (cashier.getCashierName().toLowerCase().contains(cashierName)) {
                    System.out.println("Thu ngân tìm thấy:");
                    System.out.println("Mã NV: " + cashier.getCashierID());
                    System.out.println("Tên: " + cashier.getCashierName());
                    System.out.println("Vai trò: " + cashier.getRole());
                    System.out.println("Ca làm: " + cashier.getShift());
                    System.out.println("Số ngày công: " + cashier.getNumWorkingDays());
                    found = true;
                }
            }
        }
        // Nếu lựa chọn không hợp lệ
        else {
            System.out.println("Lựa chọn không hợp lệ!");
        }
    
        // Nếu không tìm thấy thu ngân nào
        if (!found) {
            System.out.println("Không tìm thấy thu ngân nào.");
        }
    }

    // Phương thức thống kê nhân viên xuất sắc nhất tháng/năm 
    public void statisticBestCashier() {
        Scanner scanner = new Scanner(System.in);
    
        // Hỏi người dùng chọn thống kê theo tháng hoặc năm
        String choice;
        do {
            System.out.println("Bạn muốn thống kê theo (1) Tháng hay (2) Năm?");
            choice = scanner.nextLine().trim();
        } while (!choice.equals("1") && !choice.equals("2"));
    
        // Xác định thống kê theo tháng hay năm
        boolean isMonth = choice.equals("1");
        int period = -1;
    
        if (isMonth) {
            do {
                System.out.println("Nhập tháng (1-12):");
                try {
                    period = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập số nguyên hợp lệ.");
                }
            } while (period < 1 || period > 12);
        } else {
            do {
                System.out.println("Nhập năm (VD: 2023):");
                try {
                    period = Integer.parseInt(scanner.nextLine().trim());
                } catch (NumberFormatException e) {
                    System.out.println("Vui lòng nhập số nguyên hợp lệ.");
                }
            } while (period <= 0);
        }
    
        // Đọc danh sách từ file
        Cashier[] cashiers = readFromFile("SieuThiMini\\CashierList.txt");
    
        if (cashiers.length == 0) {
            System.out.println("Danh sách nhân viên rỗng hoặc không có dữ liệu hợp lệ.");
            return;
        }
    
        // Tìm số ngày công lớn nhất
        int maxWorkingDays = -1;
        for (Cashier cashier : cashiers) {
            if (cashier.getNumWorkingDays() > maxWorkingDays) {
                maxWorkingDays = cashier.getNumWorkingDays();
            }
        }
    
        // Liệt kê các nhân viên xuất sắc nhất
        System.out.println("Nhân viên xuất sắc nhất trong " + (isMonth ? "tháng" : "năm") + " " + period + ":");
        System.out.printf("%-10s | %-25s | %-15s | %-10s | %-10s%n",
                "Mã NV", "Tên Nhân Viên", "Vai Trò", "Ca Làm", "Ngày Công");
        System.out.println("-------------------------------------------------------------------------------");
        for (Cashier cashier : cashiers) {
            if (cashier.getNumWorkingDays() == maxWorkingDays) {
                System.out.printf("%-10s | %-25s | %-15s | %-10s | %-10d%n",
                        cashier.getCashierID(),
                        cashier.getCashierName(),
                        cashier.getRole(),
                        cashier.getShift(),
                        cashier.getNumWorkingDays());
            }
        }
    }   
}

