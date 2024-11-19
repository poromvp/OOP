public class Manager extends Staff {
    protected Department[] managerDepartment;

    public Manager() {
    }

    public Manager(String staffID, String name, String role, int salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Manager(Department[] managerDepartment) {
        this.managerDepartment = managerDepartment;
    }

    public Manager(String staffID, String name, String role, int salary, String contactNum,
            Department[] managerDepartment) {
        super(staffID, name, role, salary, contactNum);
        this.managerDepartment = managerDepartment;
    }

    public Department[] getManagerDepartment() {
        return managerDepartment;
    }

    public void setManagerDepartment(Department[] managerDepartment) {
        this.managerDepartment = managerDepartment;
    }

    public void manageStaff (){

    }

    public boolean approveTransaction (){

    }

}
