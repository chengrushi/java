package action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import entity.Students;
import service.StudentsDAO;
import serviceImpl.StudentsDAO_Impl;

/*
 * ѧ��Action��
 * */
public class StudentsAction extends SuperAction {

	private static final long serialVersionUID = 1L;
	
	//��ѯ����ѧ���Ķ���
	public String query(){
		
		 //action�е����Ѿ�д�õĽӿ���ʵ�֣�
		StudentsDAO sdao = new StudentsDAO_Impl();
		List <Students> list = sdao.queryAllStudents();   /*list�п����ǿգ��ڽӿ�ʵ�����ѿ��Ǵ����*/
		//�Ž�session��
		if(list!=null&&list.size()>0){
			
			/*�̳���SuperAction��������Http�Ự����*/
			session.setAttribute("students_list", list);
		}
		return  "query_success";
	}
	
	//ɾ��ָ��ѧ���Ķ���
	public String delete(){
		
		StudentsDAO sdao = new StudentsDAO_Impl();
		String sid = request.getParameter("sid");    //��ô������Ĳ���
		sdao.deleteStudents(sid);   //action�е����Ѿ�д�õĽӿ���ʵ�֣�
		
		return "delete_success";
	}
	
	
	//���ѧ������
	public String add() throws ParseException{
		
		/*�½�һ��ѧ������*/
		Students s = new Students();
		
		/*Ϊѧ������s������Ӧ��Ϣ*/
		s.setSname(request.getParameter("sname"));
		s.setGender(request.getParameter("gender"));
		s.setAddress(request.getParameter("address"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String birthdayString = request.getParameter("birthday");
		Date s_birthday = sdf.parse(birthdayString);    //����û��Ҫ�½�һ�����󣡿����String��Stringû���½�����

		s.setBirthday(s_birthday);
		
		/*��controller��������Ѿ�д�õĽӿ�ʵ�����ѧ��*/
		StudentsDAO sdao = new StudentsDAO_Impl();
		sdao.addStudents(s);
		
		return "add_success";
		
	}
	
	//�޸�ѧ�����ϣ���ѧ��ԭ����Ϣչʾ��ҳ����
	public String modify(){
		
		String sid = request.getParameter("sid");
		StudentsDAO sdao = new StudentsDAO_Impl();
		Students s = sdao.queryStudentBysid(sid);
		
		//�����ڻỰ��
		session.setAttribute("modify_students", s);
		
		return "modify_success";
	}
	
	//�����޸ĺ��ѧ������
	public String  save() throws ParseException{
		Students s = new Students();
		s.setSid(request.getParameter("sid"));
		s.setSname(request.getParameter("sname"));
		s.setGender(request.getParameter("gender"));
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String s_birthday = request.getParameter("birthday");
		Date birthday = sdf.parse(s_birthday);
		s.setBirthday(birthday);
		
		s.setAddress(request.getParameter("address"));
		
		/*�����Ѿ�д�õĽӿ�*/
		StudentsDAO sdao = new StudentsDAO_Impl();
		sdao.updateStudents(s);
		
		return "save_success";
	}

}
