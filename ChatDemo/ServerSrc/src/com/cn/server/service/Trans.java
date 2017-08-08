package com.cn.server.service;

import java.io.IOException;
import java.nio.channels.SelectionKey;

/**
 * Created by qinjie on 2017/8/8.
 */
public interface Trans {
    
    void handlerAccept(SelectionKey selectionKey) throws IOException;
    
    String handlerRead(SelectionKey selectionKey) throws IOException;
    
}
