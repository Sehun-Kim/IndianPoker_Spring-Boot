package indianpoker.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class IndianPokerAcceptanceTest extends AcceptanceTest {
    private HtmlFormDataBuilder htmlFormDataBuilder;

    private static final Logger logger = LoggerFactory.getLogger(IndianPokerAcceptanceTest.class);

    @Test
    public void gameStart_no_login() {
        ResponseEntity<String> response = template().getForEntity("/indianpoker/start", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void gameStart_login() {
        ResponseEntity<String> responseEntity = basicAuthTemplate().getForEntity("/indianpoker/start", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }
}
