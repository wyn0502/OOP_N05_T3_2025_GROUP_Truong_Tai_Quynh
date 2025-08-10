package com.example.zoo.repository;

import com.example.zoo.model.LichChoAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface LichChoAnRepository extends JpaRepository<LichChoAn, String> {

    List<LichChoAn> findAllByOrderByThoiGianDesc();

    List<LichChoAn> findTop5ByOrderByThoiGianDesc();

    List<LichChoAn> findTop5ByThoiGianGreaterThanEqualOrderByThoiGianAsc(LocalDateTime now);

    List<LichChoAn> findTop5ByThoiGianLessThanOrderByThoiGianDesc(LocalDateTime now);

    boolean existsByMaLich(String maLich);
}