/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author KINDLY
 */

import koneksi.Koneksi;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormDataMinuman extends JFrame {

    private JTable tblMinuman;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JComboBox<String> cmbKategori;
    private JLabel lblTotalData;

    public FormDataMinuman() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Data Minuman");
        setSize(1366, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel sidebar = new JPanel(null);
        sidebar.setBounds(0, 0, 230, 900);
        sidebar.setBackground(new Color(250, 250, 255));
        add(sidebar);

        JLabel lblLogo = new JLabel(getIcon("/assets/logo_minuman.png", 48, 48));
        lblLogo.setBounds(30, 35, 48, 48);
        sidebar.add(lblLogo);

        JLabel lblNama = new JLabel("<html><b>Inventaris</b><br>Minuman</html>");
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNama.setBounds(90, 32, 120, 55);
        sidebar.add(lblNama);

        JButton btnDashboard = menuButton("Dashboard", 30, 160, false);
        JButton btnData = menuButton("Data Minuman", 30, 215, true);
        JButton btnLaporan = menuButton("Laporan", 30, 270, false);
        JButton btnPengaturan = menuButton("Pengaturan", 30, 325, false);
        JButton btnLogout = menuButton("Logout", 30, 650, false);
        btnLogout.setForeground(Color.RED);

        sidebar.add(btnDashboard);
        sidebar.add(btnData);
        sidebar.add(btnLaporan);
        sidebar.add(btnPengaturan);
        sidebar.add(btnLogout);

        btnDashboard.addActionListener(e -> {
            new FormDashboard().setVisible(true);
            dispose();
        });

        btnLaporan.addActionListener(e -> {
            new FormLaporan().setVisible(true);
            dispose();
        });

        btnPengaturan.addActionListener(e -> {
            new FormPengaturan().setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> dispose());

        JLabel lblTitle = new JLabel("Data Minuman");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setBounds(300, 60, 300, 40);
        add(lblTitle);

        JLabel lblSub = new JLabel("Kelola data inventaris minuman");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSub.setBounds(300, 100, 350, 25);
        add(lblSub);

        txtCari = new JTextField();
        txtCari.setBounds(300, 155, 360, 38);
        add(txtCari);

        cmbKategori = new JComboBox<>(new String[]{
            "Semua Kategori", "Teh", "Jus", "Air Mineral", "Kopi", "Susu"
        });
        cmbKategori.setBounds(680, 155, 180, 38);
        add(cmbKategori);

        JButton btnCari = new JButton("Cari");
        btnCari.setBounds(880, 155, 90, 38);
        add(btnCari);

        JButton btnTambah = new JButton("+ Tambah");
        btnTambah.setBounds(1110, 155, 130, 38);
        btnTambah.setBackground(new Color(98, 55, 230));
        btnTambah.setForeground(Color.WHITE);
        add(btnTambah);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(300, 625, 110, 38);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(430, 625, 110, 38);
        btnDelete.setBackground(Color.RED);
        btnDelete.setForeground(Color.WHITE);
        add(btnDelete);

        model = new DefaultTableModel();
        model.addColumn("ID Minuman");
        model.addColumn("Kode Minuman");
        model.addColumn("Nama Minuman");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Harga");
        model.addColumn("Tanggal Masuk");

        tblMinuman = new JTable(model);
        tblMinuman.setRowHeight(32);

        JScrollPane scroll = new JScrollPane(tblMinuman);
        scroll.setBounds(300, 220, 940, 380);
        add(scroll);

        lblTotalData = new JLabel("Menampilkan 0 data");
        lblTotalData.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalData.setBounds(560, 630, 300, 25);
        add(lblTotalData);

        btnCari.addActionListener(e -> loadData());
        cmbKategori.addActionListener(e -> loadData());

        btnTambah.addActionListener(e -> {
            new FormTambahBarang(this).setVisible(true);
        });

        btnUpdate.addActionListener(e -> updateData());
        btnDelete.addActionListener(e -> deleteData());
    }

    private JButton menuButton(String text, int x, int y, boolean active) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 170, 42);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        if (active) {
            btn.setBackground(new Color(98, 55, 230));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(250, 250, 255));
            btn.setForeground(new Color(30, 30, 50));
        }

        return btn;
    }

    public void refreshData() {
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);

        String cari = txtCari.getText().trim();
        String kategori = cmbKategori.getSelectedItem().toString();

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT * FROM Barang WHERE (kodeBarang LIKE ? OR namaBarang LIKE ?)";

            if (!kategori.equals("Semua Kategori")) {
                sql += " AND kategori = ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + cari + "%");
            ps.setString(2, "%" + cari + "%");

            if (!kategori.equals("Semua Kategori")) {
                ps.setString(3, kategori);
            }

            ResultSet rs = ps.executeQuery();

            int total = 0;

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idBarang"),
                    rs.getString("kodeBarang"),
                    rs.getString("namaBarang"),
                    rs.getString("kategori"),
                    rs.getInt("stok"),
                    rs.getBigDecimal("harga"),
                    rs.getDate("tanggalMasuk")
                });
                total++;
            }

            lblTotalData.setText("Menampilkan " + total + " data");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

    private void updateData() {
        int row = tblMinuman.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate!");
            return;
        }

        int idBarang = Integer.parseInt(model.getValueAt(row, 0).toString());
        new FormUpdateBarang(this, idBarang).setVisible(true);
    }

    private void deleteData() {
        int row = tblMinuman.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            return;
        }

        int idBarang = Integer.parseInt(model.getValueAt(row, 0).toString());
        new FormDeleteBarang(this, idBarang).setVisible(true);
    }

    private ImageIcon getIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    public static void main(String[] args) {
        new FormDataMinuman().setVisible(true);
    }
}