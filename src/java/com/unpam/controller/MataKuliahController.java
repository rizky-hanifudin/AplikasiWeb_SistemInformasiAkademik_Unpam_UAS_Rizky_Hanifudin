/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.MataKuliah;
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
public class MataKuliahController extends HttpServlet {

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
        MataKuliah mataKuliah = new MataKuliah();
        String userName = "";

        String tombol = request.getParameter("tombol");
        String kodeMataKuliah = request.getParameter("kodeMataKuliah");
        String namaMataKuliah = request.getParameter("namaMataKuliah");
        String jumlahSks = request.getParameter("jumlahSks");
        String mulaiParameter = request.getParameter("mulai");
        String jumlahParameter = request.getParameter("jumlah");
        String kodeMataKuliahDipilih = request.getParameter("kodeMataKuliahDipilih");

        if (tombol == null) {
            tombol = "";
        }
        if (kodeMataKuliah == null) {
            kodeMataKuliah = "";
        }
        if (namaMataKuliah == null) {
            namaMataKuliah = "";
        }
        if (jumlahSks == null) {
            jumlahSks = "2";
        }
        if (kodeMataKuliahDipilih == null) {
            kodeMataKuliahDipilih = "";
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
                if (!kodeMataKuliah.equals("")) {
                    mataKuliah.setKodeMataKuliah(kodeMataKuliah);
                    mataKuliah.setNamaMataKuliah(namaMataKuliah);
                    mataKuliah.setJumlahSks(Integer.parseInt(jumlahSks));

                    if (mataKuliah.simpan()) {
                        kodeMataKuliah = "";
                        namaMataKuliah = "";
                        jumlahSks = "2";
                        keterangan = "Sudah tersimpan";
                    } else {
                        keterangan = "Gagal menyimpan:\n" + mataKuliah.getPesan();
                    }
                } else {
                    keterangan = "Gagal menyimpan, kode mata kuliah tidak boleh kosong";
                }
            } else if (tombol.equals("Hapus")) {
                if (!kodeMataKuliah.equals("")) {
                    if (mataKuliah.hapus(kodeMataKuliah)) {
                        kodeMataKuliah = "";
                        namaMataKuliah = "";
                        jumlahSks = "2";
                        keterangan = "Data sudah dihapus";
                    } else {
                        keterangan = "Kode mata kuliah tersebut tidak ada, atau ada kesalahan:\n" + mataKuliah.getPesan();
                    }
                } else {
                    keterangan = "Kode mata kuliah masih kosong";
                }
            } else if (tombol.equals("Cari")) {
                if (!kodeMataKuliah.equals("")) {
                    if (mataKuliah.baca(kodeMataKuliah)) {
                        kodeMataKuliah = mataKuliah.getKodeMataKuliah();
                        namaMataKuliah = mataKuliah.getNamaMataKuliah();
                        jumlahSks = Integer.toString(mataKuliah.getJumlahSks());
                        keterangan = "<br>";
                    } else {
                        keterangan = "Kode mata kuliah tersebut tidak ada";
                    }
                } else {
                    keterangan = "Kode mata kuliah masih kosong";
                }
            } else if (tombol.equals("Pilih")) {
                kodeMataKuliah = kodeMataKuliahDipilih;
                namaMataKuliah = "";
                jumlahSks = "2";
                if (!kodeMataKuliahDipilih.equals("")) {
                    if (mataKuliah.baca(kodeMataKuliahDipilih)) {
                        kodeMataKuliah = mataKuliah.getKodeMataKuliah();
                        namaMataKuliah = mataKuliah.getNamaMataKuliah();
                        jumlahSks = Integer.toString(mataKuliah.getJumlahSks());
                        keterangan = "<br>";
                    } else {
                        keterangan = "Kode mata kuliah tersebut tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
                }
            }

            String kontenLihat = "";
            if (tombol.equals("Lihat") || tombol.equals("Sebelumnya") || tombol.equals("Berikutnya") || tombol.equals("Tampilkan")) {
                kontenLihat += "<tr>";
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

                Object[][] listMataKuliah = null;
                if (mataKuliah.bacaData(mulai, jumlah)) {
                    listMataKuliah = mataKuliah.getList();
                } else {
                    keterangan = mataKuliah.getPesan();
                }

                if (listMataKuliah != null) {
                    for (int i = 0; i < listMataKuliah.length; i++) {
                        kontenLihat += "<tr>";
                        kontenLihat += "<td>";
                        if (i == 0) {
                            kontenLihat += "<input type='radio' checked name='kodeMataKuliahDipilih' value='" + listMataKuliah[i][0].toString() + "'>";
                        } else {
                            kontenLihat += "<input type='radio' name='kodeMataKuliahDipilih' value='" + listMataKuliah[i][0].toString() + "'>";
                        }
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listMataKuliah[i][0].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listMataKuliah[i][1].toString();
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

            String konten = "<h2>Master Data Mata Kuliah</h2>";
            konten += "<form action='MataKuliahController' method='post'>";
            konten += "<table>";
            konten += "<tr>";
            konten += "<td align='right'>Kode Mata Kuliah</td>";
            konten += "<td align='left'><input type='text' value='" + kodeMataKuliah + "' name='kodeMataKuliah' maxlength='15' size='15'><input type='submit' name='tombol' value='Cari'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nama Mata Kuliah</td>";
            konten += "<td align='left'><input type='text' value='" + namaMataKuliah + "' name='namaMataKuliah' maxlength='30' size='30'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Jumlah SKS</td>";
            konten += "<td align='left'>";
            konten += "<select name='jumlahSks'>";

            for (int i = 2; i <= 6; i++) {
                if (i != 5) {
                    if (i == Integer.parseInt(jumlahSks)) {
                        konten += "<option selected value=" + i + ">" + i + "</option>";
                    } else {
                        konten += "<option value=" + i + ">" + i + "</option>";
                    }
                }
            }

            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='2' align='center'>";
            konten += "<table>";
            konten += "<tr>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Simpan' style='width: 100px'></td>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Hapus' style='width: 100px'></td>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Lihat' style='width: 100px'></td>";
            konten += "</tr>";
            konten += "</table>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='2' align='center'><br>";
            konten += "</td>";
            konten += "</tr>";
            konten += kontenLihat;
            konten += "</table>";
            konten += "</form>";

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
