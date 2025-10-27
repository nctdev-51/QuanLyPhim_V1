package dao;

import java.util.ArrayList;

import entity.NhanVien;

public class QuanLyNhanVien_DAO {
	protected ArrayList<NhanVien> danhSachNV;
	private NhanVien nv; // Nhân viên đang đăng nhập hệ thống

	public QuanLyNhanVien_DAO() {
		this.danhSachNV = new ArrayList<NhanVien>();
		nv = new NhanVien();
	}

	public boolean add(NhanVien nhanVien) {
		if (nhanVien == null || danhSachNV.contains(nhanVien)) {
			return false;
		}
		NhanVien nv = timNhanVienTheoMaNV(nhanVien.getMaNV());
		if (nv != null)
			return false; // đã tồn tại mã nhanVien này
		danhSachNV.add(nhanVien);
		return true;
	}

	public NhanVien timNhanVienTheoMaNV(String maNV) {
		for (NhanVien nv : danhSachNV) {
			if (nv.getMaNV().equalsIgnoreCase(maNV))
				return nv;
		}
		return null;
	}

	public NhanVien getNhanVien() {
		return nv;
	}
}
