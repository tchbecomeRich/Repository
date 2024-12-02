package org.socket;

import lombok.extern.slf4j.Slf4j;
import org.nio.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author tchstart
 * @data 2024-12-02
 * 非阻塞模式确实能解决单线程阻塞问题，但是在服务器一直循环，有点劳碌命
 * 解决方案： selector
 */
@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        Selector selector = Selector.open();

        ByteBuffer buffer = ByteBuffer.allocate(16);

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false); // 非阻塞模式
        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        /**
         * 事件4种
         * accept： 会有连接请求时触发
         * connect：是客户端，建立连接后触发
         * read： 有数据了，可读事件
         * write： 可写事件
         */
        SelectionKey sscKey = ssc.register(selector, 0, null);
        // sscKey只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}",sscKey);

        ssc.bind(new InetSocketAddress(8080));
        List<SocketChannel> channels = new ArrayList<>();
        while(true){
            // select没有事件发生，线程阻塞，有事件，线程恢复运行
            selector.select();
            // 处理事件,selectKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                log.debug("key: {}",key);
                ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                SocketChannel sc = channel.accept();
                log.debug("{}",sc);
            }
        }
    }
}
