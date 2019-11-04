package com.callenled.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: callenled
 * @Date: 19-11-4 下午2:44
 */
public class Websocket {

    private static Websocket INSTANCE;

    /**
     * 私有的默认构造函数
     */
    private Websocket(){}

    public synchronized static Websocket getInstance(){
        if(INSTANCE == null){
            synchronized (Websocket.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Websocket();
                }
            }
        }
        return INSTANCE;
    }

    private static Logger log = LoggerFactory.getLogger(Websocket.class);

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     * session 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private Map<String, Session> webSocketMap = new ConcurrentHashMap<>();

    public void onOpen(String id, Session session) throws IOException {
        if (!webSocketMap.containsKey(id)) {
            webSocketMap.put(id, session);
            log.info("有新窗口开始监听:" + id + ",当前在线人数为" + onlineCount());
            session.getBasicRemote().sendText("连接成功");
        } else {
            session.getBasicRemote().sendText("连接冲突，当前会话关闭");
            session.close();
        }
    }

    public void onClose(String id, Session session) {
        if (webSocketMap.containsKey(id) && webSocketMap.get(id) == session) {
            webSocketMap.remove(id);
            log.info("有一连接关闭:" + id + "！当前在线人数为" + onlineCount());
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String id, String message) throws IOException {
        if (webSocketMap.containsKey(id)) {
            log.info("向窗口: " + id + " 发送一条新的信息:" + message);
            webSocketMap.get(id).getBasicRemote().sendText(message);
        } else {
            log.error("推送失败，无法找到会话窗口:" + id);
        }
    }

    public int onlineCount() {
        return webSocketMap.size();
    }
}