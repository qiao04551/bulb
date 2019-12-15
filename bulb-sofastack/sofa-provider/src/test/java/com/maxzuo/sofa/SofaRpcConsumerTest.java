package com.maxzuo.sofa;

import com.alipay.sofa.rpc.config.ConsumerConfig;

/**
 * Sofa-Rpc 消费端
 * <p>
 * Created by zfh on 2019/12/15
 */
public class SofaRpcConsumerTest {

    public static void main(String[] args) {
        ConsumerConfig<IHelloSyncService> consumerConfig = new ConsumerConfig<IHelloSyncService>()
                .setInterfaceId(IHelloSyncService.class.getName())
                // 指定协议
                .setProtocol("bolt")
                // 指定直连地址
                .setDirectUrl("bolt://127.0.0.1:12200");

        // 生成代理类
        IHelloSyncService helloSyncService = consumerConfig.refer();
        while (true) {
            System.out.println(helloSyncService.saySync("hello sofa"));
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
            }
        }
    }
}
