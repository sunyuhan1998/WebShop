package com.sunyuhan.dao.impl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sunyuhan.dao.OrderDao;
import com.sunyuhan.domain.Order;
import com.sunyuhan.domain.OrderItem;
import com.sunyuhan.domain.Product;
import com.sunyuhan.domain.User;
import com.sunyuhan.utils.JDBCUtils;

/**
*@author SunYuHan
*2018年11月28日下午4:59:02
*
*/
public class OrderDaoImpl implements OrderDao {

	@Override
	public int getTotalRecords(User user) throws SQLException {
		String sql = "select count(*) from orders where uid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)runner.query(sql, new ScalarHandler(),user.getUid());
		return num.intValue();
	}

	@Override
	public List findMyOrdersWithPage(User user, int startIndex, int pageSize) throws Exception {
		String sql = "select * from orders where uid=? limit ? , ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		List<Order> list = runner.query(sql, new BeanListHandler<Order>(Order.class),user.getUid(),startIndex,pageSize);
		for(Order order : list) {
			//获取每笔订单oid 查询每个订单下的订单项以及详细商品信息
			String oid = order.getOid();
			sql = "select * from orderitem o , product p where o.pid = p.pid and oid = ?";
			List<Map<String , Object>> list02 = runner.query(sql, new MapListHandler(),oid);
			//遍历list02
			for (Map<String , Object> map : list02) {
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
					// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
					// 1_创建时间类型的转换器
					DateConverter dt = new DateConverter();
					// 2_设置转换的格式
					dt.setPattern("yyyy-MM-dd");
					// 3_注册转换器
					ConvertUtils.register(dt, java.util.Date.class);
				//将map中属于orderitem的数据自动填充到orderitem对象上
				BeanUtils.populate(orderItem, map);
				//将map中属于product的数据自动填充到product对象上
				BeanUtils.populate(product, map);
				//订单项与商品关联
				orderItem.setProduct(product);
				//订单与订单项关联
				order.getList().add(orderItem);
			}
		}
		return list;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		String sql = "update orders set ordertime = ?, total = ? , state = ? , address = ? , name = ? , telephone = ? where oid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getOid()};
		runner.update(sql,params);
	}

	@Override
	public Order findOrderByOid(String oid) throws Exception {
		String sql = "select * from orders where oid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Order order =  runner.query(sql, new BeanHandler<Order>(Order.class),oid);
		//根据订单id查询所有订单项及商品信息
		sql = "select * from orderitem o , product p where o.pid = p.pid and oid = ?";
		List<Map<String , Object>> list02 = runner.query(sql, new MapListHandler(),oid);
		//遍历list02
		for (Map<String , Object> map : list02) {
			OrderItem orderItem = new OrderItem();
			Product product = new Product();
				// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
				// 1_创建时间类型的转换器
				DateConverter dt = new DateConverter();
				// 2_设置转换的格式
				dt.setPattern("yyyy-MM-dd");
				// 3_注册转换器
				ConvertUtils.register(dt, java.util.Date.class);
			//将map中属于orderitem的数据自动填充到orderitem对象上
			BeanUtils.populate(orderItem, map);
			//将map中属于product的数据自动填充到product对象上
			BeanUtils.populate(product, map);
			//订单项与商品关联
			orderItem.setProduct(product);
			//订单与订单项关联
			order.getList().add(orderItem);
		}
		return order;
	}

	@Override
	public void saveOrder(Connection conn, Order order) throws SQLException {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner();
		Object[] params = {order.getOid(),order.getOrdertime(),order.getTotal(),order.getState(),order.getAddress(),order.getName(),order.getTelephone(),order.getUser().getUid()};
		runner.update(conn,sql,params);
	}

	@Override
	public void saveOrderItem(Connection conn, OrderItem item) throws SQLException {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		QueryRunner runner = new QueryRunner();
		Object[] params = {item.getItemid(),item.getQuantity(),item.getTotal(),item.getProduct().getPid(),item.getOrder().getOid()};
		runner.update(conn,sql,params);
	}

	@Override
	public List<Order> findOrders() throws Exception {
		String sql = "select * from orders";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Order>(Order.class));
	}

	@Override
	public List<Order> findOrders(String state) throws Exception {
		String sql = "select * from orders where state = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Order>(Order.class),state);
	}
	

}
