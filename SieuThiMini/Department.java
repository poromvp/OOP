
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
    public void readFromFile(String filepath){
        filepath="DepartmentStaffList.txt";
         try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
            String line;
            count = 0;

            System.out.printf("%-10s| %-10s| %-12s %n", 
            "Mã phòng ban","Tên phòng ban","Họ Tên");
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                if (parts.length >= 3) {
                    // Lấy các trường từ mảng parts
                    departmentlist[count].setDepartmentID(parts[0]);    // phần tử đầu là mã phòng ban
                    departmentlist[count].setDepartmentName(parts [1]); // phần tử thứ hai là tên phòng ban
                    departmentlist[count].setStaffName(String.join(" ", Arrays.copyOfRange(parts, 3, parts.length - 1))); // từ phần tử thứ 3 trở đi là tên nhân viên thuộc phòng ban đó
                }
                count ++;
                System.out.printf("%-10s| %-10s| %-12s %n",departmentlist[count].getDepartmentID(), departmentlist[count].getDepartmentName(), departmentlist[count].getStaffName());
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
        File inputFile = new File(filepath);
        String filetemp = "temp.txt";
        File temp = new File(filetemp);
        System.out.print("Nhập mã phòng ban bạn muốn thêm vào(SER, MAN, SAL): ");
        String departID=sc.nextLine();
        while(!departID.equals("SER")||!departID.equals("MAN")||!departID.equals("SAL")){
            System.out.print("Nhập sai!!! xin nhập lại: ");
            departID = sc.nextLine();
        }

        System.out.println("nhập vị trí dòng bạn muốn thêm vào: ");
        int vitri = Integer.parseInt(sc.nextLine());

        String departName; 

        if(departID.equals("SER")){
             departName = "SERVER";
        }
        else if(departID.equals("MAN")){
             departName="Manage";
        } else {
             departName="SAL";
        }

        System.out.print("Nhập họ tên nhân viên bạn muốn thêm vào: ");
        String newName = sc.nextLine();

        String newDepart = departID + " " + departName + " " + newName;

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
        ){
            String line;
            int currentLine = 0; // dòng đang thao tác hiện tại
            while ((line = br.readLine())!=null){
                //chèn dòng mới vào vị trí mong muốn
                if(currentLine==vitri){
                    bw.write(newDepart);
                    bw.newLine();
                }
                bw.write(line);
                bw.newLine();
                currentLine++;
            }
            // nếu vị trí muốn thêm lớn hơn số dòng hiện có thì sẽ thêm mới một dòng ở cuối
            if (vitri >= currentLine){
                bw.write(newDepart);
                bw.newLine();
            }
        } catch(IOException e) {
            e.printStackTrace();
        }

        if (!inputFile.delete()){
            System.out.println("bị lỗi !!!");
            return;
        }

        if(!temp.renameTo(inputFile)){
            System.out.println("không thể đổi tên file");
        }else {
            System.out.println("đã chèn dòng mới vào");
        }

        readFromFile(filepath);
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
        readFromFile(filepath);
    }
}

    

