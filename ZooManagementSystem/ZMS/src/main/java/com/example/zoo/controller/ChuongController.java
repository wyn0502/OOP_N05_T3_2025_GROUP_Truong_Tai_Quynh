package com.example.zoo.controller;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.User;
import com.example.zoo.service.ChuongService;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chuong")
public class ChuongController {

    @Autowired
    private ChuongService service;

    // ========== Kiểm tra quyền ==========
    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    // ========== Danh sách ==========
    @GetMapping
    public String list(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("danhSach", service.hienThi());
        return "chuong/list";
    }

    // ========== Form thêm ==========
    @GetMapping("/them")
    public String themForm(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("chuong", new Chuong("", "", 0, 0));
        return "chuong/form";
    }

    // ========== Form sửa ==========
    @GetMapping("/sua/{maChuong}")
    public String suaForm(@PathVariable String maChuong, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        Chuong c = service.hienThi().stream()
                .filter(item -> item.getMaChuong().equals(maChuong))
                .findFirst()
                .orElse(null);
        if (c == null) {
            return "redirect:/chuong";
        }
        model.addAttribute("chuong", c);
        model.addAttribute("originalMaChuong", maChuong);
        return "chuong/form";
    }

    // ========== Lưu ==========
    @PostMapping("/luu")
    public String luu(@ModelAttribute Chuong chuong,
                      @RequestParam(name = "originalMaChuong", required = false) String originalMaChuong,
                      HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        if (originalMaChuong != null && !originalMaChuong.isEmpty()) {
            service.sua(originalMaChuong, chuong);
        } else {
            boolean existed = service.hienThi().stream()
                    .anyMatch(c -> c.getMaChuong().equals(chuong.getMaChuong()));
            if (!existed) {
                service.them(chuong);
            }
        }
        return "redirect:/chuong";
    }

    // ========== Xóa ==========
    @GetMapping("/xoa/{maChuong}")
    public String xoa(@PathVariable String maChuong, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        service.xoa(maChuong);
        return "redirect:/chuong";
    }
}
