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

public class FormDashboard extends JFrame {

    private JLabel lblTotalBarang, lblTotalStok, lblStokMenipis, lblStokHabis, lblTotalKategori;

    public FormDashboard() {
        initComponents();
        loadDashboard();
    }

    private void initComponents() {
        setTitle("Dashboard");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel sidebar = new JPanel();
        sidebar.setBounds(0, 0, 180, 600);
        sidebar.setBackground(new Color(250, 250, 255));
        sidebar.setLayout(null);
        add(sidebar);

        JLabel lblLogo = new JLabel("Inventaris\nMinuman");
        lblLogo.setText("<html><b>Inventaris</b><br>Minuman</html>");
        lblLogo.setBounds(35, 30, 120, 50);
        sidebar.add(lblLogo);

        JButton btnDashboard = menuButton("Dashboard", 25, 120, true);
        JButton btnData = menuButton("Data Barang", 25, 170, false);
        JButton btnLaporan = menuButton("Laporan", 25, 220, false);
        JButton btnPengaturan = menuButton("Pengaturan", 25, 270, false);
        JButton btnLogout = menuButton("Logout", 25, 500, false);

        btnLogout.setForeground(Color.RED);

        sidebar.add(btnDashboard);
        sidebar.add(btnData);
        sidebar.add(btnLaporan);
        sidebar.add(btnPengaturan);
        sidebar.add(btnLogout);

        btnData.addActionListener(e -> {
            new FormDataMinuman().setVisible(true);
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

        btnLogout.addActionListener(e -> {
            dispose();
        });

        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setBounds(220, 35, 300, 30);
        add(lblTitle);

        JLabel lblSub = new JLabel("Ringkasan inventaris minuman");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setBounds(220, 65, 300, 25);
        add(lblSub);

        JLabel lblAdmin = new JLabel("A  Admin");
        lblAdmin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAdmin.setBounds(830, 35, 120, 30);
        add(lblAdmin);

        JLabel lblWelcome = new JLabel("Selamat datang, Admin. Berikut ringkasan inventaris.");
        lblWelcome.setBounds(220, 120, 500, 25);
        add(lblWelcome);

        lblTotalBarang = createCard("Total Barang", "0", "jenis terdaftar", 220, 175, new Color(245, 240, 255));
        lblTotalStok = createCard("Total Stok", "0", "unit tersedia", 370, 175, new Color(240, 255, 247));
        lblStokMenipis = createCard("Stok Menipis", "0", "perlu restock", 520, 175, new Color(255, 249, 238));
        lblStokHabis = createCard("Stok Habis", "0", "stok = 0 unit", 670, 175, new Color(255, 240, 240));
        lblTotalKategori = createCard("Total Kategori", "0", "kategori minuman", 820, 175, new Color(240, 247, 255));

        JPanel info = new JPanel();
        info.setLayout(null);
        info.setBounds(220, 360, 720, 70);
        info.setBackground(new Color(248, 247, 255));
        info.setBorder(BorderFactory.createLineBorder(new Color(230, 225, 250)));
        add(info);

        JLabel lblInfo = new JLabel("Informasi Terbaru");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInfo.setBounds(30, 12, 250, 25);
        info.add(lblInfo);

        JLabel lblTanggal = new JLabel("Laporan terakhir diperbarui: 02 Juni 2026, 13:45 WIB");
        lblTanggal.setBounds(30, 35, 400, 25);
        info.add(lblTanggal);
    }

    private JButton menuButton(String text, int x, int y, boolean active) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 130, 35);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);

        if (active) {
            btn.setBackground(new Color(98, 55, 230));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(250, 250, 255));
            btn.setForeground(new Color(30, 30, 50));
        }

        return btn;
    }

    private JLabel createCard(String title, String value, String desc, int x, int y, Color color) {
        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBounds(x, y, 130, 130);
        card.setBackground(color);
        card.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 235)));
        add(card);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setBounds(15, 20, 110, 20);
        card.add(lblTitle);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblValue.setBounds(15, 50, 100, 35);
        card.add(lblValue);

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lblDesc.setBounds(15, 90, 110, 20);
        card.add(lblDesc);

        return lblValue;
    }

    private void loadDashboard() {
        try {
            Connection conn = Koneksi.getConnection();

            lblTotalBarang.setText(getSingleValue(conn, "SELECT COUNT(*) FROM Barang"));
            lblTotalStok.setText(getSingleValue(conn, "SELECT ISNULL(SUM(stok), 0) FROM Barang"));
            lblStokMenipis.setText(getSingleValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok <= 10 AND stok > 0"));
            lblStokHabis.setText(getSingleValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok = 0"));
            lblTotalKategori.setText(getSingleValue(conn, "SELECT COUNT(DISTINCT kategori) FROM Barang"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load dashboard: " + e.getMessage());
        }
    }

    private String getSingleValue(Connection conn, String sql) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);

        if (rs.next()) {
            return rs.getString(1);
        }

        return "0";
    }

    public static void main(String[] args) {
        new FormDashboard().setVisible(true);
    }
}
