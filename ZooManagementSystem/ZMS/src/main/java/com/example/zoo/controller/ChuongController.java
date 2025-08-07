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

    // Danh sách chuồng
    @GetMapping
    public String list(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("danhSach", service.hienThi());
        return "chuong/list";
    }

    // Form thêm chuồng
    @GetMapping("/them")
    public String themForm(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("chuong", new Chuong("", "", 0, 0));
        model.addAttribute("mode", "add");
        return "chuong/form";
    }

    // Form sửa chuồng
    @GetMapping("/sua/{maChuong}")
    public String suaForm(@PathVariable String maChuong, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        Chuong c = service.timTheoMa(maChuong);
        if (c == null) return "redirect:/chuong";
        model.addAttribute("chuong", c);
        model.addAttribute("mode", "edit");
        return "chuong/form";
    }

    // Lưu chuồng (thêm mới hoặc sửa)
    @PostMapping("/luu")
    public String luu(@ModelAttribute Chuong chuong,
                      @RequestParam(name = "mode") String mode,
                      HttpSession session,
                      Model model) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        // Làm sạch mã chuồng
        chuong.setMaChuong(sanitizeMaChuong(chuong.getMaChuong()));

        if ("add".equals(mode)) {
            // Chỉ thêm nếu chưa tồn tại mã này
            if (service.timTheoMa(chuong.getMaChuong()) != null) {
                model.addAttribute("chuong", chuong);
                model.addAttribute("mode", "add");
                model.addAttribute("error", "Mã chuồng đã tồn tại!");
                return "chuong/form";
            }
            service.them(chuong);
        } else if ("edit".equals(mode)) {
            // Sửa, giữ nguyên mã chuồng
            service.sua(chuong.getMaChuong(), chuong);
        }
        return "redirect:/chuong";
    }

    // Xóa chuồng
    @GetMapping("/xoa/{maChuong}")
    public String xoa(@PathVariable String maChuong, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        service.xoa(maChuong);
        return "redirect:/chuong";
    }

    // Loại bỏ dấu ',' và khoảng trắng đầu/cuối
    private String sanitizeMaChuong(String maChuong) {
        if (maChuong == null) return null;
        return maChuong.replaceAll("^[,\\s]+", "").replaceAll("[,\\s]+$", "");
    }
}
