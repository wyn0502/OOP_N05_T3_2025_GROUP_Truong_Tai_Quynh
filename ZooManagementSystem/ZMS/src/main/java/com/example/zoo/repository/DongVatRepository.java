package com.example.zoo.repository;

import com.example.zoo.model.DongVat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DongVatRepository extends JpaRepository<DongVat, Long> {
    DongVat findByTen(String ten); 
    void deleteByTen(String ten);  
}