package com.maxzuo.graphql;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.ImmutableMap;
import graphql.ExecutionInput;
import graphql.GraphQL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

/**
 * Created by zfh on 2019/08/09
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private GraphQL graphQL;

    @Test
    public void testQuery () {
        String query = "{\n    bookById(id: \"book-1\") {\n        id\n        name\n        pageCount\n        author {\n            firstName\n            lastName\n        }\n    }\n}";
        String operationName = "";
        ImmutableMap<String, Object> variables = ImmutableMap.of("dazuo", "dazuo");

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .operationName(operationName)
                .variables(variables)
                .build();

        Map<String, Object> result = graphQL.execute(executionInput).toSpecification();
        System.out.println(JSONObject.toJSONString(result));
    }
}
