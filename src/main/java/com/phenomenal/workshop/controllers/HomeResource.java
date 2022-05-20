package com.phenomenal.workshop.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.phenomenal.workshop.entity.Product;
import com.phenomenal.workshop.entity.Role;
import com.phenomenal.workshop.entity.Sale;
import com.phenomenal.workshop.entity.Stock;
import com.phenomenal.workshop.entity.User;
import com.phenomenal.workshop.repository.CategoryRepository;
import com.phenomenal.workshop.repository.ProductRepository;
import com.phenomenal.workshop.repository.SaleRepository;
import com.phenomenal.workshop.repository.UserRepository;

@RestController
public class HomeResource {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ProductRepository productRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private SaleRepository saleRepository;
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;
	
	@GetMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index");
	}
	
	@GetMapping("/login")
	public ModelAndView login() {
		return new ModelAndView("login");
	}
	
	@GetMapping("/signup")
	public ModelAndView signup() {
		return new ModelAndView("signup");
	}
	
	
	@PostMapping("/signup/user")
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
	@GetMapping("/allproduct")
	public ModelAndView getAllPorductsPage() {
		return new ModelAndView("allProducts");
	}
	@GetMapping("/products")
	public List<Product>getAllProducts(){
		List<Product>products=null;
		try{
			products =productRepository.findAll();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		return products;
	}
	
	@GetMapping("/products/{name}")
	public ModelAndView getProductsPage(@PathVariable String name) {
		return new ModelAndView("products");
	}
	
	@GetMapping("/products/category/{name}")
	public List<Product>getProductsByCategory(@PathVariable String name){
		List<Product>products= new ArrayList<>();
		List<Integer>product_ids = categoryRepository.findAllProductIdByName(name);
	    product_ids.forEach((product_id)->{
		   System.out.println("id:"+product_id);
		   Product product= productRepository.findById(product_id).get();
		   products.add(product);
	    });
		return products;
	}
//	@PostMapping("/products/search")
//	public List<Product>getSearchedProducts(@RequestBody String name){
//		List<Product>products= new ArrayList<>();
//		List<Product>productList =productRepository.findAllByName(name);
//		productList.forEach((product)->{
//			products.add(product);
//		});
//		List<Integer>product_ids = categoryRepository.findAllProductIdByName(name);
//	    product_ids.forEach((product_id)->{
//		   System.out.println("id:"+product_id);
//		   Product product= productRepository.findById(product_id).get();
//		   products.add(product);
//	    });
//		return products;
//	}
	
	@PostMapping("/sales")
	public String sales(@RequestBody Sale sale, Authentication authentication) {
		if(sale.getStock().size()<1) {
			return "Please Order Something!";
		}
		sale.setCustomerName(authentication.getName());
		sale.setPickedDate(null);
		Optional<User> user = userRepository.findByUsername(sale.getCustomerName());
		List<Stock>stocks = sale.getStock();
		List<Stock>boughtProducts = new ArrayList<>();
		double total = 0;
		if(user.isPresent()) {
		for(int i=0;i<stocks.size();i++){
			try {
				Stock boughtProduct = new Stock();
				Product product = productRepository.findByName(stocks.get(i).getProductName()).get();
				product.setQuantity(product.getQuantity() - stocks.get(i).getQuantity());
				boughtProduct.setProductName(product.getName());
				boughtProduct.setPrice(product.getPrice());
				boughtProduct.setQuantity(stocks.get(i).getQuantity());
				boughtProducts.add(boughtProduct);
				productRepository.save(product);
				stocks.get(i).setPrice(product.getPrice());
				total +=(product.getPrice() *  stocks.get(i).getQuantity());
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		sale.setTotal(total);
		sale.setOrderDate(new Date());
		try {
		saleRepository.save(sale);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
		String successfulPurchase= "Successful Purchase of the following products\n";
		for(int i=0;i<boughtProducts.size();i++) {
			successfulPurchase = successfulPurchase + boughtProducts.get(i).toString();
;		}
		successfulPurchase = successfulPurchase + "Payment on Delivery!";
		return successfulPurchase;
		}
		return "Purchase was unsuccessfull..try again!";
	}
	
	
	
	
}




