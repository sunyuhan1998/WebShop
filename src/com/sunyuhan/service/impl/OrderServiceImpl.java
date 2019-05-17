package com.sunyuhan.service.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.sunyuhan.dao.OrderDao;
import com.sunyuhan.domain.Order;
import com.sunyuhan.domain.OrderItem;
import com.sunyuhan.domain.PageModel;
import com.sunyuhan.domain.User;
import com.sunyuhan.service.OrderService;
import com.sunyuhan.utils.BeanFactory;
import com.sunyuhan.utils.JDBCUtils;

/**
*@author SunYuHan
*2018年11月28日下午4:58:33
*
*/
public class OrderServiceImpl implements OrderService {
	OrderDao orderDao = (OrderDao)BeanFactory.createObject("OrderDao");
	@Override
	public void saveOrder(Order order) throws SQLException{
		//保存订单和所有订单项（必须同时成功、失败）
		/*try {
			JDBCUtils.startTransaction();
			OrderDao orderDao = new OrderDaoImpl();
			orderDao.saveOrder(order);
			for (OrderItem item : order.getList()) {
				orderDao.saveOrder(item);
			}
			JDBCUtils.commitAndClose();
		} catch (Exception e) {
			JDBCUtils.rollbackAndClose();
		}*/
		Connection conn = null;
		
		try {
			//获取链接
			conn = JDBCUtils.getConnection();
			//开启事务
			conn.setAutoCommit(false);
			//保存订单
			orderDao.saveOrder(conn,order);
			//保存订单项
			for (OrderItem item: order.getList()) {
				orderDao.saveOrderItem(conn,item);
			}
			//提交
			conn.commit();
		} catch (SQLException e) {
			//回滚
			conn.rollback();
		}
	}

	@Override
	public void UpdateOrder(Order order) throws Exception {
		orderDao.updateOrder(order);
		
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		return orderDao.findOrderByOid(oid);
	}

	@Override
	public PageModel findMyOrderWithPage(User user, int curNum) throws Exception{
		//创建PageModel对象计算携带分页参数
		int totalRecords = orderDao.getTotalRecords(user);
		PageModel pageModel = new PageModel(curNum,totalRecords,3);
		//关联集合
		List list = orderDao.findMyOrdersWithPage(user,pageModel.getStartIndex(),pageModel.getPageSize());
		pageModel.setRecords(list);
		//关联url
		pageModel.setUrl("OrderServlet?method=findMyOrderWithPage");
		return pageModel;
	}

	@Override
	public List<Order> findOrders() throws Exception {
		return orderDao.findOrders();
	}

	@Override
	public List<Order> findOrders(String state) throws Exception {
		return orderDao.findOrders(state);
	}
	
	

}
