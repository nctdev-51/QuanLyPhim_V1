package entity;

public class Rap {
    private String maRap;
    private String tenRap;
    private String diaChi;

    public Rap(String maRap) {
        setMaRap(maRap);
        this.tenRap = "";
        this.diaChi = "";
    }

    public Rap(String maRap, String tenRap, String diaChi) {
        setMaRap(maRap);
        setTenRap(tenRap);
        setDiaChi(diaChi);
    }

    public String getMaRap() {
        return maRap;
    }

    public void setMaRap(String maRap) {
        if (maRap == null || maRap.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã rạp không được rỗng!");
        }
        this.maRap = maRap.trim();
    }

    public String getTenRap() {
        return tenRap;
    }

    public void setTenRap(String tenRap) {
        if (tenRap == null || tenRap.trim().isEmpty()) {
            throw new IllegalArgumentException("Tên rạp không được rỗng!");
        }
        this.tenRap = tenRap.trim();
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = (diaChi == null) ? "" : diaChi.trim();
    }

    @Override
    public String toString() {
        return "Rap [maRap=" + maRap + ", tenRap=" + tenRap + ", diaChi=" + diaChi + "]";
    }
}
