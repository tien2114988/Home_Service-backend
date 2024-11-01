//package com.larkEWA.config;
//
//import com.larkEWA.utils.WebsocketHandlerUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.socket.config.annotation.*;
//
//@Configuration
//@EnableWebSocket
//public class WebSocketConfig implements WebSocketConfigurer {
//    @Autowired
//    private WebSocketInterceptorConfig wsInterceptorConfig;
//
//
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler(), "/data").addInterceptors(wsInterceptorConfig);
//    }
//
//    @Bean
//    public WebsocketHandlerUtil webSocketHandler() {
//        return new WebsocketHandlerUtil();
//    }
//
//}
