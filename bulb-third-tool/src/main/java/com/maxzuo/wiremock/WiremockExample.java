package com.maxzuo.wiremock;

import com.github.tomakehurst.wiremock.WireMockServer;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

/**
 * WireMock服务器
 *
 * Created by zfh on 2019/11/08
 */
public class WiremockExample {

    private static WireMockServer wireMockServer = new WireMockServer(8080);

    public static void main(String[] args) {
        startServer();
    }

    private static void startServer(){
        wireMockServer.start();

        stubFor(
                get(urlEqualTo("/user/get"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));

        stubFor(
                post(urlEqualTo("/user/create"))
                        .withHeader("content-type", equalTo("application/json"))
                        .withRequestBody(containing("id"))
                        .willReturn(aResponse()
                                .withStatus(200)
                                .withHeader("Content-Type", "application/json")
                                .withBody("{ \"id\": \"1234\", name: \"John Smith\" }")));
    }
}
