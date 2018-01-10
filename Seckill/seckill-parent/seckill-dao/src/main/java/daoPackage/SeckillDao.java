package daoPackage;

import entityPackage.Seckill;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/23.
 */
public interface SeckillDao {

    /**
     * �����
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(@Param("seckillId") int seckillId, @Param("killTime") Date killTime);

    /**
     *����id��ѯ��ɱ����
     * @param seckillId
     * @return
     */
    Seckill queryById(int seckillId);

    /**
     * ����ƫ������ѯ��ɱ��Ʒ�б�
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * ʹ�ô洢����ִ����ɱ
     * @param paramMap
     */
    void killByProcedure(Map<String,Object> paramMap);
}
