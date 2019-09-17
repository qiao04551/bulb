package com.maxzuo.elastic.example;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.junit.Test;

/**
 * ElasticSearch Search API的使用
 * <p>
 * Created by zfh on 2019/09/17
 */
public class SearchApiExample extends DocumentApiExample {

    /**
     * 全文查询
     * <pre>
     *   search API允许执行搜索查询并返回匹配查询的搜索结果。它可以跨一个或多个索引和一个或多个类型执行。
     * </pre>
     */
    @Test
    public void testSearchData () {
        SearchResponse response = client.prepareSearch("zxcity_elk", "test_dazuo")
                .setTypes("file")
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                // Query
                .setQuery(QueryBuilders.termQuery("name", "dazuo3"))
                // Filter
                // .setPostFilter(QueryBuilders.rangeQuery("age").from(12).to(18))
                .setFrom(0).setSize(60).setExplain(true)
                .execute()
                .actionGet();

        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            String sourceAsString = hit.getSourceAsString();
            System.out.println(sourceAsString);
        }
    }
}
