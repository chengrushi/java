package dao;

import entity.PluploadFile;
import org.apache.ibatis.annotations.Param;

import java.sql.Timestamp;
import java.util.List;

public interface PluploadDao {

    /**
     * �û�û�ϴ�һ���ļ��������ݿⱣ�������Ϣ
     * @param fileName
     * @param uploadUsername
     * @param uploadTime
     * @return
     */
    int insertFileInfo(@Param("fileName") String fileName,
                       @Param("uploadUsername") String uploadUsername,
                       @Param("uploadTime")Timestamp uploadTime);

    /**
     * �����û������Ҹ��û��ϴ���ȫ���ļ�
     * @param uploadUsername
     * @return
     */
    List<PluploadFile> selectFileByUsername(@Param("uploadUsername") String uploadUsername);

    PluploadFile selectFileById(@Param("id") int id);

    void deleteInfoOfFile(@Param("id") int id);
}
