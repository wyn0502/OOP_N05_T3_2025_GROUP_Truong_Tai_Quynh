package com.example.zoo.controller;

import com.example.zoo.model.RegisterForm;
import com.example.zoo.model.User;
import com.example.zoo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.Locale;
import java.util.regex.Pattern;

@Controller
public class RegisterController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${app.admin.invite-code:}")
    private String adminInvite;

    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final Pattern P_LOWER   = Pattern.compile(".*[a-z].*");
    private static final Pattern P_UPPER   = Pattern.compile(".*[A-Z].*");
    private static final Pattern P_DIGIT   = Pattern.compile(".*\\d.*");
    private static final Pattern P_SPECIAL = Pattern.compile(".*[^A-Za-z0-9].*");

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("form")) {
            RegisterForm form = new RegisterForm();
            form.setRole("staff");
            model.addAttribute("form", form);
        }
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("form") RegisterForm form,
                                  RedirectAttributes redirect) {

        String fullname = trim(form.getFullname());
        String username = normalizeUsername(form.getUsername());
        String password = trim(form.getPassword());
        String confirm  = trim(form.getConfirmPassword());
        String phoneRaw = trim(form.getPhone());
        String phone    = normalizePhone(phoneRaw);
        String roleIn   = trim(form.getRole());
        String invite   = trim(form.getInvite());

        if (isBlank(fullname) || isBlank(username) || isBlank(password)) {
            return backWithError("Vui lòng nhập đầy đủ Họ tên, Tên đăng nhập và Mật khẩu.", form, redirect);
        }

        if (confirm != null && !password.equals(confirm)) {
            return backWithError("Mật khẩu xác nhận không khớp.", form, redirect);
        }

        if (userRepository.existsByUsername(username)) {
            return backWithError("Tên đăng nhập đã tồn tại.", form, redirect);
        }

        if (isBlank(phone)) {
            return backWithError("Vui lòng nhập số điện thoại.", form, redirect);
        }

        if (phone.length() != 10 || !phone.chars().allMatch(Character::isDigit)) {
            return backWithError("Số điện thoại phải đúng 10 chữ số.", form, redirect);
        }

        if (userRepository.existsByPhone(phone)) {
            return backWithError("Số điện thoại đã được sử dụng.", form, redirect);
        }

        String role = "staff";
        if ("admin".equalsIgnoreCase(roleIn)) {
            if (isBlank(adminInvite) || isBlank(invite) || !invite.equals(adminInvite)) {
                return backWithError("Mã mời quản lý không hợp lệ.", form, redirect);
            }
            role = "admin";
        }


        int strength = getPasswordStrength(password);
        if (strength <= 2) {
            redirect.addFlashAttribute("warning", "Mật khẩu của bạn khá yếu. Vui lòng cân nhắc sử dụng mật khẩu mạnh hơn.");
        }

        User user = new User();
        user.setFullname(fullname);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(role);
        user.setPhone(phone);
        user.setDatework(LocalDate.now());
        user.setChuong(null);

        userRepository.save(user);

        redirect.addFlashAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    private static int getPasswordStrength(String pwd) {
        if (pwd == null) return 0;
        int score = 0;
        if (pwd.length() >= 8) score++;
        if (P_LOWER.matcher(pwd).matches()) score++;
        if (P_UPPER.matcher(pwd).matches()) score++;
        if (P_DIGIT.matcher(pwd).matches()) score++;
        if (P_SPECIAL.matcher(pwd).matches()) score++;
        return score;
    }

    private static String normalizeUsername(String s) {
        return s == null ? null : s.trim().toLowerCase(Locale.ROOT);
    }

    private static String normalizePhone(String s) {
        if (s == null) return null;
        return s.replaceAll("\\D", "");
    }

    private static String backWithError(String msg, RegisterForm form, RedirectAttributes ra) {
        ra.addFlashAttribute("error", msg);
        ra.addFlashAttribute("form", form);
        return "redirect:/register";
    }

    private static String trim(String s) {
        return s == null ? null : s.trim();
    }

    private static boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}