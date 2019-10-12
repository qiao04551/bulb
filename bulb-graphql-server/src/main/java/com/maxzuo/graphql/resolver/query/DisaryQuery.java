package com.maxzuo.graphql.resolver.query;

import com.maxzuo.graphql.resolver.GraphQLQueryResolver;
import com.maxzuo.graphql.vo.DiaryVO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 日记查询
 * <p>
 * Created by zfh on 2019/08/19
 */
@Component
public class DisaryQuery implements GraphQLQueryResolver {

    /**
     * 查询日记
     */
    public DataFetcher<List<DiaryVO>> queryDiaryHistory() {
        return new DataFetcher<List<DiaryVO>>() {
            @Override
            public List<DiaryVO> get(DataFetchingEnvironment environment) throws Exception {
                return null;
            }
        };
    }

    /**
     * 单篇日记详情
     */
    public DataFetcher<DiaryVO> queryDiary() {
        return new DataFetcher<DiaryVO>() {
            @Override
            public DiaryVO get(DataFetchingEnvironment environment) throws Exception {
                return null;
            }
        };
    }
}
