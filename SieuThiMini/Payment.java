public abstract class Payment {
    protected double amount;

    public Payment(double amount){
        this.amount = amount;
    }

    public abstract void pay();


    public double getAmount() {
        return this.amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }


}
