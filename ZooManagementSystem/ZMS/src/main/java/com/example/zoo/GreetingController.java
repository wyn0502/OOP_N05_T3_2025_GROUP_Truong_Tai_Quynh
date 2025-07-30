package com.example.zoo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.GiaVe;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "Xuân Trường") String name,
            Model model) {

        DongVat dv = new DongVat("Gà Tre daden", 2, "Gà");
        GiaVe gv = new GiaVe("ve1612", "Vé Người Lớn", 150_000);

        model.addAttribute("name", name);
        model.addAttribute("dongVat", dv);
        model.addAttribute("giaVe", gv);

        return "greeting";
    }

    // đây là gọi riêng dongvat/
    // @GetMapping("/")
    // public String home() {
    // return "redirect:/dongvat";
    // }
}