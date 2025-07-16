

import java.util.ArrayList;
import java.util.List;

public class TestLichChoAnManager {

    public static LichChoAnManager getSampleManager() {
        // Tạo danh sách mẫu
        List<LichChoAn> list = new ArrayList<>();

        list.add(new LichChoAn("L01", "Hổ", "Thịt", "NV01", "08:00"));
        list.add(new LichChoAn("L02", "Voi", "Chuối", "NV02", "09:00"));
        list.add(new LichChoAn("L03", "Khỉ", "Chuối", "NV03", "10:00"));

       
        return new LichChoAnManager(list);
    }
}
