package com.example.zoo.controller;

import com.example.zoo.model.GiaVe;
import com.example.zoo.model.User;
import com.example.zoo.service.GiaVeService;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfWriter;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/giave")
public class GiaVeController {

    @Autowired
    private GiaVeService giaVeService;

    private boolean isAdmin(User user) {
        return user != null && "admin".equalsIgnoreCase(user.getRole());
    }

    private boolean isAuthorized(HttpSession session) {
        User user = (User) session.getAttribute("loggedInUser");
        return isAdmin(user);
    }

    // ======== CRUD ========

    @GetMapping
    public String danhSachVe(@RequestParam(name = "keyword", required = false) String keyword,
                             Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        try {
            List<GiaVe> danhSachVe = giaVeService.layTatCa();
            if (keyword != null && !keyword.isBlank()) {
                danhSachVe = danhSachVe.stream()
                        .filter(ve -> ve.getLoaiVe().toLowerCase().contains(keyword.toLowerCase()))
                        .collect(Collectors.toList());
                model.addAttribute("keyword", keyword);
            }
            model.addAttribute("danhSachVe", danhSachVe);
            return "giave/list";
        } catch (Exception ex) {
            model.addAttribute("error", "Đã xảy ra lỗi khi tải danh sách vé.");
            return "error";
        }
    }

    @GetMapping("/add")
    public String hienThiFormThem(Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        model.addAttribute("ve", new GiaVe());
        return "giave/add";
    }

    @PostMapping("/add")
    public String xuLyThemVe(@Valid @ModelAttribute("ve") GiaVe ve,
                             BindingResult result,
                             Model model,
                             HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        if (result.hasErrors()) return "giave/add";
        try {
            giaVeService.luu(ve);
            return "redirect:/giave";
        } catch (Exception ex) {
            model.addAttribute("error", "Không thể lưu vé. Vui lòng thử lại!");
            return "giave/add";
        }
    }

    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        GiaVe ve = giaVeService.timTheoId(id);
        if (ve == null) return "redirect:/giave";
        model.addAttribute("ve", ve);
        return "giave/edit";
    }

    @PostMapping("/edit")
    public String xuLySuaVe(@Valid @ModelAttribute("ve") GiaVe ve,
                            BindingResult result,
                            Model model,
                            HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        if (result.hasErrors()) return "giave/edit";
        try {
            giaVeService.luu(ve);
            return "redirect:/giave";
        } catch (Exception ex) {
            model.addAttribute("error", "Không thể cập nhật vé. Vui lòng thử lại!");
            return "giave/edit";
        }
    }

    @GetMapping("/delete/{id}")
    public String xoaVe(@PathVariable("id") Long id, Model model, HttpSession session) {
        if (!isAuthorized(session)) return "redirect:/error/505";
        try {
            giaVeService.xoaTheoId(id);
            return "redirect:/giave";
        } catch (Exception ex) {
            model.addAttribute("error", "Không thể xóa vé.");
            return "error";
        }
    }

    // ======== Xem và In vé (không chặn vì dùng cho cả staff) ========

    @GetMapping("/inve/{id}")
    public String xemTruocVe(@PathVariable("id") Long id, Model model) {
        GiaVe ve = giaVeService.timTheoId(id);
        if (ve == null) return "redirect:/giave";
        model.addAttribute("ve", ve);
        return "giave/inve";
    }

    @GetMapping("/print-pdf/{id}")
    public void xuatPdf(@PathVariable("id") Long id, HttpServletResponse response) throws IOException {
        GiaVe ve = giaVeService.timTheoId(id);
        if (ve == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ve_" + ve.getId() + ".pdf");

        Document document = new Document(PageSize.A5);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();
        addVeToPdf(document, ve);
        document.close();
    }

    @PostMapping("/print-many")
    public void xuatNhieuPdf(@RequestParam("ids") List<Long> ids,
                             HttpServletResponse response) throws IOException {
        List<GiaVe> veList = giaVeService.layTatCa()
                .stream()
                .filter(ve -> ids.contains(ve.getId()))
                .collect(Collectors.toList());

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=danhsach_ve.pdf");

        Document document = new Document(PageSize.A5);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        for (GiaVe ve : veList) {
            addVeToPdf(document, ve);
            document.add(new Paragraph("---------------", new Font(Font.HELVETICA, 12, Font.ITALIC)));
        }

        document.close();
    }

    @PostMapping("/print-multiple-copies/{id}")
    public void inNhieuBan(@PathVariable("id") Long id,
                           @RequestParam(name = "soluong", defaultValue = "1") int soLuong,
                           HttpServletResponse response) throws IOException {
        GiaVe ve = giaVeService.timTheoId(id);
        if (ve == null || soLuong <= 0) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=ve_" + ve.getId() + "_x" + soLuong + ".pdf");

        Document document = new Document(PageSize.A5);
        PdfWriter.getInstance(document, response.getOutputStream());
        document.open();

        for (int i = 0; i < soLuong; i++) {
            addVeToPdf(document, ve);
            document.add(new Paragraph("---------------", new Font(Font.HELVETICA, 12, Font.ITALIC)));
        }

        document.close();
    }

    private void addVeToPdf(Document document, GiaVe ve) throws DocumentException {
        Font titleFont = new Font(Font.HELVETICA, 18, Font.BOLD, Color.BLUE);
        Font bodyFont = new Font(Font.HELVETICA, 12, Font.NORMAL, Color.BLACK);

        document.add(new Paragraph("🎟️ VÉ THAM QUAN SỞ THÚ", titleFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("🆔 Mã vé: " + ve.getId(), bodyFont));
        document.add(new Paragraph("📄 Loại vé: " + ve.getLoaiVe(), bodyFont));
        document.add(new Paragraph("💰 Giá gốc: " + ve.getGiaCoBan() + " VNĐ", bodyFont));

        if (ve.getPhanTramGiamGia() > 0) {
            document.add(new Paragraph("🏷️ Giảm giá: " + ve.getPhanTramGiamGia() + "%", bodyFont));
            document.add(new Paragraph("✅ Giá sau khuyến mãi: " + ve.apDungKhuyenMai() + " VNĐ", bodyFont));
        }

        if (ve.getLyDoGiamGia() != null && !ve.getLyDoGiamGia().isBlank()) {
            document.add(new Paragraph("📌 Lý do: " + ve.getLyDoGiamGia(), bodyFont));
        }

        document.add(new Paragraph("🕓 Ngày in: " + LocalDateTime.now(), bodyFont));
        document.add(new Paragraph(" "));
        document.add(new Paragraph("🎉 Cảm ơn quý khách đã đến với sở thú!", bodyFont));
    }
}
