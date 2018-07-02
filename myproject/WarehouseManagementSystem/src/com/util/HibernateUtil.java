//MySessionFactory.java工具类升级
package com.util;

import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static SessionFactory sessionFactory=null;
	private static ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();//使用线程局部变量模式管理Session
	private HibernateUtil(){};
	static{
		sessionFactory=new Configuration().configure().buildSessionFactory();
	}
	//关闭sessionFactory
	public static void closeSessionFactory(){
		sessionFactory.close();
	}
	//获取全新的session
	public static Session openSession(){
		return sessionFactory.openSession();
	}
	//获取和线程关联的session
	public static Session getCurrentSession(){
		Session session=threadLocal.get();
		if(session==null){
			session=sessionFactory.openSession();
			threadLocal.set(session);//把session对象设置到threadLocal，相当于该session已经和线程绑定
		}
		return session;
	}
	//提供一个更加安全关闭CurrentSession的方法
	public static void closeCurrentSession(){
		Session s=getCurrentSession();
		if(s!=null&&s.isOpen()){
			s.close();
			threadLocal.set(null);
		}
	}
	//提供一个统一的查询方法
	//from 类名 where 条件=?
	public static List executeQuery(String hql,String[] paramters){
		Session session=null;
		List list=null;
		try {
			session=openSession();//此方法得到的session在查询的时候不需事务提交,增删改需要
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){//先判定是否有参数要绑定
				for(int i=0;i<paramters.length;i++){
					query.setString(i, paramters[i]);
				}
			}
			list=query.list();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	//提供一个统一的查询方法 但是我们知道总是返回一个对象
	public static Object uniqueQuery(String hql,String[] paramters){
		Session session=null;
		Object obj=null;
		try {
			session=openSession();//此方法得到的session在查询的时候不需事务提交,增删改需要
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){//先判定是否有参数要绑定
				for(int i=0;i<paramters.length;i++){
					query.setString(i, paramters[i]);
				}
			}
			obj=query.uniqueResult();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return obj;
	}
	//查询方法(带分页)
	public static List executeQueryByPage(String hql,String[] paramters,int pageSize,int pageNow){
		Session session=null;
		List list=null;
		try {
			session=openSession();//此方法得到的session在查询的时候不需事务提交,增删改需要
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){//先判定是否有参数要绑定
				for(int i=0;i<paramters.length;i++){
					query.setString(i, paramters[i]);
				}
			}
			query.setFirstResult((pageNow-1)*pageSize).setMaxResults(pageSize);
			list=query.list();
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return list;
	}
	//根据id返回对象的方法
	public static Object findById(Class clazz,java.io.Serializable id){
		Session session=null;
		Transaction tx=null;
		Object obj=null;
		try {
			session=openSession();
			tx=session.beginTransaction();
			obj=session.load(clazz, id);
			Hibernate.initialize(obj);//初始化代理对象 
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
		return obj;
	}
	//添加
	public static void save(Object obj){
		Session s=null;
		Transaction tx=null;
		try {
			s=openSession();
			tx=s.beginTransaction();
			s.save(obj);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(s.isOpen()&&s!=null){
				s.close();
			}
		}
	}
	//修改
	public static void update(Object obj){
		Session s=null;
		Transaction tx=null;
		try {
			s=openSession();
			tx=s.beginTransaction();
			s.update(obj);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(s.isOpen()&&s!=null){
				s.close();
			}
		}
	}
	//删除
	public static void delete(Object obj){
		Session s=null;
		Transaction tx=null;
		try {
			s=openSession();
			tx=s.beginTransaction();
			s.delete(obj);
			tx.commit();
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			throw new RuntimeException(e.getMessage());
		}finally{
			if(s.isOpen()&&s!=null){
				s.close();
			}
		}
	}
	//修改和删除
	public static void executeUpdate(String hql,String []paramters){
		Session session=null;
		Transaction tx=null;
		try {
			session=openSession();
			tx=session.beginTransaction();
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){
				for(int i=0;i<paramters.length;i++){
					query.setString(i, paramters[i]);
				}
			}
			query.executeUpdate();//executeUpdate执行删除/更新的语句
			tx.commit();
		} catch (Exception e) {
			tx.rollback();
			throw new RuntimeException(e.getMessage());
		}finally{
			if(session!=null&&session.isOpen()){
				session.close();
			}
		}
	}
}
