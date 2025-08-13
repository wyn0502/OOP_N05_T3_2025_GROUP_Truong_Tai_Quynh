package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.service.LichChoAnService;
import com.example.zoo.service.DongVatService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    private final LichChoAnService lichChoAnService;
    private final DongVatService dongVatService;

    public LichChoAnController(LichChoAnService lichChoAnService, DongVatService dongVatService) {
        this.lichChoAnService = lichChoAnService;
        this.dongVatService = dongVatService;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    // Cho phép admin + staff xem danh sách
    private boolean canView(User user) {
        return user != null && (
                "admin".equalsIgnoreCase(user.getRole()) ||
                "staff".equalsIgnoreCase(user.getRole())
        );
    }

    // Admin-only
    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    // ===== Danh sách (admin + staff xem được) =====
    @GetMapping
    public String danhSach(Model model, HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u)) {
            return "redirect:/error/505";
        }
        model.addAttribute("danhSachLich", lichChoAnService.getAll());
        model.addAttribute("role", u.getRole()); // truyền role sang view
        return "lichchoan/list";
    }

    // ===== Form thêm (admin) =====
    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        if (!model.containsAttribute("lich")) {
            model.addAttribute("lich", new LichChoAn());
        }
        
        // THÊM DANH SÁCH ĐỘNG VẬT VÀO MODEL
        List<DongVat> danhSachDongVat = dongVatService.layTatCa();
        model.addAttribute("danhSachDongVat", danhSachDongVat);
        
        return "lichchoan/add";
    }

    // ===== Xử lý thêm (admin) =====
    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich,
                           BindingResult result,
                           Model model,
                           HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        // Validate mã lịch
        if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
            result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số (ví dụ L001)");
        } else if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
            result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
        }

        // THÊM LOGIC TÌM ĐỘNG VẬT VÀ SET DONG_VAT_ID
        if (lich.getDongVat() != null && !lich.getDongVat().trim().isEmpty()) {
            DongVat dongVat = dongVatService.timTheoTen(lich.getDongVat().trim());
            if (dongVat != null) {
                lich.setDongVatId(dongVat.getId());
            } else {
                // Nếu không tìm thấy chính xác, thử tìm gần đúng
                List<DongVat> danhSachGanDung = dongVatService.timTheoTenGanDung(lich.getDongVat().trim());
                if (!danhSachGanDung.isEmpty()) {
                    lich.setDongVatId(danhSachGanDung.get(0).getId()); // Lấy kết quả đầu tiên
                } else {
                    // Nếu vẫn không tìm thấy, set default value
                    lich.setDongVatId(1L);
                }
            }
        } else {
            lich.setDongVatId(1L); // Set default value nếu không có tên động vật
        }

        // THÊM LOGIC SET NHAN_VIEN_ID
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser != null && currentUser.getId() != null) {
            lich.setNhanVienId(currentUser.getId()); // Lấy ID từ user hiện tại
        } else {
            lich.setNhanVienId(1L); // Default ID nếu không có user
        }

        // Validate thời gian
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
            // THÊM LẠI DANH SÁCH ĐỘNG VẬT KHI CÓ LỖI
            List<DongVat> danhSachDongVat = dongVatService.layTatCa();
            model.addAttribute("danhSachDongVat", danhSachDongVat);
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
        
        // THÊM DANH SÁCH ĐỘNG VẬT VÀO MODEL
        List<DongVat> danhSachDongVat = dongVatService.layTatCa();
        model.addAttribute("danhSachDongVat", danhSachDongVat);
        
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

        // THÊM LOGIC TÌM ĐỘNG VẬT VÀ SET DONG_VAT_ID (GIỐNG METHOD THÊM)
        if (lich.getDongVat() != null && !lich.getDongVat().trim().isEmpty()) {
            DongVat dongVat = dongVatService.timTheoTen(lich.getDongVat().trim());
            if (dongVat != null) {
                lich.setDongVatId(dongVat.getId());
            } else {
                List<DongVat> danhSachGanDung = dongVatService.timTheoTenGanDung(lich.getDongVat().trim());
                if (!danhSachGanDung.isEmpty()) {
                    lich.setDongVatId(danhSachGanDung.get(0).getId());
                } else {
                    lich.setDongVatId(1L);
                }
            }
        } else {
            lich.setDongVatId(1L);
        }

        // THÊM LOGIC SET NHAN_VIEN_ID (GIỐNG METHOD THÊM)
        User currentUser = (User) session.getAttribute("loggedInUser");
        if (currentUser != null && currentUser.getId() != null) {
            lich.setNhanVienId(currentUser.getId());
        } else {
            lich.setNhanVienId(1L);
        }

        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (lich.getThoiGian() == null || !lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES).isAfter(now)) {
            result.rejectValue("thoiGian", "error.lich", "Thời gian phải lớn hơn hiện tại (tối thiểu +1 phút).");
        } else {
            lich.setThoiGian(lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES));
        }

        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            // THÊM LẠI DANH SÁCH ĐỘNG VẬT KHI CÓ LỖI
            List<DongVat> danhSachDongVat = dongVatService.layTatCa();
            model.addAttribute("danhSachDongVat", danhSachDongVat);
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
