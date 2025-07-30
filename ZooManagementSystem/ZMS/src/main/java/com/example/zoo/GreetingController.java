package com.example.zoo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.zoo.GiaVe;
import com.example.zoo.model.DongVat;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "name", required = false, defaultValue = "Xuân Trường") String name,
            Model model) {

        // Chỉ test DongVat trước
        DongVat dv = new DongVat("Gà Tre daden", 2, "Gà");

        model.addAttribute("name", name);
        model.addAttribute("dongVat", dv);

        return "greeting"; // Test DongVat only
    }
    
    // Redirect root to animal list
    @GetMapping("/")
    public String home() {
        return "redirect:/dongvat";
    }
}