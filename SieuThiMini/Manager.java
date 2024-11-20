import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Manager extends Staff {

    protected Department[] managerDepartment;

    public Manager(int staffID, String name, String role, double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Manager() {
    }

    public Manager(int staffID, String name, String role, double salary, String contactNum,
            Department[] managerDepartment) {
        super(staffID, name, role, salary, contactNum);
        this.managerDepartment = managerDepartment;
    }

    public Manager(Department[] managerDepartment) {
        this.managerDepartment = managerDepartment;
    }

    public Department[] getManagerDepartment() {
        return managerDepartment;
    }

    public void setManagerDepartment(Department[] managerDepartment) {
        this.managerDepartment = managerDepartment;
    }

    @Override
    public void readFromFile(String filepath){
            String filePath = "dsnv.txt"; // Đường dẫn tới file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;

            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split("\\s+"); // Tách các từ bằng khoảng trắng

                if (parts.length >= 6) {
                    // Lấy các trường từ mảng parts
                    StaffID = Integer.parseInt(parts[0]);
                    ContactNum = parts[parts.length - 1]; // Số điện thoại là phần tử cuối
                    Salary = Double.parseDouble(parts[parts.length - 2]); // Lương là phần tử kế cuối
                    Role = parts[parts.length - 3]; // Vai trò là phần tử trước lương
                    Name = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 3)); // Ghép tên từ các phần tử còn lại
                }
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi khi phân tích dữ liệu: " + e.getMessage());
        }

    }


    //them nhan vien vao danh sach nhan vien
    public void writeToFile (String filepath){
        Scanner sc= new Scanner(System.in);
        filepath ="dsnv.txt";

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

    public boolean approveTransaction (){
            return true;
    }

}
