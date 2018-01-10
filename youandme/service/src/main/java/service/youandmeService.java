package service;


import entity.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

public interface youandmeService {

    /**
     * ע��
     * @param username
     * @param password
     * @param email
     * @return
     */
    int register(String username,String password,String email);

    /**
     * ��¼���������û�����¼Ҳ�����������¼
     * @param stringToLogin
     * @param password
     * @return
     */
    User login(String stringToLogin,String password);

    /**
     * �û��ϴ���̬��Ĭ��ֻ��������+һ��ͼƬ��������+һ����Ƶ
     * @param request
     * @param userId
     */
    void postDynamics(HttpServletRequest request,int userId);

    /**
     * ˢ�½�����ҳʱ�鿴ȫ����̬
     * @return
     */
    List<SocialDynamics> showDynamics();

    /**
     * ���ص�ǰҳ�涯̬�������ֵ�����û�о���0
     * @return
     */
    int curMaxDynamicsId();

    /**
     * ��pos��ʼ�ֲ�ˢ���µĶ�̬
     * @param pos
     * @return
     */
    List<SocialDynamics> showNewDynamics(int pos);

    boolean changeHeadImg(HttpServletRequest request,int userId);

    /**
     * �û����ĸ�����Ϣ���ɹ�����true��ʧ�ܷ���false
     * @param userId
     * @param username
     * @param email
     * @param address
     * @param description
     * @return
     */
    boolean changePersonalInfo(int userId,String username,String email,String address,String description);

    User queryUserById(int userId);

    /**
     * �û����ޣ����ص��޺�ǰ��̬�����޵�����
     * @param dynamicsId
     * @param userId
     * @return
     */
    String clickLikeDynamics(int dynamicsId,int userId);

    List<Integer> showWhichLike(int userId);

    SocialDynamics showDetailDynamicsById(int dynamicsId);

    List<User> showLikeUserOfDynamics(int dynamicsId);

    /**
     * �û����ض���̬��������
     * @param dynamicsId
     * @param sendId
     * @param commentText
     * @return
     */
    CommentInfo sendComment(int dynamicsId,int sendId,String commentText);

    /**
     * ���ݶ�̬id���Ҹö�̬��ȫ������
     * @param dynamicsId
     * @return
     */
    List<CommentInfo> showCommentById(int dynamicsId);

    /**
     * ��������id����������Ϣ
     * @param commentId
     * @return
     */
    CommentInfo showComment(int commentId);

    /**
     * �û�����Զ�̬���۵Ļظ�������ReplyInfo����
     * @param commentId
     * @param sendId
     * @param replyText
     */
    ReplyInfo sendReply(int commentId,int sendId,String replyText);

    /**
     * �������۵�ȫ���ظ�
     * @param commentId
     * @return
     */
    List<ReplyInfo> showAllReplyByCommentId(int commentId);

    /**
     * �û������۵Ļظ����лظ�
     * @param replyId
     * @param sendId
     * @param replyText
     * @return
     */
    ReplyInfo sendReplyOfReply(int replyId,int sendId,String replyText);

    ReplyInfo showReplyInfo(int replyId);

    /**
     * ��ҳ�����û��������û���Lucene����
     * @param inputString
     * @return �û����󼯺ϣ������û����Ѵ��и���
     */
    List<User> luceneSearchUser(String inputString);

    void addMessage(int fromId,String fromName,int toId,String messageText,Timestamp messageDate);

    List<Message> showMessage(int fromId,int toId);

    void uploadInfo(String fileName,String uploadUsername,Timestamp uploadTime);

    List<PluploadFile> showUploadOfUser(String uploadUsername);

    /**
     * ����idɾ���Ѿ��ϴ����ļ�
     * @param request
     * @param userId
     * @param id
     */
    void deletePluploadFile(HttpServletRequest request,int userId,int id);

    PluploadFile showFileOfId(int id);


}
