package com.phenomenal.workshop.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phenomenal.workshop.entity.Inventory;
import com.phenomenal.workshop.entity.Product;
import com.phenomenal.workshop.entity.Role;
import com.phenomenal.workshop.entity.Sale;
import com.phenomenal.workshop.entity.Stock;
import com.phenomenal.workshop.entity.User;
import com.phenomenal.workshop.repository.ProductRepository;
import com.phenomenal.workshop.repository.RoleRepository;
import com.phenomenal.workshop.repository.SaleRepository;
import com.phenomenal.workshop.repository.StockRepository;
import com.phenomenal.workshop.repository.UserRepository;

@RestController
public class AdminController {
	@Autowired
	UserRepository userRepository; 
	
	@Autowired
	StockRepository stockRepository;
	
	@Autowired
	ProductRepository productRepository; 
	
	@Autowired
	RoleRepository roleRepository;
	
	@Autowired
	SaleRepository saleRepository;
	
	@Autowired 
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/admin")
	public ModelAndView admin(Model model) {
		List<Sale>unpicked = null;
		try {
			unpicked = saleRepository.findAllByPicked();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("unpicked",unpicked);
		mav.setViewName("dashboard");
		return mav;
	}
	
	@GetMapping("/admin/unpickedsize")
	public String getUnpickedSize() {
		int unpickedSize = 0;
		try {
			List<Sale>unpicked = saleRepository.findAllByPicked();
			unpickedSize = unpicked.size();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(unpickedSize);
	}
	
	@GetMapping("/admin/usersize")
	public String getUsersize() {
		int userSize = 0;
		try {
			List<User>users = userRepository.findAll();
			userSize = users.size();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(userSize);
	}
	
	@GetMapping("/admin/inventorysize")
	public String getInventoryQuantity() {
		int inventoryQuantity=0;
		try {
			List<Product>products =productRepository.findAll();
			for(int i=0;i<products.size();i++) {
				inventoryQuantity =inventoryQuantity + products.get(i).getQuantity();
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(inventoryQuantity);
	}
	
	@GetMapping("/admin/todaysale")
	public String getTodaySales() {
		int todaySale = 0;
		try {
			List<Sale>sales = saleRepository.findAllByCurrentDate();
			todaySale = sales.size();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return String.valueOf(todaySale);
		
	}
	@GetMapping("/admin/pickup/{id}")
	public String pickupSale(@PathVariable String id,Authentication authentication) {
		try { 
			Sale sale = saleRepository.findById(Integer.parseInt(id)).get();
			sale.setAdmin(authentication.getName());
			sale.setPickedDate(new Date());
			sale.setPickedup(true);
			try {
				saleRepository.save(sale);
			}
			catch(Exception e) {
				e.printStackTrace();
				return"An error Occurred!";
			}
		}
		catch(Exception e) {
			e.printStackTrace();
			return"An error Occurred!";
		}
		return "order picked up successfully!";
	}
	@GetMapping("/admin/dashboard")
	public ModelAndView dashborad(Model model) {
		List<Sale>unpicked = null;
		try {
			unpicked = saleRepository.findAllByPicked();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		ModelAndView mav = new ModelAndView();
		mav.addObject("unpicked",unpicked);
		mav.setViewName("dashboard");
		return mav;
	}
	
	@PostMapping("/admin/products/add")
	public String addUser() {
		return null;
	}
	
	@GetMapping("/admin/products")
	public List<Product> getProducts() {
		return productRepository.findAll();
	}
	
	@PostMapping("/admin/stock/add")
	public String addStock(@RequestBody Stock stock) {
		Optional<Product> productOptional = productRepository.findByName(stock.getProductName());
		if(productOptional.isEmpty()) {
			return "Product Does Not Exist!";
		}
		else if(productOptional.isPresent()) {
			Product product = productOptional.get();
			product.setQuantity(product.getQuantity() + stock.getQuantity());
			productRepository.save(product);
			stockRepository.save(stock);
			return "Stock added successfully";
		}
		else {
			return " An Error Occurred!";
		}
	}
	
	@GetMapping("/admin/users")
	public ModelAndView orders(Model model) {
		 ModelAndView mav = new ModelAndView();
		 List<User>users = new ArrayList<>();
		 List<User>admins = new ArrayList<>();
		 List<User>managers = new ArrayList<>();
		 List<Integer>adminIds ;
		 try {
		 adminIds = roleRepository.findAllUserIdByRoleName("ADMIN");
		 if(adminIds.size()>0) {
	            for(int i =0;i<adminIds.size();i++) {
	               User us = null;
	          	   try {
	          		  us = userRepository.findById(adminIds.get(i)).get();
	          	   }
	          	   catch(Exception e) {
	          		   e.printStackTrace();
	          	   }
	               admins.add(us);
	            }
			}
			mav.addObject("admins", admins);
		 }
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		 
		try {
		List<Integer>managerIds = roleRepository.findAllUserIdByRoleName("MANAGER");
		if(managerIds.size()>0) {
           for(int i =0;i<managerIds.size();i++) {
     	   User us = null;
     	   try {
     		  us = userRepository.findById(managerIds.get(i)).get();
     	   }
     	   catch(Exception e) {
     		   e.printStackTrace();
     	   }
        	managers.add(us);
           }
		}
		mav.addObject("managers", managers);
		}
		 catch(Exception e) {
			 e.printStackTrace();
		 }
		try {
		List<Integer>userIds = roleRepository.findAllUserIdByRoleName("USER");
		if(userIds.size()>0) {
           for(int i =0;i<userIds.size();i++) {
        	   User us = null;
        	   try {
        		  us = userRepository.findById(userIds.get(i)).get();
        	   }
        	   catch(Exception e) {
        		   e.printStackTrace();
        	   }
           	users.add(us);
           }
		}
		mav.addObject("users", users);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		mav.setViewName("users");
		return mav;
	}
	
	@GetMapping("/admin/sales/all")
	public List<Sale>getSales(){
		List<Sale>sales = null;
		try {
			sales = saleRepository.findAll();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return sales;
	}
	
	@GetMapping("/admin/sales/unpicked")
	public List<Sale>getUnpickedSales(){
		List<Sale>sales = null;
		try {
			sales = saleRepository.findAllByPicked();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return sales;
	}
	
	@GetMapping("/admin/sales")
	public ModelAndView sale(Model model) {
		ModelAndView mav = new ModelAndView("sales");
		List<Sale>sales = null;
		try {
			sales = saleRepository.findAll();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("sales",sales);
		return mav;
	}

	@GetMapping("/admin/sales/details/{id}")
	public ModelAndView getSalesDetails(@PathVariable String id) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("saledetails");
		Sale sale =null;
		List<Stock>stock = null;
		try {
			sale = saleRepository.findById(Integer.parseInt(id)).get();
			stock = sale.getStock();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		mav.addObject("sale",sale);
		mav.addObject("stock",stock);
		return mav;
	}
	
	@GetMapping("/admin/inventory") 
	public ModelAndView manager() {
		ModelAndView mav = new ModelAndView();
		int totalQuantity = 0;
		double totalWorth = 0;
		List<Product>products= productRepository.findAll();
		for(int i =0;i<products.size();i++) {
			 double productPrice = products.get(i).getPrice();
			 int productQuantity = products.get(i).getQuantity();
			 totalQuantity = totalQuantity + productQuantity;
			 totalWorth = totalWorth + (productQuantity*productPrice);
		}
		Inventory inventory = new Inventory();
		inventory.setQuantity(totalQuantity);
		inventory.setWorth(totalWorth);
		mav.addObject("inventory",inventory);
		mav.addObject("products",products);
		mav.setViewName("inventory");
		return mav;
	}
	
	@PostMapping("/admin/user")
	public String adduser(@RequestBody User user) {
		Optional<User> userExist = userRepository.findByUsername(user.getUsername());
		if(userExist.isEmpty()) {
			String encodedPassword = passwordEncoder.encode(user.getPassword());
			user.setPassword(encodedPassword);
			List<Role>roles =new ArrayList<>();
			Role role = new Role();
			role.setRole_name("USER");
		    roles.add(role);
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
	
}
