

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
	tenGhe NVARCHAR(20) NOT NULL,
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
('P001', N'Cuộc chiến vĩ đại', N'Studio A', N'Hành động', 120, N'Mỹ'),
('P002', N'Tình yêu mùa hè', N'Studio B', N'Tình cảm', 105, N'Hàn Quốc'),
('P003', N'Cười thả ga', N'Studio C', N'Hài hước', 95, N'Mỹ'),
('P004', N'Ngôi nhà ma', N'Studio D', N'Kinh dị', 110, N'Anh'),
('P005', N'Chú mèo tinh nghịch', N'Studio E', N'Hoạt hình', 80, N'Nhật Bản'),
('P006', N'Bí mật tâm lý', N'Studio F', N'Tâm lý', 115, N'Pháp'),
('P007', N'Hành tinh xa xôi', N'Studio G', N'Viễn tưởng', 130, N'Mỹ'),
('P008', N'Sát thủ bóng đêm', N'Studio H', N'Hành động', 125, N'Mỹ'),
('P009', N'Chuyện tình Paris', N'Studio I', N'Tình cảm', 100, N'Pháp'),
('P010', N'Hội bạn vui nhộn', N'Studio J', N'Hài hước', 90, N'Hàn Quốc'),
('P011', N'Người ngoài hành tinh', N'Studio K', N'Viễn tưởng', 140, N'Mỹ'),
('P012', N'Ngôi làng ma quái', N'Studio L', N'Kinh dị', 105, N'Anh'),
('P013', N'Bức tranh tâm lý', N'Studio M', N'Tâm lý', 110, N'Nhật Bản'),
('P014', N'Chú heo phiêu lưu', N'Studio N', N'Hoạt hình', 85, N'Mỹ'),
('P015', N'Cuộc đua tốc độ', N'Studio O', N'Hành động', 115, N'Mỹ'),
('P016', N'Tình yêu tuổi học trò', N'Studio P', N'Tình cảm', 95, N'Hàn Quốc'),
('P017', N'Cười rụng rốn', N'Studio Q', N'Hài hước', 100, N'Mỹ'),
('P018', N'Bóng ma trong rừng', N'Studio R', N'Kinh dị', 120, N'Anh'),
('P019', N'Tâm lý gia đình', N'Studio S', N'Tâm lý', 110, N'Pháp'),
('P020', N'Cuộc phiêu lưu của gấu', N'Studio T', N'Hoạt hình', 90, N'Nhật Bản'),
('P021', N'Hành trình cứu thế giới', N'Studio U', N'Viễn tưởng', 135, N'Mỹ'),
('P022', N'Sát thủ đường phố', N'Studio V', N'Hành động', 125, N'Mỹ'),
('P023', N'Mối tình ngọt ngào', N'Studio W', N'Tình cảm', 105, N'Hàn Quốc'),
('P024', N'Hội bạn siêu hài', N'Studio X', N'Hài hước', 95, N'Mỹ'),
('P025', N'Ngôi nhà kỳ bí', N'Studio Y', N'Kinh dị', 115, N'Anh'),
('P026', N'Tâm lý học sinh', N'Studio Z', N'Tâm lý', 100, N'Pháp'),
('P027', N'Chú chó tinh nghịch', N'Studio AA', N'Hoạt hình', 85, N'Mỹ'),
('P028', N'Cuộc chiến vũ trụ', N'Studio BB', N'Viễn tưởng', 140, N'Mỹ'),
('P029', N'Anh hùng đường phố', N'Studio CC', N'Hành động', 120, N'Mỹ'),
('P030', N'Chuyện tình mùa đông', N'Studio DD', N'Tình cảm', 100, N'Hàn Quốc');
GO
-- ==========================================
-- CHÈN DỮ LIỆU MẪU VÀO BẢNG RẠP
-- ==========================================
INSERT INTO Rap (maRap, tenRap, soLuongGhe) VALUES
('RAP001', N'Phòng 1', 25),
('RAP002', N'Phòng 2', 30),
('RAP003', N'Phòng 3', 30),
('RAP004', N'Phòng 4', 30);
GO
-- ==========================================
-- CHÈN DỮ LIỆU MẪU VÀO BẢNG SUẤT CHIẾU
-- ==========================================
INSERT INTO SuatChieu (maSuatChieu, maPhim, maRap, ngayChieu, gioChieu, giaVe) VALUES
('SC001', 'P001', 'RAP001', '2025-11-03', '10:00', 50000),
('SC002', 'P002', 'RAP002', '2025-11-03', '12:30', 55000),
('SC003', 'P003', 'RAP003', '2025-11-03', '14:00', 45000),
('SC004', 'P004', 'RAP004', '2025-11-03', '16:00', 60000),
('SC005', 'P005', 'RAP001', '2025-11-03', '18:30', 50000),
('SC006', 'P006', 'RAP001', '2025-11-03', '20:00', 55000),
('SC007', 'P007', 'RAP001', '2025-11-04', '10:30', 60000),
('SC008', 'P008', 'RAP001', '2025-11-04', '13:00', 50000),
('SC009', 'P009', 'RAP001', '2025-11-04', '15:30', 55000),
('SC010', 'P010', 'RAP001', '2025-11-04', '18:00', 45000),
('SC011', 'P011', 'RAP001', '2025-11-05', '10:00', 60000),
('SC012', 'P012', 'RAP002', '2025-11-05', '12:30', 50000),
('SC013', 'P013', 'RAP003', '2025-11-05', '14:30', 55000),
('SC014', 'P014', 'RAP002', '2025-11-05', '17:00', 45000),
('SC015', 'P015', 'RAP002', '2025-11-05', '19:00', 60000),
('SC016', 'P016', 'RAP004', '2025-11-06', '10:30', 50000),
('SC017', 'P017', 'RAP004', '2025-11-06', '13:00', 55000),
('SC018', 'P018', 'RAP003', '2025-11-06', '15:30', 60000),
('SC019', 'P019', 'RAP003', '2025-11-06', '18:00', 50000),
('SC020', 'P020', 'RAP003', '2025-11-06', '20:00', 55000);
GO
-- ==========================================
-- CHÈN DỮ LIỆU MẪU VÀO BẢNG Ghế
-- ==========================================
-- Ghế cho RAP001 (25 ghế)
INSERT INTO Ghe (maGhe, tenGhe, maRap, tinhTrang) VALUES
('RAP001_G1', N'Ghế 1', 'RAP001', 1),
('RAP001_G2', N'Ghế 2', 'RAP001', 1),
('RAP001_G3', N'Ghế 3', 'RAP001', 0),
('RAP001_G4', N'Ghế 4', 'RAP001', 0),
('RAP001_G5', N'Ghế 5', 'RAP001', 0),
('RAP001_G6', N'Ghế 6', 'RAP001', 0),
('RAP001_G7', N'Ghế 7', 'RAP001', 0),
('RAP001_G8', N'Ghế 8', 'RAP001', 0),
('RAP001_G9', N'Ghế 9', 'RAP001', 0),
('RAP001_G10', N'Ghế 10', 'RAP001', 0),
('RAP001_G11', N'Ghế 11', 'RAP001', 0),
('RAP001_G12', N'Ghế 12', 'RAP001', 0),
('RAP001_G13', N'Ghế 13', 'RAP001', 0),
('RAP001_G14', N'Ghế 14', 'RAP001', 0),
('RAP001_G15', N'Ghế 15', 'RAP001', 0),
('RAP001_G16', N'Ghế 16', 'RAP001', 0),
('RAP001_G17', N'Ghế 17', 'RAP001', 0),
('RAP001_G18', N'Ghế 18', 'RAP001', 0),
('RAP001_G19', N'Ghế 19', 'RAP001', 0),
('RAP001_G20', N'Ghế 20', 'RAP001', 0),
('RAP001_G21', N'Ghế 21', 'RAP001', 0),
('RAP001_G22', N'Ghế 22', 'RAP001', 0),
('RAP001_G23', N'Ghế 23', 'RAP001', 0),
('RAP001_G24', N'Ghế 24', 'RAP001', 0),
('RAP001_G25', N'Ghế 25', 'RAP001', 0);
GO
-- Ghế cho RAP002 (30 ghế)
INSERT INTO Ghe (maGhe, tenGhe, maRap, tinhTrang) VALUES
('RAP002_G1', N'Ghế 1', 'RAP002', 0),
('RAP002_G2', N'Ghế 2', 'RAP002', 0),
('RAP002_G3', N'Ghế 3', 'RAP002', 0),
('RAP002_G4', N'Ghế 4', 'RAP002', 0),
('RAP002_G5', N'Ghế 5', 'RAP002', 0),
('RAP002_G6', N'Ghế 6', 'RAP002', 0),
('RAP002_G7', N'Ghế 7', 'RAP002', 0),
('RAP002_G8', N'Ghế 8', 'RAP002', 0),
('RAP002_G9', N'Ghế 9', 'RAP002', 0),
('RAP002_G10', N'Ghế 10', 'RAP002', 1),
('RAP002_G11', N'Ghế 11', 'RAP002', 1),
('RAP002_G12', N'Ghế 12', 'RAP002', 0),
('RAP002_G13', N'Ghế 13', 'RAP002', 0),
('RAP002_G14', N'Ghế 14', 'RAP002', 0),
('RAP002_G15', N'Ghế 15', 'RAP002', 0),
('RAP002_G16', N'Ghế 16', 'RAP002', 0),
('RAP002_G17', N'Ghế 17', 'RAP002', 0),
('RAP002_G18', N'Ghế 18', 'RAP002', 0),
('RAP002_G19', N'Ghế 19', 'RAP002', 0),
('RAP002_G20', N'Ghế 20', 'RAP002', 0),
('RAP002_G21', N'Ghế 21', 'RAP002', 0),
('RAP002_G22', N'Ghế 22', 'RAP002', 0),
('RAP002_G23', N'Ghế 23', 'RAP002', 0),
('RAP002_G24', N'Ghế 24', 'RAP002', 0),
('RAP002_G25', N'Ghế 25', 'RAP002', 0),
('RAP002_G26', N'Ghế 26', 'RAP002', 0),
('RAP002_G27', N'Ghế 27', 'RAP002', 0),
('RAP002_G28', N'Ghế 28', 'RAP002', 0),
('RAP002_G29', N'Ghế 29', 'RAP002', 0),
('RAP002_G30', N'Ghế 30', 'RAP002', 0);
GO
-- Ghế cho RAP003 (30 ghế)
INSERT INTO Ghe (maGhe, tenGhe, maRap, tinhTrang) VALUES
('RAP003_G1', N'Ghế 1', 'RAP003', 0),
('RAP003_G2', N'Ghế 2', 'RAP003', 0),
('RAP003_G3', N'Ghế 3', 'RAP003', 0),
('RAP003_G4', N'Ghế 4', 'RAP003', 0),
('RAP003_G5', N'Ghế 5', 'RAP003', 0),
('RAP003_G6', N'Ghế 6', 'RAP003', 0),
('RAP003_G7', N'Ghế 7', 'RAP003', 0),
('RAP003_G8', N'Ghế 8', 'RAP003', 0),
('RAP003_G9', N'Ghế 9', 'RAP003', 0),
('RAP003_G10', N'Ghế 10', 'RAP003', 0),
('RAP003_G11', N'Ghế 11', 'RAP003', 0),
('RAP003_G12', N'Ghế 12', 'RAP003', 0),
('RAP003_G13', N'Ghế 13', 'RAP003', 0),
('RAP003_G14', N'Ghế 14', 'RAP003', 0),
('RAP003_G15', N'Ghế 15', 'RAP003', 1),
('RAP003_G16', N'Ghế 16', 'RAP003', 1),
('RAP003_G17', N'Ghế 17', 'RAP003', 1),
('RAP003_G18', N'Ghế 18', 'RAP003', 0),
('RAP003_G19', N'Ghế 19', 'RAP003', 0),
('RAP003_G20', N'Ghế 20', 'RAP003', 0),
('RAP003_G21', N'Ghế 21', 'RAP003', 0),
('RAP003_G22', N'Ghế 22', 'RAP003', 0),
('RAP003_G23', N'Ghế 23', 'RAP003', 0),
('RAP003_G24', N'Ghế 24', 'RAP003', 0),
('RAP003_G25', N'Ghế 25', 'RAP003', 0),
('RAP003_G26', N'Ghế 26', 'RAP003', 0),
('RAP003_G27', N'Ghế 27', 'RAP003', 0),
('RAP003_G28', N'Ghế 28', 'RAP003', 0),
('RAP003_G29', N'Ghế 29', 'RAP003', 0),
('RAP003_G30', N'Ghế 30', 'RAP003', 0);
GO
-- Ghế cho RAP004 (30 ghế)
INSERT INTO Ghe (maGhe, tenGhe, maRap, tinhTrang) VALUES
('RAP004_G1', N'Ghế 1', 'RAP004', 0),
('RAP004_G2', N'Ghế 2', 'RAP004', 0),
('RAP004_G3', N'Ghế 3', 'RAP004', 0),
('RAP004_G4', N'Ghế 4', 'RAP004', 0),
('RAP004_G5', N'Ghế 5', 'RAP004', 0),
('RAP004_G6', N'Ghế 6', 'RAP004', 0),
('RAP004_G7', N'Ghế 7', 'RAP004', 0),
('RAP004_G8', N'Ghế 8', 'RAP004', 0),
('RAP004_G9', N'Ghế 9', 'RAP004', 0),
('RAP004_G10', N'Ghế 10', 'RAP004', 0),
('RAP004_G11', N'Ghế 11', 'RAP004', 0),
('RAP004_G12', N'Ghế 12', 'RAP004', 0),
('RAP004_G13', N'Ghế 13', 'RAP004', 0),
('RAP004_G14', N'Ghế 14', 'RAP004', 0),
('RAP004_G15', N'Ghế 15', 'RAP004', 0),
('RAP004_G16', N'Ghế 16', 'RAP004', 0),
('RAP004_G17', N'Ghế 17', 'RAP004', 0),
('RAP004_G18', N'Ghế 18', 'RAP004', 0),
('RAP004_G19', N'Ghế 19', 'RAP004', 0),
('RAP004_G20', N'Ghế 20', 'RAP004', 0),
('RAP004_G21', N'Ghế 21', 'RAP004', 0),
('RAP004_G22', N'Ghế 22', 'RAP004', 0),
('RAP004_G23', N'Ghế 23', 'RAP004', 0),
('RAP004_G24', N'Ghế 24', 'RAP004', 0),
('RAP004_G25', N'Ghế 25', 'RAP004', 0),
('RAP004_G26', N'Ghế 26', 'RAP004', 0),
('RAP004_G27', N'Ghế 27', 'RAP004', 0),
('RAP004_G28', N'Ghế 28', 'RAP004', 0),
('RAP004_G29', N'Ghế 29', 'RAP004', 1),
('RAP004_G30', N'Ghế 30', 'RAP004', 1);
GO
-- Thêm 3 nhân viên vào bảng NhanVien
INSERT INTO NhanVien (maNV, tenNV, diaChi, soDienThoai, ngaySinh, email, gioiTinh)
VALUES
('NV01', N'Lê Minh Tân', N'123 Lê Lợi, Quận 1, TP.HCM', '0905123456', '1998-03-15', 'an.nguyen@example.com', N'Nam'),
('NV02', N'Nguyễn Chí Tâm', N'45 Hai Bà Trưng, Hà Nội', '0987654321', '2000-07-22', 'binh.tran@example.com', N'Nữ'),
('NV03', N'Đỗ Thanh Tường', N'78 Nguyễn Huệ, Đà Nẵng', '0912345678', '1995-11-09', 'phuc.le@example.com', N'Nam');
GO

--Thêm 3 tài khoản

INSERT INTO TaiKhoan (maNV, taiKhoan, matKhau)
VALUES
('NV01', N'leminhtan', N'123455'),
('NV02', N'nguyenchitam', N'123455'),
('NV03', N'dothanhtuong', N'123455');
