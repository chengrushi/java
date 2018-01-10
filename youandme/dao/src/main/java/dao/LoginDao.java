package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;

public interface LoginDao {
    /**
     * �����û�����ע���ʺ�
     * @param username
     * @param password
     * @return
     */
    int insertUser(@Param("username") String username,
                   @Param("password") String password,
                   @Param("email") String email,
                   @Param("joinTime") String joinTime);


    /**
     * ��¼������ȫ��User�Ƿ�����Ӧ�û���������
     * Ҫ���û��������������Ψһ�ģ����û���������Ҳ��һ����
     * ���������ֻ����һ��User
     * @param stringToLogin
     * @param password
     * @return
     */
    User selectUserFromAllUser(@Param("stringToLogin") String stringToLogin,@Param("password") String password);
}
