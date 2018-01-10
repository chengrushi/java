package action;

import org.apache.struts2.interceptor.validation.SkipValidation;

import service.UsersDAO_Me;
import serviceImpl.UsersDAO_Me_Impl;

import com.opensymphony.xwork2.ModelDriven;

import entity.Users;

public class UsersAction extends SuperAction implements ModelDriven<Users> {

	private static final long serialVersionUID = 1L;
	private Users user = new Users();
	
	//�û���¼
	public String login(){
		UsersDAO_Me udao = new UsersDAO_Me_Impl();
		if(udao.usersLogin(user)){
			
			/*
			 * ��session�б����½�ɹ����û���
			 * UsersAction�̳���SuperAction����SuperAction����web���ö������ֱ��ʹ��
			 * */
			session.setAttribute("loginUserName", user.getUsername());
			return "login_success";
		}
		else{
			return "login_failure";
		}
	}
	
	@SkipValidation             /*��Ϊvalidate()Ĭ�϶�UsersAction������action����֤����������ע��ע��action��������֤*/
	//�û�ע��
	public String logout(){
		
		if(session.getAttribute("loginUserName")!=null){
			
			session.removeAttribute("loginUserName");
			session.invalidate();
			/*sessionId��û�������*/
		}
		return "logout_success";
	}
	
	@Override
	public Users getModel() {
		// TODO Auto-generated method stub
		return this.user;
	}

	@Override
	public void validate() {
		// TODO Auto-generated method stub
		
		/*ʹ��Ĭ�ϵı���֤����Ҫ��struts.xml�н������ã�
		 * <result name="input">/users/Users_login.jsp</result>
		 * */
		if("".equals(user.getUsername().trim())){
			/*trim()ȥǰ��ո�*/
			this.addFieldError("usernameError", "�û�������Ϊ�գ�");
		}
		if(user.getPassword().length()<6){
			this.addFieldError("passwordError", "���볤�Ȳ�������6λ��");
		}
		
	}
	
	

}
