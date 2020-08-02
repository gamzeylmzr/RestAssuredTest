package suites;

import com.gamzeyilmazer.GetRequestParameterizedTest;
import com.gamzeyilmazer.GetRequestTest;
import com.gamzeyilmazer.PostRequestTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetRequestTest.class,
        GetRequestParameterizedTest.class,
        PostRequestTest.class
})
public class TestSuite {
}
