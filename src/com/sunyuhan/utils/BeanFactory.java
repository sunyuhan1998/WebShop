package com.sunyuhan.utils;

import java.io.InputStream;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
*@author SunYuHan
*2018年12月3日下午5:31:21
*
*/
public class BeanFactory {
	//解析application.xml，通过传递过来的name获取对应的class
	public static Object createObject(String name){
			try {
				//获取Document对象
				SAXReader reader = new SAXReader();
				InputStream is = BeanFactory.class.getClassLoader().getResourceAsStream("application.xml");
				Document docu = reader.read(is);
				//通过Document对象获取根节点Beans
				Element rootElement = docu.getRootElement();
				//通过根节点获取根节点下所有子节点bean，返回集合
				List<Element> list = rootElement.elements();
				//遍历集合，判断每个元素的id和当前的name是否一致
				for (Element ele : list) {//ele即是Beans根节点下每个Bean。
					//获取到当前节点的id属性值
					String id = ele.attributeValue("id");
					if (id.equals(name)) {
						//一致则获取class
						String str = ele.attributeValue("class");
						Class clazz = Class.forName(str);
						//通过反射创建对象返回
						return clazz.newInstance();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
	}
}
