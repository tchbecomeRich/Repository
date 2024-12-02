package org.nio;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;

/**
 * @author tchstart
 * @data 2024-12-02
 */
@Slf4j
public class Nio02ByteBufferAllocate {
    public static void main(String[] args) {
        log.info(ByteBuffer.allocate(16).getClass().toString());
        log.info(ByteBuffer.allocateDirect(16).getClass().toString());

        /**
         * java.nio.HeapByteBuffer -java 堆内存，读写效率比较低，收到GC影响
         * java.nio.DirectByteBuffer -直接内存，读写效率高（少一次拷贝），不会收到GC影响，分配的效率低下
         */
    }
}
