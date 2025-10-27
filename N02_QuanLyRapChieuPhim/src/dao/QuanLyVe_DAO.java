package dao;

import java.util.ArrayList;
import java.util.Random;

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
            return false; // đã tồn tại mã Ve này
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
        int rand = new Random().nextInt(1000);
        return "VE" + timeMillis + String.format("%03d", rand);
    }

    public ArrayList<Ve> getDanhSachVe() {
        return this.danhSachVe;
    }
}
