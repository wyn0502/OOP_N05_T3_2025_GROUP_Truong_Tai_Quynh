

import java.util.ArrayList;
import java.util.List;

public class TestLichChoAnManager {

    public static LichChoAnManager getManager() {
      
        List<LichChoAn> list = new ArrayList<>();

        list.add(new LichChoAn("001", "Hổ", "Thịt", "NV01", "08:00"));
        list.add(new LichChoAn("002", "Voi", "Chuối", "NV02", "09:00"));
        list.add(new LichChoAn("003", "Khỉ", "Chuối", "NV03", "10:00"));

       
        return new LichChoAnManager(list);
    }
}
