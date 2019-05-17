package com.sunyuhan.service.impl;

import java.sql.SQLException;
import java.util.List;

import com.sunyuhan.dao.CategoryDao;
import com.sunyuhan.dao.impl.CategoryDaoImpl;
import com.sunyuhan.domain.Category;
import com.sunyuhan.service.CategoryService;
import com.sunyuhan.utils.BeanFactory;
import com.sunyuhan.utils.JedisUtils;

import redis.clients.jedis.Jedis;

/**
*@author SunYuHan
*2018年11月26日上午9:57:39
*
*/
public class CategoryServiceImpl implements CategoryService {
	CategoryDao dao = (CategoryDao)BeanFactory.createObject("CategoryDao");
	@Override
	public List<Category> getAllCats() throws SQLException {
		return dao.getAllCats();
	}

	@Override
	public void addCategory(Category c) throws SQLException {
		//在数据库中更新分类
		dao.addCategory(c);
		//更新redis缓存
		Jedis jedis = JedisUtils.getJedis();
		jedis.del("allCats");
		JedisUtils.closeJedis(jedis);
	}


}
