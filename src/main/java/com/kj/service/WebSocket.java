package com.kj.service;

import org.springframework.stereotype.Component;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/webSocket")
public class WebSocket {

    private Session session;

    private static CopyOnWriteArraySet<WebSocket> set = new CopyOnWriteArraySet<>();

    @OnOpen
    public void opOpen(Session session) {
        System.out.println("建立了连接");
        this.session = session;
        set.add(this);
    }

    @OnClose
    public void onClose() {
        System.out.println("关闭了连接");
        set.remove(this);
    }

    @OnMessage
    public void onMessage(String message) {
        System.out.println("接受到了消息");
        System.out.println(message);
    }

    public void sendMessage(String message) {
        for (WebSocket w : set) {
            System.out.println("我发送的消息是：" + message);
            try {
                synchronized (w.session) {
                    w.session.getBasicRemote().sendText(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
