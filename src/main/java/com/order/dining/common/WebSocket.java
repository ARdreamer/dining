package com.order.dining.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @Author: baojx
 * @Date: 2020/2/24 14:33
 * @Desc: 通信
 */
@Component
@Slf4j
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> webSockets = new CopyOnWriteArraySet<>();

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        webSockets.add(this);
        log.info("【WebSocket消息】有新的连接，总数：{}", webSockets.size());
    }

    @OnClose
    public void onClose() {
        webSockets.remove(this);
        log.info("【WebSocket消息】移除连接，总数：{}", webSockets.size());
    }

    @OnMessage
    public void onMessage(String msg) {
        log.info("【WebSocket消息】收到消息，内容：{}", msg);
    }

    public void sendMessage(String msg) {
        for (WebSocket webSocket : webSockets) {
            try {
                webSocket.session.getBasicRemote().sendText(msg);
            } catch (IOException e) {
                log.info("【WebSocket消息】广播消息出错,", e);

            }
            log.info("【WebSocket消息】广播消息，内容：{}", msg);
        }
    }
}
