package support.test;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.domain.humanplayer.HumanPlayerRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class AcceptanceTest extends BaseTest{
    private static final String TESTER = "tester1";

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private HumanPlayerRepository humanPlayerRepository;

    public TestRestTemplate template() {
        return this.template;
    }

    public TestRestTemplate basicAuthTemplate() {
        return basicAuthTemplate(defaultUser());
    }

    public TestRestTemplate basicAuthTemplate(HumanPlayer humanPlayer) {
        return this.template.withBasicAuth(humanPlayer.getPlayerName(), humanPlayer.getPassword());
    }

    protected HumanPlayer defaultUser() {
        return humanPlayerRepository.findByPlayerName(TESTER).get();
    }
}
