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

public class FormDeleteBarang extends JFrame {

    private int idBarang;
    private FormDataMinuman parent;

    private JLabel lblId, lblKode, lblNama, lblKategori, lblStok, lblHarga, lblTanggal;

    public FormDeleteBarang(FormDataMinuman parent, int idBarang) {
        this.parent = parent;
        this.idBarang = idBarang;

        setTitle("Delete Barang");
        setSize(420, 430);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(new Color(250, 250, 255));

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 420, 55);
        header.setBackground(new Color(230, 57, 70));
        add(header);

        JLabel title = new JLabel("Delete Barang");
        title.setBounds(25, 15, 200, 25);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.add(title);

        JLabel lblTitle = new JLabel("Yakin ingin menghapus data ini?");
        lblTitle.setBounds(95, 75, 250, 30);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        add(lblTitle);

        lblId = createDetailLabel(50, 120);
        lblKode = createDetailLabel(50, 150);
        lblNama = createDetailLabel(50, 180);
        lblKategori = createDetailLabel(50, 210);
        lblStok = createDetailLabel(50, 240);
        lblHarga = createDetailLabel(50, 270);
        lblTanggal = createDetailLabel(50, 300);

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(80, 350, 100, 35);
        btnBatal.setBackground(Color.WHITE);
        btnBatal.setForeground(new Color(30, 30, 50));
        btnBatal.setFocusPainted(false);
        add(btnBatal);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(220, 350, 100, 35);
        btnHapus.setBackground(new Color(230, 57, 70));
        btnHapus.setForeground(Color.WHITE);
        btnHapus.setFocusPainted(false);
        add(btnHapus);

        btnBatal.addActionListener(e -> dispose());
        btnHapus.addActionListener(e -> hapusData());

        loadData();
    }

    private JLabel createDetailLabel(int x, int y) {
        JLabel label = new JLabel();
        label.setBounds(x, y, 330, 25);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        add(label);
        return label;
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