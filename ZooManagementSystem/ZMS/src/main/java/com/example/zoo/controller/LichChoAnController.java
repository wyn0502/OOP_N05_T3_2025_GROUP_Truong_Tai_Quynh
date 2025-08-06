package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.service.LichChoAnService;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    @Autowired
    private LichChoAnService lichChoAnService;

    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("dsLich", lichChoAnService.layTatCa());
        return "lichchoan/list"; // bạn cần có file list.html
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        model.addAttribute("lichChoAn", new LichChoAn());
        return "lichchoan/form";
    }

    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lichChoAn") LichChoAn lich,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "lichchoan/form";
        }
        lichChoAnService.themLich(lich);
        return "redirect:/lichchoan";
    }

    @GetMapping("/sua/{maLich}")
    public String hienThiFormSua(@PathVariable String maLich, Model model) {
        LichChoAn lich = lichChoAnService.timTheoMa(maLich);
        if (lich == null) {
            return "redirect:/lichchoan";
        }
        model.addAttribute("lichChoAn", lich);
        return "lichchoan/form";
    }

    @PostMapping("/sua")
    public String xuLySua(@Valid @ModelAttribute("lichChoAn") LichChoAn lich,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "lichchoan/form";
        }
        lichChoAnService.capNhatLich(lich.getMaLich(), lich);
        return "redirect:/lichchoan";
    }

    @GetMapping("/xoa/{maLich}")
    public String xoa(@PathVariable String maLich) {
        lichChoAnService.xoaLich(maLich);
        return "redirect:/lichchoan";
    }
}

    
    


    

