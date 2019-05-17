package com.sunyuhan.domain;
/**
*@author SunYuHan
*2018年11月28日下午4:59:30
*
*/
public class OrderItem {
	private String itemid;
	private int quantity;
	private double total;
	//同order，对象与对象发生关系，而不是对象与对象属性
	private Product product;
	private Order order;
	public String getItemid() {
		return itemid;
	}
	public void setItemid(String itemid) {
		this.itemid = itemid;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public double getTotal() {
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public Order getOrder() {
		return order;
	}
	public void setOrder(Order order) {
		this.order = order;
	}
}
