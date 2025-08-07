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

    // Danh sách
    @GetMapping
    public String hienThiDanhSach(Model model) {
        try {
            model.addAttribute("danhSach", service.layTatCa());
            return "dongvat/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tải danh sách động vật.");
            return "dongvat/list";
        }
    }

    // Form thêm mới
    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        try {
            model.addAttribute("dongVat", new DongVat());
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tạo mới động vật.");
            return "dongvat/form";
        }
    }

    // Form sửa
    @GetMapping("/sua/{id}")
    public String hienThiFormSua(@PathVariable Long id, Model model) {
        try {
            DongVat dv = service.timTheoId(id);
            if (dv == null) {
                model.addAttribute("error", "Không tìm thấy động vật.");
                return "redirect:/dongvat?notfound";
            }
            model.addAttribute("dongVat", dv);
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tải thông tin động vật.");
            return "dongvat/form";
        }
    }

    // Lưu
    @PostMapping("/luu")
    public String xuLyLuu(@ModelAttribute DongVat dongVat, Model model) {
        try {
            service.luuHoacCapNhat(dongVat);
            return "redirect:/dongvat?success";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể lưu động vật. Vui lòng thử lại!");
            model.addAttribute("dongVat", dongVat);
            return "dongvat/form";
        }
    }

    // Xóa
    @GetMapping("/xoa/{id}")
    public String xoaDongVat(@PathVariable Long id, Model model) {
        try {
            service.xoaTheoId(id);
            return "redirect:/dongvat?deleted";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa động vật.");
            return "dongvat/list";
        }
    }
}
