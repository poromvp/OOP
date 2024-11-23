
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Manager extends Staff {

    protected Staff[] Stafflist;
    protected int count;

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
    public void readFromFile(String filepath){
        String filePath = "dsnv.txt"; // Đường dẫn tới file
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            count = 0;

            System.out.printf("%-5s| %-10s| %-10s| %-10s| %-12s %n",
                    "Mã NV","Họ Tên", "Lương", "Vai trò", "Số điện thoại");
            System.out.println();
            while ((line = reader.readLine()) != null ) {
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                if (parts.length >= 6) {
                    // Lấy các trường từ mảng parts
                    Stafflist[count].setStaffID(parts[0]);
                    Stafflist[count].setContactNum(parts[parts.length - 1]); // Số điện thoại là phần tử cuối
                    Stafflist[count].setSalary(Double.parseDouble(parts[parts.length - 2])); // Lương là phần tử kế cuối
                    Stafflist[count].setRole(parts[parts.length - 3]); // Vai trò là phần tử trước lương
                    Stafflist[count].setName(String.join(" ", Arrays.copyOfRange(parts, 1, parts.length - 3))); // Ghép tên từ các phần tử còn lại
                }
                count ++;
                System.out.printf("%-5s| %-10s| %-10s| %-10s| %-12s %n",
                        Stafflist[count].getStaffID(), Stafflist[count].getName(), Stafflist[count].getSalary(), Stafflist[count].getRole(), Stafflist[count].getContactNum());
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
        File inputFile = new File(filepath);
        String filetemp = "temp.txt";
        File temp = new File(filetemp);

        System.out.println("nhập vị trí dòng bạn muốn thêm vào danh sách");
        int vitri = sc.nextInt();

        System.out.print("nhập mã cho nhân viên mới: ");
        String ID=sc.nextLine();

        System.out.println("nhập họ tên của nhân viên mới: ");
        String Ten=sc.nextLine();

        String Luong="25000";

        System.out.println("nhập vai trò bạn muốn cho nhân viên mới: ");
        String role =  sc.nextLine();

        System.out.println("nhập số điện thoại của nhân viên mới: ");
        String PhoneNum = sc.nextLine();

        String newline = ID + " " + Ten + " " + Luong + " " + role + " " + PhoneNum;

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
        ){
            String line;
            int currentLine = 0; // dòng đang thao tác hiện tại
            while ((line = br.readLine())!=null){
                //chèn dòng mới vào vị trí mong muốn
                if(currentLine==vitri){
                    bw.write(newline);
                    bw.newLine();
                }
                bw.write(line);
                bw.newLine();
                currentLine++;
            }

            if (vitri >= currentLine){
                bw.write(newline);
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

    public void removeStaff (){
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

        readFromFile(filepath);
    }



}
