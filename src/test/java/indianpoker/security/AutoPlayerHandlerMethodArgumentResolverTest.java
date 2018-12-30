package indianpoker.security;

import indianpoker.domain.player.Player;
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

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AutoPlayerHandlerMethodArgumentResolverTest extends BaseTest {
    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest request;

    private AutoPlayerHandlerMethodArgumentResolver autoPlayerHandlerMethodArgumentResolver;

    @Before
    public void setUp() throws Exception {
        autoPlayerHandlerMethodArgumentResolver = new AutoPlayerHandlerMethodArgumentResolver();
    }

    @Test
    public void supportParameter_true() {
        when(parameter.hasParameterAnnotation(AutoPlayer.class)).thenReturn(true);
        softly.assertThat(autoPlayerHandlerMethodArgumentResolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    public void name() {
        when(parameter.hasParameterAnnotation(AutoPlayer.class)).thenReturn(false);
        softly.assertThat(autoPlayerHandlerMethodArgumentResolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void autoPlayer_normal() throws Exception {
        Player autoPlayer = PlayerFixture.getAutoPlayer();
        when(request.getAttribute(SessionUtil.AUTO_PLAYER, WebRequest.SCOPE_SESSION)).thenReturn(autoPlayer);

        softly.assertThat(autoPlayerHandlerMethodArgumentResolver.resolveArgument(parameter, null, request, null)).isInstanceOf(Player.class);
    }
}