package com.callenled.websocket.service;

import java.io.IOException;

/**
 * @Author: callenled
 * @Date: 19-11-4 下午3:09
 */
public interface WebSocketService {

    /**
     * websocket发送消息
     *
     * @param id 主键
     * @param appName 应用名
     * @param message 发送的内容
     */
    void sendMessage(String id, String appName, String message) throws IOException;

    /**
     * 当前在线人数
     */
    void onlineCount();
}
