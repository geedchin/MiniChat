package com.cn.server.impl;

import com.cn.server.service.Trans;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by qinjie on 2017/8/8.
 */
public abstract class TransAbstract implements Trans {
    
    
    
    @Override
    public void handlerAccept(SelectionKey selectionKey) throws IOException {
        
        this.accept(selectionKey);
    }
    
    @Override
    public String handlerRead(SelectionKey selectionKey) throws IOException {
        return this.read(selectionKey);
    }
    
    abstract void accept(SelectionKey selectionKey) throws IOException;
    
    abstract String read(SelectionKey selectionKey) throws IOException;
    
    
}
