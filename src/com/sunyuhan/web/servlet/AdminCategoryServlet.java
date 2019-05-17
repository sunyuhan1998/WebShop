package com.sunyuhan.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.Category;
import com.sunyuhan.service.CategoryService;
import com.sunyuhan.service.impl.CategoryServiceImpl;
import com.sunyuhan.utils.UUIDUtils;
import com.sunyuhan.web.base.BaseServlet;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String findAllCats(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取全部分类信息
		CategoryService categoryService = new CategoryServiceImpl();
		List<Category> list = categoryService.getAllCats();
		//保存数据
		request.setAttribute("allCats", list);
		//转发
		return "/admin/category/list.jsp";
	}
	public String addCategoryUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		return "/admin/category/add.jsp";
	}
	public void addCategory(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取分类名称
		String cname = request.getParameter("cname");
		//创建分类
		String id = UUIDUtils.getId();
		Category c = new Category();
		c.setCid(id);
		c.setCname(cname);
		//调用业务层添加分类功能
		CategoryService categoryService = new CategoryServiceImpl();
		categoryService.addCategory(c);
		//跳转
		response.sendRedirect("/WebShop/AdminCategoryServlet?method=findAllCats");
	}
}
