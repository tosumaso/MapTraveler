package MapTraveler.develop.WebSocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer{

	@Override
	public void configureMessageBroker(MessageBrokerRegistry config) {
		config.enableSimpleBroker("/big"); // '/big'宛てに送られたメッセージはBrokerで直接ハンドリングされる
		config.setApplicationDestinationPrefixes("/app"); // ’/app’宛てにメッセージが送られた場合Controllerを経由し、その結果がBrokerを通りクライアントに渡る
		
	}
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/endpoint").withSockJS(); //エンドポイントを登録、websocketを扱うSockJSを有効化
	}
}
