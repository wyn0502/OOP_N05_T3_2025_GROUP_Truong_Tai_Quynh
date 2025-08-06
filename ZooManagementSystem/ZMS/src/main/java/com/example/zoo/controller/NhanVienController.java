package com.example.zoo.controller;

import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepository;
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

    // Hiển thị danh sách nhân viên
    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("danhSach", userRepository.findAll());
        return "nhanvien/list";
    }

    // Hiển thị form thêm nhân viên
    @GetMapping("/add")
    public String hienThiFormThem(Model model) {
        model.addAttribute("user", new User());
        return "nhanvien/add";
    }

    // Lưu nhân viên mới
    @PostMapping("/save")
    public String luuNhanVien(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/nhanvien?success";
    }

    // Hiển thị form sửa thông tin nhân viên
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") Long id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "nhanvien/edit";
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    // Cập nhật thông tin nhân viên (giữ lại mật khẩu cũ nếu không thay)
    @PostMapping("/update")
    public String capNhatNhanVien(@ModelAttribute("user") User user) {
        Optional<User> optionalUser = userRepository.findById(user.getId());

        if (optionalUser.isPresent()) {
            User existingUser = optionalUser.get();

            // Giữ lại mật khẩu cũ
            user.setPassword(existingUser.getPassword());

            userRepository.save(user);
            return "redirect:/nhanvien?updated";
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    // Xoá nhân viên
    @GetMapping("/delete/{id}")
    public String xoaNhanVien(@PathVariable("id") Long id) {
        userRepository.deleteById(id);
        return "redirect:/nhanvien?deleted";
    }

    // Hiển thị form reset mật khẩu
    @GetMapping("/reset-password/{id}")
    public String hienThiFormResetMatKhau(@PathVariable("id") Long id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "nhanvien/reset-password"; // Tạo file này
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    // Xử lý cập nhật mật khẩu
    @PostMapping("/reset-password")
    public String resetMatKhau(@RequestParam("id") Long id,
                               @RequestParam("newPassword") String newPassword,
                               @RequestParam("confirmPassword") String confirmPassword) {

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
