package gui;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import dao.QuanLyNhanVien_DAO;
import entity.NhanVien;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class QuanLyNhanVien extends JFrame {
    private QuanLyNhanVien_DAO daoNV;
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMa, txtTen, txtDiaChi, txtSdt, txtEmail, txtNgaySinh, txtTim;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem, btnSua, btnXoa, btnTaiLai, btnTim;
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public QuanLyNhanVien() {
        setTitle("Quản lý nhân viên");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // ✅ Tự giãn full màn hình
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        daoNV = new QuanLyNhanVien_DAO();
        Font font = new Font("Segoe UI", Font.PLAIN, 14);

        // ====== PANEL CHÍNH ======
        JPanel pnlMain = new JPanel(new BorderLayout(15, 15));
        pnlMain.setBorder(new EmptyBorder(10, 15, 10, 15));
        pnlMain.setBackground(Color.WHITE);
        getContentPane().add(pnlMain);

        // ====== TIÊU ĐỀ ======
        JLabel lblTitle = new JLabel("QUẢN LÝ NHÂN VIÊN", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setForeground(new Color(0, 102, 204));
        lblTitle.setBorder(new EmptyBorder(15, 0, 15, 0));
        pnlMain.add(lblTitle, BorderLayout.NORTH);

        // ====== KHU VỰC GIỮA: FORM + BẢNG ======
        JPanel pnlCenter = new JPanel(new BorderLayout(10, 10));
        pnlCenter.setBackground(Color.WHITE);
        pnlMain.add(pnlCenter, BorderLayout.CENTER);

        // ====== FORM NHẬP ======
        JPanel pnlForm = new JPanel(new GridBagLayout());
        pnlForm.setBorder(new TitledBorder(BorderFactory.createLineBorder(new Color(180, 180, 180)), "Thông tin nhân viên"));
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setPreferredSize(new Dimension(1000, 160));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 10, 8, 10);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1; // ✅ Giúp giãn ngang

        int row = 0;

        gbc.gridx = 0; gbc.gridy = row; pnlForm.add(new JLabel("Mã NV:"), gbc);
        gbc.gridx = 1; txtMa = new JTextField(); pnlForm.add(txtMa, gbc);
        gbc.gridx = 2; pnlForm.add(new JLabel("Tên NV:"), gbc);
        gbc.gridx = 3; txtTen = new JTextField(); pnlForm.add(txtTen, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; pnlForm.add(new JLabel("Giới tính:"), gbc);
        gbc.gridx = 1; cboGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ"}); pnlForm.add(cboGioiTinh, gbc);
        gbc.gridx = 2; pnlForm.add(new JLabel("Ngày sinh (dd/MM/yyyy):"), gbc);
        gbc.gridx = 3; txtNgaySinh = new JTextField(); pnlForm.add(txtNgaySinh, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; pnlForm.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 1; txtSdt = new JTextField(); pnlForm.add(txtSdt, gbc);
        gbc.gridx = 2; pnlForm.add(new JLabel("Email:"), gbc);
        gbc.gridx = 3; txtEmail = new JTextField(); pnlForm.add(txtEmail, gbc);
        row++;

        gbc.gridx = 0; gbc.gridy = row; pnlForm.add(new JLabel("Địa chỉ:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 3; txtDiaChi = new JTextField(); pnlForm.add(txtDiaChi, gbc);

        pnlCenter.add(pnlForm, BorderLayout.NORTH);

        // ====== BẢNG DỮ LIỆU ======
        String[] cols = {"Mã NV", "Tên NV", "Giới tính", "Ngày sinh", "SĐT", "Email", "Địa chỉ"};
        model = new DefaultTableModel(cols, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        table.setFont(font);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        table.getTableHeader().setBackground(new Color(240, 240, 240));
        table.setSelectionBackground(new Color(204, 229, 255));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createTitledBorder("Danh sách nhân viên"));
        pnlCenter.add(scroll, BorderLayout.CENTER);

        // ====== NÚT CHỨC NĂNG ======
        JPanel pnlBtn = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        pnlBtn.setBackground(Color.WHITE);
        pnlBtn.setBorder(new TitledBorder("Chức năng"));

        btnThem = taoButton("➕ Thêm", new Color(0, 153, 76));
        btnSua = taoButton("✏️ Sửa", new Color(255, 153, 0));
        btnXoa = taoButton("🗑️ Xóa", new Color(204, 0, 0));
        btnTaiLai = taoButton("🔄 Tải lại", new Color(0, 102, 204));
        btnTim = taoButton("🔍 Tìm", new Color(102, 0, 204));

        txtTim = new JTextField(20);
        txtTim.setFont(font);

        pnlBtn.add(new JLabel("Tìm theo tên:"));
        pnlBtn.add(txtTim);
        pnlBtn.add(btnTim);
        pnlBtn.add(btnThem);
        pnlBtn.add(btnSua);
        pnlBtn.add(btnXoa);
        pnlBtn.add(btnTaiLai);

        pnlMain.add(pnlBtn, BorderLayout.SOUTH);

        // ====== SỰ KIỆN ======
        btnTaiLai.addActionListener(e -> loadNhanVien());
        btnThem.addActionListener(e -> themNhanVien());
        btnSua.addActionListener(e -> suaNhanVien());
        btnXoa.addActionListener(e -> xoaNhanVien());
        btnTim.addActionListener(e -> timNhanVien());
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                hienThiLenForm();
            }
        });

        loadNhanVien();
    }

    private JButton taoButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setFocusPainted(false);
        btn.setBorder(new EmptyBorder(8, 15, 8, 15));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void loadNhanVien() {
        model.setRowCount(0);
        ArrayList<NhanVien> ds = daoNV.getAllNhanVien();
        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMaNV(), nv.getTenNV(), nv.getGioiTinh(),
                    nv.getNgaySinh().format(fmt), nv.getSoDienThoai(),
                    nv.getEmail(), nv.getDiaChi()
            });
        }
    }

    private void hienThiLenForm() {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMa.setText(model.getValueAt(row, 0).toString());
            txtTen.setText(model.getValueAt(row, 1).toString());
            cboGioiTinh.setSelectedItem(model.getValueAt(row, 2).toString());
            txtNgaySinh.setText(model.getValueAt(row, 3).toString());
            txtSdt.setText(model.getValueAt(row, 4).toString());
            txtEmail.setText(model.getValueAt(row, 5).toString());
            txtDiaChi.setText(model.getValueAt(row, 6).toString());
        }
    }

    private void themNhanVien() {
        try {
            NhanVien nv = layDuLieuForm();
            if (daoNV.themNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "✅ Thêm nhân viên thành công!");
                loadNhanVien();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không thể thêm nhân viên!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Lỗi: " + ex.getMessage());
        }
    }

    private void suaNhanVien() {
        try {
            NhanVien nv = layDuLieuForm();
            if (daoNV.capNhatNhanVien(nv)) {
                JOptionPane.showMessageDialog(this, "✏️ Cập nhật thành công!");
                loadNhanVien();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không thể cập nhật!");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "⚠️ Lỗi: " + ex.getMessage());
        }
    }

    private void xoaNhanVien() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "⚠️ Vui lòng chọn nhân viên cần xóa!");
            return;
        }
        String maNV = model.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa nhân viên này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (daoNV.xoaNhanVien(maNV)) {
                JOptionPane.showMessageDialog(this, "🗑️ Đã xóa thành công!");
                loadNhanVien();
            } else {
                JOptionPane.showMessageDialog(this, "❌ Không thể xóa!");
            }
        }
    }

    private void timNhanVien() {
        String keyword = txtTim.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Nhập tên cần tìm!");
            return;
        }
        model.setRowCount(0);
        ArrayList<NhanVien> ds = daoNV.timTheoTen(keyword);
        for (NhanVien nv : ds) {
            model.addRow(new Object[]{
                    nv.getMaNV(), nv.getTenNV(), nv.getGioiTinh(),
                    nv.getNgaySinh().format(fmt), nv.getSoDienThoai(),
                    nv.getEmail(), nv.getDiaChi()
            });
        }
    }

    private NhanVien layDuLieuForm() {
        String ma = txtMa.getText().trim();
        String ten = txtTen.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String sdt = txtSdt.getText().trim();
        String email = txtEmail.getText().trim();
        String gioiTinh = cboGioiTinh.getSelectedItem().toString();
        LocalDate ngaySinh = LocalDate.parse(txtNgaySinh.getText().trim(), fmt);

        if (ma.isEmpty() || ten.isEmpty() || sdt.isEmpty())
            throw new IllegalArgumentException("Không được bỏ trống Mã NV, Tên hoặc SĐT!");

        return new NhanVien(ma, ten, diaChi, sdt, ngaySinh, email, gioiTinh);
    }
}
