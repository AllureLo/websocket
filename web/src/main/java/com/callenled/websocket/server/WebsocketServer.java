package com.callenled.websocket.server;

import com.callenled.websocket.Websocket;
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
@ServerEndpoint("/websocket/{appName}/{id}")
@Component
public class WebsocketServer {
    private static Logger log = LoggerFactory.getLogger(WebsocketServer.class);

    /**
     * 连接建立成功调用的方法
     *
     * @param session
     * @param id
     */
    @OnOpen
    public void onOpen(Session session,
                       @PathParam("id") String id,
                       @PathParam("appName") String appName) throws IOException {
        Websocket.onOpen(id, appName, session);
    }

    /**
     * 连接关闭调用的方法
     *
     * @param id
     */
    @OnClose
    public void onClose(Session session,
                        @PathParam("id") String id,
                        @PathParam("appName") String appName) throws IOException {
        Websocket.onClose(id, appName, session);
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息*/
    @OnMessage
    public void onMessage(Session session,
                          @PathParam("id") String id,
                          @PathParam("appName") String appName,
                          String message) {
        log.info("应用：" + appName + "-会话窗口:" + id + "，收到一条新的信息:" + message);
    }


    /**
     * 连接异常
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session,
                        @PathParam("id") String id,
                        @PathParam("appName") String appName,
                        Throwable error) throws IOException {
        session.close();
        log.error("应用：" + appName + "-会话窗口:" + id + "，异常信息" + error.getMessage());
    }
}
