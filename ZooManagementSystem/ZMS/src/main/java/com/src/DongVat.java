package src;

public class DongVat {
    String ten;
    int tuoi;
    String loai;

    public DongVat(String ten, int tuoi, String loai) {
        this.ten = ten;
        this.tuoi = tuoi;
        this.loai = loai;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getTuoi() {
        return tuoi;
    }

    public void setTuoi(int tuoi) {
        this.tuoi = tuoi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public void hienThiThongTin() {
        System.out.println("Ten: " + ten);
        System.out.println("Tuoi: " + tuoi);
        System.out.println("Loai: " + loai);
    }

    public void keu(String loai) {
        if (loai.equalsIgnoreCase("khi")) {
            System.out.println("Khi: ec ec!");
        } else if (loai.equalsIgnoreCase("su tu")) {
            System.out.println("Su tu: Gam gu!");
        }
    }
}
