package com.maxzuo.factory;

/**
 * 工厂模式 + 建造者模式 + fluent编程风格经典案例
 * <p>
 * Created by zfh on 2019/08/24
 */
public class FactoryBuilderExample {

    public static void main(String[] args) {
        CuratorFramework curatorFramework = CuratorFrameworkFactory.newClient("localhost:2181");
        curatorFramework.start();
    }
}

/**
 * 参考 CuratorFramework的工厂模式 + 建造者模式 + fluent风格
 */
class CuratorFrameworkFactory {

    public static CuratorFramework newClient(String connectString)
    {
        return newClient(connectString, 1, 1);
    }

    public static CuratorFramework newClient(String connectString, int sessionTimeoutMs, int connectionTimeoutMs)
    {
        return builder()
                .connectString(connectString)
                .sessionTimeoutMs(sessionTimeoutMs)
                .connectionTimeoutMs(connectionTimeoutMs)
                .build();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private String connectString;

        private int sessionTimeoutMs;

        private int connectionTimeoutMs;

        public Builder connectString(String connectString){
            this.connectString = connectString;
            return this;
        }

        public Builder sessionTimeoutMs (int sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
            return this;
        }

        public Builder connectionTimeoutMs (int connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
            return this;
        }

        public String getConnectString() {
            return connectString;
        }

        public void setConnectString(String connectString) {
            this.connectString = connectString;
        }

        public int getSessionTimeoutMs() {
            return sessionTimeoutMs;
        }

        public void setSessionTimeoutMs(int sessionTimeoutMs) {
            this.sessionTimeoutMs = sessionTimeoutMs;
        }

        public int getConnectionTimeoutMs() {
            return connectionTimeoutMs;
        }

        public void setConnectionTimeoutMs(int connectionTimeoutMs) {
            this.connectionTimeoutMs = connectionTimeoutMs;
        }

        public CuratorFramework build () {
            return new CuratorFrameworkImpl(this);
        }
    }
}

/**
 * 客户端
 */
interface CuratorFramework {

    void start();

    void stop();
}

class CuratorFrameworkImpl implements CuratorFramework {

    public CuratorFrameworkImpl (CuratorFrameworkFactory.Builder builder) {
        // TODO: 构建对象
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }
}