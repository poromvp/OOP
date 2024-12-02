
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Manager extends Staff {

    protected int count;

    public Manager(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Manager() {
    }

    public Manager(String staffID, String name, String role, Double salary, String contactNum, int count) {
        super(staffID, name, role, salary, contactNum);
        this.count = count;
    }

    public Manager(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    Scanner sc = new Scanner(System.in);


    @Override
    // đọc và lưu lại các phần tử trong file
    public  Manager[] readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Manager[] managers = new Manager[0]; // Bắt đầu với một mảng rỗng
    
            // Đọc từng dòng trong file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");
                
                // Kiểm tra độ dài mảng parts (số phần tử trong dòng dữ liệu)
                if (parts.length >= 3) { // 5 phần tử cho mã NV, tên, vai trò, lương và số điện thoại
                    String staffID = parts[0].trim();
                    String name = (String.join(" ", Arrays.copyOfRange(parts, 1, parts.length-3)));
                    String role = parts[parts.length-2];
                    double salary = Double.parseDouble(parts[parts.length-3]);
                    String contactNum = parts[parts.length-1];
    
                    // Tạo một đối tượng Manager mới
                    Manager newManager = new Manager(staffID, name, role, salary, contactNum);
                    
                    // Mở rộng mảng managers bằng Arrays.copyOf và thêm Manager mới vào
                    managers = Arrays.copyOf(managers, managers.length + 1);
                    managers[managers.length - 1] = newManager;
                }
            }
    
            return managers;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new Manager[0]; // Trả về mảng rỗng nếu có lỗi
        }
    }
@Override
    public void getdetail() {
        // Gọi phương thức readFromFile để đọc dữ liệu từ file và trả về mảng các đối tượng Manager
        Manager[] managers = readFromFile("SieuThiMini\\dsnv.txt");
    
        // Kiểm tra nếu không có dữ liệu
        if (managers == null || managers.length == 0) {
            System.out.println("Không có dữ liệu nhân viên");
            return;
        }
    
        // In ra tiêu đề bảng
        System.out.printf("╔════════════╤═════════════════════════════════════╤═════════════════╤════════════╤═════════════════╗\n");
        System.out.printf("║ %-10s │ %-35s │ %-15s │ %-10s │ %-15s ║\n", "Mã NV", "Họ Tên", "Lương (VND)", "Vai trò", "Số điện thoại");
        System.out.printf("╠════════════╪═════════════════════════════════════╪═════════════════╪════════════╪═════════════════╣\n");
    
        // Duyệt qua mảng managers và in ra thông tin của từng nhân viên
        for (Manager manager : managers) {
            System.out.printf("║ %-10s │ %-35s │ %-15.2f │ %-10s │ %-15s ║\n",
                manager.getStaffID(),
                manager.getName(),
                manager.getSalary(),
                manager.getRole(),
                manager.getContactNum());
        }
    
        // Đường viền cuối bảng
        System.out.printf("╚════════════╧═════════════════════════════════════╧═════════════════╧════════════╧═════════════════╝\n");
        System.out.println("");
    }

    @Override
    //them nhan vien vao danh sach nhan vien
    public void add() {
    getdetail();


    // Đọc danh sách nhân viên hiện tại từ file
    Manager[] managers = readFromFile("SieuThiMini\\dsnv.txt");

    System.out.print("Bạn muốn thêm bao nhiêu nhân viên: ");
    int n = Integer.parseInt(sc.nextLine());
    while(n<0){
        System.out.print("số lượng không hợp lệ !!!! vui lòng nhập lại: ");
        n=Integer.parseInt(sc.nextLine());
    }

    System.out.println("=================================================================");

    for(int j=0; j<n; j++){

    // Hỏi vị trí dòng cần thêm
    System.out.print("Nhập vị trí dòng bạn muốn thêm vào danh sách:");
    int vitri = Integer.parseInt(sc.nextLine());

    // Nhập thông tin nhân viên mới
    System.out.print("Nhập mã cho nhân viên mới: ");
    String ID = sc.nextLine();

    System.out.print("Nhập họ tên của nhân viên mới: ");
    String Ten = sc.nextLine();

    System.out.print("Nhập số lương của nhân viên mới: ");
    double Luong = Double.parseDouble(sc.nextLine());

    System.out.print("Nhập vai trò bạn muốn cho nhân viên mới: ");
    String role = sc.nextLine();

    System.out.print("Nhập số điện thoại của nhân viên mới: ");
    String PhoneNum = sc.nextLine();

    // Tạo nhân viên mới
    Manager newManager = new Manager(ID, Ten, role, Luong, PhoneNum);

    // Mở rộng mảng managers để chứa nhân viên mới
    if (vitri -1 > managers.length) {
        // Nếu vị trí lớn hơn 3, thêm nhân viên vào cuối danh sách
        managers = Arrays.copyOf(managers, managers.length + 1); // Mở rộng mảng
        managers[managers.length - 1] = newManager; // Thêm nhân viên mới vào cuối
    } else {
            // Nếu vị trí hợp lệ, chèn nhân viên vào vị trí chỉ định
            if (vitri  < managers.length) {
                managers = Arrays.copyOf(managers, managers.length + 1); // Mở rộng mảng
                for (int i = managers.length - 1; i > vitri - 1; i--) {
                    managers[i] = managers[i - 1]; // Di chuyển các phần tử phía sau
                }
            }
            // Thêm nhân viên mới vào mảng tại vị trí vitri - 1
            managers[vitri - 1] = newManager;
            }
        System.out.println("=================================================================");
        }

    // Cập nhật lại số lượng nhân viên
    count = managers.length;

    // Ghi lại dữ liệu vào file SieuThiMini\\dsnv.txt
    writeToFile("SieuThiMini\\dsnv.txt", managers);
    

    // Hiển thị danh sách nhân viên sau khi cập nhật
    System.out.println("Danh sách sau khi cập nhật: ");
    getdetail();
}

