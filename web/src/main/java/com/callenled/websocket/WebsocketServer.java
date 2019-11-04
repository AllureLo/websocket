package com.callenled.websocket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

/**
 * @Author: callenled
 * @Date: 19-11-4 下午2:20
 */
@ServerEndpoint("/websocket/{id}")
@Component
public class WebsocketServer {
    private static Logger log = LoggerFactory.getLogger(WebsocketServer.class);

    private Websocket websocket = Websocket.getInstance();

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) throws IOException {
        websocket.onOpen(id, session);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param id
     */
    @OnClose
    public void onClose(Session session, @PathParam("id") String id) {
        websocket.onClose(id, session);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(String message, Session session, @PathParam("id") String id) {
        log.info("收到一条新的信息:" + message);
    }


    /**
     * 连接异常
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) throws IOException {
        log.error(error.getMessage());
    }
}
