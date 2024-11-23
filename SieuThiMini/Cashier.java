
import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.text.ChangedCharSetException;
public class Cashier extends Staff {
    protected String cashCounterNum;
    protected String shiftTiming;
    protected Cashier[] cashier;
    protected int count;

    public Cashier(String staffID, String name, String role, Double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Cashier() {
    }

    public Cashier(String staffID, String name, String role, Double salary, String contactNum, String cashCounterNum,
                   String shiftTiming, Cashier[] cashier,int count) {
        super(staffID, name, role, salary, contactNum);
        this.cashCounterNum = cashCounterNum;
        this.shiftTiming = shiftTiming;
        this.cashier = new Cashier[100];
    }

    public Cashier(String cashCounterNum, String shiftTiming, Cashier[] cashier, int count) {
        this.cashCounterNum = cashCounterNum;
        this.shiftTiming = shiftTiming;
        this.cashier = new Cashier[100];
    }

    public String getCashCounterNum() {
        return cashCounterNum;
    }

    public void setCashCounterNum(String cashCounterNum) {
        this.cashCounterNum = cashCounterNum;
    }

    public String getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(String shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    public Cashier[] getCashier() {
        return cashier;
    }

    public void setCashier(Cashier[] cashier) {
        this.cashier = cashier;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void readFromFile (String filepath){
        filepath="CashierList.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))){
            String line;
            count =0;
            System.out.printf("%-10s| %-10s| %-10s| %-10s| %-12s %n",
                    "Quầy","Ca làm việc", "Họ tên");
            System.out.println();
            while ((line = br.readLine()) != null){
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                while(parts.length >= 2){
                    cashier[count].setCashCounterNum(parts[0]); // phần tử đầu tiên là quầy nhân viên sẽ đứng làm việc
                    cashier[count].setShiftTiming(parts[1]);     //phần tử thứ hai là ca làm việc của nhân viên đó
                    cashier[count].setName(String.join(" ", Arrays.copyOfRange(parts, 2, parts.length - 1))); // từ phần tử thứ 3 trở đi là tên nhân viên
                }
                count ++;
                System.out.printf("%-10s| %-10s| %-12s %n",cashier[count].getCashCounterNum(), cashier[count].getShiftTiming(), cashier[count].getName());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // thêm nhân viên vào vị trí thu ngân ở ca nào đó
    public void writeToFile (String filepath){
        Scanner sc = new Scanner (System.in);
        filepath = "CashierList.txt";
        File inputFile = new File(filepath);
        String filetemp = "temp.txt";
        File temp = new File(filetemp);

        System.out.println("nhập vị trí dòng bạn muốn thêm vào danh sách");
        int vitri = sc.nextInt();

        System.out.println("Nhập nhân viên bạn muốn xếp lịch làm: ");
        String CashierName = sc.nextLine();

        System.out.println("Bạn muốn nhân viên làm ca nào(Nhập CaA / CaB): ");
        String shift = sc.nextLine();
        while(!shift.equals("SER")||!shift.equals("MAN")||!shift.equals("SAL")){
            System.out.print("Nhập sai!!! xin nhập lại: ");
            shift = sc.nextLine();
        }
        System.out.println("Bạn muốn nhân viên làm việc ở quầy nào (Nhập QuayA / QuayB): ");
        String CashNumCounter = sc.nextLine();
        while(!CashNumCounter.equals("SER")||!CashNumCounter.equals("MAN")||!CashNumCounter.equals("SAL")){
            System.out.print("Nhập sai!!! xin nhập lại: ");
            CashNumCounter = sc.nextLine();
        }
        String newShift = CashNumCounter + " " + shift + " " + CashierName;

        try (
                BufferedReader br = new BufferedReader(new FileReader(inputFile));
                BufferedWriter bw = new BufferedWriter(new FileWriter(temp))
        ){
            String line;
            int currentLine = 0; // dòng đang thao tác hiện tại
            while ((line = br.readLine())!=null){
                //chèn dòng mới vào vị trí mong muốn
                if(currentLine==vitri){
                    bw.write(newShift);
                    bw.newLine();
                }
                bw.write(line);
                bw.newLine();
                currentLine++;
            }
            // nếu vị trí muốn thêm lớn hơn số dòng hiện có thì sẽ thêm mới một dòng ở cuối
            if (vitri >= currentLine){
                bw.write(newShift);
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

    public void removeShift(){
        Scanner sc= new Scanner(System.in);
        System.out.print("Nhap ho ten nhan vien ban muon xoa ca lam: ");
        String NameShiftRemove = sc.nextLine();
        String filepath= "CashierList.txt";
        StringBuilder content = new StringBuilder();

        try (BufferedReader br = new BufferedReader( new FileReader(filepath))){
            String line;
            while ((line= br.readLine())!=null){
                String[] parts = line.split(" ");
                if(NameShiftRemove==String.join(" ", Arrays.copyOfRange(parts, 3, parts.length - 1))){
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
