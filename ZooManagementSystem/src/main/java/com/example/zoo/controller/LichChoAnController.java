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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.ArrayList;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    private static final Logger logger = LoggerFactory.getLogger(LichChoAnController.class);
    private static final DateTimeFormatter CUSTOM_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final LichChoAnService lichChoAnService;
    private final DongVatService dongVatService;

    public LichChoAnController(LichChoAnService lichChoAnService, DongVatService dongVatService) {
        this.lichChoAnService = lichChoAnService;
        this.dongVatService = dongVatService;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean canView(User user) {
        return user != null && (
                "admin".equalsIgnoreCase(user.getRole()) ||
                "staff".equalsIgnoreCase(user.getRole())
        );
    }

    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    // **METHOD CHUNG CHO VALIDATION THỜI GIAN**
    private boolean validateThoiGian(LichChoAn lich, BindingResult result, String thoiGianStr) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        logger.info("=== TIME VALIDATION DEBUG ===");
        logger.info("Current time: {}", now);
        logger.info("Input string: {}", thoiGianStr);
        
        // Xử lý convert từ string sang LocalDateTime
        if (thoiGianStr != null && !thoiGianStr.trim().isEmpty()) {
            try {
                LocalDateTime parsedTime = LocalDateTime.parse(thoiGianStr.trim(), CUSTOM_FORMATTER);
                lich.setThoiGian(parsedTime);
                logger.info("Parsed time: {}", parsedTime);
            } catch (DateTimeParseException e) {
                logger.error("Parse time error: ", e);
                result.rejectValue("thoiGian", "error.lich", 
                    "Định dạng thời gian không hợp lệ. Sử dụng định dạng: dd/MM/yyyy HH:mm");
                return false;
            }
        }
        
        if (lich.getThoiGian() == null) {
            lich.setThoiGian(now.plusMinutes(1));
            return true;
        }
        
        LocalDateTime tg = lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES);
        logger.info("Truncated input time: {}", tg);
        logger.info("Is after current time: {}", tg.isAfter(now));
        
        if (!tg.isAfter(now)) {
            result.rejectValue("thoiGian", "error.lich", 
                String.format("Thời gian phải lớn hơn hiện tại (hiện tại: %s, nhập: %s)", 
                now.format(CUSTOM_FORMATTER), tg.format(CUSTOM_FORMATTER)));
            return false;
        }
        
        lich.setThoiGian(tg);
        return true;
    }

    // ===== Danh sách (admin + staff xem được) =====
    @GetMapping
    public String danhSach(Model model, HttpSession session) {
        try {
            User u = (User) session.getAttribute("loggedInUser");
            if (!canView(u)) {
                return "redirect:/error/505";
            }
            
            List<LichChoAn> danhSachLich = lichChoAnService.getAll();
            model.addAttribute("danhSachLich", danhSachLich);
            model.addAttribute("role", u.getRole());
            return "lichchoan/list";
            
        } catch (Exception e) {
            logger.error("Lỗi khi lấy danh sách lịch cho ăn: ", e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải danh sách lịch cho ăn");
            model.addAttribute("danhSachLich", new ArrayList<>());
            return "lichchoan/list";
        }
    }

    // ===== Form thêm (admin) =====
    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            
            if (!model.containsAttribute("lich")) {
                model.addAttribute("lich", new LichChoAn());
            }
            
            List<DongVat> danhSachDongVat = dongVatService.layTatCa();
            model.addAttribute("danhSachDongVat", danhSachDongVat);
            
            return "lichchoan/add";
            
        } catch (Exception e) {
            logger.error("Lỗi khi hiển thị form thêm lịch cho ăn: ", e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải form thêm lịch");
            model.addAttribute("lich", new LichChoAn());
            model.addAttribute("danhSachDongVat", new ArrayList<>());
            return "lichchoan/add";
        }
    }

    // ===== Xử lý thêm (admin) =====
    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich,
                           BindingResult result,
                           @RequestParam(value = "thoiGianStr", required = false) String thoiGianStr,
                           Model model,
                           HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";

            // **FIX REGEX** - bỏ bớt backslash
            if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
                result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số (ví dụ L001)");
            } else if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
                result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
            }

            // Xử lý động vật
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

            // Set nhân viên
            User currentUser = (User) session.getAttribute("loggedInUser");
            if (currentUser != null && currentUser.getId() != null) {
                lich.setNhanVienId(currentUser.getId());
            } else {
                lich.setNhanVienId(1L);
            }

            // **SỬ DỤNG METHOD CHUNG CHO VALIDATION THỜI GIAN**
            if (!validateThoiGian(lich, result, thoiGianStr)) {
                // Validation failed
            }

            if (result.hasErrors()) {
                model.addAttribute("lich", lich);
                List<DongVat> danhSachDongVat = dongVatService.layTatCa();
                model.addAttribute("danhSachDongVat", danhSachDongVat);
                return "lichchoan/add";
            }

            lichChoAnService.themLich(lich);
            return "redirect:/lichchoan";
            
        } catch (Exception e) {
            logger.error("Lỗi khi thêm lịch cho ăn: ", e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi thêm lịch cho ăn: " + e.getMessage());
            model.addAttribute("lich", lich);
            try {
                List<DongVat> danhSachDongVat = dongVatService.layTatCa();
                model.addAttribute("danhSachDongVat", danhSachDongVat);
            } catch (Exception ex) {
                model.addAttribute("danhSachDongVat", new ArrayList<>());
            }
            return "lichchoan/add";
        }
    }

    // ===== Form sửa (admin) =====
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model, HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            
            LichChoAn lich = lichChoAnService.timTheoId(id);
            if (lich == null) {
                logger.warn("Không tìm thấy lịch cho ăn với ID: {}", id);
                return "redirect:/lichchoan";
            }
            
            model.addAttribute("lich", lich);
            List<DongVat> danhSachDongVat = dongVatService.layTatCa();
            model.addAttribute("danhSachDongVat", danhSachDongVat);
            
            return "lichchoan/edit";
            
        } catch (Exception e) {
            logger.error("Lỗi khi hiển thị form sửa lịch cho ăn với ID {}: ", id, e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi tải thông tin lịch cho ăn");
            return "redirect:/lichchoan";
        }
    }

    // ===== Xử lý sửa (admin) =====
    @PostMapping("/edit")
    public String xuLySua(@Valid @ModelAttribute("lich") LichChoAn lich,
                          BindingResult result,
                          @RequestParam(value = "thoiGianStr", required = false) String thoiGianStr,
                          Model model,
                          HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";

            // **FIX REGEX** - bỏ bớt backslash
            if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
                result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số");
            } else if (lichChoAnService.timTheoId(lich.getMaLich()) == null) {
                return "redirect:/lichchoan";
            }

            // Xử lý động vật
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

            // Set nhân viên
            User currentUser = (User) session.getAttribute("loggedInUser");
            if (currentUser != null && currentUser.getId() != null) {
                lich.setNhanVienId(currentUser.getId());
            } else {
                lich.setNhanVienId(1L);
            }

            // **SỬ DỤNG METHOD CHUNG CHO VALIDATION THỜI GIAN**
            if (!validateThoiGian(lich, result, thoiGianStr)) {
                // Validation failed
            }

            if (result.hasErrors()) {
                model.addAttribute("lich", lich);
                List<DongVat> danhSachDongVat = dongVatService.layTatCa();
                model.addAttribute("danhSachDongVat", danhSachDongVat);
                return "lichchoan/edit";
            }

            lichChoAnService.capNhatLich(lich);
            return "redirect:/lichchoan";
            
        } catch (Exception e) {
            logger.error("Lỗi khi cập nhật lịch cho ăn: ", e);
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật lịch cho ăn: " + e.getMessage());
            model.addAttribute("lich", lich);
            try {
                List<DongVat> danhSachDongVat = dongVatService.layTatCa();
                model.addAttribute("danhSachDongVat", danhSachDongVat);
            } catch (Exception ex) {
                model.addAttribute("danhSachDongVat", new ArrayList<>());
            }
            return "lichchoan/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable String id, HttpSession session) {
        try {
            if (!isAuthorized(session)) return "redirect:/error/505";
            
            lichChoAnService.xoaLich(id);
            logger.info("Đã xóa thành công lịch cho ăn với ID: {}", id);
            return "redirect:/lichchoan";
            
        } catch (Exception e) {
            logger.error("Lỗi khi xóa lịch cho ăn với ID {}: ", id, e);
            return "redirect:/lichchoan?error=delete";
        }
    }
}
