package com.maxzuo.protostuff;

import io.protostuff.Input;
import io.protostuff.Output;
import io.protostuff.Schema;

import java.io.IOException;

/**
 * Created by zfh on 2020/01/05
 */
public class UserSchema implements Schema<User> {


    @Override
    public String getFieldName(int i) {
        return null;
    }

    @Override
    public int getFieldNumber(String s) {
        return 0;
    }

    @Override
    public boolean isInitialized(User user) {
        return user.getName() != null;
    }

    @Override
    public User newMessage() {
        return null;
    }

    @Override
    public String messageName() {
        return null;
    }

    @Override
    public String messageFullName() {
        return null;
    }

    @Override
    public Class<? super User> typeClass() {
        return null;
    }

    @Override
    public void mergeFrom(Input input, User user) throws IOException {

    }

    @Override
    public void writeTo(Output output, User user) throws IOException {

    }
}
