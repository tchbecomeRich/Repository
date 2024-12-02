package org.nio;

import io.netty.buffer.ByteBuf;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author tchstart
 * @data 2024-12-02
 */
public class Nio04ByteBufferString {
    public static void main(String[] args) {
        // 字符串转为ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("hello".getBytes());
        ByteBufferUtil.debugAll(buffer);

        // charset
        ByteBuffer byteBuffer = StandardCharsets.UTF_8.encode("hello");
        ByteBufferUtil.debugAll(byteBuffer);

        // wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes());
        ByteBufferUtil.debugAll(buffer3);


        // byteBuffer转字符串
        String str = StandardCharsets.UTF_8.decode(buffer3).toString();
        System.out.println(str);

        buffer.flip();
        String str1 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str1);
    }
}
