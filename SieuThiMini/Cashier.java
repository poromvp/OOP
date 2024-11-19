public class Cashier extends Staff {
    protected int cashCounterNum;
    protected String shiftTiming;

    public Cashier() {
    }

    public Cashier(String staffID, String name, String role, int salary, String contactNum) {
        super(staffID, name, role, salary, contactNum);
    }

    public Cashier(int cashCounterNum, String shiftTiming) {
        this.cashCounterNum = cashCounterNum;
        this.shiftTiming = shiftTiming;
    }

    public Cashier(String staffID, String name, String role, int salary, String contactNum, int cashCounterNum,
            String shiftTiming) {
        super(staffID, name, role, salary, contactNum);
        this.cashCounterNum = cashCounterNum;
        this.shiftTiming = shiftTiming;
    }

    public int getCashCounterNum() {
        return cashCounterNum;
    }

    public void setCashCounterNum(int cashCounterNum) {
        this.cashCounterNum = cashCounterNum;
    }

    public String getShiftTiming() {
        return shiftTiming;
    }

    public void setShiftTiming(String shiftTiming) {
        this.shiftTiming = shiftTiming;
    }

    

    

    
}
