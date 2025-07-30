package com.example.zoo.controller;

import com.example.zoo.model.GiaVe;
import com.example.zoo.service.GiaVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/giave")
public class GiaVeController {

    @Autowired
    private GiaVeService giaVeService;

    @GetMapping
    public String danhSachVe(Model model) {
        model.addAttribute("danhSachVe", giaVeService.getAll());
        return "giave/list";
    }

    @GetMapping("/add")
    public String hienThiFormThem(Model model) {
        model.addAttribute("ve", new GiaVe());
        return "giave/add";
    }

    @PostMapping("/add")
    public String xuLyThemVe(@ModelAttribute GiaVe ve) {
        ve.setId(UUID.randomUUID().toString());
        giaVeService.themVe(ve);
        return "redirect:/giave";
    }

    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model) {
        GiaVe ve = giaVeService.timTheoId(id);
        if (ve == null) return "redirect:/giave";
        model.addAttribute("ve", ve);
        return "giave/edit";
    }

    @PostMapping("/edit")
    public String xuLySuaVe(@ModelAttribute GiaVe ve) {
        giaVeService.capNhatVe(ve);
        return "redirect:/giave";
    }

    @GetMapping("/delete/{id}")
    public String xoaVe(@PathVariable String id) {
        giaVeService.xoaVe(id);
        return "redirect:/giave";
    }
}
