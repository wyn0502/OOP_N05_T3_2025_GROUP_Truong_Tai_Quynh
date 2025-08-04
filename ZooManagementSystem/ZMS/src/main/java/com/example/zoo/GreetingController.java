package com.example.zoo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.GiaVe;

import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "User") String name,
            Model model) {

        DongVat dv = new DongVat("Gà Tre daden", 2, "Gà");
        
        GiaVe gv = new GiaVe("ve1612", "Vé Trẻ Em", 100000.0, "Vui hè", 50.0);

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

        model.addAttribute("name", name);
        model.addAttribute("dongVat", dv);
        model.addAttribute("giaVe", gv);
        model.addAttribute("currentTime", now.format(formatter));
        model.addAttribute("timezone", "GMT+7 (Việt Nam)");
        
        return "greeting";
    }

    // Uncomment nếu muốn redirect root về dongvat
    // @GetMapping("/")
    // public String home() {
    //     return "redirect:/dongvat";
    // }
}
