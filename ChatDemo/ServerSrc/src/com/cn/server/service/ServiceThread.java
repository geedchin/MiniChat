package com.cn.server.service;

import com.cn.server.BroadcastMsg;
import com.cn.server.impl.TransImpl;
import com.cn.server.utils.ChatServerConfig;

import java.io.IOException;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

/**
 * Created by qinjie on 2017/8/8.
 */
public class ServiceThread extends Thread {
    
    private final int time_out = ChatServerConfig.TIME_OUT;
    private Selector selector;
    private final ServerSocketChannel listenChannel;
    private Trans trans;
    
    public ServiceThread(ServerSocketChannel listenChannel, Selector selector) {
        
        this.listenChannel = listenChannel;
        this.selector = selector;
        trans = new TransImpl();
    }
    
    @Override
    public void run() {
        
        while (true) {
            try {
                int count = 0;
                while (selector.select(time_out) == 0) {
                    System.out.print(".");
                    if (count++ == 100) {
                        count = 0;
                        System.out.println();
                    }
                }
                
                Iterator<SelectionKey> it = selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = it.next();
                    
                    try {
                        if (key.isAcceptable()) {
                            trans.handlerAccept(key);
                        }
                        String receive = null;
                        if (key.isReadable()) {
                            receive = trans.handlerRead(key);
                        }
                        if (receive != null) {
                            BroadcastMsg.send(key, receive, selector.keys());
                        }
                    } catch (Exception e) {
                        key.cancel();
                    } finally {
                        it.remove();
                    }
                    
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }
}
