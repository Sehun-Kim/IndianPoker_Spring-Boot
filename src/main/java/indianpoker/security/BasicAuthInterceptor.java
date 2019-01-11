package indianpoker.security;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.exception.UnAuthenticationException;
import indianpoker.service.HumanPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import support.util.SessionUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.Base64;

public class BasicAuthInterceptor extends HandlerInterceptorAdapter {
    private static final Logger logger = LoggerFactory.getLogger(BasicAuthInterceptor.class);

    @Autowired
    private HumanPlayerService humanPlayerService;

    // 브라우저에서 컨트롤러로 요청이 가기 전, 컨트롤러에서 처리한 응답이 브라우저에 가기 전
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization"); // 요청에 Authorization 이라는 헤더필드의 값
        logger.debug("Authorization : {}", authorization);
        if (authorization == null || !authorization.startsWith("Basic")) { // Authorization 헤더의 값이 없거나, 값이 Basic인증으로 시작하지 않을 때
            return true;
        }

        String base64Credentials = authorization.substring("Basic".length()).trim(); // Basic과 띄어쓰기를 자른 다음 값
        String credentials = new String(Base64.getDecoder().decode(base64Credentials), Charset.forName("UTF-8")); // Base64로 인코딩되어있는 값을 UTF-8 형식으로 디코딩
        final String[] values = credentials.split(":", 2); // id : password 분리
        logger.debug("username : {}", values[0]);
        logger.debug("password : {}", values[1]);

        // login
        try {
            HumanPlayer loginedHumanPlayer = humanPlayerService.login(values[0], values[1]);
            logger.debug("loginedHumanPlayer : {}", loginedHumanPlayer);
            request.getSession().setAttribute(SessionUtil.PLAYER_SESSION, loginedHumanPlayer);
            return true;
        } catch (UnAuthenticationException e) {
            return true;
        }
    }

}
