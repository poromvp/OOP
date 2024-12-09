public class CashPayment extends Payment {
    public CashPayment(double soTien) {
        super(soTien);
    }
    @Override
    public String xuLyThanhToan() {
        return String.format("Thanh toán bằng tiền mặt với số tiền:        %, .2fđ", soTien);
    }
}
