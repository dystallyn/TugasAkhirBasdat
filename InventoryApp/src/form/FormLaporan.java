/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author RAVINA
 */

import koneksi.Koneksi;
import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormLaporan extends JFrame {
    

    private final String PATH_ICON_HEADER_LAPORAN = "/assets/laporan.png";
    private final String PATH_ICON_INFO = "";
 

    private JTable tblLaporan;
    private DefaultTableModel model;
    private JTextField txtCari;
    private JLabel lblTotalMinuman, lblStokMenipis, lblStokHabis, lblTotalKategori;
    private JLabel lblTotalData, lblWaktuUpdate;
    private JPanel panelPagination;
    
  
    private String statusFilter = "Semua";
    private int currentPage = 1;
    private final int pageSize = 5; // Menampilkan 5 baris data per halaman
    private int totalData = 0;
    
    public FormLaporan() {
        initComponents();
        loadCardData();
        loadTableData();
    }
    
    private void initComponents() {
        setTitle("Laporan Stok Barang");
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

        JButton btnDashboard = menuButton("Dashboard", 30, 160, false); // Diubah ke false karena sedang di halaman laporan
        JButton btnData = menuButton("Data Minuman", 30, 215, false);
        JButton btnLaporan = menuButton("Laporan", 30, 270, true); // Diubah ke true karena ini halaman laporan
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

        btnData.addActionListener(e -> {
            new FormDataMinuman().setVisible(true);
            dispose();
        });

        btnLogout.addActionListener(e -> dispose());
        

        JLabel lblIconHeader = new JLabel(getIcon(PATH_ICON_HEADER_LAPORAN, 40, 40));
        lblIconHeader.setBounds(300, 35, 40, 40);
        add(lblIconHeader);

        JLabel lblTitle = new JLabel("Laporan Barang");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setBounds(355, 30, 350, 30); // Disesuaikan posisi Y-nya agar seimbang
        add(lblTitle);

        JLabel lblSub = new JLabel("Lihat laporan stok barang dalam sistem");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setBounds(355, 60, 350, 20);
        add(lblSub);

  
        JPanel panelAdmin = new JPanel(null);
        panelAdmin.setBounds(1200, 35, 120, 40);
        panelAdmin.setBackground(Color.WHITE);
        add(panelAdmin);

        JLabel lblAvatar = new JLabel(getIcon(PATH_ICON_ADMIN, 32, 32));
        lblAvatar.setBounds(5, 4, 32, 32);
        panelAdmin.add(lblAvatar);

        JLabel lblAdminName = new JLabel("Admin ▾");
        lblAdminName.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblAdminName.setBounds(45, 10, 70, 20);
        panelAdmin.add(lblAdminName);

        JLabel lblWelcome = new JLabel("Selamat datang, Admin. Berikut ringkasan inventaris.");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblWelcome.setBounds(300, 120, 600, 25);
        add(lblWelcome);


        lblTotalMinuman = card("Total Minuman", "0", "jenis minuman", 300, 160, new Color(245, 240, 255), "/assets/minuman.png");
        lblStokMenipis = card("Stok Menipis", "0", "perlu restock", 540, 160, new Color(255, 249, 238), "/assets/warn.png");
        lblStokHabis = card("Stok Habis", "0", "stok = 0 unit", 780, 160, new Color(255, 240, 240), "/assets/stok.png");
        lblTotalKategori = card("Total Kategori", "0", "kategori minuman", 1020, 160, new Color(240, 247, 255), "/assets/label.png");

        JButton btnSemua = new RoundedButton("Semua Barang", new Color(98, 55, 230), Color.WHITE);
        btnSemua.setBounds(300, 315, 130, 38);
        add(btnSemua);

        JButton btnFilterMenipis = new RoundedButton("Stok Menipis", Color.WHITE, new Color(120, 120, 140));
        btnFilterMenipis.setBounds(440, 315, 120, 38);
        btnFilterMenipis.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true));
        add(btnFilterMenipis);

        JButton btnFilterHabis = new RoundedButton("Stok Habis", Color.WHITE, new Color(120, 120, 140));
        btnFilterHabis.setBounds(570, 315, 110, 38);
        btnFilterHabis.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true));
        add(btnFilterHabis);

        txtCari = new JTextField("Cari nama barang...");
        txtCari.setBounds(1080, 315, 250, 38);
        txtCari.setForeground(Color.GRAY);
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtCari.setMargin(new Insets(0, 12, 0, 0));
        add(txtCari);

        btnSemua.addActionListener(e -> {
            statusFilter = "Semua";
            resetFilterButtons(btnSemua, btnFilterMenipis, btnFilterHabis);
            currentPage = 1;
            loadTableData();
        });

        btnFilterMenipis.addActionListener(e -> {
            statusFilter = "Menipis";
            resetFilterButtons(btnFilterMenipis, btnSemua, btnFilterHabis);
            currentPage = 1;
            loadTableData();
        });

        btnFilterHabis.addActionListener(e -> {
            statusFilter = "Habis";
            resetFilterButtons(btnFilterHabis, btnSemua, btnFilterMenipis);
            currentPage = 1;
            loadTableData();
        });

        txtCari.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (txtCari.getText().equals("Cari nama barang...")) {
                    txtCari.setText("");
                    txtCari.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (txtCari.getText().trim().isEmpty()) {
                    txtCari.setText("Cari nama barang...");
                    txtCari.setForeground(Color.GRAY);
                }
            }
        });

        txtCari.addActionListener(e -> {
            currentPage = 1;
            loadTableData();
        });
        
  
        JPanel panelTableContainer = new JPanel(null);
        panelTableContainer.setBounds(300, 370, 1030, 300);
        panelTableContainer.setBackground(Color.WHITE);
        panelTableContainer.setBorder(BorderFactory.createLineBorder(new Color(240, 240, 245), 1));
        add(panelTableContainer);

        JLabel lblDaftar = new JLabel("Daftar Laporan");
        lblDaftar.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblDaftar.setBounds(20, 15, 200, 22);
        panelTableContainer.add(lblDaftar);

        JLabel lblDaftarSub = new JLabel("Ringkasan stok barang dalam sistem");
        lblDaftarSub.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDaftarSub.setForeground(Color.GRAY);
        lblDaftarSub.setBounds(20, 37, 300, 18);
        panelTableContainer.add(lblDaftarSub);

        model = new DefaultTableModel() {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        model.addColumn("ID");
        model.addColumn("Nama Barang");
        model.addColumn("Kategori");
        model.addColumn("Stok");
        model.addColumn("Harga (Rp)");
        model.addColumn("Status");

        tblLaporan = new JTable(model);
        tblLaporan.setRowHeight(38);
        tblLaporan.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblLaporan.setGridColor(new Color(245, 245, 250));
        tblLaporan.setShowGrid(true);
        tblLaporan.setIntercellSpacing(new Dimension(0, 1));
        tblLaporan.setBackground(Color.WHITE);
        tblLaporan.setSelectionBackground(new Color(245, 242, 255));
        tblLaporan.setSelectionForeground(Color.BLACK);

        JTableHeader header = tblLaporan.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));
        header.setBackground(new Color(250, 250, 255));
        header.setForeground(new Color(50, 50, 70));
        header.setPreferredSize(new Dimension(0, 35));

        setTableStyle();

        JScrollPane scroll = new JScrollPane(tblLaporan);
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(20, 70, 990, 180);
        panelTableContainer.add(scroll);

        lblTotalData = new JLabel("Total 0 data");
        lblTotalData.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTotalData.setForeground(Color.GRAY);
        lblTotalData.setBounds(20, 260, 200, 25);
        panelTableContainer.add(lblTotalData);

        panelPagination = new JPanel(null);
        panelPagination.setBounds(840, 255, 170, 35);
        panelPagination.setBackground(Color.WHITE);
        panelTableContainer.add(panelPagination);

        JPanel panelInfo = new JPanel(null);
        panelInfo.setBounds(300, 685, 1030, 55);
        panelInfo.setBackground(new Color(250, 250, 255));
        panelInfo.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 245)));
        add(panelInfo);

        JLabel lblIconInfo = new JLabel(getIcon(PATH_ICON_INFO, 24, 24));
        lblIconInfo.setBounds(20, 15, 24, 24);
        panelInfo.add(lblIconInfo);

        JLabel lblInfoTitle = new JLabel("Informasi Laporan");
        lblInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblInfoTitle.setBounds(55, 8, 200, 20);
        panelInfo.add(lblInfoTitle);

        lblWaktuUpdate = new JLabel("Laporan terakhir diperbarui: -");
        lblWaktuUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblWaktuUpdate.setForeground(Color.GRAY);
        lblWaktuUpdate.setBounds(55, 26, 400, 20);
        panelInfo.add(lblWaktuUpdate);
    }

    private void resetFilterButtons(JButton active, JButton nonActive1, JButton nonActive2) {
        active.setBackground(new Color(98, 55, 230));
        active.setForeground(Color.WHITE);
        ((RoundedButton)active).setBorder(null);

        nonActive1.setBackground(Color.WHITE);
        nonActive1.setForeground(new Color(120, 120, 140));
        nonActive1.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true));

        nonActive2.setBackground(Color.WHITE);
        nonActive2.setForeground(new Color(120, 120, 140));
        nonActive2.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true));
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

    private JLabel card(String title, String value, String desc, int x, int y, Color color, String iconPath) {
        JPanel panel = new JPanel(null);
        panel.setBounds(x, y, 220, 135);
        panel.setBackground(color);
        panel.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 240), 1, true));
        add(panel);

        JLabel lblIcon = new JLabel(getIcon(iconPath, 36, 36));
        lblIcon.setBounds(20, 20, 36, 36);
        panel.add(lblIcon);

        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setBounds(70, 16, 140, 20);
        panel.add(lblTitle);

        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setBounds(70, 38, 140, 32);
        panel.add(lblValue);

        JLabel lblDesc = new JLabel(desc);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(Color.LIGHT_GRAY);
        lblDesc.setBounds(20, 95, 180, 20);
        panel.add(lblDesc);

        return lblValue;
    }

    private void setTableStyle() {
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(JLabel.LEFT);

        tblLaporan.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        tblLaporan.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        tblLaporan.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);
        tblLaporan.getColumnModel().getColumn(3).setCellRenderer(centerRenderer);
        tblLaporan.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        tblLaporan.getColumnModel().getColumn(5).setCellRenderer(new StatusBadgeRenderer());

        tblLaporan.getColumnModel().getColumn(0).setPreferredWidth(50);
        tblLaporan.getColumnModel().getColumn(1).setPreferredWidth(250);
        tblLaporan.getColumnModel().getColumn(2).setPreferredWidth(120);
        tblLaporan.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblLaporan.getColumnModel().getColumn(4).setPreferredWidth(130);
        tblLaporan.getColumnModel().getColumn(5).setPreferredWidth(120);
    }

    private ImageIcon getIcon(String path, int width, int height) {
        if(path == null || path.isEmpty()) return null;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(img);
        } catch(Exception e) {
            return null;
        }
    }

    private void loadCardData() {
        try {
            Connection conn = Koneksi.getConnection();
            lblTotalMinuman.setText(getValue(conn, "SELECT COUNT(*) FROM Barang"));
            lblStokMenipis.setText(getValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok <= 10 AND stok > 0"));
            lblStokHabis.setText(getValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok = 0"));
            lblTotalKategori.setText(getValue(conn, "SELECT COUNT(DISTINCT kategori) FROM Barang"));
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm 'WIB'");
            lblWaktuUpdate.setText("Laporan terakhir diperbarui: " + sdf.format(new Date()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat ringkasan data: " + e.getMessage());
        }
    }

    private String getValue(Connection conn, String sql) throws SQLException {
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(sql);
        if (rs.next()) return rs.getString(1);
        return "0";
    }

    private void loadTableData() {
        model.setRowCount(0);
        String cari = txtCari.getText().trim();
        if (cari.equals("Cari nama barang...")) cari = "";

        try {
            Connection conn = Koneksi.getConnection();
            String where = " WHERE namaBarang LIKE ? ";

            if (statusFilter.equals("Menipis")) {
                where += " AND stok <= 10 AND stok > 0 ";
            } else if (statusFilter.equals("Habis")) {
                where += " AND stok = 0 ";
            }

      
            String countSql = "SELECT COUNT(*) FROM Barang " + where;
            PreparedStatement countPs = conn.prepareStatement(countSql);
            countPs.setString(1, "%" + cari + "%");
            ResultSet countRs = countPs.executeQuery();
            
            if (countRs.next()) totalData = countRs.getInt(1);

            int totalPage = (int) Math.ceil((double) totalData / pageSize);
            if (totalPage == 0) totalPage = 1;
            if (currentPage > totalPage) currentPage = totalPage;

            int offset = (currentPage - 1) * pageSize;

            String sql = "SELECT * FROM Barang " + where
                       + " ORDER BY idBarang OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + cari + "%");
            ps.setInt(2, offset);
            ps.setInt(3, pageSize);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int stok = rs.getInt("stok");
                String status = "Aman";
                if (stok == 0) status = "Habis";
                else if (stok <= 10) status = "Menipis";

                model.addRow(new Object[]{
                    String.format("%03d", rs.getInt("idBarang")),
                    rs.getString("namaBarang"),
                    rs.getString("kategori"),
                    stok,
                    formatRupiah(rs.getBigDecimal("harga")),
                    status
                });
            }

            lblTotalData.setText("Total " + totalData + " data");
            renderPagination(totalPage);

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat tabel: " + e.getMessage());
        }
    }

    private void renderPagination(int totalPage) {
        panelPagination.removeAll();
        if (totalPage <= 1) {
            panelPagination.repaint();
            panelPagination.revalidate();
            return;
        }

        int x = 0;
        JButton btnPrev = new JButton("<");
        btnPrev.setBounds(x, 0, 45, 30);
        btnPrev.setFocusPainted(false);
        btnPrev.setBackground(Color.WHITE);
        btnPrev.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235)));
        panelPagination.add(btnPrev);
        x += 50;

        btnPrev.addActionListener(e -> {
            if (currentPage > 1) { currentPage--; loadTableData(); }
        });

        for (int i = 1; i <= totalPage; i++) {
            final int page = i;
            JButton btnPage = new JButton(String.valueOf(i));
            btnPage.setBounds(x, 0, 45, 30);
            btnPage.setFocusPainted(false);
            
            if (currentPage == i) {
                btnPage.setBackground(new Color(98, 55, 230));
                btnPage.setForeground(Color.WHITE);
            } else {
                btnPage.setBackground(Color.WHITE);
                btnPage.setForeground(Color.BLACK);
            }
            btnPage.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235)));
            panelPagination.add(btnPage);
            x += 50;

            btnPage.addActionListener(e -> {
                currentPage = page;
                loadTableData();
            });
        }

        JButton btnNext = new JButton(">");
        btnNext.setBounds(x, 0, 45, 30);
        btnNext.setFocusPainted(false);
        btnNext.setBackground(Color.WHITE);
        btnNext.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235)));
        panelPagination.add(btnNext);

        btnNext.addActionListener(e -> {
            if (currentPage < totalPage) { currentPage++; loadTableData(); }
        });

        panelPagination.repaint();
        panelPagination.revalidate();
    }

    private String formatRupiah(java.math.BigDecimal harga) {
        return String.format("%,.0f", harga).replace(",", ".");
    }


    class StatusBadgeRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = new JLabel(value.toString());
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setOpaque(true);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            if (value.toString().equals("Aman")) {
                lbl.setBackground(new Color(235, 247, 238));
                lbl.setForeground(new Color(46, 125, 50));
            } else if (value.toString().equals("Menipis")) {
                lbl.setBackground(new Color(255, 244, 229));
                lbl.setForeground(new Color(230, 120, 0));
            } else if (value.toString().equals("Habis")) {
                lbl.setBackground(new Color(253, 237, 237));
                lbl.setForeground(new Color(211, 47, 47));
            }

            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);
            if (isSelected) panel.setBackground(new Color(245, 242, 255));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.ipadx = 20; gbc.ipady = 6;
            panel.add(lbl, gbc);
            
            return panel;
        }
    }

    class RoundedButton extends JButton {
        private final Color bgColor;
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
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(bgColor);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            super.paintComponent(g);
            g2.dispose();
        }
    }

    public static void main(String[] args) {
        new FormLaporan().setVisible(true);
    }
}