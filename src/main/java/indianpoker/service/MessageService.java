package indianpoker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indianpoker.dto.ErrorInfoDto;
import indianpoker.dto.GameInfoDto;
import indianpoker.dto.GameMessage;
import indianpoker.dto.ReceiveMessageDto;
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

        if (gameMessage.getType().equals(DtoType.NOTICE))
            sendNotice(gameMessage, sessions);

        if (gameMessage.getType().equals(DtoType.GAME_INFO))
            sendBettingInfo(gameMessage, sessions);

        if (gameMessage.getType().equals(DtoType.ERROR))
            sendError(gameMessage, sessions);

        if (gameMessage.getType().equals(DtoType.TURN_RESULT)) {
            sendTurnResult(gameMessage, sessions);
        }
    }

    private void sendTurnResult(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendTurnResult : {}", gameMessage);

    }

    private void sendNotice(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendNotice : {}", gameMessage);
        sendToAll(gameMessage, sessions);
    }

    private void sendBettingInfo(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendBettingInfo : {}", gameMessage);
        GameInfoDto gameInfoDto = (GameInfoDto) gameMessage;

        // Announce To all
        sendToAll(gameInfoDto.getTurnInfoDto(), sessions);

        // Announce To better
        send(gameInfoDto.getBetterInfoDto(),
                sessions.getPlayerSession(gameInfoDto.getBetterName()));
    }

    private void sendError(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendError : {}", gameMessage);
        ErrorInfoDto errorInfoDto = (ErrorInfoDto) gameMessage;

        // Announce To better
        send(errorInfoDto,
                sessions.getPlayerSession(errorInfoDto.getPlayerName()));
    }

    private void sendToAll(GameMessage gameMessage, GameSession sessions) {
        sessions.getAllSessions().forEach(session -> send(gameMessage, session));
    }

    private <T> void send(T messageObject, WebSocketSession session) {
        logger.debug("send : {}", session);
        try {
            TextMessage message = new TextMessage(objectMapper.writeValueAsString(messageObject));
            synchronized(session) {
                session.sendMessage(message);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }


    public ReceiveMessageDto recieveMessage(TextMessage message) {
        String payload = message.getPayload();
        try {
            return objectMapper.readValue(payload, ReceiveMessageDto.class);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
