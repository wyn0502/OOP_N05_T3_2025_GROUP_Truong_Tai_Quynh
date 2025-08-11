package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.model.User;
import com.example.zoo.model.NhanVien;
import com.example.zoo.model.DongVat;
import com.example.zoo.service.LichChoAnService;
import com.example.zoo.service.NhanVienService;
import com.example.zoo.service.DongVatService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    private final LichChoAnService lichChoAnService;
    private final NhanVienService nhanVienService;
    private final DongVatService dongVatService;

    public LichChoAnController(LichChoAnService lichChoAnService, 
                              NhanVienService nhanVienService,
                              DongVatService dongVatService) {
        this.lichChoAnService = lichChoAnService;
        this.nhanVienService = nhanVienService;
        this.dongVatService = dongVatService;
    }

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }
    
    private boolean isAuthorized(HttpSession session) {
        User u = (User) session.getAttribute("loggedInUser");
        return isAdmin(u);
    }

    @GetMapping
    public String danhSach(Model model, HttpSession session) {
        try {
            List<LichChoAn> danhSach = lichChoAnService.getAll();
            model.addAttribute("danhSachLich", danhSach);
            
            if (session.getAttribute("successMessage") != null) {
                model.addAttribute("successMessage", session.getAttribute("successMessage"));
                session.removeAttribute("successMessage");
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi load danh sách lịch: " + e.getMessage());
            model.addAttribute("danhSachLich", List.of());
            model.addAttribute("errorMessage", "Có lỗi khi tải danh sách lịch cho ăn");
        }
        return "lichchoan/list";
    }

    @GetMapping("/them")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        
        LichChoAn lich;
        
        LichChoAn backupLich = (LichChoAn) session.getAttribute("formBackup");
        if (backupLich != null && !model.containsAttribute("lich")) {
            lich = backupLich;
            System.out.println("🔄 Restored form data from session backup");
        } else if (!model.containsAttribute("lich")) {
            lich = new LichChoAn();
            lich.setDonVi("kg");
            lich.setTrangThai("CHUA_THUC_HIEN");
        } else {
            lich = (LichChoAn) model.getAttribute("lich");
        }
        
        model.addAttribute("lich", lich);
        
        try {
            List<NhanVien> nhanVienList = nhanVienService.getAllStaffOrderByName();
            List<DongVat> dongVatList = dongVatService.getHealthyAnimalsOrderByCage();
            
            System.out.println("📊 Form data - Nhân viên: " + nhanVienList.size() + ", Động vật: " + dongVatList.size());
            
            model.addAttribute("danhSachNhanVien", nhanVienList != null ? nhanVienList : List.of());
            model.addAttribute("danhSachDongVat", dongVatList != null ? dongVatList : List.of());
            
            if (nhanVienList.isEmpty()) {
                model.addAttribute("warningMessage", "⚠️ Không có nhân viên staff nao trong hệ thống");
            }
            if (dongVatList.isEmpty()) {
                model.addAttribute("warningMessage", "⚠️ Không có động vật khỏe mạnh nào");
            }
            
        } catch (Exception e) {
            System.err.println("Lỗi khi load dữ liệu form: " + e.getMessage());
            model.addAttribute("danhSachNhanVien", List.of());
            model.addAttribute("danhSachDongVat", List.of());
            model.addAttribute("errorMessage", "Không thể tải dữ liệu nhân viên và động vật");
        }
        
        return "lichchoan/add";
    }

    // ===== DEBUG ENDPOINT ĐỂ TEST PARSING =====
    @GetMapping("/debug-time")
    @ResponseBody
    public Map<String, String> debugTime(@RequestParam String timeStr) {
        Map<String, String> result = new HashMap<>();
        
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            LocalDateTime parsed = LocalDateTime.parse(timeStr.trim(), formatter);
            
            LocalDateTime now = LocalDateTime.now();
            
            result.put("input", timeStr);
            result.put("parsed", parsed.toString());
            result.put("formatted", parsed.format(formatter));
            result.put("serverTime", now.toString());
            result.put("serverFormatted", now.format(formatter));
            result.put("isAfterNow", String.valueOf(parsed.isAfter(now)));
            result.put("diffMinutes", String.valueOf(java.time.Duration.between(now, parsed).toMinutes()));
            result.put("status", "SUCCESS");
            
        } catch (Exception e) {
            result.put("status", "ERROR");
            result.put("error", e.getMessage());
        }
        
        return result;
    }

    // ===== XỬ LÝ THÊM VỚI DATETIME VALIDATION ĐÃ FIX =====
    @PostMapping("/them")
    public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich,
                           BindingResult result,
                           Model model,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        
        if (!isAuthorized(session)) return "redirect:/error/505";

        session.setAttribute("formBackup", lich);
        
        System.out.println("=== 📝 FORM SUBMISSION DEBUG ===");
        System.out.println("Mã lịch: " + lich.getMaLich());
        System.out.println("Nhân viên ID: " + (lich.getNhanVien() != null ? lich.getNhanVien().getId() : "null"));
        System.out.println("Động vật ID: " + (lich.getDongVat() != null ? lich.getDongVat().getId() : "null"));
        System.out.println("Thức ăn: " + lich.getThucAn());
        System.out.println("Số lượng: " + lich.getSoLuong());
        System.out.println("Thời gian RAW: " + lich.getThoiGian());

        try {
            // Validate mã lịch
            if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
                result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số (ví dụ L001)");
            } else if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
                result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
            }

            // ===== FIX DATETIME VALIDATION HOÀN TOÀN =====
            LocalDateTime now = LocalDateTime.now();
            System.out.println("🕒 Server current time: " + now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));

            if (lich.getThoiGian() == null) {
                result.rejectValue("thoiGian", "error.lich", "Phải nhập thời gian");
            } else {
                LocalDateTime inputTime = lich.getThoiGian();
                System.out.println("📝 Parsed input time: " + inputTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));
                
                // GIẢM BUFFER XUỐNG 0 - CHỈ CHECK QUÁ KHỨ THỰC SỰ
                if (inputTime.isBefore(now.minusMinutes(5))) { // Cho phép sai số 5 phút
                    String currentFormatted = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    String inputFormatted = inputTime.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                    
                    result.rejectValue("thoiGian", "error.lich", 
                        String.format("Thời gian %s quá cũ. Thời gian hiện tại: %s", inputFormatted, currentFormatted));
                } else {
                    lich.setThoiGian(inputTime.truncatedTo(ChronoUnit.MINUTES));
                    System.out.println("✅ Time validation passed: " + inputTime);
                }
            }

            // Validate nhân viên
            if (lich.getNhanVien() == null || lich.getNhanVien().getId() == null || lich.getNhanVien().getId() <= 0) {
                result.rejectValue("nhanVien", "error.lich", "Phải chọn nhân viên hợp lệ");
            } else {
                NhanVien nv = nhanVienService.findById(lich.getNhanVien().getId());
                if (nv == null) {
                    result.rejectValue("nhanVien", "error.lich", "Nhân viên không tồn tại trong hệ thống");
                } else {
                    lich.setNhanVien(nv);
                    System.out.println("✅ Nhân viên hợp lệ: " + nv.getFullname());
                }
            }
            
            // Validate động vật
            if (lich.getDongVat() == null || lich.getDongVat().getId() == null || lich.getDongVat().getId() <= 0) {
                result.rejectValue("dongVat", "error.lich", "Phải chọn động vật hợp lệ");
            } else {
                DongVat dv = dongVatService.findById(lich.getDongVat().getId());
                if (dv == null) {
                    result.rejectValue("dongVat", "error.lich", "Động vật không tồn tại trong hệ thống");
                } else {
                    if (!"Tốt".equals(dv.getSucKhoe()) && !"Trung bình".equals(dv.getSucKhoe())) {
                        result.rejectValue("dongVat", "error.lich", "Chỉ có thể tạo lịch cho động vật có sức khỏe Tốt hoặc Trung bình");
                    } else {
                        lich.setDongVat(dv);
                        System.out.println("✅ Động vật hợp lệ: " + dv.getTen() + " (" + dv.getSucKhoe() + ")");
                    }
                }
            }

            // Validate thức ăn và số lượng
            if (lich.getThucAn() == null || lich.getThucAn().trim().isEmpty()) {
                result.rejectValue("thucAn", "error.lich", "Phải nhập thức ăn");
            }
            
            if (lich.getSoLuong() == null || lich.getSoLuong() <= 0) {
                result.rejectValue("soLuong", "error.lich", "Số lượng thức ăn phải lớn hơn 0");
            }

            if (result.hasErrors()) {
                System.out.println("❌ VALIDATION ERRORS:");
                result.getAllErrors().forEach(error -> {
                    System.out.println("- " + error.getDefaultMessage());
                });
                
                model.addAttribute("danhSachNhanVien", nhanVienService.getAllStaffOrderByName());
                model.addAttribute("danhSachDongVat", dongVatService.getHealthyAnimalsOrderByCage());
                model.addAttribute("lich", lich);
                return "lichchoan/add";
            }

            System.out.println("💾 Attempting to save schedule...");
            lichChoAnService.themLich(lich);
            
            session.removeAttribute("formBackup");
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "✅ Đã tạo lịch cho ăn " + lich.getMaLich() + " thành công!");
            
            System.out.println("✅ Lưu thành công lịch: " + lich.getMaLich());
            return "redirect:/lichchoan";
            
        } catch (Exception e) {
            System.err.println("🚨 Lỗi khi tạo lịch cho ăn: " + e.getMessage());
            e.printStackTrace();
            
            model.addAttribute("errorMessage", "❌ Có lỗi xảy ra khi tạo lịch cho ăn: " + e.getMessage());
            model.addAttribute("danhSachNhanVien", nhanVienService.getAllStaffOrderByName());
            model.addAttribute("danhSachDongVat", dongVatService.getHealthyAnimalsOrderByCage());
            model.addAttribute("lich", lich);
            return "lichchoan/add";
        }
    }

    @GetMapping("/api/food-suggestions/{dongVatId}")
    @ResponseBody
    public ResponseEntity<List<String>> getFoodSuggestions(@PathVariable Long dongVatId) {
        if (dongVatId == null || dongVatId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        try {
            DongVat dongVat = dongVatService.findById(dongVatId);
            if (dongVat == null) {
                return ResponseEntity.notFound().build();
            }
            
            List<String> suggestions = getFoodSuggestionsBySpecies(dongVat.getLoai());
            return ResponseEntity.ok(suggestions);
        } catch (Exception e) {
            System.err.println("Lỗi API food suggestions: " + e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    private List<String> getFoodSuggestionsBySpecies(String loai) {
        if (loai == null) return List.of("Thức ăn tổng hợp");
        
        return switch (loai.toLowerCase()) {
            case "sư tử", "hổ", "báo", "lion", "tiger", "leopard" -> 
                List.of("Thịt bò tươi", "Thịt gà", "Thịt cừu", "Xương ống");
            case "voi", "elephant" -> 
                List.of("Cỏ khô", "Trái cây tổng hợp", "Rau củ", "Chuối", "Khoai lang");
            case "khỉ", "monkey", "gorilla", "orangutan" -> 
                List.of("Trái cây tươi", "Rau xanh", "Hạt điều", "Chuối", "Táo");
            case "gấu", "bear" -> 
                List.of("Cá tươi", "Trái cây", "Mật ong", "Thịt gà", "Rau củ");
            default -> 
                List.of("Thức ăn tổng hợp", "Rau củ tươi", "Trái cây");
        };
    }

    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        
        try {
            LichChoAn lich = lichChoAnService.timTheoId(id);
            if (lich == null) {
                session.setAttribute("errorMessage", "Không tìm thấy lịch với mã " + id);
                return "redirect:/lichchoan";
            }
            
            model.addAttribute("lich", lich);
            model.addAttribute("danhSachNhanVien", nhanVienService.getAllStaffOrderByName());
            model.addAttribute("danhSachDongVat", dongVatService.getHealthyAnimalsOrderByCage());
            
        } catch (Exception e) {
            System.err.println("Lỗi khi load form edit: " + e.getMessage());
            session.setAttribute("errorMessage", "Có lỗi khi tải form chỉnh sửa");
            return "redirect:/lichchoan";
        }
        
        return "lichchoan/edit";
    }

    @PostMapping("/edit")
    public String xuLySua(@Valid @ModelAttribute("lich") LichChoAn lich,
                          BindingResult result,
                          Model model,
                          HttpSession session,
                          RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session)) return "redirect:/error/505";

        try {
            if (lich.getMaLich() == null || !lich.getMaLich().matches("^L00\\d+$")) {
                result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số");
            } else if (lichChoAnService.timTheoId(lich.getMaLich()) == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "Không tìm thấy lịch cần sửa");
                return "redirect:/lichchoan";
            }

            // Validate thời gian với logic đã fix
            LocalDateTime now = LocalDateTime.now();
            if (lich.getThoiGian() == null) {
                result.rejectValue("thoiGian", "error.lich", "Phải nhập thời gian");
            } else if (lich.getThoiGian().isBefore(now.minusMinutes(5))) {
                String currentFormatted = now.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                String inputFormatted = lich.getThoiGian().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
                result.rejectValue("thoiGian", "error.lich", 
                    String.format("Thời gian %s quá cũ. Hiện tại: %s", inputFormatted, currentFormatted));
            } else {
                lich.setThoiGian(lich.getThoiGian().truncatedTo(ChronoUnit.MINUTES));
            }

            // Validate entities tương tự như add
            if (lich.getNhanVien() == null || lich.getNhanVien().getId() == null || lich.getNhanVien().getId() <= 0) {
                result.rejectValue("nhanVien", "error.lich", "Phải chọn nhân viên hợp lệ");
            } else {
                NhanVien nv = nhanVienService.findById(lich.getNhanVien().getId());
                if (nv == null) {
                    result.rejectValue("nhanVien", "error.lich", "Nhân viên không tồn tại");
                } else {
                    lich.setNhanVien(nv);
                }
            }
            
            if (lich.getDongVat() == null || lich.getDongVat().getId() == null || lich.getDongVat().getId() <= 0) {
                result.rejectValue("dongVat", "error.lich", "Phải chọn động vật hợp lệ");
            } else {
                DongVat dv = dongVatService.findById(lich.getDongVat().getId());
                if (dv == null) {
                    result.rejectValue("dongVat", "error.lich", "Động vật không tồn tại");
                } else {
                    if (!"Tốt".equals(dv.getSucKhoe()) && !"Trung bình".equals(dv.getSucKhoe())) {
                        result.rejectValue("dongVat", "error.lich", "Chỉ có thể chọn động vật có sức khỏe Tốt hoặc Trung bình");
                    } else {
                        lich.setDongVat(dv);
                    }
                }
            }

            if (result.hasErrors()) {
                model.addAttribute("danhSachNhanVien", nhanVienService.getAllStaffOrderByName());
                model.addAttribute("danhSachDongVat", dongVatService.getHealthyAnimalsOrderByCage());
                model.addAttribute("lich", lich);
                return "lichchoan/edit";
            }

            lichChoAnService.capNhatLich(lich);
            
            redirectAttributes.addFlashAttribute("successMessage", 
                "✅ Đã cập nhật lịch " + lich.getMaLich() + " thành công!");
            
            return "redirect:/lichchoan";
            
        } catch (Exception e) {
            System.err.println("Lỗi khi cập nhật lịch: " + e.getMessage());
            model.addAttribute("errorMessage", "Có lỗi xảy ra khi cập nhật lịch");
            model.addAttribute("danhSachNhanVien", nhanVienService.getAllStaffOrderByName());
            model.addAttribute("danhSachDongVat", dongVatService.getHealthyAnimalsOrderByCage());
            return "lichchoan/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable String id, HttpSession session, RedirectAttributes redirectAttributes) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        
        try {
            LichChoAn lich = lichChoAnService.timTheoId(id);
            if (lich != null) {
                lichChoAnService.xoaLich(id);
                redirectAttributes.addFlashAttribute("successMessage", 
                    "✅ Đã xóa lịch " + id + " thành công!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "❌ Không tìm thấy lịch " + id);
            }
        } catch (Exception e) {
            System.err.println("Lỗi khi xóa lịch: " + e.getMessage());
            redirectAttributes.addFlashAttribute("errorMessage", 
                "❌ Có lỗi khi xóa lịch " + id);
        }
        
        return "redirect:/lichchoan";
    }
}
