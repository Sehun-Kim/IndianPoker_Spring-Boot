package indianpoker.web;

import indianpoker.domain.user.UserRepository;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

public class UserAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(UserAcceptanceTest.class);
    private HtmlFormDataBuilder htmlFormDataBuilder;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void createForm() {
        ResponseEntity<String> responseEntity = template().getForEntity("/users/form", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }

    @Test
    public void create_with_no_picture() {
        htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm().addParameter("userId", "tester2").addParameter("password", "1234");

        ResponseEntity<String> responseEntity = template().postForEntity("/users", htmlFormDataBuilder.build(), String.class);

        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(userRepository.findByUserId("tester2").isPresent()).isTrue();
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/login");
    }

    @Test
    public void create_with_picture() {
        ClassPathResource classPathResource = new ClassPathResource("./uploads/example.jpeg");
        htmlFormDataBuilder = HtmlFormDataBuilder.multipartFormData()
                .addParameter("userId", "tester2")
                .addParameter("password", "1234")
                .addParameter("pic", classPathResource);

        ResponseEntity<String> responseEntity = template().postForEntity("/users", htmlFormDataBuilder.build(), String.class);
    }

    @Test
    public void show() {
        ResponseEntity<String> responseEntity = template().getForEntity("/users/1", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }
}