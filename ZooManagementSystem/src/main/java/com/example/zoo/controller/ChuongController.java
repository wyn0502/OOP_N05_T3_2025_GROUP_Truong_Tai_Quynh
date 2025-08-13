package com.example.zoo.controller;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.User;
import com.example.zoo.service.ChuongService;
import com.example.zoo.service.DongVatService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/chuong")
public class ChuongController {

    @Autowired
    private ChuongService service;

    @Autowired
    private DongVatService dongVatService;

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    // admin và staff đều xem được
    private boolean canView(User user) {
        return user != null && ("admin".equalsIgnoreCase(user.getRole())
                || "staff".equalsIgnoreCase(user.getRole()));
    }

    // chỉ admin
    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    @GetMapping
    public String list(Model model, HttpSession session,
            @RequestParam(value = "search", required = false) String searchId) {
        try {
            User user = (User) session.getAttribute("loggedInUser");
            if (!canView(user)) {
                return "redirect:/error/505";
            }

            if (searchId != null && !searchId.trim().isEmpty()) {
                Chuong foundChuong = service.timTheoMa(searchId.trim());
                if (foundChuong != null) {
                    model.addAttribute("danhSach", java.util.Arrays.asList(foundChuong));
                    model.addAttribute("searchResult", "Tìm thấy 1 kết quả cho mã chuồng: " + searchId);
                } else {
                    model.addAttribute("danhSach", java.util.Collections.emptyList());
                    model.addAttribute("searchResult", "Không tìm thấy chuồng với mã: " + searchId);
                }
                model.addAttribute("searchValue", searchId);
            } else {
                List<Chuong> danhSach = service.hienThi();
                model.addAttribute("danhSach", danhSach);
            }

            model.addAttribute("role", user.getRole());

            return "chuong/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi lấy danh sách: " + e.getMessage());
            return "error/general";
        }
    }

    @GetMapping("/them")
    public String themForm(Model model, HttpSession session) {
        try {
            if (!isAuthorized(session)) {
                return "redirect:/error/505";
            }
            // SỬA: Tạo chuồng mới với soLuongHienTai = 0 (không cho user nhập)
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
            if (!isAuthorized(session)) {
                return "redirect:/error/505";
            }
            Chuong c = service.timTheoMa(maChuong);
            if (c == null) {
                return "redirect:/chuong";
            }
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
            if (!isAuthorized(session)) {
                return "redirect:/error/505";
            }
            chuong.setMaChuong(sanitizeMaChuong(chuong.getMaChuong()));

            if ("add".equals(mode)) {
                // SỬA: Khi thêm mới, BUỘC soLuongHienTai = 0
                chuong.setSoLuongHienTai(0);
                
                if (service.timTheoMa(chuong.getMaChuong()) != null) {
                    model.addAttribute("chuong", chuong);
                    model.addAttribute("mode", "add");
                    model.addAttribute("error", "Mã chuồng đã tồn tại!");
                    return "chuong/form";
                }
                service.them(chuong);
            } else if ("edit".equals(mode)) {
                // SỬA: Khi edit, KHÔNG cho phép thay đổi soLuongHienTai từ form
                // Lấy giá trị hiện tại từ database
                Chuong chuongCu = service.timTheoMa(chuong.getMaChuong());
                if (chuongCu != null) {
                    chuong.setSoLuongHienTai(chuongCu.getSoLuongHienTai());
                }
                service.sua(chuong.getMaChuong(), chuong);
            }
            return "redirect:/chuong";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi lưu chuồng: " + e.getMessage());
            model.addAttribute("chuong", chuong);
            model.addAttribute("mode", mode);
            return "chuong/form";
        }
    }

    @GetMapping("/kiem-tra/{maChuong}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> kiemTraChuong(@PathVariable String maChuong) {
        Map<String, Object> result = new HashMap<>();
        try {
            var danhSachDongVat = dongVatService.layDongVatTheoMaChuong(maChuong);
            result.put("coDongVat", !danhSachDongVat.isEmpty());
            result.put("soDongVat", danhSachDongVat.size());
            result.put("tenDongVat", danhSachDongVat.stream()
                    .map(dv -> dv.getTen())
                    .collect(Collectors.toList()));
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("error", e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    @GetMapping("/xoa/{maChuong}")
    public String xoa(@PathVariable String maChuong, HttpSession session, Model model) {
        try {
            if (!isAuthorized(session)) {
                return "redirect:/error/505";
            }
            long soDongVat = dongVatService.demDongVatTheoMaChuong(maChuong);
            if (soDongVat > 0) {
                var danhSachDongVat = dongVatService.layDongVatTheoMaChuong(maChuong);
                String tenDongVat = danhSachDongVat.stream()
                        .map(dv -> dv.getTen())
                        .collect(Collectors.joining(", "));
                model.addAttribute("error",
                        String.format("Không thể xóa chuồng %s vì còn %d động vật bên trong: %s. ",
                                maChuong, soDongVat, tenDongVat));
                model.addAttribute("danhSach", service.hienThi());
                return "chuong/list";
            }
            service.xoa(maChuong);
            return "redirect:/chuong?deleted";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa chuồng: " + e.getMessage());
            return "error/general";
        }
    }

    private String sanitizeMaChuong(String maChuong) {
        if (maChuong == null) {
            return null;
        }
        return maChuong.replaceAll("^[,\\s]+", "").replaceAll("[,\\s]+$", "");
    }
}
