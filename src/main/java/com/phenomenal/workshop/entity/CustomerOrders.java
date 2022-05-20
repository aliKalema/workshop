package com.phenomenal.workshop.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class CustomerOrders {
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id;
	private String name;
	private boolean picked;
	@OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
	private List<Stock>stock;
	public CustomerOrders(int id, String name, boolean picked, List<Stock> stock) {
		super();
		this.id = id;
		this.name = name;
		this.picked = picked;
		this.stock = stock;
	}
	public CustomerOrders() {
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
	public boolean isPicked() {
		return picked;
	}
	public void setPicked(boolean picked) {
		this.picked = picked;
	}

	@Override
	public String toString() {
		return "Order [name=" + name + ", picked=" + picked + ", stock=" + stock + "]";
	}
	
}
