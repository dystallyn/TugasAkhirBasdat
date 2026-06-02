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
import javax.swing.*;

public class FormTambahBarang extends JFrame {

    private JTextField txtKode, txtNama, txtStok, txtHarga, txtTanggal;
    private JComboBox<String> cmbKategori;
    private FormDataMinuman parent;

    public FormTambahBarang(FormDataMinuman parent) {
        this.parent = parent;

        setTitle("Tambah Barang");
        setSize(400, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(250, 250, 255));

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 400, 55);
        header.setBackground(new Color(98, 55, 230));
        add(header);

        JLabel title = new JLabel("Tambah Barang");
        title.setBounds(25, 15, 200, 25);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(title);

        addLabel("Kode Barang", 80);
        txtKode = addText(80);

        addLabel("Nama Barang", 125);
        txtNama = addText(125);

        addLabel("Kategori", 170);
        cmbKategori = new JComboBox<>(new String[]{"Teh", "Jus", "Air Mineral", "Kopi", "Susu"});
        cmbKategori.setBounds(160, 170, 180, 30);
        add(cmbKategori);

        addLabel("Stok", 215);
        txtStok = addText(215);

        addLabel("Harga", 260);
        txtHarga = addText(260);

        addLabel("Tanggal Masuk", 305);
        txtTanggal = addText(305);
        txtTanggal.setText("2026-06-02");

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(80, 370, 100, 35);
        btnBatal.setBackground(Color.WHITE);
        btnBatal.setForeground(new Color(30, 30, 50));
        btnBatal.setFocusPainted(false);
        add(btnBatal);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(210, 370, 100, 35);
        btnSimpan.setBackground(new Color(98, 55, 230));
        btnSimpan.setForeground(Color.WHITE);
        btnSimpan.setFocusPainted(false);
        add(btnSimpan);

        btnBatal.addActionListener(e -> dispose());
        btnSimpan.addActionListener(e -> simpanData());
    }

    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(30, y, 120, 25);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        add(label);
    }

    private JTextField addText(int y) {
        JTextField text = new JTextField();
        text.setBounds(160, y, 180, 30);
        add(text);
        return text;
    }

    private void simpanData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "INSERT INTO Barang "
                    + "(kodeBarang, namaBarang, kategori, stok, harga, tanggalMasuk) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtKode.getText());
            ps.setString(2, txtNama.getText());
            ps.setString(3, cmbKategori.getSelectedItem().toString());
            ps.setInt(4, Integer.parseInt(txtStok.getText()));
            ps.setBigDecimal(5, new java.math.BigDecimal(txtHarga.getText()));
            ps.setString(6, txtTanggal.getText());

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil ditambahkan!");
            parent.refreshData();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal tambah data: " + e.getMessage());
        }
    }
}