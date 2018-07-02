package com.tools;

import java.awt.Font;
import java.util.Enumeration;

import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class MyTools {
	public static Font f1=new Font("微软雅黑", Font.BOLD, 16);
	public static Font f2 =new Font("宋体",Font.PLAIN, 14);
	public static Font f3=new Font("宋体",Font.BOLD,12);
	//统一字体
	public static void InitGlobalFont(Font font) {  
		  FontUIResource fontRes = new FontUIResource(font);  
		  for (Enumeration<Object> keys = UIManager.getDefaults().keys();  
		  keys.hasMoreElements(); ) {  
			  Object key = keys.nextElement();  
			  Object value = UIManager.get(key);  
			  if (value instanceof FontUIResource) {  
				  UIManager.put(key, fontRes);  
			  }  
		  } 
	}
}
