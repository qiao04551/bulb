package com.maxzuo.graphql.component;

import com.maxzuo.graphql.model.GraphQLInvocationData;
import graphql.ExecutionResult;
import org.springframework.web.context.request.WebRequest;

import java.util.concurrent.CompletableFuture;

public interface GraphQLInvocation {

    CompletableFuture<ExecutionResult> invoke(GraphQLInvocationData invocationData);
}
