package indianpoker.security;

import indianpoker.domain.user.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;
import support.fixture.UserFixture;
import support.test.BaseTest;
import support.util.SessionUtil;

@RunWith(MockitoJUnitRunner.class)
public class LoginUserHandlerMethodArgumentResolverTest extends BaseTest {
    @Mock
    private MethodParameter parameter;

    @Mock
    private NativeWebRequest request;

    @Mock
    private LoginUser annotedLoginUser;

    private LoginUserHandlerMethodArgumentResolver loginUserHandlerMethodArgumentResolver;

    @Before
    public void setUp() throws Exception {
        loginUserHandlerMethodArgumentResolver = new LoginUserHandlerMethodArgumentResolver();
    }

    @Test
    public void supportParameter_true() {
        when(parameter.hasParameterAnnotation(LoginUser.class)).thenReturn(true);
        softly.assertThat(loginUserHandlerMethodArgumentResolver.supportsParameter(parameter)).isTrue();
    }

    @Test
    public void supportParameter_false() {
        when(parameter.hasParameterAnnotation(LoginUser.class)).thenReturn(false);
        softly.assertThat(loginUserHandlerMethodArgumentResolver.supportsParameter(parameter)).isFalse();
    }

    @Test
    public void login_normal() throws Exception {
        User sessionUser = UserFixture.getDefaultUser();
        when(request.getAttribute(SessionUtil.USER_SESSION, WebRequest.SCOPE_SESSION)).thenReturn(sessionUser);

        User loginUser = (User) loginUserHandlerMethodArgumentResolver.resolveArgument(parameter, null, request, null);
        softly.assertThat(loginUser).isEqualTo(sessionUser);
    }
}