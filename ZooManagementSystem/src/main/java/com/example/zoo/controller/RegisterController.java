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

    // ====== Precompiled patterns ======
    private static final Pattern P_LOWER   = Pattern.compile(".*[a-z].*");
    private static final Pattern P_UPPER   = Pattern.compile(".*[A-Z].*");
    private static final Pattern P_DIGIT   = Pattern.compile(".*\\d.*");
    private static final Pattern P_SPECIAL = Pattern.compile(".*[^A-Za-z0-9].*");

    public RegisterController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // === [GET] Hiển thị form đăng ký ===
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        if (!model.containsAttribute("form")) {
            RegisterForm form = new RegisterForm();
            form.setRole("staff"); // mặc định
            model.addAttribute("form", form);
        }
        return "register";
    }

    // === [POST] Xử lý form đăng ký ===
    @PostMapping("/register")
    public String processRegister(@ModelAttribute("form") RegisterForm form,
                                  RedirectAttributes redirect) {

        // Chuẩn hoá dữ liệu đầu vào
        String fullname = trim(form.getFullname());
        String username = normalizeUsername(form.getUsername());
        String password = trim(form.getPassword());
        String confirm  = trim(form.getConfirmPassword());
        String phoneRaw = trim(form.getPhone());
        String phone    = normalizePhone(phoneRaw);
        String roleIn   = trim(form.getRole());
        String invite   = trim(form.getInvite());

        // Bắt buộc
        if (isBlank(fullname) || isBlank(username) || isBlank(password)) {
            return backWithError("Vui lòng nhập đầy đủ Họ tên, Tên đăng nhập và Mật khẩu.", form, redirect);
        }

        // Xác nhận mật khẩu
        if (confirm != null && !password.equals(confirm)) {
            return backWithError("Mật khẩu xác nhận không khớp.", form, redirect);
        }

        // Kiểm tra mật khẩu mạnh
        String pwdMsg = validatePassword(password);
        if (pwdMsg != null) {
            return backWithError(pwdMsg, form, redirect);
        }

        // Username duy nhất
        if (userRepository.existsByUsername(username)) {
            return backWithError("Tên đăng nhập đã tồn tại.", form, redirect);
        }

        // SĐT hợp lệ & duy nhất (10 chữ số)
        if (isBlank(phone)) {
            return backWithError("Vui lòng nhập số điện thoại.", form, redirect);
        }
        if (phone.length() != 10 || !phone.chars().allMatch(Character::isDigit)) {
            return backWithError("Số điện thoại phải đúng 10 chữ số.", form, redirect);
        }
        if (userRepository.existsByPhone(phone)) {
            return backWithError("Số điện thoại đã được sử dụng.", form, redirect);
        }

        // Vai trò
        String role = "staff";
        if ("admin".equalsIgnoreCase(roleIn)) {
            if (isBlank(adminInvite) || isBlank(invite) || !invite.equals(adminInvite)) {
                return backWithError("Mã mời quản lý không hợp lệ.", form, redirect);
            }
            role = "admin";
        }

        // Tạo user + mã hoá BCrypt
        User user = new User();
        user.setFullname(fullname);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); // BCrypt
        user.setRole(role);
        user.setPhone(phone);
        user.setDatework(LocalDate.now());
        user.setChuong(null);

        userRepository.save(user);

        redirect.addFlashAttribute("success", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "redirect:/login";
    }

    // ===== Helpers =====

    private static String validatePassword(String pwd) {
        if (pwd == null || pwd.trim().isEmpty()) return "Mật khẩu không được để trống.";

        boolean hasLower   = P_LOWER.matcher(pwd).matches();
        boolean hasUpper   = P_UPPER.matcher(pwd).matches();
        boolean hasDigit   = P_DIGIT.matcher(pwd).matches();
        boolean hasSpecial = P_SPECIAL.matcher(pwd).matches();
        boolean hasLen     = pwd.length() >= 8;

        if (hasLower && hasUpper && hasDigit && hasSpecial && hasLen) return null;

        StringBuilder sb = new StringBuilder("Mật khẩu chưa đạt yêu cầu: cần ");
        boolean first = true;
        if (!hasLen)     { sb.append("độ dài ≥ 8"); first = false; }
        if (!hasLower)   { sb.append(first? "" : ", ").append("chữ thường"); first = false; }
        if (!hasUpper)   { sb.append(first? "" : ", ").append("chữ hoa"); first = false; }
        if (!hasDigit)   { sb.append(first? "" : ", ").append("chữ số"); first = false; }
        if (!hasSpecial) { sb.append(first? "" : ", ").append("ký tự đặc biệt"); }
        sb.append('.');
        return sb.toString();
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