package com.sunyuhan.test;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.junit.Test;

import com.sunyuhan.domain.OrderItem;
import com.sunyuhan.domain.Product;
import com.sunyuhan.utils.JDBCUtils;

/**
*@author SunYuHan
*2018年11月29日上午9:16:55
*
*/
public class TestMapListHandler {
	//根据订单id查询每笔订单下所有订单项及商品详细信息
	//SQl分析
			/*
			select * from orderitem , product //笛卡尔积
			select * from orderitem o , product p where o.pid = p.pid //笛卡尔积基础上筛选有意义的数据
			select * from orderitem o , product p where o.pid = p.pid and oid = '1B271B7F0B7442FA96F1354264D3228C' //最终结果
			*/
	@Test
	public void test00() {
		try {
			List<OrderItem> list2 = new ArrayList<OrderItem>();
			
			String sql = "select * from orderitem o , product p where o.pid = p.pid and oid = '1B271B7F0B7442FA96F1354264D3228C'";
			QueryRunner runner = new QueryRunner(JDBCUtils.getDataSource());
			//由于返回的数据来自多个表，多行数据MapListHandler封装返回的数据，返回结果是List，list里每个元素是Map
			List<Map<String,Object>> list = runner.query(sql, new MapListHandler());
			for (Map<String, Object> map : list) {
				/*
				//输出Map中的内容
				for (Map.Entry<String, Object> entry : map.entrySet()) {
					System.out.print(entry.getKey()+" "+entry.getValue()+" ");
				}
				System.out.println();
				*/
				
				OrderItem orderItem = new OrderItem();
				Product product = new Product();
					// 由于BeanUtils将字符串"1992-3-3"向user对象的setBithday();方法传递参数有问题,手动向BeanUtils注册一个时间类型转换器
					// 1_创建时间类型的转换器
					DateConverter dt = new DateConverter();
					// 2_设置转换的格式
					dt.setPattern("yyyy-MM-dd");
					// 3_注册转换器
					ConvertUtils.register(dt, java.util.Date.class);
				//将map中属于orderitem的数据自动填充到orderitem对象上
				BeanUtils.populate(orderItem, map);
				//将map中属于product的数据自动填充到product对象上
				BeanUtils.populate(product, map);
				//让orderitem和product关联
				orderItem.setProduct(product);
				//所有订单项都装进list2
				list2.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	//@Test
	public void testForeachMap() {
		Map<String , String> map = new HashMap<String,String>();
		map.put("111", "aaa");
		map.put("222", "bbb");
		map.put("333", "ccc");
		//foreach遍历map
		for (Map.Entry<String, String> entry : map.entrySet()) {
			System.out.println(entry.getKey()+" "+entry.getValue());
		}
	}	
	
	
}
