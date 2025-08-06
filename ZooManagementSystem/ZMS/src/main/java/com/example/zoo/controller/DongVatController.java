package com.example.zoo.controller;

import com.example.zoo.model.DongVat;
import com.example.zoo.service.DongVatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dongvat")
public class DongVatController {

    @Autowired
    private DongVatService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("danhSach", service.layTatCa());
        return "dongvat/list";
    }

    @GetMapping("/them")
    public String themForm(Model model) {
        model.addAttribute("dongVat", new DongVat());
        return "dongvat/form";
    }

    @GetMapping("/sua/{ten}")
    public String suaForm(@PathVariable String ten, Model model) {
        DongVat dv = service.timTheoTen(ten);
        if (dv == null) {
            return "redirect:/dongvat";
        }
        model.addAttribute("dongVat", dv);
        model.addAttribute("originalTen", ten);
        return "dongvat/form";
    }

    @PostMapping("/luu")
    public String luu(@ModelAttribute DongVat dongVat,
                      @RequestParam(name = "originalTen", required = false) String originalTen) {
        if (originalTen != null && !originalTen.isEmpty()) {
            service.sua(originalTen, dongVat);
        } else {
            if (service.timTheoTen(dongVat.getTen()) == null) {
                service.them(dongVat);
            }
        }
        return "redirect:/dongvat";
    }

    @GetMapping("/xoa/{ten}")
    public String xoa(@PathVariable String ten) {
        service.xoa(ten);
        return "redirect:/dongvat";
    }
}
