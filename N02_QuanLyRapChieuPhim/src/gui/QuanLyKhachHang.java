package gui; // Đảm bảo file này nằm trong package gui

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import dao.QuanLyKhachHang_DAO;
import entity.KhachHang;
import entity.LoadData;

public class QuanLyKhachHang extends JPanel implements ActionListener, MouseListener, LoadData {

    private QuanLyKhachHang_DAO kh_dao;
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtMaKH, txtHoTen, txtSoDT, txtDiaChi, txtTimKiem;
    private JComboBox<String> cboGioiTinh;
    private JButton btnThem, btnXoaTrang, btnXoa1Dong, btnLamMoi, btnSua, btnTimKiem;

    // Interface LoadData (dùng khi chuyển tab)
    @Override
    public void loadData() {
        DocDuLieuVaoTable();
        xoaTrang();
    }

    public QuanLyKhachHang() {
        // ==== CẤU HÌNH FORM ====
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // ==== TIÊU ĐỀ ====
        JPanel pNorth = new JPanel();
        pNorth.setBackground(new Color(30, 144, 255)); // Màu xanh dương
        JLabel lblTieuDe = new JLabel("QUẢN LÝ HỘI VIÊN");
        lblTieuDe.setForeground(Color.WHITE);
        lblTieuDe.setFont(new Font("Segoe UI", Font.BOLD, 24));
        pNorth.add(lblTieuDe);
        add(pNorth, BorderLayout.NORTH);

        // ==== NỘI DUNG CHÍNH ====
        Box b = Box.createVerticalBox(); // Box tổng chứa tất cả
        b.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // --- Định nghĩa thuộc tính chung ---
        Font fontTxt = new Font("Segoe UI", Font.PLAIN, 15);
        Dimension txtSize = new Dimension(200, 28);
        Insets margin = new Insets(4, 8, 4, 8);
        
        // Hàm lambda để style JTextField
        java.util.function.Consumer<JTextField> styleTextField = txt -> {
            txt.setFont(fontTxt);
            txt.setPreferredSize(txtSize);
            txt.setMargin(margin);
            txt.setAlignmentY(Component.CENTER_ALIGNMENT);
        };

        // ==== PANEL NHẬP LIỆU ====
        JPanel pInput = new JPanel();
        pInput.setLayout(new BoxLayout(pInput, BoxLayout.Y_AXIS));
        pInput.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY), "Thông tin hội viên"));

        Box b1, b2, b3, b4; // Các box cho nhập liệu

        // === MÃ KHÁCH HÀNG ===
        pInput.add(b1 = Box.createHorizontalBox());
        JLabel lblMaKH = new JLabel("Mã khách hàng:");
        lblMaKH.setFont(fontTxt);
        lblMaKH.setPreferredSize(new Dimension(120, 28));
        b1.add(lblMaKH);
        b1.add(Box.createHorizontalStrut(10));
        b1.add(txtMaKH = new JTextField());
        styleTextField.accept(txtMaKH);
        txtMaKH.setEditable(false); // Không cho sửa Mã
        pInput.add(Box.createVerticalStrut(8));

        // === HỌ TÊN ===
        pInput.add(b2 = Box.createHorizontalBox());
        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(fontTxt);
        lblHoTen.setPreferredSize(new Dimension(120, 28));
        b2.add(lblHoTen);
        b2.add(Box.createHorizontalStrut(10));
        b2.add(txtHoTen = new JTextField());
        styleTextField.accept(txtHoTen);
        pInput.add(Box.createVerticalStrut(8));

        // === GIỚI TÍNH + SĐT ===
        pInput.add(b3 = Box.createHorizontalBox());
        JLabel lblGioiTinh = new JLabel("Giới tính:");
        lblGioiTinh.setFont(fontTxt);
        lblGioiTinh.setPreferredSize(new Dimension(120, 28));
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
        b3.add(lblSoDT);
        b3.add(Box.createHorizontalStrut(10));
        b3.add(txtSoDT = new JTextField());
        styleTextField.accept(txtSoDT);
        pInput.add(Box.createVerticalStrut(8));

        // === ĐỊA CHỈ ===
        pInput.add(b4 = Box.createHorizontalBox());
        JLabel lblDiaChi = new JLabel("Địa chỉ:");
        lblDiaChi.setFont(fontTxt);
        lblDiaChi.setPreferredSize(new Dimension(120, 28));
        b4.add(lblDiaChi);
        b4.add(Box.createHorizontalStrut(10));
        b4.add(txtDiaChi = new JTextField());
        txtDiaChi.setPreferredSize(new Dimension(450, 28)); // Địa chỉ dài hơn
        txtDiaChi.setFont(fontTxt);
        txtDiaChi.setMargin(margin);
        txtDiaChi.setAlignmentY(Component.CENTER_ALIGNMENT);
        pInput.add(Box.createVerticalStrut(8)); // Thêm khoảng cách
        
        // Thêm panel Input vào Box tổng
        b.add(pInput);
        b.add(Box.createVerticalStrut(15));

        // ==== CÁC NÚT CHỨC NĂNG ====
        Box b5 = Box.createHorizontalBox();
        
        // (Lưu ý: Đảm bảo bạn có thư mục 'icon' với các file png này)
        btnThem = new JButton("Thêm");
        btnThem.setIcon(new ImageIcon("icon/add.png"));
        btnXoaTrang = new JButton("Xóa trắng");
        btnXoaTrang.setIcon(new ImageIcon("icon/clear.png"));
        btnXoa1Dong = new JButton("Xóa"); // Đổi tên cho ngắn
        btnXoa1Dong.setIcon(new ImageIcon("icon/delete.png"));
        btnLamMoi = new JButton("Làm mới");
        btnLamMoi.setIcon(new ImageIcon("icon/refresh.png"));
        btnSua = new JButton("Sửa");
        btnSua.setIcon(new ImageIcon("icon/edit.png"));
        btnTimKiem = new JButton("Tìm"); // Đổi tên cho ngắn
        btnTimKiem.setIcon(new ImageIcon("icon/search.png"));
        txtTimKiem = new JTextField();
        styleTextField.accept(txtTimKiem); // Style cho ô tìm kiếm

        // --- BẮT ĐẦU CHỈNH SỬA STYLE NÚT ---
        JButton[] btns = { btnThem, btnXoaTrang, btnXoa1Dong, btnLamMoi, btnSua, btnTimKiem };
        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(140, 36); // Kích thước đồng nhất

        for (JButton btn : btns) {
            btn.setFont(buttonFont);
            btn.setBackground(new Color(245, 245, 245)); // Màu nền xám nhạt mặc định
            btn.setForeground(Color.BLACK); // Màu chữ đen mặc định
            btn.setFocusPainted(false);
            btn.setAlignmentY(Component.CENTER_ALIGNMENT);
            
            // Đặt kích thước cố định
            btn.setPreferredSize(buttonSize);
            btn.setMinimumSize(buttonSize);
            btn.setMaximumSize(buttonSize);
        }

        // --- LÀM NỔI BẬT CÁC NÚT QUAN TRỌNG ---

        // Nút chính: Thêm (Màu xanh dương)
        btnThem.setBackground(new Color(0, 123, 255));
        btnThem.setForeground(Color.WHITE);

        // Nút Sửa: (Màu xanh lơ)
        btnSua.setBackground(new Color(23, 162, 184));
        btnSua.setForeground(Color.WHITE);
        
        // Nút Xóa: (Màu đỏ)
        btnXoa1Dong.setBackground(new Color(220, 53, 69));
        btnXoa1Dong.setForeground(Color.WHITE);

        // Nút Tìm kiếm: (Màu xám)
        btnTimKiem.setBackground(new Color(108, 117, 125));
        btnTimKiem.setForeground(Color.WHITE);
        
        // --- KẾT THÚC CHỈNH SỬA STYLE NÚT ---


        b5.add(btnThem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnXoaTrang);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnXoa1Dong);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnLamMoi);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnSua);
        b5.add(Box.createHorizontalStrut(20)); // Khoảng cách đến ô tìm kiếm

        JLabel lblTimKiem = new JLabel("Tìm mã KH:");
        lblTimKiem.setFont(fontTxt);
        lblTimKiem.setAlignmentY(Component.CENTER_ALIGNMENT);
        b5.add(lblTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(txtTimKiem);
        b5.add(Box.createHorizontalStrut(10));
        b5.add(btnTimKiem);
        b.add(b5); // Thêm Box nút vào Box tổng
        b.add(Box.createVerticalStrut(15));

        // === BẢNG DỮ LIỆU ===
        Box b6 = Box.createHorizontalBox();
        String[] headers = "Mã KH;Họ tên;Giới tính;Số ĐT;Địa chỉ".split(";");
        tableModel = new DefaultTableModel(headers, 0);
        table = new JTable(tableModel);
        table.setRowHeight(26);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 15));
        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(880, 260));
        b6.add(scroll);
        b.add(b6); // Thêm Box bảng vào Box tổng

        // Thêm Box tổng vào panel
        add(b, BorderLayout.CENTER);

        // === SỰ KIỆN ===
        table.addMouseListener(this);
        btnThem.addActionListener(this);
        btnXoaTrang.addActionListener(this);
        btnXoa1Dong.addActionListener(this);
        btnLamMoi.addActionListener(this);
        btnSua.addActionListener(this);
        btnTimKiem.addActionListener(this);

        // === TRẠNG THÁI BAN ĐẦU ===
        btnSua.setEnabled(false);
        btnXoa1Dong.setEnabled(false);

        // === LOAD DỮ LIỆU BAN ĐẦU ===
        kh_dao = new QuanLyKhachHang_DAO();
        DocDuLieuVaoTable();
    }

    /**
     * Đọc dữ liệu từ DAO và load vào table model
     */
    private void DocDuLieuVaoTable() {
        tableModel.setRowCount(0); // Xóa sạch dữ liệu cũ
        List<KhachHang> ds = kh_dao.getDanhSachKhachHang();
        if (ds == null) {
            JOptionPane.showMessageDialog(this, "Không thể kết nối hoặc không có dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        for (KhachHang kh : ds) {
            tableModel.addRow(new Object[] {
                    kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(),
                    kh.getSoDT(), kh.getDiaChi()
            });
        }
    }

    /**
     * Xóa trắng các trường nhập liệu
     */
    private void xoaTrang() {
        txtMaKH.setText("");
        txtHoTen.setText("");
        txtSoDT.setText("");
        txtDiaChi.setText("");
        cboGioiTinh.setSelectedIndex(0);
        txtTimKiem.setText("");
        table.clearSelection(); // Bỏ chọn dòng trên bảng

        // Vô hiệu hóa nút Sửa/Xóa
        btnSua.setEnabled(false);
        btnXoa1Dong.setEnabled(false);
        txtHoTen.requestFocus(); // Focus vào ô Họ tên
    }

    /**
     * Lấy dữ liệu từ form và kiểm tra tính hợp lệ
     * @return KhachHang (trả về null nếu dữ liệu không hợp lệ)
     */
    private KhachHang layKhachHangTuForm() {
        String hoTen = txtHoTen.getText().trim();
        String sdt = txtSoDT.getText().trim();
        String diaChi = txtDiaChi.getText().trim();
        String gioiTinh = cboGioiTinh.getSelectedItem().toString();

        if (hoTen.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Họ tên không được để trống!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtHoTen.requestFocus();
            return null;
        }
        
        // Kiểm tra SĐT (ví dụ đơn giản: phải là số và 10-11 chữ số)
        if (sdt.isEmpty() || !sdt.matches("\\d{10,11}")) {
             JOptionPane.showMessageDialog(this, "Số điện thoại không hợp lệ (phải là 10-11 số)!", "Lỗi", JOptionPane.ERROR_MESSAGE);
             txtSoDT.requestFocus();
             return null;
        }

        // Mã KH sẽ được lấy từ txtMaKH (khi Sửa) hoặc tạo mới (khi Thêm)
        String maKH = txtMaKH.getText().trim();
        if(maKH.isEmpty()) {
            maKH = QuanLyKhachHang_DAO.taoMaKHTuDong();
        }

        return new KhachHang(maKH, hoTen, gioiTinh, sdt, diaChi);
    }


    /**
     * Xử lý sự kiện cho các nút
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();

        if (src.equals(btnLamMoi)) {
            DocDuLieuVaoTable();
            xoaTrang();
            return;
        }

        if (src.equals(btnXoaTrang)) {
            xoaTrang();
            return;
        }

        if (src.equals(btnThem)) {
            xoaTrang(); // Xóa trắng form trước khi thêm
            KhachHang kh = layKhachHangTuForm();
            if (kh == null) return; // Dữ liệu không hợp lệ

            if (kh_dao.add(kh)) {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thành công!");
                DocDuLieuVaoTable();
                xoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (src.equals(btnSua)) {
            int row = table.getSelectedRow();
            if (row < 0) {
                 JOptionPane.showMessageDialog(this, "Bạn phải chọn một dòng để sửa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                 return;
            }
            // Đảm bảo mã KH trên form là mã của dòng đã chọn
            txtMaKH.setText(tableModel.getValueAt(row, 0).toString());

            KhachHang kh = layKhachHangTuForm();
            if (kh == null) return; // Dữ liệu không hợp lệ

            if (kh_dao.update(kh)) {
                JOptionPane.showMessageDialog(this, "Cập nhật thành công!");
                DocDuLieuVaoTable();
                xoaTrang();
            } else {
                JOptionPane.showMessageDialog(this, "Cập nhật thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return;
        }

        if (src.equals(btnXoa1Dong)) {
            int row = table.getSelectedRow();
            if (row < 0) {
                JOptionPane.showMessageDialog(this, "Bạn phải chọn một dòng để xóa!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String maKH = tableModel.getValueAt(row, 0).toString();
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa khách hàng " + maKH + "?",
                    "Xác nhận xóa", JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                if (kh_dao.delete(maKH)) {
                    JOptionPane.showMessageDialog(this, "Xóa thành công!");
                    DocDuLieuVaoTable();
                    xoaTrang();
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
            return;
        }

        if (src.equals(btnTimKiem)) {
            String maTim = txtTimKiem.getText().trim();
            if (maTim.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập mã khách hàng cần tìm!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            KhachHang kh = kh_dao.findKhachHang(maTim);
            if (kh != null) {
                tableModel.setRowCount(0); // Xóa bảng
                tableModel.addRow(new Object[] { // Thêm 1 dòng tìm thấy
                        kh.getMaKH(), kh.getHoTen(), kh.getGioiTinh(),
                        kh.getSoDT(), kh.getDiaChi()
                });
                xoaTrang(); // Xóa trắng form
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy khách hàng với mã " + maTim, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                DocDuLieuVaoTable(); // Load lại nếu không tìm thấy
            }
            return;
        }
    }

    /**
     * Xử lý sự kiện click chuột trên JTable
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        if (row >= 0) {
            txtMaKH.setText(tableModel.getValueAt(row, 0).toString());
            txtHoTen.setText(tableModel.getValueAt(row, 1).toString());
            cboGioiTinh.setSelectedItem(tableModel.getValueAt(row, 2).toString());
            txtSoDT.setText(tableModel.getValueAt(row, 3).toString());
            txtDiaChi.setText(tableModel.getValueAt(row, 4).toString());

            // Kích hoạt nút Sửa và Xóa
            btnSua.setEnabled(true);
            btnXoa1Dong.setEnabled(true);
        }
    }

    // Các sự kiện chuột không sử dụng
    @Override
    public void mousePressed(MouseEvent e) {}
    @Override
    public void mouseReleased(MouseEvent e) {}
    @Override
    public void mouseEntered(MouseEvent e) {}
    @Override
    public void mouseExited(MouseEvent e) {}

}