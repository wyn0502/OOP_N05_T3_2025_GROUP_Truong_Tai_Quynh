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

import java.util.ArrayList;
import java.util.List;

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

    private boolean canView(User user) {
        return user != null && ("admin".equalsIgnoreCase(user.getRole()) ||
                "staff".equalsIgnoreCase(user.getRole()));
    }

    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    // Tìm ID nhân viên từ username của User
    private Long resolveSelfNhanVienId(User user) {
        if (user == null || user.getUsername() == null)
            return null;
        try {
            NhanVien nhanVien = nhanVienService.timTheoUsername(user.getUsername());
            return nhanVien != null ? nhanVien.getId() : null;
        } catch (Exception e) {
            System.err.println("Lỗi tìm nhân viên theo username: " + e.getMessage());
            return null;
        }
    }

    // Kiểm tra staff có được truy cập record này không
    private boolean canAccessRecord(User user, Long targetNhanVienId) {
        if (user == null)
            return false;
        if (isAdmin(user))
            return true; // admin truy cập tất cả

        // Staff chỉ truy cập chính mình
        Long selfId = resolveSelfNhanVienId(user);
        return selfId != null && selfId.equals(targetNhanVienId);
    }

    // ======= LIST =======
    @GetMapping
    public String hienThiDanhSach(Model model, HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u))
            return UNAUTHORIZED_REDIRECT;

        if (isAdmin(u)) {
            // Admin: xem tất cả
            model.addAttribute("danhSach", nhanVienService.hienThiTatCa());
        } else {
            // Staff: chỉ xem chính mình
            Long selfId = resolveSelfNhanVienId(u);
            if (selfId == null) {
                model.addAttribute("danhSach", java.util.Collections.emptyList());
                model.addAttribute("warning", "Không tìm thấy hồ sơ nhân viên của bạn.");
            } else {
                NhanVien myself = nhanVienService.timTheoId(selfId);
                if (myself == null) {
                    model.addAttribute("danhSach", java.util.Collections.emptyList());
                    model.addAttribute("warning", "Không tìm thấy hồ sơ nhân viên của bạn.");
                } else {
                    model.addAttribute("danhSach", java.util.List.of(myself));
                }
            }
        }

        model.addAttribute("role", u.getRole()); // Truyền role cho view
        return "nhanvien/list";
    }

    // ======= SEARCH NHÂN VIÊN (admin: tất cả, staff: chỉ chính mình) =======
    @GetMapping("/search")
public String timKiemNhanVien(@RequestParam String type,
                              @RequestParam String keyword,
                              Model model,
                              HttpSession session) {
    User u = (User) session.getAttribute("loggedInUser");
    if (!canView(u)) return UNAUTHORIZED_REDIRECT;

    List<NhanVien> ketQua = new ArrayList<>(); // ✅ Chỉ khai báo 1 lần

    if ("id".equals(type)) {
        try {
            Long id = Long.parseLong(keyword);
            NhanVien nv = nhanVienService.timTheoId(id);
            if (nv != null) {
                ketQua.add(nv);
            }
        } catch (NumberFormatException e) {
            // Ghi log hoặc bỏ qua
        }
    } else if ("username".equals(type)) {
        NhanVien nv = nhanVienService.timTheoUsername(keyword);
        if (nv != null) {
            ketQua.add(nv);
        }
    } else if ("fullname".equals(type)) {
        ketQua = nhanVienService.timTheoFullname(keyword); // ✅ Gán lại, không khai báo lại
    }

    model.addAttribute("danhSach", ketQua);
    model.addAttribute("keyword", keyword);
    model.addAttribute("searchType", type);
    model.addAttribute("role", u.getRole());

    return "nhanvien/list";
}

    // ======= ADD (chỉ admin) =======
    @GetMapping("/add")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session))
            return UNAUTHORIZED_REDIRECT;
        model.addAttribute("user", new NhanVien());
        return "nhanvien/add";
    }

    @PostMapping("/save")
    public String luuNhanVien(@ModelAttribute("user") NhanVien nv,
            HttpSession session,
            RedirectAttributes ra) {
        if (!isAuthorized(session))
            return UNAUTHORIZED_REDIRECT;
        try {
            NhanVien saved = nhanVienService.them(nv);
            if (saved == null) {
                ra.addFlashAttribute("error", "Không thể thêm nhân viên.");
                return "redirect:/nhanvien?error";
            }
            ra.addFlashAttribute("success", "Thêm nhân viên thành công.");
            return "redirect:/nhanvien?success";
        } catch (IllegalArgumentException e) {
            ra.addFlashAttribute("error", e.getMessage());
            return "redirect:/nhanvien/add";
        }
    }

    // ======= EDIT (admin: tất cả, staff: chỉ chính mình) =======
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") Long id,
            Model model,
            HttpSession session,
            RedirectAttributes ra) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u))
            return UNAUTHORIZED_REDIRECT;

        // Kiểm tra quyền truy cập record này
        if (!canAccessRecord(u, id)) {
            ra.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa thông tin nhân viên #" + id);
            return "redirect:/nhanvien?forbidden";
        }

        NhanVien nv = nhanVienService.timTheoId(id);
        if (nv == null) {
            ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + id);
            return "redirect:/nhanvien?notfound";
        }

        model.addAttribute("user", nv);
        model.addAttribute("isStaffSelfEdit", !isAdmin(u)); // Để view biết là staff đang sửa chính mình
        return "nhanvien/edit";
    }

    @GetMapping("/nhanvien")
