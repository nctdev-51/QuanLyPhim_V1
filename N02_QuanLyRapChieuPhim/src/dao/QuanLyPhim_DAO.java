package dao;

import java.util.ArrayList;

import entity.Phim;

public class QuanLyPhim_DAO {
    protected ArrayList<Phim> danhSachPhim;

    public QuanLyPhim_DAO() {
        danhSachPhim = new ArrayList<>();
    }

    public boolean add(Phim newMovie) {
        if (newMovie == null || danhSachPhim.contains(newMovie)) {
            return false;
        }
        Phim phim = timPhim(newMovie.getMaPhim());
        if (phim != null)
            return false; 
        danhSachPhim.add(newMovie);
        return true;
    }

    public Phim timPhim(String maPhim) {
        for (Phim phim : danhSachPhim) {
            if (phim.getMaPhim().equalsIgnoreCase(maPhim))
                return phim;
        }
        return null;
    }

    public ArrayList<Phim> getDanhSachPhim() {
        return danhSachPhim;
    }

    public Phim getPhim(int index) {
        if (index < 0 || index >= danhSachPhim.size())
            return null;
        return danhSachPhim.get(index);
    }
}
