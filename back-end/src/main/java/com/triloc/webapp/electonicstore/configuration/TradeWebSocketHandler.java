package com.triloc.webapp.electonicstore.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class TradeWebSocketHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    Random r = new Random();


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

        TextMessage message = new TextMessage("New Product created");
        session.sendMessage(message);
        Thread.sleep(1000);

        sessions.add(session);
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        // Handle the received message
        String clientMessage = message.getPayload();
        System.out.println("Session id:" +session.getId());
        System.out.println("Message received: " + clientMessage);
        // You can add logic here to respond to the message or broadcast it to other sessions
    }

    public List<WebSocketSession> getSessions() {
        return Collections.unmodifiableList(sessions);
    }
}
