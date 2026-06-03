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

    private JTextField txtKode;
    private JTextField txtNama;
    private JComboBox<String> cmbKategori;
    private JTextField txtStok;
    private JTextField txtHarga;
    private JTextField txtTanggal;

    public FormTambahBarang(FormDataMinuman parent) {
        this.parent = parent;
        initComponents();
    }

    private void initComponents() {
        setTitle("Tambah Minuman");
        setSize(430, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 430, 70);
        header.setBackground(new Color(98, 55, 230));
        add(header);

        JLabel lblTitle = new JLabel("Tambah Minuman");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(28, 18, 250, 35);
        header.add(lblTitle);

        JLabel lblKode = label("Kode Minuman", 40, 105);
        txtKode = field(190, 100);

        JLabel lblNama = label("Nama Minuman", 40, 160);
        txtNama = field(190, 155);

        JLabel lblKategori = label("Kategori", 40, 215);
        cmbKategori = new JComboBox<>(new String[]{
            "Teh", "Jus", "Air Mineral", "Kopi", "Susu"
        });
        cmbKategori.setBounds(190, 210, 200, 35);
        add(cmbKategori);

        JLabel lblStok = label("Stok", 40, 270);
        txtStok = field(190, 265);

        JLabel lblHarga = label("Harga", 40, 325);
        txtHarga = field(190, 320);

        JLabel lblTanggal = label("Tanggal Masuk", 40, 380);
        txtTanggal = field(190, 375);
        txtTanggal.setText(LocalDate.now().toString());
        txtTanggal.setEditable(true);

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(70, 465, 130, 42);
        btnBatal.setFocusPainted(false);
        btnBatal.setBackground(Color.WHITE);
        btnBatal.setForeground(new Color(30, 30, 50));
        add(btnBatal);

        JButton btnSimpan = new JButton("Simpan");
        btnSimpan.setBounds(230, 465, 130, 42);
        btnSimpan.setFocusPainted(false);
        btnSimpan.setBackground(new Color(98, 55, 230));
        btnSimpan.setForeground(Color.WHITE);
        add(btnSimpan);

        btnBatal.addActionListener(e -> dispose());
        btnSimpan.addActionListener(e -> simpanData());
    }

    private JLabel label(String text, int x, int y) {
        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lbl.setBounds(x, y, 130, 25);
        add(lbl);
        return lbl;
    }

    private JTextField field(int x, int y) {
        JTextField txt = new JTextField();
        txt.setBounds(x, y, 200, 35);
        add(txt);
        return txt;
    }

    private void simpanData() {
        if (txtKode.getText().trim().isEmpty()
                || txtNama.getText().trim().isEmpty()
                || txtStok.getText().trim().isEmpty()
                || txtHarga.getText().trim().isEmpty()) {

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
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal tambah data: " + e.getMessage());
        }
    }
}