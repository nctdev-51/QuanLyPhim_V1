package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import dao.QuanLyCTHD_DAO;
import dao.QuanLyGhe_DAO;
import dao.QuanLyHoaDon_DAO;
import dao.QuanLyPhim_DAO;
import dao.QuanLyRap_DAO;
import dao.QuanLySuatChieu_DAO;
import dao.QuanLyVe_DAO;
import entity.ChiTietHoaDon;
import entity.Ghe;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phim;
import entity.Rap;
import entity.SuatChieu;
import entity.Ve;

public class ThongTinVeModal extends JFrame {
    private SuatChieu suatChieuDuocChon;
    private ArrayList<String> selectedChairs;
    private QuanLySuatChieu_DAO suatChieuManager;
    private QuanLyPhim_DAO movieManager;
    private QuanLyRap_DAO rapManager;
    private QuanLyGhe_DAO chairManager;
    private QuanLyVe_DAO ticketManager;
    private QuanLyHoaDon_DAO billManager;
    private Font fChonGhe = new Font("Arial", Font.BOLD, 16);
    private QuanLyBanVe parentform;
    private QuanLyCTHD_DAO cthdManager;
    private Dimension modalDimension = new Dimension(500, 600);
    private JButton btnInVe;
    private JButton btnHuy;
    private JButton btnThanhToan;
    private JButton btnXemHoaDon;
    private HoaDon hoaDon;
    private Object trangThai;
    private JLabel lblTrangThai;

