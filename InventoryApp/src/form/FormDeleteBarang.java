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

public class FormDeleteBarang extends JFrame {

    private int idBarang;
    private FormDataMinuman parent;

    private JLabel lblId, lblKode, lblNama, lblKategori, lblStok, lblHarga, lblTanggal;

    public FormDeleteBarang(FormDataMinuman parent, int idBarang) {
        this.parent = parent;
        this.idBarang = idBarang;

        setTitle("Delete Barang");
        setSize(420, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);

        JLabel lblTitle = new JLabel("Yakin ingin menghapus data ini?");
        lblTitle.setBounds(100, 25, 250, 30);
        add(lblTitle);

        lblId = new JLabel();
        lblId.setBounds(50, 80, 300, 25);
        add(lblId);

        lblKode = new JLabel();
        lblKode.setBounds(50, 110, 300, 25);
        add(lblKode);

        lblNama = new JLabel();
        lblNama.setBounds(50, 140, 300, 25);
        add(lblNama);

        lblKategori = new JLabel();
        lblKategori.setBounds(50, 170, 300, 25);
        add(lblKategori);

        lblStok = new JLabel();
        lblStok.setBounds(50, 200, 300, 25);
        add(lblStok);

        lblHarga = new JLabel();
        lblHarga.setBounds(50, 230, 300, 25);
        add(lblHarga);

        lblTanggal = new JLabel();
        lblTanggal.setBounds(50, 260, 300, 25);
        add(lblTanggal);

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(80, 310, 100, 35);
        add(btnBatal);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(220, 310, 100, 35);
        add(btnHapus);

        btnBatal.addActionListener(e -> dispose());
        btnHapus.addActionListener(e -> hapusData());

        loadData();
    }

    private void loadData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT * FROM Barang WHERE idBarang=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                lblId.setText("ID Barang : " + rs.getInt("idBarang"));
                lblKode.setText("Kode Barang : " + rs.getString("kodeBarang"));
                lblNama.setText("Nama Barang : " + rs.getString("namaBarang"));
                lblKategori.setText("Kategori : " + rs.getString("kategori"));
                lblStok.setText("Stok : " + rs.getInt("stok"));
                lblHarga.setText("Harga : " + rs.getBigDecimal("harga"));
                lblTanggal.setText("Tanggal Masuk : " + rs.getDate("tanggalMasuk"));
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

    private void hapusData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "DELETE FROM Barang WHERE idBarang=?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);

            ps.executeUpdate();

            JOptionPane.showMessageDialog(this, "Data berhasil dihapus!");
            parent.refreshData();
            dispose();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal hapus data: " + e.getMessage());
        }
    }
}
