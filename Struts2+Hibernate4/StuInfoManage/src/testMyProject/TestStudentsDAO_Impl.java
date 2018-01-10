package testMyProject;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import service.StudentsDAO;
import serviceImpl.StudentsDAO_Impl;

import entity.Students;

public class TestStudentsDAO_Impl {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//�������ö���
		Configuration config = new Configuration().configure();
		//��������ע�����
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(config.getProperties()).buildServiceRegistry();
		//�����Ự��������
		sessionFactory = config.buildSessionFactory(serviceRegistry);
		//�Ự����
		session = sessionFactory.openSession();
		//��������
		transaction = session.beginTransaction();
	}
	
	
	@After
	public void destory(){
		transaction.commit(); //�ύ����
		session.close(); //�رջỰ�������ͷſ��ܵ������ݿ����ӳ����
		sessionFactory.close(); //�رջỰ����	
	}
	
	@Test
	public void testQueryAllStudents(){
		
		/*
		 * ע�������д����
		 * */
		 StudentsDAO sdao = new StudentsDAO_Impl();      //sdaoΪ�ӿڣ�
		 List<Students> list = sdao.queryAllStudents();     
		 
		 for(int i=0;i<list.size();i++)
		 {
			 System.out.println("ѧ��������"+list.get(i).getSname());
		 }
	}
}
