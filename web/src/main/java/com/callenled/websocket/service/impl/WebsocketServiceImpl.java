package com.callenled.websocket.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import com.callenled.websocket.Websocket;
import com.callenled.websocket.service.WebSocketService;

import java.io.IOException;

/**
 * @Author: callenled
 * @Date: 19-11-4 下午2:44
 */
@Service(interfaceClass = WebSocketService.class, version = "1.0.0",timeout = 3000)
public class WebsocketServiceImpl implements WebSocketService {

    private Websocket websocket = Websocket.getInstance();

    @Override
    public void sendMessage(String id, String message) throws IOException {
        websocket.sendMessage(id, message);
    }

    @Override
    public void onlineCount() {
        websocket.onlineCount();
    }
}
