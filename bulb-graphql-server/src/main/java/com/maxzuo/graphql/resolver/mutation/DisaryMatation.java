package com.maxzuo.graphql.resolver.mutation;

import com.maxzuo.graphql.resolver.GraphQLMutationResolver;
import com.maxzuo.graphql.vo.DiaryVO;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.stereotype.Component;

/**
 * 日记变更
 * Created by zfh on 2019/08/22
 */
@Component
public class DisaryMatation implements GraphQLMutationResolver {

    /**
     * 发布日记
     */
    public DataFetcher<DiaryVO> publishDiary () {
        return new DataFetcher<DiaryVO>() {
            @Override
            public DiaryVO get(DataFetchingEnvironment environment) throws Exception {
                // TODO: 获取参数
                System.out.println(environment.getArguments());

                // TODO：变更逻辑

                // TODO: 响应结果
                DiaryVO diaryVO = new DiaryVO();
                diaryVO.setCardId(12);
                diaryVO.setDiaryId(10);
                diaryVO.setSummary("summary");
                diaryVO.setRichText("rich text");
                diaryVO.setPublishTime("2019-08-22");
                return diaryVO;
            }
        };
    }
}
