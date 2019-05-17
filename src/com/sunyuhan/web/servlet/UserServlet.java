package com.sunyuhan.web.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.Cart;
import com.sunyuhan.domain.User;
import com.sunyuhan.service.UserService;
import com.sunyuhan.service.impl.UserServiceImpl;
import com.sunyuhan.utils.MailUtils;
import com.sunyuhan.utils.MyBeanUtils;
import com.sunyuhan.utils.UUIDUtils;
import com.sunyuhan.web.base.BaseServlet;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String registUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/register.jsp";
	}
	public String loginUI(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		return "/jsp/login.jsp";
	}
	public String userRegist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//接收表单参数
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//为用户其他属性赋值
		user.setUid(UUIDUtils.getId());
		user.setState(0);
		user.setCode(UUIDUtils.getCode());
		System.out.println(user);
		//调用业务层注册
		UserService userService = new UserServiceImpl();
		try {
			MailUtils.sendMail(user.getEmail(), user.getCode());
			userService.userRegist(user);
			request.setAttribute("msg", "用户注册成功！请前往您的邮箱激活");
		} catch (Exception e) {
			//注册失败
			request.setAttribute("msg", "用户注册失败！请重新注册");
		}
		return "/jsp/info.jsp";
	}
	public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
		//获取激活码
		String code = request.getParameter("code");
		//调用业务层激活功能
		UserService userService = new UserServiceImpl();
		boolean flag = userService.userActive(code);
		if (flag) {
			//用户激活成功 
			request.setAttribute("msg", "用户激活成功，请登录！");
			return "/jsp/login.jsp";
		}else {
			//用户激活失败
			request.setAttribute("msg", "用户激活失败，请重新激活！");
			return "/jsp/info.jsp";
		}
	}
	public String userLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户账密
		User user = new User();
		MyBeanUtils.populate(user, request.getParameterMap());
		//调用业务层
		UserService userService = new UserServiceImpl();
		User user02 = null;
		try {
			//登陆成功
			user02 = userService.userLogin(user);
			//登陆后建立购物车对象
			Cart cart = new Cart();
			request.getSession().setAttribute("cart", cart);
			request.getSession().setAttribute("loginUser", user02);
			response.sendRedirect("/WebShop/index.jsp");
			return null;
		} catch (Exception e) {
			//登录失败
			String msg = e.getMessage();
			System.out.println(msg);
			request.setAttribute("msg", msg);
			return "/jsp/login.jsp";
		}
	}
	public void logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//清除session
		request.getSession().invalidate();
		//重定向到首页
		response.sendRedirect("index.jsp");
	}

}
