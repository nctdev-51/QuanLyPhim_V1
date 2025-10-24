package dao;

import java.util.ArrayList;
import java.util.List;
import entity.Phim;

public class QuanLyPhim_DAO {
    private ArrayList<Phim> danhSachPhim;

    public QuanLyPhim_DAO() {
        danhSachPhim = new ArrayList<>();
    }

    public boolean create(Phim newMovie) {
        if (newMovie == null)
            return false;
        if (timPhim(newMovie.getMaPhim()) != null)
            return false; 
        danhSachPhim.add(newMovie);
        return true;
    }

    public boolean suaPhim(Phim updatedMovie) {
        for (int i = 0; i < danhSachPhim.size(); i++) {
            if (danhSachPhim.get(i).getMaPhim().equalsIgnoreCase(updatedMovie.getMaPhim())) {
                danhSachPhim.set(i, updatedMovie);
                return true;
            }
        }
        return false;
    }

    public boolean xoaPhim(String maPhim) {
        Phim p = timPhim(maPhim);
        if (p != null) {
            danhSachPhim.remove(p);
            return true;
        }
        return false;
    }

    public Phim timPhim(String maPhim) {
        for (Phim p : danhSachPhim) {
            if (p.getMaPhim().equalsIgnoreCase(maPhim))
                return p;
        }
        return null;
    }
    
    public Phim getPhim(int index) {
        if (danhSachPhim == null || index < 0 || index >= danhSachPhim.size()) {
            return null;
        }
        return danhSachPhim.get(index);
    }

    public Phim search(String maPhim) {
        return timPhim(maPhim);
    }

    public List<Phim> getDanhSachPhim() {
        return danhSachPhim;
    }
}
