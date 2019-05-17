package com.sunyuhan.web.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.Cart;
import com.sunyuhan.domain.CartItem;
import com.sunyuhan.domain.Product;
import com.sunyuhan.service.ProductService;
import com.sunyuhan.service.impl.ProductServiceImpl;
import com.sunyuhan.web.base.BaseServlet;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public void addCartItemToCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		if (null == cart) {
			request.setAttribute("msg", "您还未登录!无法添加！");
			request.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}else {
			//获取参数
			String pid = request.getParameter("pid");
			int num = Integer.parseInt(request.getParameter("quantity"));
			//调用service查询购物项
			ProductService productService = new ProductServiceImpl();
			Product product = productService.findProductByPid(pid);
			//添加购物车
			CartItem cartItem = new CartItem();
			cartItem.setNum(num);
			cartItem.setProduct(product);
			//保存数据
			cart.addCartItem(cartItem);
			//转发
			response.sendRedirect("/WebShop/jsp/cart.jsp");
		}
	}
	public void removeCartItem(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取待删除商品的pid
		String pid = request.getParameter("pid");
		//获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//调用删除方法
		cart.removeCartItem(pid);
		//跳转
		response.sendRedirect("/WebShop/jsp/cart.jsp");
	}
	public void clearCart(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取购物车
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		//调用购物车的清空方法
		cart.clearCart();
		//跳转
		response.sendRedirect("/WebShop/jsp/cart.jsp");
	}
}
