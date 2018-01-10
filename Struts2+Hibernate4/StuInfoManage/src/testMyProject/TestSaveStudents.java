package testMyProject;

import java.util.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import entity.Students;

public class TestSaveStudents {

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
	
	/*
	 * ��Ӳ�������
	 * */
	@Test
	public void testSaveStudents(){
		/*
		 * ���������ݿ��е�����Ӧ����A_I����Ȼ�ᱨ����
		 * ���ݿ��е�sid��int����ʵ�����п�����String��Hibernate��ܻ��Զ�ת����
		 * */
		Students s1 = new Students("�Ƹ���~","��",new Date(),"�㶫����");
		session.save(s1);
	}
}
