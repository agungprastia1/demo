package com.oneIndonesia.demo.repository;

import com.oneIndonesia.demo.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {
    @Query(value = "SELECT * FROM `category` a WHERE a.category like %?1% or \"All\" like %?2% ;",nativeQuery = true)
    Optional<Category> getByCategory(String category);
}
