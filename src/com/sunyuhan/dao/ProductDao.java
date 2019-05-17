package com.sunyuhan.dao;

import java.util.List;

import com.sunyuhan.domain.Product;

/**
*@author SunYuHan
*2018年11月26日下午6:23:06
*
*/
public interface ProductDao {

	List<Product> findHots()throws Exception;

	List<Product> findNews()throws Exception;

	Product findProductByPid(String pid)throws Exception;

	int findTotalRecords(int cid)throws Exception;

	List<Product> findProducts(int cid,int startIndex, int pageSize)throws Exception;

	int findTotalRecords()throws Exception;

	List<Product> findAllProductsWithPage(int startIndex, int pageSize)throws Exception;

	void saveProduct(Product product)throws Exception;



}
