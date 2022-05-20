package com.phenomenal.workshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phenomenal.workshop.entity.Stock;

public interface StockRepository extends JpaRepository<Stock,Integer> {
	

}
