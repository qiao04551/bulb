package com.maxzuo.graphql.resolver.query;

import com.maxzuo.graphql.resolver.GraphQLQueryResolver;
import com.maxzuo.graphql.vo.MonkeyVO;
import com.maxzuo.graphql.vo.TigerVO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 查询动物
 * <p>
 * Created by zfh on 2019/08/22
 */
@Component
public class AnimalQuery implements GraphQLQueryResolver {

    /**
     * 查询猴子
     */
    public DataFetcher<MonkeyVO> queryMonkey() {
        return new DataFetcher<MonkeyVO>() {
            @Override
            public MonkeyVO get(DataFetchingEnvironment environment) throws Exception {
                MonkeyVO monkeyVO = new MonkeyVO();
                monkeyVO.setId(1);
                monkeyVO.setAge(22);
                monkeyVO.setName("monkey");
                monkeyVO.setHeight(120);
                return monkeyVO;
            }
        };
    }

    /**
     * 查询老虎
     */
    public DataFetcher<TigerVO> queryTiger () {
        return new DataFetcher<TigerVO>() {
            @Override
            public TigerVO get(DataFetchingEnvironment environment) throws Exception {
                TigerVO tigerVO = new TigerVO();
                tigerVO.setId(1);
                tigerVO.setAge(22);
                tigerVO.setName("tiger");
                tigerVO.setWeight(100);
                return tigerVO;
            }
        };
    }

    /**
     * 查询-联合类型
     */
    public DataFetcher<Object> searchUnion () {
        return new DataFetcher<Object>() {
            @Override
            public Object get(DataFetchingEnvironment environment) throws Exception {
                Integer id = environment.getArgument("id");
                if (id == 1) {
                    MonkeyVO monkeyVO = new MonkeyVO();
                    monkeyVO.setId(1);
                    monkeyVO.setAge(22);
                    monkeyVO.setName("monkey");
                    monkeyVO.setHeight(120);
                    return monkeyVO;
                }
                if (id == 2) {
                    TigerVO tigerVO = new TigerVO();
                    tigerVO.setId(2);
                    tigerVO.setAge(22);
                    tigerVO.setName("tiger");
                    tigerVO.setWeight(100);
                    return tigerVO;
                }
                return null;
            }
        };
    }
}
