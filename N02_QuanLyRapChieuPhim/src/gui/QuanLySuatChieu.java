package gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import ConnectDB.DataBase;
import dao.QuanLyPhim_DAO;
import dao.QuanLySuatChieu_DAO;
import entity.SuatChieu;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class QuanLySuatChieu extends JFrame implements ActionListener {
    private QuanLySuatChieu_DAO quanLySuatChieu_DAO;
    private JTable tblSuatChieu;
    private JTextField txtMaSuat, txtNgayChieu, txtThoiGian, txtGiaVe, txtTimSuat, txtTenPhim, txtMaPhim;
    private JComboBox<String> cbPhong;
    private JButton btnThem, btnSua, btnXoa, btnXoaRong, btnLuu, btnTim;
    private DefaultTableModel model;
    private JTree treeNgayChieu;
    private QuanLySuatChieu_DAO data;
    protected QuanLyPhim_DAO dataPhim;

    public QuanLySuatChieu() {
        // ===== Cấu hình Frame =====
        setTitle("Quản lý Suất chiếu");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(1200, 700);
        setLocationRelativeTo(null);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        getContentPane().setBackground(Color.WHITE);
        setLayout(new BorderLayout());

        // ===== Khởi tạo DAO =====
        quanLySuatChieu_DAO = new QuanLySuatChieu_DAO();
        laydulieu();

        // ===== Tiêu đề =====
        JLabel lblTitle = new JLabel("QUẢN LÝ SUẤT CHIẾU", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Arial", Font.BOLD, 26));
        lblTitle.setForeground(new Color(220, 20, 60));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        add(lblTitle, BorderLayout.NORTH);

        // ===== Panel trái: Cây ngày chiếu =====
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Danh sách ngày chiếu");
        String[] ngayList = {
                "04/10/2025", "12/09/2025", "05/08/2025", "14/07/2025",
                "02/06/2025", "05/05/2025", "30/04/2025", "02/03/2025",
                "05/02/2025", "29/01/2025"
        };
        for (String ngay : ngayList) {
            root.add(new DefaultMutableTreeNode("Ngày chiếu: " + ngay));
        }

        treeNgayChieu = new JTree(new DefaultTreeModel(root));
        JScrollPane scrollTree = new JScrollPane(treeNgayChieu);
        scrollTree.setPreferredSize(new Dimension(220, 0));
        add(scrollTree, BorderLayout.WEST);

        // ===== Panel trung tâm =====
        JPanel pnBody = new JPanel(new BorderLayout());
        pnBody.setBackground(Color.WHITE);
        pnBody.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(pnBody, BorderLayout.CENTER);

        // ===== Form nhập liệu =====
        JPanel pnForm = new JPanel(new GridLayout(4, 4, 10, 10));
        pnForm.setBackground(Color.WHITE);

        pnForm.add(new JLabel("Mã Suất Chiếu:"));
        txtMaSuat = new JTextField();
        pnForm.add(txtMaSuat);

        pnForm.add(new JLabel("Mã Phim:"));
        txtMaPhim = new JTextField();
        pnForm.add(txtMaPhim);

        pnForm.add(new JLabel("Tên Phim:"));
        txtTenPhim = new JTextField();
        pnForm.add(txtTenPhim);

        pnForm.add(new JLabel("Giá vé:"));
        txtGiaVe = new JTextField();
        pnForm.add(txtGiaVe);

        pnForm.add(new JLabel("Ngày Chiếu (dd/MM/yyyy):"));
        txtNgayChieu = new JTextField();
        pnForm.add(txtNgayChieu);

        pnForm.add(new JLabel("Thời Gian (HH:mm):"));
        txtThoiGian = new JTextField();
        pnForm.add(txtThoiGian);

        pnForm.add(new JLabel("Phòng chiếu:"));
        cbPhong = new JComboBox<>(new String[]{
                "phong01", "phong02", "phong03", "phong04", "phong05",
                "phong06", "phong07", "phong08", "phong09", "phong10"
        });
        pnForm.add(cbPhong);

        pnBody.add(pnForm, BorderLayout.NORTH);

        // ===== Bảng suất chiếu =====
        model = new DefaultTableModel(
                new String[]{"Mã Suất", "Mã Phim", "Tên Phim", "Phòng", "Ngày chiếu", "Thời gian", "Giá vé"}, 0
        ) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tblSuatChieu = new JTable(model);
        tblSuatChieu.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        tblSuatChieu.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 17));
        tblSuatChieu.setRowHeight(26);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tblSuatChieu.getColumnCount(); i++) {
            tblSuatChieu.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        pnBody.add(new JScrollPane(tblSuatChieu), BorderLayout.CENTER);

        // ===== Panel dưới: nút chức năng =====
        JPanel pnSouth = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 12));
        pnSouth.setBackground(Color.WHITE);
        add(pnSouth, BorderLayout.SOUTH);

        JLabel lblTim = new JLabel("Tìm suất:");
        lblTim.setFont(new Font("Segoe UI", Font.BOLD, 16));
        txtTimSuat = new JTextField(15);

        btnTim = new JButton("Tìm");
        btnThem = new JButton("Thêm");
        btnSua = new JButton("Sửa");
        btnXoa = new JButton("Xóa");
        btnXoaRong = new JButton("Xóa rỗng");
        btnLuu = new JButton("Lưu");

        JButton[] arrBtns = {btnTim, btnThem, btnSua, btnXoa, btnXoaRong, btnLuu};
        Color[] colors = {
                new Color(255, 140, 0),
                new Color(46, 204, 113),
                new Color(52, 152, 219),
                new Color(231, 76, 60),
                new Color(155, 89, 182),
                new Color(241, 196, 15)
        };
        for (int i = 0; i < arrBtns.length; i++) {
            arrBtns[i].setBackground(colors[i]);
            arrBtns[i].setForeground(Color.WHITE);
            arrBtns[i].setFocusPainted(false);
            arrBtns[i].setPreferredSize(new Dimension(120, 40));
            arrBtns[i].setFont(new Font("Segoe UI", Font.BOLD, 16));
            arrBtns[i].addActionListener(this);
            pnSouth.add(arrBtns[i]);
        }

        pnSouth.add(lblTim);
        pnSouth.add(txtTimSuat);

        // ===== Gọi hàm hiển thị dữ liệu =====
        capNhatBang();

        // ===== Sự kiện chọn node trên cây =====
        treeNgayChieu.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) treeNgayChieu.getLastSelectedPathComponent();
            if (node == null) return;
            String text = node.toString();
            if (text.startsWith("Ngày chiếu:")) {
                try {
                    LocalDate date = LocalDate.parse(text.replace("Ngày chiếu:", "").trim(),
                            DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                    capNhatBangTheoNgay(date);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Định dạng ngày không hợp lệ!");
                }
            } else {
                capNhatBang();
            }
        });
    }

    // ================= Các hàm xử lý ====================
    private String tenPhim(String maPhim) {
        dataPhim = new QuanLyPhim_DAO();
        return dataPhim.timPhimTheoMa(maPhim).getTenPhim();
    }

    private void capNhatBang() {
        model.setRowCount(0);
        for (SuatChieu suat : quanLySuatChieu_DAO.getDanhSachSuatChieu()) {
            model.addRow(new Object[]{
                    suat.getMaSuatChieu(),
                    suat.getMaPhim(),
                    tenPhim(suat.getMaPhim()),
                    suat.getMaRap(),
                    suat.getNgayChieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    suat.getGioChieu().format(DateTimeFormatter.ofPattern("HH:mm")),
                    suat.getGiaVe()
            });
        }
    }

    private void capNhatBangTheoNgay(LocalDate ngay) {
        model.setRowCount(0);
        for (SuatChieu suat : quanLySuatChieu_DAO.getDanhSachSuatChieu()) {
            if (suat.getNgayChieu().isEqual(ngay)) {
                model.addRow(new Object[]{
                        suat.getMaSuatChieu(),
                        suat.getMaPhim(),
                        tenPhim(suat.getMaPhim()),
                        suat.getMaRap(),
                        suat.getNgayChieu().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                        suat.getGioChieu().format(DateTimeFormatter.ofPattern("HH:mm")),
                        suat.getGiaVe()
                });
            }
        }
    }

    public ArrayList<SuatChieu> laydulieu() {
        data = DataBase.FakeSuatChieuDB();
        for (SuatChieu s : data.getDanhSachSuatChieu()) {
            quanLySuatChieu_DAO.addNewSuatChieu(s);
        }
        return quanLySuatChieu_DAO.getDanhSachSuatChieu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == btnThem) {
            // gọi hàm thêm
        } else if (src == btnSua) {
            // gọi hàm sửa
        } else if (src == btnXoa) {
            // gọi hàm xóa
        } else if (src == btnXoaRong) {
            // gọi hàm xóa rỗng
        } else if (src == btnTim) {
            // gọi hàm tìm
        } else if (src == btnLuu) {
            // gọi hàm lưu
        }
    }
}
