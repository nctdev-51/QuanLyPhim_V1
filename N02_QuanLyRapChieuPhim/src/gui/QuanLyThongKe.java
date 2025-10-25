package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import entity.SuatChieu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QuanLyThongKe extends JPanel implements ActionListener {
    private JTable tblThongKe;
    private JButton btnXem;
    // Summary labels (thống kê tổng quan)
    private JLabel lblTotalPhimValue, lblTotalVeValue, lblTotalDoanhThuValue;
    private JTextField txtTimKiem;
    private JButton btnTim;
    private JButton btnBaoCao;
    private JButton btnThemThuMuc; // nút thêm thư mục cho JTree
    private JTree treeNgayChieu;
    private DefaultMutableTreeNode root; // root của cây, lưu làm trường lớp
    private JLabel lblTotalNgayChieuValue;
    private DefaultTableModel model;

    public QuanLyThongKe() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // ===== NORTH - TIÊU ĐỀ =====
        JLabel lblTitle = new JLabel("BÁO CÁO THỐNG KÊ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(new Color(40, 40, 40));
        add(lblTitle, BorderLayout.NORTH);
        // === Cây ngày chiếu (bên trái) ===
        this.root = new DefaultMutableTreeNode("Quản lý suất chiếu");
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 04/10/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 12/09/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 05/08/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 14/07/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 02/06/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 05/05/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 30/04/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 02/03/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 05/02/2025"));
        this.root.add(new DefaultMutableTreeNode("Ngày chiếu: 29/01/2025"));

        treeNgayChieu = new JTree(new DefaultTreeModel(root));
        JScrollPane scrollTree = new JScrollPane(treeNgayChieu);
        scrollTree.setPreferredSize(new Dimension(200, 0));

        // === Sự kiện khi nhấn vào một node trong cây ===
        treeNgayChieu.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeNgayChieu.getLastSelectedPathComponent();
            if (selectedNode == null)
                return;

            String nodeText = selectedNode.toString();

            // Chỉ xử lý nếu node có dạng "Ngày chiếu: ..."
            if (nodeText.startsWith("Ngày chiếu:")) {
                String ngayStr = nodeText.replace("Ngày chiếu:", "").trim();

                try {
                    LocalDate ngayChon = LocalDate.parse(ngayStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    capNhatBangTheoNgay(ngayChon);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!", "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                }
            } else if (nodeText.equals("Quản lý suất chiếu")) {
                capNhatBang(); // Hiện lại tất cả
            }

        });

        add(scrollTree, BorderLayout.WEST);

        // ===== CENTER =====
        JPanel pnCenter = new JPanel(new BorderLayout(10, 10));
        pnCenter.setBackground(Color.WHITE);

        // --- CENTER TOP: Thống kê tổng quan (thay cho chọn ngày) ---
        JPanel pnDate = new JPanel(new GridLayout(1, 3, 12, 5));
        pnDate.setBackground(Color.WHITE);
        pnDate.setBorder(BorderFactory.createTitledBorder("Thống kê tổng quan"));

        Font sumTitleFont = new Font("Segoe UI", Font.BOLD, 14);
        Font sumValueFont = new Font("Segoe UI", Font.BOLD, 20);
        // Ngày chiếu
        JPanel p1 = new JPanel(new BorderLayout());
        p1.setBackground(Color.WHITE);
        JLabel lblTotalNgayChieu = new JLabel("Ngày chiếu", SwingConstants.CENTER);
        lblTotalNgayChieu.setFont(sumTitleFont);
        lblTotalNgayChieuValue = new JLabel("12/12/2020", SwingConstants.CENTER);
        lblTotalNgayChieuValue.setFont(sumValueFont);
        p1.add(lblTotalNgayChieu, BorderLayout.NORTH);
        p1.add(lblTotalNgayChieuValue, BorderLayout.CENTER);

        // Tổng số phim
        JPanel p2 = new JPanel(new BorderLayout());
        p2.setBackground(Color.WHITE);
        JLabel lblTotalPhim = new JLabel("Tổng số phim", SwingConstants.CENTER);
        lblTotalPhim.setFont(sumTitleFont);
        lblTotalPhimValue = new JLabel("0", SwingConstants.CENTER);
        lblTotalPhimValue.setFont(sumValueFont);
        p2.add(lblTotalPhim, BorderLayout.NORTH);
        p2.add(lblTotalPhimValue, BorderLayout.CENTER);

        // Tổng số vé đã bán
        JPanel p3 = new JPanel(new BorderLayout());
        p3.setBackground(Color.WHITE);
        JLabel lblTotalVe = new JLabel("Tổng số vé bán ra", SwingConstants.CENTER);
        lblTotalVe.setFont(sumTitleFont);
        lblTotalVeValue = new JLabel("0", SwingConstants.CENTER);
        lblTotalVeValue.setFont(sumValueFont);
        p3.add(lblTotalVe, BorderLayout.NORTH);
        p3.add(lblTotalVeValue, BorderLayout.CENTER);

        // Tổng doanh thu
        JPanel p4 = new JPanel(new BorderLayout());
        p4.setBackground(Color.WHITE);
        JLabel lblTotalDoanhThu = new JLabel("Tổng doanh thu (vnđ)", SwingConstants.CENTER);
        lblTotalDoanhThu.setFont(sumTitleFont);
        lblTotalDoanhThuValue = new JLabel("0", SwingConstants.CENTER);
        lblTotalDoanhThuValue.setFont(sumValueFont);
        p4.add(lblTotalDoanhThu, BorderLayout.NORTH);
        p4.add(lblTotalDoanhThuValue, BorderLayout.CENTER);

        pnDate.add(p1);
        pnDate.add(p2);
        pnDate.add(p3);
        pnDate.add(p4);

        pnCenter.add(pnDate, BorderLayout.NORTH);

        // --- CENTER BOTTOM: Bảng thống kê ---
        // Thêm cột "Mã phim" và cột "Ngày" vào bảng (đã loại bỏ cột số buổi chiếu)
        String[] columns = { "Mã phim", "Ngày", "Tên phim", "Số vé đã bán", "Tổng doanh thu (vnđ)" };
        Object[][] data = {
                { "MP001", "2025/10/01", "Những nụ hôn rực rỡ", "1231", "33.000.000" },
                { "MP002", "2025/09/15", "Avatar", "2342", "234.765.000" },
                { "MP003", "2025/08/20", "Tết này ai đến nhà mình", "4435", "455.456.400" },
                { "MP004", "2025/07/05", "Chuông reo là bắn", "2352", "54.678.000" },
                { "MP005", "2025/06/10", "Đẹp từng Centimet", "2453", "54.002.000" }
        };

        model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Không cho phép chỉnh sửa trực tiếp trong bảng
            }
        };
        tblThongKe = new JTable(model);
        tblThongKe.setRowHeight(25);
        tblThongKe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tblThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblThongKe.setGridColor(Color.LIGHT_GRAY);
        JScrollPane scroll = new JScrollPane(tblThongKe);

        // ===== Tính các số liệu tổng từ dữ liệu mẫu và cập nhật ô thống kê =====
        int totalPhim = model.getRowCount();
        int totalVe = 0;
        long totalDoanhThu = 0L;
        // Sau khi loại bỏ cột 'Số buổi chiếu', chỉ số cột hiện là:
        // 0=Mã phim,1=Ngày,2=Tên,3=Số vé,4=Doanh thu
        for (int i = 0; i < model.getRowCount(); i++) {
            String veStr = model.getValueAt(i, 3).toString().replaceAll("[^0-9]", "");
            if (!veStr.isEmpty())
                totalVe += Integer.parseInt(veStr);
            String doanhStr = model.getValueAt(i, 4).toString().replaceAll("[^0-9]", "");
            if (!doanhStr.isEmpty())
                totalDoanhThu += Long.parseLong(doanhStr);
        }

        lblTotalPhimValue.setText(String.valueOf(totalPhim));
        lblTotalVeValue.setText(String.valueOf(totalVe));
        DecimalFormat df = new DecimalFormat("#,###");
        lblTotalDoanhThuValue.setText(df.format(totalDoanhThu));

        pnCenter.add(scroll, BorderLayout.CENTER);

        add(pnCenter, BorderLayout.CENTER);

        // === Khu vực nút chức năng (dưới) ===
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnSouth.setBackground(Color.WHITE);

        JLabel lblTim = new JLabel("Tìm mã suất chiếu:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTimKiem = new JTextField(15);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        btnTim = new JButton("Tìm");
        btnBaoCao = new JButton("Lập Báo cáo");
        btnXem = new JButton("Xem");

        // nút thêm thư mục cho cây ngày chiếu
        btnThemThuMuc = new JButton("Thêm thư mục");

        JButton[] arrBtns = { btnBaoCao, btnXem, btnTim };
        Color[] colors = {
                new Color(46, 204, 113), // xanh lá
                new Color(52, 152, 219), // xanh dương
                new Color(231, 76, 60), // đỏ

        };

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        for (int i = 0; i < arrBtns.length; i++) {
            arrBtns[i].setFont(btnFont);
            arrBtns[i].setBackground(colors[i]);
            arrBtns[i].setForeground(Color.WHITE);
            arrBtns[i].setFocusPainted(false);
            arrBtns[i].setPreferredSize(new Dimension(150, 45));
            arrBtns[i].addActionListener(this); // 🔹 GẮN SỰ KIỆN CHO NÚT
        }

        // Style cho nút Thêm thư mục
        btnThemThuMuc.setFont(btnFont);
        btnThemThuMuc.setBackground(new Color(155, 89, 182));
        btnThemThuMuc.setForeground(Color.WHITE);
        btnThemThuMuc.setFocusPainted(false);
        btnThemThuMuc.setPreferredSize(new Dimension(150, 45));
        btnThemThuMuc.addActionListener(this);

        pnSouth.add(lblTim);
        pnSouth.add(txtTimKiem);
        pnSouth.add(btnTim);
        pnSouth.add(btnBaoCao);
        pnSouth.add(btnXem);
        pnSouth.add(btnThemThuMuc);

        add(pnSouth, BorderLayout.SOUTH);
    }

    private void capNhatBang() {

    }

    private void capNhatBangTheoNgay(LocalDate ngayChon) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object source = e.getSource();
        if (source == btnXem) {
            capNhatBang();
        } else if (source == btnTim) {
            timKiem();
        } else if (source == btnThemThuMuc) {
            themNodeNgayChieu();
        } else if (source == btnBaoCao) {
            // Xử lý sự kiện nút Lập Báo cáo
            JOptionPane.showMessageDialog(this, "Lập báo cáo thống kê.");
        }
    }

    private void timKiem() {
        String maTim = txtTimKiem.getText().trim();
        if (maTim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã suất cần tìm!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equalsIgnoreCase(maTim)) {
                tblThongKe.setRowSelectionInterval(i, i);
                JOptionPane.showMessageDialog(this, "Đã tìm thấy suất chiếu!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }

        JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void themNodeNgayChieu() {
        // Thêm node ngày chiếu: yêu cầu người dùng nhập ngày ở định dạng dd/MM/yyyy
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String defaultDate = LocalDate.now().format(fmt);
        while (true) {
            String dateInput = (String) JOptionPane.showInputDialog(this,
                    "Nhập ngày chiếu (dd/MM/yyyy):", "Thêm ngày chiếu",
                    JOptionPane.PLAIN_MESSAGE, null, null, defaultDate);
            if (dateInput == null) {
                // Người dùng hủy
                break;
            }
            dateInput = dateInput.trim();
            if (dateInput.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập ngày (dd/MM/yyyy).", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                continue;
            }
            try {
                LocalDate parsed = LocalDate.parse(dateInput, fmt);
                String nodeText = "Ngày chiếu: " + parsed.format(fmt);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(nodeText);
                root.add(newNode);
                DefaultTreeModel treeModel = (DefaultTreeModel) treeNgayChieu.getModel();
                treeModel.reload(root);
                // Mở rộng root để thấy node mới
                treeNgayChieu.expandPath(new javax.swing.tree.TreePath(root.getPath()));
                break;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ. Vui lòng nhập dd/MM/yyyy.",
                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    // (Các hàm tạo dữ liệu chọn ngày đã bị loại bỏ vì phần chọn ngày được thay thế
    // bằng thống kê tổng quan)

    // ====== MAIN TEST ======
    public static void main(String[] args) {
        JFrame frame = new JFrame("Báo cáo thống kê");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 500);
        frame.setLocationRelativeTo(null);
        frame.add(new QuanLyThongKe());
        frame.setVisible(true);
    }

}
