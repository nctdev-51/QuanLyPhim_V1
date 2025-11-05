package dao;

import java.util.ArrayList;
import java.util.Random;

import ConnectDB.ConnectDB;

import java.sql.*;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;

public class QuanLyHoaDon_DAO {
    private Connection conn;

    public QuanLyHoaDon_DAO() {
        this.conn = ConnectDB.getConnection();
    }

    public boolean add(HoaDon hoaDon) {
        if (this.conn == null || hoaDon == null)
            return false;
        PreparedStatement stmt = null;
        int n = 0;
        try {
            String sql = "Insert into HoaDon(maHoaDon, ngayLap, maNV, maKH, soLuongVe, tongTien) "
                    + "values(?,?,?,?,?,?)";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, hoaDon.getMaHoaDon());
            stmt.setDate(2, Date.valueOf(hoaDon.getNgayLap()));
            stmt.setString(3, hoaDon.getNhanVien().getMaNV());
            stmt.setString(4, hoaDon.getKhachHang().getMaKH());
            stmt.setInt(5, hoaDon.getSoLuongVe());
            stmt.setFloat(6, hoaDon.getTongTien());
            n = stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(null, stmt);
        }
        return n > 0;
    }

    public HoaDon findHoaDonByID(String maHoaDon) {
        if (this.conn == null || maHoaDon == null || maHoaDon.trim().isEmpty())
            return null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        HoaDon hoaDon = null;
        try {
            String sql = "Select * from HoaDon where maHoaDon = ?";
            stmt = this.conn.prepareStatement(sql);
            stmt.setString(1, maHoaDon);
            rs = stmt.executeQuery();
            if (rs.next()) {
                Date ngayLapDate = rs.getDate("ngayLap");
                String maNV = rs.getString("maNV");
                String maKH = rs.getString("maKH");
                int soLuongVe = rs.getInt("soLuongVe");
                float tongTien = rs.getFloat("tongTien");

                QuanLyNhanVien_DAO nhanVienDAO = new QuanLyNhanVien_DAO();
                QuanLyKhachHang_DAO khachHangDAO = new QuanLyKhachHang_DAO();
                NhanVien nv = nhanVienDAO.timTheoMa(maNV);
                KhachHang kh = khachHangDAO.findKhachHang(maKH);
                hoaDon = new HoaDon(maHoaDon, ngayLapDate.toLocalDate(), nv, kh, soLuongVe, tongTien);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt);
        }
        return hoaDon;
    }

    public static String taoMaHoaDonTuDong() {
        long timeMillis = System.currentTimeMillis();
        int rand = new Random().nextInt(1000);
        return "HD" + timeMillis + String.format("%03d", rand);
    }

    public ArrayList<HoaDon> getDanhSachHoaDon() {
        if (this.conn == null)
            return null;
        ArrayList<HoaDon> danhSachHoaDon = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        try {
            String sql = "Select * from HoaDon";
            stmt = this.conn.prepareStatement(sql);
            rs = stmt.executeQuery();
            while (rs.next()) {
                String maHoaDon = rs.getString("maHoaDon");
                Date ngayLapDate = rs.getDate("ngayLap");
                String maNV = rs.getString("maNV");
                String maKH = rs.getString("maKH");
                int soLuongVe = rs.getInt("soLuongVe");
                float tongTien = rs.getFloat("tongTien");

                QuanLyNhanVien_DAO nhanVienDAO = new QuanLyNhanVien_DAO();
                QuanLyKhachHang_DAO khachHangDAO = new QuanLyKhachHang_DAO();
                NhanVien nv = nhanVienDAO.timTheoMa(maNV);
                KhachHang kh = khachHangDAO.findKhachHang(maKH);
                HoaDon hoaDon = new HoaDon(maHoaDon, ngayLapDate.toLocalDate(), nv, kh, soLuongVe, tongTien);
                danhSachHoaDon.add(hoaDon);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(rs, stmt);
        }
        return danhSachHoaDon;
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
