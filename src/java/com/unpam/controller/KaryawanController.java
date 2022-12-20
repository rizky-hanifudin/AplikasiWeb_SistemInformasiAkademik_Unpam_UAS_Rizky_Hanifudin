/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Karyawan;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author indbr
 */
public class KaryawanController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        Karyawan karyawan = new Karyawan();
        String userName = "";
        String allowedGender[] = {"Pria", "Wanita"};
        String tombol = request.getParameter("tombol");
        String nim = request.getParameter("nim");
        String nama = request.getParameter("nama");
        String jenisKelamin = request.getParameter("jenisKelamin");
        String alamat = request.getParameter("alamat");
        String kelurahan = request.getParameter("kelurahan");
        String kecamatan = request.getParameter("kecamatan");
        String kabupaten = request.getParameter("kabupaten");
        String provinsi = request.getParameter("provinsi");
        String mulaiParameter = request.getParameter("mulai");
        String jumlahParameter = request.getParameter("jumlah");
        String nimDipilih = request.getParameter("nimDipilih");

        if (tombol == null) {
            tombol = "";
        }
        if (nim == null) {
            nim = "";
        }
        if (nama == null) {
            nama = "";
        }
        if (jenisKelamin == null) {
            jenisKelamin = allowedGender[0];
        }
        if (alamat == null) {
            alamat = "";
        }
        if (kelurahan == null) {
            kelurahan = "";
        }
        if (kecamatan == null) {
            kecamatan = "";
        }
        if (kabupaten == null) {
            kabupaten = "";
        }
        if (provinsi == null) {
            provinsi = "";
        }
        if (nimDipilih == null) {
            nimDipilih = "";
        }

        int mulai = 0, jumlah = 10;

        try {
            mulai = Integer.parseInt(mulaiParameter);
        } catch (NumberFormatException ex) {
        }

        try {
            jumlah = Integer.parseInt(jumlahParameter);
        } catch (NumberFormatException ex) {
        }

        String keterangan = "<br>";

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception ex) {
        }

        if (!((userName == null) || userName.equals(""))) {

            switch (tombol) {
                case "Simpan":
                    if (!nim.equals("")) {
                        karyawan.setId(nim);
                        karyawan.setNim(nim);
                        karyawan.setNama(nama);
                        karyawan.setJenisKelamin(jenisKelamin);
                        karyawan.setAlamat(alamat);
                        karyawan.setKelurahan(kelurahan);
                        karyawan.setKecamatan(kecamatan);
                        karyawan.setKabupaten(kabupaten);
                        karyawan.setProvinsi(provinsi);
                        if (karyawan.simpan()) {
                            nim = "";
                            nama = "";
                            jenisKelamin = "";
                            alamat = "";
                            kelurahan = "";
                            kecamatan = "";
                            kabupaten = "";
                            provinsi = "";
                            keterangan = "Sudah tersimpan";
                        } else {
                            keterangan = karyawan.getPesan();
                        }
                    } else {
                        keterangan = "NIM tidak boleh kosong";
                    }   break;
                case "Hapus":
                    if (!nim.equals("")) {
                        if (karyawan.hapus(nim)) {
                            nim = "";
                            nama = "";
                            jenisKelamin = "";
                            alamat = "";
                            kelurahan = "";
                            kecamatan = "";
                            kabupaten = "";
                            provinsi = "";
                            keterangan = "Data sudah dihapus";
                        } else {
                            keterangan = karyawan.getPesan();
                        }
                    } else {
                        keterangan = "NIM masih kosong";
                    }   break;
                case "Cari":
                    if (!nim.equals("")) {
                        if (karyawan.baca(nim)) {
                            nim = karyawan.getNim();
                            nama = karyawan.getNama();
                            jenisKelamin = karyawan.getJenisKelamin();
                            alamat = karyawan.getAlamat();
                            kelurahan = karyawan.getKelurahan();
                            kecamatan = karyawan.getKecamatan();
                            kabupaten = karyawan.getKabupaten();
                            provinsi = karyawan.getProvinsi();
                            keterangan = "<br>";
                        } else {
                            nama = "";
                            jenisKelamin = "";
                            alamat = "";
                            kelurahan = "";
                            kecamatan = "";
                            kabupaten = "";
                            provinsi = "";
                            keterangan = "NIM " + nim + " tidak ada";
                        }
                    } else {
                        keterangan = "NIM harus diisi";
                    }   break;
                case "Pilih":
                    nim = nimDipilih;
                    nama = "";
                    jenisKelamin = "";
                    alamat = "";
                    kelurahan = "";
                    kecamatan = "";
                    kabupaten = "";
                    provinsi = "";
                    if (!nimDipilih.equals("")) {
                        if (karyawan.baca(nimDipilih)) {
                            nim = karyawan.getNim();
                            nama = karyawan.getNama();
                            jenisKelamin = karyawan.getJenisKelamin();
                            alamat = karyawan.getAlamat();
                            kelurahan = karyawan.getKelurahan();
                            kecamatan = karyawan.getKecamatan();
                            kabupaten = karyawan.getKabupaten();
                            provinsi = karyawan.getProvinsi();
                            keterangan = "<br>";
                        } else {
                            keterangan = "NIM " + nim + " tidak ada";
                        }
                    } else {
                        keterangan = "Tidak ada yang dipilih";
                    }   break;
                default:
                    break;
            }

            String kontenLihat = "";
            if (tombol.equals("Lihat") || tombol.equals("Sebelumnya") || tombol.equals("Berikutnya") || tombol.equals("Tampilkan")) {
                kontenLihat = "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";

                if (tombol.equals("Sebelumnya")) {
                    mulai -= jumlah;
                    if (mulai < 0) {
                        mulai = 0;
                    }
                }

                if (tombol.equals("Berikutnya")) {
                    mulai += jumlah;
                }

                Object[][] listKaryawan = null;
                if (karyawan.bacaData(mulai, jumlah)) {
                    listKaryawan = karyawan.getList();
                } else {
                    keterangan = karyawan.getPesan();
                }

                if (listKaryawan != null) {
                    for (int i = 0; i < listKaryawan.length; i++) {
                        kontenLihat += "<tr>";
                        kontenLihat += "<td>";
                        if (i == 0) {
                            kontenLihat += "<input type='radio' checked name='nimDipilih' value='" + listKaryawan[i][0].toString() + "'>";
                        } else {
                            kontenLihat += "<input type='radio' name='nimDipilih' value='" + listKaryawan[i][0].toString() + "'>";
                        }
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listKaryawan[i][0].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listKaryawan[i][1].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "</tr>";
                    }
                }

                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";

                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";
                kontenLihat += "<tr>";
                kontenLihat += "<td align='center'><input type='submit' name='tombol' value='Sebelumnya' style='width: 100px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombol' value='Pilih' style='width: 60px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombol' value='Berikutnya' style='width: 100px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td align='center'>Mulai <input type='text' name='mulai' value=" + mulai + " style='width: 40px'></td>";
                kontenLihat += "<td>Jumlah";
                kontenLihat += "<select name='jumlah'>";
                for (int i = 1; i <= 10; i++) {
                    if (jumlah == (i * 10)) {
                        kontenLihat += "<option selected value=" + i * 10 + ">" + i * 10 + "</option>";
                    } else {
                        kontenLihat += "<option value=" + i * 10 + ">" + i * 10 + "</option>";
                    }
                }
                kontenLihat += "</select>";
                kontenLihat += "</td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombol' value='Tampilkan' style='width: 90px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";
            }

            String konten = "<h2>Master Data Karyawan</h2>"
                    + "<form action='KaryawanController' method='post'>"
                    + "<table>"
                    + "<tr>"
                    + "<td align='right'>NIM</td>"
                    + "<td align='left'><input type='text' value='" + nim + "' name='nim' maxlength='15' size='15'><input type='submit' name='tombol' value='Cari'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Nama</td>"
                    + "<td align='left'><input type='text' value='" + nama + "' name='nama' maxlength='30' size='30'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Jenis Kelamin</td>"
                    + "<td align='left'>"
                    + "<select name='jenisKelamin'>";

            for (String val : allowedGender) {
                konten += "<option " + (val.equals(jenisKelamin) ? "selected" : "" )+" value=" + val + ">" + val + "</option>";
            }

            konten += "</select>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Alamat</td>"
                    + "<td align='left'><input type='text' value='" + alamat + "' name='alamat' size='80'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Kelurahan</td>"
                    + "<td align='left'><input type='text' value='" + kelurahan + "' name='kelurahan' size='80'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Kecamatan</td>"
                    + "<td align='left'><input type='text' value='" + kecamatan + "' name='kecamatan' size='80'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Kabupaten</td>"
                    + "<td align='left'><input type='text' value='" + kabupaten + "' name='kabupaten' size='80'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Provinsi</td>"
                    + "<td align='left'><input type='text' value='" + provinsi + "' name='provinsi' size='80'></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td colspan='2' align='center'>"
                    + "<table>"
                    + "<tr>"
                    + "<td align='center'><input type='submit' name='tombol' value='Simpan' style='width: 100px'></td>"
                    + "<td align='center'><input type='submit' name='tombol' value='Hapus' style='width: 100px'></td>"
                    + "<td align='center'><input type='submit' name='tombol' value='Lihat' style='width: 100px'></td>"
                    + "</tr>"
                    + "</table>"
                    + "</td>"
                    + "</tr>"
                    + kontenLihat
                    + "</table>"
                    + "</form>";

            Cookie cookie;
            try {
                int i = 0, batas;
                do {
                    batas = (konten.length() > (3000 * (i + 1))) ? (3000 * (i + 1)) : konten.length();
                    cookie = new Cookie("konten" + Integer.toString(i), konten.substring(3000 * i, batas));
                    cookie.setMaxAge(2);
                    response.addCookie(cookie);
                    i++;
                } while (batas < konten.length());
            } catch (Exception ex) {
                cookie = new Cookie("konten", ex.getMessage());
                cookie.setMaxAge(2);
                response.addCookie(cookie);
            }
        }

        response.sendRedirect(".");
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
