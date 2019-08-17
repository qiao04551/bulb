package com.maxzuo.mybatis;

import com.alibaba.fastjson.JSONObject;
import com.maxzuo.mybatis.dao.ShopOrderInfoMapper;
import com.maxzuo.mybatis.entity.ScUserProfessionDO;
import com.maxzuo.mybatis.entity.ShopOrderInfoDO;
import com.maxzuo.mybatis.entity.UserAndOrderInfoDTO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.io.InputStream;
import java.util.concurrent.TimeUnit;

/**
 * Mybatis 使用示例
 * <p>
 * Created by zfh on 2019/08/16
 */
public class SimpleQuery {

    private SqlSessionFactory sqlSessionFactory;

    @Before
    public void init () {
        InputStream inputStream = SimpleQuery.class.getResourceAsStream("/mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

    /**
     * 简单查询
     * <pre>
     *    SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，没有任何理由丢弃它或重新创建另一个实例。
     *
     *    每个线程都应该有它自己的 SqlSession 实例。SqlSession 的实例不是线程安全的，因此是不能被共享的，
     *    所以它的最佳的作用域是请求或方法作用域。
     * </pre>
     */
    @Test
    public void testSimpleQuery () {
        // 默认不开启自动提交
        SqlSession sqlSession = sqlSessionFactory.openSession();
        try {
            ShopOrderInfoMapper mapper = sqlSession.getMapper(ShopOrderInfoMapper.class);

            ShopOrderInfoDO shopOrderInfo = mapper.selectShopOrderInfoByPrimaryKey(1);
            System.out.println(shopOrderInfo);

            sqlSession.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (sqlSession != null) {
                sqlSession.close();
            }
        }
    }

    /**
     * 复合查询
     * <pre>
     *   使用association、collection标签
     * </pre>
     */
    @Test
    public void testAssociationQuery (){
        SqlSession sqlSession = sqlSessionFactory.openSession();
        ShopOrderInfoMapper mapper = sqlSession.getMapper(ShopOrderInfoMapper.class);
        /// 类型一
        // UserAndOrderInfoDTO userAndOrderInfoDTO = mapper.selectUserAndOrderInfo(1);

        // 类型二
        UserAndOrderInfoDTO userAndOrderInfoDTO = mapper.selectUserAndOrderInfoTwo(1);
        System.out.println(JSONObject.toJSONString(userAndOrderInfoDTO));
    }

    /**
     * 一级缓存默认开启（sqlsession级别）
     * <pre>
     *    如果不想用缓存，直接在select节点中增加useCache="false"和flushCache="true"属性即可：
     *    示例：
     *      <select id="selectShopOrderInfoByPrimaryKey" parameterType="int" resultMap="BaseResultMap" useCache="false"></select>
     * </pre>
     */
    @Test
    public void testLevelCache () {
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        ShopOrderInfoMapper mapper = sqlSession.getMapper(ShopOrderInfoMapper.class);
        while (true) {
            ScUserProfessionDO scUserProfessionDO = mapper.selectUserProfessionByUserId(1);
            System.out.println(JSONObject.toJSONString(scUserProfessionDO));

            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 二级缓存需要手动开启（Mapper级别，全局Application）
     * <pre>
     *   <cache />
     *
     *   某一个作用区域，mapper下进行了C/U/D操作后，默认该作用域下所有select中的缓存将被clear。
     *   示例：
     *     代码中更新了订单信息，可见职业信息的缓存被更新。
     * </pre>
     */
    @Test
    public void testSecondLevelCache () {
        while (true) {
            // 新会话
            SqlSession sqlSession = sqlSessionFactory.openSession(true);
            ShopOrderInfoMapper mapper = sqlSession.getMapper(ShopOrderInfoMapper.class);
            ScUserProfessionDO scUserProfessionDO = mapper.selectUserProfessionByUserId(1);
            System.out.println(JSONObject.toJSONString(scUserProfessionDO));

            try {
                TimeUnit.SECONDS.sleep(5);

                mapper.updateShopOrderInfoByPrimaryKey(1, 1);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                sqlSession.close();
            }
        }
    }
}
