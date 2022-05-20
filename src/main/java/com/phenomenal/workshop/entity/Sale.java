package com.phenomenal.workshop.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Sale {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String customerName;
	private boolean pickedup;
	private double total;
	private Date orderDate;
	private Date pickedDate;
	private String admin;
	@OneToMany(cascade= CascadeType.ALL,fetch= FetchType.EAGER)
	private List<Stock>stock;
	public Sale(int id, String customerName, List<Stock>stock, boolean pickedup,double total, Date orderDate, Date pickedDate,String admin) {
		super();
		this.id = id;
		this.customerName = customerName;
		this.stock = stock;
		this.pickedup = pickedup;
		this.total = total;
		this.orderDate = orderDate;
		this.pickedDate= pickedDate;
		this.admin = admin;
	}
	
	public Sale() {
		super();
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getCustomerName() {
		return customerName;
	}
	
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	
	public List<Stock> getStock() {
		return stock;
	}
	
	public void setStock(List<Stock> stock) {
		this.stock = stock;
	}
	
	public boolean isPickedup() {
		return pickedup;
	}
	
	public void setPickedup(boolean pickedup) {
		this.pickedup = pickedup;
	}
	
	public  double getTotal() {
		return total;
	}
	
	public void setTotal(double total) {
		this.total = total;
	}
	
	public Date getOrderDate() {
		return orderDate;
	}
	
	public void setOrderDate(Date date) {
		this.orderDate = date;
	}
	public Date getPickedDate() {
		return pickedDate;
	}
	public void setPickedDate(Date pickedDate) {
		this.pickedDate = pickedDate;
	}
	public String getAdmin() {
		return admin;
	}
	public void setAdmin(String admin) {
		this.admin = admin;
	}
	
	

}
