import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class Department extends Staff {
    protected String departmentID;
    protected String departmentName;
    protected String ManagerName;

    // Constructor mặc định
    public Department(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    // Constructor mặc định không có tham số
    public Department() {
    }

    // Constructor với tất cả thông tin
    public Department(String staffID, String name, String role, Double salary, String contactNum, String departmentID,
                      String departmentName, String ManagerName) {
        super(staffID, name, role, salary, contactNum);
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.ManagerName = ManagerName;
    }

    // Constructor phòng ban
    public Department(String departmentID, String departmentName, String ManagerName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        this.ManagerName = ManagerName;
    }

    // Getter và Setter
    public String getDepartmentID() {
        return departmentID;
    }

    public void setDepartmentID(String departmentID) {
        this.departmentID = departmentID;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getManagerName() {
        return ManagerName;
    }

    public void setManagerName(String managerName) {
        this.ManagerName = managerName;
    }

    // Phương thức đọc thông tin phòng ban từ file
    @Override
    public Department[] readFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            Department[] departments = new Department[0]; // Bắt đầu với một mảng rỗng

            // Đọc từng dòng trong file
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("\\s+");

                // Kiểm tra độ dài mảng parts (số phần tử trong dòng dữ liệu)
                if (parts.length >= 3) { // 3 phần tử cho mã phòng ban, tên phòng ban, và tên người quản lý
                    String departID = parts[0];
                    String departName = parts[1];
                    String managerName = String.join(" ", Arrays.copyOfRange(parts, 2, parts.length));

                    // Tạo một đối tượng Department mới với các tham số đầy đủ
                    Department newDepartment = new Department(departID, departName, managerName);

                    // Mở rộng mảng departments và thêm Department mới vào
                    departments = Arrays.copyOf(departments, departments.length + 1);
                    departments[departments.length - 1] = newDepartment;

                }
            }

            return departments;
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
            return new Department[0]; // Trả về mảng rỗng nếu có lỗi
        }
    }

    public void getdetail (){
        Department[] temp =readFromFile("SieuThiMini\\DepartmentList.txt");
        if (temp == null || temp.length == 0) {
            System.out.println("Không có dữ liệu nhân viên");
            return;
        }

        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");
        System.out.printf("║ %-10s │ %-15s │ %-20s ║\n", "Mã phòng ban", "Tên phòng ban", "Tên người quản lý");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");

        // Dữ liệu trong bảng
        for (Department department : temp) {
            System.out.printf("║ %-10s │ %-15s │ %-20s ║\n",
                department.getDepartmentID(),
                department.getDepartmentName(),
                department.getManagerName());
        }
        System.out.printf("╚════════════╧═══════════════╧════════════════════════════╝\n");
        System.out.println("");
    }

    // Phương thức ghi lại dữ liệu vào file
    public void writeToFile(String fileName, Department[] departments) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Duyệt qua mảng các đối tượng Department và ghi thông tin vào file
            for (Department department : departments) {
                // Ghi thông tin phòng ban vào file (departmentID, departmentName, managerName)
                writer.write(department.getDepartmentID() + " " + department.getDepartmentName() + " " + department.getManagerName());
                writer.newLine(); // Thêm dòng mới sau mỗi phòng ban
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi ghi file: " + e.getMessage());
        }
    }

    @Override
    public void add() {
        // Hiển thị danh sách phòng ban hiện tại
        getdetail();
    
        // Khởi tạo scanner để nhập dữ liệu
        Scanner sc = new Scanner(System.in);
    
        // Đọc danh sách phòng ban hiện tại từ file
        Department[] departments = readFromFile("SieuThiMini\\DepartmentList.txt");

        System.out.print("Số lượng phong ban bạn muốn thêm vào: ");
        int n =Integer.parseInt(sc.nextLine());
        while (n<=0){
            System.out.print("Số lượng thêm không hợp lệ !!! vui lòng nhập lại: ");
            n= Integer.parseInt(sc.nextLine());
        }
        System.out.println("=================================================================");

        for(int j=0; j<n; j++){
    
            // Hỏi vị trí dòng cần thêm
            System.out.print("Nhập vị trí dòng bạn muốn thêm vào danh sách:");
            int vitri = Integer.parseInt(sc.nextLine());
        
            // Nhập thông tin phòng ban mới
            System.out.print("Nhập mã cho phòng ban mới: ");
            String departmentID = sc.nextLine();
        
            System.out.print("Nhập tên phòng ban mới: ");
            String departmentName = sc.nextLine();
        
            System.out.print("Nhập tên người quản lý của phòng ban mới: ");
            String managerName = sc.nextLine();
        
            // Tạo phòng ban mới
            Department newDepartment = new Department(departmentID, departmentName, managerName);
        
            // Kiểm tra nếu vitri lớn hơn hoặc bằng kích thước mảng, thêm vào cuối mảng
            if (vitri-1 > departments.length) {
                departments = Arrays.copyOf(departments, departments.length + 1);
                departments[departments.length - 1] = newDepartment;
            } else {
                // Mở rộng mảng departments để chứa phòng ban mới
                departments = Arrays.copyOf(departments, departments.length + 1); // Mở rộng mảng
                for (int i = departments.length - 1; i > vitri - 1; i--) {
                    departments[i] = departments[i - 1]; // Di chuyển các phần tử phía sau
                }
                // Thêm phòng ban mới vào mảng tại vị trí vitri - 1
                departments[vitri - 1] = newDepartment;
            }
            System.out.println("=================================================================");
        }
    
        // Ghi lại dữ liệu vào file
        writeToFile("SieuThiMini\\DepartmentList.txt", departments);
    
        // Hiển thị danh sách phòng ban sau khi cập nhật
        System.out.println("Danh sách phòng ban sau khi cập nhật: ");
        getdetail();
    
    }


    @Override
    public void remove() {
        // Hiển thị danh sách phòng ban
        getdetail();
    
        // Nhập mã phòng ban muốn xóa
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhập mã phòng ban bạn muốn xoá: ");
        String IDremove = sc.nextLine();
    
        // Đọc danh sách phòng ban từ file
        Department[] departments = readFromFile("SieuThiMini\\DepartmentList.txt");
    
        // Kiểm tra nếu không tìm thấy phòng ban
        boolean found = false;
    
        // Duyệt qua mảng departments và đếm số lượng phòng ban cần giữ lại
        int newSize = 0;
        for (Department department : departments) {
            if (!department.getDepartmentID().equals(IDremove)) {
                newSize++; // Nếu phòng ban không phải là phòng ban cần xóa, tăng kích thước mảng
            }
        }
    
        if (newSize == departments.length) {
            System.out.println("Không tìm thấy phòng ban với mã: " + IDremove);
        } else {
            // Tạo một mảng mới với kích thước giảm đi 1
            Department[] updatedDepartments = new Department[newSize];
            int index = 0;
    
            // Duyệt qua mảng departments và sao chép các phòng ban không bị xóa vào mảng mới
            for (Department department : departments) {
                if (!department.getDepartmentID().equals(IDremove)) {
                    updatedDepartments[index++] = department;
                }
            }
    
            // Ghi lại danh sách mới vào file
            writeToFile("SieuThiMini\\DepartmentList.txt", updatedDepartments);
            System.out.println("Phòng ban với mã " + IDremove + " đã được xoá.");
        }
    
        // Hiển thị lại danh sách phòng ban sau khi xoá
        getdetail();
        ;
    }

    @Override
    public void ChangeInFo() {
        // Hiển thị danh sách phòng ban hiện tại
        getdetail();
    
        // Khởi tạo scanner để nhập dữ liệu
        Scanner sc = new Scanner(System.in);
    
        // Đọc danh sách phòng ban hiện tại từ file
        Department[] departments = readFromFile("SieuThiMini\\DepartmentList.txt");
    
        System.out.print("Nhập mã phòng ban bạn muốn thay đổi thông tin: ");
        String departmentID = sc.nextLine();
    
        boolean found = false;
    
        // Duyệt qua mảng departments để tìm phòng ban cần thay đổi thông tin
        for (int i = 0; i < departments.length; i++) {
            if (departments[i].getDepartmentID().equals(departmentID)) {
                // Phòng ban được tìm thấy, tiến hành sửa thông tin
                found = true;
    
                System.out.println("Nhập thông tin mới cho phòng ban (bỏ qua nếu không muốn thay đổi)");
    
                // Cập nhật tên phòng ban
                System.out.print("Nhập tên phòng ban mới: ");
                String departmentName = sc.nextLine();
                if (!departmentName.isEmpty()) {
                    departments[i].setDepartmentName(departmentName); // Nếu không để trống, cập nhật tên phòng ban mới
                }
    
                // Cập nhật tên người quản lý
                System.out.print("Nhập tên người quản lý mới: ");
                String managerName = sc.nextLine();
                if (!managerName.isEmpty()) {
                    departments[i].setManagerName(managerName); // Nếu không để trống, cập nhật tên người quản lý mới
                }
    
                System.out.println("Thông tin phòng ban đã được cập nhật.");
                break; // Thoát khỏi vòng lặp khi tìm thấy phòng ban
            }
        }
    
        if (!found) {
            System.out.println("Không tìm thấy phòng ban với mã: " + departmentID);
        }
    
        // Cập nhật lại dữ liệu vào file
        writeToFile("SieuThiMini\\DepartmentList.txt", departments);
    
        // Hiển thị lại danh sách phòng ban sau khi cập nhật
        getdetail();
        ;
    }

    @Override
    public void search() {
        Scanner sc = new Scanner(System.in);
        Department[] departments = readFromFile("SieuThiMini\\DepartmentList.txt");
    
        // Yêu cầu nhập các tiêu chí tìm kiếm
        System.out.println("Nhập tiêu chí tìm kiếm (Có thể bỏ qua một số tiêu chí bằng cách nhấn Enter)");
    
        // Tiêu chí tìm kiếm theo mã phòng ban
        System.out.print("Nhập mã phòng ban (hoặc nhấn Enter để bỏ qua): ");
        String departmentID = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo tên phòng ban
        System.out.print("Nhập tên phòng ban (hoặc nhấn Enter để bỏ qua): ");
        String departmentName = sc.nextLine().trim();
    
        // Tiêu chí tìm kiếm theo tên người quản lý
        System.out.print("Nhập tên người quản lý (hoặc nhấn Enter để bỏ qua): ");
        String managerName = sc.nextLine().trim();
    
        // Kiểm tra nếu không có tiêu chí nào được nhập
        boolean found = false;
    
        // In ra tiêu đề bảng
        System.out.printf("╔════════════╤══════════════╤══════════════════════════════╤══════════════════╗\n");
        System.out.printf("║ %-10s │ %-15s │ %-20s ║\n", "Mã phòng ban", "Tên phòng ban", "Tên người quản lý");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╪══════════════════╣\n");
    
        // Duyệt qua mảng departments và tìm phòng ban thỏa mãn ít nhất một tiêu chí
        for (Department department : departments) {
            boolean match = false;
    
            // Kiểm tra mã phòng ban nếu có
            if (!departmentID.isEmpty() && department.getDepartmentID().equalsIgnoreCase(departmentID)) {
                match = true;
            }
    
            // Kiểm tra tên phòng ban nếu có
            if (!departmentName.isEmpty() && department.getDepartmentName().toLowerCase().contains(departmentName.toLowerCase())) {
                match = true;
            }
    
            // Kiểm tra tên người quản lý nếu có
            if (!managerName.isEmpty() && department.getManagerName().toLowerCase().contains(managerName.toLowerCase())) {
                match = true;
            }
    
            // Nếu có ít nhất một tiêu chí trùng khớp
            if (match) {
                found = true;
                // In ra thông tin phòng ban
                System.out.printf("║ %-10s │ %-15s │ %-20s ║\n",
                    department.getDepartmentID(),
                    department.getDepartmentName(),
                    department.getManagerName());

            }
        }
    
        // Nếu không tìm thấy phòng ban nào
        if (!found) {
            System.out.println("Không tìm thấy phòng ban thỏa mãn điều kiện tìm kiếm.");
        }
    
        // Đường viền cuối bảng
        System.out.printf("╚════════════╧══════════════╧══════════════════════════════╧══════════════════╝\n");
    }
}

