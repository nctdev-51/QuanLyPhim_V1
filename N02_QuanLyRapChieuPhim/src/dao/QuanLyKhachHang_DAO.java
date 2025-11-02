package dao;

import java.util.ArrayList;
import java.util.Random;
import java.sql.*;
import entity.KhachHang;
import ConnectDB.ConnectDB;
public class QuanLyKhachHang_DAO {
    private Connection conn;

    public QuanLyKhachHang_DAO() {
        this.conn = ConnectDB.getConnection();
    }

    public boolean add(KhachHang khachHang) {
        if(this.conn == null || khachHang == null)
            return false;
        PreparedStatement stmt = null;
        int n = 0;
        try {
            String sql = "Insert into KhachHang(maKH, hoTen, gioiTinh, soDT, diaChi) "
                    + "values(?,?,?,?,?)";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, khachHang.getMaKH());
            stmt.setString(2, khachHang.getHoTen());
            stmt.setString(3, khachHang.getGioiTinh());
            stmt.setString(4, khachHang.getSoDT());
            stmt.setString(5, khachHang.getDiaChi());
            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, stmt);
        }
        return n > 0;
    }

    public KhachHang findKhachHang(String maKhachHang) {
        if(this.conn == null || maKhachHang == null || maKhachHang.trim().isEmpty())
            return null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        KhachHang khachHang = null;
        try {
            String sql = "Select * from KhachHang where maKH = ?";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, maKhachHang);
            rs = stmt.executeQuery();
            if (rs.next()) {
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String soDT = rs.getString("soDT");
                String diaChi = rs.getString("diaChi");
                khachHang = new KhachHang(maKhachHang, hoTen, gioiTinh, soDT, diaChi);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt);
        }
        return khachHang;
    }

    public ArrayList<KhachHang> getDanhSachKhachHang() {
        if(this.conn == null)
            return null;
        ArrayList<KhachHang> danhSachKhachHang = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from KhachHang";
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String maKH = rs.getString("maKH");
                String hoTen = rs.getString("hoTen");
                String gioiTinh = rs.getString("gioiTinh");
                String soDT = rs.getString("soDT");
                String diaChi = rs.getString("diaChi");
                KhachHang khachHang = new KhachHang(maKH, hoTen, gioiTinh, soDT, diaChi);
                danhSachKhachHang.add(khachHang);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt);
        }
        return danhSachKhachHang;
    }

    public static String taoMaKHTuDong() {
        long timeMillis = System.currentTimeMillis();
        int rand = new Random().nextInt(1000);
        return "KH" + timeMillis + String.format("%03d", rand);
    }
    // ====== HÀM TIỆN ÍCH ======
    private void close(ResultSet rs, Statement stmt) {
        try {
            if (rs != null)
                rs.close();
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
