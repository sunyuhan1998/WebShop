package com.sunyuhan.web.servlet;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.PageModel;
import com.sunyuhan.domain.Product;
import com.sunyuhan.service.ProductService;
import com.sunyuhan.service.impl.ProductServiceImpl;
import com.sunyuhan.web.base.BaseServlet;

/**
 * Servlet implementation class ProductServlet
 */
public class ProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String findProductByPid(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取商品id
		String pid = request.getParameter("pid");
		//根据商品pid查询
		ProductService productService = new ProductServiceImpl();
		Product product = productService.findProductByPid(pid);
		//将获取到的商品存入request
		request.setAttribute("product", product);
		//转发
		return "/jsp/product_info.jsp";
		
	}
	public String findPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取参数
		int curNum = Integer.parseInt(request.getParameter("num"));
		int cid = Integer.parseInt(request.getParameter("cid"));
		//调用业务层
		ProductService productService = new ProductServiceImpl();
		PageModel pageModel = productService.findProducts(cid,curNum);
		//保存数据
		request.setAttribute("page", pageModel);
		//转发
		return "/jsp/product_list.jsp";
	}
}
