package com.test;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.domain.TbGood;
import com.service.PickerService;
import com.util.HibernateUtil;

public class HashMapTest {
	public static void main(String args[]){
		//ͨ��value��ȡkeyֵ
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		map.put(0, "aa");
		map.put(1, "bb");
		for(int key:map.keySet()){
			if(map.get(key).equals("bb")){
				System.out.println(key+" "+"bb");
			};
		}
		//��ȡ���е�valueֵ keyֵ
		Collection<String> values=map.values();
		System.out.println(values.toArray(new String[values.size()])[0]);
		Set<Integer> key=map.keySet();
		System.out.println(key.toArray(new Integer[key.size()])[0]);
	}
}
