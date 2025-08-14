package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.User;
import com.example.zoo.repository.DongVatRepository;
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
    private final DongVatRepository dongVatRepository; // thêm repository

    public LichChoAnController(LichChoAnService lichChoAnService,
                                DongVatService dongVatService,
                                DongVatRepository dongVatRepository) {
        this.lichChoAnService = lichChoAnService;
        this.dongVatService = dongVatService;
        this.dongVatRepository = dongVatRepository;
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

    private boolean validateThoiGian(LichChoAn lich, BindingResult result, String thoiGianStr) {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if (thoiGianStr != null && !thoiGianStr.trim().isEmpty()) {
            try {
                LocalDateTime parsedTime = LocalDateTime.parse(thoiGianStr.trim(), CUSTOM_FORMATTER);
                lich.setThoiGian(parsedTime);
            } catch (DateTimeParseException e) {
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
        if (!tg.isAfter(now)) {
            result.rejectValue("thoiGian", "error.lich",
                    String.format("Thời gian phải lớn hơn hiện tại (hiện tại: %s, nhập: %s)",
                            now.format(CUSTOM_FORMATTER), tg.format(CUSTOM_FORMATTER)));
            return false;
        }
        lich.setThoiGian(tg);
        return true;
    }

    @GetMapping
    public String danhSach(Model model, HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u)) {
            return "redirect:/error/505";
        }
        List<LichChoAn> danhSachLich = lichChoAnService.getAll();
        model.addAttribute("danhSachLich", danhSachLich);
        model.addAttribute("role", u.getRole());
        return "lichchoan/list";
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        if (!model.containsAttribute("lich")) {
            model.addAttribute("lich", new LichChoAn());
        }
        List<DongVat> danhSachDongVat = dongVatService.layTatCa();
        model.addAttribute("danhSachDongVat", danhSachDongVat);
        return "lichchoan/add";
    }

    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich,
                           BindingResult result,
                           @RequestParam(value = "thoiGianStr", required = false) String thoiGianStr,
                           Model model,
                           HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
            result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số (ví dụ L001)");
        } else if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
            result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
        }

        // ==== SỬ DỤNG REPOSITORY TRỰC TIẾP ====
        if (lich.getDongVat() != null && !lich.getDongVat().trim().isEmpty()) {
            DongVat dongVat = dongVatRepository.findByTen(lich.getDongVat().trim());
            if (dongVat != null) {
                lich.setDongVatId(dongVat.getId());
            } else {
                List<DongVat> danhSachGanDung =
                        dongVatRepository.findByTenContainingIgnoreCase(lich.getDongVat().trim());
                if (!danhSachGanDung.isEmpty()) {
                    lich.setDongVatId(danhSachGanDung.get(0).getId());
                } else {
                    lich.setDongVatId(1L);
                }
            }
        } else {
            lich.setDongVatId(1L);
        }

        User currentUser = (User) session.getAttribute("loggedInUser");
        lich.setNhanVienId(currentUser != null && currentUser.getId() != null ? currentUser.getId() : 1L);

        if (!validateThoiGian(lich, result, thoiGianStr)) {
            // lỗi thời gian
        }
        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            model.addAttribute("danhSachDongVat", dongVatService.layTatCa());
            return "lichchoan/add";
        }
        lichChoAnService.themLich(lich);
        return "redirect:/lichchoan";
    }

    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        LichChoAn lich = lichChoAnService.timTheoId(id);
        if (lich == null) {
            return "redirect:/lichchoan";
        }
        model.addAttribute("lich", lich);
        model.addAttribute("danhSachDongVat", dongVatService.layTatCa());
        return "lichchoan/edit";
    }

    @PostMapping("/edit")
    public String xuLySua(@Valid @ModelAttribute("lich") LichChoAn lich,
                          BindingResult result,
                          @RequestParam(value = "thoiGianStr", required = false) String thoiGianStr,
                          Model model,
                          HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
            result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số");
        } else if (lichChoAnService.timTheoId(lich.getMaLich()) == null) {
            return "redirect:/lichchoan";
        }

        // ==== SỬ DỤNG REPOSITORY TRỰC TIẾP ====
        if (lich.getDongVat() != null && !lich.getDongVat().trim().isEmpty()) {
            DongVat dongVat = dongVatRepository.findByTen(lich.getDongVat().trim());
            if (dongVat != null) {
                lich.setDongVatId(dongVat.getId());
            } else {
                List<DongVat> danhSachGanDung =
                        dongVatRepository.findByTenContainingIgnoreCase(lich.getDongVat().trim());
                if (!danhSachGanDung.isEmpty()) {
                    lich.setDongVatId(danhSachGanDung.get(0).getId());
                } else {
                    lich.setDongVatId(1L);
                }
            }
        } else {
            lich.setDongVatId(1L);
        }

        User currentUser = (User) session.getAttribute("loggedInUser");
        lich.setNhanVienId(currentUser != null && currentUser.getId() != null ? currentUser.getId() : 1L);

        if (!validateThoiGian(lich, result, thoiGianStr)) {
            // lỗi thời gian
        }
        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            model.addAttribute("danhSachDongVat", dongVatService.layTatCa());
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
