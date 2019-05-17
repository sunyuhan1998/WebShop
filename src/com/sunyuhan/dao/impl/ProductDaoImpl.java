package com.sunyuhan.dao.impl;

import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import com.sunyuhan.dao.ProductDao;
import com.sunyuhan.domain.Product;
import com.sunyuhan.utils.JDBCUtils;

/**
*@author SunYuHan
*2018年11月26日下午6:23:57
*
*/
public class ProductDaoImpl implements ProductDao {

	@Override
	public Product findProductByPid(String pid) throws Exception {
		String sql = "select * from product where pid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanHandler<Product>(Product.class),pid);
	}

	@Override
	public List<Product> findHots() throws Exception {
		String sql = "select * from product where pflag = 0 and is_hot=1 order by pdate desc limit 0 , 9";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}

	@Override
	public int findTotalRecords() throws Exception {
		String sql = "select count(*) from product";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)runner.query(sql, new ScalarHandler());
		return num.intValue();
	}

	@Override
	public List<Product> findNews() throws Exception {
		String sql = "select * from product where pflag = 0 order by pdate desc limit 0 , 9";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class));
	}
	
	@Override
	public List<Product> findAllProductsWithPage(int startIndex, int pageSize) throws Exception {
		String sql = "select * from product order by pdate desc limit ? , ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class),startIndex,pageSize);
	}

	@Override
	public int findTotalRecords(int cid) throws Exception {
		String sql = "select count(*) from product where cid = ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Long num = (Long)runner.query(sql, new ScalarHandler(),cid);
		return num.intValue();
	}

	@Override
	public List<Product> findProducts(int cid,int startIndex, int pageSize) throws Exception {
		String sql = "select * from product where cid = ? limit ? , ?";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		return runner.query(sql, new BeanListHandler<Product>(Product.class),cid,startIndex,pageSize);
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		String sql = "insert into product values(? ,? ,? ,? ,? ,? ,? ,? ,? ,?)";
		QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
		Object[] params = {product.getPid(),product.getPname(),product.getMarket_price(),product.getShop_price(),product.getPimage(),product.getPdate(),product.getIs_hot(),product.getPdesc(),product.getPflag(),product.getCid()};
		runner.update(sql,params);
	}
	

}
