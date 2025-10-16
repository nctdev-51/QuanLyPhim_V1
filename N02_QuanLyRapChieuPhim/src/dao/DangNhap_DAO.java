package dao;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

import entity.NhanVien;
import entity.TaiKhoan;

public class DangNhap_DAO {
	
	ArrayList<TaiKhoan> dstk;
	
	public DangNhap_DAO() {
		dstk = new ArrayList<TaiKhoan>();
	}
	
	public TaiKhoan ktDangNhap(String taiKhoan, String matKhau) {
		Connection con = ConnectDB.getInstance().getConnection();
		PreparedStatement stmt = null;
		TaiKhoan tkhoan = null;
		int n = 0;
		try {
			stmt = con.prepareStatement("SELECT * FROM TAIKHOAN K JOIN NHANVIEN V ON K.maNV = V.maNV WHERE K.tenDangNhap = ? and K.matKhau = ? ");
			stmt.setString(1, taiKhoan);
			stmt.setString(2, matKhau);
			ResultSet rs = stmt.executeQuery();
			 if (rs.next()) {
				String maNV =  rs.getString(1);
				String tenDN = rs.getString(2);
				String mk = rs.getString(3);
				String tenNV = rs.getString(5);
				String diaChi = rs.getString(6);
				String sdt = rs.getString(7);
				String trangThai = rs.getString(8);
				LocalDate ngaysinh = rs.getDate(9).toLocalDate();
				String vaiTro = rs.getString(10);
				String email = rs.getString(11);
				String gioiTinh = rs.getString(12);
				NhanVien nvien = new NhanVien(maNV,tenNV,diaChi,sdt,trangThai,ngaysinh,vaiTro,email,gioiTinh);
		            tkhoan = new TaiKhoan(nvien,tenDN,mk);
		            return tkhoan;
			 }
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return tkhoan;
	}
	
}
