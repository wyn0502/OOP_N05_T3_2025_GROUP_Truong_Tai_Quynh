package com.example.zoo;

import com.example.zoo.model.DongVat;
import com.example.zoo.model.GiaVe;
import com.example.zoo.model.NhanVien;
import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepository;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GreetingController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/greeting")
    public String greeting(Model model, HttpSession session) {
        // Lấy thông tin đăng nhập từ session
        User sessionUser = (User) session.getAttribute("loggedInUser");

        if (sessionUser == null) {
            return "redirect:/login";
        }

        // Truy vấn lại từ database theo username
        User loggedInUser = userRepository.findByUsername(sessionUser.getUsername());

        if (loggedInUser == null) {
            // Trường hợp bị xóa khỏi DB sau khi login
            session.invalidate();
            return "redirect:/login";
        }

        // Thông tin người đăng nhập
        model.addAttribute("name", loggedInUser.getFullname());
        model.addAttribute("role", loggedInUser.getRole());

        // Thời gian hiện tại
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        String formattedTime = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        model.addAttribute("currentTime", formattedTime);
        model.addAttribute("timezone", "GMT+7 (Việt Nam)");

        // Thông tin động vật mẫu
        DongVat dv = new DongVat("Gà", 2, "Gà", "Khỏe mạnh");
        model.addAttribute("dongVat", dv);

        //  Giá vé mẫu
        GiaVe gv = new GiaVe("ve1612", "Vé Trẻ Em", 100000.0, "Vui hè", 50.0);
        model.addAttribute("giaVe", gv);

        //  Cập nhật dữ liệu từ SQL
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        danhSachNhanVien.add(new NhanVien(
                String.valueOf(loggedInUser.getId()),
                loggedInUser.getFullname(),
                loggedInUser.getUsername(),
                loggedInUser.getRole(),
                loggedInUser.getDatework(),
                loggedInUser.getPhone(),
                loggedInUser.getChuong()));
        model.addAttribute("danhSachNhanVien", danhSachNhanVien);

        return "greeting";
    }
}
