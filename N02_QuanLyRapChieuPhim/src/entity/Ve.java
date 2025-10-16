package entity;

import java.time.LocalDate;

public class Ve {
    private String maVe;
    private Ghe ghe;
    private LocalDate ngayBan;
    private String maKhachHang;
    private String maNhanVien;
    private String maPhim;
    private String maRap;
    private String maSuatChieu;
    private boolean daThanhToan;

    public Ve(String maVe) {
        setMaVe(maVe);
        this.ngayBan = LocalDate.now();
        this.daThanhToan = false;
    }

    public Ve(String maVe, Ghe ghe, LocalDate ngayBan, String maKhachHang, 
              String maNhanVien, String maPhim, String maRap, String maSuatChieu, boolean daThanhToan) {
        setMaVe(maVe);
        setGhe(ghe);
        setNgayBan(ngayBan);
        setMaKhachHang(maKhachHang);
        setMaNhanVien(maNhanVien);
        setMaPhim(maPhim);
        setMaRap(maRap);
        setMaSuatChieu(maSuatChieu);
        setDaThanhToan(daThanhToan);
    }

    public String getMaVe() {
        return maVe;
    }

    public void setMaVe(String maVe) {
        if (maVe == null || maVe.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã vé không được rỗng!");
        }
        this.maVe = maVe;
    }

    public Ghe getGhe() {
        return ghe;
    }

    public void setGhe(Ghe ghe) {
        this.ghe = ghe;
    }

    public LocalDate getNgayBan() {
        return ngayBan;
    }

    public void setNgayBan(LocalDate ngayBan) {
        this.ngayBan = ngayBan;
    }

    public String getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(String maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getMaNhanVien() {
        return maNhanVien;
    }

    public void setMaNhanVien(String maNhanVien) {
        this.maNhanVien = maNhanVien;
    }

    public String getMaPhim() {
        return maPhim;
    }

    public void setMaPhim(String maPhim) {
        this.maPhim = maPhim;
    }

    public String getMaRap() {
        return maRap;
    }

    public void setMaRap(String maRap) {
        this.maRap = maRap;
    }

    public String getMaSuatChieu() {
        return maSuatChieu;
    }

    public void setMaSuatChieu(String maSuatChieu) {
        this.maSuatChieu = maSuatChieu;
    }

    public boolean isDaThanhToan() {
        return daThanhToan;
    }

    public void setDaThanhToan(boolean daThanhToan) {
        this.daThanhToan = daThanhToan;
    }

    @Override
    public String toString() {
        return "Vé {" +
                "Mã vé='" + maVe + '\'' +
                ", Ghế=" + (ghe != null ? ghe.getMaGhe() : "Chưa chọn") +
                ", Ngày bán=" + ngayBan +
                ", Mã KH='" + maKhachHang + '\'' +
                ", Mã NV='" + maNhanVien + '\'' +
                ", Mã phim='" + maPhim + '\'' +
                ", Mã rạp='" + maRap + '\'' +
                ", Mã suất chiếu='" + maSuatChieu + '\'' +
                ", Đã thanh toán=" + daThanhToan +
                '}';
    }
}
