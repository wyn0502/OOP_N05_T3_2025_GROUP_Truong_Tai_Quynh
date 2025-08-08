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

    // Kiểm tra quyền admin
    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    @GetMapping
    public String list(Model model, HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            model.addAttribute("danhSach", service.hienThi());
            return "chuong/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi lấy danh sách: " + e.getMessage());
            return "error/general"; // Tạo mới file error/general.html nếu chưa có
        }
    }

    @GetMapping("/them")
    public String themForm(Model model, HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            model.addAttribute("chuong", new Chuong("", "", 0, 0));
            model.addAttribute("mode", "add");
            return "chuong/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hiển thị form thêm: " + e.getMessage());
            return "error/general";
        }
    }

    @GetMapping("/sua/{maChuong}")
    public String suaForm(@PathVariable String maChuong, Model model, HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            Chuong c = service.timTheoMa(maChuong);
            if (c == null) return "redirect:/chuong";
            model.addAttribute("chuong", c);
            model.addAttribute("mode", "edit");
            return "chuong/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi hiển thị form sửa: " + e.getMessage());
            return "error/general";
        }
    }

    @PostMapping("/luu")
    public String luu(@ModelAttribute Chuong chuong,
                      @RequestParam(name = "mode") String mode,
                      HttpSession session,
                      Model model) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            chuong.setMaChuong(sanitizeMaChuong(chuong.getMaChuong()));

            if ("add".equals(mode)) {
                if (service.timTheoMa(chuong.getMaChuong()) != null) {
                    model.addAttribute("chuong", chuong);
                    model.addAttribute("mode", "add");
                    model.addAttribute("error", "Mã chuồng đã tồn tại!");
                    return "chuong/form";
                }
                service.them(chuong);
            } else if ("edit".equals(mode)) {
                service.sua(chuong.getMaChuong(), chuong);
            }
            return "redirect:/chuong";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi lưu chuồng: " + e.getMessage());
            return "chuong/form";
        }
    }

    @GetMapping("/xoa/{maChuong}")
    public String xoa(@PathVariable String maChuong, HttpSession session, Model model) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            service.xoa(maChuong);
            return "redirect:/chuong";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi xóa chuồng: " + e.getMessage());
            return "error/general";
        }
    }

    // Loại bỏ dấu ',' và khoảng trắng đầu/cuối
    private String sanitizeMaChuong(String maChuong) {
        if (maChuong == null) return null;
        return maChuong.replaceAll("^[,\\s]+", "").replaceAll("[,\\s]+$", "");
    }
}
