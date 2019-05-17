package com.sunyuhan.web.servlet;


import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.Product;
import com.sunyuhan.service.ProductService;
import com.sunyuhan.service.impl.ProductServiceImpl;
import com.sunyuhan.web.base.BaseServlet;

public class IndexServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response)throws Exception{
		//调用业务层查询最新商品，最热商品，返回两个集合
		ProductService productService = new ProductServiceImpl();
		List<Product> list01 = productService.findHots();
		List<Product> list02 = productService.findNews();
		//存入request
		request.setAttribute("hots", list01);
		request.setAttribute("news", list02);
		//转发到真实首页
		return "/jsp/index.jsp";
	}
}
