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
    Scanner sc = new Scanner(System.in);

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
        Cashier[] cashiers = readFromFile("SieuThiMini\\CashierList.txt");
        getdetail();

        // Khởi tạo scanner để nhập dữ liệu

        System.out.print("Nhập số lượng thu ngân bạn muốn thêm vào ca làm: ");
        int n =Integer.parseInt(sc.nextLine());
        while (n<0){
            System.out.print("Số lượng không hợp lệ !!!! vui lòng nhập lại: ");
            n = Integer.parseInt(sc.nextLine());
        }

        System.out.println("=================================================================");
        for(int j=0; j<n; j++){
            // Nhập thông tin thu ngân mới
            System.out.print("Nhập vị trí bạn muốn thêm vào danh sách: ");
            int vitri = Integer.parseInt(sc.nextLine());

            System.out.print("Nhập mã thu ngân mới: ");
            String cashierID = sc.nextLine();

            System.out.print("Nhập tên thu ngân mới: ");
            String cashierName = sc.nextLine();

            System.out.print("Nhập vai trò thu ngân mới: ");
            String role = sc.nextLine();

            System.out.print("Nhập ca làm việc của thu ngân mới (A(ca sáng) hoặc B(ca chiều)): ");
            String shift = sc.nextLine();
            while(!shift.equalsIgnoreCase("A") && !shift.equalsIgnoreCase("B")){
                System.out.print("Ca làm không hợp lệ !!! vui lòng nhập lại:  ");
                shift = sc.nextLine();
            }

            System.out.print("Nhập số ngày công của thu ngân mới: ");
            int numWorkingDays = Integer.parseInt(sc.nextLine());

            // Tạo một đối tượng Cashier mới
            Cashier newCashier = new Cashier(cashierID, cashierName, role, shift.toUpperCase(), numWorkingDays);

                // Mở rộng mảng managers để chứa nhân viên mới
            if (vitri  > cashiers.length) {
                // Nếu vị trí lớn hơn 3, thêm nhân viên vào cuối danh sách
                cashiers = Arrays.copyOf(cashiers, cashiers.length + 1); // Mở rộng mảng
                cashiers[cashiers.length - 1] = newCashier; // Thêm nhân viên mới vào cuối
            } else {
                    // Nếu vị trí hợp lệ, chèn nhân viên vào vị trí chỉ định
                    if (vitri - 1 < cashiers.length) {
                        cashiers = Arrays.copyOf(cashiers, cashiers.length + 1); // Mở rộng mảng
                        for (int i = cashiers.length - 1; i > vitri - 1; i--) {
                            cashiers[i] = cashiers[i - 1]; // Di chuyển các phần tử phía sau
                        }
                    }
                    // Thêm nhân viên mới vào mảng tại vị trí vitri - 1
                    cashiers[vitri - 1] = newCashier;
                    }
                System.out.println("=================================================================");
        }
        writeToFile("SieuThiMini\\CashierList.txt", cashiers);

        // In danh sách thu ngân sau khi cập nhật
        System.out.println("Danh sách thu ngân sau khi cập nhật: ");
        getdetail();
    }

    @Override
    // Phương thức xóa một Cashier khỏi danh sách
    public void remove() {
        // Hiển thị danh sách thu ngân
        Cashier[] cashiers = readFromFile("SieuThiMini\\CashierList.txt");
        getdetail();

        // Khởi tạo scanner để nhập mã thu ngân cần xóa
        System.out.print("Nhập mã thu ngân bạn muốn xóa: ");
        String cashierID = sc.nextLine();

        // Duyệt qua mảng cashiers để tìm và xóa thu ngân
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
            writeToFile("SieuThiMini\\CashierList.txt", updatedCashiers);
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
        Cashier[] cashiers = readFromFile("SieuThiMini\\CashierList.txt");
        getdetail();

        // Khởi tạo scanner để nhập thông tin thu ngân cần sửa
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
            writeToFile("SieuThiMini\\CashierList.txt", cashiers);
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
        Cashier[] cashiers = readFromFile("SieuThiMini\\CashierList.txt");
    
        // Khởi tạo scanner để nhập từ bàn phím
    
        // Yêu cầu nhập các tiêu chí tìm kiếm
        System.out.println("Nhập tiêu chí tìm kiếm (Có thể bỏ qua một số tiêu chí bằng cách nhấn Enter)");
    
        // Tiêu chí tìm kiếm theo mã thu ngân
        System.out.print("Nhập mã thu ngân (hoặc nhấn Enter để bỏ qua): ");
        String cashierID = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo tên
        System.out.print("Nhập tên thu ngân (hoặc nhấn Enter để bỏ qua): ");
        String cashierName = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo vai trò
        System.out.print("Nhập vai trò (hoặc nhấn Enter để bỏ qua): ");
        String role = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo ca làm
        System.out.print("Nhập ca làm (hoặc nhấn Enter để bỏ qua): ");
        String shift = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo số ngày công
        System.out.print("Nhập số ngày công (hoặc nhấn Enter để bỏ qua): ");
        String workingDaysInput = sc.nextLine().trim();
        Integer numWorkingDays = workingDaysInput.isEmpty() ? null : Integer.parseInt(workingDaysInput);
    
        // Kiểm tra nếu không có tiêu chí nào được nhập
        boolean found = false;
    
        // Định dạng tiêu đề bảng
        System.out.printf("╔════════════╤══════════════════════════════╤══════════════╤════════════╤══════════════════╗\n");
        System.out.printf("║ %-10s │ %-30s │ %-12s │ %-10s │ %-15s ║\n", "Mã NV", "Tên thu ngân", "Vai trò", "Ca làm", "Số ngày công");
        System.out.printf("╠════════════╪══════════════════════════════╪══════════════╪════════════╪══════════════════╣\n");
    
        // Duyệt qua danh sách các thu ngân và tìm kiếm
        for (Cashier cashier : cashiers) {
            boolean match = true;
    
            // Kiểm tra từng tiêu chí (bỏ qua nếu không được nhập)
            if (!cashierID.isEmpty() && !cashier.getCashierID().equalsIgnoreCase(cashierID)) {
                match = false;
            }
            if (!cashierName.isEmpty() && !cashier.getCashierName().toLowerCase().contains(cashierName.toLowerCase())) {
                match = false;
            }
            if (!role.isEmpty() && !cashier.getRole().toLowerCase().contains(role.toLowerCase())) {
                match = false;
            }
            if (!shift.isEmpty() && !cashier.getShift().equalsIgnoreCase(shift)) {
                match = false;
            }
            if (numWorkingDays != null && cashier.getNumWorkingDays() != numWorkingDays) {
                match = false;
            }
    
            // Nếu tất cả tiêu chí khớp, in ra thông tin thu ngân
            if (match) {
                found = true;
                System.out.printf("║ %-10s │ %-30s │ %-12s │ %-10s │ %-15d ║\n",
                    cashier.getCashierID(),
                    cashier.getCashierName(),
                    cashier.getRole(),
                    cashier.getShift(),
                    cashier.getNumWorkingDays());
            }
        }
    
        // Nếu không tìm thấy thu ngân nào
        if (!found) {
            System.out.println("Không tìm thấy thu ngân thỏa mãn điều kiện tìm kiếm.");
        }
    
        // Đường viền cuối bảng
        System.out.printf("╚════════════╧══════════════════════════════╧══════════════╧════════════╧══════════════════╝\n");
    }

    // Phương thức thống kê nhân viên xuất sắc nhất tháng/năm 
    public void statisticBestCashier() {
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
    
        // Liệt kê nhân viên xuất sắc nhất
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

