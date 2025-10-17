package gui;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import dao.QuanLyPhim_DAO;
import entity.Phim;

import java.awt.*;
import java.util.ArrayList;

public class QuanLyPhim extends JPanel {
    private JTable table;
    private DefaultTableModel model;
    private JTextField txtMaPhim, txtTenPhim, txtNhaSX, txtTheLoai, txtThoiLuong, txtQuocGia;
    private JTextField txtTimPhim;
    private JButton btnThem, btnSua, btnXoa, btnXoaRong, btnLuu, btnTim;
    private QuanLyPhim_DAO dao;
    private ArrayList<Phim> dsPhim;

    public QuanLyPhim() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ==== PANEL TRÊN (THÔNG TIN PHIM) ====
        JPanel pnNorth = new JPanel(new GridBagLayout());
        pnNorth.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1, true),
                "Thông tin phim",
                TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Segoe UI", Font.BOLD, 20),
                Color.DARK_GRAY
        ));
        pnNorth.setBackground(Color.WHITE);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 10, 6, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblMaPhim = new JLabel("Mã phim:");
        JLabel lblTenPhim = new JLabel("Tên phim:");
        JLabel lblNhaSX = new JLabel("Nhà sản xuất:");
        JLabel lblTheLoai = new JLabel("Thể loại:");
        JLabel lblThoiLuong = new JLabel("Thời lượng (phút):");
        JLabel lblQuocGia = new JLabel("Quốc gia:");

        Font lblFont = new Font("Segoe UI", Font.BOLD, 18);
        lblMaPhim.setFont(lblFont); lblTenPhim.setFont(lblFont);
        lblNhaSX.setFont(lblFont); lblTheLoai.setFont(lblFont);
        lblThoiLuong.setFont(lblFont); lblQuocGia.setFont(lblFont);

        txtMaPhim = new JTextField(40);
        txtTenPhim = new JTextField(40);
        txtNhaSX = new JTextField(40);
        txtTheLoai = new JTextField(40);
        txtThoiLuong = new JTextField(40);
        txtQuocGia = new JTextField(40);

        gbc.gridx = 0; gbc.gridy = 0; pnNorth.add(lblMaPhim, gbc);
        gbc.gridx = 1; pnNorth.add(txtMaPhim, gbc);
        gbc.gridx = 2; pnNorth.add(lblTenPhim, gbc);
        gbc.gridx = 3; pnNorth.add(txtTenPhim, gbc);

        gbc.gridx = 0; gbc.gridy = 1; pnNorth.add(lblNhaSX, gbc);
        gbc.gridx = 1; pnNorth.add(txtNhaSX, gbc);
        gbc.gridx = 2; pnNorth.add(lblTheLoai, gbc);
        gbc.gridx = 3; pnNorth.add(txtTheLoai, gbc);

        gbc.gridx = 0; gbc.gridy = 2; pnNorth.add(lblThoiLuong, gbc);
        gbc.gridx = 1; pnNorth.add(txtThoiLuong, gbc);
        gbc.gridx = 2; pnNorth.add(lblQuocGia, gbc);
        gbc.gridx = 3; pnNorth.add(txtQuocGia, gbc);

        add(pnNorth, BorderLayout.NORTH);

        // ==== BẢNG DỮ LIỆU ====
        model = new DefaultTableModel(new String[]{"Mã phim", "Tên phim", "Nhà SX", "Thể loại", "Thời lượng", "Quốc gia"}, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        table.setRowHeight(25);
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 18));
        table.getTableHeader().setBackground(new Color(240, 240, 240));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        add(scroll, BorderLayout.CENTER);

        // ==== PANEL DƯỚI (CÁC NÚT CHỨC NĂNG + TÌM KIẾM NGANG) ====
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnSouth.setBackground(Color.WHITE);

        // Tạo textfield tìm kiếm + nút tìm
        JLabel lblTim = new JLabel("Tìm mã phim:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTimPhim = new JTextField(15);
        txtTimPhim.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        btnTim = new JButton("Tìm");

        // Các nút chức năng chính
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoaRong = new JButton("Xóa rỗng");
        btnLuu = new JButton("Lưu");

        JButton[] arrBtns = {btnThem, btnSua, btnXoa, btnXoaRong, btnLuu, btnTim};
        Color[] colors = {
                new Color(46, 204, 113),  // xanh lá
                new Color(52, 152, 219),  // xanh dương
                new Color(231, 76, 60),   // đỏ
                new Color(155, 89, 182),  // tím
                new Color(241, 196, 15),  // vàng
                new Color(255, 140, 0)    // cam (tìm)
        };

        Font btnFont = new Font("Segoe UI", Font.BOLD, 20);
        for (int i = 0; i < arrBtns.length; i++) {
            arrBtns[i].setFont(btnFont);
            arrBtns[i].setBackground(colors[i]);
            arrBtns[i].setForeground(Color.WHITE);
            arrBtns[i].setFocusPainted(false);
            arrBtns[i].setPreferredSize(new Dimension(140, 45));
        }

        // Thêm vào cùng một hàng
        pnSouth.add(lblTim);
        pnSouth.add(txtTimPhim);
        pnSouth.add(btnTim);
        pnSouth.add(btnThem);
        pnSouth.add(btnSua);
        pnSouth.add(btnXoa);
        pnSouth.add(btnXoaRong);
        pnSouth.add(btnLuu);

        add(pnSouth, BorderLayout.SOUTH);

        dao = new QuanLyPhim_DAO();
        suaAction();

        btnThem.addActionListener(e -> themPhim());
        btnSua.addActionListener(e -> suaPhim());
        btnXoa.addActionListener(e -> xoaPhim());
        btnXoaRong.addActionListener(e -> xoaRongAction());
        btnLuu.addActionListener(e -> luuPhim());
        btnTim.addActionListener(e -> timPhim());
        table.getSelectionModel().addListSelectionListener(e -> hienThiTable());
    }

    private void themPhim() {
        try {
            Phim p = new Phim(
                    txtMaPhim.getText(),
                    txtTenPhim.getText(),
                    txtNhaSX.getText(),
                    txtTheLoai.getText(),
                    Integer.parseInt(txtThoiLuong.getText()),
                    txtQuocGia.getText()
            );
            dao.add(p);
            suaAction();
            xoaRongAction();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
        }
    }

    private void suaPhim() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            try {
                Phim p = dao.getPhim(index);
                p.setMaPhim(txtMaPhim.getText());
                p.setTenPhim(txtTenPhim.getText());
                p.setNhaSanXuat(txtNhaSX.getText());
                p.setTheLoai(txtTheLoai.getText());
                p.setThoiLuong(Integer.parseInt(txtThoiLuong.getText()));
                p.setQuocGia(txtQuocGia.getText());
                suaAction();
                xoaRongAction();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Chọn phim cần sửa!");
        }
    }

    private void xoaPhim() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            dsPhim.remove(index);
            suaAction();
            xoaRongAction();
        } else {
            JOptionPane.showMessageDialog(this, "Chọn phim cần xóa!");
        }
    }

    private void luuPhim() {
//        dao.save(); 
        JOptionPane.showMessageDialog(this, "Đã lưu danh sách phim thành công!");
    }

    private void timPhim() {
        int count = 0;
        try {
            String maPhimTim = txtTimPhim.getText().trim();

            if (maPhimTim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã phim cần tìm!");
                txtTimPhim.requestFocus();
                return;
            }
            Phim p = dao.timPhim(maPhimTim);
            if (p != null) {
                for (int i = 0; i < model.getRowCount(); i++) {
                    String id = model.getValueAt(i, 0).toString().trim();
                    if (id.equalsIgnoreCase(maPhimTim)) {
                        txtMaPhim.setText(model.getValueAt(i, 0).toString());
                        txtTenPhim.setText(model.getValueAt(i, 1).toString());
                        txtNhaSX.setText(model.getValueAt(i, 2).toString());
                        txtTheLoai.setText(model.getValueAt(i, 3).toString());
                        txtThoiLuong.setText(model.getValueAt(i, 4).toString());
                        txtQuocGia.setText(model.getValueAt(i, 5).toString());
                        table.setRowSelectionInterval(i, i);
                        table.scrollRectToVisible(table.getCellRect(i, 0, true));

                        count = 1;
                        return;
                    }
                }
            }
            if (count == 0) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy phim có mã: " + maPhimTim);
                txtTimPhim.requestFocus();
                txtTimPhim.selectAll();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm phim: " + e.getMessage());
            txtTimPhim.requestFocus();
            txtTimPhim.selectAll();
        }
    }

    private void hienThiTable() {
        int index = table.getSelectedRow();
        if (index >= 0) {
            Phim p = dsPhim.get(index);
            txtMaPhim.setText(p.getMaPhim());
            txtTenPhim.setText(p.getTenPhim());
            txtNhaSX.setText(p.getNhaSanXuat());
            txtTheLoai.setText(p.getTheLoai());
            txtThoiLuong.setText(String.valueOf(p.getThoiLuong()));
            txtQuocGia.setText(p.getQuocGia());
        }
    }

    private void suaAction() {
        model.setRowCount(0);
        dsPhim = dao.getDanhSachPhim();
        for (Phim p : dsPhim) {
            model.addRow(new Object[]{
                    p.getMaPhim(), p.getTenPhim(), p.getNhaSanXuat(),
                    p.getTheLoai(), p.getThoiLuong(), p.getQuocGia()
            });
        }
    }

    private void xoaRongAction() {
        txtMaPhim.setText(""); txtTenPhim.setText("");
        txtNhaSX.setText(""); txtTheLoai.setText("");
        txtThoiLuong.setText(""); txtQuocGia.setText("");
        txtTimPhim.setText("");
        txtMaPhim.requestFocus();
    }
}
