package com.maxzuo.grpc.service;

import com.maxzuo.grpc.protocol.SearchRequest;
import com.maxzuo.grpc.protocol.SearchResponse;
import io.grpc.stub.StreamObserver;

/**
 * 实现搜索接口
 * Created by zfh on 2019/08/23
 */
public class SearchServiceImpl extends SearchServiceGrpc.SearchServiceImplBase {

    @Override
    public void search(SearchRequest request, StreamObserver<SearchResponse> responseObserver) {
        System.out.println("request greeting: " + request.getGreeting());

        SearchResponse response = SearchResponse.newBuilder().setReply("hello client！").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