    public ThongTinVeModal(KhachHang khachHang, SuatChieu suatChieuDuocChon,
            ArrayList<String> selectedChairs, QuanLyBanVe parentForm) {

        if (suatChieuDuocChon == null || selectedChairs == null || parentForm == null)
            return;
        this.suatChieuDuocChon = suatChieuDuocChon;
        this.selectedChairs = selectedChairs;
        this.parentform = parentForm;

        this.suatChieuManager = new QuanLySuatChieu_DAO();
        this.movieManager = new QuanLyPhim_DAO();
        this.rapManager = new QuanLyRap_DAO();
        this.chairManager = new QuanLyGhe_DAO();
        this.ticketManager = new QuanLyVe_DAO();
        this.billManager = new QuanLyHoaDon_DAO();
        this.cthdManager = new QuanLyCTHD_DAO();

        setSize(modalDimension);
        setLocationRelativeTo(this);
        setLayout(new BorderLayout());
        setTitle("Thông tin vé");

        JLabel lblTitle = new JLabel("THÔNG TIN VÉ XEM PHIM");
        Font fTitle = new Font("Arial", Font.BOLD, 20);
        lblTitle.setFont(fTitle);
        lblTitle.setForeground(Color.RED);
        JPanel pNorth = new JPanel();
        pNorth.add(lblTitle);

        add(pNorth, BorderLayout.NORTH);

        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBorder(BorderFactory.createTitledBorder("THÔNG TIN VÉ"));
        // mã vé, thời gian chiếu, tên phim, tên phòng chiếu, số ghế, số lượng vé, thời
        // gian đặt vé
        Ve ve = this.parentform.createTicket();
        if (ve == null)
            return;
        String maVe = ve.getMaVe();
        SuatChieu suatChieuFind = this.suatChieuManager.timSuatChieu(ve.getMaSuatChieu());
        if (suatChieuFind == null)
            return;
        Phim phim = this.movieManager.timPhimTheoMa(suatChieuFind.getMaPhim());
        String tenPhim = phim.getTenPhim();
        Rap rap = this.rapManager.findRapByID(suatChieuFind.getMaRap());
        String tenPhong = rap.getTenRap();
        String thoiGian = this.suatChieuDuocChon.getGioChieu().toString() + ", "
                + this.suatChieuDuocChon.getNgayChieu().toString();
        String soVe = Integer.toString(this.selectedChairs.size());

        String soGhe = String.join(", ", this.selectedChairs);
        String thoiGianDatVe = ve.getNgayBan().toString();
        this.trangThai = "Chưa thanh toán";

        JLabel lblMaVe = new JLabel("  Mã vé:                   " + maVe);
        JLabel lblTenPhim = new JLabel("  Tên phim:             " + tenPhim);
        JLabel lblTenPhong = new JLabel("  Phòng chiếu:        " + tenPhong);
        JLabel lblThoiGian = new JLabel("  Thời gian:             " + thoiGian);
        JLabel lblSoVe = new JLabel("  Số vé:                     " + soVe);
        JLabel lblSoGhe = new JLabel("  Số ghế:                   " + soGhe);
        JLabel lblThoiGianDatVe = new JLabel("  Thời gian đặt vé:  " + thoiGianDatVe);
        this.lblTrangThai = new JLabel("  Trạng thái:             " + trangThai);
        pCenter.add(Box.createVerticalStrut(10));
        pCenter.add(lblMaVe);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblTenPhim);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblTenPhong);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblThoiGian);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblSoVe);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblSoGhe);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblThoiGianDatVe);
        pCenter.add(Box.createVerticalStrut(5));
        pCenter.add(lblTrangThai);
        pCenter.add(Box.createVerticalStrut(10));

        add(pCenter, BorderLayout.CENTER);

        JPanel pSouth = new JPanel();

        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setFont(fChonGhe);
        btnThanhToan.setBackground(Color.GREEN);

        btnHuy = new JButton("Đóng");
        btnHuy.setFont(this.fChonGhe);
        btnHuy.setBackground(Color.RED);
        btnHuy.setForeground(Color.WHITE);

        btnInVe = new JButton("In vé");
        btnInVe.setFont(fChonGhe);
        btnInVe.setEnabled(false);

        btnXemHoaDon = new JButton("Xem hóa đơn");
        btnXemHoaDon.setFont(fChonGhe);
        btnXemHoaDon.setEnabled(false);

        pSouth.add(btnHuy);
        pSouth.add(btnThanhToan);
        pSouth.add(btnInVe);
        pSouth.add(btnXemHoaDon);

        add(pSouth, BorderLayout.SOUTH);

        btnHuy.addActionListener(e -> close());
        btnThanhToan.addActionListener(e -> thanhToan(khachHang));
        btnInVe.addActionListener(e -> InVe());
        btnXemHoaDon.addActionListener(e -> xemHoaDon(this.hoaDon));

        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setVisible(true);
    }

    private void close() {
        this.dispose();
        this.parentform.resetForm();
        this.hoaDon = null;
    }

    private void InVe() {
        JOptionPane.showMessageDialog(this, "Đã in vé thành công",
                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    private void thanhToan(KhachHang khachHang) {
        ArrayList<Ve> danhSachVeDaDat = new ArrayList<>();
        for (int i = 0; i < this.selectedChairs.size(); i++) {
            String tenGhe = this.selectedChairs.get(i);
            Ghe ghe = this.chairManager.TimGheTheoTen(tenGhe, this.suatChieuDuocChon.getMaRap());

            Ve ve = this.parentform.xuLyTaoVeTheoGhe(ghe);
            if (ve != null) {
                danhSachVeDaDat.add(ve);
                this.ticketManager.add(ve);
            }
            ghe.setTinhTrang(true);
            this.chairManager.capNhatTinhTrangGhe(ghe);
        }

        float giaVe = this.suatChieuDuocChon.getGiaVe();
        this.hoaDon = xuLyTaoHoaDon(khachHang, danhSachVeDaDat, giaVe);
        // Thêm hóa đơn
        this.billManager.add(this.hoaDon);
        xuLyTaoChiTietHoaDon(this.hoaDon, danhSachVeDaDat, giaVe);

        JOptionPane.showMessageDialog(this, "Thanh toán thành công !",
                "Hệ thống thông báo",
                JOptionPane.INFORMATION_MESSAGE);

        this.btnInVe.setEnabled(true);
        this.btnXemHoaDon.setEnabled(true);
        this.btnThanhToan.setEnabled(false);
        this.trangThai = "Đã thanh toán";
        this.lblTrangThai.setText("  Trạng thái:             " + this.trangThai);
    }

    private void xemHoaDon(HoaDon hoaDon) {
        if (this.hoaDon == null)
            return;
        new HoaDonModal(hoaDon, this.parentform);
    }

    private HoaDon xuLyTaoHoaDon(KhachHang khachHang, ArrayList<Ve> danhSachVeDaDat, float giaVe) {
        // Get NhanVien đang đăng nhập vào hệ thống - giả sử có mã là NV01
        NhanVien nhanVien = DangNhap.nhanVienDangNhap;
        int soLuongVe = danhSachVeDaDat.size();
        float tongTien = giaVe * soLuongVe;

        HoaDon hoaDon = new HoaDon(this.billManager.taoMaHoaDonTuDong(), LocalDate.now(), nhanVien, khachHang,
                soLuongVe, tongTien);
        return hoaDon;
    }

    private void xuLyTaoChiTietHoaDon(HoaDon hoaDon, ArrayList<Ve> danhSachVeDaDat, double giaVe) {
        for (Ve ve : danhSachVeDaDat) {
            ChiTietHoaDon cthd = new ChiTietHoaDon(hoaDon, ve, 1, giaVe);
            this.cthdManager.add(cthd);
        }
    }
}
