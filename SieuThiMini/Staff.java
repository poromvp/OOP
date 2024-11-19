import java.io.BufferedReader; 
import java.io.FileReader; 
import java.io.IOException;

public abstract class Staff {
    protected String StaffID;
    protected String Name;
    protected String Role;
    protected int Salary;
    protected String ContactNum;

    public Staff() {
    }

    public Staff(String staffID, String name, String role, int salary, String contactNum) {
        StaffID = staffID;
        Name = name;
        Role = role;
        Salary = salary;
        ContactNum = contactNum;
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

    public int getSalary() {
        return Salary;
    }

    public void setSalary(int salary) {
        Salary = salary;
    }

    public String getContactNum() {
        return ContactNum;
    }

    public void setContactNum(String contactNum) {
        ContactNum = contactNum;
    }

    
    public void GetDetail (){
        String filepath="dsnv.txt";
        try (BufferedReader br= new BufferedReader(new FileReader(filepath))){
            String Line;
            while ((Line= br.readLine())!=null){
                String [] parts= Line.split(" ");
                StaffID = parts[0];
                Name = parts[1] +" "+ parts[2]+ " "+ parts[3];
                Salary= Integer.parseInt(parts[4]);
                Role = parts[5];
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }
    



    



}
