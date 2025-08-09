package com.example.zoo.repository;

import com.example.zoo.model.LichChoAn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LichChoAnRepository extends JpaRepository<LichChoAn, Long> {
}
