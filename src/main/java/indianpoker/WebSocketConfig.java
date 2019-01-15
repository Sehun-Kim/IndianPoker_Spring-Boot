package indianpoker;

import indianpoker.socket.IndianPokerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private IndianPokerHandler indianPokerHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        // setAllowedOrigins 소켓 서버에 들어오는 모든 것을 허용한다.
        // endpoint websocket이 연결되는 url
        // websocketsession에 httpsession을 얹어 사용할 수 있게하는 interceptor
        registry.addHandler(indianPokerHandler, "/ws/game")
                .addInterceptors(new HttpSessionHandshakeInterceptor()).setAllowedOrigins("*").withSockJS();
    }
}
