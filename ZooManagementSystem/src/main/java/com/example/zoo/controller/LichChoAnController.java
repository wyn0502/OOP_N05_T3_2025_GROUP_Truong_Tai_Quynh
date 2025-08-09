package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.model.User;
import com.example.zoo.service.LichChoAnService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    @Autowired
    private LichChoAnService lichChoAnService;

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    // Hiển thị danh sách
    @GetMapping
    public String danhSach(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        model.addAttribute("danhSachLich", lichChoAnService.getAll());
        return "lichchoan/list";
    }

    // Hiển thị form thêm
@GetMapping("/them")
public String hienThiFormThem(Model model, HttpSession session) {
    if (!isAuthorized(session)) return "redirect:/error/505";
    model.addAttribute("lich", new LichChoAn());
    return "lichchoan/add";
}

// Xử lý thêm lịch
@PostMapping("/them")
public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich,
                       BindingResult result,
                       Model model,
                       HttpSession session) {
    if (!isAuthorized(session)) return "redirect:/error/505";

    if (!lich.getMaLich().matches("^L00\\d+$")) {
        result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số");
    }

    if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
        result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
    }

    try {
        LocalDateTime thoiGian = LocalDateTime.parse(lich.getThoiGian());
        if (thoiGian.isBefore(LocalDateTime.now())) {
            result.rejectValue("thoiGian", "error.lich", "Không thể chọn thời gian đã qua");
        }
    } catch (Exception e) {
        result.rejectValue("thoiGian", "error.lich", "Thời gian không hợp lệ");
    }

    if (result.hasErrors()) {
        model.addAttribute("lich", lich);
        return "lichchoan/add";
    }

    lichChoAnService.themLich(lich);
    return "redirect:/lichchoan";
}


    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        LichChoAn lich = lichChoAnService.timTheoId(id);
        if (lich == null) return "redirect:/lichchoan";

        model.addAttribute("lich", lich);
        return "lichchoan/edit";
    }

    // Xử lý cập nhật
    @PostMapping("/edit")
    public String xuLySua(@Valid @ModelAttribute("lich") LichChoAn lich,
                          BindingResult result,
                          Model model,
                          HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        try {
            LocalDateTime thoiGian = LocalDateTime.parse(lich.getThoiGian());
            if (thoiGian.isBefore(LocalDateTime.now())) {
                result.rejectValue("thoiGian", "error.lich", "Không thể chọn thời gian đã qua");
            }
        } catch (Exception e) {
            result.rejectValue("thoiGian", "error.lich", "Thời gian không hợp lệ");
        }

        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            return "lichchoan/edit";
        }

        LichChoAn lichCu = lichChoAnService.timTheoId(lich.getMaLich());
        if (lichCu == null) return "redirect:/lichchoan";

        lichChoAnService.capNhatLich(lich);
        return "redirect:/lichchoan";
    }

    // Xóa lịch
    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable String id, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        lichChoAnService.xoaLich(id);
        return "redirect:/lichchoan";
    }
}
