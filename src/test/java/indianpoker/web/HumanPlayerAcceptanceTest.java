package indianpoker.web;

import indianpoker.domain.humanplayer.HumanPlayerRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import java.io.File;

public class HumanPlayerAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(HumanPlayerAcceptanceTest.class);
    private HtmlFormDataBuilder htmlFormDataBuilder;

    @Autowired
    private HumanPlayerRepository humanPlayerRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> responseEntity = template().getForEntity("/players/form", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }

    @Test
    public void create_with_no_picture() {
        htmlFormDataBuilder = HtmlFormDataBuilder.multipartFormData()
                .addParameter("playerName", "tester3")
                .addParameter("password", "1234");

        ResponseEntity<String> responseEntity = template().postForEntity("/players", htmlFormDataBuilder.build(), String.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(humanPlayerRepository.findByPlayerName("tester2").isPresent()).isTrue();
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/login");
    }

    @Test
    public void create_with_picture() {
        File image = new File("/Users/sehun/Desktop/uploads/example.jpeg");
        htmlFormDataBuilder = HtmlFormDataBuilder.multipartFormData()
                .addParameter("playerName", "tester2")
                .addParameter("password", "1234")
                .addParameter("pic", image);

        ResponseEntity<String> responseEntity = template().postForEntity("/players", htmlFormDataBuilder.build(), String.class);
    }

    @Test
    public void show() {
        ResponseEntity<String> responseEntity = template().getForEntity("/players/1", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }
}