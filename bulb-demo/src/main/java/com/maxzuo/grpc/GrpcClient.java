package com.maxzuo.grpc;

import com.maxzuo.grpc.protocol.SearchRequest;
import com.maxzuo.grpc.protocol.SearchResponse;
import com.maxzuo.grpc.service.SearchServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

/**
 * GRPC 客户端
 * <p>
 * Created by zfh on 2019/08/23
 */
public class GrpcClient {

    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("127.0.0.1", 5000).usePlaintext().build();
        SearchServiceGrpc.SearchServiceBlockingStub stub = SearchServiceGrpc.newBlockingStub(channel);

        SearchRequest request = SearchRequest.newBuilder().setGreeting("hello server").build();
        SearchResponse response = stub.search(request);
        System.out.println("response : " + response.getReply());
    }
}
