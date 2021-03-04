package com.maxzuo.guice;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;

/**
 * Created by zfh on 2020/04/26
 */
public class Communication {

    @Inject
    private Communicator communicator;

    @Inject
    private TransactionLog transactionLog;

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new BasicModule());
        Communication comm = injector.getInstance(Communication.class);

        comm.communicator.sendMessage("hello Guice");
        comm.transactionLog.setJdbcUrl("jdbc:mysql://localhost");
    }
}
