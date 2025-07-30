package com.example.zoo.controller;

import com.example.zoo.service.DongVatService;
import com.example.zoo.model.DongVat;
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

    // Hiển thị form để thêm mới
    @GetMapping("/them")
    public String themForm(Model model) {
        model.addAttribute("dongVat", new DongVat());
        // Không cần thêm originalTen vì đây là form thêm mới
        return "dongvat/form";
    }

    // Hiển thị form để sửa, gửi kèm "originalTen" (tên gốc)
    @GetMapping("/sua/{ten}")
    public String suaForm(@PathVariable String ten, Model model) {
        DongVat dv = service.timTheoTen(ten);
        if (dv == null) {
            return "redirect:/dongvat";
        }
        model.addAttribute("dongVat", dv);
        // Gửi tên gốc sang view để lưu vào một trường ẩn
        model.addAttribute("originalTen", ten);
        return "dongvat/form";
    }

    // Xử lý việc lưu (cho cả thêm mới và sửa)
    @PostMapping("/luu")
    public String luu(@ModelAttribute DongVat dongVat,
                      @RequestParam(name = "originalTen", required = false) String originalTen) {

        // Nếu có originalTen, nghĩa là đây là hành động SỬA
        if (originalTen != null && !originalTen.isEmpty()) {
            service.sua(originalTen, dongVat);
        } else { // Ngược lại, đây là hành động THÊM MỚI
            // Kiểm tra xem tên đã tồn tại chưa để tránh trùng lặp
            if (service.timTheoTen(dongVat.getTen()) == null) {
                service.them(dongVat);
            }
        }
        return "redirect:/dongvat";
    }

    // Xử lý xóa động vật
    @GetMapping("/xoa/{ten}")
    public String xoa(@PathVariable String ten) {
        service.xoa(ten);
        return "redirect:/dongvat";
    }
}