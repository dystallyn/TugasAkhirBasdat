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

        JLabel lblKode = new JLabel("Kode Barang");
        lblKode.setBounds(30, 30, 120, 25);
        add(lblKode);

        txtKode = new JTextField();
        txtKode.setBounds(160, 30, 180, 30);
        add(txtKode);

        JLabel lblNama = new JLabel("Nama Barang");
        lblNama.setBounds(30, 75, 120, 25);
        add(lblNama);

        txtNama = new JTextField();
        txtNama.setBounds(160, 75, 180, 30);
        add(txtNama);

        JLabel lblKategori = new JLabel("Kategori");
        lblKategori.setBounds(30, 120, 120, 25);
        add(lblKategori);

        cmbKategori = new JComboBox<>(new String[]{"Teh", "Jus", "Air Mineral", "Kopi", "Susu"});
        cmbKategori.setBounds(160, 120, 180, 30);
        add(cmbKategori);

        JLabel lblStok = new JLabel("Stok");
        lblStok.setBounds(30, 165, 120, 25);
        add(lblStok);

        txtStok = new JTextField();
        txtStok.setBounds(160, 165, 180, 30);
        add(txtStok);

        JLabel lblHarga = new JLabel("Harga");
        lblHarga.setBounds(30, 210, 120, 25);
        add(lblHarga);

        txtHarga = new JTextField();
        txtHarga.setBounds(160, 210, 180, 30);
        add(txtHarga);

        JLabel lblTanggal = new JLabel("Tanggal Masuk");
        lblTanggal.setBounds(30, 255, 120, 25);
        add(lblTanggal);

        txtTanggal = new JTextField("2026-06-02");
        txtTanggal.setBounds(160, 255, 180, 30);
        add(txtTanggal);

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(80, 320, 100, 35);
        add(btnBatal);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(210, 320, 100, 35);
        add(btnSimpan);

        btnBatal.addActionListener(e -> dispose());
        btnSimpan.addActionListener(e -> simpanData());
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
