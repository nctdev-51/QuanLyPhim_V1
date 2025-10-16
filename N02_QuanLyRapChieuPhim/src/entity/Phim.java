package entity;

public class Phim {
    private String maPhim;
    private String tenPhim;
    private String nhaSanXuat;
    private String theLoai;
    private int thoiLuong;
    private String quocGia;

    public Phim(String maPhim) {
        setMaPhim(maPhim);
        this.tenPhim = "";
        this.nhaSanXuat = "";
        this.theLoai = "";
        this.thoiLuong = 60;
        this.quocGia = "";
    }

    public Phim(String maPhim, String tenPhim, String nhaSanXuat, String theLoai, int thoiLuong, String quocGia) {
        setMaPhim(maPhim);
        setTenPhim(tenPhim);
        setNhaSanXuat(nhaSanXuat);
        setTheLoai(theLoai);
        setThoiLuong(thoiLuong);
        setQuocGia(quocGia);
    }

    public String getMaPhim() { return maPhim; }
    public void setMaPhim(String maPhim) {
        if (maPhim == null || maPhim.isEmpty()) throw new IllegalArgumentException("Mã phim rỗng!");
        this.maPhim = maPhim;
    }

    public String getTenPhim() { return tenPhim; }
    public void setTenPhim(String tenPhim) {
        if (tenPhim == null || tenPhim.isEmpty()) throw new IllegalArgumentException("Tên phim rỗng!");
        this.tenPhim = tenPhim;
    }

    public String getNhaSanXuat() { return nhaSanXuat; }
    public void setNhaSanXuat(String nhaSanXuat) {
        if (nhaSanXuat == null || nhaSanXuat.isEmpty()) throw new IllegalArgumentException("Nhà sản xuất rỗng!");
        this.nhaSanXuat = nhaSanXuat;
    }

    public String getTheLoai() { return theLoai; }
    public void setTheLoai(String theLoai) {
        if (theLoai == null || theLoai.isEmpty()) throw new IllegalArgumentException("Thể loại rỗng!");
        this.theLoai = theLoai;
    }

    public int getThoiLuong() { return thoiLuong; }
    public void setThoiLuong(int thoiLuong) {
        if (thoiLuong <= 0) throw new IllegalArgumentException("Thời lượng <= 0!");
        this.thoiLuong = thoiLuong;
    }

    public String getQuocGia() { return quocGia; }
    public void setQuocGia(String quocGia) {
        if (quocGia == null || quocGia.isEmpty()) throw new IllegalArgumentException("Quốc gia rỗng!");
        this.quocGia = quocGia;
    }

	@Override
	public String toString() {
		return "Phim [maPhim=" + maPhim + ", tenPhim=" + tenPhim + ", nhaSanXuat=" + nhaSanXuat + ", theLoai=" + theLoai
				+ ", thoiLuong=" + thoiLuong + ", quocGia=" + quocGia + "]";
	}

    
}
