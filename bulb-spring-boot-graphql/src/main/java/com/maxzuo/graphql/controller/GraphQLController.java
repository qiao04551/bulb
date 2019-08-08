package com.maxzuo.graphql.controller;

import com.maxzuo.graphql.component.ExecutionResultHandler;
import com.maxzuo.graphql.component.GraphQLInvocation;
import com.maxzuo.graphql.model.GraphQLInvocationData;
import com.maxzuo.graphql.model.GraphQLRequestBody;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * GraphQL接口Rest
 * <p>
 * Created by zfh on 2019/08/08
 */
@RestController
public class GraphQLController {

    @Autowired
    GraphQLInvocation graphQLInvocation;

    @Autowired
    ExecutionResultHandler executionResultHandler;

    /**
     * 查询一本书的信息
     * @param body {@link GraphQLRequestBody}
     */
    @PostMapping("graphql")
    public Object graphqlPOST(@RequestBody GraphQLRequestBody body) {
        String query = body.getQuery() == null ? "" : body.getQuery();
        CompletableFuture<ExecutionResult> executionResult =
                graphQLInvocation.invoke(new GraphQLInvocationData(query, body.getOperationName(), body.getVariables()));

        return executionResultHandler.handleExecutionResult(executionResult);
    }
}
