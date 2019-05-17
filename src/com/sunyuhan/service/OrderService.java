package com.sunyuhan.service;

import java.sql.SQLException;
import java.util.List;

import com.sunyuhan.domain.Order;
import com.sunyuhan.domain.PageModel;
import com.sunyuhan.domain.User;

/**
*@author SunYuHan
*2018年11月28日下午4:58:13
*
*/
public interface OrderService {


	void saveOrder(Order order) throws SQLException;

	PageModel findMyOrderWithPage(User user, int curNum) throws Exception;

	Order findOrderByOid(String oid) throws Exception;

	void UpdateOrder(Order order) throws Exception;

	List<Order> findOrders() throws Exception;

	List<Order> findOrders(String state) throws Exception;

}
