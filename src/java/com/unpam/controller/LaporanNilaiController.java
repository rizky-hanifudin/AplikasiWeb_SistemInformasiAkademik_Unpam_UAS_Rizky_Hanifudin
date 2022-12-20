/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Nilai;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.OutputStream;

/**
 *
 * @author indbr
 */
public class LaporanNilaiController extends HttpServlet {

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
        String[][] formatTypeData = {{"PDF (Portable Document Format)", "pdf", "application/pdf"},
        {"XLSX (Microsoft Excel)", "xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"},
        {"XLS (Microsoft Excel 97-2003)", "xls", "application/vnd.ms-excel"},
        {"DOCX (Microsoft Word)", "docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"},
        {"ODT (OpenDocument Text)", "odt", "application/vnd.oasis.opendocument.text"},
        {"RTF (Rich Text Format)", "rtf", "text/rtf"}};

        HttpSession session = request.getSession(true);
        String userName = "";

        String tombol = request.getParameter("tombol");
        String opsi = request.getParameter("opsi");
        String nim = request.getParameter("nim");
        String semester = request.getParameter("semester");
        String kelas = request.getParameter("kelas");
        String formatType = request.getParameter("formatType");

        if (tombol == null) {
            tombol = "";
        }
        if (nim == null) {
            nim = "";
        }
        if (opsi == null) {
            opsi = "";
        }
        if (semester == null) {
            semester = "0";
        }
        if (kelas == null) {
            kelas = "";
        }
        if (formatType == null) {
            kelas = "";
        }

        String keterangan = "<br>";
        int noType = 0;

        for (int i = 0; i < formatTypeData.length; i++) {
            if (formatTypeData[i][0].equals(formatType)) {
                noType = i;
            }
        }

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception ex) {
        }

        if (!((userName == null) || userName.equals(""))) {
            boolean opsiSelected = false;

            if (tombol.equals("Cetak")) {
                Nilai nilai = new Nilai();

                int semesterDipilih = 0;
                try {
                    semesterDipilih = Integer.parseInt(semester);
                } catch (NumberFormatException ex) {
                }

                if (kelas.equals("-")) {
                    kelas = "";
                }
                String file = getServletConfig().getServletContext().getRealPath(".");
                System.out.println("file: "+file);
                if (nilai.cetakLaporan(opsi, nim, semesterDipilih, kelas, formatTypeData[noType][1], getServletConfig().getServletContext().getRealPath("reports/NilaiReport.jrxml"))) {
                    byte[] pdfasbytes = nilai.getPdfasbytes();
                    try ( OutputStream outStream = response.getOutputStream()) {
                        response.setHeader("Content-Disposition", "inline; filename=NilaiReport." + formatTypeData[noType][1]);
                        response.setContentType(formatTypeData[noType][2]);

                        response.setContentLength(pdfasbytes.length);
                        outStream.write(pdfasbytes, 0, pdfasbytes.length);

                        outStream.flush();
                        outStream.close();
                    }
                } else {
                    keterangan += nilai.getPesan();
                }
            }

            String konten = "<h2>Mencetak Nilai</h2>";
            konten += "<form action='LaporanNilaiController' method='post'>";
            konten += "<table>";
            konten += "<tr>";
            if (opsi.equalsIgnoreCase("NIM")) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='NIM'></td>";
                opsiSelected = true;
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='NIM'></td>";
            }
            konten += "<td align='left'>NIM</td>";
            konten += "<td align='left'><input type='text' value='" + nim + "' name='nim' maxlength='15' size='15'></td>";
            konten += "</tr>";

            konten += "<tr>";
            if (opsi.equals("semesterKelas")) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='semesterKelas'></td>";
                opsiSelected = true;
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='semesterKelas'></td>";
            }
            konten += "<td align='left'>Semester</td>";
            konten += "<td align='left'>";
            konten += "<select name='semester'>";
            konten += "<option selected value=0>Semua</option>";
            for (int i = 1; i <= 14; i++) {
                if (i == Integer.parseInt(semester)) {
                    konten += "<option selected value=" + i + ">" + i + "</option>";
                } else {
                    konten += "<option value=" + i + ">" + i + "</option>";
                }
            }
            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td><br></td>";
            konten += "<td align='left'>Kelas</td>";
            konten += "<td align='left'>";
            konten += "<select name='kelas'>";
            konten += "<option selected value=->Semua</option>";
            for (int i = 0; i <= 25; i++) {
                String namaKelas = new String(new char[]{(char) (i + 65)});
                if (kelas.equals(namaKelas)) {
                    konten += "<option selected value=" + namaKelas + ">" + namaKelas + "</option>";
                } else {
                    konten += "<option value=" + namaKelas + ">" + namaKelas + "</option>";
                }
            }
            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            if (!opsiSelected) {
                konten += "<td align='right'><input type='radio' checked name='opsi' value='Semua'></td>";
            } else {
                konten += "<td align='right'><input type='radio' name='opsi' value='Semua'></td>";
            }
            konten += "<td align='left'>Semua</td>";
            konten += "<td><br></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='3'><br></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td>Format Laporan</td>";
            konten += "<td colspan=2>";
            konten += "<select name='formatType'>";
            for (String[] formatLaporan : formatTypeData) {
                if (formatLaporan[0].equals(formatType)) {
                    konten += "<option selected value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
                } else {
                    konten += "<option value='" + formatLaporan[0] + "'>" + formatLaporan[0] + "</option>";
                }
            }
            konten += "</select>";
            konten += "</td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='3'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
            konten += "</tr>";

            konten += "<tr>";
            konten += "<td colspan='3' align='center'><input type='submit' name='tombol' value='Cetak' style='width: 100px'></td>";
            konten += "</tr>";

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
