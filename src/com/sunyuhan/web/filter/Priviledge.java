package com.sunyuhan.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.sunyuhan.domain.User;

/**
 * Servlet Filter implementation class Privililedge
 */
public class Priviledge implements Filter {

    public Priviledge() {
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		//判断当前session中是否存在已经登陆成功的用户，存在则放行
		HttpServletRequest myReq = (HttpServletRequest)request;
		User user = (User)myReq.getSession().getAttribute("loginUser");
		if (null != user) {
			chain.doFilter(request, response);
		}else {
			myReq.setAttribute("msg", "请登陆后再访问！");
			myReq.getRequestDispatcher("/jsp/info.jsp").forward(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
