package com.phenomenal.workshop.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Double  price;
	private String image;
	private int quantity;
	private String description;
	@OneToMany(cascade= CascadeType.ALL,fetch= FetchType.LAZY)
	@JoinColumn(name = "product_id")
	private List<Category>categories;
	public Product(int id, String name, Double price, String image, int quantity, List<Category> categories, String decription) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.image = image;
		this.categories = categories;
		this.quantity = quantity;
		this.description = description;
	}
	public Product() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public List<Category> getCategories() {
		return categories;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "Product [name=" + name + ", price=" + price + ", categories=" + categories + "]";
	}
	
	
}
