package com.maxzuo.nio.socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO-基于通道的网络通信（非阻塞）
 * <p>
 *   Selector
 *     1.Selector是一个或多个SelectableChannel对象的多路复用器。
 *     2.通过调用Selector.open()来创建，除非调用selector.close()，否则该selector将会一直保持打开状态。
 *     3.通过channel的register方法，将channel注册到给定的selector中，并返回一个表示注册关系的SelectionKey 对象。
 *     4.Selector维护着三个selection keys集合:
 *        1）`key set` 包含着所有selectionKeys，当前所有注册到selector中的channel返回的注册关系SelectionKey都包含在内，
 *          这个集合可以通过selector.keys()方法返回。
 *        2）`selected-key set`包含着一部分selectionKeys，其中的每个selectionKey所关联的channel在selection operation
 *          期间被检测出至少 准备好了一个可以在兴趣集中匹配到的操作。这个集合可以通过调用selector.selectedKeys()方法返回。
 *          `selected-key set`一定是`key set`的子集。
 *        3）`cancelled-key set` 也包含着一部分selectionKeys，其中的每个selectionKey都已经被取消，但是所关联channel还
 *          没有被撤销登记。`cancelled-key set`不能够被直接返回，但也一定是`key set`的子集。
 *        参考：https://blog.csdn.net/qq_32331073/article/details/81132937
 *     5.Selector线程安全性
 *        多线程并发情况下Selector本身是线程安全的，但是他们所持有的`key sets`不是线程安全的。
 *     6.常用方法：
 *        select()             —— 阻塞到至少有一个通道在你注册的事件上就绪了，本质是Object.await()阻塞。
 *        select(long timeout) —— 和select()一样，除了最长会阻塞timeout毫秒
 *        selectNow()          —— 不会阻塞，不管什么通道就绪都立刻返回
 *        selectedKeys()       —— 一旦调用了select()方法，并且返回值表明有一个或更多个通道就绪了，然后可以通过调用selector
 *                                的selectedKeys()方法，访问“已选择键集（selected key set）”中的就绪通道。
 *        wakeUp()             —— 阻塞在select()方法上的线程会立马返回
 *        close()              —— 用完Selector后调用其close()方法会关闭该Selector，且使注册到该Selector上的所有
 *                                SelectionKey实例无效。通道本身并不会关闭。
 * </p>
 * <p>
 *   SelectionKey
 *     1.定义的4种事件：
 *        OP_ACCEPT  —— 接收连接进行事件，表示服务器监听到了客户连接，那么服务器可以接收这个连接了
 *        OP_CONNECT —— 连接就绪事件，表示客户与服务器的连接已经建立成功
 *        OP_READ    —— 读就绪事件，表示通道中已经有了可读的数据，可以执行读操作了
 *        OP_WRITE   —— 写就绪事件，表示已经可以向通道写数据了
 *     2.常用方法
 *        isConnectable() —— 连接就绪
 *        attach()        —— 将给定对象附加到此键
 *        attachment()    —— 返回SelectionKey的attachment，attachment可以在注册channel的时候指定。
 *        channel()       —— 返回该SelectionKey对应的channel
 *        selector()      —— 返回该SelectionKey对应的Selector
 *        interestOps()   —— 返回代表需要Selector监控的IO操作的bit mask
 *        readyOps()      —— 返回一个bit mask，代表在相应channel上可以进行的IO操作
 *     3.SelectionKey理解：https://www.cnblogs.com/burgeen/p/3618059.html
 * </p>
 *
 * Created by zfh on 2019/01/24
 */
public class SimpleServer {

    private static final Logger logger = LoggerFactory.getLogger(SimpleServer.class);

    public static void main(String[] args) throws IOException {
        // 1.打开一个Server-socket通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 2.配置为非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 3.绑定连接
        serverSocketChannel.bind(new InetSocketAddress(8090));

        // 4.打开一个选择器
        Selector selector = Selector.open();
        // 5.将通道注册到选择器上，并且 监听“接受”事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 6.获取选择器上已经“准备就绪”的事件，当没有就绪的键select()将阻塞当前线程
        while (selector.select() > 0) {
            // 7.获取选择器上就绪的“选择键”
            Iterator<SelectionKey> it = selector.selectedKeys().iterator();
            while (it.hasNext()) {
                SelectionKey sk = it.next();
                // 8.执行“已就绪”的监听事件
                if (sk.isAcceptable()) {
                    // 10.若“接收就绪”，则获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 11.切换为非阻塞模式
                    socketChannel.configureBlocking(false);
                    // 12.将该通道注册到选择器上
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }
                // 13.执行“已可读”的监听事件
                //    注意：如果客户端关闭了，服务端还是会收到该channel的读事件，但是读到的字节长度为0，read()返回值是-1。
                //         其实-1在网络io中就是socket关闭的含义，在文件时末尾的含义，所以为了避免客户端关闭服务端一直收到读事件，
                //         必须检测上一次的读是不是-1，如果是-1，就关闭这个channel。
                if (sk.isReadable()) {
                    // 14.获取可读的通道
                    SocketChannel channel = (SocketChannel) sk.channel();
                    // 15.读取数据
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    int byteCount;
                    while ((byteCount = channel.read(byteBuffer)) > 0) {
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(), 0, byteCount));
                        byteBuffer.clear();
                    }
                    if (byteCount == -1) {
                        logger.info("【服务端】客户端断开连接");
                        channel.close();
                    }
                }
                // 16.移除已处理的选择键
                it.remove();
            }
        }
    }
}
