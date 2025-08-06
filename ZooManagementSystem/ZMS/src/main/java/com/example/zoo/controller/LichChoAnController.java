package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import com.example.zoo.service.LichChoAnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/lichchoan")
public class LichChoAnController {

    @Autowired
    private LichChoAnService lichChoAnService;

    // Hiển thị danh sách
    @GetMapping
    public String danhSach(Model model) {
        model.addAttribute("danhSachLich", lichChoAnService.getAll());
        return "lichchoan/list";
    }

    // Hiển thị form thêm
    @GetMapping("/add")
    public String hienThiFormThem(Model model) {
        model.addAttribute("lich", new LichChoAn());
        return "lichchoan/add";
    }

    // Xử lý thêm lịch
    @PostMapping("/add")
    public String xuLyThem(@Valid @ModelAttribute("lich") LichChoAn lich, BindingResult result, Model model) {
        // Kiểm tra định dạng mã lịch
        if (!lich.getMaLich().matches("^L00\\d+$")) {
            result.rejectValue("maLich", "error.lich", "Mã lịch phải có định dạng L00 + số");
        }

        // Kiểm tra mã lịch trùng
        if (lichChoAnService.timTheoId(lich.getMaLich()) != null) {
            result.rejectValue("maLich", "error.lich", "Mã lịch đã tồn tại");
        }

        // Kiểm tra thời gian không được ở quá khứ
        try {
            LocalDateTime thoiGian = LocalDateTime.parse(lich.getThoiGian());
            if (thoiGian.isBefore(LocalDateTime.now())) {
                result.rejectValue("thoiGian", "error.lich", "Không thể chọn thời gian đã qua");
            }
        } catch (Exception e) {
            result.rejectValue("thoiGian", "error.lich", "Thời gian không hợp lệ");
        }

        // Trả lại form nếu có lỗi
        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            return "lichchoan/add";
        }

        lichChoAnService.themLich(lich);
        return "redirect:/lichchoan";
    }

    // Hiển thị form sửa
    @GetMapping("/edit/{id}")
    public String hienThiFormSua(@PathVariable String id, Model model) {
        LichChoAn lich = lichChoAnService.timTheoId(id);
        if (lich == null) return "redirect:/lichchoan";
        model.addAttribute("lich", lich);
        return "lichchoan/edit";
    }

    // Xử lý cập nhật
    @PostMapping("/edit")
    public String xuLySua(@Valid @ModelAttribute("lich") LichChoAn lich, BindingResult result, Model model) {
        // Kiểm tra thời gian không được ở quá khứ
        try {
            LocalDateTime thoiGian = LocalDateTime.parse(lich.getThoiGian());
            if (thoiGian.isBefore(LocalDateTime.now())) {
                result.rejectValue("thoiGian", "error.lich", "Không thể chọn thời gian đã qua");
            }
        } catch (Exception e) {
            result.rejectValue("thoiGian", "error.lich", "Thời gian không hợp lệ");
        }

        // Trả lại form nếu có lỗi
        if (result.hasErrors()) {
            model.addAttribute("lich", lich);
            return "lichchoan/edit";
        }

        // Không cho đổi mã lịch → chỉ update nội dung
        LichChoAn lichCu = lichChoAnService.timTheoId(lich.getMaLich());
        if (lichCu == null) return "redirect:/lichchoan";

        lichChoAnService.capNhatLich(lich);
        return "redirect:/lichchoan";
    }

    // Xóa lịch
    @GetMapping("/delete/{id}")
    public String xoa(@PathVariable String id) {
        lichChoAnService.xoaLich(id);
        return "redirect:/lichchoan";
    }
}
