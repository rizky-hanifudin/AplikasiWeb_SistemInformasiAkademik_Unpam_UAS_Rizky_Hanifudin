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
import java.io.ByteArrayOutputStream;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRRtfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.oasis.JROdtExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

/**
 *
 * @author indbr
 */
public class Karyawan {

    private String id, nim, nama, jenisKelamin, alamat, 
            kelurahan, kecamatan, kabupaten, provinsi;
    private String pesan;
    private Object[][] list;
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();
    private byte[] pdfasbytes;

    public byte[] getPdfasbytes() {
        return pdfasbytes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getKelurahan() {
        return kelurahan;
    }

    public void setKelurahan(String kelurahan) {
        this.kelurahan = kelurahan;
    }

    public String getKecamatan() {
        return kecamatan;
    }

    public void setKecamatan(String kecamatan) {
        this.kecamatan = kecamatan;
    }

    public String getKabupaten() {
        return kabupaten;
    }

    public void setKabupaten(String kabupaten) {
        this.kabupaten = kabupaten;
    }

    public String getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(String provinsi) {
        this.provinsi = provinsi;
    }

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
            int jumlahSimpan;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "insert into karyawan(id, nim, nama, jenis_kelamin, alamat, kelurahan, kecamatan, kabupaten, provinsi) values( ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?,  ?)";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, nim);
                preparedStatement.setString(3, nama);
                preparedStatement.setString(4, jenisKelamin);
                preparedStatement.setString(5, alamat);
                preparedStatement.setString(6, kelurahan);
                preparedStatement.setString(7, kecamatan);
                preparedStatement.setString(8, kabupaten);
                preparedStatement.setString(9, provinsi);
                jumlahSimpan = preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data karyawan";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel karyawan\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
    public boolean hapus(String id) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int rowAffected;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "DELETE FROM karyawan WHERE id = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, id);
                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data karyawan";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel karyawan\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    public boolean baca(String id) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "SELECT * FROM karyawan WHERE id = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, id);
                ResultSet res = preparedStatement.executeQuery();

                if (res.next()){
                    this.id = res.getString("id");
                    this.nim = res.getString("nim");
                    this.nama = res.getString("nama");
                    this.jenisKelamin = res.getString("jenis_kelamin");
                    this.alamat = res.getString("alamat");
                    this.kelurahan = res.getString("kelurahan");
                    this.kecamatan = res.getString("kecamatan");
                    this.kabupaten = res.getString("kabupaten");
                    this.provinsi = res.getString("provinsi");
                } else {
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data karyawan";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel karyawan\n" + ex + "\n" + SQLStatemen;
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
                SQLStatemen = "SELECT * FROM karyawan LIMIT ? OFFSET ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setInt(1, jumlah);
                preparedStatement.setInt(2, mulai);
                ResultSet res = preparedStatement.executeQuery();
                
                if (!res.next()){
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data karyawan";
                }
                var i = 0;
                var list = new ArrayList<Object[]>();
                do {
                    var item = new Object[9];
                    item[0] = res.getString("id");
                    item[1] = res.getString("nim");
                    item[2] = res.getString("nama");
                    item[3] = res.getString("jenis_kelamin");
                    item[4] = res.getString("alamat");
                    item[5] = res.getString("kelurahan");
                    item[6] = res.getString("kecamatan");
                    item[7] = res.getString("kabupaten");
                    item[8] = res.getString("provinsi");
                    list.add(item);
                    i++;
                } while(res.next());
                this.list = new Object[i][9];
                this.list = list.toArray(this.list);
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel karyawan\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    public boolean cetakLaporan(String opsi, String fileExt, String namaFile) {
        boolean adaKesalahan = false;
        Connection connection;
        pdfasbytes = null;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement;
            ResultSet resultSet = null;

            try {
                Statement statement = connection.createStatement();

                SQLStatement = "SELECT * FROM karyawan " +
                               "ORDER BY id ASC";
                resultSet = statement.executeQuery(SQLStatement);
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data\n" + ex;
            }

            if ((!adaKesalahan) && (resultSet != null)) {
                try {
                    JasperDesign disain = JRXmlLoader.load(namaFile);
                    JasperReport nilaiLaporan = JasperCompileManager.compileReport(disain);
                    JRResultSetDataSource resultSetDataSource = new JRResultSetDataSource(resultSet);
                    JasperPrint cetak = JasperFillManager.fillReport(nilaiLaporan, new HashMap(), resultSetDataSource);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

                    JRExporter exporter = null;
                    if (fileExt.equalsIgnoreCase("PDF")) {
                        exporter = new JRPdfExporter();
                    } else if (fileExt.equalsIgnoreCase("XLSX")) {
                        exporter = new JRXlsxExporter();
                    } else if (fileExt.equalsIgnoreCase("XLS")) {
                        exporter = new JRXlsExporter();
                    } else if (fileExt.equalsIgnoreCase("DOCX")) {
                        exporter = new JRDocxExporter();
                    } else if (fileExt.equalsIgnoreCase("ODT")) {
                        exporter = new JROdtExporter();
                    } else if (fileExt.equalsIgnoreCase("RTF")) {
                        exporter = new JRRtfExporter();
                    } else {
                        adaKesalahan = true;
                        pesan = "Format file dengan ektensi " + fileExt + " tidak terdaftar";
                    }

                    if (!adaKesalahan && (exporter != null)) {
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, cetak);
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, byteArrayOutputStream);
                        exporter.exportReport();
                        pdfasbytes = byteArrayOutputStream.toByteArray();
                    }
                } catch (JRException ex) {
                    adaKesalahan = true;
                    pesan = "Tidak dapat mencetak laporan\n" + ex;
                }
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
