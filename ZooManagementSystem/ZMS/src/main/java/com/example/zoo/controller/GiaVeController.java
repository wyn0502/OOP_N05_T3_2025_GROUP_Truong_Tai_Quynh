package com.example.zoo.controller;

import com.example.zoo.model.GiaVe;
import com.example.zoo.service.GiaVeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
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
    public String xuLyThemVe(@Valid @ModelAttribute("ve") GiaVe ve, BindingResult result, Model model) {
        // Kiểm tra thêm logic nghiệp vụ
        if (ve.getLoaiVe() == null || ve.getLoaiVe().trim().isEmpty()) {
            result.rejectValue("loaiVe", "error.ve", "Vui lòng chọn loại vé");
        }
        
        if (result.hasErrors()) {
            model.addAttribute("ve", ve);
            return "giave/add";
        }
        
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
    public String xuLySuaVe(@Valid @ModelAttribute("ve") GiaVe ve, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("ve", ve);
            return "giave/edit";
        }
        giaVeService.capNhatVe(ve);
        return "redirect:/giave";
    }

    @GetMapping("/delete/{id}")
    public String xoaVe(@PathVariable String id) {
        giaVeService.xoaVe(id);
        return "redirect:/giave";
    }
}
