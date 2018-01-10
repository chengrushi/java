package serviceImpl;

import java.util.List;


/*Ҫȫ��hibernate�İ�����Ȼ�ᱨ��*/
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import entity.Users;   //ʵ�����
import service.UsersDAO_Me;    //�ӿڰ�

public class UsersDAO_Me_Impl implements UsersDAO_Me  {

	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	@Override
	public boolean usersLogin(Users u) {
		// TODO Auto-generated method stub
				String hql = "";
				try
				{
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
					
					hql = "from Users where username =? and password =?";
					Query  query = (Query) session.createQuery(hql);
					query.setParameter(0, u.getUsername());
					query.setParameter(1, u.getPassword());
					List list = query.list();
					if(list.size()>0)
					{
						return true;
					}
					else
					{
						return false;
					}
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
					return false;
				}
				finally
				{
					if(transaction!=null)
					{
						transaction.commit(); //�ύ����
						session.close(); //�رջỰ�������ͷſ��ܵ������ݿ����ӳ����
						sessionFactory.close(); //�رջỰ����	
					}
				}
	}

}
