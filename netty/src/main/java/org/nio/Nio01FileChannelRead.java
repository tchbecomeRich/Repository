package org.nio;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author tchstart
 * @data 2024-12-02
 */
@Slf4j
public class Nio01FileChannelRead {
    public static void main(String[] args) {
        try(FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true){
                int len = channel.read(buffer);
                log.debug("read byte: {}",len);
                if(len == -1){
                    break;
                }

                buffer.flip(); // 切换读模式
                while(buffer.hasRemaining()){
                    byte b = buffer.get();
                    log.info("actual byte: {}",(char)b);
                }

                buffer.clear(); // 切换为写模式
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
