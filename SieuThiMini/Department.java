import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Scanner;
import java.io.BufferedReader;
public class Department extends Staff {
    protected String departmentID;
    protected String departmentName;
    protected Staff[] StaffList;

    public Department(String departmentID, String departmentName, Staff[] staffList) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        StaffList = staffList;
    }

    public Department(String staffID, String name, String role, int salary, String contactNum, String departmentID,
            String departmentName, Staff[] staffList) {
        super(staffID, name, role, salary, contactNum);
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        StaffList = staffList;
    }

    public Department() {
    }

    public Department(String staffID, String name, String role, int salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
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

    public Staff[] getStaffList() {
        return StaffList;
    }

    public void setStaffList(Staff[] staffList) {
        StaffList = staffList;
    }
    
    public void addStaff (Staff[] Stafflist){
        Scanner sc= new Scanner(System.in);
        String filepath ="dsnv.txt";

        System.out.print("Nhap ma cho nhan vien moi: ");
        String ID=sc.nextLine();

        System.out.println("Nhap ho ten nhan vien moi: ");
        String Ten=sc.nextLine();

        String Luong="25000";

        System.out.println("Nhap vi tri ban muon dua vao: ");
        String role =  sc.nextLine();

        String newStaff= ID + " " + Ten + " " + Luong + " " + role; 
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            bw.write(newStaff);
            bw.newLine();
            bw.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        } 
    }

    public void removeStaff (String Staff[]){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhap ma nhan vien ban muon xoa: ");
        String IDremove = sc.nextLine();
        String filepath= "dsnv.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader( new FileReader(filepath))){
            String line;
            while ((line= br.readLine())!=null){
                if(!line.startsWith(IDremove)){
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
    }

    
    
}
