package org.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author tchstart
 * @data 2024-12-02
 */
@Slf4j
public class Nio03ByteBufferRead {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

        System.out.println((char) buffer.get(3));
        ByteBufferUtil.debugAll(buffer);
    }

    public static void mark(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();
        // mark & reset
        // mark做一个标记，记录position位置，reset

        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark(); // 加标记 索引2的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.reset(); // 将position重置到索引2
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
    }
    public static void rewind(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

        buffer.get(new byte[4]);
        ByteBufferUtil.debugAll(buffer);
        buffer.rewind(); // rewind 从头读
        log.info(String.valueOf(buffer.get()));
        ByteBufferUtil.debugAll(buffer);
    }
}
