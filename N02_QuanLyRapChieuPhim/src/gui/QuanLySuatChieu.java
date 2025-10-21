package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Vector;

public class QuanLySuatChieu extends JPanel {

    private JTable tblSuatChieu;
    private JTextField txtMaSuat, txtNgayChieu, txtTimKiem;
    private JComboBox<String> cbPhim, cbPhong;
    private JTextField txtThoiGian;

    public QuanLySuatChieu() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ====== TIÊU ĐỀ ======
        JLabel lblTitle = new JLabel("Quản Lý Suất Chiếu", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ====== PANEL TRÊN: FORM NHẬP ======
        JPanel pnlTop = new JPanel(null);
        pnlTop.setPreferredSize(new Dimension(0, 130));
        pnlTop.setBackground(Color.WHITE);

        // Mã suất
        JLabel lblMaSuat = new JLabel("Mã suất:");
        lblMaSuat.setBounds(40, 10, 80, 25);
        pnlTop.add(lblMaSuat);

        txtMaSuat = new JTextField("SC812");
        txtMaSuat.setBounds(100, 10, 150, 25);
        pnlTop.add(txtMaSuat);

        // Phim
        JLabel lblPhim = new JLabel("Phim:");
        lblPhim.setBounds(280, 10, 80, 25);
        pnlTop.add(lblPhim);

        cbPhim = new JComboBox<>(new String[] { "P001", "P002", "P003" });
        cbPhim.setSelectedIndex(2);
        cbPhim.setBounds(330, 10, 150, 25);
        pnlTop.add(cbPhim);

        // Phòng
        JLabel lblPhong = new JLabel("Phòng:");
        lblPhong.setBounds(40, 50, 80, 25);
        pnlTop.add(lblPhong);

        cbPhong = new JComboBox<>(new String[] { "P001", "P002", "P003" });
        cbPhong.setSelectedIndex(2);
        cbPhong.setBounds(100, 50, 150, 25);
        pnlTop.add(cbPhong);

        // Thời gian
        JLabel lblThoiGian = new JLabel("Thời gian:");
        lblThoiGian.setBounds(280, 50, 80, 25);
        pnlTop.add(lblThoiGian);

        txtThoiGian = new JTextField("12/05/2025 10.00");
        txtThoiGian.setBounds(350, 50, 130, 25);
        pnlTop.add(txtThoiGian);

        // Ngày chiếu
        JLabel lblNgayChieu = new JLabel("Ngày chiếu:");
        lblNgayChieu.setBounds(40, 90, 80, 25);
        pnlTop.add(lblNgayChieu);

        txtNgayChieu = new JTextField("12/05/2025");
        txtNgayChieu.setBounds(120, 90, 130, 25);
        pnlTop.add(txtNgayChieu);

        add(pnlTop, BorderLayout.NORTH);

        // ====== BẢNG DỮ LIỆU ======
        String[] columnNames = { "Mã suất chiếu", "Phim", "Phòng", "Giờ chiếu", "Ngày" };
        Object[][] data = {
                { "SC001", "P001", "P001", "02/05/2025 19.00", "02/05/2025" },
                { "SC002", "P001", "P002", "03/05/2025 19.00", "03/05/2025" },
                { "SC003", "P002", "P001", "04/05/2025 19.00", "04/05/2025" },
                { "SC004", "P003", "P002", "05/05/2025 19.00", "05/05/2025" },
                { "SC005", "P001", "P002", "06/05/2025 19.00", "06/05/2025" },
                { "SC006", "P002", "P001", "07/05/2025 19.00", "07/05/2025" },
                { "SC007", "P003", "P001", "08/05/2025 19.00", "08/05/2025" },
                { "SC008", "P002", "P002", "09/05/2025 19.00", "09/05/2025" },
                { "SC009", "P003", "P003", "10/05/2025 19.00", "10/05/2025" },
                { "SC010", "P001", "P002", "11/05/2025 19.00", "11/05/2025" },
                { "SC811", "P003", "P003", "13/05/2025 10.00", "13/05/2025" },
                { "SC812", "P003", "P003", "12/05/2025 10.00", "12/05/2025" },
        };

        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        tblSuatChieu = new JTable(model);
        tblSuatChieu.setRowHeight(22);
        JScrollPane scrollPane = new JScrollPane(tblSuatChieu);
        add(scrollPane, BorderLayout.CENTER);

        // ====== PANEL DƯỚI: NÚT CHỨC NĂNG ======
        JPanel pnlBottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        JButton btnTimKiem = new JButton("Tìm kiếm");
        txtTimKiem = new JTextField(10);
        JButton btnThem = new JButton("Thêm");
        JButton btnSua = new JButton("Sửa");
        JButton btnXoa = new JButton("Xóa");
        JButton btnXoaRong = new JButton("Xóa Rỗng");

        pnlBottom.add(btnTimKiem);
        pnlBottom.add(txtTimKiem);
        pnlBottom.add(btnThem);
        pnlBottom.add(btnSua);
        pnlBottom.add(btnXoa);
        pnlBottom.add(btnXoaRong);

        add(pnlBottom, BorderLayout.SOUTH);
    }

    // ==== DÙNG MAIN ĐỂ CHẠY THỬ PANEL ====
    public static void main(String[] args) {
        JFrame f = new JFrame("Demo Quản Lý Suất Chiếu");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(800, 550);
        f.setLocationRelativeTo(null);
        f.add(new QuanLySuatChieu());
        f.setVisible(true);
    }
}