public String danhSachNhanVien(@RequestParam(value = "search", required = false) String keyword, Model model) {
    List<NhanVien> danhSach;
    if (keyword != null && !keyword.isBlank()) {
        danhSach = nhanVienService.timKiem(keyword); // phải có hàm tìm kiếm
    } else {
        danhSach = nhanVienService.hienThiTatCa();
    }
    model.addAttribute("danhSachNhanVien", danhSach);
    return "nhanvien/list"; // tên file html hiển thị danh sách
}

    @PostMapping("/update")
    public String capNhatNhanVien(@ModelAttribute("user") NhanVien nv,
            HttpSession session,
            RedirectAttributes ra) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u))
            return UNAUTHORIZED_REDIRECT;

        if (nv.getId() == null) {
            ra.addFlashAttribute("error", "Thiếu ID nhân viên.");
            return "redirect:/nhanvien?bad_id";
        }

        // Kiểm tra quyền truy cập record này
        if (!canAccessRecord(u, nv.getId())) {
            ra.addFlashAttribute("error", "Bạn không có quyền chỉnh sửa thông tin nhân viên #" + nv.getId());
            return "redirect:/nhanvien?forbidden";
        }

        try {
            // Nếu là staff, chỉ cho phép sửa một số field nhất định
            if (!isAdmin(u)) {
                NhanVien existing = nhanVienService.timTheoId(nv.getId());
                if (existing != null) {
                    // Staff chỉ được sửa: fullname, phone, password (nếu có)
                    // Không được sửa: username, role, datework, chuong
                    nv.setUsername(existing.getUsername()); // giữ nguyên
                    nv.setRole(existing.getRole()); // giữ nguyên
                    nv.setDatework(existing.getDatework()); // giữ nguyên
                    nv.setChuong(existing.getChuong()); // giữ nguyên

                    // Nếu không nhập password mới thì giữ nguyên password cũ
                    if (nv.getPassword() == null || nv.getPassword().trim().isEmpty()) {
                        nv.setPassword(existing.getPassword());
                    }
                }
            }

            NhanVien updated = nhanVienService.capNhat(nv.getId(), nv);
            if (updated == null) {
                ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + nv.getId());
                return "redirect:/nhanvien?notfound";
            }

            ra.addFlashAttribute("success", "Cập nhật thông tin thành công.");
            return "redirect:/nhanvien?updated";
        } catch (Exception e) {
            ra.addFlashAttribute("error", "Lỗi cập nhật: " + e.getMessage());
            return "redirect:/nhanvien/edit/" + nv.getId();
        }
    }

    // ======= DELETE (chỉ admin) =======
    @GetMapping("/delete/{id}")
    public String xoaNhanVien(@PathVariable("id") Long id,
            HttpSession session,
            RedirectAttributes ra) {
        if (!isAuthorized(session))
            return UNAUTHORIZED_REDIRECT;
        nhanVienService.xoa(id);
        ra.addFlashAttribute("success", "Đã xoá nhân viên #" + id);
        return "redirect:/nhanvien?deleted";
    }

    // ======= RESET PASSWORD (admin: tất cả, staff: chỉ chính mình) =======
    @GetMapping("/reset-password/{id}")
    public String showResetPasswordForm(@PathVariable Long id,
            Model model,
            HttpSession session,
            RedirectAttributes ra) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u))
            return UNAUTHORIZED_REDIRECT;

        // Kiểm tra quyền truy cập record này
        if (!canAccessRecord(u, id)) {
            ra.addFlashAttribute("error", "Bạn không có quyền thao tác với nhân viên #" + id);
            return "redirect:/nhanvien?forbidden";
        }

        NhanVien nv = nhanVienService.timTheoId(id);
        if (nv == null) {
            ra.addFlashAttribute("warning", "Không tìm thấy nhân viên #" + id);
            return "redirect:/nhanvien?notfound";
        }

        model.addAttribute("userId", id);
        return "nhanvien/reset-password";
    }

    @PostMapping("/reset-password/{id}")
    public String doResetPassword(@PathVariable Long id,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            HttpSession session,
            RedirectAttributes ra) {
        User u = (User) session.getAttribute("loggedInUser");
        if (!canView(u))
            return UNAUTHORIZED_REDIRECT;

        // Kiểm tra quyền truy cập record này
        if (!canAccessRecord(u, id)) {
            ra.addFlashAttribute("error", "Bạn không có quyền thao tác với nhân viên #" + id);
            return "redirect:/nhanvien?forbidden";
        }

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

        nv.setPassword(newPassword);

        NhanVien updated = nhanVienService.capNhat(id, nv);
        if (updated == null) {
            ra.addFlashAttribute("error", "Đổi mật khẩu thất bại.");
            return "redirect:/nhanvien/reset-password/" + id;
        }
        ra.addFlashAttribute("success", "Đổi mật khẩu thành công.");
        return "redirect:/nhanvien?updated";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleTypeMismatch(RedirectAttributes ra) {
        ra.addFlashAttribute("error", "ID không hợp lệ.");
        return "redirect:/nhanvien?bad_id";
    }
}
