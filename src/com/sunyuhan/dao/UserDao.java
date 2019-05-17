package com.sunyuhan.dao;

import java.sql.SQLException;

import com.sunyuhan.domain.User;

/**
*@author SunYuHan
*2018年11月23日下午8:55:47
*
*/
public interface UserDao {

	void userRegist(User user) throws SQLException;

	User userActive(String code) throws SQLException ;

	void updateUser(User user) throws SQLException ;

	User userLogin(User user) throws SQLException ;

}
