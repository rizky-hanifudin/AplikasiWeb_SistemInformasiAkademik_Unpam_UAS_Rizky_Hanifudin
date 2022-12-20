/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.model.BiayaKuliah;
import com.unpam.model.PembayaranKuliah;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.text.SimpleDateFormat;

/**
 *
 * @author indbr
 */
public class PembayaranController extends HttpServlet {

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
        BiayaKuliah biayaKuliah = new BiayaKuliah();
        PembayaranKuliah objPembayaran = new PembayaranKuliah();
        String userName = "";

        String tombol = request.getParameter("tombol");
        String tombolMahasiswa = request.getParameter("tombolMahasiswa");
        String nim = request.getParameter("nim");
        String namaMahasiswa = request.getParameter("namaMahasiswa");
        String semester = request.getParameter("semester");
        String kelas = request.getParameter("kelas");
        String mulaiParameter = request.getParameter("mulai");
        String jumlahParameter = request.getParameter("jumlah");
        String nimDipilih = request.getParameter("nimDipilih");
        String tombolBiayaKuliah = request.getParameter("tombolBiayaKuliah");
        String prodi = request.getParameter("prodi");
        String pembayaran = request.getParameter("pembayaran");
        String prodiDipilih = request.getParameter("prodiDipilih");
        String jumlahBayar = request.getParameter("jumlahBayar");
        String status = request.getParameter("status");
        String noTagihan = request.getParameter("noTagihan");
        String tanggalBayar = "";

        if (tombol == null) {
            tombol = "";
        }
        if (tombolMahasiswa == null) {
            tombolMahasiswa = "";
        }
        if (nim == null) {
            nim = "";
        }
        if (namaMahasiswa == null) {
            namaMahasiswa = "";
        }
        if (semester == null) {
            semester = "";
        }
        if (kelas == null) {
            kelas = "";
        }
        if (nimDipilih == null) {
            nimDipilih = "";
        }
        if (tombolBiayaKuliah == null) {
            tombolBiayaKuliah = "";
        }
        if (prodi == null) {
            prodi = "";
        }
        if (pembayaran == null) {
            pembayaran = "";
        }
        if (jumlahBayar == null) {
            jumlahBayar = "";
        }
        if (prodiDipilih == null) {
            prodiDipilih = "";
        }
        if (status == null) {
            status = "";
        }
        if (noTagihan == null) {
            noTagihan = "";
        }

        int mulai = 0, jumlah = 10, intJumlahBayar = 0, intPembayaran = 0;

        try {
            mulai = Integer.parseInt(mulaiParameter);
        } catch (NumberFormatException ex) {
        }

        try {
            jumlah = Integer.parseInt(jumlahParameter);
        } catch (NumberFormatException ex) {
        }

        try {
            intJumlahBayar = Integer.parseInt(jumlahBayar);
        } catch (NumberFormatException ex) {
        }

        try {
            intPembayaran = Integer.parseInt(pembayaran);
        } catch (NumberFormatException ex) {
        }

        String keterangan = "<br>";

        try {
            userName = session.getAttribute("userName").toString();
        } catch (Exception ex) {
        }

