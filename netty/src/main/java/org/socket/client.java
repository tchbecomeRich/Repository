package org.socket;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

/**
 * @author tchstart
 * @data 2024-12-02
 */
@Slf4j
public class client {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost",8080));
        sc.write(StandardCharsets.UTF_8.encode("hello"));
        System.out.println("waiting...");
    }
}
