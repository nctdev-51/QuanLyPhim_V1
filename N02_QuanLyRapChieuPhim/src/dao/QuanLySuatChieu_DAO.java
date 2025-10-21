package dao;

import java.util.ArrayList;
import entity.SuatChieu;

public class QuanLySuatChieu_DAO {
    private ArrayList<SuatChieu> danhSachSuatChieu;

    public QuanLySuatChieu_DAO() {
        danhSachSuatChieu = new ArrayList<>();
    }

    public boolean addNewSuatChieu(SuatChieu suatChieu) {
        if (suatChieu == null) 
            return false;
        
        if (timSuatChieu(suatChieu.getMaSuatChieu()) != null)
            return false;

        danhSachSuatChieu.add(suatChieu);
        return true;
    }
    
    public SuatChieu timSuatChieu(String maSuatChieu) {
        if (maSuatChieu == null || maSuatChieu.trim().isEmpty())
            return null;

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

    public ArrayList<SuatChieu> getSuatChieuTheoPhim(String maPhim) { 
        ArrayList<SuatChieu> list = new ArrayList<>();
        if (maPhim == null || maPhim.trim().isEmpty())
            return list;

        for (SuatChieu sc : danhSachSuatChieu) {
            if (sc.getMaPhim().equalsIgnoreCase(maPhim))
                list.add(sc);
        }
        return list;
    }
    
    public boolean suaSuatChieu(SuatChieu suatChieu) {
        if (suatChieu == null)
            return false;

        for (int i = 0; i < danhSachSuatChieu.size(); i++) {
            SuatChieu sc = danhSachSuatChieu.get(i);
            if (sc.getMaSuatChieu().equalsIgnoreCase(suatChieu.getMaSuatChieu())) {
                danhSachSuatChieu.set(i, suatChieu);
                return true;
            }
        }
        return false;
    }

    public boolean xoaSuatChieu(String maSuatChieu) {
        if (maSuatChieu == null || maSuatChieu.trim().isEmpty())
            return false;

        return danhSachSuatChieu.removeIf(
            sc -> sc.getMaSuatChieu().equalsIgnoreCase(maSuatChieu)
        );
    }
}
