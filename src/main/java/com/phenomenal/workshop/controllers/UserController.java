package com.phenomenal.workshop.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.phenomenal.workshop.entity.Product;
import com.phenomenal.workshop.repository.CategoryRepository;
import com.phenomenal.workshop.repository.ProductRepository;

@RestController
@RequestMapping("/api/")
public class UserController {
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@GetMapping("/admin/products")
	public List<Product> getProducts() {
		return productRepository.findAll();
	}
	
	@GetMapping("/admin/products/category/{categoryName}")
	public List<Product> getProductsByCategory(@PathVariable String categoryName){
		return null;
	}

}
