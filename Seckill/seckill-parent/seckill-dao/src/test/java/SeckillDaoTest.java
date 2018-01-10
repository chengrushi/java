import daoPackage.SeckillDao;
import entityPackage.Seckill;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/6/13.
 */
/*
*����spring��junit�����ϣ�junit����ʱ����springIOC����
* */
@RunWith(SpringJUnit4ClassRunner.class)
//����junit spring�����ļ�
@ContextConfiguration({"classpath:spring/spring-dao.xml"})
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;
    @Test
    public void testReduceNumber() throws Exception {
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(6,killTime);
        System.out.println("updateCount:"+updateCount);
    }

    @Test
    public void testQueryById() throws Exception {
        int id = 6;
        Seckill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
    }

    @Test
    public void testQueryAll() throws Exception {
        List<Seckill> seckillList = seckillDao.queryAll(0,100);
        for (Seckill seckill:seckillList){
            System.out.println(seckill);
        }
    }
}