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

    // Hiển thị danh sách động vật
    @GetMapping
    public String list(Model model) {
        model.addAttribute("danhSach", service.layTatCa());
        model.addAttribute("dongVat", new DongVat()); // Thêm object rỗng để tránh lỗi binding
        return "dongvat/list"; // Trả về dongvat/list.html
    }

    // Hiển thị form thêm động vật
    @GetMapping("/them")
    public String themForm(Model model) {
        System.out.println("Accessing themForm method"); // Debug log
        model.addAttribute("dongVat", new DongVat());
        System.out.println("Returning dongvat/form"); // Debug log
        return "dongvat/form"; // Trả về dongvat/form.html
    }

    // Xử lý lưu động vật
    @PostMapping("/luu")
    public String luu(@ModelAttribute DongVat dongVat) {
        System.out.println("Saving animal: " + dongVat); // Debug log
        service.them(dongVat);
        return "redirect:/dongvat"; // Redirect về danh sách
    }

    // Hiển thị form sửa động vật
    @GetMapping("/sua/{ten}")
    public String sua(@PathVariable String ten, Model model) {
        System.out.println("Editing animal: " + ten); // Debug log
        DongVat dv = service.timTheoTen(ten);
        if (dv == null) {
            System.out.println("Animal not found: " + ten);
            return "redirect:/dongvat"; // Nếu không tìm thấy, quay về danh sách
        }
        model.addAttribute("dongVat", dv);
        return "dongvat/form"; // Trả về form để sửa
    }

    // Xử lý xóa động vật
    @GetMapping("/xoa/{ten}")
    public String xoa(@PathVariable String ten) {
        System.out.println("Deleting animal: " + ten); // Debug log
        boolean deleted = service.xoa(ten);
        System.out.println("Delete result: " + deleted);
        return "redirect:/dongvat"; // Redirect về danh sách
    }
}