package com.maxzuo.sofa;

import com.alipay.sofa.rpc.common.RpcConstants;
import com.alipay.sofa.rpc.config.ProviderConfig;
import com.alipay.sofa.rpc.config.RegistryConfig;
import com.alipay.sofa.rpc.config.ServerConfig;

/**
 * Sofa-Rpc 服务端
 * <p>
 * Created by zfh on 2019/12/15
 */
public class Application {

    public static void main(String[] args) {
        // 1.注册中心
        RegistryConfig registryConfig = new RegistryConfig();
        registryConfig.setProtocol("zookeeper");
        registryConfig.setAddress("127.0.0.1:2181");

        // 2.服务运行容器
        ServerConfig serverConfig = new ServerConfig();
        // 通信协议
        serverConfig.setProtocol(RpcConstants.PROTOCOL_TYPE_BOLT);
        // serverConfig.setProtocol(RpcConstants.PROTOCOL_TYPE_HTTP);
        serverConfig.setPort(12200);
        serverConfig.setDaemon(false);

        // 3.服务发布
        ProviderConfig<IHelloSyncService> providerConfig = new ProviderConfig<>();
        providerConfig.setInterfaceId(IHelloSyncService.class.getName());
        providerConfig.setRef(new HelloSyncServiceImpl());
        // 指定服务端和注册中心
        providerConfig.setServer(serverConfig);
        providerConfig.setRegistry(registryConfig);

        providerConfig.export();
    }
}
