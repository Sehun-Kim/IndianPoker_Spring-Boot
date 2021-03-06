package indianpoker.web;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.service.HumanPlayerService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.fixture.PlayerFixture;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class LoginAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(LoginAcceptanceTest.class);
    private static HtmlFormDataBuilder htmlFormDataBuilder;

    @Autowired
    private HumanPlayerService humanPlayerService;

    @Test
    public void loginForm() {
        ResponseEntity<String> responseEntity = template().getForEntity("/login", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }

    @Test
    public void login() {
        HumanPlayer humanPlayer = PlayerFixture.getDefaultHumanPlayer();
        htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("playerName", humanPlayer.getPlayerName())
                .addParameter("password", humanPlayer.getPassword());

        ResponseEntity<String> responseEntity = template().postForEntity("/login", htmlFormDataBuilder.build(), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/");
    }

    @Test
    public void login_failed() {
        htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("userId", "id").addParameter("password", "1234");

        ResponseEntity<String> responseEntity = template().postForEntity("/login", htmlFormDataBuilder.build(), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/login");
    }
}
