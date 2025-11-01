

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
('P001', 'Cuộc chiến vĩ đại', 'Studio A', 'Hành động', 120, 'Mỹ'),
('P002', 'Tình yêu mùa hè', 'Studio B', 'Tình cảm', 105, 'Hàn Quốc'),
('P003', 'Cười thả ga', 'Studio C', 'Hài hước', 95, 'Mỹ'),
('P004', 'Ngôi nhà ma', 'Studio D', 'Kinh dị', 110, 'Anh'),
('P005', 'Chú mèo tinh nghịch', 'Studio E', 'Hoạt hình', 80, 'Nhật Bản'),
('P006', 'Bí mật tâm lý', 'Studio F', 'Tâm lý', 115, 'Pháp'),
('P007', 'Hành tinh xa xôi', 'Studio G', 'Viễn tưởng', 130, 'Mỹ'),
('P008', 'Sát thủ bóng đêm', 'Studio H', 'Hành động', 125, 'Mỹ'),
('P009', 'Chuyện tình Paris', 'Studio I', 'Tình cảm', 100, 'Pháp'),
('P010', 'Hội bạn vui nhộn', 'Studio J', 'Hài hước', 90, 'Hàn Quốc'),
('P011', 'Người ngoài hành tinh', 'Studio K', 'Viễn tưởng', 140, 'Mỹ'),
('P012', 'Ngôi làng ma quái', 'Studio L', 'Kinh dị', 105, 'Anh'),
('P013', 'Bức tranh tâm lý', 'Studio M', 'Tâm lý', 110, 'Nhật Bản'),
('P014', 'Chú heo phiêu lưu', 'Studio N', 'Hoạt hình', 85, 'Mỹ'),
('P015', 'Cuộc đua tốc độ', 'Studio O', 'Hành động', 115, 'Mỹ'),
('P016', 'Tình yêu tuổi học trò', 'Studio P', 'Tình cảm', 95, 'Hàn Quốc'),
('P017', 'Cười rụng rốn', 'Studio Q', 'Hài hước', 100, 'Mỹ'),
('P018', 'Bóng ma trong rừng', 'Studio R', 'Kinh dị', 120, 'Anh'),
('P019', 'Tâm lý gia đình', 'Studio S', 'Tâm lý', 110, 'Pháp'),
('P020', 'Cuộc phiêu lưu của gấu', 'Studio T', 'Hoạt hình', 90, 'Nhật Bản'),
('P021', 'Hành trình cứu thế giới', 'Studio U', 'Viễn tưởng', 135, 'Mỹ'),
('P022', 'Sát thủ đường phố', 'Studio V', 'Hành động', 125, 'Mỹ'),
('P023', 'Mối tình ngọt ngào', 'Studio W', 'Tình cảm', 105, 'Hàn Quốc'),
('P024', 'Hội bạn siêu hài', 'Studio X', 'Hài hước', 95, 'Mỹ'),
('P025', 'Ngôi nhà kỳ bí', 'Studio Y', 'Kinh dị', 115, 'Anh'),
('P026', 'Tâm lý học sinh', 'Studio Z', 'Tâm lý', 100, 'Pháp'),
('P027', 'Chú chó tinh nghịch', 'Studio AA', 'Hoạt hình', 85, 'Mỹ'),
('P028', 'Cuộc chiến vũ trụ', 'Studio BB', 'Viễn tưởng', 140, 'Mỹ'),
('P029', 'Anh hùng đường phố', 'Studio CC', 'Hành động', 120, 'Mỹ'),
('P030', 'Chuyện tình mùa đông', 'Studio DD', 'Tình cảm', 100, 'Hàn Quốc');

select * from Phim
