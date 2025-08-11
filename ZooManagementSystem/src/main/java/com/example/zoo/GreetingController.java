package com.example.zoo;

import com.example.zoo.model.*;
import com.example.zoo.repository.*;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Controller
public class GreetingController {

    private final UserRepository userRepository;
    private final DongVatRepository dongVatRepository;
    private final GiaVeRepository giaVeRepository;
    private final ChuongRepository chuongRepository;
    private final LichChoAnRepository lichChoAnRepository;

    public GreetingController(UserRepository userRepository,
                              DongVatRepository dongVatRepository,
                              GiaVeRepository giaVeRepository,
                              ChuongRepository chuongRepository,
                              LichChoAnRepository lichChoAnRepository) {
        this.userRepository = userRepository;
        this.dongVatRepository = dongVatRepository;
        this.giaVeRepository = giaVeRepository;
        this.chuongRepository = chuongRepository;
        this.lichChoAnRepository = lichChoAnRepository;
    }

    @GetMapping("/greeting")
    public String greeting(
            @RequestParam(name = "dongVatId",   required = false) Long dongVatId,
            @RequestParam(name = "chuongId",    required = false) String chuongId,
            @RequestParam(name = "giaVeId",     required = false) Long giaVeId,
            @RequestParam(name = "lichChoAnId", required = false) String lichChoAnId,
            Model model,
            HttpSession session) {

        try {
            // 1) Bắt buộc đăng nhập
            User sessionUser = (User) session.getAttribute("loggedInUser");
            if (sessionUser == null) return "redirect:/login";
            User loggedInUser = userRepository.findByUsername(sessionUser.getUsername());
            if (loggedInUser == null) {
                session.invalidate();
                return "redirect:/login";
            }

            // 2) Thông tin user + đồng hồ
            model.addAttribute("name", loggedInUser.getFullname());
            model.addAttribute("role", loggedInUser.getRole());
            ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
            model.addAttribute("currentTime",
                    now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
            model.addAttribute("timezone", "GMT+7 (Việt Nam)");

            // 3) Động vật với error handling
            List<DongVat> danhSachDongVat = new ArrayList<>();
            try {
                danhSachDongVat = dongVatRepository.findAll();
                if (danhSachDongVat == null) danhSachDongVat = new ArrayList<>();
            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi load động vật: " + e.getMessage());
                danhSachDongVat = new ArrayList<>();
            }
            
            model.addAttribute("danhSachDongVat", danhSachDongVat);
            DongVat selectedDongVat = null;
            if (!danhSachDongVat.isEmpty()) {
                selectedDongVat = (dongVatId == null || dongVatId <= 0)
                        ? danhSachDongVat.get(0)
                        : danhSachDongVat.stream()
                            .filter(dv -> dv.getId() != null && dv.getId().equals(dongVatId))
                            .findFirst()
                            .orElse(danhSachDongVat.get(0));
            }
            model.addAttribute("dongVat", selectedDongVat);

            // 4) Giá vé với error handling
            List<GiaVe> danhSachGiaVe = new ArrayList<>();
            try {
                danhSachGiaVe = giaVeRepository.findAll();
                if (danhSachGiaVe == null) danhSachGiaVe = new ArrayList<>();
            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi load giá vé: " + e.getMessage());
                danhSachGiaVe = new ArrayList<>();
            }
            
            model.addAttribute("danhSachGiaVe", danhSachGiaVe);
            GiaVe giaVe = null;
            if (!danhSachGiaVe.isEmpty()) {
                giaVe = (giaVeId == null || giaVeId <= 0)
                        ? danhSachGiaVe.get(0)
                        : danhSachGiaVe.stream()
                            .filter(v -> v.getId() != null && v.getId().equals(giaVeId))
                            .findFirst()
                            .orElse(danhSachGiaVe.get(0));
            }
            model.addAttribute("giaVe", giaVe);

            // 5) Nhân viên
            NhanVien nv = new NhanVien();
            nv.setId(loggedInUser.getId());
            nv.setFullname(loggedInUser.getFullname());
            nv.setUsername(loggedInUser.getUsername());
            nv.setRole(loggedInUser.getRole());
            nv.setDatework(loggedInUser.getDatework());
            nv.setPhone(loggedInUser.getPhone());
            nv.setChuong(loggedInUser.getChuong());
            model.addAttribute("danhSachNhanVien", List.of(nv));

            // 6) Chuồng với error handling
            List<Chuong> danhSachChuong = new ArrayList<>();
            try {
                danhSachChuong = chuongRepository.findAll();
                if (danhSachChuong == null) danhSachChuong = new ArrayList<>();
            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi load chuồng: " + e.getMessage());
                danhSachChuong = new ArrayList<>();
            }
            
            model.addAttribute("danhSachChuong", danhSachChuong);
            Chuong chuong = null;
            if (!danhSachChuong.isEmpty()) {
                chuong = (chuongId == null || chuongId.isBlank())
                        ? danhSachChuong.get(0)
                        : danhSachChuong.stream()
                            .filter(c -> c.getMaChuong() != null && c.getMaChuong().equals(chuongId))
                            .findFirst()
                            .orElse(danhSachChuong.get(0));
            }
            model.addAttribute("chuong", chuong);
            model.addAttribute("chuongCount", danhSachChuong.size());

            // 7) Thống kê
            model.addAttribute("dongVatCount", danhSachDongVat.size());
            model.addAttribute("loaiVeCount", danhSachGiaVe.size());
            
            long tongNhanVien = 0;
            try {
                tongNhanVien = userRepository.count();
            } catch (Exception e) {
                System.err.println("⚠️ Lỗi khi đếm nhân viên: " + e.getMessage());
            }
            model.addAttribute("tongNhanVien", tongNhanVien);
            model.addAttribute("user", loggedInUser);

            // ===== 8) LỊCH CHO ĂN - SỬA LỖI VỚI SAFE QUERIES =====
            List<LichChoAn> danhSachLichChoAn = new ArrayList<>();
            try {
                // Sử dụng safe query để tránh lỗi JPA
                List<LichChoAn> rawList = lichChoAnRepository.findAllSafeByOrderByThoiGianDesc();
                
                if (rawList == null || rawList.isEmpty()) {
                    // Fallback: thử method thông thường nhưng với filtering
                    rawList = lichChoAnRepository.findAll();
                    if (rawList != null) {
                        rawList = rawList.stream()
                            .filter(this::isValidLichChoAn)
                            .collect(Collectors.toList());
                        rawList.sort(Comparator.comparing(LichChoAn::getThoiGian,
                                Comparator.nullsLast(Comparator.naturalOrder())).reversed());
                    }
                }

                // Filter out các bản ghi không hợp lệ
                if (rawList != null) {
                    danhSachLichChoAn = rawList.stream()
                        .filter(this::isValidLichChoAn)
                        .collect(Collectors.toList());
                    
                    System.out.println("🔍 Dashboard: Đã filter từ " + rawList.size() + " xuống " + danhSachLichChoAn.size() + " lịch hợp lệ");
                }
                
            } catch (Exception e) {
                System.err.println("🚨 LỖI CHÍNH - Không thể load lịch cho ăn trong dashboard: " + e.getMessage());
                e.printStackTrace();
                danhSachLichChoAn = new ArrayList<>();
                model.addAttribute("lichChoAnError", "Có lỗi khi tải lịch cho ăn. Vui lòng liên hệ admin để kiểm tra dữ liệu.");
            }

            model.addAttribute("danhSachLichChoAn", danhSachLichChoAn);
            model.addAttribute("lichChoAnCount", danhSachLichChoAn.size());

            // Chọn lịch cho ăn hiện tại
            LichChoAn lichChoAn = null;
            if (!danhSachLichChoAn.isEmpty()) {
                lichChoAn = (lichChoAnId == null || lichChoAnId.isBlank())
                        ? danhSachLichChoAn.get(0)
                        : danhSachLichChoAn.stream()
                            .filter(l -> l.getMaLich() != null && l.getMaLich().equals(lichChoAnId))
                            .findFirst()
                            .orElse(danhSachLichChoAn.get(0));
            }
            model.addAttribute("lichChoAn", lichChoAn);

            // Format thời gian an toàn
            if (lichChoAn != null && lichChoAn.getThoiGian() != null) {
                try {
                    model.addAttribute("lichChoAnNgay",
                            lichChoAn.getThoiGian().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                    model.addAttribute("lichChoAnGio",
                            lichChoAn.getThoiGian().format(DateTimeFormatter.ofPattern("HH:mm")));
                } catch (Exception e) {
                    System.err.println("⚠️ Lỗi format thời gian: " + e.getMessage());
                    model.addAttribute("lichChoAnNgay", "N/A");
                    model.addAttribute("lichChoAnGio", "N/A");
                }
            }

        } catch (Exception globalException) {
            System.err.println("🚨 LỖI TỔNG QUÁT trong GreetingController: " + globalException.getMessage());
            globalException.printStackTrace();
            
            // Set default values
            model.addAttribute("name", "Unknown User");
            model.addAttribute("role", "guest");
            model.addAttribute("danhSachDongVat", new ArrayList<>());
            model.addAttribute("danhSachGiaVe", new ArrayList<>());
            model.addAttribute("danhSachChuong", new ArrayList<>());
            model.addAttribute("danhSachLichChoAn", new ArrayList<>());
            model.addAttribute("dongVatCount", 0);
            model.addAttribute("loaiVeCount", 0);
            model.addAttribute("chuongCount", 0);
            model.addAttribute("lichChoAnCount", 0);
            model.addAttribute("tongNhanVien", 0);
            model.addAttribute("generalError", "Có lỗi hệ thống. Vui lòng thử lại sau.");
        }

        return "greeting";
    }

    /**
     * Kiểm tra tính hợp lệ của LichChoAn entity
     */
    private boolean isValidLichChoAn(LichChoAn lich) {
        try {
            if (lich == null) return false;
            
            if (lich.getDongVat() == null) {
                System.err.println("⚠️ LichChoAn " + lich.getMaLich() + " có DongVat null");
                return false;
            }
            
            if (lich.getDongVat().getId() == null || lich.getDongVat().getId() <= 0) {
                System.err.println("⚠️ LichChoAn " + lich.getMaLich() + " có DongVat ID không hợp lệ: " + lich.getDongVat().getId());
                return false;
            }
            
            if (lich.getNhanVien() == null) {
                System.err.println("⚠️ LichChoAn " + lich.getMaLich() + " có NhanVien null");
                return false;
            }
            
            if (lich.getNhanVien().getId() == null || lich.getNhanVien().getId() <= 0) {
                System.err.println("⚠️ LichChoAn " + lich.getMaLich() + " có NhanVien ID không hợp lệ: " + lich.getNhanVien().getId());
                return false;
            }
            
            return true;
            
        } catch (Exception e) {
            System.err.println("⚠️ Lỗi khi validate LichChoAn: " + e.getMessage());
            return false;
        }
    }
}
