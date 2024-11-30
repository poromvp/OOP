public abstract class Payment {
    protected double soTien;

    public Payment(double soTien) {
        this.soTien = soTien;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public abstract String xuLyThanhToan();
    
}
