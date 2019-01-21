package indianpoker.security;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.service.HumanPlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import support.fixture.PlayerFixture;
import support.test.BaseTest;
import support.util.SessionUtil;

import java.util.Base64;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BasicAuthInterceptorTest extends BaseTest {
    @Mock
    private HumanPlayerService humanPlayerService;

    @InjectMocks
    private BasicAuthInterceptor basicAuthInterceptor;

    @Test
    public void preHandle_login_success() throws Exception {
        HumanPlayer humanPlayer = PlayerFixture.getDefaultHumanPlayer();
        MockHttpServletRequest request = basicAuthHttpRequest(humanPlayer.getPlayerName(), humanPlayer.getPassword());
        when(humanPlayerService.login(humanPlayer.getPlayerName(), humanPlayer.getPassword())).thenReturn(PlayerFixture.getDefaultHumanPlayer());

        basicAuthInterceptor.preHandle(request, null, null);
        softly.assertThat(request.getSession().getAttribute(SessionUtil.PLAYER_SESSION)).isEqualTo(PlayerFixture.getDefaultHumanPlayer());
    }

    private MockHttpServletRequest basicAuthHttpRequest(String userId, String password) {
        String encodedBasicAuth = Base64.getEncoder().encodeToString(String.format("%s:%s", userId, password).getBytes());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodedBasicAuth);
        return request;
    }
}