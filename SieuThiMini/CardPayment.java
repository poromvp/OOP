public class CardPayment extends Payment {
    private String soThe;

    public CardPayment(double soTien, String soThe) {
        super(soTien);
        this.soThe = soThe;
    }

    @Override
    public String xuLyThanhToan() {
        return "Thanh toán qua thẻ: " + soThe + " với số tiền:        " + soTien;
    }
}
