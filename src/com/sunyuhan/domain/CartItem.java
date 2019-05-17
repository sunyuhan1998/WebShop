package com.sunyuhan.domain;
/**
*@author SunYuHan
*2018年11月27日下午9:11:14
*
*/
public class CartItem {
	private Product product;
	private int num;//当前类别商品数量
	private double subTotal;//小计
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public double getSubTotal() {
		subTotal = product.getShop_price()*num;
		return subTotal;
	}
	public void setSubTotal(double subTotal) {
		this.subTotal = subTotal;
	}
	
	
}
