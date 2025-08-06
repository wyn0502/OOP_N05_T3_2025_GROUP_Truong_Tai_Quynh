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

    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("danhSach", userRepository.findAll());
        return "nhanvien/list";
    }

    @GetMapping("/add")
    public String hienThiFormThem(Model model) {
        model.addAttribute("user", new User());
        return "nhanvien/add";
    }

    @PostMapping("/save")
    public String luuNhanVien(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/nhanvien?success";
    }

    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") Integer id, Model model) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            model.addAttribute("user", optionalUser.get());
            return "nhanvien/edit";
        } else {
            return "redirect:/nhanvien?notfound";
        }
    }

    @PostMapping("/update")
    public String capNhatNhanVien(@ModelAttribute("user") User user) {
        userRepository.save(user);
        return "redirect:/nhanvien?updated";
    }

    @GetMapping("/delete/{id}")
    public String xoaNhanVien(@PathVariable("id") Integer id) {
        userRepository.deleteById(id);
        return "redirect:/nhanvien?deleted";
    }
}
