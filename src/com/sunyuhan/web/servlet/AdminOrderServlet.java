package com.sunyuhan.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.Order;
import com.sunyuhan.service.OrderService;
import com.sunyuhan.service.impl.OrderServiceImpl;
import com.sunyuhan.web.base.BaseServlet;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class AdminOrderServlet
 */
public class AdminOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String findOrders(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String state = request.getParameter("state");
		OrderService orderService = new OrderServiceImpl();
		List<Order> list = null;
		if (state==null||"".equals(state)) {
			list = orderService.findOrders();
		}else {
			list = orderService.findOrders(state);
		}
		//存入request
		request.setAttribute("allOrders", list);
		//跳转
		return "/admin/order/list.jsp";
	}
	public void findOrderByOidWithAjax(HttpServletRequest request, HttpServletResponse response) throws Exception{
		String oid = request.getParameter("id");
		OrderService orderService = new OrderServiceImpl();
		Order order = orderService.findOrderByOid(oid);
		String jsonSt = JSONArray.fromObject(order.getList()).toString();
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jsonSt);
	}
	public void updateOrderByOid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		OrderService orderService = new OrderServiceImpl();
		//获取订单oid
		String oid = request.getParameter("oid");
		//查询订单
		Order order = orderService.findOrderByOid(oid);
		//修改订单状态
		order.setState(3);
		orderService.UpdateOrder(order);
		//跳转
		response.sendRedirect("/WebShop/AdminOrderServlet?method=findOrders&state=3");
	}
}
