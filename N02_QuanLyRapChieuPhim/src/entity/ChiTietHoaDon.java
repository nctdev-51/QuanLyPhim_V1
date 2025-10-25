package entity;

public class ChiTietHoaDon {
    private HoaDon hoaDon;
    private int soLuongVe;
    private double giaVe;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon hoaDon, int soLuongVe, double giaVe) {
        this.hoaDon = hoaDon;
        this.soLuongVe = soLuongVe;
        this.giaVe = giaVe;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public int getVe() {
        return soLuongVe;
    }

    public void setVe(int soLuongVe) {
        this.soLuongVe = soLuongVe;
    }

    public double getGiaVe() {
        return giaVe;
    }

    public void setGiaVe(double giaVe) {
        this.giaVe = giaVe;
    }

    public double tinhThanhTien() {
        return giaVe;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                "hoaDon=" + (hoaDon != null ? hoaDon.getMaHoaDon() : "null") +
                ", so luong ve=" + soLuongVe +
                ", giaVe=" + giaVe +
                '}';
    }
}
