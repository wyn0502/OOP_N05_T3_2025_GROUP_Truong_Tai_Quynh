package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

// Giả sử ZooSystemManagement được overload constructor để làm việc với DongVat
public class TestDongVat {

    private ZooSystemManagement zsmDongVat;
    private List<DongVat> danhSachDongVat;

    @BeforeEach
    public void setup() {
        danhSachDongVat = new ArrayList<>();
        zsmDongVat = new ZooSystemManagement(danhSachDongVat); // overload constructor
    }

    @Test
    public void testCreateDongVat() {
        DongVat d = new DongVat("dv01", "Sư tử", "Ăn thịt");
        zsmDongVat.create(d);
        assertEquals(1, zsmDongVat.getList().size());
        assertEquals("Sư tử", zsmDongVat.getList().get(0).ten);
    }

    @Test
    public void testDeleteDongVat() {
        zsmDongVat.create(new DongVat("dv02", "Hổ", "Ăn thịt"));
        zsmDongVat.delete("dv02");
        assertTrue(zsmDongVat.getList().isEmpty());
    }

    @Test
    public void testEditDongVat() {
        DongVat d = new DongVat("dv03", "Gấu", "Ăn tạp");
        zsmDongVat.create(d);

        // Giả lập sửa
        for (DongVat dongVat : zsmDongVat.getList()) {
            if (dongVat.id.equals("dv03")) {
                dongVat.ten = "Gấu trắng";
                dongVat.loai = "Ăn thịt";
            }
        }

        assertEquals("Gấu trắng", zsmDongVat.getList().get(0).ten);
        assertEquals("Ăn thịt", zsmDongVat.getList().get(0).loai);
    }

    @Test
    public void testDanhSachRong() {
        assertTrue(zsmDongVat.getList().isEmpty());
    }
}
