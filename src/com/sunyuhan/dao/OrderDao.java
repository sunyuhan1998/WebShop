package com.sunyuhan.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sunyuhan.domain.Order;
import com.sunyuhan.domain.OrderItem;
import com.sunyuhan.domain.User;

/**
*@author SunYuHan
*2018年11月28日下午4:58:46
*
*/
public interface OrderDao {

	void saveOrder(Connection conn, Order order)throws SQLException;

	void saveOrderItem(Connection conn, OrderItem item)throws SQLException;

	int getTotalRecords(User user)throws SQLException;

	List findMyOrdersWithPage(User user, int startIndex, int pageSize)throws Exception;

	Order findOrderByOid(String oid)throws Exception;

	void updateOrder(Order order)throws Exception;

	List<Order> findOrders()throws Exception;

	List<Order> findOrders(String state)throws Exception;

}
