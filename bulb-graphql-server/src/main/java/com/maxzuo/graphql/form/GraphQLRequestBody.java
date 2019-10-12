package com.maxzuo.graphql.form;

import com.maxzuo.graphql.exception.ParameterException;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * GraphQL 查询请求对象
 */
public class GraphQLRequestBody {

    /**
     * 查询QL
     */
    private String query;

    /**
     * 操作名称
     */
    private String operationName;

    /**
     * 变量
     */
    private Map<String, Object> variables;

    /**
     * 验证入参
     */
    public void validateParam () {
        if (StringUtils.isBlank(query)) {
            throw new ParameterException();
        }
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getOperationName() {
        return operationName;
    }

    public void setOperationName(String operationName) {
        this.operationName = operationName;
    }

    public Map<String, Object> getVariables() {
        return variables;
    }

    public void setVariables(Map<String, Object> variables) {
        this.variables = variables;
    }
}
