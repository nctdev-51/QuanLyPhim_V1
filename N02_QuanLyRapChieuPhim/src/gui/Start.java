package gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Start extends JFrame implements ActionListener {
    private JLabel lblNhanVien;
    private JButton btnTrangChu, btnBanVe, btnPhim, btnSuatChieu,
                    btnKhachHang, btnNhanVien, btnThongKe, btnDangXuat;

    public Start() {
        setTitle("Hệ thống quản lý bán vé rạp chiếu phim");
        setDefaultCloseOperation(EXIT_ON_CLOSE);	
        setSize(1200,600);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel pnWest = new JPanel(new BorderLayout());
        pnWest.setPreferredSize(new Dimension(220, 1000));
        pnWest.setBackground(new Color(255, 56, 56));
        pnWest.setBorder(BorderFactory.createLineBorder(Color.RED, 2));

        Box boxMenu = Box.createVerticalBox();
        pnWest.add(boxMenu);
        boxMenu.add(Box.createVerticalStrut(25));

        ImageIcon logoIcon = new ImageIcon("icon/logo.jpg");
        Image logoImg = logoIcon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
        JLabel lblLogo = new JLabel(new ImageIcon(logoImg));
        lblLogo.setAlignmentX(Component.CENTER_ALIGNMENT);
        boxMenu.add(lblLogo);
        boxMenu.add(Box.createVerticalStrut(15));

        lblNhanVien = new JLabel("Xin chào, " + DangNhap.tenNhanVien);
        lblNhanVien.setFont(new Font("Arial", Font.BOLD, 18));
        lblNhanVien.setForeground(Color.WHITE);
        lblNhanVien.setAlignmentX(Component.CENTER_ALIGNMENT);
        boxMenu.add(lblNhanVien);
        boxMenu.add(Box.createVerticalStrut(15));
        boxMenu.add(createSeparatorLine(3));

        Font fontButton = new Font("Arial", Font.BOLD, 18);
        Color bgMenu = pnWest.getBackground();

        btnTrangChu = new JButton("Trang chủ");
        btnBanVe = new JButton("Bán vé");
        btnPhim = new JButton("Phim");
        btnSuatChieu = new JButton("Suất chiếu");
        btnKhachHang = new JButton("Khách hàng");
        btnNhanVien = new JButton("Nhân viên");
        btnThongKe = new JButton("Thống kê");
        btnDangXuat = new JButton("Đăng xuất");

        JButton[] menuButtons = {
            btnTrangChu, btnBanVe, btnPhim, btnSuatChieu, btnKhachHang, btnNhanVien, btnThongKe
        };

        for (JButton btn : menuButtons) {
            btn.setBackground(bgMenu);
            btn.setForeground(Color.WHITE);
            btn.setFont(fontButton);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 55));
            btn.setBorderPainted(false);
		    btn.setFocusPainted(false);
            btn.setIconTextGap(5);
            if (btn == btnNhanVien) continue;
            btn.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    btn.setBackground(new Color(41, 128, 185)); 
                }
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    btn.setBackground(bgMenu); 
                }
            });
        }

        btnTrangChu.setIcon(resizeIcon("icon/home.png", 28, 28));
        btnBanVe.setIcon(resizeIcon("icon/ticket.png", 28, 28));
        btnPhim.setIcon(resizeIcon("icon/movie.png", 60, 28));
        btnSuatChieu.setIcon(resizeIcon("icon/schedule.png", 28, 28));
        btnKhachHang.setIcon(resizeIcon("icon/customer.png", 28, 28));
        btnNhanVien.setIcon(resizeIcon("icon/staff.png", 28, 28));
        btnThongKe.setIcon(resizeIcon("icon/stats.png", 28, 28));

        btnDangXuat.setBackground(Color.WHITE);
        btnDangXuat.setForeground(Color.BLACK);
        btnDangXuat.setFont(fontButton);
        btnDangXuat.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnDangXuat.setBorderPainted(false);
        btnDangXuat.setFocusPainted(false);

        boxMenu.add(Box.createVerticalStrut(10));
        for (JButton btn : menuButtons) {
            boxMenu.add(btn);
            boxMenu.add(Box.createVerticalStrut(10));
        }

        boxMenu.add(createSeparatorLine(3));
        boxMenu.add(Box.createVerticalStrut(8));
        boxMenu.add(btnDangXuat);

        add(pnWest, BorderLayout.WEST);

        JPanel pnCenter = new JPanel(new CardLayout());
        add(pnCenter, BorderLayout.CENTER);

        JPanel trangChuPanel = new TrangChu();
        JPanel banVePanel = new QuanLyBanVe();
        JPanel phimPanel = new QuanLyPhim();
        JPanel suatChieuPanel = new QuanLySuatChieu();
        JPanel khachHangPanel = new QuanLyKhachHang();
        JPanel nhanVienPanel = new QuanLyNhanVien();
        JPanel thongKePanel = new QuanLyThongKe();

        pnCenter.add(trangChuPanel, "TrangChu");
        pnCenter.add(banVePanel, "BanVe");
        pnCenter.add(phimPanel, "Phim");
        pnCenter.add(suatChieuPanel, "SuatChieu");
        pnCenter.add(khachHangPanel, "KhachHang");
        pnCenter.add(nhanVienPanel, "NhanVien");
        pnCenter.add(thongKePanel, "ThongKe");

        CardLayout cardLayout = (CardLayout) pnCenter.getLayout();
        btnTrangChu.addActionListener(e -> cardLayout.show(pnCenter, "TrangChu"));
        btnBanVe.addActionListener(e -> cardLayout.show(pnCenter, "BanVe"));
        btnPhim.addActionListener(e -> cardLayout.show(pnCenter, "Phim"));
        btnSuatChieu.addActionListener(e -> cardLayout.show(pnCenter, "SuatChieu"));
        btnKhachHang.addActionListener(e -> cardLayout.show(pnCenter, "KhachHang"));
        btnNhanVien.addActionListener(e -> cardLayout.show(pnCenter, "NhanVien"));
        btnThongKe.addActionListener(e -> cardLayout.show(pnCenter, "ThongKe"));
        btnDangXuat.addActionListener(this);

        hienThiPhanQuyen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnDangXuat) {
            new DangNhap();
            setVisible(false);
        }
    }

    public static ImageIcon resizeIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(path);
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public JPanel createSeparatorLine(int thickness) {
        JPanel line = new JPanel();
        line.setMaximumSize(new Dimension(Integer.MAX_VALUE, thickness));
        line.setPreferredSize(new Dimension(Integer.MAX_VALUE, thickness));
        line.setBackground(Color.WHITE);
        return line;
    }

    private void hienThiPhanQuyen() {
        if (DangNhap.vaiTro.equalsIgnoreCase("Nhân viên")) {
            btnNhanVien.setEnabled(false);
            btnNhanVien.setBackground(new Color(127, 140, 141));
            btnNhanVien.setToolTipText("Chức năng chỉ dành cho Quản lý");
        } else {
            btnNhanVien.setEnabled(true);
        }
    }

    public static void main(String[] args) {
        new Start().setVisible(true);
    }
}
