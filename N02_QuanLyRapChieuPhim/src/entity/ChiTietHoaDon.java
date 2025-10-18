package entity;

public class ChiTietHoaDon {
    private HoaDon hoaDon;
    private Ve ve;
    private double giaVe;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(HoaDon hoaDon, Ve ve, double giaVe) {
        this.hoaDon = hoaDon;
        this.ve = ve;
        this.giaVe = giaVe;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public void setHoaDon(HoaDon hoaDon) {
        this.hoaDon = hoaDon;
    }

    public Ve getVe() {
        return ve;
    }

    public void setVe(Ve ve) {
        this.ve = ve;
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
                ", ve=" + (ve != null ? ve.getMaVe() : "null") +
                ", giaVe=" + giaVe +
                '}';
    }
}
