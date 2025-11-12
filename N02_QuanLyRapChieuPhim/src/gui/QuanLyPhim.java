package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

import ConnectDB.ConnectDB;
import dao.QuanLyPhim_DAO;
import entity.Phim;
import entity.TheLoaiPhim;
import entity.LoadData;

public class QuanLyPhim extends JPanel implements LoadData {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaPhim, txtTenPhim, txtNhaSX, txtThoiLuong, txtQuocGia, txtTimPhim;
    private JComboBox<TheLoaiPhim> cboTheLoai;
    private JButton btnThem, btnSua, btnXoa, btnXoaRong, btnLuu, btnTim;

    private QuanLyPhim_DAO phimDAO;
    private ArrayList<Phim> dsPhim;

    public QuanLyPhim() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ======= KẾT NỐI DATABASE =======
        try {
            ConnectDB.getInstance().connect();
            Connection con = ConnectDB.getConnection();
            if (con != null)
                System.out.println("✅ Kết nối SQL Server thành công!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Không thể kết nối CSDL: " + e.getMessage());
        }

        phimDAO = new QuanLyPhim_DAO();

        // ===== PANEL THÔNG TIN PHIM =====
        JPanel pnNorth = new JPanel(new GridBagLayout());
        pnNorth.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                "Thông tin phim",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 20),
                Color.DARK_GRAY));
        pnNorth.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblMaPhim = new JLabel("Mã phim:");
        JLabel lblTenPhim = new JLabel("Tên phim:");
        JLabel lblNhaSX = new JLabel("Nhà sản xuất:");
        JLabel lblTheLoai = new JLabel("Thể loại:");
        JLabel lblThoiLuong = new JLabel("Thời lượng (phút):");
        JLabel lblQuocGia = new JLabel("Quốc gia:");

        Font lblFont = new Font("Segoe UI", Font.BOLD, 18);
        for (JLabel lbl : new JLabel[] { lblMaPhim, lblTenPhim, lblNhaSX, lblTheLoai, lblThoiLuong, lblQuocGia })
            lbl.setFont(lblFont);

        txtMaPhim = new JTextField(20);
        txtMaPhim.setEditable(false); 
        
        txtTenPhim = new JTextField(20);
        txtNhaSX = new JTextField(20);
        cboTheLoai = new JComboBox<>(TheLoaiPhim.values());
        cboTheLoai.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        txtThoiLuong = new JTextField(20);
        txtQuocGia = new JTextField(20);

        gbc.gridx = 0;
        gbc.gridy = 0;
        pnNorth.add(lblMaPhim, gbc);
        gbc.gridx = 1;
        pnNorth.add(txtMaPhim, gbc);
        gbc.gridx = 2;
        pnNorth.add(lblTenPhim, gbc);
        gbc.gridx = 3;
        pnNorth.add(txtTenPhim, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        pnNorth.add(lblNhaSX, gbc);
        gbc.gridx = 1;
        pnNorth.add(txtNhaSX, gbc);
        gbc.gridx = 2;
        pnNorth.add(lblTheLoai, gbc);
        gbc.gridx = 3;
        pnNorth.add(cboTheLoai, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        pnNorth.add(lblThoiLuong, gbc);
        gbc.gridx = 1;
        pnNorth.add(txtThoiLuong, gbc);
        gbc.gridx = 2;
        pnNorth.add(lblQuocGia, gbc);
        gbc.gridx = 3;
        pnNorth.add(txtQuocGia, gbc);

        add(pnNorth, BorderLayout.NORTH);

        model = new DefaultTableModel(new String[] {
                "Mã phim", "Tên phim", "Nhà SX", "Thể loại", "Thời lượng", "Quốc gia"
        }, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(245, 245, 245));
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); 

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                "Danh sách phim",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 18),
                Color.DARK_GRAY));
        add(scroll, BorderLayout.CENTER);

        // ===== PANEL CHỨC NĂNG =====
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnSouth.setBackground(Color.WHITE);

        JLabel lblTim = new JLabel("Tìm mã phim:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTimPhim = new JTextField(15);
        txtTimPhim.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        btnTim = new JButton("Tìm");
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoaRong = new JButton("Xóa rỗng");
        btnLuu = new JButton("Lưu");

        JButton[] arrBtns = { btnThem, btnSua, btnXoa, btnXoaRong, btnLuu, btnTim };
        Color[] colors = {
                new Color(46, 204, 113), // xanh lá
                new Color(52, 152, 219), // xanh dương
                new Color(231, 76, 60), // đỏ
                new Color(155, 89, 182), // tím
                new Color(241, 196, 15), // vàng
                new Color(255, 140, 0) // cam
        };

        Font btnFont = new Font("Segoe UI", Font.BOLD, 18);
        for (int i = 0; i < arrBtns.length; i++) {
            arrBtns[i].setFont(btnFont);
            arrBtns[i].setBackground(colors[i]);
            arrBtns[i].setForeground(Color.WHITE);
            arrBtns[i].setFocusPainted(false);
            arrBtns[i].setPreferredSize(new Dimension(130, 45));
        }

        pnSouth.add(lblTim);
        pnSouth.add(txtTimPhim);
        pnSouth.add(btnTim);
        pnSouth.add(btnThem);
        pnSouth.add(btnSua);
        pnSouth.add(btnXoa);
        pnSouth.add(btnXoaRong);
        pnSouth.add(btnLuu);

        add(pnSouth, BorderLayout.SOUTH);

        // ===== SỰ KIỆN =====
        loadDataToTable();

        btnThem.addActionListener(e -> themPhim());
        btnSua.addActionListener(e -> suaPhim());
        btnXoa.addActionListener(e -> xoaPhim());
        btnXoaRong.addActionListener(e -> xoaRong());
        btnLuu.addActionListener(e -> luu());
        btnTim.addActionListener(e -> timPhim());
        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { 
                hienThiLenForm();
            }
        });
        
        xoaRong();
    }


    private void loadDataToTable() {
        model.setRowCount(0);
        dsPhim = phimDAO.getAllPhim();
        for (Phim p : dsPhim) {
            model.addRow(new Object[] {
                    p.getMaPhim(), p.getTenPhim(), p.getNhaSanXuat(),
                    p.getTheLoai().getTenHienThi(), p.getThoiLuong(), p.getQuocGia()
            });
        }
    }

    private boolean validateInput(boolean isAdding) {
        String maPhim = txtMaPhim.getText().trim();
        String tenPhim = txtTenPhim.getText().trim();
        String nhaSX = txtNhaSX.getText().trim();
        String thoiLuongStr = txtThoiLuong.getText().trim();
        String quocGia = txtQuocGia.getText().trim();

        if (maPhim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã phim không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaPhim.requestFocus();
            return false;
        }
        if (!maPhim.matches("^P\\d{3,5}$")) { 
            JOptionPane.showMessageDialog(this, "Mã phim phải có định dạng Pxxx (ví dụ: P001, P0123).", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaPhim.requestFocus();
            return false;
        }
        if (isAdding && phimDAO.timPhimTheoMa(maPhim) != null) {
            JOptionPane.showMessageDialog(this, "Mã phim đã tồn tại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaPhim.requestFocus();
            return false;
        }

        if (tenPhim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên phim không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTenPhim.requestFocus();
            return false;
        }
        if (!tenPhim.matches("^[A-Za-zÀ-ỹ0-9\\s'.,-]+$")) {
            JOptionPane.showMessageDialog(this, "Tên phim chỉ được chứa chữ, số, khoảng trắng và một số ký tự .,',-.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtTenPhim.requestFocus();
            return false;
        }

        if (nhaSX.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhà sản xuất không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNhaSX.requestFocus();
            return false;
        }
        if (!nhaSX.matches("^[A-Za-zÀ-ỹ\\s'.,-]+$")) {
            JOptionPane.showMessageDialog(this, "Tên nhà sản xuất chỉ được chứa chữ và ký tự .,',-.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtNhaSX.requestFocus();
            return false;
        }

        if (thoiLuongStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thời lượng không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtThoiLuong.requestFocus();
            return false;
        }

        int thoiLuong;
        try {
            thoiLuong = Integer.parseInt(thoiLuongStr);
            if (thoiLuong <= 0 || thoiLuong > 500) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải nằm trong khoảng 1–500 phút.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                txtThoiLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là một số nguyên hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtThoiLuong.requestFocus();
            return false;
        }

        if (quocGia.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Quốc gia không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtQuocGia.requestFocus();
            return false;
        }
        if (!quocGia.matches("^[A-Za-zÀ-ỹ\\s]+$")) {
            JOptionPane.showMessageDialog(this, "Tên quốc gia chỉ được chứa chữ và khoảng trắng.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtQuocGia.requestFocus();
            return false;
        }

        return true; 
    }


    private void themPhim() {
        if (!txtMaPhim.isEditable()) {
            JOptionPane.showMessageDialog(this, "Bạn đang ở chế độ sửa. Vui lòng nhấn 'Xóa rỗng' để thêm phim mới.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        if (!validateInput(true)) { 
            return; 
        }

        try {
            Phim p = new Phim(
                    txtMaPhim.getText().trim(),
                    txtTenPhim.getText().trim(),
                    txtNhaSX.getText().trim(),
                    (TheLoaiPhim) cboTheLoai.getSelectedItem(),
                    Integer.parseInt(txtThoiLuong.getText().trim()),
                    txtQuocGia.getText().trim());

            if (phimDAO.themPhim(p)) {
                JOptionPane.showMessageDialog(this, "✅ Thêm phim thành công!");
                loadDataToTable();
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Thêm thất bại!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi không xác định khi thêm: " + e.getMessage());
        }
    }

    private void suaPhim() {
        if (txtMaPhim.isEditable() || txtMaPhim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim từ danh sách bên dưới để sửa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        if (!validateInput(false)) { 
            return; 
        }
        
        try {
            Phim p = new Phim(
                    txtMaPhim.getText().trim(), 
                    txtTenPhim.getText().trim(),
                    txtNhaSX.getText().trim(),
                    (TheLoaiPhim) cboTheLoai.getSelectedItem(),
                    Integer.parseInt(txtThoiLuong.getText().trim()),
                    txtQuocGia.getText().trim());

            if (phimDAO.capNhatPhim(p)) {
                JOptionPane.showMessageDialog(this, "✅ Cập nhật thành công!");
                loadDataToTable();
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không tìm thấy phim cần sửa!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa phim: " + e.getMessage());
        }
    }

    private void xoaPhim() {
        if (txtMaPhim.isEditable() || txtMaPhim.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phim từ danh sách để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String ma = txtMaPhim.getText().trim();
        
        int confirm = JOptionPane.showConfirmDialog(this, "Xóa phim " + ma + "?\n(Thao tác này cũng sẽ xóa các suất chiếu liên quan)", "Xác nhận",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            if (phimDAO.xoaPhim(ma)) {
                JOptionPane.showMessageDialog(this, "✅ Xóa thành công!");
                loadDataToTable();
                xoaRong();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không tìm thấy phim cần xóa!");
            }
        }
    }

    private void timPhim() {
        String ma = txtTimPhim.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã phim cần tìm!");
            return;
        }
        Phim p = phimDAO.timPhimTheoMa(ma);
        if (p != null) {
            JOptionPane.showMessageDialog(this, "✅ Tìm thấy phim: " + p.getTenPhim());
            txtMaPhim.setText(p.getMaPhim());
            txtTenPhim.setText(p.getTenPhim());
            txtNhaSX.setText(p.getNhaSanXuat());
            cboTheLoai.setSelectedItem(p.getTheLoai());
            txtThoiLuong.setText(String.valueOf(p.getThoiLuong()));
            txtQuocGia.setText(p.getQuocGia());
            txtMaPhim.setEditable(false);

            int index = -1;
            for (int i = 0; i < dsPhim.size(); i++) {
                if (dsPhim.get(i).getMaPhim().equals(p.getMaPhim())) {
                    index = i;
                    break;
                }
            }
            
            if (index != -1) {
                table.setRowSelectionInterval(index, index);
                table.scrollRectToVisible(table.getCellRect(index, 0, true));
            }

        } else {
            JOptionPane.showMessageDialog(this, "❌ Không tìm thấy phim có mã " + ma);
            xoaRong(); 
        }
    }

    private void hienThiLenForm() {
        int i = table.getSelectedRow();
        if (i >= 0 && i < dsPhim.size()) {
            Phim p = dsPhim.get(i);
            txtMaPhim.setText(p.getMaPhim());
            txtTenPhim.setText(p.getTenPhim());
            txtNhaSX.setText(p.getNhaSanXuat());
            cboTheLoai.setSelectedItem(p.getTheLoai());
            txtThoiLuong.setText(String.valueOf(p.getThoiLuong()));
            txtQuocGia.setText(p.getQuocGia());
            txtMaPhim.setEditable(false); 
        }
    }

    private void xoaRong() {
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtNhaSX.setText("");
        cboTheLoai.setSelectedIndex(0);
        txtThoiLuong.setText("");
        txtQuocGia.setText("");
        txtTimPhim.setText("");
        table.clearSelection();
        txtMaPhim.setEditable(true);
        txtMaPhim.requestFocus();
    }

    private void luu() {
        JOptionPane.showMessageDialog(this, "💾 Dữ liệu đã được lưu vào CSDL!");
    }

    @Override
    public void loadData() {
        loadDataToTable();
        xoaRong();
    }
}