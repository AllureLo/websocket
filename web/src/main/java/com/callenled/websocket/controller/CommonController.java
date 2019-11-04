package com.callenled.websocket.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.callenled.websocket.service.WebSocketService;
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

    @Reference(url = "dubbo://127.0.0.1:20880")
    private WebSocketService webSocketService;

    @RequestMapping("/send")
    public String send(@RequestParam(value = "id") String id,
                       @RequestParam(value = "message") String message) throws IOException {
        webSocketService.sendMessage(id, message);
        return "发送成功";
    }

}
