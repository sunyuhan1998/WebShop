package com.sunyuhan.dao.impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.sunyuhan.dao.CategoryDao;
import com.sunyuhan.domain.Category;
import com.sunyuhan.utils.JDBCUtils;

/**
*@author SunYuHan
*2018年11月26日上午9:58:36
*
*/
public class CategoryDaoImpl implements CategoryDao {

	@Override
	public List<Category> getAllCats() throws SQLException {
		String sql = "select * from category";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Category>(Category.class));
	}

	@Override
	public void addCategory(Category c) throws SQLException {
		String sql = "insert into category values(? , ?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		runner.update(sql,c.getCid(),c.getCname());
	}

}
