package com.example.zoo.controller;

import com.example.zoo.model.RegisterForm;
import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
public class RegisterController {

    private final UserRepository userRepository;

    // ví dụ cấu hình: app.admin.invite-code=ADMIN-2025
    @Value("${app.admin.invite-code:}")
    private String adminInvite;

    public RegisterController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("form")) {
            RegisterForm f = new RegisterForm();
            f.setRole("staff"); // mặc định staff
            model.addAttribute("form", f);
        }
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("form") RegisterForm f,
                                  RedirectAttributes ra) {

        // ===== Validate cơ bản =====
        if (isBlank(f.getFullname()) || isBlank(f.getUsername()) || isBlank(f.getPassword())) {
            ra.addFlashAttribute("error", "Vui lòng nhập Họ tên, Tên đăng nhập và Mật khẩu.");
            ra.addFlashAttribute("form", f);
            return "redirect:/register";
        }
        if (!isBlank(f.getConfirmPassword()) && !f.getPassword().equals(f.getConfirmPassword())) {
            ra.addFlashAttribute("error", "Mật khẩu xác nhận không khớp.");
            ra.addFlashAttribute("form", f);
            return "redirect:/register";
        }
        if (userRepository.existsByUsername(safe(f.getUsername()))) {
            ra.addFlashAttribute("error", "Tên đăng nhập đã tồn tại.");
            ra.addFlashAttribute("form", f);
            return "redirect:/register";
        }

        // ===== Xác định role an toàn =====
        String role = "staff";
        if ("admin".equalsIgnoreCase(safe(f.getRole()))
                && !isBlank(adminInvite)
                && adminInvite.equals(safe(f.getInvite()))) {
            role = "admin";
        }

        // ===== Lưu User (map đầy đủ các cột bảng `users`) =====
        User u = new User();
        u.setFullname(safe(f.getFullname()));        
        u.setUsername(safe(f.getUsername()));
        u.setPassword(safe(f.getPassword()));        
        u.setRole(role);
        u.setPhone(safe(f.getPhone()));
        u.setChuong(null);                           // chưa phân công khi đăng ký
        u.setDatework(LocalDate.now());              // ngày vào làm = ngày đăng ký

        userRepository.save(u);

        ra.addFlashAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    // ===== Helpers =====
    private static String safe(String s) { return s == null ? null : s.trim(); }
    private static boolean isBlank(String s) { return s == null || s.trim().isEmpty(); }
}
