package com.phenomenal.workshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.phenomenal.workshop.entity.Sale;

public interface SaleRepository extends JpaRepository<Sale,Integer> {
	@Query(value ="SELECT * FROM sale  WHERE pickedup = 0", nativeQuery=true)
	List<Sale> findAllByPicked();
	
	@Query(value="select * from sale  WHERE MONTH(order_date) = MONTH(CURRENT_DATE()) AND YEAR(order_date) = YEAR(CURRENT_DATE()) AND DAY(order_date) =  DAY(CURRENT_DATE()) ",nativeQuery=true)
	List<Sale> findAllByCurrentDate();
}