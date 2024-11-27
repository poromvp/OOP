

public abstract class Payment {

    protected double totalAmount;
    protected double customerPaid;

    public Payment() {
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public double getCustomerPaid() {
        return customerPaid;
    }

    public void setCustomerPaid(double customerPaid) {
        this.customerPaid = customerPaid;
    }

    public Payment(double totalAmount, double customerPaid) {
        this.totalAmount = totalAmount;
        this.customerPaid = customerPaid;
    }


    public void processPayment() {
        if (customerPaid >= totalAmount) {
            double change = customerPaid - totalAmount;
            System.out.println("Payment successful!");
            System.out.println("Change to return: $" + change);
        } else {
            double deficit = totalAmount - customerPaid;
            System.out.println("Insufficient payment. Need more: $" + deficit);
        }
    }
    public abstract void pay();
}
