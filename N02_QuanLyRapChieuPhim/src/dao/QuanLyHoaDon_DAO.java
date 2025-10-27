package dao;

import java.util.ArrayList;
import java.util.Random;

import entity.HoaDon;

public class QuanLyHoaDon_DAO {
    private ArrayList<HoaDon> danhSachHoaDon;

    public QuanLyHoaDon_DAO() {
        this.danhSachHoaDon = new ArrayList<>();
    }

    public boolean add(HoaDon hoaDon) {
        if (hoaDon == null || danhSachHoaDon.contains(hoaDon)) {
            return false;
        }
        HoaDon hoaDonFind = findHoaDonByID(hoaDon.getMaHoaDon());
        if (hoaDonFind != null)
            return false; // đã tồn tại mã hoa don này
        danhSachHoaDon.add(hoaDon);
        return true;
    }

    public HoaDon findHoaDonByID(String maHoaDon) {
        for (HoaDon hoaDon : danhSachHoaDon) {
            if (hoaDon.getMaHoaDon().equalsIgnoreCase(maHoaDon))
                return hoaDon;
        }
        return null;
    }

    public static String taoMaHoaDonTuDong() {
        long timeMillis = System.currentTimeMillis();
        int rand = new Random().nextInt(1000);
        return "HD" + timeMillis + String.format("%03d", rand);
    }

    public ArrayList<HoaDon> getDanhSachHoaDon() {
        return this.danhSachHoaDon;
    }
}
