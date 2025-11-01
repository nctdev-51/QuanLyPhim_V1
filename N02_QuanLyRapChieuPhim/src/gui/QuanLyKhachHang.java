package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dao.QuanLyKhachHang_DAO;
import entity.KhachHang;

public class QuanLyKhachHang extends JFrame implements ActionListener, MouseListener {

    private QuanLyKhachHang_DAO kh_dao;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaKH, txtHoTen, txtSoDT, txtDiaChi, txtTimKiem;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem, btnXoaTrang, btnXoa1Dong, btnLamMoi, btnSua, btnTimKiem;

    public QuanLyKhachHang() {
        // ==== CẤU HÌNH FORM ====
        setTitle("Quản lý hội viên");
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, 600));

        // ==== TIÊU ĐỀ ====
        JPanel pNorth = new JPanel();
        pNorth.setBackground(new Color(30, 144, 255));
        JLabel lblTieuDe = new JLabel("QUẢN LÝ HỘI VIÊN");
        lblTieuDe.setForeground(Color.WHITE);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 24));
        pNorth.add(lblTieuDe);
        add(pNorth, BorderLayout.NORTH);

        // ==== NỘI DUNG CHÍNH ====
        Box b = Box.createVerticalBox();
        Box b1, b2, b3, b4, b5, b6;
        b.add(Box.createVerticalStrut(10));

        Font fontTxt = new Font("Segoe UI", Font.PLAIN, 15); 
        Dimension txtSize = new Dimension(200, 28); 
        Insets margin = new Insets(4, 8, 4, 8); 

        java.util.function.Consumer<JTextField> styleTextField = txt -> {
            txt.setFont(fontTxt);
            txt.setPreferredSize(txtSize);
            txt.setMargin(margin);
            txt.setAlignmentY(Component.CENTER_ALIGNMENT);
        };

        // === MÃ KHÁCH HÀNG ===
        b.add(b1 = Box.createHorizontalBox());
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setFont(fontTxt);
        lblMaKH.setPreferredSize(new Dimension(120, 28));
        lblMaKH.setAlignmentY(Component.CENTER_ALIGNMENT);
        b1.add(lblMaKH);
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtMaKH = new JTextField());
        styleTextField.accept(txtMaKH);
        b.add(Box.createVerticalStrut(8));

        // === HỌ TÊN ===
        b.add(b2 = Box.createHorizontalBox());
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(fontTxt);
        lblHoTen.setPreferredSize(new Dimension(120, 28));
        lblHoTen.setAlignmentY(Component.CENTER_ALIGNMENT);
        b2.add(lblHoTen);
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtHoTen = new JTextField());
        styleTextField.accept(txtHoTen);
        b.add(Box.createVerticalStrut(8));

        // === GIỚI TÍNH + SĐT ===
        b.add(b3 = Box.createHorizontalBox());
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(fontTxt);
        lblGioiTinh.setPreferredSize(new Dimension(120, 28));
        lblGioiTinh.setAlignmentY(Component.CENTER_ALIGNMENT);
        b3.add(lblGioiTinh);
        b3.add(Box.createHorizontalStrut(10));
        cboGioiTinh = new JComboBox<>(new String[] { "Nam", "Nữ" });
        cboGioiTinh.setFont(fontTxt);
        cboGioiTinh.setPreferredSize(new Dimension(100, 28));
        cboGioiTinh.setAlignmentY(Component.CENTER_ALIGNMENT);
        b3.add(cboGioiTinh);
        b3.add(Box.createHorizontalStrut(20));
        JLabel lblSoDT = new JLabel("Số điện thoại:");
        lblSoDT.setFont(fontTxt);
        lblSoDT.setPreferredSize(new Dimension(100, 28));
        lblSoDT.setAlignmentY(Component.CENTER_ALIGNMENT);
        b3.add(lblSoDT);
        b3.add(Box.createHorizontalStrut(10));
        b3.add(txtSoDT = new JTextField());
        styleTextField.accept(txtSoDT);
        b.add(Box.createVerticalStrut(8));

        // === ĐỊA CHỈ ===
        b.add(b4 = Box.createHorizontalBox());
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(fontTxt);
        lblDiaChi.setPreferredSize(new Dimension(120, 28));
        lblDiaChi.setAlignmentY(Component.CENTER_ALIGNMENT);
        b4.add(lblDiaChi);
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtDiaChi = new JTextField());
        txtDiaChi.setPreferredSize(new Dimension(450, 28));
        txtDiaChi.setFont(fontTxt);
        txtDiaChi.setMargin(margin);
        txtDiaChi.setAlignmentY(Component.CENTER_ALIGNMENT);
        b.add(Box.createVerticalStrut(15));

        // === CÁC NÚT CHỨC NĂNG ===
        b.add(b5 = Box.createHorizontalBox());
        btnThem = new JButton("Thêm");
        btnXoaTrang = new JButton("Xóa trắng");
        btnXoa1Dong = new JButton("Xóa 1 dòng");
        btnLamMoi = new JButton("Làm mới");
        btnSua = new JButton("Sửa");
        btnTimKiem = new JButton("Tìm kiếm");
        txtTimKiem = new JTextField();
        styleTextField.accept(txtTimKiem);

        JButton[] btns = { btnThem, btnXoaTrang, btnXoa1Dong, btnLamMoi, btnSua };
        for (JButton btn : btns) {
            btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
            btn.setBackground(new Color(245, 245, 245));
            btn.setFocusPainted(false);
            btn.setPreferredSize(new Dimension(110, 32));
            btn.setAlignmentY(Component.CENTER_ALIGNMENT);
        }

        b5.add(btnThem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnXoaTrang);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnXoa1Dong);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnLamMoi);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnSua);
        b5.add(Box.createHorizontalStrut(20));

        JLabel lblTimKiem = new JLabel("Tìm mã KH:");
        lblTimKiem.setFont(fontTxt);
        lblTimKiem.setAlignmentY(Component.CENTER_ALIGNMENT);
        b5.add(lblTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(txtTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnTimKiem);

        // === BẢNG DỮ LIỆU ===
        b.add(Box.createVerticalStrut(15));
        b.add(b6 = Box.createHorizontalBox());
        String[] headers = "Mã KH;Họ tên;Giới tính;Số ĐT;Địa chỉ".split(";");
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(880, 260));
        b6.add(scroll);

        add(b, BorderLayout.CENTER);
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // === SỰ KIỆN ===
        table.addMouseListener(this);
        for (JButton btn : btns)
            btn.addActionListener(this);
        btnTimKiem.addActionListener(this);

        // === LOAD DỮ LIỆU BAN ĐẦU ===
        kh_dao = new QuanLyKhachHang_DAO();
        DocDuLieuVaoTable();

        // === HIỂN THỊ FRAME ===
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void DocDuLieuVaoTable() {
        tableModel.setRowCount(0);
        List<KhachHang> ds = kh_dao.getDanhSachKhachHang();
        for (KhachHang kh : ds) {
            tableModel.addRow(new Object[]{
                    kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(),
                    kh.getSoDT(), kh.getDiaChi()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Xử lý các nút thêm, xóa, sửa, tìm kiếm...
    }

    @Override
    public void mouseClicked(MouseEvent e) { /* TODO */ }
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}
