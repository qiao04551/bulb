package com.maxzuo.guice;

public class DatabaseTransactionLog implements TransactionLog {

    @Override
    public void setJdbcUrl(String url) {
        System.out.println("jdbc url：" + url);
    }
}
