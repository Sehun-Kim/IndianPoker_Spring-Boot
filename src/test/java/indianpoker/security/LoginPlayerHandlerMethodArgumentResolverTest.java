package indianpoker.security;

import indianpoker.domain.player.Player;
import indianpoker.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import support.fixture.UserFixture;
import support.test.BaseTest;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginPlayerHandlerMethodArgumentResolverTest extends BaseTest {
    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest request;

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
    public void player_normal() throws Exception {
        Player sessionPlayer = UserFixture.getDefaultUser().toPlayer();
        User sessionUser = (User) sessionPlayer;
        when(request.getAttribute(SessionUtil.USER_SESSION, WebRequest.SCOPE_SESSION)).thenReturn(sessionUser);

        softly.assertThat(loginPlayerHandlerMethodArgumentResolver.resolveArgument(parameter, null, request, null)).isInstanceOf(Player.class);
        Player loginPlayer = (Player) loginPlayerHandlerMethodArgumentResolver.resolveArgument(parameter, null, request, null);
        softly.assertThat(loginPlayer).isEqualTo(sessionPlayer);
    }
}