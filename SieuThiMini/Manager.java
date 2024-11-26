
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Manager extends Staff {

    protected Staff[] Stafflist;
    protected int count =0;

    public Staff[] getStafflist() {
        return Stafflist;
    }

    public void setStafflist(Staff[] stafflist) {
        Stafflist = stafflist;
    }

    public Manager(String staffID, String name, String role, Double salary, String contactNum, Staff[] stafflist,
                   int count) {
        super(staffID, name, role, salary, contactNum);
        Stafflist = stafflist;
        this.count = count;
    }

    public Manager(Staff[] stafflist, int count) {
        Stafflist = stafflist;
        this.count = count;
    }

    public Manager(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Manager() {
    }

    @Override
    // đọc và lưu lại các phần tử trong file
    public Staff[] readFromFile(String filepath){
        try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {

            String line;
            Stafflist = new Staff[100];
            for(int i=0; i<Stafflist.length; i++){
                Stafflist[i]= new Staff();
            }
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng
                if (parts.length >= 6) {
                    // Lấy các trường từ mảng parts
                    Stafflist[count].setStaffID(parts[0]);
                    Stafflist[count].setContactNum(parts[parts.length - 1]); // Số điện thoại là phần tử cuối
                    Stafflist[count].setSalary(Double.parseDouble(parts[parts.length - 3])); // Lương là phần tử đứng trước Vai trò
                    Stafflist[count].setRole(parts[parts.length - 2]); // Vai trò là phần tử sau lương
                    Stafflist[count].setName(String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 3))); // Ghép tên từ các phần tử còn lại
                }
                count ++;
            }
        } catch (IOException e) {
            System.out.println("Lỗi khi đọc file: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Lỗi khi phân tích dữ liệu: " + e.getMessage());
        }
        return Stafflist;
    }

    public void outStaff (){
            count = 0;
            Staff[] temp =readFromFile("dsnv.txt");
            if(temp==null){
                System.out.println("khong luu vao temp được");
            }
            System.out.printf("╔════════════╤═══════════════════════════════════════╤═════════════════╤════════════╤══════════════════╗\n");
            System.out.printf("║ %-10s │ %-35s │ %-15s │ %-10s │ %-15s ║\n", "Mã NV", "Họ Tên", "Lương (VND)", "Vai trò", "Số điện thoại");
            System.out.printf("╠════════════╪═══════════════════════════════════════╪═════════════════╪════════════╪══════════════════╣\n");

            // Dữ liệu trong bảng
            for (int i = 0; i < count; i++) {
                System.out.printf("║ %-10s │ %-35s │ %-15.2f │ %-10s │ %-15s ║\n",
                    temp[i].getStaffID(),
                    temp[i].getName(),
                    temp[i].getSalary(),
                    temp[i].getRole(),
                    temp[i].getContactNum());
            }

            // Đường viền cuối bảng
            System.out.printf("╚════════════╧═══════════════════════════════════════╧═════════════════╧════════════╧══════════════════╝\n");
            System.out.println("");
    }


    //them nhan vien vao danh sach nhan vien
    public void addStaff (){
        Scanner sc= new Scanner(System.in);
        String filepath="dsnv.txt";
        count = 0;
        readFromFile(filepath);


        System.out.println("nhập vị trí dòng bạn muốn thêm vào danh sách");
        int vitri = Integer.parseInt(sc.nextLine());

        System.out.print("nhập mã cho nhân viên mới: ");
        String ID=sc.nextLine();

        System.out.println("nhập họ tên của nhân viên mới: ");
        String Ten=sc.nextLine();

        System.out.println("nhập số lương của nhân viên mới");
        double Luong = Double.parseDouble(sc.nextLine());

        System.out.println("nhập vai trò bạn muốn cho nhân viên mới: ");
        String role =  sc.nextLine();

        System.out.println("nhập số điện thoại của nhân viên mới: ");
        String PhoneNum = sc.nextLine();
        sc.nextLine();
        if(vitri-1<count){
            for (int i = count; i > vitri-1; i--) {
                Stafflist[i] = Stafflist[i - 1];

            }
        }
        if(vitri-1 >count)
        Stafflist[count++]= new Staff(ID, Ten,role,Luong,PhoneNum);
        //cập nhật lại file dsnv.txt;
        writeToFile(filepath); // cập nhật lại file dsnv.txt
        System.out.println("danh sách sau khi cập nhật: ");
        outStaff();
        sc.close();
   
        
    }


    public void writeToFile (String filepath){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))){
            String line;
            for(int i=0; i<count; i++){
                line = Stafflist[i].getStaffID()+ " " + Stafflist[i].getName() + " " +
                Stafflist[i].getSalary() + " " +
                Stafflist[i].getRole() + " " +
                Stafflist[i].getContactNum();
                bw.write(line);
                bw.newLine();
                line ="";
            }

        }catch (IOException e){
                e.printStackTrace();
            }
    }

    public void removeStaff (String filepath){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhap ma nhan vien ban muon xoa: ");
        String IDremove = sc.nextLine();
        sc.nextLine();
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
        outStaff();
        sc.close();
        
    }

    public void ChangeInFo(){
        Scanner sc = new Scanner(System.in);
        count =0;
        readFromFile("dsnv.txt");
        System.out.println("Nhập mã nhân viên bạn muốn đổi thông tin: ");
        String id=sc.nextLine();

        boolean found = false;
        for (int i = 0; i < count; i++) {
            if (Stafflist[i].getStaffID().equals(id)) {
                // Nhân viên được tìm thấy, tiến hành sửa thông tin
                found = true;

                System.out.println("Nhập thông tin mới cho nhân viên (bỏ qua nếu không muốn thay đổi):");

            // Cập nhật họ tên
            System.out.print("Nhập họ tên mới: ");
            String name = sc.nextLine();
            if (!name.isEmpty()) {
                Stafflist[i].setName(name); // Nếu không để trống, cập nhật tên mới
            }

            // Cập nhật lương
            System.out.print("Nhập lương mới: ");
            String salaryInput = sc.nextLine();
            if (!salaryInput.isEmpty()) {
                double salary = Double.parseDouble(salaryInput);
                Stafflist[i].setSalary(salary); // Nếu không để trống, cập nhật lương mới
            }

            // Cập nhật vai trò
            System.out.print("Nhập vai trò mới: ");
            String role = sc.nextLine();
            if (!role.isEmpty()) {
                Stafflist[i].setRole(role); // Nếu không để trống, cập nhật vai trò mới
            }

            // Cập nhật số điện thoại
            System.out.print("Nhập số điện thoại mới: ");
            String contactNum = sc.nextLine();
            if (!contactNum.isEmpty()) {
                Stafflist[i].setContactNum(contactNum); // Nếu không để trống, cập nhật số điện thoại mới
            }

            System.out.println("Thông tin nhân viên đã được cập nhật.");
            break; // Thoát khỏi vòng lặp khi tìm thấy nhân viên
        }
    }

    // Cập nhật lại file sau khi sửa
    writeToFile("dsnv.txt"); 
    outStaff();
    sc.close();
    }

    public void Search (){
        Scanner sc = new Scanner(System.in);
        count =0;
        readFromFile("dsnv.txt");

        System.out.println("\n--- TÌM KIẾM Nhân viên ---");
        System.out.println("Nhập tiêu chí để tìm (nhấn Enter để bỏ qua tiêu chí):");

        System.out.print("Mã nhân viên: ");
        String ID = sc.nextLine().trim();
        if (ID.isEmpty()) {
            ID = null;
        }

        System.out.print("Tên nhân viên: ");
        String Name = sc.nextLine().trim();
        if (Name.isEmpty()) {
            Name = null;
        }

        if(Name== null){
            for(int i =0; i<count; i++){
                if(ID.equals(Stafflist[i].getStaffID())){
                    System.out.println("==============================");
                    System.out.println("Tên nhân viên: "+Stafflist[i].getName());
                    System.out.println("Lương nhân viên: "+ Stafflist[i].getSalary());
                    System.out.println("Vai trò: "+ Stafflist[i].getRole());
                    System.out.println("Số điện thoại: "+Stafflist[i].getContactNum());
                }
            }
        }
         else if(ID==null){
            for(int i =0; i<count; i++){
                if(Name.equals(Stafflist[i].getName())){
                    System.out.println("==============================");
                    System.out.println("Mã nhân viên: "+Stafflist[i].getStaffID());
                    System.out.println("Lương nhân viên: "+ Stafflist[i].getSalary());
                    System.out.println("Vai trò: "+ Stafflist[i].getRole());
                    System.out.println("Số điện thoại: "+Stafflist[i].getContactNum());
                }
        }
        }
    }
}








