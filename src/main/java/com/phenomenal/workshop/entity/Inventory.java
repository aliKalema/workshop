package com.phenomenal.workshop.entity;

public class Inventory {
	private int quantity;
	private double worth;
	public Inventory(int quantity,double worth) {
		this.quantity = quantity;
		this.worth = worth;
	}
	public Inventory() {
		super();
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getWorth() {
		return worth;
	}
	public void setWorth(double worth) {
		this.worth = worth;
	}
}
