/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package form;

/**
 *
 * @author KINDLY
 */
import javax.swing.JFrame;
import javax.swing.JLabel;

public class FormLaporan extends JFrame {
    
    private JLabel lblTotalMinuman, lblTotalStok, lblStokMenipis, lblStokHabis, lblTotalKategori;
    
    public FormLaporan() {
        initComponents();
        loadDashboard();
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

        JButton btnDashboard = menuButton("Dashboard", 30, 160, true);
        JButton btnData = menuButton("Data Minuman", 30, 215, false);
        JButton btnLaporan = menuButton("Laporan", 30, 270, false);
        JButton btnLogout = menuButton("Logout", 30, 650, false);
        btnLogout.setForeground(Color.RED);

        sidebar.add(btnDashboard);
        sidebar.add(btnData);
        sidebar.add(btnLaporan);
        sidebar.add(btnLogout);

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
        lblTitle.setBounds(355, 60, 350, 20);
        add(lblTitle);

        Label lblSub = new JLabel("Lihat laporan stok barang dalam sistem");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setBounds(355, 60, 350, 20);
        add(lblSub);

        JLabel lblWelcome = new JLabel("Selamat datang, Admin. Berikut ringkasan inventaris.");
        lblWelcome.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblWelcome.setBounds(300, 160, 600, 25);
        add(lblWelcome);

        lblTotalMinuman = card("Total Minuman", "0", "jenis minuman", 300, 220, new Color(245, 240, 255), "/assets/total_minuman.png");
        lblStokMenipis = card("Stok Menipis", "0", "perlu restock", 700, 220, new Color(255, 249, 238), "/assets/stok_menipis.png");
        lblStokHabis = card("Stok Habis", "0", "stok = 0 unit", 900, 220, new Color(255, 240, 240), "/assets/stok_habis.png");
        lblTotalKategori = card("Total Kategori", "0", "kategori minuman", 1100, 220, new Color(240, 247, 255), "/assets/total_kategori.png");

        JButton btnSemua = new RoundedButton("Semua Barang", new Color(98, 55, 230), Color.WHITE);
        btnSemua.setBounds(300, 270, 130, 38);
        add(btnSemua);

        JButton btnFilterMenipis = new RoundedButton("Stok Menipis", Color.WHITE, new Color(120, 120, 140));
        btnFilterMenipis.setBounds(440, 270, 120, 38);
        btnFilterMenipis.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true));
        add(btnFilterMenipis);

        JButton btnFilterHabis = new RoundedButton("Stok Habis", Color.WHITE, new Color(120, 120, 140));
        btnFilterHabis.setBounds(570, 270, 110, 38);
        btnFilterHabis.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 235), 1, true));
        add(btnFilterHabis);

        txtCari = new JTextField("Cari nama barang...");[cite: 2]
        txtCari.setBounds(800, 270, 250, 38);[cite: 2]
        txtCari.setForeground(Color.GRAY);[cite: 2]
        txtCari.setFont(new Font("Segoe UI", Font.PLAIN, 13));[cite: 2]
        txtCari.setMargin(new Insets(0, 12, 0, 0));[cite: 2]
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
        panelTableContainer.setBounds(300, 330, 1030, 330);
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

        tblLaporan = new JTable(model);[cite: 2]
        tblLaporan.setRowHeight(45);[cite: 2]
        tblLaporan.setFont(new Font("Segoe UI", Font.PLAIN, 13));[cite: 2]
        tblLaporan.setGridColor(new Color(245, 245, 250));[cite: 2]
        tblLaporan.setShowGrid(true);[cite: 2]
        tblLaporan.setIntercellSpacing(new Dimension(0, 1));[cite: 2]
        tblLaporan.setBackground(Color.WHITE);[cite: 2]
        tblLaporan.setSelectionBackground(new Color(245, 242, 255));[cite: 2]
        tblLaporan.setSelectionForeground(Color.BLACK);[cite: 2]

        JTableHeader header = tblLaporan.getTableHeader();[cite: 2]
        header.setFont(new Font("Segoe UI", Font.BOLD, 13));[cite: 2]
        header.setBackground(new Color(250, 250, 255));[cite: 2]
        header.setForeground(new Color(50, 50, 70));[cite: 2]
        header.setPreferredSize(new Dimension(0, 38));[cite: 2]

        setTableStyle();

        JScrollPane scroll = new JScrollPane(tblLaporan);[cite: 2]
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setBounds(20, 70, 990, 210);
        panelTableContainer.add(scroll);

        lblTotalData = new JLabel("Total 0 data");
        lblTotalData.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTotalData.setForeground(Color.GRAY);
        lblTotalData.setBounds(20, 290, 200, 25);
        panelTableContainer.add(lblTotalData);

        panelPagination = new JPanel(null);[cite: 2]
        panelPagination.setBounds(840, 285, 170, 35);[cite: 2]
        panelPagination.setBackground(Color.WHITE);[cite: 2]
        panelTableContainer.add(panelPagination);

        // ==========================================
        // FOOTER INFO SECTION
        // ==========================================
        JPanel panelInfo = new JPanel(null);[cite: 1]
        panelInfo.setBounds(300, 675, 1030, 60);[cite: 1]
        panelInfo.setBackground(new Color(250, 250, 255));[cite: 1]
        panelInfo.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 245)));[cite: 1]
        add(panelInfo);

        JLabel lblIconInfo = new JLabel(getIcon(PATH_ICON_INFO, 28, 28));
        lblIconInfo.setBounds(20, 16, 28, 28);
        panelInfo.add(lblIconInfo);

        JLabel lblInfoTitle = new JLabel("Informasi Laporan");[cite: 1]
        lblInfoTitle.setFont(new Font("Segoe UI", Font.BOLD, 13));[cite: 1]
        lblInfoTitle.setBounds(60, 10, 200, 20);[cite: 1]
        panelInfo.add(lblInfoTitle);

        lblWaktuUpdate = new JLabel("Laporan terakhir diperbarui: -");
        lblWaktuUpdate.setFont(new Font("Segoe UI", Font.PLAIN, 12));[cite: 1]
        lblWaktuUpdate.setForeground(Color.GRAY);
        lblWaktuUpdate.setBounds(60, 30, 400, 20);[cite: 1]
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

    private JButton menuButton(String text, int x, int y, boolean active) {[cite: 1]
        JButton btn = new JButton(text);[cite: 1]
        btn.setBounds(x, y, 170, 42);[cite: 1]
        btn.setFocusPainted(false);[cite: 1]
        btn.setBorderPainted(false);[cite: 1]
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));[cite: 1]

        if (active) {[cite: 1]
            btn.setBackground(new Color(98, 55, 230));[cite: 1]
            btn.setForeground(Color.WHITE);[cite: 1]
        } else {[cite: 1]
            btn.setBackground(new Color(250, 250, 255));[cite: 1]
            btn.setForeground(new Color(30, 30, 50));[cite: 1]
        }[cite: 1]
        return btn;[cite: 1]
    }

    private JLabel card(String title, String value, String desc, int x, int y, Color color, String iconPath) {[cite: 1]
        JPanel panel = new JPanel(null);[cite: 1]
        panel.setBounds(x, y, 220, 135);
        panel.setBackground(color);[cite: 1]
        panel.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 240), 1, true));[cite: 1]
        add(panel);[cite: 1]

        JLabel lblIcon = new JLabel(getIcon(iconPath, 36, 36));
        lblIcon.setBounds(20, 20, 36, 36);
        panel.add(lblIcon);[cite: 1]

        JLabel lblTitle = new JLabel(title);[cite: 1]
        lblTitle.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblTitle.setForeground(Color.GRAY);
        lblTitle.setBounds(70, 16, 140, 20);
        panel.add(lblTitle);[cite: 1]

        JLabel lblValue = new JLabel(value);[cite: 1]
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblValue.setBounds(70, 38, 140, 32);
        panel.add(lblValue);[cite: 1]

        JLabel lblDesc = new JLabel(desc);[cite: 1]
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));[cite: 1]
        lblDesc.setForeground(Color.LIGHT_GRAY);
        lblDesc.setBounds(20, 95, 180, 20);
        panel.add(lblDesc);[cite: 1]

        return lblValue;[cite: 1]
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

    private ImageIcon getIcon(String path, int width, int height) {[cite: 1]
        if(path == null || path.isEmpty()) return null;
        try {
            ImageIcon icon = new ImageIcon(getClass().getResource(path));[cite: 1]
            Image img = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);[cite: 1]
            return new ImageIcon(img);[cite: 1]
        } catch(Exception e) {
            return null;
        }
    }

    private void loadCardData() {[cite: 1]
        try {
            Connection conn = Koneksi.getConnection();[cite: 1]
            lblTotalBarang.setText(getValue(conn, "SELECT COUNT(*) FROM Barang"));[cite: 1]
            lblStokMenipis.setText(getValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok <= 10 AND stok > 0"));[cite: 1]
            lblStokHabis.setText(getValue(conn, "SELECT COUNT(*) FROM Barang WHERE stok = 0"));[cite: 1]
            lblTotalKategori.setText(getValue(conn, "SELECT COUNT(DISTINCT kategori) FROM Barang"));[cite: 1]
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy, HH:mm 'WIB'");
            lblWaktuUpdate.setText("Laporan terakhir diperbarui: " + sdf.format(new Date()));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal memuat ringkasan data: " + e.getMessage());
        }
    }

    private String getValue(Connection conn, String sql) throws SQLException {[cite: 1]
        Statement st = conn.createStatement();[cite: 1]
        ResultSet rs = st.executeQuery(sql);[cite: 1]
        if (rs.next()) return rs.getString(1);[cite: 1]
        return "0";[cite: 1]
    }

    private void loadTableData() {
        model.setRowCount(0);[cite: 2]
        String cari = txtCari.getText().trim();[cite: 2]
        if (cari.equals("Cari nama barang...")) cari = "";[cite: 2]

        try {
            Connection conn = Koneksi.getConnection();[cite: 2]
            String where = " WHERE namaBarang LIKE ? ";[cite: 2]

            if (statusFilter.equals("Menipis")) {
                where += " AND stok <= 10 AND stok > 0 ";
            } else if (statusFilter.equals("Habis")) {
                where += " AND stok = 0 ";
            }

            // Hitung Total Data untuk Pagination
            String countSql = "SELECT COUNT(*) FROM Barang " + where;[cite: 2]
            PreparedStatement countPs = conn.prepareStatement(countSql);[cite: 2]
            countPs.setString(1, "%" + cari + "%");[cite: 2]
            ResultSet countRs = countPs.executeQuery();[cite: 2]
            
            if (countRs.next()) totalData = countRs.getInt(1);[cite: 2]

            int totalPage = (int) Math.ceil((double) totalData / pageSize);[cite: 2]
            if (totalPage == 0) totalPage = 1;[cite: 2]
            if (currentPage > totalPage) currentPage = totalPage;[cite: 2]

            int offset = (currentPage - 1) * pageSize;[cite: 2]

            // Ambil Data Sesuai Limit/Offset (SQL Server Syntax sesuai context master)
            String sql = "SELECT * FROM Barang " + where[cite: 2]
                       + " ORDER BY idBarang OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";[cite: 2]

            PreparedStatement ps = conn.prepareStatement(sql);[cite: 2]
            ps.setString(1, "%" + cari + "%");[cite: 2]
            ps.setInt(2, offset);[cite: 2]
            ps.setInt(3, pageSize);[cite: 2]

            ResultSet rs = ps.executeQuery();[cite: 2]

            while (rs.next()) {[cite: 2]
                int stok = rs.getInt("stok");[cite: 2]
                String status = "Aman";
                if (stok == 0) status = "Habis";
                else if (stok <= 10) status = "Menipis";

                model.addRow(new Object[]{
                    String.format("%03d", rs.getInt("idBarang")), // Format ID 001, 002 dst
                    rs.getString("namaBarang"),[cite: 2]
                    rs.getString("kategori"),[cite: 2]
                    stok,
                    formatRupiah(rs.getBigDecimal("harga")),[cite: 2]
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
        panelPagination.removeAll();[cite: 2]
        if (totalPage <= 1) {[cite: 2]
            panelPagination.repaint();[cite: 2]
            panelPagination.revalidate();[cite: 2]
            return;[cite: 2]
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

        panelPagination.repaint();[cite: 2]
        panelPagination.revalidate();[cite: 2]
    }

    private String formatRupiah(java.math.BigDecimal harga) {[cite: 2]
        return String.format("%,.0f", harga).replace(",", ".");[cite: 2]
    }

    // ==========================================
    // CUSTOM RENDERER & HOOK COMPONENTS
    // ==========================================
    class StatusBadgeRenderer extends DefaultTableCellRenderer {
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            JLabel lbl = new JLabel(value.toString());
            lbl.setHorizontalAlignment(JLabel.CENTER);
            lbl.setOpaque(true);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 12));
            
            // Konfigurasi style badge berdasarkan isi data status
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

            // Wrapper panel untuk memberikan padding di dalam cell tabel
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);
            if (isSelected) panel.setBackground(new Color(245, 242, 255));
            
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.ipadx = 20; gbc.ipady = 6; // Mengatur lebar tinggi badge label
            panel.add(lbl, gbc);
            
            return panel;
        }
    }

    class RoundedButton extends JButton {[cite: 2]
        private final Color bgColor;
        public RoundedButton(String text, Color bgColor, Color fgColor) {
            super(text);[cite: 2]
            this.bgColor = bgColor;
            setForeground(fgColor);[cite: 2]
            setFocusPainted(false);[cite: 2]
            setBorderPainted(false);[cite: 2]
            setContentAreaFilled(false);[cite: 2]
            setOpaque(false);[cite: 2]
            setFont(new Font("Segoe UI", Font.BOLD, 13));[cite: 2]
        }
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();[cite: 2]
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);[cite: 2]
            g2.setColor(bgColor);[cite: 2]
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12); // Rounding disesuaikan agar lebih slim
            super.paintComponent(g);[cite: 2]
            g2.dispose();[cite: 2]
        }
    }

    public static void main(String[] args) {
        new FormLaporan().setVisible(true);
    }
}