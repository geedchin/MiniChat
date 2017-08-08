package com.cn.client;


import com.cn.client.utils.CharactorUtils;
import com.cn.client.utils.ChatClientConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;


/**
 * Created by qinjie on 2017/8/8.
 */
public class ClientService {
    
    public static SocketChannel clientChannel = null;
    public static Selector selector = null;
    
    public static void main(String[] args) throws IOException {
        
        clientChannel = SocketChannel.open(new InetSocketAddress(ChatClientConfig.HOSTS, ChatClientConfig.PORT));
        clientChannel.configureBlocking(false);
        selector = Selector.open();
        clientChannel.register(selector, SelectionKey.OP_READ);
        
        ReadThread readThread = new ReadThread();
        readThread.start();
        
        sendMsg();
    }
    
    public static void sendMsg() {
        ByteBuffer buffer = ByteBuffer.allocate(ChatClientConfig.BUFFER_SIZE);
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        
        String msg = null;
        System.out.print("请输入用户名：");
        buffer.clear();
        try {
            while ((msg = br.readLine()) != null) {
    
                buffer.put(CharactorUtils.utf16Encode(msg));
                buffer.flip();
                clientChannel.write(buffer);
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
}
