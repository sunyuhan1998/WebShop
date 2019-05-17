package com.sunyuhan.dao.impl;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.sunyuhan.dao.UserDao;
import com.sunyuhan.domain.User;
import com.sunyuhan.utils.JDBCUtils;

/**
*@author SunYuHan
*2018年11月24日上午11:34:44
*
*/
public class UserDaoImpl implements UserDao {

	@Override
	public void userRegist(User user) throws SQLException {
		String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode()};
		runner.update(sql,params);
	}

	@Override
	public User userActive(String code) throws SQLException {
		String sql = "select * from user where code=?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class),code);
	}

	@Override
	public void updateUser(User user) throws SQLException { 
		String sql = "update user set username = ?,password = ?,name = ?,email = ?,telephone= ?,birthday = ?,sex = ?,state = ?,code = ? where uid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {user.getUsername(),user.getPassword(),user.getName(),user.getEmail(),user.getTelephone(),user.getBirthday(),user.getSex(),user.getState(),user.getCode(),user.getUid()};
		runner.update(sql,params);
	}

	@Override
	public User userLogin(User user) throws SQLException {
		String sql = "select * from user where username = ? and password = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<User>(User.class),user.getUsername(),user.getPassword());
	}

	
	
}
