package com.example.zoo.controller;

import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private UserRepository userRepository;

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    // Danh sách nhân viên
    @GetMapping
    public String hienThiDanhSach(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        model.addAttribute("danhSach", userRepository.findAll());
        return "nhanvien/list";
    }

    // Hiển thị form thêm nhân viên
    @GetMapping("/add")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        model.addAttribute("user", new User());
        return "nhanvien/add";
    }

    // Lưu nhân viên mới
    @PostMapping("/save")
    public String luuNhanVien(@ModelAttribute("user") User user, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        userRepository.save(user);
        return "redirect:/nhanvien?success";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "nhanvien/edit";
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    // Cập nhật thông tin nhân viên
    @PostMapping("/update")
    public String capNhatNhanVien(@ModelAttribute("user") User user, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        Optional<User> optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();
            user.setPassword(existingUser.getPassword()); // giữ lại mật khẩu cũ
            userRepository.save(user);
            return "redirect:/nhanvien?updated";
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    // Xoá nhân viên
    @GetMapping("/delete/{id}")
    public String xoaNhanVien(@PathVariable("id") Long id, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        userRepository.deleteById(id);
        return "redirect:/nhanvien?deleted";
    }

    // Hiển thị form reset mật khẩu
    @GetMapping("/reset-password/{id}")
    public String hienThiFormResetMatKhau(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "nhanvien/reset-password";
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    // Xử lý cập nhật mật khẩu
    @PostMapping("/reset-password")
    public String resetMatKhau(@RequestParam("id") Long id,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("confirmPassword") String confirmPassword,
                               HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        if (!newPassword.equals(confirmPassword)) {
            return "redirect:/nhanvien?password_mismatch";
        }

        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return "redirect:/nhanvien?password_updated";
        }

        return "redirect:/nhanvien?notfound";
    }
}
