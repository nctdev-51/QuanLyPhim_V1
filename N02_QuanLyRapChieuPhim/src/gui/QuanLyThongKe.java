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

import entity.LoadData;
import entity.SuatChieu;

public class QuanLyThongKe extends JPanel implements ActionListener, LoadData {
    private JTable tblThongKe;
    private JButton btnXem, btnBaoCao, btnTim, btnThemThuMuc;

    @Override
    public void loadData() {
        // TODO Auto-generated method stub
        capNhatBang();
    }

    private JLabel lblTotalPhimValue, lblTotalVeValue, lblTotalDoanhThuValue;
    private JTextField txtTimKiem;
    private JTree treeNgayChieu;
    private DefaultMutableTreeNode root;
    private DefaultTableModel model;

    public QuanLyThongKe() {
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);

        // ===== NORTH =====
        JLabel lblTitle = new JLabel("BÁO CÁO THỐNG KÊ", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitle.setForeground(new Color(220, 20, 60));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ===== WEST (JTree ngày chiếu) =====
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Danh sách Tháng chiếu");
        String[] thangList = {
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"
        };
        for (String thang : thangList) {
            root.add(new DefaultMutableTreeNode("Tháng: " + thang));
        }

        treeNgayChieu = new JTree(new DefaultTreeModel(root));
        JScrollPane scrollTree = new JScrollPane(treeNgayChieu);
        scrollTree.setPreferredSize(new Dimension(220, 0));
        add(scrollTree, BorderLayout.WEST);

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
        // -- Tổng phim --
        JPanel pnTotalPhim = new JPanel(new BorderLayout());
        pnTotalPhim.setBackground(Color.WHITE);
        JLabel lblTotalPhimTitle = new JLabel("Tổng số phim", SwingConstants.CENTER);
        lblTotalPhimTitle.setFont(fTitle);
        lblTotalPhimTitle.setForeground(Color.BLACK);
        lblTotalPhimValue = new JLabel("0", SwingConstants.CENTER);
        lblTotalPhimValue.setFont(fValue);
        lblTotalPhimValue.setForeground(Color.BLACK);
        pnTotalPhim.add(lblTotalPhimTitle, BorderLayout.NORTH);
        pnTotalPhim.add(lblTotalPhimValue, BorderLayout.CENTER);
        pnThongKe.add(pnTotalPhim);
        // -- Tổng vé --
        JPanel pnTotalVe = new JPanel(new BorderLayout());
        pnTotalVe.setBackground(Color.WHITE);
        JLabel lblTotalVeTitle = new JLabel("Tổng số vé đã bán", SwingConstants.CENTER);
        lblTotalVeTitle.setFont(fTitle);
        lblTotalVeTitle.setForeground(Color.BLACK);
        lblTotalVeValue = new JLabel("0", SwingConstants.CENTER);
        lblTotalVeValue.setFont(fValue);
        lblTotalVeValue.setForeground(Color.BLACK);
        pnTotalVe.add(lblTotalVeTitle, BorderLayout.NORTH);
        pnTotalVe.add(lblTotalVeValue, BorderLayout.CENTER);
        pnThongKe.add(pnTotalVe);
        // -- Tổng doanh thu --
        JPanel pnTotalDoanhThu = new JPanel(new BorderLayout());
        pnTotalDoanhThu.setBackground(Color.WHITE);
        JLabel lblTotalDoanhThuTitle = new JLabel("Tổng doanh thu (vnđ)", SwingConstants.CENTER);
        lblTotalDoanhThuTitle.setFont(fTitle);
        lblTotalDoanhThuTitle.setForeground(Color.BLACK);
        lblTotalDoanhThuValue = new JLabel("0", SwingConstants.CENTER);
        lblTotalDoanhThuValue.setFont(fValue);
        lblTotalDoanhThuValue.setForeground(Color.BLACK);
        pnTotalDoanhThu.add(lblTotalDoanhThuTitle, BorderLayout.NORTH);
        pnTotalDoanhThu.add(lblTotalDoanhThuValue, BorderLayout.CENTER);
        pnThongKe.add(pnTotalDoanhThu);

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
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
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
        pnSouth.add(lblTim);
        pnSouth.add(txtTimKiem);
        btnTim = new JButton("Tìm");
        btnBaoCao = new JButton("Lập Báo cáo");
        btnXem = new JButton("Xem");

        JButton[] arrBtns = { btnTim, btnBaoCao, btnXem };
        Color[] colors = {
                new Color(231, 76, 60),
                new Color(46, 204, 113),
                new Color(52, 152, 219)

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

        add(pnSouth, BorderLayout.SOUTH);
        capNhatBang();
        chonNut();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnXem)
            capNhatBang();
        else if (src == btnTim)
            timKiem();
        else if (src == btnThemThuMuc)
            JOptionPane.showMessageDialog(this, "Đã thêm nút mới!");
        else if (src == btnBaoCao)
            JOptionPane.showMessageDialog(this, "Đã lập báo cáo thống kê!");
    }

    // ==================== CÁC HÀM XỬ LÝ ====================
    private void chonNut() {
        // ===== Sự kiện chọn node trên cây =====
        treeNgayChieu.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNgayChieu.getLastSelectedPathComponent();
            if (node == null)
                return;
            String text = node.toString();
            if (text.startsWith("Tháng:")) {
                try {
                    // chỉ có tháng và không có ngày chiếu nên lấy tháng
                    String month = text.replace("Tháng:", "").trim();
                    capNhatBangTheoThang(month);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Định dạng tháng không hợp lệ!");
                }
            } else {
                capNhatBang();
            }
        });
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

    private void capNhatBangTheoThang(String monthStr) {
        // model.setRowCount(0);
        // for (SuatChieu suat : quanLySuatChieu_DAO.getAllSuatChieu()) {
        // // lọc theo tháng
        // if (suat.getNgayChieu().getMonthValue() == Integer.parseInt(monthStr)) {
        // model.addRow(new Object[] {
        // suat.getMaSuatChieu(),
        // suat.getMaPhim(),
        // tenPhim(suat.getMaPhim()),
        // suat.getMaRap(),
        // suat.getNgayChieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
        // suat.getGioChieu().format(DateTimeFormatter.ofPattern("HH:mm")),
        // suat.getGiaVe()
        // });
        // }
        // }
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
                tblThongKe.scrollRectToVisible(tblThongKe.getCellRect(i, 0, true));
                JOptionPane.showMessageDialog(this, "Đã tìm thấy suất chiếu!", "Thông báo",
                        JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(this, "Không tìm thấy suất chiếu!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame("Quản Lý Thống Kê");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 600);
        frame.setLocationRelativeTo(null);
        frame.setContentPane(new QuanLyThongKe());
        frame.setVisible(true);
    }
}