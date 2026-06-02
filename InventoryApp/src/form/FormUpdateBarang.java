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

public class FormUpdateBarang extends JFrame {

    private JTextField txtKode, txtNama, txtStok, txtHarga, txtTanggal;
    private JComboBox<String> cmbKategori;
    private FormDataMinuman parent;
    private int idBarang;

    public FormUpdateBarang(FormDataMinuman parent, int idBarang) {
        this.parent = parent;
        this.idBarang = idBarang;

        setTitle("Update Barang");
        setSize(400, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(250, 250, 255));

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 400, 55);
        header.setBackground(new Color(98, 55, 230));
        add(header);

        JLabel title = new JLabel("Update Barang");
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

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(80, 370, 100, 35);
        btnBatal.setBackground(Color.WHITE);
        btnBatal.setForeground(new Color(30, 30, 50));
        btnBatal.setFocusPainted(false);
        add(btnBatal);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(210, 370, 100, 35);
        btnUpdate.setBackground(new Color(98, 55, 230));
        btnUpdate.setForeground(Color.WHITE);
        btnUpdate.setFocusPainted(false);
        add(btnUpdate);

        btnBatal.addActionListener(e -> dispose());
        btnUpdate.addActionListener(e -> updateData());

        loadDataLama();
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

    private void loadDataLama() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT * FROM Barang WHERE idBarang=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                txtKode.setText(rs.getString("kodeBarang"));
                txtNama.setText(rs.getString("namaBarang"));
                cmbKategori.setSelectedItem(rs.getString("kategori"));
                txtStok.setText(rs.getString("stok"));
                txtHarga.setText(rs.getString("harga"));
                txtTanggal.setText(rs.getString("tanggalMasuk"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data lama: " + e.getMessage());
        }
    }

    private void updateData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "UPDATE Barang SET "
                    + "kodeBarang=?, namaBarang=?, kategori=?, stok=?, harga=?, tanggalMasuk=? "
                    + "WHERE idBarang=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, txtKode.getText());
            ps.setString(2, txtNama.getText());
            ps.setString(3, cmbKategori.getSelectedItem().toString());
            ps.setInt(4, Integer.parseInt(txtStok.getText()));
            ps.setBigDecimal(5, new java.math.BigDecimal(txtHarga.getText()));
            ps.setString(6, txtTanggal.getText());
            ps.setInt(7, idBarang);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil diupdate!");
            parent.refreshData();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal update data: " + e.getMessage());
        }
    }
}