// Phương thức ghi lại dữ liệu vào file
public void writeToFile(String fileName, Manager[] managers) {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
        for (Manager manager : managers) {
            // Ghi từng dòng nhân viên vào file
            writer.write(manager.getStaffID() + " " + manager.getName() + " " + manager.getSalary() + " " +
                    manager.getRole() + " " + manager.getContactNum());
            writer.newLine();
        }
    } catch (IOException e) {
        System.out.println("Lỗi khi ghi file: " + e.getMessage());
    }
}

    @Override
    public void remove() {
        getdetail();
            System.out.print("Nhập mã nhân viên bạn muốn xoá: ");
        String IDremove = sc.nextLine();

        // Đọc danh sách nhân viên từ file
        Manager[] managers = readFromFile("SieuThiMini\\dsnv.txt");

        // Kiểm tra nếu không tìm thấy nhân viên

        // Duyệt qua mảng managers và đếm số lượng nhân viên cần giữ lại
        int newSize = 0;
        for (Manager manager : managers) {
            if (!manager.getStaffID().equals(IDremove)) {
                newSize++; // Nếu nhân viên không phải là nhân viên cần xoá, tăng kích thước mảng
            }
        }

        if (newSize == managers.length) {
            System.out.println("Không tìm thấy nhân viên với mã: " + IDremove);
        } else {
            // Tạo một mảng mới với kích thước giảm đi 1
            Manager[] updatedManagers = new Manager[newSize];
            int index = 0;

            // Duyệt qua mảng managers và sao chép các nhân viên không bị xoá vào mảng mới
            for (Manager manager : managers) {
                if (!manager.getStaffID().equals(IDremove)) {
                    updatedManagers[index++] = manager;
                }
            }

            // Ghi lại danh sách mới vào file
            writeToFile("SieuThiMini\\dsnv.txt", updatedManagers);
            System.out.println("Nhân viên với mã " + IDremove + " đã được xoá.");
        }

        // Hiển thị lại danh sách nhân viên sau khi xoá
        getdetail();
        ;
    }
    @Override
    public void ChangeInFo() {
        getdetail();
        
        // Đọc dữ liệu từ file vào mảng managers
        Manager[] managers = readFromFile("SieuThiMini\\dsnv.txt");
    
        System.out.print("Nhập mã nhân viên bạn muốn đổi thông tin: ");
        String id = sc.nextLine();
    
        boolean found = false;
    
        // Duyệt qua mảng managers để tìm nhân viên cần thay đổi thông tin
        for (int i = 0; i < managers.length; i++) {
            if (managers[i].getStaffID().equals(id)) {
                // Nhân viên được tìm thấy, tiến hành sửa thông tin
                found = true;
    
                System.out.println("Nhập thông tin mới cho nhân viên (bỏ qua nếu không muốn thay đổi)");
    
                // Cập nhật họ tên
                System.out.print("Nhập họ tên mới: ");
                String name = sc.nextLine();
                if (!name.isEmpty()) {
                    managers[i].setName(name); // Nếu không để trống, cập nhật tên mới
                }
    
                // Cập nhật lương
                System.out.print("Nhập lương mới: ");
                String salaryInput = sc.nextLine();
                if (!salaryInput.isEmpty()) {
                    double salary = Double.parseDouble(salaryInput);
                    managers[i].setSalary(salary); // Nếu không để trống, cập nhật lương mới
                }
    
                // Cập nhật vai trò
                System.out.print("Nhập vai trò mới: ");
                String role = sc.nextLine();
                if (!role.isEmpty()) {
                    managers[i].setRole(role); // Nếu không để trống, cập nhật vai trò mới
                }
    
                // Cập nhật số điện thoại
                System.out.print("Nhập số điện thoại mới: ");
                String contactNum = sc.nextLine();
                if (!contactNum.isEmpty()) {
                    managers[i].setContactNum(contactNum); // Nếu không để trống, cập nhật số điện thoại mới
                }
    
                System.out.println("Thông tin nhân viên đã được cập nhật.");
                break; // Thoát khỏi vòng lặp khi tìm thấy nhân viên
            }
        }
    
        if (!found) {
            System.out.println("Không tìm thấy nhân viên với mã: " + id);
        }
    
        // Cập nhật lại dữ liệu vào file
        writeToFile("SieuThiMini\\dsnv.txt", managers);
    
        // Hiển thị lại danh sách nhân viên sau khi cập nhật
        getdetail();
        ;
    }
    @Override
        // Phương thức tìm kiếm nhân viên theo các tiêu chí
        public void search() {
                    Manager[] managers = readFromFile("SieuThiMini\\dsnv.txt");
    
            // Yêu cầu nhập các tiêu chí tìm kiếm
            System.out.println("Nhập tiêu chí tìm kiếm (Có thể bỏ qua một số tiêu chí bằng cách nhấn Enter)");
    
            // Tiêu chí tìm kiếm theo mã nhân viên
            System.out.print("Nhập mã nhân viên (hoặc nhấn Enter để bỏ qua): ");
            String staffID = sc.nextLine().trim();
    
            // Tiêu chí tìm kiếm theo tên
            System.out.print("Nhập tên nhân viên (hoặc nhấn Enter để bỏ qua): ");
            String name = sc.nextLine().trim();
    
            // Tiêu chí tìm kiếm theo lương
            System.out.print("Nhập lương nhân viên (hoặc nhấn Enter để bỏ qua): ");
            String salaryInput = sc.nextLine().trim();
            Double salary = salaryInput.isEmpty() ? null : Double.parseDouble(salaryInput);
    
            // Tiêu chí tìm kiếm theo vai trò
            System.out.print("Nhập vai trò nhân viên (hoặc nhấn Enter để bỏ qua): ");
            String role = sc.nextLine().trim();
    
            // Kiểm tra nếu không có tiêu chí nào được nhập
            boolean found = false;
            
            System.out.printf("╔════════════╤═════════════════════════════════════╤═════════════════╤════════════╤═════════════════╗\n");
            System.out.printf("║ %-10s │ %-35s │ %-15s │ %-10s │ %-15s ║\n", "Mã NV", "Họ Tên", "Lương (VND)", "Vai trò", "Số điện thoại");
            System.out.printf("╠════════════╪═════════════════════════════════════╪═════════════════╪════════════╪═════════════════╣\n");
            
            // Duyệt qua mảng managers và tìm nhân viên thỏa mãn ít nhất một tiêu chí
            for (Manager manager : managers) {
                boolean match = false;
    
                // Kiểm tra mã nhân viên nếu có
                if (!staffID.isEmpty() && manager.getStaffID().equalsIgnoreCase(staffID)) {
                    match = true;
                }
    
                // Kiểm tra tên nếu có
                if (!name.isEmpty() && manager.getName().toLowerCase().contains(name.toLowerCase())) {
                    match = true;
                }
    
                // Kiểm tra lương nếu có
                if (salary != null && manager.getSalary() == salary) {
                    match = true;
                }
    
                // Kiểm tra vai trò nếu có
                if (!role.isEmpty() && manager.getRole().toLowerCase().contains(role.toLowerCase())) {
                    match = true;
                }
    
                // Nếu có ít nhất một tiêu chí trùng khớp
                if (match) {
                    found = true;
                    // In ra thông tin nhân viên
                    System.out.printf("║ %-10s │ %-35s │ %-15.2f │ %-10s │ %-15s ║\n",
                        manager.getStaffID(),
                        manager.getName(),
                        manager.getSalary(),
                        manager.getRole(),
                        manager.getContactNum());
                }
            }
    
            // Nếu không tìm thấy nhân viên nào
            if (!found) {
                System.out.println("Không tìm thấy nhân viên thỏa mãn điều kiện tìm kiếm.");
            }
    
            // Đường viền cuối bảng
            System.out.printf("╚════════════╧═════════════════════════════════════╧═════════════════╧════════════╧═════════════════╝\n");
        }
}