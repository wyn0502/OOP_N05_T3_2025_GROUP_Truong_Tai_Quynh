package com.example.zoo.controller;

import com.example.zoo.model.NhanVien;
import com.example.zoo.model.User; // để lấy user trong session và check role
import com.example.zoo.service.NhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    @Autowired
    private NhanVienService nhanVienService;

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    // ===== Danh sách nhân viên =====
    @GetMapping
    public String hienThiDanhSach(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("danhSach", nhanVienService.hienThiTatCa());
        return "nhanvien/list";
    }

    // ===== Hiển thị form thêm =====
    @GetMapping("/add")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("user", new NhanVien()); // giữ tên attribute "user" để khớp template hiện có
        return "nhanvien/add";
    }

    // ===== Lưu nhân viên mới =====
    @PostMapping("/save")
    public String luuNhanVien(@ModelAttribute("user") NhanVien nv, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        // Ép vai trò chỉ là staff bên service
        nhanVienService.them(nv);
        return "redirect:/nhanvien?success";
    }

    // ===== Hiển thị form sửa =====
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") String id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        NhanVien nv = nhanVienService.timTheoId(id);
        if (nv == null) return "redirect:/nhanvien?notfound";
        model.addAttribute("user", nv);
        return "nhanvien/edit";
    }

    // ===== Cập nhật thông tin =====
    @PostMapping("/update")
    public String capNhatNhanVien(@ModelAttribute("user") NhanVien nv, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        NhanVien updated = nhanVienService.capNhat(nv.getId(), nv);
        return (updated != null) ? "redirect:/nhanvien?updated" : "redirect:/nhanvien?notfound";
    }

    // ===== Xoá nhân viên =====
    @GetMapping("/delete/{id}")
    public String xoaNhanVien(@PathVariable("id") String id, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        nhanVienService.xoa(id);
        return "redirect:/nhanvien?deleted";
    }

   
}
