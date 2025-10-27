package dao;

import java.util.ArrayList;
import java.util.Random;

import entity.KhachHang;

public class QuanLyKhachHang_DAO {
    private ArrayList<KhachHang> danhSachKhachHang;

    public QuanLyKhachHang_DAO() {
        danhSachKhachHang = new ArrayList<>();
    }

    public boolean add(KhachHang khachHang) {
        if (khachHang == null || danhSachKhachHang.contains(khachHang)) {
            return false;
        }
        KhachHang kh = findKhachHang(khachHang.getMaKH());
        if (kh != null)
            return false; // đã tồn tại mã khachHang này
        danhSachKhachHang.add(khachHang);
        return true;
    }

    public KhachHang findKhachHang(String maKhachHang) {
        for (KhachHang khachHang : danhSachKhachHang) {
            if (khachHang.getMaKH().equalsIgnoreCase(maKhachHang))
                return khachHang;
        }
        return null;
    }

    public ArrayList<KhachHang> getDanhSachKhachHang() {
        return danhSachKhachHang;
    }

    public KhachHang getKhachHangByIndex(int index) {
        if (index < 0 || index >= danhSachKhachHang.size())
            return null;
        return danhSachKhachHang.get(index);
    }

    public static String taoMaKHTuDong() {
        long timeMillis = System.currentTimeMillis();
        int rand = new Random().nextInt(1000);
        return "KH" + timeMillis + String.format("%03d", rand);
    }
}
