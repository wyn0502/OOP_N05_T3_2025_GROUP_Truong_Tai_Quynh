package com.example.zoo.controller;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.Chuong;
import com.example.zoo.model.User;
import com.example.zoo.service.DongVatService;
import com.example.zoo.service.ChuongService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/dongvat")
public class DongVatController {

    @Autowired
    private DongVatService service;

    @Autowired
    private ChuongService chuongService;

    // Kiểm tra quyền admin
    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    @GetMapping
    public String hienThiDanhSach(Model model, HttpSession session,
            @RequestParam(value = "search", required = false) String searchId) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            if (searchId != null && !searchId.trim().isEmpty()) {
                try {
                    Long id = Long.parseLong(searchId.trim());
                    DongVat foundDongVat = service.timTheoId(id);
                    if (foundDongVat != null) {
                        model.addAttribute("danhSach", java.util.Arrays.asList(foundDongVat));
                        model.addAttribute("searchResult", "Tìm thấy 1 kết quả cho ID: " + searchId);
                    } else {
                        model.addAttribute("danhSach", java.util.Collections.emptyList());
                        model.addAttribute("searchResult", "Không tìm thấy động vật với ID: " + searchId);
                    }
                    model.addAttribute("searchValue", searchId);
                } catch (NumberFormatException e) {
                    model.addAttribute("danhSach", java.util.Collections.emptyList());
                    model.addAttribute("searchResult", "ID phải là một số nguyên hợp lệ");
                    model.addAttribute("searchValue", searchId);
                }
            } else {
                model.addAttribute("danhSach", service.layTatCa());
            }
            return "dongvat/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tải danh sách động vật: " + e.getMessage());
            return "dongvat/list";
        }
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            model.addAttribute("dongVat", new DongVat());
            
            List<Chuong> chuongCoChoTrong = chuongService.layChuongCoChoTrong();
            model.addAttribute("danhSachChuong", chuongCoChoTrong);
            
            if (chuongCoChoTrong.isEmpty()) {
                model.addAttribute("warning", "Hiện tại không có chuồng nào còn chỗ trống! Vui lòng tạo chuồng mới hoặc mở rộng sức chứa chuồng hiện có.");
            }
            
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tạo mới động vật: " + e.getMessage());
            return "dongvat/form";
        }
    }

    @GetMapping("/sua/{id}")
    public String hienThiFormSua(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            DongVat dv = service.timTheoId(id);
            if (dv == null) {
                model.addAttribute("error", "Không tìm thấy động vật.");
                return "redirect:/dongvat?notfound";
            }
            
            model.addAttribute("dongVat", dv);
            
            List<Chuong> chuongCoChoTrong = chuongService.layChuongCoChoTrong();
            Chuong chuongHienTai = chuongService.timTheoMa(dv.getMaChuong());
            
            if (chuongHienTai != null && 
                chuongCoChoTrong.stream().noneMatch(c -> c.getMaChuong().equals(chuongHienTai.getMaChuong()))) {
                chuongCoChoTrong.add(chuongHienTai);
            }
            
            model.addAttribute("danhSachChuong", chuongCoChoTrong);
            
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi tải thông tin động vật: " + e.getMessage());
            return "dongvat/form";
        }
    }

    // API kiểm tra sức chứa chuồng
    @GetMapping("/kiem-tra-chuong/{maChuong}")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> kiemTraSucChuaChuong(
            @PathVariable String maChuong,
            @RequestParam(required = false) Long dongVatId) {
        Map<String, Object> result = new HashMap<>();
        
        try {
            Chuong chuong = chuongService.timTheoMa(maChuong);
            if (chuong == null) {
                result.put("error", "Chuồng không tồn tại");
                return ResponseEntity.badRequest().body(result);
            }
            
            boolean coChoTrong = service.kiemTraSucChua(maChuong, dongVatId);
            long soDongVatHienTai = service.demDongVatTheoMaChuong(maChuong);
            
            result.put("coChoTrong", coChoTrong);
            result.put("soDongVatHienTai", soDongVatHienTai);
            result.put("sucChuaToiDa", chuong.getSucChuaToiDa());
            result.put("message", coChoTrong ? 
                "Chuồng còn chỗ trống" : 
                "Chuồng đã đầy (" + soDongVatHienTai + "/" + chuong.getSucChuaToiDa() + ")");
            
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            result.put("error", e.getMessage());
            return ResponseEntity.status(500).body(result);
        }
    }

    // Lưu động vật (thêm/sửa)
    @PostMapping("/luu")
    public String xuLyLuu(@ModelAttribute DongVat dongVat, Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            service.luuHoacCapNhat(dongVat);
            return "redirect:/dongvat?success";
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("dongVat", dongVat);
            
            if (dongVat.getId() == null) {
                List<Chuong> chuongCoChoTrong = chuongService.layChuongCoChoTrong();
                model.addAttribute("danhSachChuong", chuongCoChoTrong);
            } else {
                List<Chuong> chuongCoChoTrong = chuongService.layChuongCoChoTrong();
                Chuong chuongHienTai = chuongService.timTheoMa(dongVat.getMaChuong());
                if (chuongHienTai != null && 
                    chuongCoChoTrong.stream().noneMatch(c -> c.getMaChuong().equals(chuongHienTai.getMaChuong()))) {
                    chuongCoChoTrong.add(chuongHienTai);
                }
                model.addAttribute("danhSachChuong", chuongCoChoTrong);
            }
            
            return "dongvat/form";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể lưu động vật. Vui lòng thử lại! " + e.getMessage());
            model.addAttribute("dongVat", dongVat);
            return "dongvat/form";
        }
    }

    @GetMapping("/xoa/{id}")
    public String xoaDongVat(@PathVariable Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) {
            return "redirect:/error/505";
        }
        try {
            service.xoaTheoId(id);
            return "redirect:/dongvat?deleted";
        } catch (Exception e) {
            model.addAttribute("error", "Không thể xóa động vật: " + e.getMessage());
            return "dongvat/list";
        }
    }
}
