package SieuThiMini;
import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;
import java.util.Scanner;


public class Staff implements QLFile{
    protected String StaffID;
    protected String Name;
    protected String Role;
    protected double Salary; 
    protected String ContactNum;
    public Object removeStaff;

    

    public Staff(String staffID, String name, String role, Double salary, String contactNum) {
        StaffID = staffID;
        Name = name;
        Role = role;
        Salary = salary;
        ContactNum = contactNum;
    }

    public Staff() {
    }

    public String getStaffID() {
        return StaffID;
    }

    public void setStaffID(String staffID) {
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

  
    public void inputStaff() {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter Staff ID: ");
        StaffID = scanner.nextLine();

        System.out.print("Enter Name: ");
        Name = scanner.nextLine();

        System.out.print("Enter Role: ");
        Role = scanner.nextLine();

        System.out.print("Enter Salary: ");
        Salary = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter Contact Number: ");
        ContactNum = scanner.nextLine();
    }

    public void displayStaffInfo() {
        System.out.println("Staff ID: " + StaffID);
        System.out.println("Name: " + Name);
        System.out.println("Role: " + Role);
        System.out.println("Salary: " + Salary);
        System.out.println("Contact Number: " + ContactNum);
        System.out.println("--------------------------");
    }
    @Override 
    public void readFromFile(String path){};
    public void writeToFile(String path){};
}
