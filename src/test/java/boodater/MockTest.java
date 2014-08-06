package boodater;

import org.junit.Before;
import org.mockito.MockitoAnnotations;

public abstract class MockTest extends BaseTest {
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
}
