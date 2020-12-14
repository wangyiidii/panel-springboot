package cn.yiidii.panel.common.websocket4test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint(value = "/ws/trendChart")
@Component
@Slf4j
@Data
public class TrendServer {

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        TrendDataWraper.add(this);
    }

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    @OnClose
    public void onClose() {
        TrendDataWraper.remove(this);
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("来自客户端[" + WebSocketUtil.getRemoteAddress(session) + "]的消息:" + message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

}
