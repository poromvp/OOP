public class CardPayment extends Payment {
    private String soThe;
    public CardPayment(double soTien, String soThe) {
        super(soTien);
        this.soThe = soThe;
    }

    @Override
    public String xuLyThanhToan() {
        return String.format("Thanh toán qua thẻ: %s với số tiền:        %, .2fđ", soThe, soTien);
    }

    public String getSoThe() {
        return soThe;
    }

    public void setSoThe(String soThe) {
        this.soThe = soThe;
    }
    
}
