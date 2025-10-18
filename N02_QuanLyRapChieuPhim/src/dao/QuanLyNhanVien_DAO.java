package dao;

import java.util.ArrayList;

import entity.NhanVien;

public class QuanLyNhanVien_DAO {
	protected ArrayList<NhanVien> danhSachNV;
	private NhanVien nv;
	
	public QuanLyNhanVien_DAO() {
		this.danhSachNV = new ArrayList<NhanVien>();
		nv = new NhanVien();
	}
}
