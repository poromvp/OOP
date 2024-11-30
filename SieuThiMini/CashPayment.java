public class CashPayment extends Payment {
    public CashPayment(double soTien) {
        super(soTien);
    }

    @Override
    public String xuLyThanhToan() {
        return "Thanh toán bằng tiền mặt với số tiền:        " + soTien;
    }
}
