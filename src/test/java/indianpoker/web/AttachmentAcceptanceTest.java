package indianpoker.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import support.test.AcceptanceTest;
import support.test.HtmlFormDataBuilder;

import static org.junit.Assert.*;

public class AttachmentAcceptanceTest extends AcceptanceTest {
    private static final Logger logger = LoggerFactory.getLogger(AttachmentAcceptanceTest.class);

    @Test
    public void download() throws Exception {
        ResponseEntity<String> result = template().getForEntity("/attachments/1", String.class);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        logger.debug("body : {}", result.getBody());
    }

    @Test
    public void upload() throws Exception {
        HttpEntity<MultiValueMap<String, Object>> request = HtmlFormDataBuilder
                .multipartFormData()
                .addParameter("file", new ClassPathResource("logback.xml"))
                .build();
        ResponseEntity<String> result = template().postForEntity("/attachments", request, String.class);
        assertEquals(HttpStatus.FOUND, result.getStatusCode());
    }

}