package com.example.zoo.repository;

import com.example.zoo.model.Chuong;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChuongRepository extends JpaRepository<Chuong, String> {
}
