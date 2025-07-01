public class Chuong {
    String MaChuong;
    String Tenkhuvuc;
    int SucChuaToiDa;
    int SoLuongHienTai;

    public Chuong(String MaChuong, String Tenkhuvuc, int SucChuaToiDa) {
        this.MaChuong = MaChuong;
        this.Tenkhuvuc = Tenkhuvuc;
        this.SucChuaToiDa = SucChuaToiDa;
    }

    public void hienThiThongTin() {
        System.out.println("Ma chuong: " + MaChuong);
        System.out.println("Ten khu vuc: " + Tenkhuvuc);
        System.out.println("Suc chua: " + SucChuaToiDa);
    }
}
