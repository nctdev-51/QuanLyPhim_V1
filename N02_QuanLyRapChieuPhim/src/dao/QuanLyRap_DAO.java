package dao;

import java.util.ArrayList;

import entity.Rap;
import entity.SuatChieu;

public class QuanLyRap_DAO {
    ArrayList<Rap> danhSachRap;
    public QuanLyRap_DAO(){
        danhSachRap = new ArrayList<>();
    }
    public boolean addNewRoom(Rap newRoom) {
        if (newRoom == null || danhSachRap.contains(newRoom)) {
            return false;
        }
        Rap rap = findRapByID(newRoom.getMaRap());
        if (rap != null)
            return false; // đã tồn tại mã rap này
        danhSachRap.add(newRoom);
        return true;
    }

    public Rap findRapByID(String maRap) {
        for (Rap rap : danhSachRap) {
            if (rap.getMaRap().equalsIgnoreCase(maRap))
                return rap;
        }
        return null;
    }

    public ArrayList<Rap> getDanhSachRap() {
        return danhSachRap;
    }

    public Rap getRapByIndex(int index) {
        if (index < 0 || index >= danhSachRap.size())
            return null;
        return danhSachRap.get(index);
    }
}
