package com.example.zoo.controller;

import com.example.zoo.manager.DongVatManager;
import com.example.zoo.model.DongVat;
import com.example.zoo.manager.DongVatManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class DongVatController {

    @Autowired
    private DongVatManager manager;

    @GetMapping("/dongvat")
    public String danhSach(Model model) {
        model.addAttribute("dsDongVat", manager.getAll());
        return "dongvat/list";
    }

    @PostMapping("/dongvat/them")
    public String them(@RequestParam String ten,
                       @RequestParam int tuoi,
                       @RequestParam String loai) {
        manager.them(new DongVat(ten, tuoi, loai));
        return "redirect:/dongvat";
    }
}