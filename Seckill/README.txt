1.����Ŀͨ��maven����,��ɱϵͳʵ�ֿ��ΪSpring+SpringMVC+MyBatis,��˿���˵��Maven+Spring+SpringMVC+MyBatis������
    �ο����ӣ�
          https://github.com/HuangFuGui/Javaweb/tree/master/SpringMVC%2BSpring%2BMyBatis/seckill

2.���ھ۵���ϣ�

    ���Ƕ�֪������һ��ϵͳ�ò���,���ھ۵������һ������Ҫ�ı�׼.���ھ�,��һ��ģ��ǡ����һ������,��������ģ���ڵĹ�����
    ϵ(����daoģ��ֻ�������ݿ�ķ��������,enumsģ��ֻ����ö��,redisģ��ֻ�������ݻ���);�����,��ָģ����ģ��֮�����ϵ
    Ҫ�����ٶ���.��ֻ�Ǽ򵥵ĵ��ù�ϵ,�������ģ��Ĺ�ϵ�Ƚϸ���,�Ǿ�Ҫ���ǽ�һ������.�ܶ���֮,���ھ۵����������ϵͳ
    �޸������.

    ����ڿ���������,Ӧ��������Ŀ�ķֿ鿪��,��ͳ��Javaweb����ģʽ�����ְ�����ʵ�ܺܺõ����ָ��ھ۵����.

3.̸̸maven��

    Maven�ṩ��һ����Ŀģ�黯�������̨�������Ѹ��ھ۵���ϵ����Է��ӵ�����.���ұȴ�ͳ�Ŀ���ģʽ���Ƚ�,�����ŶӼ��忪��,
    ��Ŀ�����Ͽ���˵���޷�Խ�,ͨ��Maven�����ȴ���һ����ģ��(parent),�ڸ�ģ���´�������ģ��(entity,dao,service,web��),��
    ����һ��ϵͳ�����ɸ������ֹ���ʱ����Ե÷ǳ���,A����entityģ��,B����daoģ��,C����serviceģ��...����֮��Ŀ���������
    ��,��Ϊ����ֻ��Ҫ������import/open�Լ���ģ�����,���ڴ�ͳ��Javaweb����ģʽ(���ְ������ַ�ģ���˼��)���ǰ첻����,��ͳ
    �Ŀ���ģʽÿ���˶�Ҫ����������Ŀ,��Ŀ���е�һ���׶�׼������ʱ��Ҫ�Ѹ��Ը���İ����ϵĽ��и���ճ����Ϊһ���µ���Ŀ,��
    maven�Ͳ���Ҫ,��Ϊ���Ǹ��Ը����ģ�鱾��������һ����ģ���µ�.

    Maven����Ҫ�ĺô����Ƿ�������,һ�㹫˾����ҵ�ὫһЩ�Լ���������Ҫ�����������������ֿ���,�����Ժ�Ҫ�õ��Ͳ���Ҫ�ٱ�д
    ,ֱ��������������Ϳ���ֱ�ӵ���,Ϊ�Ĳ�����������

    Maven���кܶ�ô�,����ͨ��dependency�Զ����ز�����jar��,��������Ҫ�ֶ�����,���ܹ�����ֲ���ȵ�...

4.��Ŀ�ṹ��

    parent
        dao
            pom.xml(jar)
        dto
            pom.xml(jar)
        entity
            pom.xml(jar)
        enums
            pom.xml(jar)
        exception
            pom.xml(jar)
        redis
            pom.xml(jar)
        service
            pom.xml(jar)
        web
            pom.xml(war)
        pom.xml(pom)

5.ע�����

    ģ��������ϵһ�����ܳ���ѭ����javaע��ģʽ��֧��ѭ����
    ����Ҫ�õ�SpringIOC�����ڵ�������Ҫ����@Autowiredע�⣨�����½�����,new��
    ��web.xml���ر�ע��������ļ�������˳��

    ��serviceģ���test�У�
        @RunWith(SpringJUnit4ClassRunner.class)
        @ContextConfiguration({
        "classpath:spring/spring-dao.xml",
        "classpath:spring/spring-Redisdao.xml",
        "classpath:spring/spring-service.xml"})
    Ŀ�ģ�
        ����dao�����ļ�,���ӳ�,mybatisʵ�������ϵ�spring������,redisdaoע�뵽spring������,service��Ӧ��ʵ������Ҫ������Щ
        ʵ����.ʵ�����е�@Serviceע����˼�ǽ�д�õ�SeckillServiceע�뵽SpringIOC������,�����Ϳ�����test��ֱ��@Autowired
        private SeckillService seckillService;�õ�ͳһ�Ľӿ�SeckillService.

    ��webģ���web.xml�У�
        <param-value>classpath:spring/spring-dao.xml,classpath:spring/spring-Redisdao.xml,classpath:spring/spring-service
        .xml,classpath:spring/spring-web.xml</param-value>
    Ŀ�ģ�
        ��Ϊweb.xml����tomcat���������,�������ʵ�����ʼ�����������ļ����ص�Servlet��,��ʵ����,ͳһ��ʵ�ֽӿ�,�Ѿ�
        ��spring������׼������.��ʱ���ã�

    spring-dao.xmlҪ��daoģ���ж���,spring-service.xml����serviceģ���ж���,��ģ��������ļ��ֿ�.

    һ����Ŀ��������
        web������service,service������dao,dao������entity,sqlSessionFactory....