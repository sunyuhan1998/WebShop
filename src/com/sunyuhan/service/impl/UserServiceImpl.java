package com.sunyuhan.service.impl;

import java.sql.SQLException;

import javax.management.RuntimeErrorException;

import com.sunyuhan.dao.CategoryDao;
import com.sunyuhan.dao.UserDao;
import com.sunyuhan.dao.impl.UserDaoImpl;
import com.sunyuhan.domain.User;
import com.sunyuhan.service.UserService;
import com.sunyuhan.utils.BeanFactory;

/**
*@author SunYuHan
*2018年11月24日上午11:35:06
*
*/
public class UserServiceImpl implements UserService {
	UserDao userDao = (UserDao)BeanFactory.createObject("UserDao");
	@Override
	public void userRegist(User user) throws SQLException{
		//实现注册功能
		userDao.userRegist(user);
	}

	@Override
	public boolean userActive(String code) throws SQLException {
		User user = userDao.userActive(code);
		if (null!=user) {
			//修改用户状态码,清除激活码
			user.setState(1);
			user.setCode(null);
			//对数据库进行一次真实的更新操作
			userDao.updateUser(user);
			return true;
		}else {
			return false;
		}
		
		
	}

	@Override
	public User userLogin(User user) throws SQLException {
		User user02 = userDao.userLogin(user);
		if (null == user02) {
			throw new RuntimeException("密码有误！");
		}else if (user02.getState() == 0) {
			throw new RuntimeException("账户未激活！");
		}else {
			return user02;
		}
	}

}
