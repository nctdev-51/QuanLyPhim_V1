package entity;

public class Ghe {
    private String maGhe;
    private Rap rap;
    private boolean tinhTrang; // true = đã đặt

    public Ghe(String maGhe, Rap rap, boolean tinhTrang) {
        setMaGhe(maGhe);
        setRap(rap);
        this.tinhTrang = tinhTrang;
    }

    public String getMaGhe() { return maGhe; }
    public void setMaGhe(String maGhe) {
        if (maGhe == null || maGhe.isEmpty()) throw new IllegalArgumentException("Mã ghế rỗng!");
        this.maGhe = maGhe;
    }

    public Rap getRap() { return rap; }
    public void setRap(Rap rap) {
        if (rap == null) throw new IllegalArgumentException("Rạp không được null!");
        this.rap = rap;
    }

    public boolean isTinhTrang() { return tinhTrang; }
    public void setTinhTrang(boolean tinhTrang) { this.tinhTrang = tinhTrang; }

    public boolean datGhe() {
        if (!tinhTrang) {
            tinhTrang = true;
            return true;
        }
        return false;
    }

    public boolean huyDat() {
        if (tinhTrang) {
            tinhTrang = false;
            return true;
        }
        return false;
    }

	@Override
	public String toString() {
		return "Ghe [maGhe=" + maGhe + ", rap=" + rap + ", tinhTrang=" + tinhTrang + "]";
	}

}
