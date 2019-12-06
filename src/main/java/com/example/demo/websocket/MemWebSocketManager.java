package com.example.demo.websocket;

import org.springframework.web.socket.client.WebSocketClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Author: wangchao
 * Time: 2019-06-24
 * Description: This is
 */
public class MemWebSocketManager {
    private final Map<String, WebSocket> connections = new ConcurrentHashMap<>(100);




}
