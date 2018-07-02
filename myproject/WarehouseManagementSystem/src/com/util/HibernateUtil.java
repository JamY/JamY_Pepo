//MySessionFactory.java����������
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
	private static ThreadLocal<Session> threadLocal=new ThreadLocal<Session>();//ʹ���ֲ߳̾�����ģʽ����Session
	private HibernateUtil(){};
	static{
		sessionFactory=new Configuration().configure().buildSessionFactory();
	}
	//�ر�sessionFactory
	public static void closeSessionFactory(){
		sessionFactory.close();
	}
	//��ȡȫ�µ�session
	public static Session openSession(){
		return sessionFactory.openSession();
	}
	//��ȡ���̹߳�����session
	public static Session getCurrentSession(){
		Session session=threadLocal.get();
		if(session==null){
			session=sessionFactory.openSession();
			threadLocal.set(session);//��session�������õ�threadLocal���൱�ڸ�session�Ѿ����̰߳�
		}
		return session;
	}
	//�ṩһ�����Ӱ�ȫ�ر�CurrentSession�ķ���
	public static void closeCurrentSession(){
		Session s=getCurrentSession();
		if(s!=null&&s.isOpen()){
			s.close();
			threadLocal.set(null);
		}
	}
	//�ṩһ��ͳһ�Ĳ�ѯ����
	//from ���� where ����=?
	public static List executeQuery(String hql,String[] paramters){
		Session session=null;
		List list=null;
		try {
			session=openSession();//�˷����õ���session�ڲ�ѯ��ʱ���������ύ,��ɾ����Ҫ
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){//���ж��Ƿ��в���Ҫ��
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
	//�ṩһ��ͳһ�Ĳ�ѯ���� ��������֪�����Ƿ���һ������
	public static Object uniqueQuery(String hql,String[] paramters){
		Session session=null;
		Object obj=null;
		try {
			session=openSession();//�˷����õ���session�ڲ�ѯ��ʱ���������ύ,��ɾ����Ҫ
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){//���ж��Ƿ��в���Ҫ��
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
	//��ѯ����(����ҳ)
	public static List executeQueryByPage(String hql,String[] paramters,int pageSize,int pageNow){
		Session session=null;
		List list=null;
		try {
			session=openSession();//�˷����õ���session�ڲ�ѯ��ʱ���������ύ,��ɾ����Ҫ
			Query query=session.createQuery(hql);
			if(paramters!=null&&paramters.length>0){//���ж��Ƿ��в���Ҫ��
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
	//����id���ض���ķ���
	public static Object findById(Class clazz,java.io.Serializable id){
		Session session=null;
		Transaction tx=null;
		Object obj=null;
		try {
			session=openSession();
			tx=session.beginTransaction();
			obj=session.load(clazz, id);
			Hibernate.initialize(obj);//��ʼ��������� 
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
	//���
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
	//�޸�
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
	//ɾ��
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
	//�޸ĺ�ɾ��
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
			query.executeUpdate();//executeUpdateִ��ɾ��/���µ����
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
