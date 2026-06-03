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
import javax.swing.table.*;

public class FormDataMinuman extends JFrame {

    private JTable tblMinuman;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JComboBox<String> cmbKategori;
    private JLabel lblTotalData;
    private JPanel panelPagination;

    private int currentPage = 1;
    private int pageSize = 6;
    private int totalData = 0;
    private boolean filterStokMenipis = false;

    public FormDataMinuman() {
        initComponents();
        loadData();
    }

    private void initComponents() {
        setTitle("Data Minuman");
        setSize(1366, 768);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        getContentPane().setBackground(Color.WHITE);

        JPanel sidebar = new JPanel(null);
        sidebar.setBounds(0, 0, 230, 900);
        sidebar.setBackground(new Color(250, 250, 255));
        add(sidebar);

        JLabel lblLogo = new JLabel(getIcon("/assets/logo_minuman.png", 48, 48));
        lblLogo.setBounds(30, 35, 48, 48);
        sidebar.add(lblLogo);

        JLabel lblNama = new JLabel("<html><b>Inventaris</b><br>Minuman</html>");
        lblNama.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNama.setBounds(90, 32, 120, 55);
        sidebar.add(lblNama);

        JButton btnDashboard = menuButton("Dashboard", 30, 160, false);
        JButton btnData = menuButton("Data Minuman", 30, 215, true);
        JButton btnLaporan = menuButton("Laporan", 30, 270, false);
        JButton btnLogout = menuButton("Logout", 30, 650, false);
        btnLogout.setForeground(Color.RED);

        sidebar.add(btnDashboard);
        sidebar.add(btnData);
        sidebar.add(btnLaporan);
        sidebar.add(btnLogout);

        btnDashboard.addActionListener(e -> {
            new FormDashboard().setVisible(true);
            dispose();
        });

        btnLaporan.addActionListener(e -> {
            new FormLaporan().setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> dispose());

        JLabel lblTitle = new JLabel("Data Minuman");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 30));
        lblTitle.setBounds(300, 55, 350, 40);
        add(lblTitle);

        JLabel lblSub = new JLabel("Kelola data inventaris minuman");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        lblSub.setBounds(300, 95, 350, 25);
        add(lblSub);

