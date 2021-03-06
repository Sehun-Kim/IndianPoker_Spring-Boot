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
    public void gameForm_no_login() {
        ResponseEntity<String> response = template().getForEntity("/indianpokers/form", String.class);
        softly.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
        logger.debug("body : {}", response.getBody());
    }

    @Test
    public void gameForm_login() {
        ResponseEntity<String> responseEntity = basicAuthTemplate().getForEntity("/indianpokers/form", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }

    @Test
    public void gameList_login() {
        ResponseEntity<String> responseEntity = basicAuthTemplate().getForEntity("/indianpokers", String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        logger.debug("body : {}", responseEntity.getBody());
    }

    @Test
    public void gameCreate_login() {
        htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm()
                .addParameter("preemptive", "TRUE")
                .addParameter("chipsSize", "20");
        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/indianpokers", htmlFormDataBuilder.build(), String.class);
        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/indianpokers/2");
    }

    //    @Test
//    public void gameStart_no_login() {
//        htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm()
//                .addParameter("firstBetter", "1")
//                .addParameter("chipsNum", "20");
//
//        ResponseEntity<String> responseEntity = template().postForEntity("/indianpoker/start", htmlFormDataBuilder.build(), String.class);
//        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.UNAUTHORIZED);
//        logger.debug("body : {}", responseEntity.getBody());
//    }
//
//    @Test
//    public void gameStart_login() {
//        htmlFormDataBuilder = HtmlFormDataBuilder.urlEncodedForm()
//                .addParameter("firstBetter", "1")
//                .addParameter("chipsNum", "20");
//
//        ResponseEntity<String> responseEntity = basicAuthTemplate().postForEntity("/indianpoker/start", htmlFormDataBuilder.build(), String.class);
//        softly.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.FOUND);
//        softly.assertThat(responseEntity.getHeaders().getLocation().getPath()).startsWith("/api/turn/1");

//    }

}
