package indianpoker.security;

import indianpoker.domain.user.User;
import indianpoker.domain.user.UserService;
import indianpoker.exception.UnAuthenticationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import support.fixture.UserFixture;
import support.test.BaseTest;

import java.util.Base64;

import static org.mockito.Mockito.when;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class BasicAuthInterceptorTest extends BaseTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private BasicAuthInterceptor basicAuthInterceptor;

    @Test
    public void preHandle_login_success() throws Exception {
        User user = UserFixture.getDefaultUser();
        MockHttpServletRequest request = basicAuthHttpRequest(user.getUserId(), user.getPassword());
        when(userService.login(user.getUserId(), user.getPassword())).thenReturn(UserFixture.getDefaultUser());

        basicAuthInterceptor.preHandle(request, null, null);
        softly.assertThat(request.getSession().getAttribute(SessionUtil.USER_SESSION)).isEqualTo(UserFixture.getDefaultUser());
    }

    private MockHttpServletRequest basicAuthHttpRequest(String userId, String password) {
        String encodedBasicAuth = Base64.getEncoder().encodeToString(String.format("%s:%s", userId, password).getBytes());
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.addHeader("Authorization", "Basic " + encodedBasicAuth);
        return request;
    }
}