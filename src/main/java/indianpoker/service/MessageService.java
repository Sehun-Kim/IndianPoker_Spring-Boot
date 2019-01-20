package indianpoker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import indianpoker.dto.ErrorInfoDto;
import indianpoker.dto.GameInfoDto;
import indianpoker.dto.GameMessage;
import indianpoker.dto.ReceiveMessageDto;
import indianpoker.socket.sessions.GameSession;
import indianpoker.vo.MessageType;
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

    // todo
    // if 제거
    public void sendMessage(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendMessage : {}", sessions);

        if (gameMessage.getType().equals(MessageType.NOTICE))
            sendNotice(gameMessage, sessions);

        if (gameMessage.getType().equals(MessageType.TURN_START))
            sendTurnStart(gameMessage, sessions);

        if (gameMessage.getType().equals(MessageType.GAME_INFO))
            sendGameInfo(gameMessage, sessions);

        if (gameMessage.getType().equals(MessageType.ERROR))
            sendError(gameMessage, sessions);

        if (gameMessage.getType().equals(MessageType.TURN_RESULT))
            sendTurnResult(gameMessage, sessions);

        if (gameMessage.getType().equals(MessageType.BETTING_RESULT))
            sendBettingResult(gameMessage, sessions);

        if (gameMessage.getType().equals(MessageType.GAME_RESULT))
            sendGameResult(gameMessage, sessions);
    }

    private void sendGameResult(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendGameResult : {}", gameMessage);
        sendToAll(gameMessage, sessions);
    }

    private void sendBettingResult(GameMessage gameMessage, GameSession sessions) {
        sendToAll(gameMessage, sessions);
    }

    private void sendTurnStart(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendTurnStart : {}", gameMessage);
        sendToAll(gameMessage, sessions);
    }

    private void sendTurnResult(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendTurnResult : {}", gameMessage);
        sendToAll(gameMessage, sessions);
    }

    private void sendNotice(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendNotice : {}", gameMessage);
        sendToAll(gameMessage, sessions);
    }

    private void sendGameInfo(GameMessage gameMessage, GameSession sessions) {
        logger.debug("sendGameInfo : {}", gameMessage);
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
        if (errorInfoDto.getPoint().equals(Point.BETTING))
            send(errorInfoDto, sessions.getPlayerSession(errorInfoDto.getPlayerName()));
        if (errorInfoDto.getPoint().equals(Point.PLAYER_OUT))
            sendToAll(errorInfoDto, sessions);
    }

    private void sendToAll(GameMessage gameMessage, GameSession sessions) {
        sessions.getAllSessions().forEach(session -> send(gameMessage, session));
    }

    private <T> void send(T messageObject, WebSocketSession session) {
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
