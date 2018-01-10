package dao;

import entity.SocialDynamics;
import entity.User;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface DynamicsDao {

    /**
     * ����̬�������ݿ�
     * @param userId
     * @param dynamicsText
     * @param dynamicsFile
     * @return
     */
    int insertDynamics(@Param("userId") int userId,
                       @Param("dynamicsText") String dynamicsText,
                       @Param("dynamicsFile") String dynamicsFile,
                       @Param("createTime") Timestamp createTime);


    /**
     * ��ҳ�������ݿ����ȫ�����˶�̬������ʾ�����û�ͷ�񣬲���ʾ���۵�
     * @return
     */
    List<SocialDynamics> selectAllDynamics();

    /**
     * ���ҵ�ǰҳ�涯̬���������ֵ
     * @return
     */
    String selectMaxDynamicsId();

    /**
     * ���ұȲ���dynamicsId��Ķ�̬��������ʾ�����û�ͷ�񣬲���ʾ���۵ȣ��û�ajaxҳ�沿��ˢ��
     * @param dynamicsId
     * @return
     */
    List<SocialDynamics> selectDynamicsFromPos(@Param("dynamicsId") int dynamicsId);


    /**
     * ���޲�������̬����like_num +1
     * @param dynamicsId
     * @return
     */
    int updateLikeNum(@Param("dynamicsId") int dynamicsId);

    /**
     * ȡ�����޲�������̬����like_num -1
     * @param dynamicsId
     * @return
     */
    int updateLikeNumSub(@Param("dynamicsId") int dynamicsId);

    /**
     * ���޲����������û�id�����޵Ķ�̬id
     * @param dynamicsId
     * @param userId
     * @return
     */
    int insertLike(@Param("dynamicsId") int dynamicsId,@Param("userId") int userId);

    /**
     * ȡ�����޲�����ɾ���û����޹��Ķ�̬�ļ�¼
     * @param dynamicsId
     * @param userId
     * @return
     */
    int deleteLike(@Param("dynamicsId") int dynamicsId,@Param("userId") int userId);

    /**
     * �鿴�Ƿ��û��е��޲���������У�˵���ظ����ޣ���ȡ�����ޣ����û�У����ǵ�һ�ε��ޣ��ǵ��޲�����
     * @param dynamicsId
     * @param userId
     * @return
     */
    int selectLike(@Param("dynamicsId") int dynamicsId,@Param("userId") int userId);

    /**
     * �鿴��ǰ��̬�ĵ�����
     * @param dynamicsId
     * @return
     */
    int selectLikeNum(@Param("dynamicsId") int dynamicsId);

    /**
     * �鿴��ǰ�û���������Щ��̬���û�������ҳʱ��ʾ��̬���޺��ġ�
     * @param userId
     * @return
     */
    List<Integer> selectWhichLike(@Param("userId") int userId);

    /**
     * �������ͼ������id��ʾ��̬��ϸ��Ϣ
     * @param dynamicsId
     * @return
     */
    SocialDynamics selectDetailDynamicsById(@Param("dynamicsId") int dynamicsId);

    /**
     * ����ָ����̬�ĵ����û���id��ͷ��
     * @param dynamicsId
     * @return
     */
    List<User> selectLikeUserOfDynamics(@Param("dynamicsId") int dynamicsId);
}
