package complexService.download.impl;

import complexService.download.downloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import service.youandmeService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

@Component
public class downloadServiceImpl implements downloadService {

    @Autowired youandmeService youandmeService;

    public void downloadSolve(int id, HttpServletRequest request, HttpServletResponse response, int userId) throws ServletException, IOException {
        //�����ļ�id�����ݿ��л�ȡ�ļ���
        String fileName = (youandmeService.showFileOfId(id)).getFileName();
        //�ļ�����Ŀ¼·��
        String filePath = request.getSession().getServletContext().getRealPath("/")+"user_space/"+userId+"/pluploadDir/";
        //�õ����ļ�
        File file = new File(filePath+fileName);
        if(!file.exists()){
            System.out.println("Have no such file!");
            return;//�ļ������ھ��˳�����
        }

        FileInputStream fileInputStream = new FileInputStream(file);
        //����Http��Ӧͷ��������������������
        response.setHeader("Content-Disposition", "attachment;Filename=" + URLEncoder.encode(fileName, "UTF-8"));
        OutputStream outputStream = response.getOutputStream();
        byte[] bytes = new byte[2048];
        int len = 0;
        while ((len = fileInputStream.read(bytes))>0){
            outputStream.write(bytes,0,len);
        }
        fileInputStream.close();
        outputStream.close();
    }
}

