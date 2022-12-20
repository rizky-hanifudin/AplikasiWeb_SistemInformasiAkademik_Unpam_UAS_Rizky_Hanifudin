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
import java.util.Date;
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
public class PembayaranKuliah {

    private String noTagihan, nim, prodi, status;
    private int pembayaran, jumlahBayar;
    private Date tanggalBayar;
    private String pesan;
    private Object[][] list;
    private byte[] pdfasbytes;

    public byte[] getPdfasbytes() {
        return pdfasbytes;
    }

    public PembayaranKuliah() {
    }

    public PembayaranKuliah(String noTagihan, String nim, String prodi, String status, int pembayaran, int jumlahBayar) {
        this.noTagihan = noTagihan;
        this.nim = nim;
        this.prodi = prodi;
        this.status = status;
        this.pembayaran = pembayaran;
        this.jumlahBayar = jumlahBayar;
    }

    public String getNoTagihan() {
        return noTagihan;
    }

    public void setNoTagihan(String noTagihan) {
        this.noTagihan = noTagihan;
    }

    public String getProdi() {
        return prodi;
    }

    public void setProdi(String prodi) {
        this.prodi = prodi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPembayaran() {
        return pembayaran;
    }

    public void setPembayaran(int pembayaran) {
        this.pembayaran = pembayaran;
    }

    public int getJumlahBayar() {
        return jumlahBayar;
    }

    public void setJumlahBayar(int jumlahBayar) {
        this.jumlahBayar = jumlahBayar;
    }

    public Date getTanggalBayar() {
        return tanggalBayar;
    }

    public void setTanggalBayar(Date tanggalBayar) {
        this.tanggalBayar = tanggalBayar;
    }
    private final Koneksi koneksi = new Koneksi();
    private final PesanDialog pesanDialog = new PesanDialog();

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
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
                SQLStatemen = 
                    "replace into tbpembayarankuliah"
                    + "(no_tagihan, nim, prodi, pembayaran, jumlah_bayar, status, tanggal_bayar) "
                    + " values "
                    + "( ?,  ?,  ?,  ?,  ?,  ?,  ?) ";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, noTagihan);
                preparedStatement.setString(2, nim);
                preparedStatement.setString(3, prodi);
                preparedStatement.setInt(4, pembayaran);
                preparedStatement.setInt(5, jumlahBayar);
                status = jumlahBayar >= pembayaran ? "LUNAS" : "KURANG";
                preparedStatement.setString(6, status);
                if (tanggalBayar == null) tanggalBayar = new Date();
                preparedStatement.setTimestamp(7, new java.sql.Timestamp(tanggalBayar.getTime()));
                jumlahSimpan = preparedStatement.executeUpdate();

                if (jumlahSimpan < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menyimpan data pembayaran kuliah";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbpembayarankuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
    public boolean hapus(String noTagihan) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int rowAffected;
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "DELETE FROM tbpembayarankuliah WHERE no_tagihan = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, noTagihan);
                rowAffected = preparedStatement.executeUpdate();

                if (rowAffected < 1) {
                    adaKesalahan = true;
                    pesan = "Gagal menghapus data pembayaran kuliah";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbpembayarankuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    public boolean baca(String noTagihan) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen = "";
            PreparedStatement preparedStatement;

            try {
                SQLStatemen = "SELECT * FROM tbpembayarankuliah WHERE no_tagihan = ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setString(1, noTagihan);
                ResultSet res = preparedStatement.executeQuery();

                if (res.next()){
                    this.noTagihan = res.getString("no_tagihan");
                    this.nim = res.getString("nim");
                    this.prodi = res.getString("prodi");
                    this.pembayaran = res.getInt("pembayaran");
                    this.jumlahBayar = res.getInt("jumlah_bayar");
                    this.status = res.getString("status");
                    this.tanggalBayar = new Date(res.getTimestamp("tanggal_bayar").getTime());
                } else {
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data pembayaran kuliah";
                }

                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbpembayarankuliah\n" + ex + "\n" + SQLStatemen;
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
                SQLStatemen = "SELECT * FROM tbpembayarankuliah LIMIT ? OFFSET ?";
                preparedStatement = connection.prepareStatement(SQLStatemen);
                preparedStatement.setInt(1, jumlah);
                preparedStatement.setInt(2, mulai);
                ResultSet res = preparedStatement.executeQuery();
                
                if (!res.next()){
                    adaKesalahan = true;
                    pesan = "Gagal mengambil data pembayaran kuliah";
                }
                var i = 0;
                var list = new ArrayList<Object[]>();
                do {
                    var item = new Object[7];
                    item[0] = res.getString("no_tagihan");
                    item[1] = res.getString("nim");
                    item[2] = res.getString("prodi");
                    item[3] = res.getInt("pembayaran");
                    item[4] = res.getInt("jumlah_bayar");
                    item[5] = res.getString("status");
                    item[6] = new Date(res.getTimestamp("tanggal_bayar").getTime());
                    list.add(item);
                    i++;
                } while(res.next());
                this.list = new Object[i][7];
                this.list = list.toArray(this.list);
                preparedStatement.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbpembayarankuliah\n" + ex + "\n" + SQLStatemen;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }
        return !adaKesalahan;
    }
    
    public boolean cetakLaporan(String opsi, String nim, String fileExt, String namaFile) {
        boolean adaKesalahan = false;
        Connection connection;
        pdfasbytes = null;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement;
            ResultSet resultSet = null;

            try {
                Statement statement = connection.createStatement();

                SQLStatement = "SELECT " +
                                    "tbpembayarankuliah.no_tagihan, " +
                                    "tbpembayarankuliah.nim, " +
                                    "tbmahasiswa.nama, " +
                                    "tbpembayarankuliah.prodi, " +
                                    "tbpembayarankuliah.pembayaran, " +
                                    "tbpembayarankuliah.jumlah_bayar, " +
                                    "tbpembayarankuliah.status, " +
                                    "tbpembayarankuliah.tanggal_bayar " +
                                "FROM tbpembayarankuliah " +
                                "JOIN tbmahasiswa ON tbmahasiswa.nim = tbpembayarankuliah.nim";
                if (opsi.equals("NIM")) {
                    SQLStatement = SQLStatement + " where tbmahasiswa.nim='" + nim + "'";
                }
                SQLStatement = SQLStatement + " ORDER BY tbpembayarankuliah.no_tagihan ASC";
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
