/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.unpam.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import com.unpam.view.PesanDialog;
import java.util.ArrayList;

/**
 *
 * @author indbr
 */
public class Mahasiswa {

    private String nim, nama, kelas, password;
    private int semester;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKelas() {
        return kelas;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public String getPesan() {
        return pesan;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
            int jumlahSimpan;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "insert into tbmahasiswa(nim, nama, semester, kelas, password) values( ?,  ?,  ?,  ?,  ?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nim);
                preparedStatement.setString(2, nama);
                preparedStatement.setInt(3, semester);
                preparedStatement.setString(4, kelas);
                preparedStatement.setString(5, password);
                jumlahSimpan = preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data mahasiswa";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
    public boolean hapus(String nim) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int rowAffected;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "DELETE FROM tbmahasiswa WHERE nim = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nim);
                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data mahasiswa";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    public boolean baca(String nim) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "SELECT * FROM tbmahasiswa WHERE nim = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, nim);
                ResultSet res = preparedStatement.executeQuery();

                if (res.next()){
                    this.nim = res.getString("nim");
                    this.nama = res.getString("nama");
                    this.semester = res.getInt("semester");
                    this.kelas = res.getString("kelas");
                    this.password = res.getString("password");
                } else {
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data mahasiswa";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex + "\n" + SQLStatemen;
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
                SQLStatemen = "SELECT * FROM tbmahasiswa LIMIT ? OFFSET ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setInt(1, jumlah);
                preparedStatement.setInt(2, mulai);
                ResultSet res = preparedStatement.executeQuery();
                
                if (!res.next()){
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data mahasiswa";
                }
                var i = 0;
                var list = new ArrayList<Object[]>();
                do {
                    var item = new Object[5];
                    item[0] = res.getString("nim");
                    item[1] = res.getString("nama");
                    item[2] = res.getInt("semester");
                    item[3] = res.getString("kelas");
                    item[4] = res.getString("password");
                    list.add(item);
                    i++;
                } while(res.next());
                this.list = new Object[i][5];
                this.list = list.toArray(this.list);
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbmahasiswa\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
}
