package indianpoker.domain.user;

import indianpoker.exception.UnAuthenticationException;
import indianpoker.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.fixture.UserFixture;
import support.test.BaseTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest extends BaseTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void add() {
        User tester = UserFixture.getDefaultUser();
        when(userRepository.save(tester)).thenReturn(tester);
        softly.assertThat(userService.add(tester).getUserId()).isEqualTo(tester.getUserId());
    }

    @Test
    public void findById() {
        User user = UserFixture.getDefaultUser();
        user.setId(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        softly.assertThat(userService.findById(1L)).isEqualTo(user);
    }

    @Test
    public void login_success() throws UnAuthenticationException {
        String userId = "tester";
        String password = "1234";
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(UserFixture.getDefaultUser()));

        softly.assertThat(userService.login(userId, password).getPassword()).isEqualTo(UserFixture.getDefaultUser().getPassword());
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_failed() throws UnAuthenticationException {
        String userId = "tester";
        String password = "4231";
        when(userRepository.findByUserId(userId)).thenReturn(Optional.of(UserFixture.getDefaultUser()));

        softly.assertThat(userService.login(userId, password)).isEqualTo(UserFixture.getDefaultUser());
    }
}