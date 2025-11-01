

CREATE DATABASE QLRapChieuPhim;
GO
USE QLRapChieuPhim;
GO

-- ==========================================
-- BẢNG RẠP
-- ==========================================
CREATE TABLE Rap (
    maRap NVARCHAR(10) PRIMARY KEY,
    tenRap NVARCHAR(100) NOT NULL,
    soLuongGhe INT CHECK (soLuongGhe >= 0)
);
GO

-- ==========================================
-- BẢNG GHẾ
-- ==========================================
CREATE TABLE Ghe (
    maGhe NVARCHAR(20) PRIMARY KEY,
    maRap NVARCHAR(10) NOT NULL,
    tinhTrang BIT DEFAULT 0, -- 0: trống, 1: đã đặt
    CONSTRAINT FK_GHE_RAP FOREIGN KEY (maRap) REFERENCES Rap(maRap)
);
GO

-- ==========================================
-- BẢNG NHÂN VIÊN
-- ==========================================
CREATE TABLE NhanVien (
    maNV NVARCHAR(10) PRIMARY KEY,
    tenNV NVARCHAR(100) NOT NULL,
    diaChi NVARCHAR(200),
    soDienThoai NVARCHAR(15),
    ngaySinh DATE,
    email NVARCHAR(100),
    gioiTinh NVARCHAR(10)
);
GO

-- ==========================================
-- BẢNG KHÁCH HÀNG
-- ==========================================
CREATE TABLE KhachHang (
    maKH NVARCHAR(10) PRIMARY KEY,
    hoTen NVARCHAR(100) NOT NULL,
    gioiTinh NVARCHAR(10),
    soDT NVARCHAR(15),
    diaChi NVARCHAR(200)
);
GO

-- ==========================================
-- BẢNG PHIM
-- ==========================================
CREATE TABLE Phim (
    maPhim NVARCHAR(10) PRIMARY KEY,
    tenPhim NVARCHAR(200) NOT NULL,
    nhaSanXuat NVARCHAR(100),
    theLoai NVARCHAR(100),
    thoiLuong INT CHECK (thoiLuong > 0),
    quocGia NVARCHAR(50)
);
GO

-- ==========================================
-- BẢNG SUẤT CHIẾU
-- ==========================================
CREATE TABLE SuatChieu (
    maSuatChieu NVARCHAR(10) PRIMARY KEY,
    maPhim NVARCHAR(10) NOT NULL,
    maRap NVARCHAR(10) NOT NULL,
    ngayChieu DATE NOT NULL,
    gioChieu TIME NOT NULL,
    giaVe FLOAT CHECK (giaVe > 0),

    CONSTRAINT FK_SUATCHIEU_PHIM FOREIGN KEY (maPhim) REFERENCES Phim(maPhim),
    CONSTRAINT FK_SUATCHIEU_RAP FOREIGN KEY (maRap) REFERENCES Rap(maRap)
);
GO

-- ==========================================
-- BẢNG VÉ
-- ==========================================
CREATE TABLE Ve (
    maVe NVARCHAR(20) PRIMARY KEY,
    maGhe NVARCHAR(20) NOT NULL,
    ngayBan DATE NOT NULL DEFAULT GETDATE(),
    maPhim NVARCHAR(10) NOT NULL,
    maRap NVARCHAR(10) NOT NULL,
    maSuatChieu NVARCHAR(10) NOT NULL,
    daThanhToan BIT DEFAULT 0,

    CONSTRAINT FK_VE_GHE FOREIGN KEY (maGhe) REFERENCES Ghe(maGhe),
    CONSTRAINT FK_VE_PHIM FOREIGN KEY (maPhim) REFERENCES Phim(maPhim),
    CONSTRAINT FK_VE_RAP FOREIGN KEY (maRap) REFERENCES Rap(maRap),
    CONSTRAINT FK_VE_SUATCHIEU FOREIGN KEY (maSuatChieu) REFERENCES SuatChieu(maSuatChieu)
);
GO

-- ==========================================
-- BẢNG HÓA ĐƠN
-- ==========================================
CREATE TABLE HoaDon (
    maHoaDon NVARCHAR(20) PRIMARY KEY,
    ngayLap DATE DEFAULT GETDATE(),
    maNV NVARCHAR(10) NOT NULL,
    maKH NVARCHAR(10) NOT NULL,
    tongTien FLOAT CHECK (tongTien >= 0),

    CONSTRAINT FK_HOADON_NV FOREIGN KEY (maNV) REFERENCES NhanVien(maNV),
    CONSTRAINT FK_HOADON_KH FOREIGN KEY (maKH) REFERENCES KhachHang(maKH)
);
GO

