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
public class LoginController extends HttpServlet {

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
        Cookie cookie;

        String userId = request.getParameter("userId");
        String password = request.getParameter("password");

        String konten = "<br><form action=LoginController method=post>"
                + "<table>"
                + "<tr>"
                + "<td>User ID</td><td><input type=text name=userId></td>"
                + "</tr>"
                + "<tr>"
                + "<td>Password</td><td><input type=password name=password></td>"
                + "</tr>"
                + "<tr>"
                + "<td colspan=2 align=center><input type=submit value=Login><td>"
                + "</tr>"
                + "</table>"
                + "</form>";
        String pesan = "";
        boolean valid = false;

        if (userId == null) {
        } else if (userId.equals("")) {
            pesan = "<br><br><font style='color:red'>User ID harus diisi</font>";
        } else {
            Mahasiswa mahasiswa = new Mahasiswa();
            Enkripsi enkripsi = new Enkripsi();

            pesan = "<br><br><font style='color:red'>User ID atau password salah</font>";

            if (mahasiswa.baca(userId)) {
                String passwordEncrypted = "";

                try {
                    passwordEncrypted = enkripsi.hashMD5(password);
                } catch (Exception ex) {
                }

                if (passwordEncrypted.equals(mahasiswa.getPassword())) {
                    pesan = "";
                    valid = true;
                    session.setAttribute("userName", mahasiswa.getNama());
                    String menu = "<br><b>Master Data</b><br>"
                            + "<a href=MahasiswaController>Mahasiswa</a><br>"
                            + "<a href=MataKuliahController>Mata Kuliah</a><br>"
                            + "<a href=BiayaKuliahController>Biaya Kuliah Prodi</a><br><br>"
                            + "<a href=KaryawanController>Karyawan</a><br><br>"
                            + "<b>Transaksi</b><br>"
                            + "<a href=NilaiController>Nilai Matkul</a><br>"
                            + "<a href=PembayaranController>Pembayaran Kuliah</a><br><br>"
                            + "<b>Laporan</b><br>"
                            + "<a href=LaporanNilaiController>Nilai Matkul</a><br>"
                            + "<a href=LaporanPembayaranController>Pembayaran Kuliah</a><br><br>"
                            + "<a href=LaporanKaryawanController>Karyawan</a><br><br>"
                            + "<a href=LogoutController>Logout</a><br><br>";
                    session.setAttribute("menu", menu);
                    session.setMaxInactiveInterval(15 * 60); // 15 x 60 detik = 15 menit
                }
            } else {
                if (!mahasiswa.getPesan().substring(0, 3).equals("NIM")) {
                    pesan = "<br><br><font style='color:red'>" + mahasiswa.getPesan().replace("\n", "<br>") + "</font>";
                }
            }
        }

        if (!valid) {
            cookie = new Cookie("konten", konten + pesan);
            cookie.setMaxAge(2);
            response.addCookie(cookie);
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
