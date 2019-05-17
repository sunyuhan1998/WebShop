package com.sunyuhan.service;

import java.util.List;

import com.sunyuhan.domain.PageModel;
import com.sunyuhan.domain.Product;

/**
*@author SunYuHan
*2018年11月26日下午6:19:11
*
*/
public interface ProductService {

	List<Product> findHots()throws Exception;

	List<Product> findNews()throws Exception;

	Product findProductByPid(String pid)throws Exception;

	PageModel findProducts(int cid,int curNum)throws Exception;

	PageModel findAllProductsWithPage(int curNum)throws Exception;

	void saveProduct(Product product)throws Exception;

}
