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
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormDashboard extends JFrame {

    private JLabel lblTotalMinuman, lblTotalStok, lblStokMenipis, lblStokHabis, lblTotalKategori;

    public FormDashboard() {
        initComponents();
        loadDashboard();
    }

    private void initComponents() {
        setTitle("Dashboard Inventaris Minuman");
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

        JButton btnDashboard = menuButton("Dashboard", 30, 160, true);
        JButton btnData = menuButton("Data Minuman", 30, 215, false);
        JButton btnLaporan = menuButton("Laporan", 30, 270, false);
        JButton btnPengaturan = menuButton("Pengaturan", 30, 325, false);
        JButton btnLogout = menuButton("Logout", 30, 650, false);
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

        btnLogout.addActionListener(e -> dispose());

        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setBounds(300, 60, 350, 40);
        add(lblTitle);

        JLabel lblSub = new JLabel("Ringkasan inventaris minuman");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblSub.setBounds(300, 100, 350, 25);
        add(lblSub);

        JLabel lblWelcome = new JLabel("Selamat datang, Admin. Berikut ringkasan inventaris.");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblWelcome.setBounds(300, 160, 600, 25);
        add(lblWelcome);

        lblTotalMinuman = card("Total Minuman", "0", "jenis minuman", 300, 220, new Color(245, 240, 255), "/assets/total_minuman.png");
        lblTotalStok = card("Total Stok", "0", "unit tersedia", 500, 220, new Color(240, 255, 247), "/assets/total_stok.png");
        lblStokMenipis = card("Stok Menipis", "0", "perlu restock", 700, 220, new Color(255, 249, 238), "/assets/stok_menipis.png");
        lblStokHabis = card("Stok Habis", "0", "stok = 0 unit", 900, 220, new Color(255, 240, 240), "/assets/stok_habis.png");
        lblTotalKategori = card("Total Kategori", "0", "kategori minuman", 1100, 220, new Color(240, 247, 255), "/assets/total_kategori.png");

        JPanel info = new JPanel(null);
        info.setBounds(300, 450, 970, 85);
        info.setBackground(new Color(248, 247, 255));
        info.setBorder(BorderFactory.createLineBorder(new Color(230, 225, 250)));
        add(info);

        JLabel lblInfo = new JLabel("Informasi Terbaru");
        lblInfo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblInfo.setBounds(35, 15, 250, 25);
        info.add(lblInfo);

        String waktuSekarang = new SimpleDateFormat("dd MMMM yyyy, HH:mm 'WIB'").format(new Date());

        JLabel lblTanggal = new JLabel("Laporan terakhir diperbarui: " + waktuSekarang);
        lblTanggal.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTanggal.setBounds(35, 45, 500, 25);
        info.add(lblTanggal);
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

    private JLabel card(String title, String value, String desc, int x, int y, Color color, String iconPath) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 170, 155);
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(new Color(225, 225, 235)));
        add(panel);

        JLabel lblIcon = new JLabel(getIcon(iconPath, 42, 42));
        lblIcon.setBounds(18, 15, 42, 42);
        panel.add(lblIcon);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setBounds(18, 65, 145, 22);
        panel.add(lblTitle);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblValue.setBounds(18, 90, 120, 38);
        panel.add(lblValue);

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setBounds(18, 128, 140, 20);
        panel.add(lblDesc);

        return lblValue;
    }

    private ImageIcon getIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    private void loadDashboard() {
        try {
            Connection conn = Koneksi.getConnection();

            lblTotalMinuman.setText(getValue(conn, "SELECT COUNT(*) FROM Barang"));
            lblTotalStok.setText(getValue(conn, "SELECT ISNULL(SUM(stok), 0) FROM Barang"));
            lblStokMenipis.setText(getValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok <= 10 AND stok > 0"));
            lblStokHabis.setText(getValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok = 0"));
            lblTotalKategori.setText(getValue(conn, "SELECT COUNT(DISTINCT kategori) FROM Barang"));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load dashboard: " + e.getMessage());
        }
    }

    private String getValue(Connection conn, String sql) throws SQLException {
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