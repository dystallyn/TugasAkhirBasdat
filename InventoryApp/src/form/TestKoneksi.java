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

public class TestKoneksi {
    public static void main(String[] args) {

        if (Koneksi.getConnection() != null) {

            System.out.println("Database Terhubung");

        }

    }

}
