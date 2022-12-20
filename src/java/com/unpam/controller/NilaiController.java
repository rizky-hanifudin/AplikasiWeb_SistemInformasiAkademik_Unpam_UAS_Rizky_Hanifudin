/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.unpam.controller;

import com.unpam.model.Mahasiswa;
import com.unpam.model.MataKuliah;
import com.unpam.model.Nilai;
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
public class NilaiController extends HttpServlet {

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
        MataKuliah mataKuliah = new MataKuliah();
        Nilai nilai = new Nilai();
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
        String tombolMataKuliah = request.getParameter("tombolMataKuliah");
        String kodeMataKuliah = request.getParameter("kodeMataKuliah");
        String namaMataKuliah = request.getParameter("namaMataKuliah");
        String jumlahSks = request.getParameter("jumlahSks");
        String kodeMataKuliahDipilih = request.getParameter("kodeMataKuliahDipilih");
        String tugas = request.getParameter("tugas");
        String uts = request.getParameter("uts");
        String uas = request.getParameter("uas");

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
        if (tombolMataKuliah == null) {
            tombolMataKuliah = "";
        }
        if (kodeMataKuliah == null) {
            kodeMataKuliah = "";
        }
        if (namaMataKuliah == null) {
            namaMataKuliah = "";
        }
        if (jumlahSks == null) {
            jumlahSks = "";
        }
        if (kodeMataKuliahDipilih == null) {
            kodeMataKuliahDipilih = "";
        }
        if (tugas == null) {
            tugas = "";
        }
        if (uts == null) {
            uts = "";
        }
        if (uas == null) {
            uas = "";
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
            if (tombolMahasiswa.equals("Cari")) {
                if (!nim.equals("")) {
                    if (mahasiswa.baca(nim)) {
                        nim = mahasiswa.getNim();
                        namaMahasiswa = mahasiswa.getNama();
                        semester = Integer.toString(mahasiswa.getSemester());
                        kelas = mahasiswa.getKelas();
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
                        keterangan = "<br>";
                    } else {
                        keterangan = "NIM " + nim + " tidak ada";
                    }
                } else {
                    keterangan = "Tidak ada yang dipilih";
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

            if (tombolMataKuliah.equals("Cari")) {
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
            } else if (tombolMataKuliah.equals("Pilih")) {
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

            if (tombolMataKuliah.equals("Lihat") || tombolMataKuliah.equals("Sebelumnya") || tombolMataKuliah.equals("Berikutnya") || tombolMataKuliah.equals("Tampilkan")) {
                kontenLihat += "<tr>";
                kontenLihat += "<td colspan='2' align='center'>";
                kontenLihat += "<table>";

                if (tombolMataKuliah.equals("Sebelumnya")) {
                    mulai -= jumlah;
                    if (mulai < 0) {
                        mulai = 0;
                    }
                }

                if (tombolMataKuliah.equals("Berikutnya")) {
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
                kontenLihat += "<td align='center'><input type='submit' name='tombolMataKuliah' value='Sebelumnya' style='width: 100px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolMataKuliah' value='Pilih' style='width: 60px'></td>";
                kontenLihat += "<td align='center'><input type='submit' name='tombolMataKuliah' value='Berikutnya' style='width: 100px'></td>";
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
                kontenLihat += "<td align='center'><input type='submit' name='tombolMataKuliah' value='Tampilkan' style='width: 90px'></td>";
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
                    if (!nim.equals("") && !kodeMataKuliah.equals("")) {
                        nilai.setNim(nim);
                        nilai.setListNilai(new Object[][]{{kodeMataKuliah, tugas, uts, uas}});
                        if (nilai.simpan()) {
                            nim = "";
                            namaMahasiswa = "";
                            semester = "";
                            kelas = "";
                            kodeMataKuliah = "";
                            namaMataKuliah = "";
                            jumlahSks = "";
                            tugas = "";
                            uts = "";
                            uas = "";
                            keterangan = "Sudah disimpan";
                        } else {
                            keterangan = "Gagal menyimpan:\n" + nilai.getPesan();
                        }
                    } else {
                        keterangan = "NIM dan kode mata kuliah tidak boleh kosong";
                    }
                } else if (tombol.equals("Hapus")) {
                    if (!nim.equals("") && !kodeMataKuliah.equals("")) {
                        if (nilai.hapus(nim, kodeMataKuliah)) {
                            nim = "";
                            namaMahasiswa = "";
                            semester = "";
                            kelas = "";
                            kodeMataKuliah = "";
                            namaMataKuliah = "";
                            jumlahSks = "";
                            tugas = "";
                            uts = "";
                            uas = "";
                            keterangan = "Sudah dihapus";
                        } else {
                            keterangan = "Gagal menghapus:\n" + nilai.getPesan();
                        }
                    } else {
                        keterangan = "NIM dan kode mata kuliah tidak boleh kosong";
                    }
                }
            }

            String konten = "<h2>Input Nilai Mahasiswa</h2>";
            konten += "<form action='NilaiController' method='post'>";
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
            konten += "<td align='right'>Kode Mata Kuliah</td>";
            konten += "<td align='left'><input type='text' value='" + kodeMataKuliah + "' name='kodeMataKuliah' maxlength='15' style='width: 120px'>";
            konten += "<input type='submit' name='tombolMataKuliah' value='Cari'><input type='submit' name='tombolMataKuliah' value='Lihat'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nama Mata Kuliah</td>";
            konten += "<td align='left'><input type='text' readonly value='" + namaMataKuliah + "' name='namaMataKuliah' style='width: 220px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Jumlah SKS</td>";
            konten += "<td align='left'><input type='text' readonly value='" + jumlahSks + "' name='jumlahSks' style='width: 20px'></td>";
            konten += "</tr>";

            if (!tombolMataKuliah.equals("")) {
                if (!keterangan.equals("<br>")) {
                    konten += "<tr>";
                    konten += "<td colspan='2'><b>" + keterangan.replaceAll("\n", "<br>").replaceAll(";", ",") + "</b></td>";
                    konten += "</tr>";
                }
                konten += kontenLihat;
            }

            konten += "<tr>";
            konten += "<td align='right'>Nilai Tugas</td>";
            konten += "<td align='left'><input type='text' value='" + tugas + "' name='tugas' style='width: 50px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nilai UTS</td>";
            konten += "<td align='left'><input type='text' value='" + uts + "' name='uts' style='width: 50px'></td>";
            konten += "</tr>";
            konten += "<tr>";
            konten += "<td align='right'>Nilai UAS</td>";
            konten += "<td align='left'><input type='text' value='" + uas + "' name='uas' style='width: 50px'></td>";
            konten += "</tr>";

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
