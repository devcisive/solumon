package com.example.solumonbackend.post.repository;

import com.example.solumonbackend.post.entity.Recommend;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecommendRepository extends JpaRepository<Recommend, Long> {
}
