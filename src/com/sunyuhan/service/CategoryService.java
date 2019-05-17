package com.sunyuhan.service;

import java.sql.SQLException;
import java.util.List;

import com.sunyuhan.domain.Category;

/**
*@author SunYuHan
*2018年11月26日上午9:57:21
*
*/
public interface CategoryService {

	List<Category> getAllCats() throws SQLException;

	void addCategory(Category c) throws SQLException;


}
