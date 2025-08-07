package com.example.zoo.controller;

import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm(Model model,
                                 @RequestParam(name = "logout", required = false) String logout) {

        model.addAttribute("user", new User());

        // Nếu không có flash thì gán mặc định
        if (!model.containsAttribute("showErrorModal")) {
            model.addAttribute("showErrorModal", false);
        }

        model.addAttribute("showLogoutModal", "true".equals(logout));
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@ModelAttribute("user") User user,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {

        // Tìm người dùng theo username & password
        User found = userRepository.findByUsernameAndPassword(
                user.getUsername(), user.getPassword());

        if (found != null) {
            // ✅ Lưu toàn bộ object User vào session
            session.setAttribute("loggedInUser", found);
            return "redirect:/greeting";
        } else {
            // Gửi flash báo lỗi
            redirectAttributes.addFlashAttribute("showErrorModal", true);
            return "redirect:/login";
        }
    }

    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login?logout=true";
    }
}
