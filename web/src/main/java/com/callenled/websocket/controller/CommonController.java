package com.callenled.websocket.controller;

import com.callenled.websocket.service.WebSocketService;
import com.callenled.websocket.service.impl.WebsocketServiceImpl;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author: callenled
 * @Date: 19-11-4 下午4:03
 */
@RequestMapping("/common")
@RestController
public class CommonController {

    private WebSocketService webSocketService = new WebsocketServiceImpl();

    @RequestMapping("/send")
    public String send(@RequestParam(value = "id") String id,
                       @RequestParam(value = "appName") String appName,
                       @RequestParam(value = "message") String message) throws IOException {
        webSocketService.sendMessage(id, appName, message);
        return "发送成功";
    }

}
