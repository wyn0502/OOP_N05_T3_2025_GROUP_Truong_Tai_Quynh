package com.example.zoo.controller;

import com.example.zoo.model.NhanVien;
import com.example.zoo.model.User;
import com.example.zoo.service.NhanVienService;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    private static final String UNAUTHORIZED_REDIRECT = "redirect:/error/505";

    private final NhanVienService nhanVienService;

    public NhanVienController(NhanVienService nhanVienService) {
        this.nhanVienService = nhanVienService;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }
    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    // ======= LIST =======
    @GetMapping
    public String hienThiDanhSach(Model model, HttpSession session) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        model.addAttribute("danhSach", nhanVienService.hienThiTatCa());
        return "nhanvien/list";
    }

    // ======= ADD =======
    @GetMapping("/add")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        model.addAttribute("user", new NhanVien());
        return "nhanvien/add";
    }

    @PostMapping("/save")
    public String luuNhanVien(@ModelAttribute("user") NhanVien nv,
                              HttpSession session,
                              RedirectAttributes ra) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        NhanVien saved = nhanVienService.them(nv);
        if (saved == null) {
            ra.addFlashAttribute("error", "Không thể thêm nhân viên.");
            return "redirect:/nhanvien?error";
        }
        ra.addFlashAttribute("success", "Thêm nhân viên thành công.");
        return "redirect:/nhanvien?success";
    }

    // ======= EDIT =======
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") Long id,
                                 Model model,
                                 HttpSession session,
                                 RedirectAttributes ra) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        NhanVien nv = nhanVienService.timTheoId(id);
        if (nv == null) {
            ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + id);
            return "redirect:/nhanvien?notfound";
        }
        model.addAttribute("user", nv);
        return "nhanvien/edit";
    }

    @PostMapping("/update")
    public String capNhatNhanVien(@ModelAttribute("user") NhanVien nv,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        if (nv.getId() == null) {
            ra.addFlashAttribute("error", "Thiếu ID nhân viên.");
            return "redirect:/nhanvien?bad_id";
        }
        NhanVien updated = nhanVienService.capNhat(nv.getId(), nv);
        if (updated == null) {
            ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + nv.getId());
            return "redirect:/nhanvien?notfound";
        }
        ra.addFlashAttribute("success", "Cập nhật nhân viên thành công.");
        return "redirect:/nhanvien?updated";
    }

    // ======= DELETE =======
    @GetMapping("/delete/{id}")
    public String xoaNhanVien(@PathVariable("id") Long id,
                              HttpSession session,
                              RedirectAttributes ra) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        nhanVienService.xoa(id);
        ra.addFlashAttribute("success", "Đã xoá nhân viên #" + id);
        return "redirect:/nhanvien?deleted";
    }

    // ======= RESET PASSWORD (FORM) =======
    @GetMapping("/reset-password/{id}")
    public String showResetPasswordForm(@PathVariable Long id,
                                        Model model,
                                        HttpSession session,
                                        RedirectAttributes ra) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;
        NhanVien nv = nhanVienService.timTheoId(id);
        if (nv == null) {
            ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + id);
            return "redirect:/nhanvien?notfound";
        }
       
        model.addAttribute("userId", id);
        return "nhanvien/reset-password";
    }

    // ======= RESET PASSWORD (SUBMIT) =======
    @PostMapping("/reset-password/{id}")
    public String doResetPassword(@PathVariable Long id,
                                  @RequestParam String newPassword,
                                  @RequestParam String confirmPassword,
                                  HttpSession session,
                                  RedirectAttributes ra) {
        if (!isAuthorized(session)) return UNAUTHORIZED_REDIRECT;

        if (newPassword == null || confirmPassword == null || !newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "Mật khẩu xác nhận không khớp.");
            return "redirect:/nhanvien/reset-password/" + id;
        }
        if (newPassword.trim().length() < 6) {
            ra.addFlashAttribute("error", "Mật khẩu phải từ 6 ký tự.");
            return "redirect:/nhanvien/reset-password/" + id;
        }

        NhanVien nv = nhanVienService.timTheoId(id);
        if (nv == null) {
            ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + id);
            return "redirect:/nhanvien?notfound";
        }

        // nv.setPassword(passwordEncoder.encode(newPassword));
        nv.setPassword(newPassword);

        NhanVien updated = nhanVienService.capNhat(id, nv);
        if (updated == null) {
            ra.addFlashAttribute("error", "Đổi mật khẩu thất bại.");
            return "redirect:/nhanvien/reset-password/" + id;
        }
        ra.addFlashAttribute("success", "Đổi mật khẩu thành công.");
        return "redirect:/nhanvien?updated";
    }

    // ======= Bắt lỗi id không phải số (ví dụ /edit/abc) =======
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "ID không hợp lệ.");
        return "redirect:/nhanvien?bad_id";
    }
}
