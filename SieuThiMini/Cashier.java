package SieuThiMini;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

import javax.swing.text.ChangedCharSetException;

import java.io.BufferedReader;
public class Cashier extends Staff {
    protected String cashCounterNum;
    protected String shiftTiming;

    public Cashier() {
    }

    public Cashier(int staffID, String name, String role, double salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Cashier(String cashCounterNum, String shiftTiming) {
        this.cashCounterNum = cashCounterNum;
        this.shiftTiming = shiftTiming;
    }

    public Cashier(int staffID, String name, String role, double salary, String contactNum, String cashCounterNum,
            String shiftTiming) {
        super(staffID, name, role, salary, contactNum);
        this.cashCounterNum = cashCounterNum;
        this.shiftTiming = shiftTiming;
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

    @Override 
    public void readFromFile (String filepath){
        filepath="CashierList.txt";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))){
            String line;

            while ((line = br.readLine()) != null){
                String[] parts = line.split(" "); // Tách các từ bằng khoảng trắng

                while(parts.length >= 2){
                    cashCounterNum = parts[0]; // phần tử đầu tiên là quầy nhân viên sẽ đứng làm việc
                    shiftTiming = parts[1];     //phần tử thứ hai là ca làm việc của nhân viên đó
                    this.setName(String.join(" ", Arrays.copyOfRange(parts, 2, parts.length - 1))); // từ phần tử thứ 3 trở đi là tên nhân viên 
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    // thêm nhân viên vào vị trí thu ngân ở ca nào đó
    public void writeToFile (String filepath){
        Scanner sc = new Scanner (System.in);
        filepath = "CashierList.txt";
        
        System.out.println("nhap ten nhan vien muon dua vao lam viec: ");
        String CashierName = sc.nextLine();

        System.out.println("Ban muon sap xep nhan vien nay lam viec ca nao (nhap CaA / CaB): ");
        String shift = sc.nextLine();

        System.out.println("Ban muon nhan vien nay lam viec tai quay nao (nhap QuayA / QuayB): ");
        String CashNumCounter = sc.nextLine();

        String newShift = CashNumCounter + " " + shift + " " + CashierName;

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath,true))){
            bw.write(newShift);
            bw.newLine();
            bw.close();

        } catch (IOException e)
        {
            e.printStackTrace();
        } 
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
    }
    

    

    
}
