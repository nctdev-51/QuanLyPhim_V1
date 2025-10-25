package dao;

import java.util.ArrayList;
import entity.Ve;

public class QuanLyVe_DAO {
    private ArrayList<Ve> danhSachVe;

    public QuanLyVe_DAO() {
        this.danhSachVe = new ArrayList<>();
    }

    public boolean add(Ve ve) {
        if (ve == null || danhSachVe.contains(ve)) {
            return false;
        }
        Ve ticketFind = findVeByID(ve.getMaVe());
        if (ticketFind != null)
            return false; // đã tồn tại mã rap này
        danhSachVe.add(ve);
        return true;
    }
    public Ve findVeByID(String maVe) {
        for (Ve ve : danhSachVe) {
            if (ve.getMaVe().equalsIgnoreCase(maVe))
                return ve;
        }
        return null;
    }
    public static String taoMaVeTuDong() {
        long timeMillis = System.currentTimeMillis();
        return "VE" + timeMillis;
    }

    public ArrayList<Ve> getDanhSachVe() {
        return this.danhSachVe;
    }
}
