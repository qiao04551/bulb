package com.maxzuo.protostuff;

import io.protostuff.LinkedBuffer;
import io.protostuff.ProtostuffIOUtil;
import io.protostuff.Schema;
import io.protostuff.runtime.RuntimeSchema;

/**
 * Java序列化库 - Protostuff
 * <pre>
 *   Protocol Buffer需要编写.proto文件，再把它编译成目标语言。
 *   有了protostuff之后，就不需要依赖.proto文件了，他可以直接对POJO进行序列化和反序列化。
 * </pre>
 * Created by zfh on 2020/01/05
 */
public class ProtostuffExample {

    public static void main(String[] args) {
        User user = new User("dazuo", 22);
        Schema<User> schema = RuntimeSchema.getSchema(User.class);
        LinkedBuffer buffer = LinkedBuffer.allocate(512);

        byte[] protostuff = new byte[0];
        try {
            // 序列化
            protostuff = ProtostuffIOUtil.toByteArray(user, schema, buffer);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            buffer.clear();
        }

        try {
            // 反序列化
            User userParsed = schema.newMessage();
            ProtostuffIOUtil.mergeFrom(protostuff, userParsed, schema);
            System.out.println(userParsed);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
