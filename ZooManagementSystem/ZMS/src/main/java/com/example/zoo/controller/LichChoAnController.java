package com.example.zoo.controller;

import com.example.zoo.model.LichChoAn;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class LichChoAnController {

    @GetMapping("/")
    public String hienThiTrangChinh(Model model) {
        ArrayList<LichChoAn> danhSach = new ArrayList<>();

        danhSach.add(new LichChoAn("L01", "Hổ", "Thịt", "Nguyễn Văn A", "08:00"));
        danhSach.add(new LichChoAn("L02", "Khỉ", "Chuối", "Trần Văn B", "09:00"));

        model.addAttribute("lichchoan", danhSach);
        return "trangchinh";
    }
}
