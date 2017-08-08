package com.cn.client;

import com.cn.client.utils.CharactorUtils;
import com.cn.client.utils.ChatClientConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Created by qinjie on 2017/8/8.
 */
public class ReadThread extends Thread {
    
    ByteBuffer buffer = ByteBuffer.allocate(ChatClientConfig.BUFFER_SIZE);
    
    @Override
    public void run() {
        
        SocketChannel clientChannel = ClientService.clientChannel;
        Selector selector = ClientService.selector;
        
        try {
            // while (true) {
            while (selector.select() > 0) {
                selector.selectedKeys().forEach((key) -> {
                    
                    if (key.isReadable()) {
                        String receive = null;
                        buffer.clear();
                        try {
                            ((SocketChannel) key.channel()).read(buffer);
                            buffer.flip();
                            receive = CharactorUtils.utf16Decoder.decode(buffer).toString();
                            System.out.println(receive);
                        } catch (IOException e) {
                            e.printStackTrace();
                        } finally {
                            selector.selectedKeys().remove(key);
                        }
                    }
                });
                //}
                // Thread.sleep(500);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}
