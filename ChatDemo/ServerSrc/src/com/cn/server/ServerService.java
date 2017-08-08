package com.cn.server;

import com.cn.server.service.ServiceThread;
import com.cn.server.utils.ChatServerConfig;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;

/**
 * Created by qinjie on 2017/8/8.
 */
public class ServerService {
    
    public static void main(String[] args) throws IOException {
        
        ServerSocketChannel listenChannel = ServerSocketChannel.open();
        
        listenChannel.configureBlocking(false);
        listenChannel.socket().bind(new InetSocketAddress(ChatServerConfig.PORT));
        
        Selector selector = Selector.open();
        listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        
        ServiceThread st = new ServiceThread(listenChannel, selector);
        
        st.start();
        
        while (st.isAlive()) {
            Thread.yield();
        }
        
    }
    
}
