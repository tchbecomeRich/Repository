package org.nio;

import java.nio.ByteBuffer;

/**
 * @author tchstart
 * @data 2024-12-02
 * 粘包半包解决方法发
 */
public class Nio07ByteBufferExam {


    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello,world\nI'm zhangsan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source){
        source.flip();

        for(int i = 0 ; i < source.limit() ; i++){
            if(source.get(i) == '\n'){
                int length = i - source.position() + 1;
                ByteBuffer target = ByteBuffer.allocate(length);
                for(int j = 0 ; j < length ;j ++){
                    target.put(source.get());
                }
                ByteBufferUtil.debugAll(target);
            }
        }
        source.compact();
    }
}
