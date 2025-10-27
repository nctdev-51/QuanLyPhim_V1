package dao;

import java.util.ArrayList;

import entity.ChiTietHoaDon;

public class QuanLyCTHD_DAO {
    private ArrayList<ChiTietHoaDon> danhSachCTHD;

    public QuanLyCTHD_DAO() {
        this.danhSachCTHD = new ArrayList<>();
    }

    public void add(ChiTietHoaDon cthd) {
        this.danhSachCTHD.add(cthd);
    }

    // Lấy danh sách CTHD theo mã HoaDon
    public ArrayList<ChiTietHoaDon> timCTHDTheoMaHoaDon(String maHoaDon) {
        ArrayList<ChiTietHoaDon> list = new ArrayList<>();
        for (ChiTietHoaDon cthd : this.danhSachCTHD) {
            String maHD = cthd.getHoaDon().getMaHoaDon();
            if (maHoaDon.equalsIgnoreCase(maHD)) {
                list.add(cthd);
            }
        }
        return list;
    }

    public ArrayList<ChiTietHoaDon> getDanhSachCTHD() {
        return this.danhSachCTHD;
    }
}
