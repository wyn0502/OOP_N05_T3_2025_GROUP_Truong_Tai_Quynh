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
            @RequestParam(name = "chuongId",    required = false) String chuongId,     // mã chuồng dạng C001 -> String
            @RequestParam(name = "giaVeId",     required = false) Long giaVeId,
            @RequestParam(name = "lichChoAnId", required = false) String lichChoAnId,  // đổi Long -> String
            Model model,
            HttpSession session) {

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

        // 3) Động vật
        List<DongVat> danhSachDongVat = dongVatRepository.findAll();
        model.addAttribute("danhSachDongVat", danhSachDongVat);
        DongVat selectedDongVat = danhSachDongVat.isEmpty() ? null :
                (dongVatId == null
                        ? danhSachDongVat.get(0)
                        : danhSachDongVat.stream()
                            .filter(dv -> dv.getId().equals(dongVatId))
                            .findFirst()
                            .orElse(danhSachDongVat.get(0)));
        model.addAttribute("dongVat", selectedDongVat);

        // 4) Giá vé
        List<GiaVe> danhSachGiaVe = giaVeRepository.findAll();
        model.addAttribute("danhSachGiaVe", danhSachGiaVe);
        GiaVe giaVe = danhSachGiaVe.isEmpty() ? null :
                (giaVeId == null
                        ? danhSachGiaVe.get(0)
                        : danhSachGiaVe.stream()
                            .filter(v -> v.getId().equals(giaVeId))
                            .findFirst()
                            .orElse(danhSachGiaVe.get(0)));
        model.addAttribute("giaVe", giaVe);

        // 5) Nhân viên (hiển thị thông tin người đang đăng nhập)
        NhanVien nv = new NhanVien();
        nv.setId(loggedInUser.getId());
        nv.setFullname(loggedInUser.getFullname());
        nv.setUsername(loggedInUser.getUsername());
        nv.setRole(loggedInUser.getRole());
        nv.setDatework(loggedInUser.getDatework());
        nv.setPhone(loggedInUser.getPhone());
        nv.setChuong(loggedInUser.getChuong());
        model.addAttribute("danhSachNhanVien", List.of(nv));

        // 6) Chuồng
        List<Chuong> danhSachChuong = chuongRepository.findAll();
        model.addAttribute("danhSachChuong", danhSachChuong);
        Chuong chuong = danhSachChuong.isEmpty() ? null :
                (chuongId == null || chuongId.isBlank()
                        ? danhSachChuong.get(0)
                        : danhSachChuong.stream()
                            .filter(c -> c.getMaChuong().equals(chuongId))
                            .findFirst()
                            .orElse(danhSachChuong.get(0)));
        model.addAttribute("chuong", chuong);
        model.addAttribute("chuongCount", danhSachChuong.size());

        // 7) Thống kê
        model.addAttribute("dongVatCount", danhSachDongVat.size());
        model.addAttribute("loaiVeCount", danhSachGiaVe.size());
        model.addAttribute("tongNhanVien", userRepository.count());
        model.addAttribute("user", loggedInUser);

        // 8) Lịch cho ăn

        List<LichChoAn> danhSachLichChoAn = lichChoAnRepository.findAllByOrderByThoiGianDesc();
        if (danhSachLichChoAn == null || danhSachLichChoAn.isEmpty()) {
            danhSachLichChoAn = lichChoAnRepository.findAll();
            danhSachLichChoAn.sort(Comparator.comparing(LichChoAn::getThoiGian,
                    Comparator.nullsLast(Comparator.naturalOrder())).reversed());
        }
        model.addAttribute("danhSachLichChoAn", danhSachLichChoAn);
        model.addAttribute("lichChoAnCount", danhSachLichChoAn.size());

        LichChoAn lichChoAn = danhSachLichChoAn.isEmpty() ? null :
                (lichChoAnId == null || lichChoAnId.isBlank()
                        ? danhSachLichChoAn.get(0)
                        : danhSachLichChoAn.stream()
                            .filter(l -> l.getMaLich().equals(lichChoAnId))
                            .findFirst()
                            .orElse(danhSachLichChoAn.get(0)));
        model.addAttribute("lichChoAn", lichChoAn);

        
        if (lichChoAn != null && lichChoAn.getThoiGian() != null) {
            model.addAttribute("lichChoAnNgay",
                    lichChoAn.getThoiGian().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            model.addAttribute("lichChoAnGio",
                    lichChoAn.getThoiGian().format(DateTimeFormatter.ofPattern("HH:mm")));
        }

        return "greeting";
    }
}
