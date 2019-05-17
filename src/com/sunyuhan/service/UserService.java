package com.sunyuhan.service;

import java.sql.SQLException;

import com.sunyuhan.domain.User;

/**
*@author SunYuHan
*2018年11月23日下午8:56:34
*
*/
public interface UserService {

	void userRegist(User user) throws SQLException;

	boolean userActive(String code) throws SQLException ;

	User userLogin(User user) throws SQLException;
	
}
