import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
public class Department extends Staff {
    protected String departmentID;
    protected String departmentName;
    protected String StaffName;
    protected Department[] departmentlist;
    protected int count;

    public Department(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Department() {
    }

    public Department(String staffID, String name, String role, Double salary, String contactNum, String departmentID,
                      String departmentName, String staffName, Department[] departmentlist, int count) {
        super(staffID, name, role, salary, contactNum);
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        StaffName = staffName;
        this.departmentlist = new Department[100];
    }

    public Department(String departmentID, String departmentName, String staffName, Department[] departmentlist, int count) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        StaffName = staffName;
        this.departmentlist = new Department[100];
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

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

    public String getStaffName() {
        return StaffName;
    }

    public void setStaffName(String staffName) {
        StaffName = staffName;
    }

    public Department[] getDepartmentlist() {
        return departmentlist;
    }

    public void setDepartmentlist(Department[] departmentlist) {
        this.departmentlist = departmentlist;
    }

    @Override
    public Department[] readFromFile(String filepath){
         try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            count = 0;
            Department[] departmentlist = new Department[100];
            for (int i = 0; i < departmentlist.length; i++) {
                 departmentlist[i] = new Department(); 
                }
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" ");       // Tách các từ bằng khoảng trắng
                if (parts.length >= 3) {
                    // Lấy các trường từ mảng parts
                    departmentlist[count].setDepartmentID(parts[0]);        // phần tử đầu là mã phòng ban
                    departmentlist[count].setDepartmentName(parts [1]);     // phần tử thứ hai là tên phòng ban
                    departmentlist[count].setStaffName(String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)));   // từ phần tử thứ 3 trở đi là tên nhân viên thuộc phòng ban đó
                }
                count ++;
            }

        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi khi phân tích dữ liệu: " + e.getMessage());
        }
        return departmentlist;
    }

    public void outDepartment (){
        count = 0;
        Department[] temp =readFromFile("DepartmentList.txt");
        if(temp==null){
            System.out.println("khong luu vao temp được");
        }
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");
        System.out.printf("║ %-10s │ %-10s │ %-20s ║\n", "Mã NV", "Họ Tên", "Lương (VND)");
        System.out.printf("╠════════════╪══════════════╪══════════════════════════════╣\n");

        // Dữ liệu trong bảng
        for (int i = 0; i < count; i++) {
            System.out.printf("║ %-10s │ %-10s │ %-20f ║\n",
                temp[i].getDepartmentID(),
                temp[i].getDepartmentName(),
                temp[i].getName());
        }
    }

    public void writeToFile (String filepath){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            String line;
            for(int i=0; i<count; i++){
                line = departmentlist[i].getDepartmentID() + " " + departmentlist[i].getDepartmentName() + " " + departmentlist[i].getName();
                bw.write(line);
                bw.newLine();
                line ="";
            }

        }catch (IOException e){
                e.printStackTrace();
            }
    }

    public void addStafftoDepartment(){
        
    }

    public void removeStaffFromDepartment(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhap ho ten nhan vien ban muon xoa: ");
        String NameRemove = sc.nextLine();
        String filepath="D:\\DoAnOOP\\OOP\\SieuThiMiNi\\DepartmentStaffList.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader( new FileReader(filepath))){
            String line;
            while ((line= br.readLine())!=null){
                String[] parts = line.split(" ");
                if(!NameRemove.equals(String.join(" ", Arrays.copyOfRange(parts, 2, parts.length)))){
                    content.append(line).append(System.lineSeparator());            // đọc file và lưu lại các dòng không chứa mã nhân viên cần xoá;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

        try(FileWriter fw = new FileWriter(filepath)){
            fw.write(content.toString());
        } catch (IOException e){
            e.printStackTrace();
        }
        readFromFile(filepath);
    }
}

