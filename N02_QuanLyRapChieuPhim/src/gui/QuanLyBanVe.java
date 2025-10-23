package gui;

import javax.swing.*;

import ConnectDB.DataBase;
import dao.QuanLyGhe_DAO;
import dao.QuanLyKhachHang_DAO;
import dao.QuanLyPhim_DAO;
import dao.QuanLyRap_DAO;
import dao.QuanLySuatChieu_DAO;

import java.awt.*;
import java.awt.Dialog.ModalExclusionType;
import java.util.ArrayList;
import java.util.HashSet;

import entity.Ve;
import entity.SuatChieu;
import entity.Rap;
import entity.Ghe;
import entity.KhachHang;
import entity.Phim;

public class QuanLyBanVe extends JPanel {
    private JComboBox<String> cbPhim, cbPhong, cbSuatChieu, cbGioiTinh;
    private JButton btnChonGhe, btnDatVe, btnXoaChon;
    private JTextField txtHoTen, txtSDT, txtDiaChi;
    private JTextField txtTenPhim, txtTenPhong, txtThoiLuong, txtTheLoai, txtSuatChieu, txtSoGhe;
    private SuatChieu suatChieu;
    private ArrayList<String> roomIDSelectList;
    private ArrayList<String> showtimeSelectList;
    private ArrayList<String> selectedChairs;
    private QuanLyPhim_DAO movieManager;
    private QuanLySuatChieu_DAO suatChieuManager;
    private QuanLyRap_DAO rapManager;
    private QuanLyGhe_DAO chairManager;
    private QuanLyKhachHang_DAO customerManager;

    public QuanLyBanVe() {
        setLayout(new BorderLayout(10, 10));
        // Load database
        LoadMovieManager();
        LoadSuatChieuManager();
        LoadRapManager();
        LoadChairManager();
        LoadCustomerManager();
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
        Font fChonGhe = new Font("Arial", Font.BOLD, 16);
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
        String regexHoten = "^[A-Z][a-z]+(\s[A-Z][a-z]+)*\\s[A-Z][a-z]+$"; // Gồm Họ và tên - chữ cái đầu phải viết hoa
        String regexSDT = "^[0-9]{10}$"; // số điện thoại - có 10 số
        String regexDiaChi = "^([a-z0-9/\\s]+,\\s){3}[a-z0-9/\\s]+$"; // gồm: ấp/đường + xã/phường + quận/huyện +
                                                                      // tỉnh/thành phố.
                                                                      // ngăn cách bởi dấu phẩy + khoảng trắng
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
            JOptionPane.showMessageDialog(this,
                    "Gồm: ấp/đường + xã/phường + quận/huyện + tỉnh/thành phố.\nngăn cách bởi dấu phẩy + khoảng trắng ",
                    "Lỗi cú pháp Họ tên", JOptionPane.ERROR_MESSAGE);
            return;
        }
        KhachHang khachHang = new KhachHang("AUTO_GENERATE", hoten, gioiTinh, sdt, diaChi);
        customerManager.add(khachHang);
        resetForm();
        Ve ve = new Ve("AUTO_GENERATE");
        ve.setDaThanhToan(true);
        // chuyển danh sách ghế đã chọn sang tình trạng đã đặt
        if (this.selectedChairs == null || this.suatChieu == null) {
            JOptionPane.showMessageDialog(this, "Chưa chọn ghế !", "Lỗi đặt vé", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < this.selectedChairs.size(); i++) {
            String maGhe = this.selectedChairs.get(i);
            Ghe ghe = this.chairManager.TimGheTheoMaRap(maGhe, this.suatChieu.getMaRap());
            if (ghe != null)
                ghe.setTinhTrang(true);
        }
        this.suatChieu = null;
        this.selectedChairs.clear();
        JOptionPane.showMessageDialog(this, "Đặt vé thành công!");
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
        suatChieu = new SuatChieu("AUTO_GENERATE");
        suatChieu.setMaPhim(phim.getMaPhim());
        // Mở khóa bước chọn phòng
        UnlockChooseRoom();
    }

    private void UnlockChooseRoom() {
        ArrayList<SuatChieu> dsSuatChieu = suatChieuManager.getSuatChieuPhim(suatChieu.getMaPhim());
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
        suatChieu.setMaRap(roomIDSelectList.get(index));
        UnlockChoosMovieShowtime();
    }

    private void UnlockChoosMovieShowtime() {
        ArrayList<SuatChieu> dsSuatChieu = suatChieuManager.getSuatChieuPhim(this.suatChieu.getMaPhim());
        cbSuatChieu.setEnabled(true);
        cbSuatChieu.removeAllItems();
        cbSuatChieu.addItem("---Chọn suất chiếu---");
        // Lưu danh sách mã suất chiếu
        showtimeSelectList = new ArrayList<>();
        showtimeSelectList.add("");
        for (SuatChieu suatChieu : dsSuatChieu) {
            if (suatChieu.getMaRap().equals(this.suatChieu.getMaRap())) {
                String dataSuatChieu = suatChieu.getNgayChieu().toString() + "T" + suatChieu.getGioChieu().toString();
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
        this.suatChieu.setMaSuatChieu(showtimeSelectList.get(index));
        // Tới phần chọn ghế
        btnChonGhe.setEnabled(true);
    }

    private void selectChair() {
        if (this.suatChieu == null || this.suatChieu.getMaRap() == null)
            return;

        Rap rap = rapManager.findRapByID(suatChieu.getMaRap());
        if (rap == null)
            return;
        int soGhe = rap.getSoLuongGhe();
        int row = (int) Math.ceil(Math.sqrt(soGhe));
        int col = row;

        JFrame chairFrame = new JFrame("Chọn ghế");
        chairFrame.setSize(500, 600);
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
}
