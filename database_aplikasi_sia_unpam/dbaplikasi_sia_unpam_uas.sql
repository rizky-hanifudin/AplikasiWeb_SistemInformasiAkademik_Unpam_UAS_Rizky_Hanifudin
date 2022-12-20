-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 20, 2022 at 11:47 AM
-- Server version: 10.4.24-MariaDB
-- PHP Version: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbaplikasi_sia_unpam_uas`
--

-- --------------------------------------------------------

--
-- Table structure for table `karyawan`
--

CREATE TABLE `karyawan` (
  `id` varchar(50) NOT NULL,
  `nim` varchar(50) DEFAULT NULL,
  `nama` varchar(50) DEFAULT NULL,
  `jenis_kelamin` varchar(50) DEFAULT NULL,
  `alamat` varchar(150) DEFAULT NULL,
  `kelurahan` varchar(50) DEFAULT NULL,
  `kecamatan` varchar(50) DEFAULT NULL,
  `kabupaten` varchar(50) DEFAULT NULL,
  `provinsi` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `karyawan`
--

INSERT INTO `karyawan` (`id`, `nim`, `nama`, `jenis_kelamin`, `alamat`, `kelurahan`, `kecamatan`, `kabupaten`, `provinsi`) VALUES
('201011400105', '201011400105', 'Rizky Hanifudin', 'Pria', 'Jl. Gn. Balong II No.58, RT.11/RW.4, Lb. Bulus, Kec. Cilandak, Kota Jakarta Selatan, Daerah Khusus Ibukota Jakarta 12440', 'Lebak Bulus', 'Cilandak', 'Kota Jakarta Selatan', 'Daerah Khusus Ibukota Jakarta');

-- --------------------------------------------------------

--
-- Table structure for table `tbbiayakuliah`
--

CREATE TABLE `tbbiayakuliah` (
  `prodi` varchar(100) NOT NULL,
  `biaya_semester` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbbiayakuliah`
--

INSERT INTO `tbbiayakuliah` (`prodi`, `biaya_semester`) VALUES
('Teknik Informatika', 2200000);

-- --------------------------------------------------------

--
-- Table structure for table `tbmahasiswa`
--

CREATE TABLE `tbmahasiswa` (
  `nim` varchar(12) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `kelas` varchar(9) NOT NULL,
  `semester` int(11) NOT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbmahasiswa`
--

INSERT INTO `tbmahasiswa` (`nim`, `nama`, `kelas`, `semester`, `password`) VALUES
('201011400105', 'Rizky Hanifudin', '05TPLE005', 5, '827ccb0eea8a706c4c34a16891f84e7b');

-- --------------------------------------------------------

--
-- Table structure for table `tbmatakuliah`
--

CREATE TABLE `tbmatakuliah` (
  `kode` varchar(12) NOT NULL,
  `nama` varchar(50) NOT NULL,
  `sks` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dumping data for table `tbmatakuliah`
--

INSERT INTO `tbmatakuliah` (`kode`, `nama`, `sks`) VALUES
('1', 'Pemrograman 1', 3),
('2', 'Pemrograman 2', 3),
('3', 'Basis Data 1', 3),
('4', 'Basis Data 2', 2);

-- --------------------------------------------------------

--
-- Table structure for table `tbnilai`
--

CREATE TABLE `tbnilai` (
  `nim` varchar(12) NOT NULL,
  `kodematakuliah` varchar(12) NOT NULL,
  `tugas` varchar(3) NOT NULL,
  `uts` varchar(3) NOT NULL,
  `uas` varchar(3) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Table structure for table `tbpembayarankuliah`
--

CREATE TABLE `tbpembayarankuliah` (
  `no_tagihan` varchar(14) NOT NULL,
  `nim` varchar(12) NOT NULL,
  `prodi` varchar(100) NOT NULL,
  `pembayaran` int(11) NOT NULL,
  `jumlah_bayar` int(11) NOT NULL,
  `status` varchar(15) NOT NULL,
  `tanggal_bayar` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `karyawan`
--
ALTER TABLE `karyawan`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `tbbiayakuliah`
--
ALTER TABLE `tbbiayakuliah`
  ADD PRIMARY KEY (`prodi`);

--
-- Indexes for table `tbmahasiswa`
--
ALTER TABLE `tbmahasiswa`
  ADD PRIMARY KEY (`nim`);

--
-- Indexes for table `tbmatakuliah`
--
ALTER TABLE `tbmatakuliah`
  ADD PRIMARY KEY (`kode`);

--
-- Indexes for table `tbnilai`
--
ALTER TABLE `tbnilai`
  ADD PRIMARY KEY (`nim`,`kodematakuliah`);

--
-- Indexes for table `tbpembayarankuliah`
--
ALTER TABLE `tbpembayarankuliah`
  ADD PRIMARY KEY (`no_tagihan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
