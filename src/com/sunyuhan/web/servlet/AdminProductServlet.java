package com.sunyuhan.web.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import com.sunyuhan.domain.Category;
import com.sunyuhan.domain.PageModel;
import com.sunyuhan.domain.Product;
import com.sunyuhan.service.CategoryService;
import com.sunyuhan.service.ProductService;
import com.sunyuhan.service.impl.CategoryServiceImpl;
import com.sunyuhan.service.impl.ProductServiceImpl;
import com.sunyuhan.utils.UUIDUtils;
import com.sunyuhan.utils.UploadUtils;
import com.sunyuhan.web.base.BaseServlet;

/**
 * Servlet implementation class AdminProductServlet
 */
public class AdminProductServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
	public String findAllProductsWithPage(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//获取当前页
		int curNum = Integer.parseInt(request.getParameter("num"));
		//调用业务层
		ProductService productService = new ProductServiceImpl();
		PageModel pm = productService.findAllProductsWithPage(curNum);
		//保存数据
		request.setAttribute("page", pm);
		//跳转
		return "/admin/product/list.jsp";
	}
	public String addProductUI(HttpServletRequest request, HttpServletResponse response) throws Exception{
		//调用业务层获取分类
		ProductService productService = new ProductServiceImpl();
		CategoryService categoryService = new CategoryServiceImpl();
	    List<Category> list = categoryService.getAllCats();
		//保存数据
	    request.setAttribute("allCats", list);
		
		//跳转
		return "/admin/product/add.jsp";
	}
	public void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception{
		Map<String , String> map = new HashMap<String , String>();
		Product product = new Product();
		//获取全部数据
		DiskFileItemFactory fac = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(fac);
		List<FileItem> list = upload.parseRequest(request);
		for (FileItem item : list) {
			if (item.isFormField()) {
				map.put(item.getFieldName(), item.getString("UTF-8"));
			}else {
				String oldFileName = item.getName();//原始文件的名称
				String newFileName = UploadUtils.getUUIDName(oldFileName);
				InputStream is = item.getInputStream();
				String realPath = getServletContext().getRealPath("/products/3/");
				String dir = UploadUtils.getDir(newFileName);
				String path = realPath+dir;
				File newDir = new File(path);
				if (!newDir.exists()) {
					newDir.mkdirs();
				}
				File finalFile = new File(newDir,newFileName);
				if (!finalFile.exists()) {
					finalFile.createNewFile();
				}
				OutputStream os = new FileOutputStream(finalFile);
				IOUtils.copy(is, os);
				IOUtils.closeQuietly(is);
				map.put("pimage", "products/3"+dir+"/"+newFileName);
			}
		}
		BeanUtils.populate(product, map);
		product.setPid(UUIDUtils.getId());
		product.setPdate(new Date());
		product.setPflag(0);
		ProductService productService = new ProductServiceImpl();
		productService.saveProduct(product);
		response.sendRedirect("/WebShop/AdminProductServlet?method=findAllProductsWithPage&num=1");
	}
}
