public class CardPayment extends Payment {

    private String cardNumber;
    private String expiryDate;
    private String cardHolder;


    public CardPayment(double amount, String cardNumber, String expiryDate, String cardHolder) {
        super(amount);
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cardHolder = cardHolder;
    }

    @Override
    public void pay(){
        if (authorize()) {
            System.out.println("Payment of đ" + amount + " made using card: " + cardNumber);
        } else {
            System.out.println("Card authorization failed.");
        }
    }

    public boolean authorize (){
        return true;
    }


    public String getCardNumber() {
        return this.cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getExpiryDate() {
        return this.expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getCardHolder() {
        return this.cardHolder;
    }

    public void setCardHolder(String cardHolder) {
        this.cardHolder = cardHolder;
    }


}

