package ConnectDB;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import dao.QuanLyCTHD_DAO;
import dao.QuanLyGhe_DAO;
import dao.QuanLyHoaDon_DAO;
import dao.QuanLyKhachHang_DAO;
import dao.QuanLyNhanVien_DAO;
import dao.QuanLyPhim_DAO;
import dao.QuanLyRap_DAO;
import dao.QuanLySuatChieu_DAO;
import dao.QuanLyVe_DAO;
import entity.*;
import gui.QuanLyBanVe;
import gui.QuanLyCTHD;

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
        // thiết lập các ghế đã đặt trong Rap có mã phong01
        Rap phong1 = rapManager.findRapByID("phong01");
        // Danh sách ghế đã đặt
        String[] soGheDaDat1 = { "5", "6", "7", "12" };
        for (String soGhe : soGheDaDat1) {
            Ghe ghe = chairManager.TimGheTheoMaRap(soGhe, phong1.getMaRap());
            if (ghe != null)
                ghe.setTinhTrang(true);
        }

        // thiết lập các ghế đã đặt trong Rap có mã phong02
        Rap phong2 = rapManager.findRapByID("phong02");
        // Danh sách ghế đã đặt
        String[] soGheDaDat2 = { "10", "20" };
        for (String soGhe : soGheDaDat2) {
            Ghe ghe = chairManager.TimGheTheoMaRap(soGhe, phong2.getMaRap());
            if (ghe != null)
                ghe.setTinhTrang(true);
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

    public static QuanLyNhanVien_DAO FakeNhanVienDB() {
        NhanVien nv01 = new NhanVien("NV01", "Lê Minh Tân", "Phú Nhuận, TPHCM", "0349099412",
                LocalDate.of(2005, 11, 5), "tan2005tg@gmail.com", "Nam");
        NhanVien nv02 = new NhanVien("NV02", "Đỗ Thanh Tường", "Gò Vấp, TPHCM", "0952050023",
                LocalDate.of(2005, 9, 17), "dothanhtuongg@gmail.com", "Không xác định");
        NhanVien nv03 = new NhanVien("NV03", "Nguyễn Chí Tâm", "Hóc-môn, TPHCM", "0356344576",
                LocalDate.of(2005, 2, 14), "tan2005tg@gmail.com", "Nam");
        QuanLyNhanVien_DAO employeeManager = new QuanLyNhanVien_DAO();
        employeeManager.add(nv01);
        employeeManager.add(nv02);
        employeeManager.add(nv03);
        return employeeManager;
    }

    public static QuanLyHoaDon_DAO FakeHoaDonDB() {
        QuanLyKhachHang_DAO customerManager = FakeKhachHangDB();
        QuanLyNhanVien_DAO employeeManager = FakeNhanVienDB();
        HoaDon hd01 = new HoaDon("HD01", LocalDate.of(2005, 2, 14), employeeManager.timNhanVienTheoMaNV("NV01"),
                customerManager.findKhachHang("KH04"), 4, 120000);
        HoaDon hd02 = new HoaDon("HD02", LocalDate.of(2005, 9, 2), employeeManager.timNhanVienTheoMaNV("NV02"),
                customerManager.findKhachHang("KH01"), 2, 90000);
        QuanLyHoaDon_DAO billManager = new QuanLyHoaDon_DAO();
        billManager.add(hd01);
        billManager.add(hd02);
        return billManager;
    }

    public static QuanLyVe_DAO FakeVeDB() {
        QuanLyVe_DAO ticketManager = new QuanLyVe_DAO();
        QuanLyGhe_DAO chairManager = FakeGheDB();
        QuanLyRap_DAO rapManager = FakeRapDB();
        // các ghế đã đặt trong Rap có mã phong01
        Rap phong1 = rapManager.findRapByID("phong01");
        // Danh sách ghế đã đặt
        String[] soGheDaDat1 = { "5", "6", "7", "12" };
        for (String soGhe : soGheDaDat1) {
            Ghe ghe = chairManager.TimGheTheoMaRap(soGhe, phong1.getMaRap());
            if (ghe != null && ghe.isDaDat()) {
                Ve ve = new Ve(ticketManager.taoMaVeTuDong(), ghe, LocalDate.of(2005, 2, 14), "suat01", true);
                ticketManager.add(ve);
            }
        }

        // thiết lập các ghế đã đặt trong Rap có mã phong02
        Rap phong2 = rapManager.findRapByID("phong02");
        // Danh sách ghế đã đặt
        String[] soGheDaDat2 = { "10", "20" };
        for (String soGhe : soGheDaDat2) {
            Ghe ghe = chairManager.TimGheTheoMaRap(soGhe, phong2.getMaRap());
            if (ghe != null && ghe.isDaDat()) {
                Ve ve = new Ve(ticketManager.taoMaVeTuDong(), ghe, LocalDate.of(2005, 9, 2), "suat01", true);
                ticketManager.add(ve);
            }
        }
        return ticketManager;
    }

    public static QuanLyCTHD_DAO FakeCTHDDB() {
        QuanLyCTHD_DAO cthdManager = new QuanLyCTHD_DAO();
        QuanLyVe_DAO ticketManager = FakeVeDB();
        QuanLyHoaDon_DAO billManager = FakeHoaDonDB();
        ArrayList<HoaDon> danhSachHoaDon = billManager.getDanhSachHoaDon();
        ArrayList<Ve> danhSachVe = ticketManager.getDanhSachVe();
        ChiTietHoaDon cthd01 = new ChiTietHoaDon(danhSachHoaDon.get(0), danhSachVe.get(0), 1, 75000.0);
        ChiTietHoaDon cthd02 = new ChiTietHoaDon(danhSachHoaDon.get(0), danhSachVe.get(1), 1, 75000.0);
        ChiTietHoaDon cthd03 = new ChiTietHoaDon(danhSachHoaDon.get(0), danhSachVe.get(2), 1, 75000.0);
        ChiTietHoaDon cthd04 = new ChiTietHoaDon(danhSachHoaDon.get(0), danhSachVe.get(3), 1, 75000.0);
        ChiTietHoaDon cthd05 = new ChiTietHoaDon(danhSachHoaDon.get(1), danhSachVe.get(4), 1, 80000.0);
        ChiTietHoaDon cthd06 = new ChiTietHoaDon(danhSachHoaDon.get(1), danhSachVe.get(5), 1, 80000.0);
        cthdManager.add(cthd01);
        cthdManager.add(cthd02);
        cthdManager.add(cthd03);
        cthdManager.add(cthd04);
        cthdManager.add(cthd05);
        cthdManager.add(cthd06);
        return cthdManager;
    }

}
