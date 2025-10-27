package gui;

import javax.swing.*;
import javax.swing.border.Border;

import ConnectDB.DataBase;
import dao.QuanLyCTHD_DAO;
import dao.QuanLyGhe_DAO;
import dao.QuanLyHoaDon_DAO;
import dao.QuanLyKhachHang_DAO;
import dao.QuanLyNhanVien_DAO;
import dao.QuanLyPhim_DAO;
import dao.QuanLyRap_DAO;
import dao.QuanLySuatChieu_DAO;
import dao.QuanLyVe_DAO;

import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;

import entity.Ve;
import entity.SuatChieu;
import entity.Rap;
import entity.ChiTietHoaDon;
import entity.Ghe;
import entity.HoaDon;
import entity.KhachHang;
import entity.NhanVien;
import entity.Phim;

public class QuanLyBanVe extends JPanel {
    private JComboBox<String> cbPhim, cbPhong, cbSuatChieu, cbGioiTinh;
    private JButton btnChonGhe, btnDatVe, btnXoaChon;
    private JTextField txtHoTen, txtSDT, txtDiaChi;
    private JTextField txtTenPhim, txtTenPhong, txtThoiLuong, txtTheLoai, txtSuatChieu, txtSoGhe;
    private SuatChieu suatChieuDuocChon;
    private ArrayList<String> roomIDSelectList;
    private ArrayList<String> showtimeSelectList;
    private ArrayList<String> selectedChairs;
    private QuanLyPhim_DAO movieManager;
    private QuanLySuatChieu_DAO suatChieuManager;
    private QuanLyRap_DAO rapManager;
    private QuanLyGhe_DAO chairManager;
    private QuanLyKhachHang_DAO customerManager;
    private QuanLyVe_DAO ticketManager;
    private Dimension modelDimension = new Dimension(500, 600);
    private QuanLyCTHD_DAO cthdManager;
    private QuanLyHoaDon_DAO billManager;
    private QuanLyNhanVien_DAO employeeManager;
    private Font fChonGhe;

