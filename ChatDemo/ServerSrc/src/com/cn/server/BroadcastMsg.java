package com.cn.server;

import com.cn.server.utils.CharactorUtils;
import com.cn.server.utils.ChatServerConfig;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by qinjie on 2017/8/8.
 */
public class BroadcastMsg {
    
    
    public static void send(SelectionKey from, String msg, Set set) {
        
        msg = UserHolder.getHolder().getNameBySelectionKey(from) + " : " + msg;
        
        ByteBuffer buffer = ByteBuffer.allocate(ChatServerConfig.BUFFER_SIZE);
        
        buffer.put(ByteBuffer.wrap(CharactorUtils.utf16Encode(msg)));
        
        buffer.flip();
        buffer.mark();
        
        Iterator<SelectionKey> it = set.iterator();
        
        set.forEach((key) -> {
            SelectionKey sk = (SelectionKey) key;
            
            if (sk.channel() instanceof ServerSocketChannel || (sk == from)) return;
            
            SocketChannel clientChannel = (SocketChannel) sk.channel();
            try {
                buffer.reset();
                clientChannel.write(buffer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    
}
