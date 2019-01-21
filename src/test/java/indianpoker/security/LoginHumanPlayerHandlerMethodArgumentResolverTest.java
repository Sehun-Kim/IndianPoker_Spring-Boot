package indianpoker.security;

import indianpoker.domain.humanplayer.HumanPlayer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import support.fixture.PlayerFixture;
import support.test.BaseTest;
import support.util.SessionUtil;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginHumanPlayerHandlerMethodArgumentResolverTest extends BaseTest {
    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest request;

    @Mock
    private LoginPlayer annotedLoginUser;

    private LoginPlayerHandlerMethodArgumentResolver loginPlayerHandlerMethodArgumentResolver;

    @Before
    public void setUp() throws Exception {
        loginPlayerHandlerMethodArgumentResolver = new LoginPlayerHandlerMethodArgumentResolver();
    }

    @Test
    public void supportParameter_true() {
        when(parameter.hasParameterAnnotation(LoginPlayer.class)).thenReturn(true);
        softly.assertThat(loginPlayerHandlerMethodArgumentResolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    public void supportParameter_false() {
        when(parameter.hasParameterAnnotation(LoginPlayer.class)).thenReturn(false);
        softly.assertThat(loginPlayerHandlerMethodArgumentResolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void login_normal() throws Exception {
        HumanPlayer sessionHumanPlayer = PlayerFixture.getDefaultHumanPlayer();
        when(request.getAttribute(SessionUtil.PLAYER_SESSION, WebRequest.SCOPE_SESSION)).thenReturn(sessionHumanPlayer);

        HumanPlayer loginHumanPlayer = (HumanPlayer) loginPlayerHandlerMethodArgumentResolver.resolveArgument(parameter, null, request, null);
        softly.assertThat(loginHumanPlayer).isEqualTo(sessionHumanPlayer);
    }
}