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

    // Hiển thị danh sách động vật
    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("danhSach", service.layTatCa());
        return "dongvat/list";
    }

    // Form thêm mới
    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        model.addAttribute("dongVat", new DongVat());
        return "dongvat/form";
    }

    // Form sửa
    @GetMapping("/sua/{id}")
    public String hienThiFormSua(@PathVariable Long id, Model model) {
        DongVat dv = service.timTheoId(id);
        if (dv == null) {
            return "redirect:/dongvat?notfound";
        }
        model.addAttribute("dongVat", dv);
        return "dongvat/form";
    }

    // Xử lý thêm/sửa
    @PostMapping("/luu")
    public String xuLyLuu(@ModelAttribute DongVat dongVat) {
        service.luuHoacCapNhat(dongVat);
        return "redirect:/dongvat?success";
    }

    // Xử lý xóa
    @GetMapping("/xoa/{id}")
    public String xoaDongVat(@PathVariable Long id) {
        service.xoaTheoId(id);
        return "redirect:/dongvat?deleted";
    }
}
