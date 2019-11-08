package com.callenled.websocket;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.websocket.Session;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: callenled
 * @Date: 19-11-4 下午2:44
 */
public class Websocket {

    /**
     * 私有的默认构造函数
     */
    private Websocket(Session session){
        this.session = session;
    }

    private static Logger log = LoggerFactory.getLogger(Websocket.class);

    private Session session;

    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的WebSocket对象。
     * session 与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    private static Map<String, Websocket> webSocketMap = new ConcurrentHashMap<>();

    public static void onOpen(String id, String appName, Session session) throws IOException {
        String key = Websocket.getKey(id, appName);
        Websocket websocket = webSocketMap.get(key);
        if (websocket == null) {
            websocket = new Websocket(session);
            webSocketMap.put(key, websocket);
            log.info("应用：" + appName + "-有新窗口开始监听:" + id + ",当前在线人数为" + onlineCount());
            websocket.sendMessage("连接成功");
        } else {
            websocket.sendMessage("连接冲突，当前会话关闭");
            websocket.onClose();
        }
    }

    public static void onClose(String id, String appName, Session session) throws IOException {
        String key = Websocket.getKey(id, appName);
        Websocket websocket = webSocketMap.get(key);
        if (websocket != null && websocket.isEquals(session)) {
            webSocketMap.remove(key);
            log.info("应用：" + appName + "-会话窗口关闭:" + id + "！当前在线人数为" + onlineCount());
            websocket.onClose();
        }
    }

    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 实现服务器主动推送
     */
    public static void sendMessage(String id, String appName, String message) throws IOException {
        String key = Websocket.getKey(id, appName);
        Websocket websocket = webSocketMap.get(key);
        if (websocket != null) {
            log.info("应用：" + appName + "-会话窗口: " + id + "，发送一条新的信息:" + message);
            websocket.sendMessage(message);
        } else {
            log.error("推送失败，应用:" + appName + "无法找到会话窗口:" + id);
        }
    }

    /**
     * 连接数
     *
     * @return
     */
    public static int onlineCount() {
        return webSocketMap.size();
    }

    private boolean isEquals(Session session) {
        return this.session == session;
    }

    private void onClose() throws IOException {
        this.session.close();
    }

    /**
     * 获取key值
     *
     * @param id
     * @param appName
     * @return
     */
    private static String getKey(String id, String appName) {
        return DigestUtils.md5DigestAsHex((id + "." + appName).getBytes());
    }
}
