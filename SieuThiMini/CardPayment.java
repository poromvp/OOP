


    import java.util.Scanner;

    public class CardPayment extends Payment{
        private String cardNumber;
        private String cardHolderName;
        private String expiryDate;
        private String cvv;

        // Constructor
        public CardPayment() {
        }

        // Getters and Setters
        public String getCardNumber() {
            return cardNumber;
        }

        public void setCardNumber(String cardNumber) {
            this.cardNumber = cardNumber;
        }

        public String getCardHolderName() {
            return cardHolderName;
        }

        public void setCardHolderName(String cardHolderName) {
            this.cardHolderName = cardHolderName;
        }

        public String getExpiryDate() {
            return expiryDate;
        }

        public void setExpiryDate(String expiryDate) {
            this.expiryDate = expiryDate;
        }

        public String getCvv() {
            return cvv;
        }

        public void setCvv(String cvv) {
            this.cvv = cvv;
        }

        // Method to input card details
        public void inputCardDetails() {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Nhập số thẻ: ");
            this.cardNumber = scanner.nextLine();

            System.out.print("Nhập tên chủ thẻ: ");
            this.cardHolderName = scanner.nextLine();

            System.out.print("Nhập ngày hết hạn (MM/YY): ");
            this.expiryDate = scanner.nextLine();

            System.out.print("Nhập CVV: ");
            this.cvv = scanner.nextLine();
        }

        // Method to display card details
        public void displayCardDetails() {
            System.out.println("Thông tin thẻ:");
            System.out.println("Số thẻ: " + cardNumber);
            System.out.println("Tên chủ thẻ: " + cardHolderName);
            System.out.println("Ngày hết hạn: " + expiryDate);
            System.out.println("CVV: " + cvv);
        }
    }




