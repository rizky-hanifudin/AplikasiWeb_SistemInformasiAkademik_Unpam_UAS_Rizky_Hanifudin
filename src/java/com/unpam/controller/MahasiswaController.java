/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Enkripsi;
import com.unpam.model.Mahasiswa;
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
public class MahasiswaController extends HttpServlet {

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
        Mahasiswa mahasiswa = new Mahasiswa();
        Enkripsi enkripsi = new Enkripsi();
        String userName = "";

        String tombol = request.getParameter("tombol");
        String nim = request.getParameter("nim");
        String nama = request.getParameter("nama");
        String semester = request.getParameter("semester");
        String kelas = request.getParameter("kelas");
        String password = request.getParameter("password");
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
        if (semester == null) {
            semester = "1";
        }
        if (kelas == null) {
            kelas = "A";
        }
        if (password == null) {
            password = "";
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

            if (tombol.equals("Simpan")) {
                if (!nim.equals("")) {
                    mahasiswa.setNim(nim);
                    mahasiswa.setNama(nama);
                    mahasiswa.setSemester(Integer.parseInt(semester));
                    mahasiswa.setKelas(kelas);
                    String passwordEcrypted = "";
                    try {
                        passwordEcrypted = enkripsi.hashMD5(password);
                    } catch (Exception ex) {
                    }
                    mahasiswa.setPassword(passwordEcrypted);

                    if (mahasiswa.simpan()) {
                        nim = "";
                        nama = "";
                        semester = "1";
                        kelas = "A";
                        password = "";
                        keterangan = "Sudah tersimpan";
                    } else {
                        keterangan = mahasiswa.getPesan();
                    }
                } else {
                    keterangan = "NIM tidak boleh kosong";
                }
            } else if (tombol.equals("Hapus")) {
                if (!nim.equals("")) {
                    if (mahasiswa.hapus(nim)) {
                        nim = "";
                        nama = "";
                        semester = "1";
                        kelas = "A";
                        password = "";
                        keterangan = "Data sudah dihapus";
                    } else {
                        keterangan = mahasiswa.getPesan();
                    }
                } else {
                    keterangan = "NIM masih kosong";
                }
            } else if (tombol.equals("Cari")) {
                if (!nim.equals("")) {
                    if (mahasiswa.baca(nim)) {
                        nim = mahasiswa.getNim();
                        nama = mahasiswa.getNama();
                        semester = Integer.toString(mahasiswa.getSemester());
                        kelas = mahasiswa.getKelas();
                        password = mahasiswa.getPassword();
                        keterangan = "<br>";
                    } else {
                        nama = "";
                        semester = "1";
                        kelas = "A";
                        password = "";
                        keterangan = "NIM " + nim + " tidak ada";
                    }
                } else {
                    keterangan = "NIM harus diisi";
                }
            } else if (tombol.equals("Pilih")) {
                nim = nimDipilih;
                nama = "";
                semester = "1";
                kelas = "A";
                if (!nimDipilih.equals("")) {
                    if (mahasiswa.baca(nimDipilih)) {
                        nim = mahasiswa.getNim();
                        nama = mahasiswa.getNama();
                        semester = Integer.toString(mahasiswa.getSemester());
                        kelas = mahasiswa.getKelas();
                        password = mahasiswa.getPassword();
                        keterangan = "<br>";
                    } else {
                        keterangan = "NIM " + nim + " tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
                }
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

                Object[][] listMahasiswa = null;
                if (mahasiswa.bacaData(mulai, jumlah)) {
                    listMahasiswa = mahasiswa.getList();
                } else {
                    keterangan = mahasiswa.getPesan();
                }

                if (listMahasiswa != null) {
                    for (int i = 0; i < listMahasiswa.length; i++) {
                        kontenLihat += "<tr>";
                        kontenLihat += "<td>";
                        if (i == 0) {
                            kontenLihat += "<input type='radio' checked name='nimDipilih' value='" + listMahasiswa[i][0].toString() + "'>";
                        } else {
                            kontenLihat += "<input type='radio' name='nimDipilih' value='" + listMahasiswa[i][0].toString() + "'>";
                        }
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listMahasiswa[i][0].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listMahasiswa[i][1].toString();
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

            String konten = "<h2>Master Data Mahasiswa</h2>"
                    + "<form action='MahasiswaController' method='post'>"
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
                    + "<td align='right'>Semester</td>"
                    + "<td align='left'>"
                    + "<select name='semester'>";

            for (int i = 1; i <= 14; i++) {
                if (i == Integer.parseInt(semester)) {
                    konten += "<option selected value=" + i + ">" + i + "</option>";
                } else {
                    konten += "<option value=" + i + ">" + i + "</option>";
                }
            }

            konten += "</select>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Kelas</td>"
                    + "<td align='left'>"
                    + "<select name='kelas'>";

            for (int i = 0; i < 26; i++) {
                String namaKelas = new String(new char[]{(char) (i + 65)});
                if (kelas.equals(namaKelas)) {
                    konten += "<option selected value=" + namaKelas + ">" + namaKelas + "</option>";
                } else {
                    konten += "<option value=" + namaKelas + ">" + namaKelas + "</option>";
                }
            }

            konten += "</select>"
                    + "</td>"
                    + "</tr>"
                    + "<tr>"
                    + "<td align='right'>Password</td>"
                    + "<td align='left'><input type='password' value='" + password + "' name='password' maxlength='30' size='30'></td>"
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
