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

    // Danh sách
    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("dsLich", lichChoAnService.layTatCa());
        return "lichchoan/list";
    }

    // Hiển thị form thêm mới
    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        model.addAttribute("lichChoAn", new LichChoAn());
        return "lichchoan/form";
    }

    // Xử lý thêm
    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lichChoAn") LichChoAn lich,
                           BindingResult result) {
        if (result.hasErrors()) {
            return "lichchoan/form";
        }
        lichChoAnService.themLich(lich);
        return "redirect:/lichchoan";
    }

    // Hiển thị form sửa
    @GetMapping("/sua/{maLich}")
    public String hienThiFormSua(@PathVariable String maLich, Model model) {
        LichChoAn lich = lichChoAnService.timTheoMa(maLich);
        if (lich == null) {
            return "redirect:/lichchoan";
        }
        model.addAttribute("lichChoAn", lich);
        return "lichchoan/form";
    }

    // Xử lý sửa
    @PostMapping("/sua")
    public String xuLySua(@Valid @ModelAttribute("lichChoAn") LichChoAn lich,
                          BindingResult result) {
        if (result.hasErrors()) {
            return "lichchoan/form";
        }
        lichChoAnService.capNhatLich(lich.getMaLich(), lich);
        return "redirect:/lichchoan";
    }

    // Xóa lịch
    @GetMapping("/xoa/{maLich}")
    public String xoa(@PathVariable String maLich) {
        lichChoAnService.xoaLich(maLich);
        return "redirect:/lichchoan";
    }
}
