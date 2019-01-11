package indianpoker.domain.user;

import indianpoker.domain.humanplayer.HumanPlayer;
import indianpoker.domain.humanplayer.Picture;
import indianpoker.domain.humanplayer.HumanPlayerRepository;
import indianpoker.exception.UnAuthenticationException;
import indianpoker.service.HumanPlayerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.fixture.PlayerFixture;
import support.test.BaseTest;

import java.util.Optional;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class HumanPlayerServiceTest extends BaseTest {

    @Mock
    private HumanPlayerRepository humanPlayerRepository;

    @InjectMocks
    private HumanPlayerService humanPlayerService;

    @Test
    public void add() {
        HumanPlayer tester = PlayerFixture.getDefaultHumanPlayer();
        Picture picture = Picture.DEFAULT_PICTURE;
        HumanPlayer result = tester;
        result.setPicture(picture);
        when(humanPlayerRepository.save(tester)).thenReturn(result);
        softly.assertThat(humanPlayerService.add(tester, picture).getPlayerName()).isEqualTo(tester.getPlayerName());
        softly.assertThat(humanPlayerService.add(tester, picture).getPicture().getOriginalFileName()).isEqualTo(picture.getOriginalFileName());
    }

    @Test
    public void findById() {
        HumanPlayer humanPlayer = PlayerFixture.getDefaultHumanPlayer();
        humanPlayer.setId(1L);
        when(humanPlayerRepository.findById(1L)).thenReturn(Optional.of(humanPlayer));

        softly.assertThat(humanPlayerService.findById(1L)).isEqualTo(humanPlayer);
    }

    @Test
    public void login_success() throws UnAuthenticationException {
        String userId = "tester";
        String password = "1234";
        when(humanPlayerRepository.findByPlayerName(userId)).thenReturn(Optional.of(PlayerFixture.getDefaultHumanPlayer()));

        softly.assertThat(humanPlayerService.login(userId, password).getPassword()).isEqualTo(PlayerFixture.getDefaultHumanPlayer().getPassword());
    }

    @Test(expected = UnAuthenticationException.class)
    public void login_failed() throws UnAuthenticationException {
        String userId = "tester";
        String password = "4231";
        when(humanPlayerRepository.findByPlayerName(userId)).thenReturn(Optional.of(PlayerFixture.getDefaultHumanPlayer()));

        softly.assertThat(humanPlayerService.login(userId, password)).isEqualTo(PlayerFixture.getDefaultHumanPlayer());
    }
}