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
    public FormLaporan() {
        setTitle("Laporan");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        add(new JLabel("Halaman Laporan"));
    }
}
