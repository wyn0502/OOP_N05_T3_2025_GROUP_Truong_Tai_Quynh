package com.example.zoo.repository;

import com.example.zoo.model.NhanVien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NhanVienRepository extends JpaRepository<NhanVien, Long> {
    
    NhanVien findByUsername(String username);

   
    List<NhanVien> findByFullnameContainingIgnoreCase(String fullname);
    List<NhanVien> findByIdOrUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(
    Long id, String username, String fullname);

  
    
    List<NhanVien> findByUsernameContainingIgnoreCaseOrFullnameContainingIgnoreCase(String username, String fullname);

    boolean existsByUsername(String username);
}