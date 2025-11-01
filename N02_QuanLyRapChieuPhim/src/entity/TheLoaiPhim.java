<<<<<<< HEAD
package entity;

public enum TheLoaiPhim {
    HANH_DONG("Hành động"),
    TINH_CAM("Tình cảm"),
    HAI_HUOC("Hài hước"),
    KINH_DI("Kinh dị"),
    HOAT_HINH("Hoạt hình"),
    TAM_LY("Tâm lý"),
    VIEN_TUONG("Viễn tưởng");

    private final String tenHienThi;

    TheLoaiPhim(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    @Override
    public String toString() {
        return tenHienThi;
    }

    public static TheLoaiPhim fromTenHienThi(String ten) {
        for (TheLoaiPhim tl : TheLoaiPhim.values()) {
            if (tl.tenHienThi.equalsIgnoreCase(ten)) {
                return tl;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy thể loại: " + ten);
    }
}
=======
package entity;

public enum TheLoaiPhim {
    HANH_DONG("Hành động"),
    TINH_CAM("Tình cảm"),
    HAI_HUOC("Hài hước"),
    KINH_DI("Kinh dị"),
    HOAT_HINH("Hoạt hình"),
    TAM_LY("Tâm lý"),
    VIEN_TUONG("Viễn tưởng");

    private final String tenHienThi;

    TheLoaiPhim(String tenHienThi) {
        this.tenHienThi = tenHienThi;
    }

    public String getTenHienThi() {
        return tenHienThi;
    }

    @Override
    public String toString() {
        return tenHienThi;
    }

    public static TheLoaiPhim fromTenHienThi(String ten) {
        for (TheLoaiPhim tl : TheLoaiPhim.values()) {
            if (tl.tenHienThi.equalsIgnoreCase(ten)) {
                return tl;
            }
        }
        throw new IllegalArgumentException("Không tìm thấy thể loại: " + ten);
    }
}
>>>>>>> 9ba1db77d8c31a7101e0c7541b4b31d5ea5e7965
