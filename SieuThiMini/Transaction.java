public class Transaction {
    public Order donhang;
    public Payment phuongThucThanhToan;
    public Transaction(){
        donhang=new Order();
    }


    public Transaction(Order donhang, Payment phuongThucThanhToan) {
        this.donhang = donhang;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public Order getDonhang() {
        return donhang;
    }

    public void setDonhang(Order donhang) {
        this.donhang = donhang;
    }

    public Payment getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(Payment phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

}
