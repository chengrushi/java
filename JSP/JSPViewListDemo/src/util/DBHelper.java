package util;
/*
 * �������ݿ�
 * */
import java.sql.Connection;
import java.sql.DriverManager;
public class DBHelper {
	
	/*�ڴ�֮ǰҪ����mysql connector jar��*/
	
	private static final String driver="com.mysql.jdbc.Driver";	
	/*3306��mysql���ݿ�Ķ˿ڣ�8001��Apache�Ķ˿ڣ�����PHP��*/
	private static final String url="jdbc:mysql://localhost:3306/mystream?useUnicode=true&characterEncoding=UTF-8";	
	private static final String username="root";
	private static final String password="";
   private static Connection conn = null;;
	
	/*��̬����鸺���������*/
	static
	{
		try
		{
			Class.forName(driver).newInstance();
			System.out.print("�ɹ���������");
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	}
	
	/*����ģʽ�������ݿ����Ӷ���*/
	public static Connection getConnection() throws Exception
	{
		if(conn == null)
		{
			conn = DriverManager.getConnection(url, username, password);
			return conn;
		}
		return conn;
		
	}
	
	/*�ڴ˼���Ƿ��ܳɹ��������ݿ�*/
	public static void main(String[] args) {
		try
		{
			Connection conn = DBHelper.getConnection();
			if(conn!=null)
			{
				System.out.print("���ݿ���������");
			}
			else
			{
				System.out.print("���ݿ����Ӵ���");
			}
		}
		catch(Exception ex)
		{
		    ex.printStackTrace();
		}
	}
	
}