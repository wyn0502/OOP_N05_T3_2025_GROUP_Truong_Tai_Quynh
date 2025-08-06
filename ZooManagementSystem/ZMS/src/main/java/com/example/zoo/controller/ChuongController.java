package com.example.zoo.controller;

import com.example.zoo.model.Chuong;
import com.example.zoo.service.ChuongService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/chuong")
public class ChuongController {

    @Autowired
    private ChuongService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("danhSach", service.hienThi());
        return "chuong/list";
    }

    @GetMapping("/them")
    public String themForm(Model model) {
        model.addAttribute("chuong", new Chuong("", "", 0, 0));
        return "chuong/form";
    }

    @GetMapping("/sua/{maChuong}")
    public String suaForm(@PathVariable String maChuong, Model model) {
        Chuong c = service.hienThi().stream()
                .filter(item -> item.getMaChuong().equals(maChuong))
                .findFirst()
                .orElse(null);
        if (c == null) {
            return "redirect:/chuong";
        }
        model.addAttribute("chuong", c);
        model.addAttribute("originalMaChuong", maChuong);
        return "chuong/form";
    }

    @PostMapping("/luu")
    public String luu(@ModelAttribute Chuong chuong,
                      @RequestParam(name = "originalMaChuong", required = false) String originalMaChuong) {
        if (originalMaChuong != null && !originalMaChuong.isEmpty()) {
            service.sua(originalMaChuong, chuong);
        } else {
            boolean existed = service.hienThi().stream()
                    .anyMatch(c -> c.getMaChuong().equals(chuong.getMaChuong()));
            if (!existed) {
                service.them(chuong);
            }
        }
        return "redirect:/chuong";
    }

    @GetMapping("/xoa/{maChuong}")
    public String xoa(@PathVariable String maChuong) {
        service.xoa(maChuong);
        return "redirect:/chuong";
    }
}
