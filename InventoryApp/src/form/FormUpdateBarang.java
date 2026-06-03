/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author RAVINA
 */

import koneksi.Koneksi;
import java.awt.*;
import java.sql.*;
import javax.swing.*;

public class FormUpdateBarang extends JFrame {

    private FormDataMinuman parent;
    private int idBarang;

    private JTextField txtKode, txtNama, txtStok, txtHarga, txtTanggal;
    private JComboBox<String> cmbKategori;

    public FormUpdateBarang(FormDataMinuman parent, int idBarang) {
        this.parent = parent;
        this.idBarang = idBarang;
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Update Minuman");
        setSize(430, 590);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 430, 85);
        header.setBackground(new Color(98, 55, 230));
        add(header);

        JLabel lblTitle = new JLabel("Update Minuman");
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

        JButton btnBatal = new RoundedButton("Batal", Color.WHITE, new Color(30, 30, 50));
        btnBatal.setBounds(65, 490, 145, 48);
        add(btnBatal);

        JButton btnUpdate = new RoundedButton("Update", new Color(98, 55, 230), Color.WHITE);
        btnUpdate.setBounds(225, 490, 145, 48);
        add(btnUpdate);

        btnBatal.addActionListener(e -> dispose());
        btnUpdate.addActionListener(e -> updateData());
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

    private void loadData() {
        try {
            Connection conn = Koneksi.getConnection();
            String sql = "SELECT * FROM Barang WHERE idBarang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtKode.setText(rs.getString("kodeBarang"));
                txtNama.setText(rs.getString("namaBarang"));
                cmbKategori.setSelectedItem(rs.getString("kategori"));
                txtStok.setText(String.valueOf(rs.getInt("stok")));
                txtHarga.setText(rs.getBigDecimal("harga").toString());
                txtTanggal.setText(rs.getDate("tanggalMasuk").toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

    private void updateData() {
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

            String sql = "UPDATE Barang SET "
                    + "kodeBarang = ?, "
                    + "namaBarang = ?, "
                    + "kategori = ?, "
                    + "stok = ?, "
                    + "harga = ?, "
                    + "tanggalMasuk = ? "
                    + "WHERE idBarang = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtKode.getText().trim());
            ps.setString(2, txtNama.getText().trim());
            ps.setString(3, cmbKategori.getSelectedItem().toString());
            ps.setInt(4, Integer.parseInt(txtStok.getText().trim()));
            ps.setBigDecimal(5, new java.math.BigDecimal(txtHarga.getText().trim()));
            ps.setDate(6, java.sql.Date.valueOf(txtTanggal.getText().trim()));
            ps.setInt(7, idBarang);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            parent.refreshData();
            dispose();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Stok dan harga harus berupa angka!");
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Format tanggal harus yyyy-MM-dd!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update data: " + e.getMessage());
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