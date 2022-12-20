<%-- 
    Document   : index
    Created on : Nov 14, 2022, 8:52:28 PM
    Author     : rizky-hanifudin.github.io
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href='style.css' rel='stylesheet' type='text/css' />
    <title>Sistem Informasi Akademik Mahasiswa Univeristas Pamulang</title>
</head>
<body bgcolor="#808080">
    <%
        String menu="<br><b>Master Data</b><br>"
                + "<a href=.>Mahasiswa</a><br>"
                + "<a href=.>Mata Kuliah</a><br>"
                + "<a href=.>Biaya Kuliah Prodi</a><br><br>"
                + "<a href=.>Karyawan</a><br><br>"
                + "<b>Transaksi</b><br>"
                + "<a href=.>Nilai Kuliah</a><br>"
                + "<a href=.>Pembayaran Kuliah</a><br><br>"
                + "<b>Laporan</b><br>"
                + "<a href=.>Nilai Kuliah</a><br><br>"
                + "<a href=.>Pembayaran Kuliah</a><br><br>"
                + "<a href=.>Karyawan</a><br><br>"
                + "<a href=LoginController>Login</a><br><br>";
        
        String topMenu="<nav><ul>"
                + "<li><a href=.>Home</a></li>"
                + "<li><a href=#>Master Data</a>"
                + "<ul>"
                + "<li><a href=.>Mahasiswa</a></li>"
                + "<li><a href=.>Mata Kuliah</a></li>"
                + "<li><a href=.>Biaya Kuliah Prodi</a></li>"
                + "<li><a href=.>Karyawan</a></li>"
                + "</ul>"
                + "</li>"
                + "<li><a href=#>Transaksi</a>"
                + "<ul>"
                + "<li><a href=.>Nilai Matkul</a></li>"
                + "<li><a href=.>Pembayaran Kuliah</a></li>"
                + "</ul>"
                + "</li>"
                + "<li><a href=#>Laporan</a>"
                + "<ul>"
                + "<li><a href=.>Nilai Matkul</a></li>"
                + "<li><a href=.>Pembayaran Kuliah</a></li>"
                + "<li><a href=.>Karyawan</a></li>"
                + "</ul>"
                + "</li>"
                + "<li><a href=LoginController>Login</a></li>"
                + "</ul>"
                + "</nav>";
        String konten="<br><h1>Selamat Datang</h1>";
        String userName="";
        
        if (!session.isNew()){
            try {
                userName = session.getAttribute("userName").toString();
            } catch (Exception ex){}
            
            if (!((userName == null) || userName.equals(""))){
                konten += "<h2>"+userName+"</h2>";
                
                try {
                    menu = session.getAttribute("menu").toString();
                } catch (Exception ex){}
            }
        }
        
        Cookie[] cookies = request.getCookies();
        if (cookies != null){
            String idKontent = "konten";
            boolean awal = true;
            for (int i=0; i<cookies.length; i++){
                if (cookies[i].getName().length() >= idKontent.length()){
                    if (cookies[i].getName().substring(0, idKontent.length()).equals(idKontent)){
                        if (awal){
                            konten = cookies[i].getValue();
                            awal = false;
                        } else {
                            konten += cookies[i].getValue();
                        }
                        
                        cookies[i].setValue("");
                        cookies[i].setMaxAge(1);
                        response.addCookie(cookies[i]);
                    }
                }
            }
        }
        
        if ((konten == null) || konten.equals("")){
            konten="<br><h1>Selamat Datang</h1>";
        }
    %>
    <center>
        <table width="80%" bgcolor="#eeeeee">   
            <tr>
                <td colspan="2" align="center">
                    <br>
                    <p>
                        <a href="https://rizky-hanifudin.github.io/">
                        <img src="https://unpam.ac.id/wp-content/uploads/2021/02/logo-unpam.png" alt="Logo UNIVERSITAS PAMULANG" width="50" height="50" align="right">
                        </a>
                    </p>
                    <h2 Style="margin-bottom:0px; margin-top:0px;">
                        Informasi Nilai Mahasiswa
                    </h2>
                    <h1 Style="margin-bottom:0px; margin-top:0px;">
                        UNIVERSITAS PAMULANG
                    </h1>
                    <h4 Style="margin-bottom:0px; margin-top:0px;">
                        Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten
                    </h4>
                    <br>
                </td>
            </tr>
            <tr height="400">
                <td width="200" align="center" valign="top" bgcolor="#eeffee">
                    <br>
                    <div id='menu'>
                        <%=menu%>
                    </div>
                </td>
                
               <td align="center" valign="top" bgcolor="#ffffff">
                   <%--<%=topMenu%>--%>
                   <br>
                   <%=konten%>
                </td> 
            </tr>
            <tr>
                <td colspan="2" align="center" bgcolor="#eeeeff">
                    <small>
                        Copyright &copy; 2022 Universitas Pamulang<br>
                        Jl. Surya Kencana No. 1 Pamulang, Tangerang Selatan, Banten<br>                        
                    </small>
                </td>
            </tr>
        </table>
    </center>
    </body>
</html>