    public QuanLyBanVe() {
        setLayout(new BorderLayout(10, 10));
        // Load database
        LoadMovieManager();
        LoadSuatChieuManager();
        LoadRapManager();
        LoadChairManager();
        LoadCustomerManager();
        LoadTicketManager();
        LoadBillManager();
        LoadEmployeeManager();
        LoadCthdManager();
        // ===== NORTH: Tiêu đề =====
        JLabel lblTitle = new JLabel("QUẢN LÝ BÁN VÉ", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.RED);
        add(lblTitle, BorderLayout.NORTH);

        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));

        cbPhim = new JComboBox<>(new String[] { "---Chọn phim---" });
        for (Phim phim : movieManager.getDanhSachPhim()) {
            cbPhim.addItem(phim.getTenPhim());
        }
        cbPhong = new JComboBox<>(new String[] { "---Chọn phòng---" });
        cbSuatChieu = new JComboBox<>(new String[] { "---Chọn suất chiếu---" });
        cbPhong.setEnabled(false);
        cbSuatChieu.setEnabled(false);

        JPanel pChonPhim = new JPanel();
        pChonPhim.setLayout(new BoxLayout(pChonPhim, BoxLayout.Y_AXIS));
        pChonPhim.setBorder(BorderFactory.createTitledBorder("CHỌN VÉ XEM PHIM"));

        JPanel pPhim = new JPanel();
        pPhim.setLayout(new BoxLayout(pPhim, BoxLayout.X_AXIS));
        pPhim.add(new JLabel("     Chọn phim:              "));
        pPhim.add(cbPhim);
        pChonPhim.add(Box.createVerticalStrut(20));
        pChonPhim.add(pPhim);

        JPanel pPhong = new JPanel();
        pPhong.setLayout(new BoxLayout(pPhong, BoxLayout.X_AXIS));
        pPhong.add(new JLabel("     Chọn phòng:            "));
        pPhong.add(cbPhong);
        pChonPhim.add(Box.createVerticalStrut(20));
        pChonPhim.add(pPhong);

        JPanel pSuat = new JPanel();
        pSuat.setLayout(new BoxLayout(pSuat, BoxLayout.X_AXIS));
        pSuat.add(new JLabel("     Chọn suất chiếu:    "));
        pSuat.add(cbSuatChieu);
        this.fChonGhe = new Font("Arial", Font.BOLD, 16);
        btnChonGhe = new JButton("Chọn ghế");
        btnChonGhe.setEnabled(false);
        btnChonGhe.setBackground(Color.RED);
        btnChonGhe.setForeground(Color.WHITE);
        btnChonGhe.setFont(fChonGhe);

        pSuat.add(Box.createHorizontalStrut(5));
        pSuat.add(btnChonGhe);
        pSuat.add(Box.createHorizontalStrut(30));

        pChonPhim.add(Box.createVerticalStrut(20));
        pChonPhim.add(pSuat);
        pChonPhim.add(Box.createVerticalStrut(20));

        pCenter.add(pChonPhim);

        txtHoTen = new JTextField();

        txtDiaChi = new JTextField();

        txtSDT = new JTextField();

        cbGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });

        txtSoGhe = new JTextField();
        txtSoGhe.setEditable(false);

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBorder(BorderFactory.createTitledBorder("THÔNG TIN KHÁCH HÀNG"));

        JPanel pRow1 = new JPanel();
        pRow1.setLayout(new BoxLayout(pRow1, BoxLayout.X_AXIS));
        pRow1.add(new JLabel("     Họ tên:               "));
        pRow1.add(txtHoTen);

        JPanel pRow2 = new JPanel();
        pRow2.setLayout(new BoxLayout(pRow2, BoxLayout.X_AXIS));
        pRow2.add(new JLabel("     Số điện thoại:   "));
        pRow2.add(txtSDT);

        JPanel pRow3 = new JPanel();
        pRow3.setLayout(new BoxLayout(pRow3, BoxLayout.X_AXIS));
        pRow3.add(new JLabel("     Địa chỉ:              "));
        pRow3.add(txtDiaChi);

        JPanel pRow4 = new JPanel();
        pRow4.setLayout(new BoxLayout(pRow4, BoxLayout.X_AXIS));
        pRow4.add(new JLabel("     Giới tính:            "));
        pRow4.add(cbGioiTinh);

        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(pRow1);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(pRow2);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(pRow3);
        infoPanel.add(Box.createVerticalStrut(20));
        infoPanel.add(pRow4);
        infoPanel.add(Box.createVerticalStrut(20));

        txtTenPhim = new JTextField();
        txtTenPhim.setEditable(false);

        txtTenPhong = new JTextField();
        txtTenPhong.setEditable(false);

        txtThoiLuong = new JTextField();
        txtThoiLuong.setEditable(false);

        txtTheLoai = new JTextField();
        txtTheLoai.setEditable(false);

        txtSuatChieu = new JTextField();
        txtSuatChieu.setEditable(false);
        JPanel pInfoMovie = new JPanel();
        pInfoMovie.setLayout(new BoxLayout(pInfoMovie, BoxLayout.Y_AXIS));
        pInfoMovie.setBorder(BorderFactory.createTitledBorder("THÔNG TIN VÉ XEM PHIM"));

        JPanel pTenPhim = new JPanel();
        pTenPhim.setLayout(new BoxLayout(pTenPhim, BoxLayout.X_AXIS));
        pTenPhim.add(new JLabel("     Tên phim:      "));
        pTenPhim.add(txtTenPhim);

        JPanel pTenPhong = new JPanel();
        pTenPhong.setLayout(new BoxLayout(pTenPhong, BoxLayout.X_AXIS));
        pTenPhong.add(new JLabel("     Phòng:           "));
        pTenPhong.add(txtTenPhong);

        JPanel pThoiLuong = new JPanel();
        pThoiLuong.setLayout(new BoxLayout(pThoiLuong, BoxLayout.X_AXIS));
        pThoiLuong.add(new JLabel("     Thời lượng:  "));
        pThoiLuong.add(txtThoiLuong);

        JPanel pTheLoai = new JPanel();
        pTheLoai.setLayout(new BoxLayout(pTheLoai, BoxLayout.X_AXIS));
        pTheLoai.add(new JLabel("     Thể loại:         "));
        pTheLoai.add(txtTheLoai);

        JPanel pSuatChieu = new JPanel();
        pSuatChieu.setLayout(new BoxLayout(pSuatChieu, BoxLayout.X_AXIS));
        pSuatChieu.add(new JLabel("     Suất chiếu:    "));
        pSuatChieu.add(txtSuatChieu);

        JPanel pSoGhe = new JPanel();
        pSoGhe.setLayout(new BoxLayout(pSoGhe, BoxLayout.X_AXIS));
        pSoGhe.add(new JLabel("     Ghế:                 "));
        pSoGhe.add(txtSoGhe);

        pInfoMovie.add(Box.createVerticalStrut(20));
        pInfoMovie.add(pTenPhim);
        pInfoMovie.add(Box.createVerticalStrut(20));
        pInfoMovie.add(pTenPhong);
        pInfoMovie.add(Box.createVerticalStrut(20));
        pInfoMovie.add(pThoiLuong);
        pInfoMovie.add(Box.createVerticalStrut(20));
        pInfoMovie.add(pTheLoai);
        pInfoMovie.add(Box.createVerticalStrut(20));
        pInfoMovie.add(pSuatChieu);
        pInfoMovie.add(Box.createVerticalStrut(20));
        pInfoMovie.add(pSoGhe);
        pInfoMovie.add(Box.createVerticalStrut(20));

        JPanel pInfoBorder = new JPanel(new BorderLayout());
        pInfoBorder.add(infoPanel, BorderLayout.NORTH);
        pInfoBorder.add(Box.createVerticalStrut(5), BorderLayout.CENTER);
        pInfoBorder.add(pInfoMovie, BorderLayout.SOUTH);

        pCenter.add(pInfoBorder);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnXoaChon = new JButton("Xóa lựa chọn");
        btnXoaChon.setFont(fChonGhe);
        btnDatVe = new JButton("Xác nhận đặt vé");
        btnDatVe.setBackground(Color.green);
        btnDatVe.setEnabled(false);
        btnDatVe.setFont(fChonGhe);
        btnPanel.add(btnXoaChon);
        btnPanel.add(btnDatVe);
        pCenter.add(btnPanel);

        add(pCenter, BorderLayout.CENTER);

        btnXoaChon.addActionListener(e -> resetForm());
        btnDatVe.addActionListener(e -> acceptTicket());
        btnChonGhe.addActionListener(e -> selectChair());
        cbPhim.addActionListener(e -> CapNhatThongTinPhim());
        cbPhong.addActionListener(e -> CapNhatPhong());
        cbSuatChieu.addActionListener(e -> CapNhatSuatChieu());
    }

    private void resetForm() {
        cbPhim.setSelectedIndex(0);
        cbPhong.setSelectedIndex(0);
        cbSuatChieu.setSelectedIndex(0);
        cbGioiTinh.setSelectedIndex(0);

        cbPhong.setEnabled(false);
        cbSuatChieu.setEnabled(false);
        btnChonGhe.setEnabled(false);
        btnDatVe.setEnabled(false);

        txtDiaChi.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");

        deleteTextMovieInfo();
        this.suatChieuDuocChon = null;
        this.selectedChairs.clear();
    }

    private void acceptTicket() {
        String hoten = txtHoTen.getText().trim();
        String sdt = txtSDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String gioiTinh = cbGioiTinh.getSelectedItem().toString();
        if (hoten.equals("") || sdt.equals("") || diaChi.equals("")) {
            JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin khách hàng", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        String regexHoten = "^[A-Z][a-z]+(\s[A-Z][a-z]+)*\s[A-Z][a-z]+$"; // Gồm Họ và tên - chữ cái đầu phải viết hoa
        String regexSDT = "^[0-9]{10}$"; // số điện thoại - có 10 số
        String regexDiaChi = "^[A-Za-z0-9/,\s]+$";
        if (!hoten.matches(regexHoten)) {
            JOptionPane.showMessageDialog(this, "Gồm phần họ và tên, chữ cái đầu phải viết hoa", "Lỗi cú pháp Họ tên",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!sdt.matches(regexSDT)) {
            JOptionPane.showMessageDialog(this, "số điện thoại - có 10 số", "Lỗi cú pháp số điện thoại",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!diaChi.matches(regexDiaChi)) {
            JOptionPane.showMessageDialog(this, "Địa chỉ bao gồm các kí tự chữ, kí tự số, dấu phẩy, khoảng trắng",
                    "Lỗi cú pháp địa chỉ", JOptionPane.ERROR_MESSAGE);
            return;
        }
        String maKH = this.customerManager.taoMaKHTuDong();
        KhachHang khachHang = new KhachHang(maKH, hoten, gioiTinh, sdt, diaChi);
        // lưu khách hàng
        customerManager.add(khachHang);

        // chuyển danh sách ghế đã chọn sang tình trạng đã đặt
        if (this.selectedChairs == null || this.suatChieuDuocChon == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn ghế !", "Lỗi đặt vé", JOptionPane.WARNING_MESSAGE);
            return;
        }
        showTicketPanel(khachHang);

    }

    private void showTicketPanel(KhachHang khachHang) {
        if (this.suatChieuDuocChon == null || this.selectedChairs == null)
            return;
        JFrame ticketFrame = new JFrame();
        ticketFrame.setSize(this.modelDimension);
        ticketFrame.setLocationRelativeTo(this);
        ticketFrame.setLayout(new BorderLayout());
        ticketFrame.setTitle("Thông tin vé");

        JLabel lblTitle = new JLabel("KIỂM TRA THÔNG TIN VÉ");
        Font fTitle = new Font("Arial", Font.BOLD, 20);
        lblTitle.setFont(fTitle);
        lblTitle.setForeground(Color.RED);
        JPanel pNorth = new JPanel();
        pNorth.add(lblTitle);

        ticketFrame.add(pNorth, BorderLayout.NORTH);

        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBorder(BorderFactory.createTitledBorder("THÔNG TIN VÉ"));
        // mã vé, thời gian chiếu, tên phim, tên phòng chiếu, số ghế, số lượng vé, thời
        // gian đặt vé
        Ve ve = createTicket();
        if (ve == null)
            return;
        String maVe = ve.getMaVe();
        SuatChieu suatChieuFind = this.suatChieuManager.timSuatChieu(ve.getMaSuatChieu());
        if (suatChieuFind == null)
            return;
        Phim phim = this.movieManager.timPhim(suatChieuFind.getMaPhim());
        String tenPhim = phim.getTenPhim();
        Rap rap = this.rapManager.findRapByID(suatChieuFind.getMaRap());
        String tenPhong = rap.getTenRap();
        String thoiGian = this.suatChieuDuocChon.getGioChieu().toString() + ", "
                + this.suatChieuDuocChon.getNgayChieu().toString();
        String soVe = Integer.toString(this.selectedChairs.size());

        String soGhe = String.join(", ", this.selectedChairs);
        String thoiGianDatVe = ve.getNgayBan().toString();
        String trangThai = ve.isDaThanhToan() ? "Đã thanh toán" : "Chưa thanh toán";

        JLabel lblMaVe = new JLabel("  Mã vé:                   " + maVe);
        JLabel lblTenPhim = new JLabel("  Tên phim:             " + tenPhim);
        JLabel lblTenPhong = new JLabel("  Phòng chiếu:        " + tenPhong);
        JLabel lblThoiGian = new JLabel("  Thời gian:             " + thoiGian);
        JLabel lblSoVe = new JLabel("  Số vé:                     " + soVe);
        JLabel lblSoGhe = new JLabel("  Số ghế:                   " + soGhe);
        JLabel lblThoiGianDatVe = new JLabel("  Thời gian đặt vé:  " + thoiGianDatVe);
        JLabel lblTrangThai = new JLabel("  Trạng thái:             " + trangThai);
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

        ticketFrame.add(pCenter, BorderLayout.CENTER);

        JPanel pSouth = new JPanel();
        JButton btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setFont(fChonGhe);
        btnThanhToan.setBackground(Color.GREEN);
        JButton btnHuy = new JButton("Hủy đặt vé");
        btnHuy.setFont(this.fChonGhe);
        btnHuy.setBackground(Color.RED);
        btnHuy.setForeground(Color.WHITE);

        pSouth.add(btnHuy);
        pSouth.add(btnThanhToan);

        ticketFrame.add(pSouth, BorderLayout.SOUTH);
        btnHuy.addActionListener(e -> huyDatVe(ticketFrame));
        btnThanhToan.addActionListener(e -> thanhToan(ticketFrame, khachHang));

        ticketFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        ticketFrame.setVisible(true);
    }

    private void huyDatVe(JFrame ticketJFrame) {
        ticketJFrame.dispose();
        resetForm();
    }

    private void thanhToan(JFrame ticketJFrame, KhachHang khachHang) {
        ArrayList<Ve> danhSachVeDaDat = new ArrayList<>();
        for (int i = 0; i < this.selectedChairs.size(); i++) {
            String maGhe = this.selectedChairs.get(i);
            Ghe ghe = this.chairManager.TimGheTheoMaRap(maGhe, this.suatChieuDuocChon.getMaRap());

            Ve ve = xuLyTaoVeTheoGhe(ghe);
            if (ve != null)
                danhSachVeDaDat.add(ve);
            ghe.setTinhTrang(true);
        }

        int option = JOptionPane.showConfirmDialog(this, "Bạn có muốn xem hóa đơn không ?",
                "Thanh toán thành công",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        double giaVe = this.suatChieuDuocChon.getGiaVe();
        HoaDon hoaDon = xuLyTaoHoaDon(khachHang, danhSachVeDaDat, giaVe);
        xuLyTaoChiTietHoaDon(hoaDon, danhSachVeDaDat, giaVe);
        // Thêm hóa đơn
        this.billManager.add(hoaDon);

        if (option == JOptionPane.YES_OPTION) {

        }
        resetForm();
        ticketJFrame.dispose();
        // Mở giao diện hóa đơn
    }

    private HoaDon xuLyTaoHoaDon(KhachHang khachHang, ArrayList<Ve> danhSachVeDaDat, double giaVe) {
        // Get NhanVien đang đăng nhập vào hệ thống - giả sử có mã là NV01
        NhanVien nhanVien = this.employeeManager.timNhanVienTheoMaNV("NV01");
        int soLuongVe = danhSachVeDaDat.size();
        double tongTien = giaVe * soLuongVe;

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

    private Ve xuLyTaoVeTheoGhe(Ghe ghe) {
        if (ghe == null)
            return null;

        Ve ve = createTicket();
        ve.setDaThanhToan(true);
        ve.setGhe(ghe);
        return ve;
    }

    private Ve createTicket() {
        if (this.suatChieuDuocChon == null)
            return null;
        Ve ve = new Ve(QuanLyVe_DAO.taoMaVeTuDong());
        ve.setMaSuatChieu(this.suatChieuDuocChon.getMaSuatChieu());
        ve.setNgayBan(LocalDate.now());
        return ve;
    }

    private void deleteTextMovieInfo() {
        txtSoGhe.setText("");
        txtTenPhim.setText("");
        txtTenPhong.setText("");
        txtTheLoai.setText("");
        txtSuatChieu.setText("");
        txtThoiLuong.setText("");
    }

    private void CapNhatThongTinPhim() {
        if (cbPhim.getItemCount() == 0)
            return;
        if (cbPhim.getSelectedIndex() == 0) {
            deleteTextMovieInfo();
            cbPhong.setEnabled(false);
            cbSuatChieu.setEnabled(false);
            btnChonGhe.setEnabled(false);
            btnDatVe.setEnabled(false);
            return;
        }
        txtTenPhim.setText("");
        txtTheLoai.setText("");
        txtThoiLuong.setText("");
        Phim phim = movieManager.getPhim(cbPhim.getSelectedIndex() - 1);
        txtTenPhim.setText(phim.getTenPhim());
        txtTheLoai.setText(phim.getTheLoai());
        txtThoiLuong.setText(Integer.toString(phim.getThoiLuong()) + " phút");
        this.suatChieuDuocChon = new SuatChieu("AUTO_GENERATE");
        this.suatChieuDuocChon.setMaPhim(phim.getMaPhim());
        // Mở khóa bước chọn phòng
        UnlockChooseRoom();
    }

    private void UnlockChooseRoom() {
        ArrayList<SuatChieu> dsSuatChieu = suatChieuManager.getSuatChieuTheoPhim(this.suatChieuDuocChon.getMaPhim());
        cbPhong.setEnabled(true);
        cbPhong.removeAllItems();
        cbPhong.addItem("---Chọn phòng---");
        roomIDSelectList = new ArrayList<>();
        roomIDSelectList.add("");
        // Lấy mã phòng không trùng lặp
        HashSet<String> dsMaRap = new HashSet<>();
        for (SuatChieu suatChieu : dsSuatChieu) {
            dsMaRap.add(suatChieu.getMaRap());
        }
        for (String maRap : dsMaRap) {
            Rap rap = rapManager.findRapByID(maRap);
            if (rap != null) {
                cbPhong.addItem(rap.getTenRap());
                roomIDSelectList.add(maRap);
            }
        }
    }

    private void CapNhatPhong() {
        if (cbPhong.getItemCount() == 0)
            return;
        if (cbPhong.getSelectedIndex() == 0) {
            txtTenPhong.setText("");
            cbSuatChieu.setEnabled(false);
            btnChonGhe.setEnabled(false);
            btnDatVe.setEnabled(false);
            return;
        }
        txtTenPhong.setText("");
        txtTenPhong.setText(cbPhong.getSelectedItem().toString());
        int index = cbPhong.getSelectedIndex();
        this.suatChieuDuocChon.setMaRap(roomIDSelectList.get(index));
        UnlockChoosMovieShowtime();
    }

    private void UnlockChoosMovieShowtime() {
        ArrayList<SuatChieu> dsSuatChieu = suatChieuManager.getSuatChieuTheoPhim(this.suatChieuDuocChon.getMaPhim());
        cbSuatChieu.setEnabled(true);
        cbSuatChieu.removeAllItems();
        cbSuatChieu.addItem("---Chọn suất chiếu---");
        // Lưu danh sách mã suất chiếu
        showtimeSelectList = new ArrayList<>();
        showtimeSelectList.add("");
        for (SuatChieu suatChieu : dsSuatChieu) {
            if (suatChieu.getMaRap().equals(this.suatChieuDuocChon.getMaRap())) {
                String dataSuatChieu = suatChieu.getNgayChieu().toString() + ", " + suatChieu.getGioChieu().toString();
                cbSuatChieu.addItem(dataSuatChieu);
                showtimeSelectList.add(suatChieu.getMaSuatChieu());
            }
        }
    }

    private void CapNhatSuatChieu() {
        if (cbSuatChieu.getItemCount() == 0)
            return;
        if (cbSuatChieu.getSelectedIndex() == 0) {
            txtSuatChieu.setText("");
            btnChonGhe.setEnabled(false);
            btnDatVe.setEnabled(false);
            return;
        }
        txtSuatChieu.setText("");
        txtSuatChieu.setText(cbSuatChieu.getSelectedItem().toString());
        int index = cbSuatChieu.getSelectedIndex();
        this.suatChieuDuocChon = this.suatChieuManager.timSuatChieu(showtimeSelectList.get(index));
        // Tới phần chọn ghế
        btnChonGhe.setEnabled(true);
    }

    private void selectChair() {
        if (this.suatChieuDuocChon == null || this.suatChieuDuocChon.getMaRap() == null)
            return;

        Rap rap = rapManager.findRapByID(suatChieuDuocChon.getMaRap());
        if (rap == null)
            return;
        int soGhe = rap.getSoLuongGhe();
        int row = (int) Math.ceil(Math.sqrt(soGhe));
        int col = row;

        JFrame chairFrame = new JFrame("Chọn ghế");
        chairFrame.setSize(this.modelDimension);
        chairFrame.setLocationRelativeTo(this);
        chairFrame.setLayout(new BorderLayout());

        // Màn hình
        JPanel screenPanel = new JPanel(new BorderLayout());
        screenPanel.setBackground(Color.DARK_GRAY);
        JLabel lblScreen = new JLabel("MÀN HÌNH", SwingConstants.CENTER);
        lblScreen.setForeground(Color.WHITE);
        lblScreen.setFont(new Font("Arial", Font.BOLD, 18));
        screenPanel.add(lblScreen, BorderLayout.CENTER);
        screenPanel.setPreferredSize(new Dimension(500, 40));
        chairFrame.add(screenPanel, BorderLayout.NORTH);

        // Sơ đồ ghế
        int distance = 1; // khoảng cách tới màn hình
        JPanel chairPanel = new JPanel();
        chairPanel.setLayout(new GridLayout(row + distance, col, 5, 5));
        // tạo khoảng cách với màn hình
        int i = 0;
        while (i < col * distance) {
            chairPanel.add(new JLabel(""));
            i++;
        }
        ArrayList<Ghe> chairList = this.chairManager.getDanhSachGheTheoRap(rap);
        ArrayList<String> selectedChairs = new ArrayList<>();
        for (i = 0; i < soGhe; i++) {
            Ghe ghe = chairList.get(i);
            JButton btn = new JButton(ghe.getMaGhe());
            if (ghe.isDaDat()) {
                btn.setBackground(Color.LIGHT_GRAY);
                btn.setEnabled(false);
            } else {
                btn.setBackground(Color.GREEN);
            }
            btn.addActionListener(e -> hanldeSelectChair(selectedChairs, btn));
            chairPanel.add(btn);
        }

        chairFrame.add(chairPanel, BorderLayout.CENTER);

        // Nút xác nhận
        JPanel confirmPanel = new JPanel();
        JButton btnConfirm = new JButton("Xác nhận");
        btnConfirm.setBackground(Color.GREEN);
        btnConfirm.addActionListener(e -> handleConfirmSelectChairs(selectedChairs, chairFrame, rap));
        confirmPanel.add(btnConfirm);

        chairFrame.add(confirmPanel, BorderLayout.SOUTH);
        chairFrame.setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        chairFrame.setVisible(true);
    }

    private void hanldeSelectChair(ArrayList<String> selectedChairs, JButton btn) {
        if (selectedChairs.contains(btn.getText())) {
            selectedChairs.remove(btn.getText());
            btn.setBackground(Color.GREEN);
        } else {
            selectedChairs.add(btn.getText());
            btn.setBackground(Color.LIGHT_GRAY);
        }
    }

    private void handleConfirmSelectChairs(ArrayList<String> selectedChairs, JFrame chairFrame, Rap rap) {
        txtSoGhe.setText(String.join(", ", selectedChairs));
        btnDatVe.setEnabled(!selectedChairs.isEmpty());
        chairFrame.dispose();
        this.selectedChairs = selectedChairs;
    }

    private void LoadMovieManager() {
        // load database movie
        movieManager = DataBase.FakeMovieDB();
    }

    private void LoadSuatChieuManager() {
        suatChieuManager = DataBase.FakeSuatChieuDB();
    }

    private void LoadRapManager() {
        rapManager = DataBase.FakeRapDB();
    }

    private void LoadChairManager() {
        this.chairManager = DataBase.FakeGheDB();
    }

    private void LoadCustomerManager() {
        this.customerManager = DataBase.FakeKhachHangDB();
    }

    private void LoadTicketManager() {
        this.ticketManager = new QuanLyVe_DAO();
    }

    private void LoadCthdManager() {
        this.cthdManager = new QuanLyCTHD_DAO();
    }

    private void LoadBillManager() {
        this.billManager = new QuanLyHoaDon_DAO();
    }

    private void LoadEmployeeManager() {
        this.employeeManager = DataBase.FakeNhanVienDB();
    }
}
