package indianpoker.service;

import indianpoker.domain.Turn;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import support.test.BaseTest;

@RunWith(MockitoJUnitRunner.class)
public class TurnServiceTest extends BaseTest {

    @Mock
    private Turn turn;

    @InjectMocks
    private TurnService turnService;

    @Before
    public void setUp() throws Exception {
        turn = new Turn();
    }

}