package com.example.zoo.service;

import com.example.zoo.model.GiaVe;
import com.example.zoo.repository.GiaVeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class GiaVeService {

    @Autowired
    private GiaVeRepository giaVeRepository;

   
    public List<GiaVe> layTatCa() {
        return giaVeRepository.findAll();
    }

    
    public void luu(GiaVe ve) {
        giaVeRepository.save(ve);
    }

    
    public GiaVe timTheoId(Long id) {
        return giaVeRepository.findById(id).orElse(null);
    }

    
    public void xoaTheoId(Long id) {
        giaVeRepository.deleteById(id);
    }
    
    public List<GiaVe> timKiem(String keyword) {
    return giaVeRepository.findAll().stream()
        .filter(ve -> ve.getLoaiVe().toLowerCase().contains(keyword.toLowerCase())
                   || (ve.getLyDoGiamGia() != null && ve.getLyDoGiamGia().toLowerCase().contains(keyword.toLowerCase())))
        .collect(Collectors.toList());
}
}
