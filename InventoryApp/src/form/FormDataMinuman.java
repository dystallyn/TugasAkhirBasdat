/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author KINDLY
 */
import koneksi.Koneksi;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class FormDataMinuman extends JFrame {

    private JTable tblMinuman;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JComboBox<String> cmbKategori;
    private JLabel lblTotalData;

    public FormDataMinuman() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Data Minuman");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Data Barang");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setBounds(30, 25, 250, 30);
        add(lblTitle);

        txtCari = new JTextField();
        txtCari.setBounds(30, 80, 300, 35);
        add(txtCari);

        cmbKategori = new JComboBox<>(new String[]{
            "Semua Kategori", "Teh", "Jus", "Air Mineral", "Kopi", "Susu"
        });
        cmbKategori.setBounds(350, 80, 170, 35);
        add(cmbKategori);

        JButton btnCari = new JButton("Cari");
        btnCari.setBounds(540, 80, 90, 35);
        add(btnCari);

        JButton btnTambah = new JButton("Tambah");
        btnTambah.setBounds(650, 80, 100, 35);
        add(btnTambah);

        JButton btnUpdate = new JButton("Update");
        btnUpdate.setBounds(760, 80, 100, 35);
        add(btnUpdate);

        JButton btnDelete = new JButton("Delete");
        btnDelete.setBounds(870, 80, 100, 35);
        add(btnDelete);

        model = new DefaultTableModel();
        model.addColumn("ID Barang");
        model.addColumn("Kode Barang");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Harga");
        model.addColumn("Tanggal Masuk");

        tblMinuman = new JTable(model);
        tblMinuman.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(tblMinuman);
        scroll.setBounds(30, 140, 940, 350);
        add(scroll);

        lblTotalData = new JLabel("Menampilkan 0 data");
        lblTotalData.setBounds(30, 505, 300, 25);
        add(lblTotalData);

        btnCari.addActionListener(e -> loadData());
        cmbKategori.addActionListener(e -> loadData());

        btnTambah.addActionListener(e -> {
            new FormTambahBarang(this).setVisible(true);
        });

        btnUpdate.addActionListener(e -> updateData());

        btnDelete.addActionListener(e -> deleteData());
    }

    public void refreshData() {
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);

        String cari = txtCari.getText().trim();
        String kategori = cmbKategori.getSelectedItem().toString();

        try {
            Connection conn = Koneksi.getConnection();

            String sql = "SELECT * FROM Barang WHERE "
                    + "(kodeBarang LIKE ? OR namaBarang LIKE ?)";

            if (!kategori.equals("Semua Kategori")) {
                sql += " AND kategori = ?";
            }

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + cari + "%");
            ps.setString(2, "%" + cari + "%");

            if (!kategori.equals("Semua Kategori")) {
                ps.setString(3, kategori);
            }

            ResultSet rs = ps.executeQuery();

            int total = 0;

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idBarang"),
                    rs.getString("kodeBarang"),
                    rs.getString("namaBarang"),
                    rs.getString("kategori"),
                    rs.getInt("stok"),
                    rs.getBigDecimal("harga"),
                    rs.getDate("tanggalMasuk")
                });
                total++;
            }

            lblTotalData.setText("Menampilkan " + total + " data");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

    private void updateData() {
        int row = tblMinuman.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin diupdate!");
            return;
        }

        int idBarang = Integer.parseInt(model.getValueAt(row, 0).toString());

        new FormUpdateBarang(this, idBarang).setVisible(true);
    }

    private void deleteData() {
        int row = tblMinuman.getSelectedRow();

        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih data yang ingin dihapus!");
            return;
        }

        int idBarang = Integer.parseInt(model.getValueAt(row, 0).toString());

        new FormDeleteBarang(this, idBarang).setVisible(true);
    }

    public static void main(String[] args) {
        new FormDataMinuman().setVisible(true);
    }
}
