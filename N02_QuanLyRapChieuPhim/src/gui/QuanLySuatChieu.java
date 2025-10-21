package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import dao.QuanLySuatChieu_DAO;
import entity.SuatChieu;
import java.awt.*;
import java.awt.event.*;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuanLySuatChieu extends JPanel implements ActionListener, MouseListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private QuanLySuatChieu_DAO dao;

    JLabel lblMaSuat, lblMaPhim, lblMaRap, lblNgay, lblTim;
    JTextField txtMaSuat, txtMaPhim, txtMaRap, txtNgay, txtTimKiem;
    JButton btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem;

    public QuanLySuatChieu() {
        dao = new QuanLySuatChieu_DAO();
        setSize(950, 650);
        setLayout(new BorderLayout(0, 0));

        JPanel pNorth = new JPanel();
        pNorth.setBackground(new Color(220, 20, 60));
        add(pNorth, BorderLayout.NORTH);

        JLabel lblTieuDe = new JLabel("QUẢN LÝ SUẤT CHIẾU");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 26));
        lblTieuDe.setForeground(Color.WHITE);
        lblTieuDe.setVerticalAlignment(SwingConstants.BOTTOM);
        pNorth.add(lblTieuDe);

        Box b = Box.createVerticalBox();
        Box b1, b2, b3, b4, b5, b6;

        Dimension txtSize = new Dimension(260, 36);

        b.add(Box.createVerticalStrut(12));
        b.add(b1 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b1.add(lblMaSuat = new JLabel("Mã Suất Chiếu:"));
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtMaSuat = new JTextField());
        txtMaSuat.setPreferredSize(txtSize);
        txtMaSuat.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(b2 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b2.add(lblMaPhim = new JLabel("Mã Phim:"));
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtMaPhim = new JTextField());
        txtMaPhim.setPreferredSize(txtSize);
        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(b3 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b3.add(lblMaRap = new JLabel("Mã Rạp:"));
        b3.add(Box.createHorizontalStrut(10));
        b3.add(txtMaRap = new JTextField());
        txtMaRap.setPreferredSize(txtSize);
        txtMaRap.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(b4 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));
        b4.add(lblNgay = new JLabel("Ngày Chiếu (yyyy-MM-dd):"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtNgay = new JTextField());
        txtNgay.setPreferredSize(txtSize);
        txtNgay.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        b.add(b5 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(15));

        btnThem = new JButton("Thêm");
        btnTimKiem = new JButton("Tìm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnLamMoi = new JButton("Làm mới");
        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(300, 40));
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JButton[] buttons = {btnThem, btnSua, btnXoa, btnLamMoi, btnTimKiem};
        for (JButton btn : buttons) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
            btn.setBackground(new Color(230, 230, 230));
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(140, 44));
        }

        b5.add(btnThem);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnSua);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnXoa);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnLamMoi);
        b5.add(Box.createHorizontalStrut(18));
        b5.add(new JLabel("Tìm mã suất chiếu:"));
        b5.add(Box.createHorizontalStrut(10));
        b5.add(txtTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnTimKiem);

        b.add(b6 = Box.createHorizontalBox());
        b.add(Box.createVerticalStrut(10));

        String[] headers = "Mã Suất Chiếu;Mã Phim;Mã Rạp;Ngày Chiếu".split(";");
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        table.setRowHeight(28);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.setAutoCreateRowSorter(true);
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(880, 300));
        b6.add(scroll);

        Dimension lblSize = new Dimension(180, 30);
        lblMaSuat.setPreferredSize(lblSize);
        lblMaPhim.setPreferredSize(lblSize);
        lblMaRap.setPreferredSize(lblSize);
        lblNgay.setPreferredSize(lblSize);

        add(b, BorderLayout.CENTER);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        table.addMouseListener(this);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnXoa.addActionListener(this);
        btnTimKiem.addActionListener(this);
        btnLamMoi.addActionListener(this);

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        List<SuatChieu> list = dao.getDanhSachSuatChieu();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (SuatChieu s : list) {
            tableModel.addRow(new Object[]{
                    s.getMaSuatChieu(),
                    s.getMaPhim(),
                    s.getMaRap(),
                    s.getNgayChieu() != null ? s.getNgayChieu().format(fmt) : ""
            });
        }
    }

    private SuatChieu revertFromField() {
        String ma = txtMaSuat.getText().trim();
        String phim = txtMaPhim.getText().trim();
        String rap = txtMaRap.getText().trim();
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate ngay = LocalDate.parse(txtNgay.getText().trim(), fmt);
        return new SuatChieu(ma, phim, rap, ngay);
    }

    private boolean validData() {
        String ma = txtMaSuat.getText().trim();
        String phim = txtMaPhim.getText().trim();
        String rap = txtMaRap.getText().trim();
        String ngay = txtNgay.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã suất chiếu không được để trống");
            txtMaSuat.requestFocus();
            return false;
        }
        if (!ma.matches("^SC\\d+$")) {
            JOptionPane.showMessageDialog(this, "Mã suất chiếu phải bắt đầu bằng 'SC' theo sau là số");
            txtMaSuat.requestFocus();
            return false;
        }
        if (phim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã phim không được để trống");
            txtMaPhim.requestFocus();
            return false;
        }
        if (rap.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã rạp không được để trống");
            txtMaRap.requestFocus();
            return false;
        }
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(ngay, fmt);
        } catch (DateTimeException ex) {
            JOptionPane.showMessageDialog(this, "Ngày chiếu sai định dạng (yyyy-MM-dd)");
            txtNgay.requestFocus();
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
            JOptionPane.showMessageDialog(this, "Trùng mã suất chiếu");
            txtMaSuat.requestFocus();
        }
    }

    private void suaSuatChieu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn suất chiếu để sửa");
            return;
        }
        String maTable = tableModel.getValueAt(row, 0).toString().trim();
        String maField = txtMaSuat.getText().trim();
        if (!maTable.equalsIgnoreCase(maField)) {
            JOptionPane.showMessageDialog(this, "Không thể sửa mã suất chiếu");
            txtMaSuat.setText(maTable);
            return;
        }
        if (!validData()) return;
        SuatChieu sc = revertFromField();
        if (dao.suaSuatChieu(sc)) {
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            loadTable();
            clearForm();
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    private void xoaSuatChieu() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng để xóa");
            return;
        }
        String ma = tableModel.getValueAt(row, 0).toString();
        int hoi = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (hoi == JOptionPane.YES_OPTION) {
            if (dao.xoaSuatChieu(ma)) {
                tableModel.removeRow(row);
                JOptionPane.showMessageDialog(this, "Xóa thành công");
                clearForm();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại");
            }
        }
    }

    private void timSuatChieu() {
        String ma = txtTimKiem.getText().trim();
        SuatChieu sc = dao.timSuatChieu(ma);
        tableModel.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (sc != null) {
            tableModel.addRow(new Object[]{
                    sc.getMaSuatChieu(),
                    sc.getMaPhim(),
                    sc.getMaRap(),
                    sc.getNgayChieu() != null ? sc.getNgayChieu().format(fmt) : ""
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu có mã " + ma);
            txtTimKiem.requestFocus();
        }
    }

    private void clearForm() {
        txtMaSuat.setText("");
        txtMaPhim.setText("");
        txtMaRap.setText("");
        txtNgay.setText("");
        txtTimKiem.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) themSuatChieu();
        else if (e.getSource() == btnSua) suaSuatChieu();
        else if (e.getSource() == btnXoa) xoaSuatChieu();
        else if (e.getSource() == btnTimKiem) timSuatChieu();
        else if (e.getSource() == btnLamMoi) {
            loadTable();
            clearForm();
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaSuat.setText(tableModel.getValueAt(row, 0).toString());
            txtMaPhim.setText(tableModel.getValueAt(row, 1).toString());
            txtMaRap.setText(tableModel.getValueAt(row, 2).toString());
            txtNgay.setText(tableModel.getValueAt(row, 3).toString());
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
