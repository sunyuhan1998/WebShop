package com.sunyuhan.domain;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
*@author SunYuHan
*2018年11月27日下午9:13:34
*
*/
public class Cart {
	//购物项
	Map<String, CartItem> map = new HashMap<String ,CartItem>();
	//总价、积分
	private double total;
	//添加购物项
	public void addCartItem(CartItem cartItem) {
		//应进行判断，如果购物车已经有此商品，则数量+1，否则添加到购物车
		String pid = cartItem.getProduct().getPid();
		if (map.containsKey(pid)) {
			CartItem oldItem = map.get(pid);
			oldItem.setNum(oldItem.getNum()+cartItem.getNum());
		}else {
			map.put(pid, cartItem);
		}
	}
	//删除购物项
	public void removeCartItem(String pid) {
		map.remove(pid);
	}
	//清空购物项
	public void clearCart() {
		map.clear();
	}
	//返回map中所有值，遍历数据
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
	public Map<String, CartItem> getMap() {
		return map;
	}
	public void setMap(Map<String, CartItem> map) {
		this.map = map;
	}
	public double getTotal() {
		total = 0;
		Collection<CartItem> values = map.values();
		for (CartItem cartItem : values) {
			total+=cartItem.getSubTotal();
		}
		return total;
	}
	public void setTotal(double total) {
		this.total = total;
	}
	
}
