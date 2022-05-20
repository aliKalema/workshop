package com.phenomenal.workshop.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phenomenal.workshop.entity.CommonCategories;
import com.phenomenal.workshop.entity.Inventory;
import com.phenomenal.workshop.entity.Product;
import com.phenomenal.workshop.entity.Role;
import com.phenomenal.workshop.entity.User;
import com.phenomenal.workshop.repository.CategoryRepository;
import com.phenomenal.workshop.repository.ProductRepository;
import com.phenomenal.workshop.repository.UserRepository;

@RestController
public class ManagementController {
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@GetMapping("/manager/products")
	public List<Product> getProducts(){
		return productRepository.findAll();
	}
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	@GetMapping("/manager/category/quantity")
	public CommonCategories getCategoryQuantity() {
		int interior =0;
		int exterior=0;
		int oils=0;
		int performance=0;
		int replacement=0;
		int tools=0;
		int tires=0;
		int motocycle=0;
		List<Integer>interior_ids;
		List<Integer>exterior_ids;
		List<Integer>oils_ids;
		List<Integer>performance_ids;
		List<Integer>replacement_ids;
		List<Integer>tools_ids;
		List<Integer>tires_ids;
		List<Integer>motocycle_ids;
		CommonCategories cc = new CommonCategories();
		try {
		 interior_ids = categoryRepository.findAllProductIdByName("interior");
		 if(interior_ids.size() >0) {
		   for(int i =0;i<interior_ids.size();i++) {
			 Product product = productRepository.findById(interior_ids.get(i)).get();
			 interior = interior + product.getQuantity();
		   }
		 }
		} catch(Exception e) {
			e.printStackTrace();
		 }
		
		try {
		  exterior_ids = categoryRepository.findAllProductIdByName("exterior");
		  if(exterior_ids.size() >0) {
		    for(int i =0;i<exterior_ids.size();i++) {
			  Product product = productRepository.findById(exterior_ids.get(i)).get();
			  exterior = exterior + product.getQuantity();
		    }
		  }
		}catch(Exception e) {
			 e.printStackTrace(); 
		}
		try { 
		 oils_ids = categoryRepository.findAllProductIdByName("oils");
		 if(oils_ids.size() >0) {
		   for(int i =0;i<oils_ids.size();i++) {
			 Product product = productRepository.findById(oils_ids.get(i)).get();
			 oils = oils + product.getQuantity();
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		performance_ids = categoryRepository.findAllProductIdByName("performance");
		if(performance_ids.size() >0) {
		for(int i =0;i<performance_ids.size();i++) {
			Product product = productRepository.findById(performance_ids.get(i)).get();
			performance = performance + product.getQuantity();
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try{
		replacement_ids = categoryRepository.findAllProductIdByName("replacement");
		if(replacement_ids.size() >0) {
		for(int i =0;i<replacement_ids.size();i++) {
			Product product = productRepository.findById(replacement_ids.get(i)).get();
			replacement = replacement + product.getQuantity();
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try{
		tools_ids = categoryRepository.findAllProductIdByName("tools");
		if(tools_ids.size() >0) {
		for(int i =0;i<tools_ids.size();i++) {
			Product product = productRepository.findById(tools_ids.get(i)).get();
			tools = tools + product.getQuantity();
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
		tires_ids = categoryRepository.findAllProductIdByName("tires");
		if(tires_ids.size() >0) {
		for(int i =0;i<tires_ids.size();i++) {
			Product product = productRepository.findById(tires_ids.get(i)).get();
			tires = tires + product.getQuantity();
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
        motocycle_ids = categoryRepository.findAllProductIdByName("motocycle");
		if(motocycle_ids.size() >0) {
		for(int i =0;i<motocycle_ids.size();i++) {
			Product product = productRepository.findById(motocycle_ids.get(i)).get();
			motocycle = motocycle + product.getQuantity();
		}
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		cc.setExterior(exterior);
		cc.setInterior(interior);
		cc.setMotocycle(motocycle);
		cc.setOils(oils);
		cc.setPerformance(performance);
		cc.setReplacement(replacement);
		cc.setTires(tires);
		cc.setTools(tools);
		return cc;
	}
	
	@GetMapping ("/manager/product/{productName}")
	public Product getProduct(@PathVariable String productName) {
		return productRepository.findByName(productName).get();
	}
	
	@PostMapping("/manager/products/add")
	public String addProducts(@RequestBody Product product) {
		System.out.println("request reached server");
		Optional<Product> productOptional = productRepository.findByName(product.getName());
		if(productOptional.isEmpty()) {
			productRepository.save(product);
			return "product saved successfully";
		}
		else if(productOptional.isPresent()) {
			return "product already exist!";
		}
		else {
			return "An Error Occurred";
		}
				
	}

	@PostMapping("/manager/user/addm")
	public String addmanager(@RequestBody User user) {
		Optional<User> userExist = userRepository.findByUsername(user.getUsername());
		if(userExist.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			List<Role>roles =new ArrayList<>();
			Role r1 = new Role();
			r1.setRole_name("MANAGER");
		    roles.add(r1);
		    Role r2 = new Role();
			r2.setRole_name("ADMIN");
		    roles.add(r2);
		    user.setRoles(roles);
			userRepository.save(user);
			return user.getUsername() +": added successfully";
		}
		else if(userExist.isPresent()) {
			return "username already exist! change username";
		}
		else {
			return "there was an error";
		}
	}
	@PostMapping("/manager/user/add/uss/")
	public String addUser(@RequestBody User user) {
		Optional<User> userExist = userRepository.findByUsername(user.getUsername());
		if(userExist.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			List<Role>roles =new ArrayList<>();
			Role r1 = new Role();
			r1.setRole_name("USER");
		    roles.add(r1);
		    user.setRoles(roles);
			userRepository.save(user);
			return user.getUsername() +": added successfully";
		}
		else if(userExist.isPresent()) {
			return "username already exist! change username";
		}
		else {
			return "there was an error";
		}
	}
	 
	@PostMapping("/manager/user/add/usd")
	public String addadmin(@RequestBody User user) {
		Optional<User> userExist = userRepository.findByUsername(user.getUsername());
		if(userExist.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			List<Role>roles =new ArrayList<>();
			Role r1 = new Role();
			r1.setRole_name("ADMIN");
		    roles.add(r1);
		    user.setRoles(roles);
			userRepository.save(user);
			return user.getUsername() +": added successfully";
		}
		else if(userExist.isPresent()) {
			return "username already exist! change username";
		}
		else {
			return "there was an error";
		}
	}
	
	@PostMapping("/manager/user/edit/{id}")
	public String editUser(@PathVariable String id, @RequestBody User user) {
	   User oldUser;
	   try {
		   oldUser = userRepository.findById(Integer.parseInt(id)).get();
		   oldUser.setUsername(user.getUsername());
		   oldUser.setEmail(user.getEmail());
		   oldUser.setPhone(user.getPhone());
		   userRepository.save(oldUser);
		   return "User Updated Successfully!";
	   }
		catch(Exception e) {
			e.printStackTrace();
			return "User does not Exist!";
		}
	}
	
	@PostMapping("/manager/product/edit/{id}")
	public String editProduct(@PathVariable String id, @RequestBody Product product) {
	   Product oldProduct;
	   try {
		   oldProduct = productRepository.findById(Integer.parseInt(id)).get();
		   oldProduct.setName(product.getName());
		   oldProduct.setPrice(product.getPrice());
		   productRepository.save(oldProduct);
		   return "Product Updated Successfully!";
	   }
		catch(Exception e) {
			e.printStackTrace();
			return "Product does not Exist!";
		}
	}
	
	@GetMapping("/manager/user/del/{id}")
	public String deleteUser(@PathVariable String id) {
	   try {
           userRepository.deleteById(Integer.parseInt(id));
		   return "Oparation was Successful!";
	   }
		catch(Exception e) {
			e.printStackTrace();
			return "unsuccessful Oparation!";
		}
	}
	
	@GetMapping("/manager/product/del/{id}")
	public String deleteProduct(@PathVariable String id) {
	   try {
           productRepository.deleteById(Integer.parseInt(id));
		   return "Oparation was Successful!";
	   }
		catch(Exception e) {
			e.printStackTrace();
			return "unsuccessful Oparation!";
		}
	}

}
