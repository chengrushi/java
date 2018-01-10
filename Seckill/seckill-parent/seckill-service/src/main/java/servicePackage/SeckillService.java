package servicePackage;


import dtoPackage.Exposer;
import dtoPackage.SeckillExecution;
import entityPackage.Seckill;
import exceptionPackage.RepeatKillException;
import exceptionPackage.SeckillCloseException;
import exceptionPackage.SeckillException;

import java.util.List;

/**ҵ��ӿڣ�վ�ڡ�ʹ���ߡ��Ƕ���ƽӿ�
 * �������棺�����������ȣ��������������ͣ�return ����/�쳣��
 * Created by Administrator on 2016/5/24.
 */
public interface SeckillService {

    /**
     * ��ѯ������ɱ��¼
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * ��ѯ������ɱ��¼
     * @param seckillId
     * @return
     */
    Seckill getById(int seckillId);

    /**
     *��ɱ����ʱ�����ɱ�ӿڵ�ַ
     * �������ϵͳʱ�����ɱʱ��
     * @param seckillId
     */
    Exposer exportSeckillUrl(int seckillId);

    /**
     * ִ����ɱ����
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckill(int seckillId, String userPhone, String md5)
        throws SeckillException, RepeatKillException, SeckillCloseException;

    /**
     * ִ����ɱ����By�洢����
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SeckillExecution executeSeckillProcedure(int seckillId, String userPhone, String md5);
}
