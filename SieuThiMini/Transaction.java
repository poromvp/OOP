public class Transaction {
    public Order donhang;
    public String tenNhanVien;
    public Payment phuongThucThanhToan;
    public Transaction(){
        donhang=new Order();
    }


    public Transaction(Order donhang, String tenNhanVien, Payment phuongThucThanhToan) {
        this.donhang = donhang;
        this.tenNhanVien = tenNhanVien;
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public Order getDonhang() {
        return donhang;
    }

    public void setDonhang(Order donhang) {
        this.donhang = donhang;
    }

    public String getTenNhanVien() {
        return tenNhanVien;
    }

    public void setTenNhanVien(String tenNhanVien) {
        this.tenNhanVien = tenNhanVien;
    }

    public Payment getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(Payment phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

}
