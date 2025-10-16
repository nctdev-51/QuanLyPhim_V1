package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import entity.Ve;
import entity.SuatChieu;
import entity.Rap;
import entity.Phim;

public class QuanLyBanVe extends JPanel {
    private JComboBox<String> cbPhim, cbPhong, cbSuatChieu;
    private JButton btnChonGhe, btnDatVe, btnXoaChon;
    private JTextField txtTenPhim, txtTenPhong, txtThoiLuong, txtTheLoai, txtSuatChieu, txtSoGhe;
    private SuatChieu suatChieu;
    private ArrayList<String> roomIDSelectList;
    private ArrayList<String> showtimeSelectList;

    public QuanLyBanVe() {
        setLayout(new BorderLayout(10, 10));

        // ===== NORTH: Tiêu đề =====
        JLabel lblTitle = new JLabel("QUẢN LÝ BÁN VÉ", JLabel.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(Color.RED);
        add(lblTitle, BorderLayout.NORTH);

        JPanel pCenter = new JPanel();
        pCenter.setLayout(new BoxLayout(pCenter, BoxLayout.Y_AXIS));
        pCenter.setBorder(BorderFactory.createTitledBorder("CHỌN VÉ XEM PHIM"));

        cbPhim = new JComboBox<>(new String[]{"---Chọn phim---"});
        cbPhong = new JComboBox<>(new String[]{"---Chọn phòng---"});
        cbSuatChieu = new JComboBox<>(new String[]{"---Chọn suất chiếu---"});
        cbPhong.setEnabled(false);
        cbSuatChieu.setEnabled(false);

        JPanel pPhim = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pPhim.add(new JLabel("Phim:"));
        pPhim.add(cbPhim);
        pCenter.add(pPhim);

        JPanel pPhong = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pPhong.add(new JLabel("Phòng:"));
        pPhong.add(cbPhong);
        pCenter.add(pPhong);

        JPanel pSuat = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pSuat.add(new JLabel("Suất chiếu:"));
        pSuat.add(cbSuatChieu);
        btnChonGhe = new JButton("Chọn ghế");
        btnChonGhe.setEnabled(false);
        pSuat.add(btnChonGhe);
        pCenter.add(pSuat);

        txtTenPhim = new JTextField(20); txtTenPhim.setEditable(false);
        txtTenPhong = new JTextField(20); txtTenPhong.setEditable(false);
        txtThoiLuong = new JTextField(20); txtThoiLuong.setEditable(false);
        txtTheLoai = new JTextField(20); txtTheLoai.setEditable(false);
        txtSuatChieu = new JTextField(20); txtSuatChieu.setEditable(false);
        txtSoGhe = new JTextField(20); txtSoGhe.setEditable(false);

        JPanel infoPanel = new JPanel(new GridLayout(6,2,5,5));
        infoPanel.add(new JLabel("Tên phim:")); infoPanel.add(txtTenPhim);
        infoPanel.add(new JLabel("Phòng:")); infoPanel.add(txtTenPhong);
        infoPanel.add(new JLabel("Thời lượng:")); infoPanel.add(txtThoiLuong);
        infoPanel.add(new JLabel("Thể loại:")); infoPanel.add(txtTheLoai);
        infoPanel.add(new JLabel("Suất chiếu:")); infoPanel.add(txtSuatChieu);
        infoPanel.add(new JLabel("Ghế:")); infoPanel.add(txtSoGhe);
        pCenter.add(infoPanel);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnXoaChon = new JButton("Xóa lựa chọn");
        btnDatVe = new JButton("Xác nhận đặt vé");
        btnDatVe.setEnabled(false);
        btnPanel.add(btnXoaChon);
        btnPanel.add(btnDatVe);
        pCenter.add(btnPanel);

        add(pCenter, BorderLayout.CENTER);

        btnXoaChon.addActionListener(e -> resetForm());
        btnDatVe.addActionListener(e -> acceptTicket());
        btnChonGhe.addActionListener(e -> selectChair());

    }

    private void resetForm() {
        cbPhim.setSelectedIndex(0);
        cbPhong.setSelectedIndex(0);
        cbSuatChieu.setSelectedIndex(0);
        cbPhong.setEnabled(false);
        cbSuatChieu.setEnabled(false);
        btnChonGhe.setEnabled(false);
        btnDatVe.setEnabled(false);
        txtTenPhim.setText("");
        txtTenPhong.setText("");
        txtThoiLuong.setText("");
        txtTheLoai.setText("");
        txtSuatChieu.setText("");
        txtSoGhe.setText("");
    }

    private void acceptTicket() {
        JOptionPane.showMessageDialog(this, "Đặt vé thành công!");
        resetForm();
        Ve ve = new Ve("");
        ve.setMaVe("AUTO_GENERATE");
        ve.setDaThanhToan(true);
    }

    private void selectChair() {
        JOptionPane.showMessageDialog(this, "Chức năng chọn ghế sẽ được triển khai sau.");
    }
}