        txtCari = new JTextField("Cari kode / nama minuman...");
        txtCari.setBounds(300, 150, 430, 40);
        txtCari.setForeground(Color.GRAY);
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        txtCari.setMargin(new Insets(0, 12, 0, 0));
        add(txtCari);

        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtCari.getText().equals("Cari kode / nama minuman...")) {
                    txtCari.setText("");
                    txtCari.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtCari.getText().trim().isEmpty()) {
                    txtCari.setText("Cari kode / nama minuman...");
                    txtCari.setForeground(Color.GRAY);
                }
            }
        });

        cmbKategori = new JComboBox<>(new String[]{
            "Semua Kategori", "Teh", "Jus", "Air Mineral", "Kopi", "Susu"
        });
        cmbKategori.setBounds(760, 150, 200, 40);
        cmbKategori.setFont(new Font("Segoe UI", Font.BOLD, 13));
        cmbKategori.setBackground(Color.WHITE);
        cmbKategori.setForeground(new Color(30, 30, 50));
        cmbKategori.setBorder(BorderFactory.createLineBorder(new Color(220, 225, 235)));
        add(cmbKategori);

        JButton btnCari = new RoundedButton("Cari", new Color(98, 55, 230), Color.WHITE);
        btnCari.setBounds(985, 150, 105, 40);
        add(btnCari);

        JButton btnStokMenipis = new RoundedButton("! Stok Menipis", new Color(245, 130, 32), Color.WHITE);
        btnStokMenipis.setBounds(1115, 150, 150, 40);
        add(btnStokMenipis);

        JButton btnTambah = new RoundedButton("+ Tambah", new Color(98, 55, 230), Color.WHITE);
        btnTambah.setBounds(1290, 150, 140, 40);
        add(btnTambah);

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("ID Minuman");
        model.addColumn("Kode Minuman");
        model.addColumn("Nama Minuman");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Harga");
        model.addColumn("Tanggal Masuk");
        model.addColumn("Edit");
        model.addColumn("Hapus");

        tblMinuman = new JTable(model);
        tblMinuman.setRowHeight(55);
        tblMinuman.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblMinuman.setGridColor(new Color(235, 238, 245));
        tblMinuman.setShowGrid(true);
        tblMinuman.setIntercellSpacing(new Dimension(0, 1));
        tblMinuman.setBackground(Color.WHITE);
        tblMinuman.setSelectionBackground(new Color(245, 242, 255));
        tblMinuman.setSelectionForeground(Color.BLACK);

        JTableHeader header = tblMinuman.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(248, 247, 255));
        header.setForeground(new Color(30, 30, 50));
        header.setPreferredSize(new Dimension(0, 42));

        setTableStyle();

        tblMinuman.getColumnModel().getColumn(7).setCellRenderer(new IconRenderer("/assets/edit.png", 28, 28));
        tblMinuman.getColumnModel().getColumn(8).setCellRenderer(new IconRenderer("/assets/delete.png", 28, 28));

        tblMinuman.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = tblMinuman.rowAtPoint(e.getPoint());
                int col = tblMinuman.columnAtPoint(e.getPoint());

                if (row == -1) {
                    return;
                }

                int idBarang = Integer.parseInt(model.getValueAt(row, 0).toString());

                if (col == 7) {
                    new FormUpdateBarang(FormDataMinuman.this, idBarang).setVisible(true);
                } else if (col == 8) {
                    new FormDeleteBarang(FormDataMinuman.this, idBarang).setVisible(true);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tblMinuman);

        scroll.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 240)));
        scroll.getViewport().setBackground(new Color(245, 245, 245));

        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_NEVER);

        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        scroll.setBounds(300, 220, 1130, 373);
        add(scroll);

        lblTotalData = new JLabel("Menampilkan 0 data");
        lblTotalData.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblTotalData.setBounds(300, 600, 400, 30);
        add(lblTotalData);

        panelPagination = new JPanel(null);
        panelPagination.setBounds(1110, 600, 320, 45);
        panelPagination.setBackground(Color.WHITE);
        add(panelPagination);

        btnCari.addActionListener(e -> {
            currentPage = 1;
            filterStokMenipis = false;
            loadData();
        });

        cmbKategori.addActionListener(e -> {
            currentPage = 1;
            filterStokMenipis = false;
            loadData();
        });

        btnStokMenipis.addActionListener(e -> {
            currentPage = 1;
            filterStokMenipis = !filterStokMenipis;
            loadData();
        });

        btnTambah.addActionListener(e -> {
            new FormTambahBarang(this).setVisible(true);
        });
    }

    private JButton menuButton(String text, int x, int y, boolean active) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 170, 42);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));

        if (active) {
            btn.setBackground(new Color(98, 55, 230));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(250, 250, 255));
            btn.setForeground(new Color(30, 30, 50));
        }

        return btn;
    }

    private JButton pageButton(String text, int x, int y, boolean active) {
        JButton btn = new JButton(text);
        btn.setBounds(x, y, 50, 40);
        btn.setFocusPainted(false);
        btn.setOpaque(true);
        btn.setContentAreaFilled(true);
        btn.setBorderPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setMargin(new Insets(0, 0, 0, 0));

        if (active) {
            btn.setBackground(new Color(98, 55, 230));
            btn.setForeground(Color.WHITE);
        } else {
            btn.setBackground(new Color(245, 242, 255));
            btn.setForeground(new Color(98, 55, 230));
        }

        return btn;
    }

    private void renderPagination(int totalPage) {
        panelPagination.removeAll();

        if (totalPage <= 1) {
            panelPagination.repaint();
            panelPagination.revalidate();
            return;
        }

        int x = 0;

        JButton btnPrev = pageButton("<", x, 0, false);
        panelPagination.add(btnPrev);
        x += 62;

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) {
                currentPage--;
                loadData();
            }
        });

        for (int i = 1; i <= totalPage; i++) {
            final int page = i;

            JButton btnPage = pageButton(String.valueOf(i), x, 0, currentPage == i);
            panelPagination.add(btnPage);

            btnPage.addActionListener(e -> {
                currentPage = page;
                loadData();
            });

            x += 62;
        }

        JButton btnNext = pageButton(">", x, 0, false);
        panelPagination.add(btnNext);

        btnNext.addActionListener(e -> {
            if (currentPage < totalPage) {
                currentPage++;
                loadData();
            }
        });

        panelPagination.repaint();
        panelPagination.revalidate();
    }

    private void setTableStyle() {
        ZebraRenderer centerZebra = new ZebraRenderer(JLabel.CENTER, 0);
        ZebraRenderer leftZebra = new ZebraRenderer(JLabel.LEFT, 14);

        tblMinuman.getColumnModel().getColumn(0).setCellRenderer(centerZebra);
        tblMinuman.getColumnModel().getColumn(1).setCellRenderer(centerZebra);
        tblMinuman.getColumnModel().getColumn(2).setCellRenderer(leftZebra);
        tblMinuman.getColumnModel().getColumn(3).setCellRenderer(centerZebra);
        tblMinuman.getColumnModel().getColumn(4).setCellRenderer(centerZebra);
        tblMinuman.getColumnModel().getColumn(5).setCellRenderer(centerZebra);
        tblMinuman.getColumnModel().getColumn(6).setCellRenderer(centerZebra);

        tblMinuman.getColumnModel().getColumn(0).setPreferredWidth(85);
        tblMinuman.getColumnModel().getColumn(1).setPreferredWidth(120);
        tblMinuman.getColumnModel().getColumn(2).setPreferredWidth(230);
        tblMinuman.getColumnModel().getColumn(3).setPreferredWidth(130);
        tblMinuman.getColumnModel().getColumn(4).setPreferredWidth(80);
        tblMinuman.getColumnModel().getColumn(5).setPreferredWidth(120);
        tblMinuman.getColumnModel().getColumn(6).setPreferredWidth(160);
        tblMinuman.getColumnModel().getColumn(7).setPreferredWidth(70);
        tblMinuman.getColumnModel().getColumn(8).setPreferredWidth(70);
    }

    public void refreshData() {
        loadData();
    }

    private void loadData() {
        model.setRowCount(0);

        String cari = txtCari.getText().trim();

        if (cari.equals("Cari kode / nama minuman...")) {
            cari = "";
        }

        String kategori = cmbKategori.getSelectedItem().toString();

        try {
            Connection conn = Koneksi.getConnection();

            String where = " WHERE (kodeBarang LIKE ? OR namaBarang LIKE ?) ";

            if (!kategori.equals("Semua Kategori")) {
                where += " AND kategori = ? ";
            }

            if (filterStokMenipis) {
                where += " AND stok <= 10 AND stok > 0 ";
            }

            String countSql = "SELECT COUNT(*) FROM Barang " + where;
            PreparedStatement countPs = conn.prepareStatement(countSql);

            int countIndex = 1;
            countPs.setString(countIndex++, "%" + cari + "%");
            countPs.setString(countIndex++, "%" + cari + "%");

            if (!kategori.equals("Semua Kategori")) {
                countPs.setString(countIndex++, kategori);
            }

            ResultSet countRs = countPs.executeQuery();

            if (countRs.next()) {
                totalData = countRs.getInt(1);
            }

            int totalPage = (int) Math.ceil((double) totalData / pageSize);

            if (totalPage == 0) {
                totalPage = 1;
            }

            if (currentPage > totalPage) {
                currentPage = totalPage;
            }

            int offset = (currentPage - 1) * pageSize;

            String sql = "SELECT * FROM Barang "
                    + where
                    + " ORDER BY idBarang "
                    + " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement ps = conn.prepareStatement(sql);

            int paramIndex = 1;
            ps.setString(paramIndex++, "%" + cari + "%");
            ps.setString(paramIndex++, "%" + cari + "%");

            if (!kategori.equals("Semua Kategori")) {
                ps.setString(paramIndex++, kategori);
            }

            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);

            ResultSet rs = ps.executeQuery();

            int tampil = 0;

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("idBarang"),
                    rs.getString("kodeBarang"),
                    rs.getString("namaBarang"),
                    rs.getString("kategori"),
                    rs.getInt("stok"),
                    formatRupiah(rs.getBigDecimal("harga")),
                    rs.getDate("tanggalMasuk"),
                    "",
                    ""
                });
                tampil++;
            }

            int awal = totalData == 0 ? 0 : offset + 1;
            int akhir = offset + tampil;

            lblTotalData.setText("Menampilkan " + awal + " - " + akhir + " dari " + totalData + " data");

            renderPagination(totalPage);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal load data: " + e.getMessage());
        }
    }

    private String formatRupiah(java.math.BigDecimal harga) {
        return "Rp " + String.format("%,.0f", harga).replace(",", ".");
    }

    private ImageIcon getIcon(String path, int width, int height) {
        ImageIcon icon = new ImageIcon(getClass().getResource(path));
        Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }

    class ZebraRenderer extends DefaultTableCellRenderer {

        private int align;
        private int leftPadding;

        public ZebraRenderer(int align, int leftPadding) {
            this.align = align;
            this.leftPadding = leftPadding;
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            JLabel label = (JLabel) super.getTableCellRendererComponent(
                    table, value, isSelected, hasFocus, row, column
            );

            label.setHorizontalAlignment(align);
            label.setBorder(BorderFactory.createEmptyBorder(0, leftPadding, 0, 10));

            if (isSelected) {
                label.setBackground(new Color(245, 242, 255));
                label.setForeground(Color.BLACK);
            } else {
                if (row % 2 == 0) {
                    label.setBackground(Color.WHITE);
                } else {
                    label.setBackground(new Color(250, 250, 255));
                }
                label.setForeground(new Color(20, 20, 35));
            }

            return label;
        }
    }

    class IconRenderer extends DefaultTableCellRenderer {

        private final String iconPath;
        private final int width;
        private final int height;

        public IconRenderer(String iconPath, int width, int height) {
            this.iconPath = iconPath;
            this.width = width;
            this.height = height;
        }

        private ImageIcon resizeIcon(String path, int width, int height) {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        }

        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected,
                boolean hasFocus, int row, int column) {

            JLabel label = new JLabel();
            label.setHorizontalAlignment(JLabel.CENTER);
            label.setIcon(resizeIcon(iconPath, width, height));
            label.setOpaque(true);

            if (isSelected) {
                label.setBackground(new Color(245, 242, 255));
            } else {
                if (row % 2 == 0) {
                    label.setBackground(Color.WHITE);
                } else {
                    label.setBackground(new Color(250, 250, 255));
                }
            }

            return label;
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

            g2.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);

            super.paintComponent(g);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        new FormDataMinuman().setVisible(true);
    }
}