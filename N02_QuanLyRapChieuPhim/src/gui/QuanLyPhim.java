package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import dao.QuanLyPhim_DAO;
import entity.Phim;

public class QuanLyPhim extends JPanel implements ActionListener, MouseListener {
    private DefaultTableModel tableModel;
    private JTable table;
    private QuanLyPhim_DAO phim_dao;

    // labels + fields
    JLabel lblMaPhim, lblTenPhim, lblNhaSX, lblTheLoai, lblThoiLuong, lblQuocGia;
    JTextField txtMaPhim, txtTenPhim, txtNhaSX, txtTheLoai, txtThoiLuong, txtQuocGia, txtTimKiem;
    // buttons
    JButton btnThem, btnXoaTrang, btnXoa1Dong, btnLamMoi, btnSua, btnTimKiem;

    public QuanLyPhim() {
        phim_dao = new QuanLyPhim_DAO();

        setLayout(new BorderLayout(0, 0));
        setSize(900, 600);

        // pnNorth (title)
        JPanel pNorth = new JPanel();
        pNorth.setBackground(new Color(220, 20, 60)); // giống style SuatChieu
        add(pNorth, BorderLayout.NORTH);
        JLabel lblTieuDe = new JLabel("QUẢN LÝ PHIM");
        lblTieuDe.setFont(new Font("Arial", Font.BOLD, 24));
        lblTieuDe.setForeground(Color.WHITE);
        pNorth.add(lblTieuDe);

        // main body (Box)
        Box b = Box.createVerticalBox();
        Box b1, b2, b3, b4, b5, b6;

        Dimension txtSize = new Dimension(260, 34); // kiểu giống SuatChieu

        b.add(Box.createVerticalStrut(12));
        b.add(b1 = Box.createHorizontalBox());
        b1.add(lblMaPhim = new JLabel("Mã phim:"));
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtMaPhim = new JTextField());
        txtMaPhim.setPreferredSize(txtSize);
        txtMaPhim.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        b.add(Box.createVerticalStrut(10));
        b.add(b2 = Box.createHorizontalBox());
        b2.add(lblTenPhim = new JLabel("Tên phim:"));
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtTenPhim = new JTextField());
        txtTenPhim.setPreferredSize(txtSize);
        txtTenPhim.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        b.add(Box.createVerticalStrut(10));
        b.add(b3 = Box.createHorizontalBox());
        b3.add(lblNhaSX = new JLabel("Nhà sản xuất:"));
        b3.add(Box.createHorizontalStrut(10));
        b3.add(txtNhaSX = new JTextField());
        txtNhaSX.setPreferredSize(txtSize);
        txtNhaSX.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        b.add(Box.createVerticalStrut(10));
        b.add(b4 = Box.createHorizontalBox());
        b4.add(lblTheLoai = new JLabel("Thể loại:"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtTheLoai = new JTextField());
        txtTheLoai.setPreferredSize(txtSize);
        txtTheLoai.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        b.add(Box.createVerticalStrut(10));
        b.add(b4 = Box.createHorizontalBox()); // reuse variable b4 for next row to keep ordering
        b4.add(lblThoiLuong = new JLabel("Thời lượng (phút):"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtThoiLuong = new JTextField());
        txtThoiLuong.setPreferredSize(txtSize);
        txtThoiLuong.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        b4.add(Box.createHorizontalStrut(20));
        b4.add(lblQuocGia = new JLabel("Quốc gia:"));
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtQuocGia = new JTextField());
        txtQuocGia.setPreferredSize(txtSize);
        txtQuocGia.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // buttons row
        b.add(Box.createVerticalStrut(15));
        b.add(b5 = Box.createHorizontalBox());

        btnThem = new JButton("Thêm");
        btnXoaTrang = new JButton("Xóa trắng");
        btnXoa1Dong = new JButton("Xóa 1 dòng");
        btnLamMoi = new JButton("Làm mới");
        btnSua = new JButton("Sửa");
        btnTimKiem = new JButton("Tìm kiếm");
        txtTimKiem = new JTextField();
        txtTimKiem.setPreferredSize(new Dimension(280, 34));
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        JButton[] btns = {btnThem, btnXoaTrang, btnXoa1Dong, btnLamMoi, btnSua};
        for (JButton btn : btns) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(new Color(240, 240, 240));
            btn.setPreferredSize(new Dimension(130, 38));
            btn.setFocusPainted(false);
        }

        b5.add(btnThem);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnXoaTrang);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnXoa1Dong);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnLamMoi);
        b5.add(Box.createHorizontalStrut(12));
        b5.add(btnSua);
        b5.add(Box.createHorizontalStrut(20));
        b5.add(new JLabel("Tìm mã phim:"));
        b5.add(Box.createHorizontalStrut(10));
        b5.add(txtTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnTimKiem);

        // table area
        b.add(Box.createVerticalStrut(10));
        b.add(b6 = Box.createHorizontalBox());

        String[] headers = "Mã phim;Tên phim;Nhà SX;Thể loại;Thời lượng;Quốc gia".split(";");
        tableModel = new DefaultTableModel(headers, 0);
        JScrollPane scroll = new JScrollPane(table = new JTable(tableModel));
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        scroll.setPreferredSize(new Dimension(880, 300));
        b6.add(scroll);

        // align labels
        Dimension lblSize = new Dimension(150, 28);
        lblMaPhim.setPreferredSize(lblSize);
        lblTenPhim.setPreferredSize(lblSize);
        lblNhaSX.setPreferredSize(lblSize);
        lblTheLoai.setPreferredSize(lblSize);
        lblThoiLuong.setPreferredSize(lblSize);
        lblQuocGia.setPreferredSize(lblSize);

        add(b, BorderLayout.CENTER);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // events
        table.addMouseListener(this);
        btnXoa1Dong.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnThem.addActionListener(this);
        btnSua.addActionListener(this);
        btnTimKiem.addActionListener(this);

        DocDuLieuVaoTable();
    }

    // Doc du lieu vao table
    private void DocDuLieuVaoTable() {
        tableModel.setRowCount(0);
        List<Phim> list = phim_dao.getDanhSachPhim(); // giữ tên theo DAO của bạn
        for (Phim p : list) {
            tableModel.addRow(new Object[]{
                    p.getMaPhim(),
                    p.getTenPhim(),
                    p.getNhaSanXuat(),
                    p.getTheLoai(),
                    p.getThoiLuong(),
                    p.getQuocGia()
            });
        }
        table.setModel(tableModel);
    }

    // revertFromField
    private Phim revertFromField() {
        String ma = txtMaPhim.getText().trim();
        String ten = txtTenPhim.getText().trim();
        String nsx = txtNhaSX.getText().trim();
        String theLoai = txtTheLoai.getText().trim();
        int thoiLuong = Integer.parseInt(txtThoiLuong.getText().trim());
        String quocGia = txtQuocGia.getText().trim();
        return new Phim(ma, ten, nsx, theLoai, thoiLuong, quocGia);
    }

    // validData
    public boolean validData() {
        String ma = txtMaPhim.getText().trim();
        String ten = txtTenPhim.getText().trim();
        String thoiluong = txtThoiLuong.getText().trim();

        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã phim không được để trống");
            txtMaPhim.requestFocus();
            return false;
        }
        if (!ma.matches("^P\\d+$")) {
            JOptionPane.showMessageDialog(this, "Mã phim phải bắt đầu bằng 'P' và theo sau là số");
            txtMaPhim.requestFocus();
            return false;
        }
        if (ten.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Tên phim không được để trống");
            txtTenPhim.requestFocus();
            return false;
        }
        if (thoiluong.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thời lượng không được để trống");
            txtThoiLuong.requestFocus();
            return false;
        }
        try {
            int t = Integer.parseInt(thoiluong);
            if (t <= 0) {
                JOptionPane.showMessageDialog(this, "Thời lượng phải lớn hơn 0");
                txtThoiLuong.requestFocus();
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Thời lượng phải là số nguyên");
            txtThoiLuong.requestFocus();
            return false;
        }
        return true;
    }

    // them phim
    public void themPhim() {
        if (!validData()) return;
        Phim p = revertFromField();
        if (phim_dao.create(p)) {
            tableModel.addRow(new Object[]{
                    p.getMaPhim(), p.getTenPhim(), p.getNhaSanXuat(),
                    p.getTheLoai(), p.getThoiLuong(), p.getQuocGia()
            });
            JOptionPane.showMessageDialog(this, "Thêm phim thành công");
            xoaTrang();
        } else {
            JOptionPane.showMessageDialog(this, "Trùng mã phim");
            txtMaPhim.requestFocus();
        }
    }

    // xoa phim
    public void xoaPhim() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn dòng để xóa");
            return;
        }
        String ma = table.getValueAt(row, 0).toString();
        int hoi = JOptionPane.showConfirmDialog(this, "Bạn chắc chắn muốn xóa chứ?", "Chú ý", JOptionPane.YES_NO_OPTION);
        if (hoi == JOptionPane.YES_OPTION) {
            if (phim_dao.xoaPhim(ma)) {
                tableModel.removeRow(row);
                JOptionPane.showMessageDialog(this, "Xóa thành công!");
                xoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Xóa thất bại!");
            }
        }
    }

    // tim phim
    public void timPhim() {
        String ma = txtTimKiem.getText().trim();
        if (ma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nhập mã phim cần tìm!");
            txtTimKiem.requestFocus();
            return;
        }
        Phim p = phim_dao.search(ma);
        tableModel.setRowCount(0);
        if (p != null) {
            tableModel.addRow(new Object[]{
                    p.getMaPhim(), p.getTenPhim(), p.getNhaSanXuat(),
                    p.getTheLoai(), p.getThoiLuong(), p.getQuocGia()
            });
        } else {
            JOptionPane.showMessageDialog(this, "Không tìm thấy phim có mã: " + ma);
            txtTimKiem.requestFocus();
        }
    }

    // sua phim
    public void suaPhim() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Chưa chọn phim để sửa");
            return;
        }
        String maTable = table.getValueAt(row, 0).toString().trim();
        String ma = txtMaPhim.getText().trim();
        if (!ma.equals(maTable)) {
            JOptionPane.showMessageDialog(this, "Không thể sửa mã phim");
            txtMaPhim.setText(maTable);
            txtMaPhim.requestFocus();
            return;
        }
        if (!validData()) return;
        Phim p = revertFromField();
        if (phim_dao.suaPhim(p)) {
            // cập nhật table
            tableModel.setValueAt(p.getTenPhim(), row, 1);
            tableModel.setValueAt(p.getNhaSanXuat(), row, 2);
            tableModel.setValueAt(p.getTheLoai(), row, 3);
            tableModel.setValueAt(p.getThoiLuong(), row, 4);
            tableModel.setValueAt(p.getQuocGia(), row, 5);
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
        } else {
            JOptionPane.showMessageDialog(this, "Cập nhật thất bại");
        }
    }

    // xoa trang
    public void xoaTrang() {
        txtMaPhim.setText("");
        txtTenPhim.setText("");
        txtNhaSX.setText("");
        txtTheLoai.setText("");
        txtThoiLuong.setText("");
        txtQuocGia.setText("");
        txtTimKiem.setText("");
        table.clearSelection();
    }

    // ActionListener
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnThem) themPhim();
        else if (e.getSource() == btnXoaTrang) xoaTrang();
        else if (e.getSource() == btnXoa1Dong) xoaPhim();
        else if (e.getSource() == btnTimKiem) timPhim();
        else if (e.getSource() == btnSua) suaPhim();
        else if (e.getSource() == btnLamMoi) {
            tableModel.setRowCount(0);
            DocDuLieuVaoTable();
            xoaTrang();
        }
    }

    // MouseListener
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaPhim.setText(tableModel.getValueAt(row, 0).toString());
            txtTenPhim.setText(tableModel.getValueAt(row, 1).toString());
            txtNhaSX.setText(tableModel.getValueAt(row, 2).toString());
            txtTheLoai.setText(tableModel.getValueAt(row, 3).toString());
            txtThoiLuong.setText(tableModel.getValueAt(row, 4).toString());
            txtQuocGia.setText(tableModel.getValueAt(row, 5).toString());
        }
    }

    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
