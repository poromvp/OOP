


public abstract class Staff implements QLFile{
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

    public abstract void getdetail();
    public abstract void add();
    public abstract void remove();
    public abstract void ChangeInFo();
    public abstract void search();




    @Override 
    public Object readFromFile(String path){Object object = new Object();
        return object;};
    public void writeToFile(String path){};
}
