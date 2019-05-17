package com.sunyuhan.web.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sunyuhan.domain.Category;
import com.sunyuhan.service.CategoryService;
import com.sunyuhan.service.impl.CategoryServiceImpl;
import com.sunyuhan.utils.JedisUtils;
import com.sunyuhan.web.base.BaseServlet;

import net.sf.json.JSONArray;
import redis.clients.jedis.Jedis;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public void findAllCats(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		//在redis中获取全部分类信息
		Jedis jedis = JedisUtils.getJedis();
		String jsonStr = jedis.get("allCats");
		if (null == jsonStr || "".equals(jsonStr)) {
			//调用业务层获取全部分类方法
			CategoryService categoryService = new CategoryServiceImpl();
			List<Category> list = categoryService.getAllCats();
			//将全部分类转换为json格式并存入redis
			jsonStr = JSONArray.fromObject(list).toString();
			jedis.set("allCats", jsonStr);
			//将全部分类信息响应到客户端
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().write(jsonStr);
		}else {
			resp.setContentType("application/json;charset=utf-8");
			resp.getWriter().write(jsonStr);
		}
		JedisUtils.closeJedis(jedis);
	}
}
