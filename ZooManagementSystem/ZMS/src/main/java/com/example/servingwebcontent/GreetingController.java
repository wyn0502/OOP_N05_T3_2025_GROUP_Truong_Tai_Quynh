package com.example.servingwebcontent;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import src.DongVat;
import src.GiaVe;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greeting(
        @RequestParam(name="name", required=false, defaultValue="Xuân Trường") String name,
        Model model) {

        DongVat dv = new DongVat("Voi Con", 5, "voi");
        GiaVe ve = new GiaVe("VIP", 200_000);

        model.addAttribute("name", name);
        model.addAttribute("dongVat", dv);
        model.addAttribute("giaVe", ve);
        model.addAttribute("giaVeTreEm", ve.tinhGiaTheoDoiTuong("tre em"));

        return "greeting";
    }
}
