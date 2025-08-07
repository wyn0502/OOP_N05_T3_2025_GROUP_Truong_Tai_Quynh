package com.example.zoo;

import com.example.zoo.model.Chuong;
import com.example.zoo.model.DongVat;
import com.example.zoo.model.GiaVe;
import com.example.zoo.model.NhanVien;
import com.example.zoo.model.User;
import com.example.zoo.repository.ChuongRepository;
import com.example.zoo.repository.DongVatRepository;
import com.example.zoo.repository.GiaVeRepository;
import com.example.zoo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DongVatRepository dongVatRepository;
    @Autowired
    private GiaVeRepository giaVeRepository;
    @Autowired
    private ChuongRepository chuongRepository;

    @GetMapping("/greeting")
    public String greeting(
        @RequestParam(name = "dongVatId", required = false) Long dongVatId,
        @RequestParam(name = "chuongId", required = false) String chuongId,
        @RequestParam(name = "giaVeId", required = false) Long giaVeId, // THÊM VÀO ĐÂY
        Model model,
        HttpSession session) {

        // 1. Kiểm tra đăng nhập
        User sessionUser = (User) session.getAttribute("loggedInUser");
        if (sessionUser == null) return "redirect:/login";
        User loggedInUser = userRepository.findByUsername(sessionUser.getUsername());
        if (loggedInUser == null) {
            session.invalidate();
            return "redirect:/login";
        }

        // 2. Thông tin cá nhân
        model.addAttribute("name", loggedInUser.getFullname());
        model.addAttribute("role", loggedInUser.getRole());

        // 3. Thời gian hiện tại
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        model.addAttribute("currentTime", now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
        model.addAttribute("timezone", "GMT+7 (Việt Nam)");

        // 4. Danh sách động vật
        List<DongVat> danhSachDongVat = dongVatRepository.findAll();
        model.addAttribute("danhSachDongVat", danhSachDongVat);

        DongVat selectedDongVat = null;
        if (!danhSachDongVat.isEmpty()) {
            selectedDongVat = (dongVatId != null)
                ? danhSachDongVat.stream().filter(dv -> dv.getId().equals(dongVatId)).findFirst().orElse(danhSachDongVat.get(0))
                : danhSachDongVat.get(0);
        }
        model.addAttribute("dongVat", selectedDongVat);

        // 5. Danh sách giá vé & lấy đúng giaVe theo giaVeId
        List<GiaVe> danhSachGiaVe = giaVeRepository.findAll();
        model.addAttribute("danhSachGiaVe", danhSachGiaVe);

        GiaVe giaVe = null;
        if (giaVeId != null) {
            // Nếu đã chọn trong select, lấy đúng vé đó
            giaVe = danhSachGiaVe.stream()
                .filter(v -> v.getId().equals(giaVeId))
                .findFirst()
                .orElse(danhSachGiaVe.isEmpty() ? null : danhSachGiaVe.get(0));
        } else if (!danhSachGiaVe.isEmpty()) {
            giaVe = danhSachGiaVe.get(0); // mặc định lấy vé đầu tiên
        }
        model.addAttribute("giaVe", giaVe);

        // 6. Nhân viên hiện tại
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

        // 7. Thống kê số lượng động vật và vé
        model.addAttribute("dongVatCount", danhSachDongVat.size());
        model.addAttribute("loaiVeCount", danhSachGiaVe.size());

        // 8. Danh sách chuồng và chuồng được chọn
        List<Chuong> danhSachChuong = chuongRepository.findAll();
        model.addAttribute("danhSachChuong", danhSachChuong);

        Chuong chuong = null;
        if (chuongId != null && !danhSachChuong.isEmpty()) {
            chuong = danhSachChuong.stream()
                .filter(c -> c.getMaChuong().equals(chuongId))
                .findFirst()
                .orElse(danhSachChuong.get(0));
        } else if (!danhSachChuong.isEmpty()) {
            chuong = danhSachChuong.get(0);
        }
        model.addAttribute("chuong", chuong);
        model.addAttribute("chuongCount", danhSachChuong.size());

        // 9. Thống kê tổng nhân viên
        long tongNhanVien = userRepository.count();
        model.addAttribute("tongNhanVien", tongNhanVien);

        // 10. Biến user
        model.addAttribute("user", loggedInUser);

        // 11. (Nếu cần) Thêm các thống kê/biến khác

        return "greeting";  // tên template
    }
}
