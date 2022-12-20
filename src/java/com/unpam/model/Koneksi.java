/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author indbr
 */
public class Koneksi {

    private static final String driver = "com.mysql.jdbc.Driver";
    private static final String database = "jdbc:mysql://localhost:3306/dbaplikasi_sia_unpam_uas";
    private static final String user = "root";
    private static final String password = "";

    private Connection connection;
    private String pesanKesalahan;

    public String getPesanKesalahan() {
        return pesanKesalahan;
    }

    public Connection getConnection() {
        connection = null;
        pesanKesalahan = "";

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(database + "?user=" + user + "&password=" + password + "");
        } catch (ClassNotFoundException ex) {
            pesanKesalahan = "JDBC Driver tidak ditemukan atau rusak\n" + ex;
        } catch (SQLException ex) {
            pesanKesalahan = "Koneksi ke " + database + " gagal\n" + ex;
        }
        
        return connection;
    }
}
