package com.phenomenal.workshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.phenomenal.workshop.entity.CustomerOrders;



public interface OrderRepository extends JpaRepository<CustomerOrders,Integer> {
	Optional<CustomerOrders>findByName(String name);
}
