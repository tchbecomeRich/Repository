package org.socket;

import lombok.extern.slf4j.Slf4j;
import org.nio.ByteBufferUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
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
            // select 在事件未处理时，它不会阻塞，事件发生后要么处理，要么取消，不能置之不理
            selector.select();
            // 处理事件,selectKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()){
                SelectionKey key = iter.next();
                // 处理key的时候，要从selectedKeys集合中删除，否则下次会有问题
                iter.remove();
                log.debug("key: {}",key);

                // 区分类型
                if(key.isAcceptable()){
                    ServerSocketChannel channel = (ServerSocketChannel)key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    ByteBuffer buffer = ByteBuffer.allocate(16); //  附件
                    // 将ByteBuffer 作为附件关联到selector上
                    SelectionKey scKey = sc.register(selector, 0, buffer);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                }else if(key.isReadable()){
                    try{
                        SocketChannel channel = (SocketChannel) key.channel();
                        // 获取selectionKey 上获取关联附件
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        int read = channel.read(buffer);// 如果是正常断开，read的方法返回值是-1
                        if(read == -1){
                            key.cancel();
                        }else{
                            split(buffer);
                            if(buffer.position() == buffer.limit()){
                                ByteBuffer newBuffer = ByteBuffer.allocate(buffer.capacity() * 2);
                                buffer.flip();
                                newBuffer.put(buffer);
                                key.attach(newBuffer);
                            }
                        }
                    }catch (IOException e){
                        e.printStackTrace();
                        key.cancel(); // 客户端断开了，因此需要将key从selector的keys集合中真正删除key
                    }
                }
            }
        }
    }

    private static void split(ByteBuffer source){
        source.flip();
        for(int i = 0 ; i < source.limit() ; i++){
            if(source.get(i) == '\n'){
                int length = i - source.position() + 1;
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0 ; j < length ; j++){
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
}
