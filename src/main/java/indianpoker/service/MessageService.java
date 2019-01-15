package indianpoker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indianpoker.dto.BettingInfoDto;
import indianpoker.dto.GameMessage;
import indianpoker.socket.sessions.GameSession;
import indianpoker.vo.DtoType;
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

    public void sendMessage(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendMessage : {}", sessions);

        if (gameMessage.getType().equals(DtoType.NOTICE)) {
            sendNotice(gameMessage, sessions);
        }

        if (gameMessage.getType().equals(DtoType.BETTING)) {
            sendBettingInfo(gameMessage, sessions);
        }
    }

    private void sendNotice(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendNotice : {}", sessions);
        sendToAll(gameMessage, sessions);
    }

    private void sendBettingInfo(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendBettingInfo : {}", sessions);
        BettingInfoDto bettingInfoDto = (BettingInfoDto) gameMessage;

        // Announce To all
        sendToAll(bettingInfoDto.getAllBettingInfoDto(), sessions);

        // Announce To better
        WebSocketSession session = sessions.getPlayerSession(bettingInfoDto.getBetterName());
        send(bettingInfoDto.getSingleBettingInfoDto(), session);
    }

    private void sendToAll(GameMessage gameMessage, GameSession sessions) {
        sessions.getAllSessions().forEach(session -> send(gameMessage, session));
    }

    private <T> void send(T messageObject, WebSocketSession session) {
        logger.debug("send : {}", session);
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            session.sendMessage(message);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

}
