package org.nio;

import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author tchstart
 * @data 2024-12-02
 * 集中读取
 */
public class Nio06GatheringReads {
    public static void main(String[] args) {
        ByteBuffer b1 = StandardCharsets.UTF_8.encode("hello");
        ByteBuffer b2 = StandardCharsets.UTF_8.encode("world");
        ByteBuffer b3 = StandardCharsets.UTF_8.encode("你好");


        try(FileChannel channel = new RandomAccessFile("words2.txt", "rw").getChannel()){

           channel.write(new ByteBuffer[]{b1,b2,b3});
        }catch (Exception e){

        }
    }
}
