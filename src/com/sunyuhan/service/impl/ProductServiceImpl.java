package com.sunyuhan.service.impl;

import java.util.List;

import com.sunyuhan.dao.ProductDao;
import com.sunyuhan.dao.UserDao;
import com.sunyuhan.dao.impl.ProductDaoImpl;
import com.sunyuhan.domain.PageModel;
import com.sunyuhan.domain.Product;
import com.sunyuhan.service.ProductService;
import com.sunyuhan.utils.BeanFactory;

/**
*@author SunYuHan
*2018年11月26日下午6:22:23
*
*/
public class ProductServiceImpl implements ProductService {
	ProductDao productDao = (ProductDao)BeanFactory.createObject("ProductDao");
	@Override
	public List<Product> findHots() throws Exception {
		return productDao.findHots();
	}

	@Override
	public List<Product> findNews() throws Exception {
		return productDao.findNews();
	}
	@Override
	public Product findProductByPid(String pid) throws Exception {
		return productDao.findProductByPid(pid);
	}
	@Override
	public PageModel findProducts(int cid,int curNum) throws Exception {
		int totalRecords = productDao.findTotalRecords(cid);
		PageModel pm = new PageModel(curNum,totalRecords);
		List<Product> list = productDao.findProducts(cid,pm.getStartIndex(),pm.getPageSize());
		pm.setRecords(list);
		pm.setUrl("ProductServlet?method=findPage&cid="+cid);
		return pm;
	}

	@Override
	public PageModel findAllProductsWithPage(int curNum) throws Exception {
		//创建对象
		int totalRecords = productDao.findTotalRecords();
		PageModel pm = new PageModel(curNum, totalRecords,5);
		//关联集合
		List<Product> list = productDao.findAllProductsWithPage(pm.getStartIndex(),pm.getPageSize());
		pm.setRecords(list);
		//关联url
		pm.setUrl("AdminProductServlet?method=findAllProductsWithPage");
		return pm;
	}

	@Override
	public void saveProduct(Product product) throws Exception {
		productDao.saveProduct(product);
		
	}
	

}
