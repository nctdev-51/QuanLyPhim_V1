package entity;

import java.time.LocalDate;

public class NhanVien {
	private String maNV;
	private String tenNV;
	private String diaChi;
	private String soDienThoai;
	private String trangThai;
	private LocalDate ngaySinh;
	private String vaiTro;
	private String email;
	private String gioiTinh;
	
	public NhanVien() {
		this("", "", "", "", "", LocalDate.now(), "Nhân viên", "", "");
	}

	public NhanVien(String maNV, String tenNV, String diaChi, String soDienThoai, String trangThai, LocalDate ngaySinh,
			String vaiTro, String email, String gioiTinh) {
		super();
		this.maNV = maNV;
		this.tenNV = tenNV;
		this.diaChi = diaChi;
		this.soDienThoai = soDienThoai;
		this.trangThai = trangThai;
		this.ngaySinh = ngaySinh;
		this.vaiTro = vaiTro;
		this.email = email;
		this.gioiTinh = gioiTinh;
	}

	public String getMaNV() {
		return maNV;
	}

	public void setMaNV(String maNV) {
		this.maNV = maNV;
	}

	public String getTenNV() {
		return tenNV;
	}

	public void setTenNV(String tenNV) {
		this.tenNV = tenNV;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public LocalDate getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDate ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public String getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(String vaiTro) {
		this.vaiTro = vaiTro;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@Override
	public String toString() {
		return "NhanVien [maNV=" + maNV + ", tenNV=" + tenNV + ", diaChi=" + diaChi + ", soDienThoai=" + soDienThoai
				+ ", trangThai=" + trangThai + ", ngaySinh=" + ngaySinh + ", vaiTro=" + vaiTro + ", email=" + email
				+ ", gioiTinh=" + gioiTinh + "]";
	}
}
