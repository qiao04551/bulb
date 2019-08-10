package com.maxzuo.graphql.controller;

import com.maxzuo.graphql.form.GraphQLRequestBody;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * GraphQL接口Rest
 * <p>
 * Created by zfh on 2019/08/08
 */
@RestController
public class GraphQLController {

    @Autowired
    private GraphQL graphQL;

    /**
     * 查询一本书的信息
     * <pre>
     *   Content-Type：application/json;charset=UTF-8
     *   查询参数（query、operationName、variables）后面两个参数可选
     * </pre>
     * @param body {@link GraphQLRequestBody}
     */
    @PostMapping("graphql")
    public Object graphqlPOST(@RequestBody GraphQLRequestBody body) {
        String query = body.getQuery() == null ? "" : body.getQuery();
        return invoke(query, body.getOperationName(), body.getVariables());
    }

    private Object invoke(String query, String operationName, Map<String, Object> variables) {
        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(query)
                .operationName(operationName)
                .variables(variables)
                .build();

        return graphQL.executeAsync(executionInput).thenApply(ExecutionResult::toSpecification);
    }
}
