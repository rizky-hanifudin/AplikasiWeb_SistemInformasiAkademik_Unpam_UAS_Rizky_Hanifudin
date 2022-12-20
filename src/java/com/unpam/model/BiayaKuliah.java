/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import com.unpam.view.PesanDialog;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author indbr
 */
public class BiayaKuliah {

    private String prodi;
    private int biayaSemester;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public void setBiayaSemester(int biayaSemester) {
        this.biayaSemester = biayaSemester;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public String getProdi() {
        return prodi;
    }

    public int getBiayaSemester() {
        return biayaSemester;
    }

    public Koneksi getKoneksi() {
        return koneksi;
    }

    public PesanDialog getPesanDialog() {
        return pesanDialog;
    }


    public String getPesan() {
        return pesan;
    }

    public Object[][] getList() {
        return list;
    }

    public void setList(Object[][] list) {
        this.list = list;
    }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;
        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            Statement sta;
            String SQLStatemen = "";
            try {
                SQLStatemen = "replace into tbbiayakuliah values('" + prodi + "'," + biayaSemester + ")";
                sta = connection.createStatement();
                jumlahSimpan = sta.executeUpdate(SQLStatemen);
                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data biaya kuliah";
                }
            } catch (SQLException ex) {
                pesan = "Tidak dapat membuka tabel tbbiayakuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
    public boolean hapus(String prodi) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int rowAffected;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "DELETE FROM tbbiayakuliah WHERE prodi = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, prodi);
                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data biaya kuliah";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbbiayakuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    public boolean baca(String prodi) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "SELECT * FROM tbbiayakuliah WHERE prodi = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, prodi);
                ResultSet res = preparedStatement.executeQuery();

                if (res.next()){
                    this.prodi = res.getString("prodi");
                    this.biayaSemester = res.getInt("biaya_semester");
                } else {
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data biaya kuliah";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbbiayakuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    public boolean bacaData(int mulai, int jumlah) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "SELECT * FROM tbbiayakuliah LIMIT ? OFFSET ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setInt(1, jumlah);
                preparedStatement.setInt(2, mulai);
                ResultSet res = preparedStatement.executeQuery();
                
                if (!res.next()){
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data biaya kuliah";
                }
                var i = 0;
                var list = new ArrayList<Object[]>();
                do {
                    var item = new Object[2];
                    item[0] = res.getString("prodi");
                    item[1] = res.getInt("biaya_semester");
                    list.add(item);
                    i++;
                } while(res.next());
                this.list = new Object[i][2];
                this.list = list.toArray(this.list);
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbbiayakuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
}
