package dao;

/*
 * 
 * ��Ʒҵ���߼���*/

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import util.DBHelper;

import entity.Items;


public class ItemsDAO {
	
	/*�ڴ˼���Ƿ��ܷ��ؽ��*/
	public static void main(String[] args) {
		Connection conn = null;
	   	PreparedStatement stmt = null;
	   	ResultSet rs = null;
	   	ArrayList<Items> list = new ArrayList<Items>();
	   	try
		 {
			 conn = DBHelper.getConnection();
			 if(conn!=null)
			 {
		   		 String str = "select * from goods";
		   		 stmt = conn.prepareStatement(str);
		   		 rs = stmt.executeQuery();
		   		 while(rs.next())
		   		 {
		   			 Items item= new Items();
		   			 item.setId(rs.getInt("id"));
		   			 item.setGoodsname(rs.getString("goodsname"));
		   			 item.setPicture(rs.getString("picture"));
		   			 list.add(item);
		   		 }
		   		 for(int i=0;i<list.size();i++)
		   		 {
		   			   		 System.out.print(list.get(i).getGoodsname()); 
		   		 }
	
			 }
		 }
		 catch(Exception ex)
		 {
			 ex.printStackTrace();
		 }
	}
	
	
     public ArrayList<Items> getAllItems()
     {
    	 Connection conn = null;
    	 PreparedStatement stmt = null;
    	 ResultSet rs = null;
    	 ArrayList<Items> list = new ArrayList<Items>();   /*��Ʒ����*/
    	 
    	 try
    	 {
    		 conn = DBHelper.getConnection();
    		 if(conn!=null)
    		 {
        		 String str = "select * from goods";
        		 stmt = conn.prepareStatement(str);
        		 rs = stmt.executeQuery();
        		 while(rs.next())
        		 {
        			 Items item= new Items();
        			 item.setId(rs.getInt("id"));
        			 item.setGoodsname(rs.getString("goodsname"));
        			 item.setPicture(rs.getString("picture").substring(3));
        			 list.add(item);
        		 }    			 
        		 return list;
    		 }
    		 else
    		 {
    			 return null;
    		 }

    	 }
    	 catch(Exception ex)
    	 {
    		 ex.printStackTrace();
    		 return null;
    	 }
    	 finally
    	 {
    		 /*�ͷ����ݼ�����*/
    		 if(rs!=null)
    		 {
    			 try
    			 {
    				 rs.close();
        			 rs = null;
    			 }
    			 catch(Exception ex)
    			 {
    				 ex.printStackTrace();
    			 }
    		 }
    		 
    		 /*�ͷ���伯����*/
    		 if(stmt!=null)
    		 {
    			 try
    			 {
    				 stmt.close();
        			 stmt = null;
    			 }
    			 catch(Exception ex)
    			 {
    				 ex.printStackTrace();
    			 }
    		 }
    	 }
     }
     
     //������Ʒid�����Ʒ����
     public Items getItemsByid(int id)
     {
    	 Connection conn = null;
    	 PreparedStatement stmt = null;
    	 ResultSet rs = null;
    	 Items item= new Items();
    	 
    	 try
    	 {
    		 conn = DBHelper.getConnection();
    		 if(conn!=null)
    		 {
        		 String str = "select * from goods WHERE id=?";
        		 stmt = conn.prepareStatement(str);
        		 stmt.setInt(1, id);    /*���ã���һ������������id��*/
        		 rs = stmt.executeQuery();
        		 if(rs.next())
        		 {
        			 
        			 item.setId(rs.getInt("id"));
        			 item.setGoodsname(rs.getString("goodsname"));
        			 item.setPicture(rs.getString("picture").substring(3));
        			
        		 }    			 
    		 }
    	 }
    	 catch(Exception ex)
    	 {
    		 ex.printStackTrace();
    	 }
    	 finally
    	 {
    		 /*�ͷ����ݼ�����*/
    		 if(rs!=null)
    		 {
    			 try
    			 {
    				 rs.close();
        			 rs = null;
    			 }
    			 catch(Exception ex)
    			 {
    				 ex.printStackTrace();
    			 }
    		 }
    		 
    		 /*�ͷ���伯����*/
    		 if(stmt!=null)
    		 {
    			 try
    			 {
    				 stmt.close();
        			 stmt = null;
    			 }
    			 catch(Exception ex)
    			 {
    				 ex.printStackTrace();
    			 }
    		 }
    	 }
    	 return item;
     }
     
     /*��ȡǰ����Cookie��Ϣ*/
     public ArrayList<Items> getViewList(String list)
     {
    	 ArrayList<Items> listItems = new ArrayList<Items>();
    	 if(list!=null&&list.length()>0)
    	 {
    		 String[] arr = list.split(",");
    		 if(arr.length>=5)
    		 {
	    	     for(int i = arr.length-1;i>=arr.length-5;i--)
	    		 {
	    			 int id = Integer.parseInt(arr[i]);
	    			 listItems.add(getItemsByid(id));
	    		 }
    		 }
    		 else
    		 {
        	     for(int i = arr.length-1;i>0;i--)
        		 {
        			 int id = Integer.parseInt(arr[i]);
        			 listItems.add(getItemsByid(id));
        		 }
    		 }

    	 }
    	 return listItems;
     }
}
