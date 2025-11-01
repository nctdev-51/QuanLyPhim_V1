package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class QuanLyThongKe extends JFrame implements ActionListener {
    private JTable tblThongKe;
    private JButton btnXem, btnBaoCao, btnTim, btnThemThuMuc;
    private JLabel lblTotalPhimValue, lblTotalVeValue, lblTotalDoanhThuValue, lblTotalNgayChieuValue;
    private JTextField txtTimKiem;
    private JTree treeNgayChieu;
    private DefaultMutableTreeNode root;
    private DefaultTableModel model;

    public QuanLyThongKe() {
        setTitle("Báo cáo thống kê");
        setSize(1400, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        getContentPane().setBackground(Color.WHITE);

        // ===== NORTH =====
        JLabel lblTitle = new JLabel("BÁO CÁO THỐNG KÊ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(40, 40, 40));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ===== WEST (JTree ngày chiếu) =====
        root = new DefaultMutableTreeNode("Quản lý suất chiếu");
        String[] ngayMau = { "04/10/2025", "12/09/2025", "05/08/2025", "14/07/2025", "02/06/2025", "05/05/2025",
                "30/04/2025", "02/03/2025", "05/02/2025", "29/01/2025" };
        for (String ngay : ngayMau)
            root.add(new DefaultMutableTreeNode("Ngày chiếu: " + ngay));

        treeNgayChieu = new JTree(new DefaultTreeModel(root));
        JScrollPane scrollTree = new JScrollPane(treeNgayChieu);
        scrollTree.setPreferredSize(new Dimension(220, 0));
        add(scrollTree, BorderLayout.WEST);

        // === Lắng nghe chọn node ===
        treeNgayChieu.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNgayChieu.getLastSelectedPathComponent();
            if (node == null) return;
            String nodeText = node.toString();
            if (nodeText.startsWith("Ngày chiếu:")) {
                String ngayStr = nodeText.replace("Ngày chiếu:", "").trim();
                try {
                    LocalDate ngay = LocalDate.parse(ngayStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    capNhatBangTheoNgay(ngay);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } else capNhatBang();
        });

        // ===== CENTER =====
        JPanel pnCenter = new JPanel(new BorderLayout(10, 10));
        pnCenter.setBackground(Color.WHITE);
        add(pnCenter, BorderLayout.CENTER);

        // === Thống kê tổng quan ===
        JPanel pnThongKe = new JPanel(new GridLayout(1, 4, 12, 5));
        pnThongKe.setBackground(Color.WHITE);
        pnThongKe.setBorder(BorderFactory.createTitledBorder("Thống kê tổng quan"));

        Font fTitle = new Font("Segoe UI", Font.BOLD, 15);
        Font fValue = new Font("Segoe UI", Font.BOLD, 20);

        lblTotalNgayChieuValue = createThongKePanel(pnThongKe, "Ngày chiếu", "12/12/2020", fTitle, fValue);
        lblTotalPhimValue = createThongKePanel(pnThongKe, "Tổng số phim", "0", fTitle, fValue);
        lblTotalVeValue = createThongKePanel(pnThongKe, "Tổng số vé bán ra", "0", fTitle, fValue);
        lblTotalDoanhThuValue = createThongKePanel(pnThongKe, "Tổng doanh thu (vnđ)", "0", fTitle, fValue);

        pnCenter.add(pnThongKe, BorderLayout.NORTH);

        // === BẢNG DỮ LIỆU ===
        String[] columns = { "Mã phim", "Ngày", "Tên phim", "Số vé đã bán", "Tổng doanh thu (vnđ)" };
        Object[][] data = {
                { "MP001", "2025/10/01", "Những nụ hôn rực rỡ", "1231", "33.000.000" },
                { "MP002", "2025/09/15", "Avatar", "2342", "234.765.000" },
                { "MP003", "2025/08/20", "Tết này ai đến nhà mình", "4435", "455.456.400" },
                { "MP004", "2025/07/05", "Chuông reo là bắn", "2352", "54.678.000" },
                { "MP005", "2025/06/10", "Đẹp từng Centimet", "2453", "54.002.000" }
        };

        model = new DefaultTableModel(data, columns) {
            @Override public boolean isCellEditable(int row, int col) { return false; }
        };
        tblThongKe = new JTable(model);
        tblThongKe.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        tblThongKe.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        tblThongKe.setRowHeight(28);
        JScrollPane scrollTable = new JScrollPane(tblThongKe);
        pnCenter.add(scrollTable, BorderLayout.CENTER);

        tinhTongThongKe();

        // ===== SOUTH (nút chức năng) =====
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        pnSouth.setBackground(Color.WHITE);

        JLabel lblTim = new JLabel("Tìm mã suất chiếu:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 18));
        txtTimKiem = new JTextField(15);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        btnTim = new JButton("Tìm");
        btnBaoCao = new JButton("Lập Báo cáo");
        btnXem = new JButton("Xem");
        btnThemThuMuc = new JButton("Thêm ngày chiếu");

        JButton[] arrBtns = { btnTim, btnBaoCao, btnXem, btnThemThuMuc };
        Color[] colors = {
                new Color(231, 76, 60),
                new Color(46, 204, 113),
                new Color(52, 152, 219),
                new Color(155, 89, 182)
        };

        Font btnFont = new Font("Segoe UI", Font.BOLD, 16);
        for (int i = 0; i < arrBtns.length; i++) {
            arrBtns[i].setFont(btnFont);
            arrBtns[i].setBackground(colors[i]);
            arrBtns[i].setForeground(Color.WHITE);
            arrBtns[i].setFocusPainted(false);
            arrBtns[i].setPreferredSize(new Dimension(160, 45));
            arrBtns[i].addActionListener(this);
            pnSouth.add(arrBtns[i]);
        }

        pnSouth.add(lblTim);
        pnSouth.add(txtTimKiem);

        add(pnSouth, BorderLayout.SOUTH);
    }

    // ==================== CÁC HÀM XỬ LÝ ====================

    private JLabel createThongKePanel(JPanel parent, String title, String value, Font fTitle, Font fValue) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        JLabel lblT = new JLabel(title, SwingConstants.CENTER);
        lblT.setFont(fTitle);
        JLabel lblV = new JLabel(value, SwingConstants.CENTER);
        lblV.setFont(fValue);
        p.add(lblT, BorderLayout.NORTH);
        p.add(lblV, BorderLayout.CENTER);
        parent.add(p);
        return lblV;
    }

    private void tinhTongThongKe() {
        int totalPhim = model.getRowCount();
        int totalVe = 0;
        long totalDoanhThu = 0L;
        for (int i = 0; i < totalPhim; i++) {
            totalVe += Integer.parseInt(model.getValueAt(i, 3).toString().replaceAll("[^0-9]", ""));
            totalDoanhThu += Long.parseLong(model.getValueAt(i, 4).toString().replaceAll("[^0-9]", ""));
        }
        lblTotalPhimValue.setText(String.valueOf(totalPhim));
        lblTotalVeValue.setText(String.valueOf(totalVe));
        lblTotalDoanhThuValue.setText(new DecimalFormat("#,###").format(totalDoanhThu));
    }

    private void capNhatBang() {
        JOptionPane.showMessageDialog(this, "Đã tải lại toàn bộ dữ liệu thống kê!");
    }

    private void capNhatBangTheoNgay(LocalDate ngay) {
        lblTotalNgayChieuValue.setText(ngay.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        JOptionPane.showMessageDialog(this, "Hiển thị dữ liệu cho ngày: " + ngay);
    }

    private void timKiem() {
        String maTim = txtTimKiem.getText().trim();
        if (maTim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã suất cần tìm!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 0).toString().equalsIgnoreCase(maTim)) {
                tblThongKe.setRowSelectionInterval(i, i);
                tblThongKe.scrollRectToVisible(tblThongKe.getCellRect(i, 0, true));
                JOptionPane.showMessageDialog(this, "Đã tìm thấy suất chiếu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void themNodeNgayChieu() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String defaultDate = LocalDate.now().format(fmt);
        while (true) {
            String dateInput = JOptionPane.showInputDialog(this, "Nhập ngày chiếu (dd/MM/yyyy):", defaultDate);
            if (dateInput == null) break;
            try {
                LocalDate parsed = LocalDate.parse(dateInput.trim(), fmt);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode("Ngày chiếu: " + parsed.format(fmt));
                root.add(newNode);
                ((DefaultTreeModel) treeNgayChieu.getModel()).reload(root);
                treeNgayChieu.expandRow(0);
                break;
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ! Vui lòng nhập dd/MM/yyyy.",
                        "Lỗi định dạng", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnXem) capNhatBang();
        else if (src == btnTim) timKiem();
        else if (src == btnThemThuMuc) themNodeNgayChieu();
        else if (src == btnBaoCao) JOptionPane.showMessageDialog(this, "Đã lập báo cáo thống kê!");
    }

}