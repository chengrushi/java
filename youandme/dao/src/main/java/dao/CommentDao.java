package dao;

import entity.CommentInfo;
import entity.ReplyInfo;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface CommentDao {

    /**
     * �����ݿ��в�������
     * @param dynamicsId
     * @param sendId
     * @param receiveUsername
     * @param commentText
     * @return
     */
    int insertComment(@Param("dynamicsId") int dynamicsId,
                      @Param("sendId") int sendId,
                      @Param("receiveUsername") String receiveUsername,
                      @Param("commentText") String commentText,
                      @Param("commentTime") Timestamp commentTime);

    /**
     * ���ݶ�̬id���Ҹö�̬��ȫ������
     * @param dynamicsId
     * @return
     */
    List<CommentInfo> selectCommentByDynamicsId(@Param("dynamicsId") int dynamicsId);

    /**
     * ��������id����������Ϣ
     * @param commentId
     * @return
     */
    CommentInfo selectCommentById(@Param("commentId") int commentId);

    /**
     * ���ҵ�ǰ�û����µ��������ݣ��ں�User���󣩣�����js append
     * @param sendId
     * @return
     */
    CommentInfo selectNewestCommentOfUser(@Param("sendId") int sendId);

    /**
     * �����ݿ��в������۵Ļظ�
     * @param commentId
     * @param sendId
     * @param receiveUsername
     * @param replyText
     * @param replyTime
     * @return
     */
    int insertReply(@Param("commentId") int commentId,
                    @Param("sendId") int sendId,
                    @Param("receiveUsername") String receiveUsername,
                    @Param("replyText") String replyText,
                    @Param("replyTime") Timestamp replyTime);

    /**
     * �����û���id�������»ظ�������
     * @param sendId
     * @return
     */
    ReplyInfo selectReplyInfoBysendId(@Param("sendId") int sendId);

    /**
     * ��������id���Ҹ����۵����лظ�
     * @param commentId
     * @return
     */
    List<ReplyInfo> selectReplyInfoByCommentId(@Param("commentId") int commentId);

    /**
     * ���ݻظ���id���һظ���Ϣ
     * @param replyId
     * @return
     */
    ReplyInfo selectReplyInfoById(@Param("replyId") int replyId);
}
