package Do_An_OOp;

public class LoyaltyProgram {
    private int programID;
    private int rewardPoints;
    private String name;
    
    public LoyaltyProgram() {

    }

    public LoyaltyProgram(int programID, int rewardPoints, String name) {
        this.programID = programID;
        this.rewardPoints = rewardPoints;
        this.name = name;
    }

    public int getProgramID() {
        return programID;
    }

    public void setProgramID(int programID) {
        this.programID = programID;
    }

    public int getRewardPoints() {
        return rewardPoints;
    }

    public void setRewardPoints(int rewardPoints) {
        this.rewardPoints = rewardPoints;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void addCustomerToProgram() {

    }

    public boolean redeemPoints() {

    }
}
