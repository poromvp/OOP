

public class CashPayment extends Payment {
    public CashPayment(double amount){
        super(amount);
    }

    @Override 
    public void pay(){
        System.out.println("Payment of $" + amount + " made in cash.");

    }

    public double caculateChange(double cashGiven){
        return cashGiven - amount;
    }
    
}
