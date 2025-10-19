package dao;

import java.util.ArrayList;

import entity.Ghe;
import entity.Rap;

public class QuanLyGhe_DAO {
    private ArrayList<Ghe> danhSachGhe;

    public QuanLyGhe_DAO() {
        this.danhSachGhe = new ArrayList<>();
    }

    public boolean add(Ghe gheMoi) {
        if (gheMoi == null || danhSachGhe.contains(gheMoi)) {
            return false;
        }
        Ghe ghe = TimGhe(gheMoi.getMaGhe(), gheMoi.getRap());
        if (ghe != null)
            return false; // đã tồn tại mã ghế ở rạp này
        danhSachGhe.add(gheMoi);
        return true;
    }
    public Ghe TimGhe(String maGhe, Rap rap) {
        for (Ghe ghe : danhSachGhe) {
            if (ghe.getMaGhe().equals(maGhe) && ghe.getRap().getMaRap().equals(rap.getMaRap()))
                return ghe;
        }
        return null;
    }
    public Ghe TimGheTheoMaRap(String maGhe, String maRap) {
        for (Ghe ghe : danhSachGhe) {
            if (ghe.getMaGhe().equals(maGhe) && ghe.getRap().getMaRap().equals(maRap))
                return ghe;
        }
        return null;
    }
    public ArrayList<Ghe> getDanhSachTatCaGhe() {
        return danhSachGhe;
    }

    public Ghe getChairByIndex(int index) {
        if (index < 0 || index >= danhSachGhe.size())
            return null;
        return danhSachGhe.get(index);
    }

    public ArrayList<Ghe> getDanhSachGheTheoRap(Rap rap){
        ArrayList<Ghe> danhSachGhe = new ArrayList<>(); 
        for(Ghe ghe : this.danhSachGhe){
            if(ghe.getRap().getMaRap().equals(rap.getMaRap())){
                danhSachGhe.add(ghe);
            }
        }
        return danhSachGhe;
    }

}
