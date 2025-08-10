package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.model.User;
import com.example.zoo.service.LichChoAnService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    private final LichChoAnService lichChoAnService;

    public LichChoAnController(LichChoAnService lichChoAnService) {
        this.lichChoAnService = lichChoAnService;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }
    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    // ===== Danh sách (ai cũng xem được) =====
    @GetMapping
    public String danhSach(Model model) {
        model.addAttribute("danhSachLich", lichChoAnService.getAll());
        return "lichchoan/list";
    }

    // ===== Form thêm (admin) =====
    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        if (!model.containsAttribute("lich")) {
            model.addAttribute("lich", new LichChoAn());
        }
        return "lichchoan/add";
    }

    // ===== Xử lý thêm (admin) =====
    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich,
                           BindingResult result,
                           Model model,
                           HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        // Mã lịch
        if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
            result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số (ví dụ L001)");
        } else if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
            result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
        }

        // Thời gian: chuẩn hoá về phút & phải > hiện tại (tối thiểu +1 phút)
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (lich.getThoiGian() == null) {
            lich.setThoiGian(now.plusMinutes(1));
        } else {
            LocalDateTime tg = lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES);
            if (!tg.isAfter(now)) {
                result.rejectValue("thoiGian", "error.lich", "Thời gian phải lớn hơn hiện tại (tối thiểu +1 phút).");
            } else {
                lich.setThoiGian(tg);
            }
        }

        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            return "lichchoan/add";
        }

        lichChoAnService.themLich(lich);
        return "redirect:/lichchoan";
    }

    // ===== Form sửa (admin) =====
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        LichChoAn lich = lichChoAnService.timTheoId(id);
        if (lich == null) return "redirect:/lichchoan";
        model.addAttribute("lich", lich);
        return "lichchoan/edit";
    }

    // ===== Xử lý sửa (admin) =====
    @PostMapping("/edit")
    public String xuLySua(@Valid @ModelAttribute("lich") LichChoAn lich,
                          BindingResult result,
                          Model model,
                          HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
            result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số");
        } else if (lichChoAnService.timTheoId(lich.getMaLich()) == null) {
            return "redirect:/lichchoan";
        }

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (lich.getThoiGian() == null || !lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES).isAfter(now)) {
            result.rejectValue("thoiGian", "error.lich", "Thời gian phải lớn hơn hiện tại (tối thiểu +1 phút).");
        } else {
            lich.setThoiGian(lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES));
        }

        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            return "lichchoan/edit";
        }

        lichChoAnService.capNhatLich(lich);
        return "redirect:/lichchoan";
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable String id, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        lichChoAnService.xoaLich(id);
        return "redirect:/lichchoan";
    }
}
