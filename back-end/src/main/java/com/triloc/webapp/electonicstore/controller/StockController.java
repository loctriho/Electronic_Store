package com.triloc.webapp.electonicstore.controller;

import com.triloc.webapp.electonicstore.configuration.TradeWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@RestController
public class StockController {

    private static int count = 0;

    @Autowired
    private TradeWebSocketHandler tradeWebSocketHandler;

    @GetMapping("/send-stock")
    public String sendStockUpdate() {
        count = count + 1;
        List<WebSocketSession> sessions = tradeWebSocketHandler.getSessions();
        for (WebSocketSession session : sessions) {
            if (session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage("Stock update from server:" + count));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return "Stock update sent to all clients";
    }
}