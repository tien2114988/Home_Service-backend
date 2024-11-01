//package com.larkEWA.config;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.HandshakeInterceptor;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServerHttpResponse;
//
//import java.util.Map;
//
//@Configuration
//public class WebSocketInterceptorConfig implements HandshakeInterceptor {
//
//    @Override
//    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
//        // Lấy thông tin người dùng từ query parameters
//        String query = request.getURI().getQuery();
//        if (query != null) {
//            String[] params = query.split("&");
//            for (String param : params) {
//                if (param.contains("staffId")) {
//                    String staffId = param.split("=")[1];
//                    attributes.put("staffId", staffId);
//                } else if (param.contains("companyId")) {
//                    String companyId = param.split("=")[1];
//                    attributes.put("companyId", companyId);
//                }
//            }
//        }
//        return true;
//    }
//
//    @Override
//    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler, Exception ex) {
//        // Không cần thực hiện gì sau khi bắt tay hoàn tất
//    }
//}
