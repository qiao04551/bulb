package com.maxzuo.graphql.component;

import graphql.ExecutionResult;

import java.util.concurrent.CompletableFuture;

public interface ExecutionResultHandler {

    Object handleExecutionResult(CompletableFuture<ExecutionResult> executionResultCF);
}