        if (!((userName == null) || userName.equals(""))) {
            if (tombolMahasiswa.equals("Cari")) {
                if (!nim.equals("")) {
                    if (mahasiswa.baca(nim)) {
                        nim = mahasiswa.getNim();
                        namaMahasiswa = mahasiswa.getNama();
                        semester = Integer.toString(mahasiswa.getSemester());
                        kelas = mahasiswa.getKelas();
                        noTagihan = !semester.isBlank() && !nim.isBlank() ? nim+semester: "";
                        keterangan = "<br>";
                    } else {
                        namaMahasiswa = "";
                        semester = "1";
                        kelas = "A";
                        keterangan = "NIM " + nim + " tidak ada";
                    }
                } else {
                    keterangan = "NIM harus diisi";
                }
            } else if (tombolMahasiswa.equals("Pilih")) {
                nim = nimDipilih;
                namaMahasiswa = "";
                semester = "1";
                kelas = "A";
                if (!nimDipilih.equals("")) {
                    if (mahasiswa.baca(nimDipilih)) {
                        nim = mahasiswa.getNim();
                        namaMahasiswa = mahasiswa.getNama();
                        semester = Integer.toString(mahasiswa.getSemester());
                        kelas = mahasiswa.getKelas();
                        noTagihan = !semester.isBlank() && !nim.isBlank() ? nim+semester: "";
                        keterangan = "<br>";
                    } else {
                        keterangan = "NIM " + nim + " tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
                }
            }
            if (!noTagihan.isBlank()){
                if (objPembayaran.baca(noTagihan)){
                    prodi = objPembayaran.getProdi();
                    pembayaran = Integer.toString(objPembayaran.getPembayaran());
                    jumlahBayar = Integer.toString(objPembayaran.getJumlahBayar());
                    status = objPembayaran.getStatus();
                    tanggalBayar = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(objPembayaran.getTanggalBayar());
                }
            }
            String kontenLihat = "";
            if (tombolMahasiswa.equals("Lihat") || tombolMahasiswa.equals("Sebelumnya") || tombolMahasiswa.equals("Berikutnya") || tombolMahasiswa.equals("Tampilkan")) {
                kontenLihat = "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";

                if (tombolMahasiswa.equals("Sebelumnya")) {
                    mulai -= jumlah;
                    if (mulai < 0) {
                        mulai = 0;
                    }
                }

                if (tombolMahasiswa.equals("Berikutnya")) {
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
                kontenLihat += "<td align='center'><input type='submit' name='tombolMahasiswa' value='Sebelumnya' style='width: 100px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolMahasiswa' value='Pilih' style='width: 60px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolMahasiswa' value='Berikutnya' style='width: 100px'></td>";
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
                kontenLihat += "<td align='center'><input type='submit' name='tombolMahasiswa' value='Tampilkan' style='width: 90px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'><br></td>";
                kontenLihat += "</tr>";
            }

            if (tombolBiayaKuliah.equals("Cari")) {
                if (!prodi.equals("")) {
                    if (biayaKuliah.baca(prodi)) {
                        prodi = biayaKuliah.getProdi();
                        pembayaran = Integer.toString(biayaKuliah.getBiayaSemester());
                        keterangan = "<br>";
                    } else {
                        keterangan = "Prodi tersebut tidak ada";
                    }
                } else {
                    keterangan = "Prodi masih kosong";
                }
            } else if (tombolBiayaKuliah.equals("Pilih")) {
                prodi = prodiDipilih;
                pembayaran = "";
                if (!prodiDipilih.equals("")) {
                    if (biayaKuliah.baca(prodiDipilih)) {
                        prodi = biayaKuliah.getProdi();
                        pembayaran = Integer.toString(biayaKuliah.getBiayaSemester());
                        keterangan = "<br>";
                    } else {
                        keterangan = "Prodi tersebut tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
                }
            }

            if (tombolBiayaKuliah.equals("Lihat") || tombolBiayaKuliah.equals("Sebelumnya") || tombolBiayaKuliah.equals("Berikutnya") || tombolBiayaKuliah.equals("Tampilkan")) {
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";

                if (tombolBiayaKuliah.equals("Sebelumnya")) {
                    mulai -= jumlah;
                    if (mulai < 0) {
                        mulai = 0;
                    }
                }

                if (tombolBiayaKuliah.equals("Berikutnya")) {
                    mulai += jumlah;
                }

                Object[][] listBiayaKuliah = null;
                if (biayaKuliah.bacaData(mulai, jumlah)) {
                    listBiayaKuliah = biayaKuliah.getList();
                } else {
                    keterangan = biayaKuliah.getPesan();
                }

                if (listBiayaKuliah != null) {
                    for (int i = 0; i < listBiayaKuliah.length; i++) {
                        kontenLihat += "<tr>";
                        kontenLihat += "<td>";
                        if (i == 0) {
                            kontenLihat += "<input type='radio' checked name='prodiDipilih' value='" + listBiayaKuliah[i][0].toString() + "'>";
                        } else {
                            kontenLihat += "<input type='radio' name='prodiDipilih' value='" + listBiayaKuliah[i][0].toString() + "'>";
                        }
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listBiayaKuliah[i][0].toString();
                        kontenLihat += "</td>";
                        kontenLihat += "<td>";
                        kontenLihat += listBiayaKuliah[i][1].toString();
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
                kontenLihat += "<td align='center'><input type='submit' name='tombolBiayaKuliah' value='Sebelumnya' style='width: 100px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolBiayaKuliah' value='Pilih' style='width: 60px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolBiayaKuliah' value='Berikutnya' style='width: 100px'></td>";
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
                kontenLihat += "<td align='center'><input type='submit' name='tombolBiayaKuliah' value='Tampilkan' style='width: 90px'></td>";
                kontenLihat += "</tr>";
                kontenLihat += "</table>";
                kontenLihat += "</td>";
                kontenLihat += "</tr>";
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'><br></td>";
                kontenLihat += "</tr>";
            }

            if (!tombol.equals("")) {
                if (tombol.equals("Simpan")) {
                    if (!nim.equals("") && !prodi.equals("")) {
                        objPembayaran = new PembayaranKuliah(noTagihan, nim, prodi, status, intPembayaran, intJumlahBayar);
                        if (objPembayaran.simpan()) {
                            nim = "";
                            namaMahasiswa = "";
                            semester = "";
                            kelas = "";
                            prodi = "";
                            pembayaran = "";
                            jumlahBayar = "";
                            status = "";
                            noTagihan = "";
                            keterangan = "Sudah disimpan";
                        } else {
                            keterangan = "Gagal menyimpan:\n" + objPembayaran.getPesan();
                        }
                    } else {
                        keterangan = "NIM dan prodi tidak boleh kosong";
                    }
                } else if (tombol.equals("Hapus")) {
                    if (!noTagihan.equals("") && !prodi.equals("")) {
                        if (objPembayaran.hapus(noTagihan)) {
                            nim = "";
                            namaMahasiswa = "";
                            semester = "";
                            kelas = "";
                            prodi = "";
                            pembayaran = "";
                            jumlahBayar = "";
                            status = "";
                            noTagihan = "";
                            tanggalBayar = "";
                            keterangan = "Sudah dihapus";
                        } else {
                            keterangan = "Gagal menghapus:\n" + objPembayaran.getPesan();
                        }
                    } else {
                        keterangan = "NIM dan prodi tidak boleh kosong";
                    }
                }
            }

            String konten = "<h2>Input Pembayaran Kuliah Mahasiswa</h2>";
            konten += "<form action='PembayaranController' method='post'>";
            konten += "<table>";
            konten += "<tr>";
            konten += "<td align='right'>NIM</td>";
            konten += "<td align='left'><input type='text' value='" + nim + "' name='nim' maxlength='15' style='width: 120px'>";
            konten += "<input type='submit' name='tombolMahasiswa' value='Cari'><input type='submit' name='tombolMahasiswa' value='Lihat'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nama</td>";
            konten += "<td align='left'><input type='text' readonly value='" + namaMahasiswa + "' name='namaMahasiswa' style='width: 220px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Semester</td>";
            konten += "<td align='left'><input type='text' readonly value='" + semester + "' name='semester' style='width: 20px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Kelas</td>";
            konten += "<td align='left'><input type='text' readonly value='" + kelas + "' name='kelas' style='width: 20px'></td>";
            konten += "</tr>";

            if (!tombolMahasiswa.equals("")) {
                if (!keterangan.equals("<br>")) {
                    konten += "<tr>";
                    konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                    konten += "</tr>";
                }
                konten += kontenLihat;
            }

            konten += "<tr>";
            konten += "<td align='right'>Prodi</td>";
            konten += "<td align='left'><input type='text' value='" + prodi + "' name='prodi' maxlength='100' size='40'>";
            konten += "<input type='submit' name='tombolBiayaKuliah' value='Cari'><input type='submit' name='tombolBiayaKuliah' value='Lihat'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Pembayaran</td>";
            konten += "<td align='left'><input type='text' readonly value='" + pembayaran + "' name='pembayaran' style='width: 220px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>No Tagihan</td>";
            konten += "<td align='left'><input type='text' readonly value='" + noTagihan + "' name='noTagihan' size='15'></td>";
            konten += "</tr>";

            if (!tombolBiayaKuliah.equals("")) {
                if (!keterangan.equals("<br>")) {
                    konten += "<tr>";
                    konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                    konten += "</tr>";
                }
                konten += kontenLihat;
            }

            konten += "<tr>";
            konten += "<td align='right'>Jumlah Bayar</td>";
            konten += "<td align='left'><input type='number' value='" + jumlahBayar + "' name='jumlahBayar' style='width: 220px'></td>";
            konten += "</tr>";
            if (!status.isBlank()){
                konten += "<tr>";
                konten += "<td align='right'>Status</td>";
                konten += "<td align='left'><input type='text' readonly value='" + status + "' name='status' style='width: 100px'></td>";
                konten += "</tr>";
            }
            if (!tanggalBayar.isBlank()){
                konten += "<tr>";
                konten += "<td align='right'>Tanggal Bayar</td>";
                konten += "<td align='left'><input type='datetime' readonly value='" + tanggalBayar + "'></td>";
                konten += "</tr>";
            }

            konten += "<tr>";
            konten += "<td colspan='2' align='center'>";
            konten += "<table>";
            konten += "<tr>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Simpan' style='width: 100px'></td>";
            konten += "<td align='center'><input type='submit' name='tombol' value='Hapus' style='width: 100px'></td>";
            konten += "</tr>";
            konten += "</table>";
            konten += "</td>";
            konten += "</tr>";

            if (!tombol.equals("") && !keterangan.equals("<br>")) {
                konten += "<tr>";
                konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                konten += "</tr>";
            }

            konten += "</table>";
            konten += "</form>";
            konten += "<br>";

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
