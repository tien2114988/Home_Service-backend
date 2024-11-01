//package com.larkEWA.utils;
//
//import com.larkEWA.entity.Staff;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.web.socket.CloseStatus;
//import org.springframework.web.socket.SubProtocolCapable;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//import org.springframework.web.util.HtmlUtils;
//
//import java.io.IOException;
//import java.util.Collections;
//import java.util.List;
//import java.util.Set;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.CopyOnWriteArraySet;
//
//public class WebsocketHandlerUtil extends TextWebSocketHandler {
//    private static final Logger logger = LoggerFactory.getLogger(WebsocketHandlerUtil.class);
//
//    private final Set<WebSocketSession> sessions = new CopyOnWriteArraySet<>();
//    private final ConcurrentHashMap<String, WebSocketSession> staffSessions = new ConcurrentHashMap<>();
//    private final ConcurrentHashMap<String, Set<WebSocketSession>> companySessions = new ConcurrentHashMap<>();
//
//    @Override
//    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
//        logger.info("Server connection opened");
//        sessions.add(session);
//
//        // Lấy thông tin staffId và companyId từ attributes
//        String staffId = (String) session.getAttributes().get("staffId");
//        String companyId = (String) session.getAttributes().get("companyId");
//
//        TextMessage message = null;
//
//        if (staffId != null) {
//            staffSessions.put(staffId, session);
//            message = new TextMessage(staffId + ": one-time message from server");
//        }
//        if (companyId != null) {
//            companySessions.computeIfAbsent(companyId, k -> new CopyOnWriteArraySet<>()).add(session);
//            message = new TextMessage(companyId + ": one-time message from server");
//        }
//
//        logger.info("Server sends: {}", message);
//        assert message != null;
//        session.sendMessage(message);
//    }
//
//
//    @Override
//    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
//        logger.info("Server connection closed: {}", status);
//        sessions.remove(session);
//
//        // Xóa session khỏi staffSessions và companySessions
//        staffSessions.values().remove(session);
//        companySessions.values().forEach(set -> set.remove(session));
//    }
//
//    public void sendMessageToStaff(String staffId, String message) throws IOException {
//        WebSocketSession session = staffSessions.get(staffId);
//        if (session != null && session.isOpen()) {
//            logger.info("Server sends to staff {}: {}", staffId, message);
//            session.sendMessage(new TextMessage(message));
//        }
//    }
//
//    public void sendMessageToCompany(String companyId, String message) throws IOException {
//        Set<WebSocketSession> sessions = companySessions.get(companyId);
//        if (sessions != null) {
//            for (WebSocketSession session : sessions) {
//                if (session.isOpen()) {
//                    logger.info("Server sends to company {}: {}", companyId, message);
//                    session.sendMessage(new TextMessage(message));
//                }
//            }
//        }
//    }
//
//    @Override
//    public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
//        String request = message.getPayload();
//        logger.info("Server received: {}", request);
//
//        String response = String.format("response from server to '%s'", request);
//        logger.info("Server sends: {}", response);
//        session.sendMessage(new TextMessage(response));
//    }
//
//    public void sendPingMessages() {
//        for (WebSocketSession session : sessions) {
//            if (session.isOpen()) {
//                try {
//                    session.sendMessage(new TextMessage("ping pong"));
//                    logger.info("Ping message sent to session: {}", session.getId());
//                } catch (IOException e) {
//                    logger.error("Error sending ping message to session: {}", session.getId(), e);
//                }
//            }
//        }
//    }
//
//
//    @Override
//    public void handleTransportError(WebSocketSession session, Throwable exception) {
//        logger.error("Server transport error: {}", exception);
//    }
//}