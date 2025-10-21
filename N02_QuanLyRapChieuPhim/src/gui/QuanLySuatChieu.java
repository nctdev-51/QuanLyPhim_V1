package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.QuanLySuatChieu_DAO;
import entity.SuatChieu;
import java.awt.*;
import java.awt.event.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class QuanLySuatChieu extends JPanel implements ActionListener, MouseListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private QuanLySuatChieu_DAO dao;

    JLabel lblMaSuat, lblMaPhim, lblMaRap, lblNgay, lblGio, lblTim;
    JTextField txtMaSuat, txtMaPhim, txtMaRap, txtNgay, txtGio, txtTimKiem;
    JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public QuanLySuatChieu() {
        dao = new QuanLySuatChieu_DAO();
        setSize(950, 650);
        setLayout(new BorderLayout(0, 0));

        JPanel pNorth = new JPanel();
        pNorth.setBackground(new Color(220, 20, 60));
        add(pNorth, BorderLayout.NORTH);

        JLabel lblTieuDe = new JLabel("QUẢN LÝ SUẤT CHIẾU");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 28));
        lblTieuDe.setForeground(Color.WHITE);
        pNorth.add(lblTieuDe);

        Box b = Box.createVerticalBox();
        Box b1, b2, b3, b4, b5, b6, b7;

        Dimension txtSize = new Dimension(260, 38);

        b.add(Box.createVerticalStrut(12));
        b.add(b1 = Box.createHorizontalBox());
        b1.add(lblMaSuat = new JLabel("Mã Suất Chiếu:"));
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtMaSuat = new JTextField());
        txtMaSuat.setPreferredSize(txtSize);
        txtMaSuat.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(Box.createVerticalStrut(10));
        b.add(b2 = Box.createHorizontalBox());
        b2.add(lblMaPhim = new JLabel("Mã Phim:"));
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtMaPhim = new JTextField());
        txtMaPhim.setPreferredSize(txtSize);
        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(Box.createVerticalStrut(10));
        b.add(b3 = Box.createHorizontalBox());
        b3.add(lblMaRap = new JLabel("Mã Rạp:"));
        b3.add(Box.createHorizontalStrut(10));
        b3.add(txtMaRap = new JTextField());
        txtMaRap.setPreferredSize(txtSize);
        txtMaRap.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(Box.createVerticalStrut(10));
        b.add(b4 = Box.createHorizontalBox());
        b4.add(lblNgay = new JLabel("Ngày Chiếu (yyyy-MM-dd):"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtNgay = new JTextField());
        txtNgay.setPreferredSize(txtSize);
        txtNgay.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(Box.createVerticalStrut(10));
        b.add(b7 = Box.createHorizontalBox());
        b7.add(lblGio = new JLabel("Giờ Chiếu (HH:mm):"));
        b7.add(Box.createHorizontalStrut(10));
        b7.add(txtGio = new JTextField());
        txtGio.setPreferredSize(txtSize);
        txtGio.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(Box.createVerticalStrut(15));
        b.add(b5 = Box.createHorizontalBox());

        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        btnTimKiem = new JButton("Tìm");
        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(280, 38));
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JButton[] btns = {btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem};
        for (JButton btn : btns) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(new Color(240, 240, 240));
            btn.setPreferredSize(new Dimension(130, 42));
            btn.setFocusPainted(false);
        }

        b5.add(btnThem);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnSua);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnXoa);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnLamMoi);
        b5.add(Box.createHorizontalStrut(20));
        b5.add(new JLabel("Tìm mã suất chiếu:"));
        b5.add(Box.createHorizontalStrut(10));
        b5.add(txtTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnTimKiem);

        b.add(Box.createVerticalStrut(10));
        b.add(b6 = Box.createHorizontalBox());

        String[] header = "Mã Suất Chiếu;Mã Phim;Mã Rạp;Ngày Chiếu;Giờ Chiếu".split(";");
        tableModel = new DefaultTableModel(header, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(880, 300));
        b6.add(scroll);

        Dimension lblSize = new Dimension(180, 30);
        lblMaSuat.setPreferredSize(lblSize);
        lblMaPhim.setPreferredSize(lblSize);
        lblMaRap.setPreferredSize(lblSize);
        lblNgay.setPreferredSize(lblSize);
        lblGio.setPreferredSize(lblSize);

        add(b, BorderLayout.CENTER);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        table.addMouseListener(this);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnTimKiem.addActionListener(this);

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");
        for (SuatChieu s : dao.getDanhSachSuatChieu()) {
            tableModel.addRow(new Object[]{
                    s.getMaSuatChieu(),
                    s.getMaPhim(),
                    s.getMaRap(),
                    s.getNgayChieu() != null ? s.getNgayChieu().format(f1) : "",
                    s.getGioChieu() != null ? s.getGioChieu().format(f2) : ""
            });
        }
    }

    private SuatChieu revertFromField() {
        String ma = txtMaSuat.getText().trim();
        String phim = txtMaPhim.getText().trim();
        String rap = txtMaRap.getText().trim();
        LocalDate ngay = LocalDate.parse(txtNgay.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        LocalTime gio = LocalTime.parse(txtGio.getText().trim(), DateTimeFormatter.ofPattern("HH:mm"));
        return new SuatChieu(ma, phim, rap, ngay, gio);
    }

    private boolean validData() {
        String ma = txtMaSuat.getText().trim();
        String phim = txtMaPhim.getText().trim();
        String rap = txtMaRap.getText().trim();
        String ngay = txtNgay.getText().trim();
        String gio = txtGio.getText().trim();

        if (ma.isEmpty() || phim.isEmpty() || rap.isEmpty() || ngay.isEmpty() || gio.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return false;
        }

        if (!ma.matches("^SC\\d+$")) {
            JOptionPane.showMessageDialog(this, "Mã suất chiếu phải bắt đầu bằng 'SC' và theo sau là số!");
            return false;
        }

        try {
            LocalDate.parse(ngay, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(this, "Ngày chiếu sai định dạng (yyyy-MM-dd)");
            return false;
        }

        try {
            LocalTime.parse(gio, DateTimeFormatter.ofPattern("HH:mm"));
        } catch (DateTimeException e) {
            JOptionPane.showMessageDialog(this, "Giờ chiếu sai định dạng (HH:mm)");
            return false;
        }
        return true;
    }

    private void themSuatChieu() {
        if (!validData()) return;
        SuatChieu sc = revertFromField();
        if (dao.addNewSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Trùng mã suất chiếu!");
        }
    }

    private void suaSuatChieu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn suất chiếu để sửa");
            return;
        }
        if (!validData()) return;
        SuatChieu sc = revertFromField();
        if (dao.suaSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu để sửa");
        }
    }

    private void xoaSuatChieu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn suất chiếu để xóa");
            return;
        }
        String ma = tableModel.getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa?", "Cảnh báo", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.xoaSuatChieu(ma)) {
                loadTable();
                clearForm();
                JOptionPane.showMessageDialog(this, "Xóa thành công");
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }

    private void timSuatChieu() {
        String ma = txtTimKiem.getText().trim();
        SuatChieu sc = dao.timSuatChieu(ma);
        tableModel.setRowCount(0);
        if (sc != null) {
            DateTimeFormatter f1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            DateTimeFormatter f2 = DateTimeFormatter.ofPattern("HH:mm");
            tableModel.addRow(new Object[]{
                    sc.getMaSuatChieu(),
                    sc.getMaPhim(),
                    sc.getMaRap(),
                    sc.getNgayChieu() != null ? sc.getNgayChieu().format(f1) : "",
                    sc.getGioChieu() != null ? sc.getGioChieu().format(f2) : ""
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu có mã: " + ma);
        }
    }

    private void clearForm() {
        txtMaSuat.setText("");
        txtMaPhim.setText("");
        txtMaRap.setText("");
        txtNgay.setText("");
        txtGio.setText("");
        txtTimKiem.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();
        if (o == btnThem) themSuatChieu();
        else if (o == btnSua) suaSuatChieu();
        else if (o == btnXoa) xoaSuatChieu();
        else if (o == btnLamMoi) {
            loadTable();
            clearForm();
        } else if (o == btnTimKiem) timSuatChieu();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaSuat.setText(tableModel.getValueAt(row, 0).toString());
            txtMaPhim.setText(tableModel.getValueAt(row, 1).toString());
            txtMaRap.setText(tableModel.getValueAt(row, 2).toString());
            txtNgay.setText(tableModel.getValueAt(row, 3).toString());
            txtGio.setText(tableModel.getValueAt(row, 4).toString());
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
