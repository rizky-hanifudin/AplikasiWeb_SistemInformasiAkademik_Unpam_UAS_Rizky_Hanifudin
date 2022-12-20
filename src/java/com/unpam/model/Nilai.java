/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unpam.model;

import java.io.ByteArrayOutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
 * @author 4R135
 */
public class Nilai {

    private String nim;
    private String pesan;
    private Object[][] listNilai;
    private byte[] pdfasbytes;
    private final Koneksi koneksi = new Koneksi();

    public String getNim() {
        return nim;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public String getPesan() {
        return pesan;
    }

    public void setPesan(String pesan) {
        this.pesan = pesan;
    }

    public Object[][] getListNilai() {
        return listNilai;
    }

    public void setListNilai(Object[][] listNilai) {
        this.listNilai = listNilai;
    }

    public byte[] getPdfasbytes() {
        return pdfasbytes;
    }

    public boolean simpan() {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int jumlahSimpan = 0;
            String SQLStatemen;
            Statement sta;

            try {
                SQLStatemen = "update tbnilai set tugas='" + listNilai[0][1] + "', uts='" + listNilai[0][2]
                        + "', uas='" + listNilai[0][3] + "' where nim='" + nim + "' and kodematakuliah='" + listNilai[0][0] + "'";
                sta = connection.createStatement();
                jumlahSimpan = sta.executeUpdate(SQLStatemen);
            } catch (SQLException ex) {
            }

            if (jumlahSimpan < 1) {
                try {
                    SQLStatemen = "insert into tbnilai values ('" + nim + "','"
                            + listNilai[0][0] + "','" + listNilai[0][1] + "','"
                            + listNilai[0][2] + "','" + listNilai[0][3] + "')";
                    sta = connection.createStatement();
                    jumlahSimpan += sta.executeUpdate(SQLStatemen);
                } catch (SQLException ex) {
                }
            }

            if (jumlahSimpan > 0) {
                adaKesalahan = false;
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

        this.nim = nim;
        listNilai = null;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatemen;
            Statement sta;
            ResultSet rset;

            try {
                SQLStatemen = "select * from tbnilai where nim='" + nim + "'";
                sta = connection.createStatement();
                rset = sta.executeQuery(SQLStatemen);

                rset.next();
                rset.last();
                listNilai = new Object[rset.getRow()][4];

                rset.first();
                int i = 0;
                do {
                    if (!rset.getString("kodematakuliah").equals("")) {
                        listNilai[i] = new Object[]{rset.getString("kodematakuliah"), rset.getInt("tugas"), rset.getInt("uts"), rset.getInt("uas")};
                    }
                    i++;
                } while (rset.next());

                if (listNilai.length > 0) {
                    adaKesalahan = false;
                }

                sta.close();
                rset.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membaca data nilai siswa\n" + ex.getMessage();
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }

    public boolean cetakLaporan(String opsi, String nim, int semester, String kelas, String fileExt, String namaFile) {
        boolean adaKesalahan = false;
        Connection connection;
        pdfasbytes = null;

        if ((connection = koneksi.getConnection()) != null) {
            String SQLStatement;
            ResultSet resultSet = null;

            try {
                Statement statement = connection.createStatement();

                SQLStatement = " SELECT tbmahasiswa.`nim` AS tbmahasiswa_nim, "
                        + " tbmahasiswa.`nama` AS tbmahasiswa_nama, "
                        + " tbmahasiswa.`semester` AS tbmahasiswa_semester, "
                        + " tbmahasiswa.`kelas` AS tbmahasiswa_kelas, "
                        + " tbmatakuliah.`kode` AS tbmatakuliah_kodematakuliah, "
                        + " tbmatakuliah.`nama` AS tbmatakuliah_namamatakuliah, "
                        + " tbmatakuliah.`sks` AS tbmatakuliah_jumlahsks, "
                        + " tbnilai.`nim` AS tbnilai_nim, "
                        + " tbnilai.`kodematakuliah` AS tbnilai_kodematakuliah, "
                        + " tbnilai.`tugas` AS tbnilai_tugas, "
                        + " tbnilai.`uts` AS tbnilai_uts, "
                        + " tbnilai.`uas` AS tbnilai_uas, "
                        + " round((tbnilai.`tugas`+tbnilai.`uts`+tbnilai.`uas`)/3, 2) AS tbnilai_nilaiakhir, "
                        + " (if((tbnilai.`tugas`+tbnilai.`uts`+tbnilai.`uas`)/3>=85,'A', "
                        + " if((tbnilai.`tugas`+tbnilai.`uts`+tbnilai.`uas`)/3>=70,'B', "
                        + " if((tbnilai.`tugas`+tbnilai.`uts`+tbnilai.`uas`)/3>=55,'C', "
                        + " if((tbnilai.`tugas`+tbnilai.`uts`+tbnilai.`uas`)/3>=40,'D','E'))))) AS tbnilai_nilaihuruf, "
                        + " (if((tbnilai.`tugas`+tbnilai.`uts`+tbnilai.`uas`)/3>=55,'Lulus','Tidak Lulus')) AS tbnilai_status "
                        + " FROM "
                        + " `tbmahasiswa` tbmahasiswa INNER JOIN `tbnilai` tbnilai ON tbmahasiswa.`nim` = tbnilai.`nim` "
                        + " INNER JOIN `tbmatakuliah` tbmatakuliah ON tbnilai.`kodematakuliah` = tbmatakuliah.`kode` ";

                if (opsi.equals("NIM")) {
                    SQLStatement = SQLStatement + " where tbmahasiswa.`nim`='" + nim + "'";
                } else if (opsi.equals("semesterKelas")) {
                    if (semester != 0) {
                        SQLStatement = SQLStatement + " where tbmahasiswa.`semester`=" + semester;

                        if (!kelas.equals("")) {
                            SQLStatement = SQLStatement + " and tbmahasiswa.`kelas`='" + kelas + "' ";
                        }
                    } else {
                        if (!kelas.equals("")) {
                            SQLStatement = SQLStatement + " where tbmahasiswa.`kelas`='" + kelas + "' ";
                        }
                    }
                }

                SQLStatement = SQLStatement + " ORDER BY "
                        + " tbmahasiswa.`semester` ASC, "
                        + " tbmahasiswa.`kelas` ASC, "
                        + " tbmahasiswa.`nama` ASC, "
                        + " tbmahasiswa.`nim` ASC";

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

    public boolean hapus(String nim, String kodeMataKuliah) {
        boolean adaKesalahan = false;
        Connection connection;

        if ((connection = koneksi.getConnection()) != null) {
            int jumlahHapus;
            Statement sta;

            try {
                String SQLStatemen = "delete from tbnilai where nim='" + nim + "' and kodematakuliah='" + kodeMataKuliah + "'";
                sta = connection.createStatement();
                jumlahHapus = sta.executeUpdate(SQLStatemen);

                if (jumlahHapus < 1) {
                    pesan = "Data nilai untuk NIM " + nim + " dan kode mata kuliah  " + kodeMataKuliah + " tidak ditemukan";
                    adaKesalahan = true;
                }

                sta.close();
                connection.close();
            } catch (SQLException ex) {
                adaKesalahan = true;
                pesan = "Tidak dapat membuka tabel tbnilai\n" + ex;
            }
        } else {
            adaKesalahan = true;
            pesan = "Tidak dapat melakukan koneksi ke server\n" + koneksi.getPesanKesalahan();
        }

        return !adaKesalahan;
    }
}
