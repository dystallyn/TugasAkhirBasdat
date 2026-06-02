/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package koneksi;

/**
 *
 * @author KINDLY
 */
import java.sql.Connection;
import java.sql.DriverManager;

public class Koneksi {
    private static Connection conn;

    public static Connection getConnection() {
        try {

            String url =
                "jdbc:sqlserver://LAPTOP-4MF4DDF5;"
                + "databaseName=InventoryDB;"
                + "encrypt=false;"
                + "trustServerCertificate=true";

            String user = "Db_Con";
            String pass = "123"; 

            conn = DriverManager.getConnection(url, user, pass);

            System.out.println("Koneksi Berhasil");

        } catch (Exception e) {

            System.out.println("Koneksi Gagal : "
                    + e.getMessage());

        }

        return conn;
    }
}
