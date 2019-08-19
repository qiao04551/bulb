package com.maxzuo.graphql.controller;

import com.maxzuo.graphql.config.GraphQLProvider;
import com.maxzuo.graphql.form.GraphQLRequestBody;
import com.maxzuo.graphql.vo.Result;
import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQLError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * GraphQL 查询入口
 * <p>
 * Created by zfh on 2019/08/12
 */
@RequestMapping("faceCircle")
@RestController
public class GraphQLController {

    @Autowired
    private GraphQLProvider graphQLProvider;

    @PostMapping("graphql")
    public Object graphql (@RequestBody GraphQLRequestBody form) {
        form.validateParam();

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(form.getQuery())
                .operationName(form.getOperationName())
                .variables(form.getVariables())
                .build();

        ExecutionResult executionResult = graphQLProvider.graphQL.execute(executionInput);
        List<GraphQLError> errors = executionResult.getErrors();

        Result result = new Result(Result.RESULT_FAILURE, "查询异常！");
        if (errors.size() > 0) {
            result.setData(errors);
        } else {
            result.setMsg("查询成功！");
            result.setCode(Result.RESULT_SUCCESS);
            result.setData(executionResult.getData());
        }
        return result;
    }
}
