package src;

public class testDongVatManager {
    public void chayThu() {
        DongVatManager manager = new DongVatManager();

        manager.themDongVat();
        manager.themDongVat();

        System.out.println("Danh sách động vật hiện tại:");
        manager.hienThiTatCaDongVat();

        manager.suaDongVat();

        System.out.println("Danh sách động vật sau khi sửa:");
        manager.hienThiTatCaDongVat();

        manager.xoaDongVat();

        System.out.println("Danh sách động vật sau khi xóa:");
        manager.hienThiTatCaDongVat();
    }
}
