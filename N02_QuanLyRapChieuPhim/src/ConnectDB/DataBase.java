package ConnectDB;

import java.time.LocalDate;
import java.time.LocalTime;

import dao.QuanLyGhe_DAO;
import dao.QuanLyKhachHang_DAO;
import dao.QuanLyPhim_DAO;
import dao.QuanLyRap_DAO;
import dao.QuanLySuatChieu_DAO;
import entity.*;

public class DataBase {

    // Database giả
    public static QuanLyPhim_DAO FakeMovieDB() {
        Phim phim1 = new Phim("phim01", "Mưa đỏ", "Quân đội nhân dân Việt Nam", "Hành động", 210, "Việt Nam");
        Phim phim2 = new Phim("phim02", "Tử chiến trên không", "Bộ công an Việt Nam", "Hành động", 240, "Việt Nam");
        Phim phim3 = new Phim("phim03", "Kimetsu no Yaiba - pháo đài vô cực", "Ufotable", "2D - Hành động", 240,
                "Nhật Bản");
        Phim phim4 = new Phim("phim04", "Cậu bé bóng cười", "NoHope", "Hài hước - Hành động", 180, "Trung quốc");
        QuanLyPhim_DAO movieManager = new QuanLyPhim_DAO();
        movieManager.add(phim1);
        movieManager.add(phim2);
        movieManager.add(phim3);
        movieManager.add(phim4);
        return movieManager;
    }

    public static QuanLySuatChieu_DAO FakeSuatChieuDB() {
        SuatChieu suat1 = new SuatChieu("suat01", "phim01", "phong01", LocalDate.of(2025, 10, 5), LocalTime.of(7, 20),
                75000.0);
        SuatChieu suat2 = new SuatChieu("suat02", "phim01", "phong02", LocalDate.of(2025, 11, 26),
                LocalTime.of(9, 30),
                80000.0);
        SuatChieu suat3 = new SuatChieu("suat03", "phim03", "phong03", LocalDate.of(2025, 11, 5), LocalTime.of(18, 00),
                90000.0);
        QuanLySuatChieu_DAO suatChieuManager = new QuanLySuatChieu_DAO();
        suatChieuManager.addNewSuatChieu(suat1);
        suatChieuManager.addNewSuatChieu(suat2);
        suatChieuManager.addNewSuatChieu(suat3);
        return suatChieuManager;
    }

    public static QuanLyRap_DAO FakeRapDB() {
        Rap phong1 = new Rap("phong01", 30, "Phòng 1");
        Rap phong2 = new Rap("phong02", 30, "Phòng 2");
        Rap phong3 = new Rap("phong03", 35, "Phòng 3");
        Rap phong4 = new Rap("phong04", 20, "Phòng 4");
        QuanLyRap_DAO roomManager = new QuanLyRap_DAO();
        roomManager.addNewRoom(phong1);
        roomManager.addNewRoom(phong2);
        roomManager.addNewRoom(phong3);
        roomManager.addNewRoom(phong4);
        return roomManager;
    }

    public static QuanLyGhe_DAO FakeGheDB() {
        QuanLyGhe_DAO chairManager = new QuanLyGhe_DAO();
        QuanLyRap_DAO rapManager = FakeRapDB();
        for (Rap rap : rapManager.getDanhSachRap()) {
            for (int i = 0; i < rap.getSoLuongGhe(); i++) {
                String maGhe = String.valueOf(i);
                Ghe ghe = new Ghe(maGhe, rap, false);
                chairManager.add(ghe);
            }
        }
        return chairManager;
    }

    public static QuanLyKhachHang_DAO FakeKhachHangDB() {
        KhachHang kh01 = new KhachHang("KH01", "Lê Minh Tân", "Nam", "0349099412",
                "66/12 nhiêu tứ, phường 7, quận phú nhuận, tphcm");
        KhachHang kh02 = new KhachHang("KH02", "Nguyễn Chí Tâm", "Nam", "0123456789", "hóc-môn, tphcm");
        KhachHang kh03 = new KhachHang("KH03", "Đỗ Thanh Tường", "Nam", "098765421",
                "21 le loi, phuong 14,quan go vap, tphcm");
        KhachHang kh04 = new KhachHang("KH04", "Trương Mỹ Lan", "Nữ", "08220530253",
                "le duan, phường 1, quận 1, tphcm");
        QuanLyKhachHang_DAO customerManager = new QuanLyKhachHang_DAO();
        customerManager.add(kh01);
        customerManager.add(kh02);
        customerManager.add(kh03);
        customerManager.add(kh04);
        return customerManager;
    }
}
