package com.example.zoo;

import com.example.zoo.model.*;
import com.example.zoo.repository.*;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class GreetingController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DongVatRepository dongVatRepository;

    @Autowired
    private GiaVeRepository giaVeRepository;

    @GetMapping("/greeting")
    public String greeting(@RequestParam(name = "dongVatId", required = false) Long dongVatId,
                           Model model,
                           HttpSession session) {

        // ===== 1. Kiểm tra đăng nhập =====
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) return "redirect:/login";

        User loggedInUser = userRepository.findByUsername(sessionUser.getUsername());
        if (loggedInUser == null) {
            session.invalidate();
            return "redirect:/login";
        }

        // ===== 2. Thông tin cá nhân =====
        model.addAttribute("name", loggedInUser.getFullname());
        model.addAttribute("role", loggedInUser.getRole());

        // ===== 3. Thời gian hiện tại =====
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        model.addAttribute("currentTime", now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        model.addAttribute("timezone", "GMT+7 (Việt Nam)");

        // ===== 4. Danh sách động vật =====
        List<DongVat> danhSachDongVat = dongVatRepository.findAll();
        model.addAttribute("danhSachDongVat", danhSachDongVat);

        DongVat selectedDongVat = null;
        if (!danhSachDongVat.isEmpty()) {
            selectedDongVat = (dongVatId != null)
                    ? danhSachDongVat.stream().filter(dv -> dv.getId().equals(dongVatId)).findFirst().orElse(danhSachDongVat.get(0))
                    : danhSachDongVat.get(0);
        }
        model.addAttribute("dongVat", selectedDongVat);

        // ===== 5. Danh sách giá vé =====
        List<GiaVe> danhSachGiaVe = giaVeRepository.findAll();
        model.addAttribute("danhSachGiaVe", danhSachGiaVe);

        // Giá vé mặc định (dùng cho phần hiển thị mẫu nếu có)
        GiaVe giaVeMacDinh = danhSachGiaVe.stream().findFirst().orElse(null);
        model.addAttribute("giaVe", giaVeMacDinh);

        // ===== 6. Nhân viên hiện tại =====
        NhanVien nhanVien = new NhanVien(
                String.valueOf(loggedInUser.getId()),
                loggedInUser.getFullname(),
                loggedInUser.getUsername(),
                loggedInUser.getRole(),
                loggedInUser.getDatework(),
                loggedInUser.getPhone(),
                loggedInUser.getChuong()
        );
        model.addAttribute("danhSachNhanVien", List.of(nhanVien));

        // ===== 7. Thống kê =====
        model.addAttribute("dongVatCount", danhSachDongVat.size());
        model.addAttribute("loaiVeCount", danhSachGiaVe.size());


       
        long tongNhanVien = userRepository.count(); 
        model.addAttribute("tongNhanVien", tongNhanVien);

        model.addAttribute("user", loggedInUser);

        return "greeting";
    }
}
