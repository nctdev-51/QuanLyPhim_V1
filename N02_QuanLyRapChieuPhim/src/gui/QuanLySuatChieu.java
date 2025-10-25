package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ConnectDB.DataBase;
import dao.QuanLyPhim_DAO;
import dao.QuanLySuatChieu_DAO;
import entity.SuatChieu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class QuanLySuatChieu extends JPanel implements ActionListener {
    private QuanLySuatChieu_DAO quanLySuatChieu_DAO;
    private JTable tblSuatChieu;
    private JTextField txtMaSuat, txtNgayChieu, txtThoiGian, txtGiaVe, txtTimSuat, txtTenPhim, txtMaPhim;
    private JComboBox<String> cbPhong;
    private JButton btnThem, btnSua, btnXoa, btnXoaRong, btnLuu, btnTim;
    private DefaultTableModel model;
    private JTree treeNgayChieu;
    private QuanLySuatChieu_DAO data;
    protected QuanLyPhim_DAO dataPhim;

    public QuanLySuatChieu() {
        // Khởi tạo DAO trước khi sử dụng
        quanLySuatChieu_DAO = new QuanLySuatChieu_DAO();

        laydulieu();

        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(Color.WHITE);

        // === Tiêu đề ===
        JLabel lblTitle = new JLabel("Quản Lý Suất Chiếu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        add(lblTitle, BorderLayout.NORTH);

        // === Cây ngày chiếu (bên trái) ===
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Quản lý suất chiếu");
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 04/10/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 12/09/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 05/08/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 14/07/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 02/06/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 05/05/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 30/04/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 02/03/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 05/02/2025"));
        root.add(new DefaultMutableTreeNode("Ngày chiếu: 29/01/2025"));

        treeNgayChieu = new JTree(new DefaultTreeModel(root));
        JScrollPane scrollTree = new JScrollPane(treeNgayChieu);
        scrollTree.setPreferredSize(new Dimension(200, 0));

        // === Sự kiện khi nhấn vào một node trong cây ===
        treeNgayChieu.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeNgayChieu.getLastSelectedPathComponent();
            if (selectedNode == null)
                return;

            String nodeText = selectedNode.toString();

            // Chỉ xử lý nếu node có dạng "Ngày chiếu: ..."
            if (nodeText.startsWith("Ngày chiếu:")) {
                String ngayStr = nodeText.replace("Ngày chiếu:", "").trim();

                try {
                    LocalDate ngayChon = LocalDate.parse(ngayStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    capNhatBangTheoNgay(ngayChon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (nodeText.equals("Quản lý suất chiếu")) {
                capNhatBang(); // Hiện lại tất cả
            }

        });

        add(scrollTree, BorderLayout.WEST);

        // === Khu vực giữa (form + bảng) ===
        JPanel pnBody = new JPanel(new BorderLayout());
        pnBody.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));
        pnBody.setBackground(Color.WHITE);

        // === Form nhập liệu ===
        JPanel pnForm = new JPanel(new GridLayout(4, 2, 10, 10));
        pnForm.setBackground(Color.WHITE);
        pnForm.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        pnForm.add(new JLabel("Mã Suất Chiếu:"));
        txtMaSuat = new JTextField();
        pnForm.add(txtMaSuat);

        pnForm.add(new JLabel("Giá vé:"));
        txtGiaVe = new JTextField();
        pnForm.add(txtGiaVe);

        pnForm.add(new JLabel("Ngày Chiếu (dd/MM/yyyy):"));
        txtNgayChieu = new JTextField();
        pnForm.add(txtNgayChieu);

        pnForm.add(new JLabel("Tên Phim:"));
        txtTenPhim = new JTextField();
        pnForm.add(txtTenPhim);

        pnForm.add(new JLabel("Mã Phim:"));
        txtMaPhim = new JTextField();
        pnForm.add(txtMaPhim);

        pnForm.add(new JLabel("Thời Gian (HH:mm):"));
        txtThoiGian = new JTextField();
        pnForm.add(txtThoiGian);
        // ==============================================================================
        pnForm.add(new JLabel("Mã Phòng:"));
        cbPhong = new JComboBox<>(
                new String[] { "phong01", "phong02", "phong03", "phong04", "phong05", "phong06", "phong07",
                        "phong08", "phong09", "phong10" });

        pnForm.add(cbPhong);

        pnBody.add(pnForm, BorderLayout.NORTH);

        // === Bảng suất chiếu ===
        model = new DefaultTableModel(
                new String[] { "Mã Suất Chiếu", "Mã Phim", "Tên Phim", "Mã Phòng", "Ngày chiếu", "Thời Gian",
                        "Giá Vé" },
                0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }

        };

        tblSuatChieu = new JTable(model);
        tblSuatChieu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tblSuatChieu.setRowHeight(25);
        tblSuatChieu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        tblSuatChieu.getTableHeader().setBackground(new Color(240, 240, 240));

        JScrollPane scrollPaneTable = new JScrollPane(tblSuatChieu);
        pnBody.add(scrollPaneTable, BorderLayout.CENTER);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblSuatChieu.getColumnCount(); i++) {
            tblSuatChieu.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        tblSuatChieu.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblSuatChieu.getSelectedRow();
                if (row != -1) {
                    txtMaSuat.setText(model.getValueAt(row, 0).toString());
                    txtMaPhim.setText(model.getValueAt(row, 1).toString());
                    txtTenPhim.setText(model.getValueAt(row, 2).toString());
                    cbPhong.setSelectedItem(model.getValueAt(row, 3).toString());
                    txtNgayChieu.setText(model.getValueAt(row, 4).toString());
                    txtThoiGian.setText(model.getValueAt(row, 5).toString());
                    txtGiaVe.setText(model.getValueAt(row, 6).toString());
                }
            }
        });

        add(pnBody, BorderLayout.CENTER);

        // Cập nhật bảng với dữ liệu đã load
        capNhatBang();

        // === Khu vực nút chức năng (dưới) ===
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnSouth.setBackground(Color.WHITE);

        JLabel lblTim = new JLabel("Tìm mã suất chiếu:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTimSuat = new JTextField(15);
        txtTimSuat.setFont(new Font("Segoe UI", Font.PLAIN, 18));

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
                new Color(255, 140, 0) // cam (tìm)
        };

        Font btnFont = new Font("Segoe UI", Font.BOLD, 18);
        for (int i = 0; i < arrBtns.length; i++) {
            arrBtns[i].setFont(btnFont);
            arrBtns[i].setBackground(colors[i]);
            arrBtns[i].setForeground(Color.WHITE);
            arrBtns[i].setFocusPainted(false);
            arrBtns[i].setPreferredSize(new Dimension(120, 45));
            arrBtns[i].addActionListener(this); // 🔹 GẮN SỰ KIỆN CHO NÚT
        }

        pnSouth.add(lblTim);
        pnSouth.add(txtTimSuat);
        pnSouth.add(btnTim);
        pnSouth.add(btnThem);
        pnSouth.add(btnSua);
        pnSouth.add(btnXoa);
        pnSouth.add(btnXoaRong);
        pnSouth.add(btnLuu);

        add(pnSouth, BorderLayout.SOUTH);
    }

    // ================ XỬ LÝ SỰ KIỆN =======================
    private String tenPhim(String maPhim) {
        // lay ten phim tu database
        dataPhim = DataBase.FakeMovieDB();
        String ten = dataPhim.timPhim(maPhim).getTenPhim();
        return ten;
    }

    private void capNhatBang() {
        model.setRowCount(0); // Xóa dữ liệu cũ
        for (SuatChieu suatChieu : quanLySuatChieu_DAO.getDanhSachSuatChieu()) {

            model.addRow(new Object[] {
                    suatChieu.getMaSuatChieu(),
                    suatChieu.getMaPhim(),
                    tenPhim(suatChieu.getMaPhim()),
                    suatChieu.getMaRap(),
                    suatChieu.getNgayChieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    suatChieu.getGioChieu().format(DateTimeFormatter.ofPattern("HH:mm")),
                    suatChieu.getGiaVe()
            });
        }
    }

    @Override

    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnThem) {
            them();

        } else if (src == btnSua) {
            sua();
        } else if (src == btnXoa) {
            xoa();
        } else if (src == btnXoaRong) {
            xoaRong();
        } else if (src == btnTim) {
            timKiem();
        } else if (src == btnLuu) {
            luu();
        }
    }

    // === Thêm suất chiếu ===
    private void them() {
        String maSuat = txtMaSuat.getText().trim();
        String ngayChieuStr = txtNgayChieu.getText().trim();
        String thoiGianStr = txtThoiGian.getText().trim();
        String giaVeStr = txtGiaVe.getText().trim();
        double giaVe;
        // --- Kiểm tra dữ liệu hợp lệ ---
        if (maSuat.isEmpty() || ngayChieuStr.isEmpty() || thoiGianStr.isEmpty() || giaVeStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Kiểm tra định dạng ngày & giờ
        try {
            LocalDate.parse(ngayChieuStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Ngày chiếu không hợp lệ! (dd/MM/yyyy)", "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            LocalTime.parse(thoiGianStr, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Thời gian không hợp lệ! (HH:mm)", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            giaVe = Double.parseDouble(giaVeStr);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Giá vé phải là số!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (quanLySuatChieu_DAO.timSuatChieu(maSuat) != null) {
            JOptionPane.showMessageDialog(this, "Mã suất chiếu đã tồn tại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        quanLySuatChieu_DAO.addNewSuatChieu(new SuatChieu(
                maSuat,
                txtMaPhim.getText().trim(),
                cbPhong.getSelectedItem().toString(),
                LocalDate.parse(ngayChieuStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.parse(thoiGianStr, DateTimeFormatter.ofPattern("HH:mm")),
                giaVe));
        // Thêm dữ liệu vào bảng
        model.addRow(new Object[] {
                maSuat,
                txtMaPhim.getText().trim(),
                cbPhong.getSelectedItem(),
                ngayChieuStr,
                thoiGianStr,
                giaVe
        });
        capNhatBang();
        JOptionPane.showMessageDialog(this, "Thêm suất chiếu thành công!", "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
        xoaRong();
    }

    // === Sửa suất chiếu ===
    private void sua() {
        int row = tblSuatChieu.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần sửa!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        model.setValueAt(txtMaSuat.getText(), row, 0);
        model.setValueAt(txtMaPhim.getText().trim(), row, 1);
        model.setValueAt(txtTenPhim.getText().trim(), row, 2);
        model.setValueAt(cbPhong.getSelectedItem(), row, 3);
        model.setValueAt(txtNgayChieu.getText(), row, 4);
        model.setValueAt(txtThoiGian.getText(), row, 5);
        model.setValueAt(txtGiaVe.getText(), row, 6);
        // Cập nhật trong DAO
        quanLySuatChieu_DAO.updateSuatChieu(new SuatChieu(
                txtMaSuat.getText().trim(),
                txtMaPhim.getText().trim(),
                cbPhong.getSelectedItem().toString(),
                LocalDate.parse(txtNgayChieu.getText().trim(), DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                LocalTime.parse(txtThoiGian.getText().trim(), DateTimeFormatter.ofPattern("HH:mm")),
                Double.parseDouble(txtGiaVe.getText().trim())));
        JOptionPane.showMessageDialog(this, "Cập nhật thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // === Xóa suất chiếu ===
    private void xoa() {
        int row = tblSuatChieu.getSelectedRow();
        if (row != -1) {
            model.removeRow(row);
            quanLySuatChieu_DAO.removeSuatChieu(quanLySuatChieu_DAO.getSuatChieuByIndex(row));
            JOptionPane.showMessageDialog(this, "Xóa thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng cần xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
        }
    }

    // === Xóa trắng form ===
    private void xoaRong() {
        txtMaSuat.setText("");
        txtNgayChieu.setText("");
        txtThoiGian.setText("");
        txtGiaVe.setText("");
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        cbPhong.setSelectedIndex(0);
    }

    // === Tìm kiếm suất chiếu ===
    private void timKiem() {
        String maTim = txtTimSuat.getText().trim();
        if (maTim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã suất cần tìm!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equalsIgnoreCase(maTim)) {
                tblSuatChieu.setRowSelectionInterval(i, i);
                JOptionPane.showMessageDialog(this, "Đã tìm thấy suất chiếu!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    // === Giả lập lưu dữ liệu ===
    private void luu() {
        if (quanLySuatChieu_DAO.saveToDatabase()) {
            JOptionPane.showMessageDialog(this, "Đã lưu dữ liệu tạm thời (chưa kết nối CSDL).", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Lưu dữ liệu thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    // === Cập nhật bảng theo ngày được chọn ===
    private void capNhatBangTheoNgay(LocalDate ngayChon) {
        model.setRowCount(0); // Xóa dữ liệu cũ

        for (SuatChieu suat : quanLySuatChieu_DAO.getDanhSachSuatChieu()) {
            if (suat.getNgayChieu().isEqual(ngayChon)) {
                model.addRow(new Object[] {
                        suat.getMaSuatChieu(),
                        suat.getMaPhim(),
                        " ",
                        suat.getMaRap(),
                        suat.getNgayChieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        suat.getGioChieu().format(DateTimeFormatter.ofPattern("HH:mm")),
                        suat.getGiaVe()
                });
            }
        }

        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Không có suất chiếu nào trong ngày "
                    + ngayChon.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // === Lấy dữ liệu từ "database" ===
    public ArrayList<SuatChieu> laydulieu() {
        data = DataBase.FakeSuatChieuDB();

        // Giả lập lấy dữ liệu từ database
        for (SuatChieu suatChieu : data.getDanhSachSuatChieu()) {
            quanLySuatChieu_DAO.addNewSuatChieu(suatChieu);
        }
        System.out.println("So luong suat chieu: " + quanLySuatChieu_DAO.getDanhSachSuatChieu().size());
        return quanLySuatChieu_DAO.getDanhSachSuatChieu();
    }
}
