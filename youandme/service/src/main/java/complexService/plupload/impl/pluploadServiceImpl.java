package complexService.plupload.impl;

import complexService.plupload.pluploadService;
import entity.Plupload;
import entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import service.youandmeService;

import java.io.*;
import java.sql.Timestamp;
import java.util.Iterator;
import java.util.List;

@Component
public class pluploadServiceImpl implements pluploadService {

    @Autowired
    private youandmeService youandmeService;

    public void upload(Plupload plupload, File pluploadDir) {
        String fileName = ""+plupload.getName();//�ڷ������������ļ���
        upload(plupload, pluploadDir, fileName);
    }

    public void upload(Plupload plupload, File pluploadDir, String fileName) {
        int chunks = plupload.getChunks();//�û��ϴ��ļ����ָ����ܿ���
        int nowChunk = plupload.getChunk();//��ǰ�飬��0��ʼ

        //����Request�������͵�ǿ��ת�����ܳ��������ļ�����SpringIOC��������multipartResolver���󼴿ɡ�
        MultipartHttpServletRequest multipartHttpServletRequest  = (MultipartHttpServletRequest)plupload.getRequest();
        //���Է���map��ֻ��һ����ֵ��
        MultiValueMap<String,MultipartFile> map = multipartHttpServletRequest.getMultiFileMap();
        if(map!=null){
            try{
                Iterator<String> iterator = map.keySet().iterator();
                while(iterator.hasNext()){

                    String key = iterator.next();
                    List<MultipartFile> multipartFileList = map.get(key);

                    for(MultipartFile multipartFile:multipartFileList){//ѭ��ֻ����һ��

                        plupload.setMultipartFile(multipartFile);//�ֶ���Plupload������MultipartFile����ֵ
                        File targetFile = new File(pluploadDir+"/"+fileName);//�½�Ŀ���ļ���ֻ�б���д��ʱ�Ż���������
                        if(chunks>1){//�û��ϴ������ܿ�������1��Ҫ���кϲ�

                            File tempFile = new File(pluploadDir.getPath()+"/"+multipartFile.getName());
                            //��һ��ֱ�Ӵ�ͷд�룬���ô�ĩ��д��
                            savePluploadFile(multipartFile.getInputStream(),tempFile,nowChunk==0?false:true);

                            if(chunks-nowChunk==1){//ȫ�����Ѿ��ϴ���ϣ���ʱtargetFile��Ϊ�б���д������ڣ�Ҫ���ļ�����
                                tempFile.renameTo(targetFile);

                                //ÿ���ļ��ϴ���ϣ����ϴ���Ϣ�������ݿ�
                                Timestamp now = new Timestamp(System.currentTimeMillis());
                                youandmeService.uploadInfo(fileName,((User)(plupload.getRequest().getSession().getAttribute("user"))).getUsername(),now);
                            }
                        }
                        else{
                            //ֻ��һ�飬��ֱ�ӿ����ļ�����
                            multipartFile.transferTo(targetFile);

                            //ÿ���ļ��ϴ���ϣ����ϴ���Ϣ�������ݿ�
                            Timestamp now = new Timestamp(System.currentTimeMillis());
                            youandmeService.uploadInfo(fileName, ((User) (plupload.getRequest().getSession().getAttribute("user"))).getUsername(), now);
                        }
                    }
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void savePluploadFile(InputStream inputStream, File tempFile, boolean flag) {
        OutputStream outputStream = null;
        try {
            if(flag==false){
                //��ͷд��
                outputStream = new FileOutputStream(tempFile);
            }
            else{
                //��ĩ��д��
                outputStream = new FileOutputStream(tempFile,true);
            }
            byte[] bytes = new byte[1024];
            int len = 0;
            while ((len = (inputStream.read(bytes)))>0){
                outputStream.write(bytes,0,len);
            }
        }
        catch (FileNotFoundException e){
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                outputStream.close();
                inputStream.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
