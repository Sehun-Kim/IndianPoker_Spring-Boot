package indianpoker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indianpoker.dto.ErrorInfoDto;
import indianpoker.dto.GameInfoDto;
import indianpoker.dto.GameMessage;
import indianpoker.dto.ReceiveMessageDto;
import indianpoker.socket.sessions.GameSession;
import indianpoker.vo.Point;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

@Service
public class MessageService {

    @Autowired
    private ObjectMapper objectMapper;

    private static final Logger logger = LoggerFactory.getLogger(MessageService.class);

    public void sendGameInfo(GameInfoDto gameInfoDto, GameSession sessions) {
        // Announce To all
        sendToAll(gameInfoDto.getTurnInfoDto(), sessions);
        // Announce To better
        send(gameInfoDto.getBetterInfoDto(), sessions.getPlayerSession(gameInfoDto.getBetterName()));
    }

    public void sendError(ErrorInfoDto errorInfoDto, GameSession gameSession) {
        if (errorInfoDto.getPoint().equals(Point.BETTING))
            send(errorInfoDto, gameSession.getPlayerSession(errorInfoDto.getPlayerName()));
        if (errorInfoDto.getPoint().equals(Point.PLAYER_OUT))
            sendToAll(errorInfoDto, gameSession);
    }

    public void sendToAll(GameMessage gameMessage, GameSession sessions) {
        sessions.getAllSessions().forEach(session -> send(gameMessage, session));
    }

    public <T> void send(T messageObject, WebSocketSession session) {
        logger.debug("send : {}", session);
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            synchronized (session) {
                session.sendMessage(message);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public ReceiveMessageDto receiveMessage(TextMessage message) {
        String payload = message.getPayload();
        try {
            return objectMapper.readValue(payload, ReceiveMessageDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
