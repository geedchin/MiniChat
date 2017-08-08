package com.cn.server;

import com.sun.istack.internal.NotNull;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by qinjie on 2017/8/8.
 */
public class UserHolder {
    
    private ConcurrentHashMap<String, SelectionKey> ns = new ConcurrentHashMap(50);
    private ConcurrentHashMap<SelectionKey, String> sn = new ConcurrentHashMap(50);
    
    private UserHolder() {
    }
    
    public static UserHolder getHolder() {
        return innerHolder.holder;
    }
    
    private static class innerHolder {
        static UserHolder holder = new UserHolder();
    }
    
    public SelectionKey getSelectionKeyByName(String name) {
        return ns.get(name);
    }
    
    public String getNameBySelectionKey(SelectionKey key) {
        return sn.get(key);
    }
    
    public void addUser(@NotNull String name, @NotNull SelectionKey key) {
        
        ns.put(name, key);
        sn.put(key, name);
    }
    
    public void delUser(String name) {
        SelectionKey key = ns.get(name);
        if (key != null) {
            delUser(name, key);
        }
    }
    
    public void delUser(SelectionKey key) {
        String name = sn.get(key);
        if (name != null) {
            delUser(name, key);
        }
    }
    
    public boolean containsKey(String name) {
        return ns.containsKey(name);
    }
    
    public boolean containsKey(SelectionKey key) {
        return sn.containsKey(key);
    }
    
    
    private void delUser(String name, SelectionKey key) {
        ns.remove(name);
        sn.remove(key);
    }
    
    
}
