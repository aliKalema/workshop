package com.phenomenal.workshop.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phenomenal.workshop.entity.Product;

public interface ProductRepository extends JpaRepository<Product,Integer> {
	Optional<Product>findByName(String name);
	@Query(value="SELECT  * FROM product p WHERE p.name =?",nativeQuery=true)
	Product findAllByName(String name);
}
