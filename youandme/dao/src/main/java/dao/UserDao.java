package dao;

import entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {

    /**
     * �����û�Ψһ��ʶ����ͷ��
     * @param userId
     * @return
     */
    int updateUserHeadImg( @Param("userId") int userId,
                           @Param("headImg") String headImg);

    /**
     * �����û�id�����û�ȫ����Ϣ�����ڸ���session
     * @param userId
     * @return
     */
    User selectUserById(@Param("userId") int userId);


    /**
     * �û������û�����������ı���Ϣ
     * @param userId
     * @param username
     * @param email
     * @param address
     * @param description
     * @return
     */
    int updateUser(@Param("userId") int userId,
                   @Param("username") String username,
                   @Param("email") String email,
                   @Param("address") String address,
                   @Param("description") String description);

    /**
     * �û�ÿ����һ�ζ�̬���䶯̬������+1
     * @param userId
     * @return
     */
    int updateDynamicsNum(@Param("userId") int userId);

    List<User> selectAllUserForLucene();
}
