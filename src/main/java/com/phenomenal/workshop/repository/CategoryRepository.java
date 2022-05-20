package com.phenomenal.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phenomenal.workshop.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Integer> {
	@Query(value = "Select c.product_id From Category c Where c.name = ?", nativeQuery = true)
	List<Integer>findAllProductIdByName(String name);
}
