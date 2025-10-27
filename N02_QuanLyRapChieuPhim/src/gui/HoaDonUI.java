package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Dialog.ModalExclusionType;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.QuanLyCTHD_DAO;
import dao.QuanLyPhim_DAO;
import dao.QuanLySuatChieu_DAO;
import entity.ChiTietHoaDon;
import entity.HoaDon;
import entity.Phim;
import entity.SuatChieu;
import entity.Ve;

public class HoaDonUI extends JFrame {
    public HoaDonUI(HoaDon hoaDon, QuanLyCTHD_DAO cthdManager, QuanLyPhim_DAO movieManager,
            QuanLySuatChieu_DAO suatChieuManager, JPanel pOwner) {
        setSize(800, 600);
        setLocationRelativeTo(pOwner);
        setLayout(new BorderLayout());

        JPanel pNorth = new JPanel(new BorderLayout());
        JLabel lblHoaDon = new JLabel("HÓA ĐƠN BÁN VÉ");
        Font fTitleHoaDon = new Font("Arial", Font.BOLD, 20);
        lblHoaDon.setFont(fTitleHoaDon);
        lblHoaDon.setForeground(Color.RED);
        JPanel pTitle = new JPanel();
        pTitle.add(lblHoaDon);
        pNorth.add(pTitle, BorderLayout.NORTH);

        if (hoaDon == null || cthdManager == null) {
            JOptionPane.showMessageDialog(this, "Không có thông tin về hóa đơn này", "Thông báo",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JPanel pHoaDon = new JPanel();
        pHoaDon.setLayout(new BoxLayout(pHoaDon, BoxLayout.Y_AXIS));

        JLabel lblMaHD = new JLabel("Mã hóa đơn: " + hoaDon.getMaHoaDon());
        JLabel lblNgayLap = new JLabel("Ngày lập hóa đơn: " + hoaDon.getNgayLap().toString());
        JLabel lblSoLuongVe = new JLabel("Số lượng vé: " + hoaDon.getSoLuongVe());
        JLabel lblTongTien = new JLabel("Tổng tiền: " + hoaDon.getTongTien());

        JLabel lblNhanVien = new JLabel("Nhân viên bán vé: " + hoaDon.getNhanVien().getTenNV());
        JLabel lblSoDienThoai = new JLabel("Số liên hệ: " + hoaDon.getNhanVien().getSoDienThoai());
        JLabel lblDiaChiRap = new JLabel("Địa chỉ rạp chiếu: " +
                "12 Nguyễn Văn Bảo, Phường 4, Quận Gò Vấp, TP. Hồ Chí Minh");
        JLabel lblEmail = new JLabel("Email: " + "3TCinema@gmail.com");

        JLabel lblTenKhachHang = new JLabel("Khách hàng: " + hoaDon.getKhachHang().getHoTen());
        JLabel lblDiaChiKH = new JLabel("Địa chỉ: " + hoaDon.getKhachHang().getDiaChi());
        JLabel lblSoDienThoaiKH = new JLabel("Số điện thoại: " + hoaDon.getKhachHang().getSoDT());

        JPanel pThongTinNhanVien = new JPanel();
        pThongTinNhanVien.setLayout(new BoxLayout(pThongTinNhanVien, BoxLayout.Y_AXIS));
        pThongTinNhanVien.setBorder(BorderFactory.createTitledBorder("THÔNG TIN NHÀ CUNG CẤP DỊCH VỤ"));
        pThongTinNhanVien.add(Box.createVerticalStrut(5));
        pThongTinNhanVien.add(lblNhanVien);
        pThongTinNhanVien.add(Box.createVerticalStrut(5));
        pThongTinNhanVien.add(lblSoDienThoai);
        pThongTinNhanVien.add(Box.createVerticalStrut(5));
        pThongTinNhanVien.add(lblDiaChiRap);
        pThongTinNhanVien.add(Box.createVerticalStrut(5));
        pThongTinNhanVien.add(lblEmail);
        pThongTinNhanVien.add(Box.createVerticalStrut(5));

        JPanel pThongTinKhachHang = new JPanel();
        pThongTinKhachHang.setLayout(new BoxLayout(pThongTinKhachHang, BoxLayout.Y_AXIS));
        pThongTinKhachHang.setBorder(BorderFactory.createTitledBorder("THÔNG TIN KHÁCH HÀNG"));
        pThongTinKhachHang.add(Box.createVerticalStrut(5));
        pThongTinKhachHang.add(lblTenKhachHang);
        pThongTinKhachHang.add(Box.createVerticalStrut(5));
        pThongTinKhachHang.add(lblSoDienThoaiKH);
        pThongTinKhachHang.add(Box.createVerticalStrut(5));
        pThongTinKhachHang.add(lblDiaChiKH);
        pThongTinKhachHang.add(Box.createVerticalStrut(5));

        JPanel pThongTinHoaDon = new JPanel();
        pThongTinHoaDon.setLayout(new BoxLayout(pThongTinHoaDon, BoxLayout.Y_AXIS));
        pThongTinHoaDon.setBorder(BorderFactory.createTitledBorder("THÔNG TIN HÓA ĐƠN"));
        pThongTinHoaDon.add(Box.createVerticalStrut(5));
        pThongTinHoaDon.add(lblMaHD);
        pThongTinHoaDon.add(Box.createVerticalStrut(5));
        pThongTinHoaDon.add(lblNgayLap);
        pThongTinHoaDon.add(Box.createVerticalStrut(5));
        pThongTinHoaDon.add(lblSoLuongVe);
        pThongTinHoaDon.add(Box.createVerticalStrut(5));
        pThongTinHoaDon.add(lblTongTien);
        pThongTinHoaDon.add(Box.createVerticalStrut(5));

        pHoaDon.add(pThongTinNhanVien);
        pHoaDon.add(pThongTinKhachHang);
        pHoaDon.add(pThongTinHoaDon);

        pNorth.add(pHoaDon, BorderLayout.CENTER);
        add(pNorth, BorderLayout.NORTH);

        ArrayList<ChiTietHoaDon> cthdList = cthdManager.timCTHDTheoMaHoaDon(hoaDon.getMaHoaDon());
        Object[] columns = { "Mã vé", "Tên phim", "Số lượng", "Giá vé", "Thành tiền" };
        DefaultTableModel model = new DefaultTableModel(null, columns);
        for (ChiTietHoaDon cthd : cthdList) {
            Ve ve = cthd.getVe();
            // Tìm phim dựa vào suất chiếu
            SuatChieu suatChieu = suatChieuManager.timSuatChieu(ve.getMaSuatChieu());
            if (suatChieu != null) {
                Phim phim = movieManager.timPhim(suatChieu.getMaPhim());

                Object[] data = { ve.getMaVe(), phim.getTenPhim(), cthd.getSoLuong(), cthd.getGiaVe(),
                        cthd.tinhThanhTien() };
                model.addRow(data);
            }
        }
        JTable table = new JTable(model);
        JScrollPane scrollTable = new JScrollPane(table);

        JPanel pSouth = new JPanel();
        Font fButton = new Font("Arial", Font.BOLD, 16);

        JButton btnDone = new JButton("Xong");
        btnDone.setFont(fButton);
        btnDone.setBackground(Color.green);
        JButton btnPrint = new JButton("In hóa đơn");
        btnPrint.setFont(fButton);
        pSouth.add(btnPrint);
        pSouth.add(btnDone);

        btnDone.addActionListener(e -> close());
        btnPrint.addActionListener(e -> handleInHoaDon());

        add(pNorth, BorderLayout.NORTH);
        add(scrollTable, BorderLayout.CENTER);
        add(pSouth, BorderLayout.SOUTH);

        setModalExclusionType(ModalExclusionType.APPLICATION_EXCLUDE);
        setVisible(true);
    }

    private void handleInHoaDon() {
        JOptionPane.showMessageDialog(this, "In hóa đơn thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        return;
    }

    private void close() {
        this.dispose();
        return;
    }

}
