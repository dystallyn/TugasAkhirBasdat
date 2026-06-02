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
import java.sql.*;
import javax.swing.*;

public class FormTambahBarang extends JFrame {

    private JTextField txtKode, txtNama, txtStok, txtHarga, txtTanggal;
    private JComboBox<String> cmbKategori;
    private FormDataMinuman parent;

    public FormTambahBarang(FormDataMinuman parent) {
        this.parent = parent;

        setTitle("Tambah Barang");
        setSize(400, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        addLabel("Kode Barang", 30);
        txtKode = addText(30);

        addLabel("Nama Barang", 75);
        txtNama = addText(75);

        addLabel("Kategori", 120);
        cmbKategori = new JComboBox<>(new String[]{"Teh", "Jus", "Air Mineral", "Kopi", "Susu"});
        cmbKategori.setBounds(160, 120, 180, 30);
        add(cmbKategori);

        addLabel("Stok", 165);
        txtStok = addText(165);

        addLabel("Harga", 210);
        txtHarga = addText(210);

        addLabel("Tanggal Masuk", 255);
        txtTanggal = addText(255);
        txtTanggal.setText("2026-06-02");

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(80, 320, 100, 35);
        add(btnBatal);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(210, 320, 100, 35);
        add(btnSimpan);

        btnBatal.addActionListener(e -> dispose());
        btnSimpan.addActionListener(e -> simpanData());
    }

    private void addLabel(String text, int y) {
        JLabel label = new JLabel(text);
        label.setBounds(30, y, 120, 25);
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