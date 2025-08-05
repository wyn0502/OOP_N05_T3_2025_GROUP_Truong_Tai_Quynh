package com.example.zoo.controller;

import com.example.zoo.model.NhanVien;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Controller
@RequestMapping("/nhanvien")
public class NhanVienController {

    private List<NhanVien> danhSachNhanVien = new ArrayList<>();

    public NhanVienController() {
        // Dữ liệu mẫu ban đầu
        danhSachNhanVien.add(new NhanVien("NV01", "Nguyễn Văn A", "Quản lý", "10 năm kinh nghiệm", "0909123456", "Chuồng Hổ"));
        danhSachNhanVien.add(new NhanVien("NV02", "Trần Thị B", "Nhân viên", "Yêu động vật", "0912345678", "Chuồng Gấu"));
    }

    // Hiển thị danh sách nhân viên
    @GetMapping
    public String hienThiDanhSach(Model model) {
        model.addAttribute("danhSach", danhSachNhanVien);
        return "nhanvien/list";  // ==> tạo file nhanvien/list.html
    }

    // Hiển thị form thêm nhân viên
    @GetMapping("/them")
    public String hienThiFormThem(Model model) {
        model.addAttribute("nhanVien", new NhanVien("", "", "", "", "", ""));
        return "nhanvien/add";  // ==> tạo file nhanvien/add.html
    }

    // Xử lý lưu nhân viên mới
    @PostMapping("/luu")
    public String luuNhanVien(@ModelAttribute NhanVien nv) {
        danhSachNhanVien.add(nv);
        return "redirect:/nhanvien";
    }

    // Hiển thị form sửa nhân viên
    @GetMapping("/sua/{ma}")
    public String hienThiFormSua(@PathVariable("ma") String ma, Model model) {
        for (NhanVien nv : danhSachNhanVien) {
            if (nv.getMaNhanVien().equalsIgnoreCase(ma)) {
                model.addAttribute("nhanVien", nv);
                return "nhanvien/edit";  // ==> tạo file nhanvien/edit.html
            }
        }
        return "redirect:/nhanvien";
    }

    // Xử lý cập nhật thông tin nhân viên
    @PostMapping("/capnhat")
    public String capNhatNhanVien(@ModelAttribute NhanVien nvCapNhat) {
        for (int i = 0; i < danhSachNhanVien.size(); i++) {
            if (danhSachNhanVien.get(i).getMaNhanVien().equalsIgnoreCase(nvCapNhat.getMaNhanVien())) {
                danhSachNhanVien.set(i, nvCapNhat);
                break;
            }
        }
        return "redirect:/nhanvien";
    }

    // Xóa nhân viên
    @GetMapping("/xoa/{ma}")
    public String xoaNhanVien(@PathVariable("ma") String ma) {
        danhSachNhanVien.removeIf(nv -> nv.getMaNhanVien().equalsIgnoreCase(ma));
        return "redirect:/nhanvien";
    }
}
