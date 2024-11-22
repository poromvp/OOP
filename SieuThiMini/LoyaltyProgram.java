public class LoyaltyProgram {
    // Thuộc tính
    private String programName;
    private int pointsToUpgrade; // Số điểm cần để nâng cấp cấp độ
    private String[] membershipLevels; // Các cấp độ thành viên
    private double[] discountRates; // Tỷ lệ giảm giá tương ứng với từng cấp độ

    // Constructor
    public LoyaltyProgram(String programName, int pointsToUpgrade, String[] membershipLevels, double[] discountRates) {
        if (membershipLevels.length != discountRates.length) {
            throw new IllegalArgumentException("Membership levels and discount rates must have the same length.");
        }
        this.programName = programName;
        this.pointsToUpgrade = pointsToUpgrade;
        this.membershipLevels = membershipLevels;
        this.discountRates = discountRates;
    }

    // Getter và Setter
    public String getProgramName() {
        return programName;
    }

    public void setProgramName(String programName) {
        this.programName = programName;
    }

    public int getPointsToUpgrade() {
        return pointsToUpgrade;
    }

    public void setPointsToUpgrade(int pointsToUpgrade) {
        this.pointsToUpgrade = pointsToUpgrade;
    }

    public String[] getMembershipLevels() {
        return membershipLevels;
    }

    public void setMembershipLevels(String[] membershipLevels) {
        this.membershipLevels = membershipLevels;
    }

    public double[] getDiscountRates() {
        return discountRates;
    }

    public void setDiscountRates(double[] discountRates) {
        this.discountRates = discountRates;
    }

    // Phương thức

    // Tính cấp độ thành viên dựa trên điểm thưởng
    public String getMembershipLevel(int loyaltyPoints) {
        int levelIndex = Math.min(loyaltyPoints / pointsToUpgrade, membershipLevels.length - 1);
        return membershipLevels[levelIndex];
    }

    // Tính tỷ lệ giảm giá dựa trên điểm thưởng
    public double getDiscountRate(int loyaltyPoints) {
        int levelIndex = Math.min(loyaltyPoints / pointsToUpgrade, discountRates.length - 1);
        return discountRates[levelIndex];
    }

    // Hiển thị thông tin chương trình
    public void displayProgramDetails() {
        System.out.println("Loyalty Program: " + programName);
        System.out.println("Points to upgrade: " + pointsToUpgrade);
        System.out.println("Membership Levels and Discount Rates:");
        for (int i = 0; i < membershipLevels.length; i++) {
            System.out.println("- " + membershipLevels[i] + ": " + (discountRates[i] * 100) + "% discount");
        }
    }

    // Chương trình chính để minh họa
    public static void main(String[] args) {
        // Ví dụ chương trình khách hàng thân thiết
        String[] levels = { "Bronze", "Silver", "Gold", "Platinum" };
        double[] rates = { 0.02, 0.05, 0.10, 0.15 }; // Tương ứng: 2%, 5%, 10%, 15% giảm giá

        LoyaltyProgram program = new LoyaltyProgram("SuperMart Loyalty Program", 100, levels, rates);

        program.displayProgramDetails();

        // Tính cấp độ và giảm giá cho khách hàng với điểm thưởng
        int customerPoints = 10;
        System.out.println("Customer Points: " + customerPoints);
        System.out.println("Membership Level: " + program.getMembershipLevel(customerPoints));
        System.out.println("Discount Rate: " + (program.getDiscountRate(customerPoints) * 100) + "%");
    }
}
