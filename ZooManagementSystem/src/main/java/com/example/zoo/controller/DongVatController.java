package com.example.zoo.controller;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.DongVatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/dongvat")
public class DongVatController {

    @Autowired
    private DongVatService service;

    // Kiểm tra quyền admin
    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    // Hiển thị danh sách
    @GetMapping
    public String hienThiDanhSach(Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            model.addAttribute("danhSach", service.layTatCa());
            return "dongvat/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tải danh sách động vật: " + e.getMessage());
            return "dongvat/list";
        }
    }

    // Form thêm động vật
    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            model.addAttribute("dongVat", new DongVat());
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tạo mới động vật: " + e.getMessage());
            return "dongvat/form";
        }
    }

    // Form sửa động vật
    @GetMapping("/sua/{id}")
    public String hienThiFormSua(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            DongVat dv = service.timTheoId(id);
            if (dv == null) {
                model.addAttribute("error", "Không tìm thấy động vật.");
                return "redirect:/dongvat?notfound";
            }
            model.addAttribute("dongVat", dv);
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tải thông tin động vật: " + e.getMessage());
            return "dongvat/form";
        }
    }

    // Lưu động vật (thêm/sửa)
    @PostMapping("/luu")
    public String xuLyLuu(@ModelAttribute DongVat dongVat, Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            service.luuHoacCapNhat(dongVat);
            return "redirect:/dongvat?success";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể lưu động vật. Vui lòng thử lại! " + e.getMessage());
            model.addAttribute("dongVat", dongVat);
            return "dongvat/form";
        }
    }

    // Xóa động vật
    @GetMapping("/xoa/{id}")
    public String xoaDongVat(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            service.xoaTheoId(id);
            return "redirect:/dongvat?deleted";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa động vật: " + e.getMessage());
            return "dongvat/list";
        }
    }
}
