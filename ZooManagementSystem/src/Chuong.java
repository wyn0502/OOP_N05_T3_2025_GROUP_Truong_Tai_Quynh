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
    
    Chuong(){};
    public Chuong (String t, String a, int p, int q){
    MaChuong = t;
    Tenkhuvuc = a;
    SucChuaToiDa = p;
    SoLuongHienTai = q;
    }

    public static void main(String[] args){
    Chuong myObj = new Chuong ("001", "16", 10, 6);
    System.out.println(myObj.MaChuong + " " + myObj.Tenkhuvuc);
   }

}
