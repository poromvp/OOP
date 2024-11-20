import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringBufferInputStream;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
public class Department extends Staff {
    protected String departmentID;
    protected String departmentName;
    protected String StaffName;

    public Department(int staffID, String name, String role, double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Department() {
    }

    public Department(int staffID, String name, String role, double salary, String contactNum, String departmentID,
            String departmentName, String staffName) {
        super(staffID, name, role, salary, contactNum);
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        StaffName = staffName;
    }

    public Department(String departmentID, String departmentName, String staffName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
        StaffName = staffName;
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

    @Override 
    public void readFromFile(String filepath){
        filepath="DepartmentStaffList.txt";
         try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;

            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                if (parts.length >= 3) {
                    // Lấy các trường từ mảng parts
                    departmentID = parts[0];
                    departmentName = parts [1];
                    StaffName = String.join(" ", Arrays.copyOfRange(parts, 3, parts.length - 1));
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi khi phân tích dữ liệu: " + e.getMessage());
        }
    }
    
    //them nhan vien vao trong danh sach department
    public void writeToFile(String filepath){
        filepath="DepartmentStaffList.txt";
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhap ma phong ban ban muon them vao(SER, MAN, SAL): ");
        String departID=sc.nextLine();
        while(!departID.equals("SER")||!departID.equals("MAN")||!departID.equals("SAL")){
            System.out.print("nhap sai !!! xin nhap lai: ");
            departID = sc.nextLine();
        }

        String departName; 

        if(departID.equals("SER")){
             departName = "SERVER";
        }
        else if(departID.equals("MAN")){
             departName="Manage";
        } else {
             departName="SAL";
        }

        System.out.print("nhap ho ten nhan vien muon them vao phong ban: ");
        String newName = sc.nextLine();

        String newdepart = departID + " " + departName + " " + newName;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath, true))){
            bw.write(newdepart);
            bw.newLine();
            bw.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        } 
    }

    public void removeStaffFromDepartment(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhap ho ten nhan vien ban muon xoa: ");
        String NameRemove = sc.nextLine();
        String filepath= "DepartmentStaffList.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader( new FileReader(filepath))){
            String line;
            while ((line= br.readLine())!=null){
                String[] parts = line.split(" ");
                if(NameRemove==String.join(" ", Arrays.copyOfRange(parts, 3, parts.length - 1))){
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

    

