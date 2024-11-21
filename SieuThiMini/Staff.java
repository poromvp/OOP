package SieuThiMini;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;

public abstract class Staff implements QLFile {
    protected int StaffID;
    protected String Name;
    protected String Role;
    protected double Salary;
    protected String ContactNum;

    

    public Staff(int staffID, String name, String role, double salary, String contactNum) {
        StaffID = staffID;
        Name = name;
        Role = role;
        Salary = salary;
        ContactNum = contactNum;
    }

    public Staff() {
    }

    public int getStaffID() {
        return StaffID;
    }

    public void setStaffID(int staffID) {
        StaffID = staffID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double salary) {
        Salary = salary;
    }

    public String getContactNum() {
        return ContactNum;
    }

    public void setContactNum(String contactNum) {
        ContactNum = contactNum;
    }

    public void getdetail(){
        System.out.println("Ma nhan vien: "+ StaffID);
        System.out.println("Ho ten nhan vien: "+Name);
        System.out.println("Luong: "+ Salary);
        System.out.println("Vai tro: "+Role);
        System.out.println("So dien thoai"+ContactNum);
    }

    @Override
    public abstract void writeToFile(String filePath);
    public abstract void readFromFile(String filePath);

}
    



    



