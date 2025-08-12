package com.example.zoo.repository;

import com.example.zoo.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    NhanVien findByUsername(String username);

    boolean existsByUsername(String username);
}
