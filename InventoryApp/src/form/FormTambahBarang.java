/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author alhud
 */

import koneksi.Koneksi;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.*;

public class FormTambahBarang extends JFrame {

    private FormDataMinuman parent;

    private JTextField txtKode, txtNama, txtStok, txtHarga, txtTanggal;
    private JComboBox<String> cmbKategori;

    public FormTambahBarang(FormDataMinuman parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setTitle("Tambah Minuman");
        setSize(430, 590);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 430, 85);
        header.setBackground(new Color(98, 55, 230));
        add(header);

        JLabel lblTitle = new JLabel("Tambah Minuman");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(30, 25, 260, 35);
        header.add(lblTitle);

        label("Kode Minuman", 45, 120);
        txtKode = field(190, 115);

        label("Nama Minuman", 45, 175);
        txtNama = field(190, 170);

        label("Kategori", 45, 230);
        cmbKategori = new JComboBox<>(new String[]{"Teh", "Jus", "Air Mineral", "Kopi", "Susu"});
        cmbKategori.setBounds(190, 225, 220, 40);
        cmbKategori.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbKategori.setBackground(Color.WHITE);
        add(cmbKategori);

        label("Stok", 45, 285);
        txtStok = field(190, 280);

        label("Harga", 45, 340);
        txtHarga = field(190, 335);

        label("Tanggal Masuk", 45, 395);
        txtTanggal = field(190, 390);
        txtTanggal.setText(LocalDate.now().toString());
        txtTanggal.setEditable(true);

        JButton btnBatal = new RoundedButton("Batal", Color.WHITE, new Color(30, 30, 50));
        btnBatal.setBounds(65, 490, 145, 48);
        add(btnBatal);

        JButton btnSimpan = new RoundedButton("Simpan", new Color(98, 55, 230), Color.WHITE);
        btnSimpan.setBounds(225, 490, 145, 48);
        add(btnSimpan);

        btnBatal.addActionListener(e -> dispose());
        btnSimpan.addActionListener(e -> simpanData());
    }

    private void label(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setForeground(new Color(30, 30, 50));
        lbl.setBounds(x, y, 130, 25);
        add(lbl);
    }

    private JTextField field(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 220, 40);
        txt.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txt.setMargin(new Insets(0, 10, 0, 10));
        txt.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 235)));
        add(txt);
        return txt;
    }

    private void simpanData() {
        if (txtKode.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtStok.getText().trim().isEmpty()
                || txtHarga.getText().trim().isEmpty()
                || txtTanggal.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(this, "Semua data wajib diisi!");
            return;
        }

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "INSERT INTO Barang "
                    + "(kodeBarang, namaBarang, kategori, stok, harga, tanggalMasuk) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtKode.getText().trim());
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, cmbKategori.getSelectedItem().toString());
            ps.setInt(4, Integer.parseInt(txtStok.getText().trim()));
            ps.setBigDecimal(5, new java.math.BigDecimal(txtHarga.getText().trim()));
            ps.setDate(6, java.sql.Date.valueOf(txtTanggal.getText().trim()));

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            parent.refreshData();
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok dan harga harus berupa angka!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus yyyy-MM-dd!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal tambah data: " + e.getMessage());
        }
    }

    class RoundedButton extends JButton {

        private Color bgColor;

        public RoundedButton(String text, Color bgColor, Color fgColor) {
            super(text);
            this.bgColor = bgColor;
            setForeground(fgColor);
            setFocusPainted(false);
            setBorderPainted(false);
            setContentAreaFilled(false);
            setOpaque(false);
            setFont(new Font("Segoe UI", Font.BOLD, 13));
        }

        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

            if (bgColor.equals(Color.WHITE)) {
                g2.setColor(new Color(220, 225, 235));
                g2.setStroke(new BasicStroke(1.5f));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 18, 18);
            }

            super.paintComponent(g);
            g2.dispose();
        }
    }
}