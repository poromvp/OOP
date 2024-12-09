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
        Cashier[] cashiers = readFromFile("CashierList.txt");
        if (cashiers == null || cashiers.length == 0) {
            System.out.println("Không có dữ liệu nhân viên");
            return;
        }
    
        System.out.println("Danh sách ca làm của nhân viên");
        System.out.println("╔════════════╤════════════════════════════════╤═════════════════╤═════════════╤════════════════════════════════╗");
        System.out.printf("║ %-10s │ %-30s │ %-15s │ %-11s │ %-30s ║\n", "Mã NV", "Họ Tên", "Vai trò", "Ca Làm", "Số ngày công (tháng 11)");
        System.out.println("╠════════════╪════════════════════════════════╪═════════════════╪═════════════╪════════════════════════════════╣");
    
        for (Cashier cashier : cashiers) {

            String calam;
            if(cashier.getShift().equals("A")){
                calam = cashier.getShift() + " (7h-15h)";
            }else {
                calam = cashier.getShift() + " (14h-22h)";
            }

            System.out.printf("║ %-10s │ %-30s │ %-15s │ %-11s │ %-30d ║\n",
                    cashier.getCashierID(),
                    cashier.getCashierName(),
                    cashier.getRole(),
                    calam,
                    cashier.getNumWorkingDays());
        }
    
        System.out.println("╚════════════╧════════════════════════════════╧═════════════════╧═════════════╧════════════════════════════════╝");
    }
    
    // Phương thức thêm một Cashier mới vào danh sách
    public void add() {
        int count = 0;
        Manager temp = new Manager();
        Manager[] managers = temp.readFromFile("dsnv.txt");
    
        // Đọc danh sách thu ngân hiện tại từ file
        Cashier[] cashiers = readFromFile("CashierList.txt");
    
        // Mảng tạm để lưu các mã nhân viên chưa có ca làm
        String[] tempManagerIDs = new String[0];
        String[] tempManagerNames = new String[0];
    
        // Kiểm tra các nhân viên chưa có ca làm
        for (Manager manager : managers) {
            boolean hasShift = false;
            for (Cashier cashier : cashiers) {
                if (manager.getStaffID().equals(cashier.getCashierID())) {
                    hasShift = true;
                    break;
                }
            }
            if (!hasShift) {
                System.out.println("===========================================================");
                System.out.println("Nhân viên chưa có ca làm với mã và tên: " + manager.getStaffID() + " || " + manager.getName());
                count++;
                tempManagerIDs = Arrays.copyOf(tempManagerIDs, tempManagerIDs.length + 1);
                tempManagerIDs[tempManagerIDs.length - 1] = manager.getStaffID();
                tempManagerNames = Arrays.copyOf(tempManagerNames, tempManagerNames.length + 1);
                tempManagerNames[tempManagerNames.length - 1] = manager.getName();
            }
        }
        System.out.println("===========================================================");
    
        if (count == 0) {
            System.out.println("Tất cả nhân viên đã có ca làm !!! Bạn hãy thêm một nhân viên mới !!!");
            System.out.println();
        } else {
            System.out.println("Số lượng nhân viên chưa có ca làm: " + count);
            System.out.println();
            for (int j = 0; j < count; j++) {
                // Nhập ca làm cho nhân viên
                System.out.print("Mã nhân viên mới: " + tempManagerIDs[j]);
                String managerID = tempManagerIDs[j];
                System.out.println();
    
                System.out.print("Tên nhân viên sở hữu ca làm: " + tempManagerNames[j]);
                String managerName = tempManagerNames[j];
                System.out.println();
    
                String role;
                if(managerID.startsWith("QL")){
                    role = "Manager";
                }else if(managerID.startsWith("NV")){
                    role = "Saler";
                }else {
                    role = "Warehouseman";
                }
                System.out.println("Vai trò của nhân viên: "+role);
    
                // Nhập ca làm cho nhân viên
                System.out.print("Nhập ca làm cho nhân viên (A: ca sáng hoặc B: ca chiều): ");
                String shift = sc.nextLine();
                while (!shift.equalsIgnoreCase("A") && !shift.equalsIgnoreCase("B")) {
                    System.out.print("Ca làm không hợp lệ, vui lòng nhập lại (A hoặc B): ");
                    shift = sc.nextLine();
                }
    
                // Nhập số ngày công cho nhân viên
                System.out.print("Nhập số ngày công trong tháng của nhân viên mới: ");
                int numWorkingDays = Integer.parseInt(sc.nextLine());
                while(numWorkingDays > 31 ){
                    System.out.print("Số ngày công trong tháng phải nhỏ hơn 31, mời nhập lại: ");
                    numWorkingDays = Integer.parseInt(sc.nextLine());
                }
    
                // Tạo một đối tượng Cashier mới
                Cashier newCashier = new Cashier(managerID, managerName, role, shift.toUpperCase(), numWorkingDays);
    
                // Thêm nhân viên vào danh sách thu ngân
                cashiers = Arrays.copyOf(cashiers, cashiers.length + 1);
                cashiers[cashiers.length - 1] = newCashier;
    
                // Ghi lại dữ liệu vào file
                writeToFile("CashierList.txt", cashiers);
    
                // Hiển thị danh sách thu ngân đã được cập nhật
                System.out.println("Danh sách ca làm đã được cập nhật!");
                System.out.println("===========================================================");
            }
        }
    }
    
    

    @Override
    // Phương thức xóa một Cashier khỏi danh sách
    public void remove() {
        int count = 0;
        Manager temp = new Manager();
        Manager[] managers = temp.readFromFile("dsnv.txt");
        Cashier[] cashiers = readFromFile("CashierList.txt");
        
        for(Cashier cashier : cashiers){
            boolean hasCashier = false;
            for(Manager manager : managers){
                if(manager.getStaffID().equals(cashier.getCashierID())){
                    hasCashier = true;
                    break;
                }
            }
            if(!hasCashier){
                count ++;
                String tmp = cashier.getCashierID();
                int newSize = 0;
                for(Cashier a : cashiers){
                    if(!a.getCashierID().equals(tmp)){
                        newSize++;  // nếu không phải thu ngân bị xoá, tăng kích thước mảng mới
                    }
                }

                if(newSize == cashiers.length){
                    System.out.println("Không tìm thấy dữ liệu !!!!");
                }else {
                    Cashier[] updateCashier = new Cashier[newSize];
                    int index = 0;

                    for(Cashier b : cashiers ){
                        if(!b.getCashierID().equals(tmp)){
                            updateCashier[index++] = b;
                        }
                    }

                    writeToFile("CashierList.txt", updateCashier);
                    System.out.println("Ca làm của nhân viên đã được xoá");
                }

            }
        }

        if (count == 0) {
            System.out.print("Bạn có muốn thay đổi ca làm không? (Y/N): ");
            String changeShiftResponse = sc.nextLine();
            
            while(!changeShiftResponse.equalsIgnoreCase("Y") && !changeShiftResponse.equalsIgnoreCase("N")){
                System.out.print("Lựa chọn của bạn không hợp lệ, mời nhập lại: ");
                changeShiftResponse = sc.nextLine();
            }

            if (changeShiftResponse.equalsIgnoreCase("Y")) {
                System.out.print("Nhập mã nhân viên bạn muốn xóa lịch làm: ");
                String cashierID = sc.nextLine();
        
                // Duyệt qua mảng cashiers để tìm và xóa thu ngân
                int newSize = 0;
                for (Cashier cashier : cashiers) {
                    if (!cashier.getCashierID().equals(cashierID)) {
                        newSize++;
                    }
                }
        
                if (newSize == cashiers.length) {
                    System.out.println("Không tìm thấy nhân viên với mã: " + cashierID);
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
                    System.out.println("Nhân viên với mã " + cashierID + " đã được xóa lịch làm.");
                }
            } else {
                System.out.println("Bạn đã chọn không thay đổi ca làm.");
            }
        }        
    }
        

    @Override
    // Phương thức thay đổi thông tin ca làm
    public void ChangeInFo() {
        int count = 0 ;
        Manager temp = new Manager();
        Manager[] managers = temp.readFromFile("dsnv.txt");

        Cashier[] cashiers = readFromFile("CashierList.txt");
        for(Cashier cashier : cashiers){
            for(Manager manager : managers){
                if(manager.getStaffID().equals(cashier.getCashierID())){
                    if(!manager.getName().equals(cashier.getCashierName())){
                        count ++;
                        cashier.setCashierName(manager.getName());
                        System.out.println("Tên nhân viên đã được cập nhật trong danh sách ca làm");

                        System.out.print("Bạn có muốn thay đổi ca làm của nhân viên này không (nhập y (có) / n (không)): ");
                        String choice = sc.nextLine();
                        while(choice.equalsIgnoreCase("Y") && choice.equalsIgnoreCase("N")){
                            System.out.print("Lựa chọn của bạn không hợp lệ !!! mời nhập lại (nhập y (có) / n (không)): ");
                            choice = sc.nextLine();
                        }

                        if(choice.equalsIgnoreCase("Y")){
                            System.out.println("Nhập thông tin mới cho ca làm (bỏ trống nếu không muốn thay đổi)");

                            System.out.print("Nhập ca làm việc mới cho nhân viên (nhập A(ca sáng) hoặc B(ca chiều)): ");
                            String shift = sc.nextLine();
                            if(!shift.isEmpty()){
                                while(!shift.equalsIgnoreCase("A") && !shift.equalsIgnoreCase("B")){
                                    System.out.print("Ca làm không hợp lệ !!! vui lòng nhập lại:  ");
                                    shift = sc.nextLine();
                                }
                                cashier.setShift(shift);
                            }

                            System.out.print("Nhập số ngày công trong tháng mới của nhân viên: ");
                            String newNum = sc.nextLine();
                                if(!newNum.isEmpty() && newNum.matches("\\d+")){
                                while(Integer.parseInt(newNum) > 31 ){
                                    System.out.print("Số ngày công trong tháng phải nhỏ hơn 31, mời nhập lại: ");
                                    newNum = sc.nextLine();
                                }
                                cashier.setNumWorkingDays(Integer.parseInt(newNum));
                            }
                            System.out.println("thông tin ca làm đã được cập nhật");
                            break;
                        }else {
                            System.out.println("Không thay đổi ca làm của nhân viên");
                        }

                    }
                }
            }
        }
        
        if(count == 0){
            System.out.print("Nhập mã nhân viên bạn muốn thay đổi ca làm: ");
            String cashierID = sc.nextLine();

            boolean found = false;
            for (Cashier cashier : cashiers) {
                if (cashier.getCashierID().equals(cashierID)) {
                    found = true;
                    System.out.println("Nhân viên tìm thấy: " + cashier.getCashierName());

                    // Nhập thông tin mới
                    System.out.print("Nhập tên Nhân viên thay thế: ");
                    cashier.setCashierName(sc.nextLine());

                    System.out.print("Nhập ca làm việc mới cho nhân viên (nhập A(ca sáng) hoặc B(ca chiều)): ");
                    String shift = sc.nextLine();
                    while(!shift.equalsIgnoreCase("A") && !shift.equalsIgnoreCase("B")){
                        System.out.print("Ca làm không hợp lệ !!! vui lòng nhập lại:  ");
                        shift = sc.nextLine();
                    }

                    System.out.print("Nhập số ngày công trong tháng mới của nhân viên: ");
                    int newNum = Integer.parseInt(sc.nextLine());
                    while(newNum > 31 ){
                        System.out.print("Số ngày công trong tháng phải nhỏ hơn 31, mời nhập lại: ");
                        newNum = Integer.parseInt(sc.nextLine());
                    }
                    System.out.println("thông tin ca làm đã được cập nhật");

                    break;
                }
            }

            if (found) {
                // Ghi lại dữ liệu vào file
                writeToFile("CashierList.txt", cashiers);
                System.out.println("Thông tin ca làm đã được cập nhật.");
            } else {
                System.out.println("Không tìm thấy thu ngân với mã: " + cashierID);
            }   
        }
        writeToFile("CashierList.txt", cashiers);
    }

    @Override
    public void search() {
        // Đọc dữ liệu từ file
        Cashier[] cashiers = readFromFile("CashierList.txt");
    
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

                String calam;
                if(cashier.getShift().equals("A")){
                    calam = cashier.getShift() + " (7h-15h)";
                }else {
                    calam = cashier.getShift() + " (14h-22h)";
                }

                found = true;
                System.out.println("╔════════════╤════════════════════════════════╤═════════════════╤═════════════╤════════════════════════════════╗");
                System.out.printf("║ %-10s │ %-30s │ %-15s │ %-11s │ %-30s ║\n", "Mã NV", "Họ Tên", "Vai trò", "Ca Làm", " Số ngày công (trong tháng)");
                System.out.println("╠════════════╪════════════════════════════════╪═════════════════╪═════════════╪════════════════════════════════╣");
                System.out.printf("║ %-10s │ %-30s │ %-15s │ %-11s │ %-30d ║\n",
                    cashier.getCashierID(),
                    cashier.getCashierName(),
                    cashier.getRole(),
                    calam,
                    cashier.getNumWorkingDays());
                System.out.println("╚════════════╧════════════════════════════════╧═════════════════╧═════════════╧════════════════════════════════╝");

            }
        }
    
        // Nếu không tìm thấy thu ngân nào
        if (!found) {
            System.out.println("╔════════════╤════════════════════════════════╤═════════════════╤════════════╤════════════════════════════════╗");
            System.out.printf("║ %-10s │ %-30s │ %-15s │ %-10s │ %-30s ║\n", "Mã NV", "Họ Tên", "Vai trò", "Ca Làm", " Số ngày công (trong tháng)");
            System.out.println("╠════════════╧════════════════════════════════╧═════════════════╧════════════╧════════════════════════════════╣");
            System.out.printf("║ %-108s║\n","Không tìm thấy nhân viên");
            System.out.println("╚═════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");

        }
    }

    // Phương thức thống kê nhân viên xuất sắc nhất tháng/năm 
    public void statisticBestCashier() {
        // Đọc danh sách từ file
        Cashier[] cashiers = readFromFile("CashierList.txt");
    
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

