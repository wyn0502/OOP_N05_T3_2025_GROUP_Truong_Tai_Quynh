package com.example.zoo;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.GiaVe;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class GreetingController {

    // Trang demo Greeting (hiển thị thông tin mẫu)
    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "User") String name,
            Model model) {

        // Dữ liệu mẫu (có thể thay bằng lấy từ DB nếu cần)
        DongVat dv = new DongVat("Gà Tre đen", 2, "Gà");
        GiaVe gv = new GiaVe("ve1612", "Vé Trẻ Em", 100000.0, "Vui hè", 50.0);

        // Thời gian hiện tại tại Việt Nam
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        model.addAttribute("name", name);
        model.addAttribute("dongVat", dv);
        model.addAttribute("giaVe", gv);
        model.addAttribute("currentTime", now.format(formatter));
        model.addAttribute("timezone", "GMT+7 (Việt Nam)");

        return "greeting"; // trả về file greeting.html
    }

    // Trang gốc sẽ chuyển về trang quản lý lịch cho ăn
    @GetMapping("/")
    public String home() {
        return "redirect:/lichchoan";
    }
}
