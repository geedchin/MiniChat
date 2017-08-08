package com.cn.server.utils;

/**
 * Created by qinjie on 2017/8/8.
 */
public interface Protocol {
    
    /**
     *       1      2       3       4       5       6       7       8
     *     新上线   发消息  
     */
    
    public static int NEW_ACCEPT = 0b00000001;
    
    public static int SEND_MSG = 0b00000010;
    
}
