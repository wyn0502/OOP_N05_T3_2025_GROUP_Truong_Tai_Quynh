public class DongVat {
    String MaDongVat;
    String TenDongVat;
    String Loai;
    int Tuoi;
    String ChuongSo;
    String ThoiGianChoAn;

    public void nhapTuoi (int x){
        this.Tuoi = x;
    }

    public int hienThiTuoi(){
        return this.Tuoi;
    }

    public void keu(){
        System.out.println("ec ec!");
    }
}
