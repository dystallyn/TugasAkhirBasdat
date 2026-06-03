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

    private FormDataMinuman parent;
    private int idBarang;

    private JLabel valueKode;
    private JLabel valueNama;
    private JLabel valueKategori;
    private JLabel valueStok;
    private JLabel valueHarga;
    private JLabel valueTanggal;

    public FormDeleteBarang(FormDataMinuman parent, int idBarang) {
        this.parent = parent;
        this.idBarang = idBarang;

        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Delete Barang");
        setSize(430, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel header = new JPanel(null);
        header.setBounds(0, 0, 430, 70);
        header.setBackground(new Color(239, 51, 64));
        add(header);

        JLabel lblTitle = new JLabel("Delete Barang");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        lblTitle.setBounds(28, 18, 250, 35);
        header.add(lblTitle);

        JLabel lblIcon = new JLabel(getIcon("/assets/delete_big.png", 78, 78));
        lblIcon.setBounds(176, 95, 78, 78);
        add(lblIcon);

        JLabel lblConfirm = new JLabel(
                "<html><div style='text-align:center;'>Yakin ingin menghapus<br>data ini?</div></html>",
                SwingConstants.CENTER
        );
        lblConfirm.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblConfirm.setBounds(90, 180, 250, 55);
        add(lblConfirm);

        JPanel detailPanel = new JPanel(null);
        detailPanel.setBounds(55, 250, 320, 155);
        detailPanel.setBackground(new Color(248, 248, 255));
        detailPanel.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 245)));
        add(detailPanel);

        valueKode = new JLabel();
        valueNama = new JLabel();
        valueKategori = new JLabel();
        valueStok = new JLabel();
        valueHarga = new JLabel();
        valueTanggal = new JLabel();

        addDetailRow(
                detailPanel,
                "Kode Barang",
                valueKode,
                15,
                5
        );

        addDetailRow(
                detailPanel,
                "Nama Barang",
                valueNama,
                15,
                30
        );

        addDetailRow(
                detailPanel,
                "Kategori",
                valueKategori,
                15,
                55
        );

        addDetailRow(
                detailPanel,
                "Stok",
                valueStok,
                15,
                80
        );

        addDetailRow(
                detailPanel,
                "Harga",
                valueHarga,
                15,
                105
        );

        addDetailRow(
                detailPanel,
                "Tanggal Masuk",
                valueTanggal,
                15,
                130
        );

        JButton btnBatal = new JButton("Batal");
        btnBatal.setBounds(55, 455, 130, 42);
        btnBatal.setFocusPainted(false);
        btnBatal.setBackground(Color.WHITE);
        btnBatal.setForeground(new Color(30, 30, 50));
        add(btnBatal);

        JButton btnHapus = new JButton("Hapus");
        btnHapus.setBounds(245, 455, 130, 42);
        btnHapus.setFocusPainted(false);
        btnHapus.setBackground(new Color(239, 51, 64));
        btnHapus.setForeground(Color.WHITE);
        add(btnHapus);

        btnBatal.addActionListener(e -> dispose());
        btnHapus.addActionListener(e -> hapusData());
    }

    private void addDetailRow(
            JPanel panel,
            String labelText,
            JLabel valueLabel,
            int x,
            int y) {

        JLabel label =
                new JLabel(labelText);

        label.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        label.setBounds(
                x,
                y,
                105,
                22
        );

        panel.add(label);

        JLabel colon =
                new JLabel(":");

        colon.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        colon.setBounds(
                x + 115,
                y,
                10,
                22
        );

        panel.add(colon);

        valueLabel.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        valueLabel.setBounds(
                x + 130,
                y,
                170,
                22
        );

        panel.add(valueLabel);
    }

    private void loadData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT * FROM Barang WHERE idBarang = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idBarang);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                valueKode.setText(
                    rs.getString("kodeBarang"));

                valueNama.setText(
                        rs.getString("namaBarang"));

                valueKategori.setText(
                        rs.getString("kategori"));

                valueStok.setText(
                        String.valueOf(
                                rs.getInt("stok")));

                valueHarga.setText(
                        rs.getBigDecimal("harga")
                                .toString());

                valueTanggal.setText(
                        rs.getDate("tanggalMasuk")
                                .toString());
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

    private void hapusData() {
        try {
            Connection conn = Koneksi.getConnection();

            String sql = "DELETE FROM Barang WHERE idBarang = ?";
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

    private ImageIcon getIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
}