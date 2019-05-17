package com.sunyuhan.dao;

import java.sql.SQLException;
import java.util.List;

import com.sunyuhan.domain.Category;

/**
*@author SunYuHan
*2018年11月26日上午9:58:19
*
*/
public interface CategoryDao {

	List<Category> getAllCats() throws SQLException;

	void addCategory(Category c) throws SQLException;

}
