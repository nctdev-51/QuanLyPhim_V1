package dao;

import java.util.ArrayList;

import ConnectDB.DataBase;
import entity.SuatChieu;

public class QuanLySuatChieu_DAO {
    private ArrayList<SuatChieu> danhSachSuatChieu;
    private QuanLySuatChieu_DAO data;

    public QuanLySuatChieu_DAO() {
        danhSachSuatChieu = new ArrayList<>();
        data = this;

        System.out.println("Fetched " + danhSachSuatChieu.size() + " SuatChieu from database.");
    }

    // lấy dữ liệu từ database

    public boolean addNewSuatChieu(SuatChieu suatChieu) {
        if (suatChieu == null || danhSachSuatChieu.contains(suatChieu)) {
            return false;
        }
        SuatChieu suatChieuById = findSuatChieuById(suatChieu.getMaSuatChieu());
        if (suatChieuById != null)
            return false; // đã tồn tại mã suất chiếu này
        danhSachSuatChieu.add(suatChieu);
        return true;
    }

    public SuatChieu findSuatChieuById(String maSuatChieu) {
        for (SuatChieu suat : danhSachSuatChieu) {
            if (suat.getMaSuatChieu().equalsIgnoreCase(maSuatChieu))
                return suat;
        }
        return null;
    }

    public ArrayList<SuatChieu> getDanhSachSuatChieu() {
        return danhSachSuatChieu;
    }

    public SuatChieu getSuatChieuByIndex(int index) {
        if (index < 0 || index >= danhSachSuatChieu.size())
            return null;
        return danhSachSuatChieu.get(index);
    }

    // About this method: lấy tất cả suất chiếu của mã phim trong mảng
    public ArrayList<SuatChieu> getSuatChieuPhim(String maPhim) {
        ArrayList<SuatChieu> suatChieus = new ArrayList<>();
        for (SuatChieu suatChieu : danhSachSuatChieu) {
            if (suatChieu.getMaPhim().equals(maPhim)) {
                suatChieus.add(suatChieu);
            }
        }
        return suatChieus;
    }

    // Xóa suất chiếu
    public boolean removeSuatChieu(SuatChieu suatChieu) {
        return danhSachSuatChieu.remove(suatChieu);
    }

    // cập nhật suất chiếu
    public boolean updateSuatChieu(SuatChieu suatChieu) {
        int index = danhSachSuatChieu.indexOf(suatChieu);
        if (index == -1) {
            return false; // suất chiếu không tồn tại
        }
        danhSachSuatChieu.set(index, suatChieu);
        return true;
    }

    // Lưu vào database
    public boolean saveToDatabase() {
        // Giả lập lưu vào database
        for (SuatChieu suatChieu : danhSachSuatChieu) {
            System.out.println("Lưu suất chiếu vào database: " + suatChieu);
        }
        return true;
    }
}