-- ==========================================
-- BẢNG CHI TIẾT HÓA ĐƠN
-- ==========================================
CREATE TABLE ChiTietHoaDon (
    maHoaDon NVARCHAR(20) NOT NULL,
    maVe NVARCHAR(20) NOT NULL,
    soLuongVe INT CHECK (soLuongVe > 0),
    giaVe FLOAT CHECK (giaVe > 0),

    CONSTRAINT PK_CTHD PRIMARY KEY (maHoaDon, maVe),
    CONSTRAINT FK_CTHD_HD FOREIGN KEY (maHoaDon) REFERENCES HoaDon(maHoaDon),
    CONSTRAINT FK_CTHD_VE FOREIGN KEY (maVe) REFERENCES Ve(maVe)
);
GO

-- ==========================================
-- BẢNG TÀI KHOẢN
-- ==========================================
CREATE TABLE TaiKhoan (
    maNV NVARCHAR(10) PRIMARY KEY,
    taiKhoan NVARCHAR(50) UNIQUE NOT NULL,
    matKhau NVARCHAR(100) NOT NULL,

    CONSTRAINT FK_TAIKHOAN_NV FOREIGN KEY (maNV) REFERENCES NhanVien(maNV)
);
GO
-- ==========================================
-- CHÈN DỮ LIỆU MẪU VÀO BẢNG PHIM
-- ==========================================
INSERT INTO Phim (maPhim, tenPhim, nhaSanXuat, theLoai, thoiLuong, quocGia) VALUES
('P001', 'Avengers: Endgame', 'Marvel Studios', 'Hành động', 181, 'Mỹ'),
('P002', 'Inception', 'Warner Bros', 'Khoa học viễn tưởng', 148, 'Mỹ'),
('P003', 'Parasite', 'CJ Entertainment', 'Tâm lý', 132, 'Hàn Quốc'),
('P004', 'Your Name', 'CoMix Wave Films', 'Tình cảm', 107, 'Nhật Bản'),
('P005', 'Titanic', '20th Century Fox', 'Tình cảm', 195, 'Mỹ'),
('P006', 'Joker', 'Warner Bros', 'Tâm lý', 122, 'Mỹ'),
('P007', 'The Dark Knight', 'Warner Bros', 'Hành động', 152, 'Mỹ'),
('P008', 'Interstellar', 'Paramount Pictures', 'Khoa học viễn tưởng', 169, 'Mỹ'),
('P009', 'Spirited Away', 'Studio Ghibli', 'Phiêu lưu', 125, 'Nhật Bản'),
('P010', 'Train to Busan', 'Next Entertainment World', 'Kinh dị', 118, 'Hàn Quốc'),
('P011', 'The Godfather', 'Paramount Pictures', 'Tội phạm', 175, 'Mỹ'),
('P012', 'Frozen', 'Walt Disney', 'Hoạt hình', 102, 'Mỹ'),
('P013', 'Minions', 'Illumination', 'Hoạt hình', 91, 'Mỹ'),
('P014', 'The Lion King', 'Walt Disney', 'Hoạt hình', 118, 'Mỹ'),
('P015', 'Doctor Strange', 'Marvel Studios', 'Hành động', 126, 'Mỹ'),
('P016', 'The Conjuring', 'New Line Cinema', 'Kinh dị', 112, 'Mỹ'),
('P017', 'John Wick', 'Summit Entertainment', 'Hành động', 101, 'Mỹ'),
('P018', 'The Matrix', 'Warner Bros', 'Khoa học viễn tưởng', 136, 'Mỹ'),
('P019', 'Avatar', '20th Century Fox', 'Khoa học viễn tưởng', 162, 'Mỹ'),
('P020', 'Detective Conan: Zero the Enforcer', 'TMS Entertainment', 'Hình sự', 110, 'Nhật Bản'),
('P021', 'Naruto the Movie: The Last', 'Studio Pierrot', 'Hành động', 114, 'Nhật Bản'),
('P022', 'A Silent Voice', 'Kyoto Animation', 'Tình cảm', 130, 'Nhật Bản'),
('P023', 'Doraemon: Nobita and the Steel Troops', 'Shin-Ei Animation', 'Hoạt hình', 108, 'Nhật Bản'),
('P024', 'The Avengers', 'Marvel Studios', 'Hành động', 143, 'Mỹ'),
('P025', 'Black Panther', 'Marvel Studios', 'Hành động', 134, 'Mỹ'),
('P026', 'The Nun', 'New Line Cinema', 'Kinh dị', 96, 'Mỹ'),
('P027', 'The Flash', 'Warner Bros', 'Siêu anh hùng', 144, 'Mỹ'),
('P028', 'Guardians of the Galaxy', 'Marvel Studios', 'Hành động', 121, 'Mỹ'),
('P029', 'Shin Godzilla', 'Toho', 'Khoa học viễn tưởng', 120, 'Nhật Bản'),
('P030', 'Squid Game: The Movie', 'Netflix Studios', 'Tâm lý', 138, 'Hàn Quốc');
