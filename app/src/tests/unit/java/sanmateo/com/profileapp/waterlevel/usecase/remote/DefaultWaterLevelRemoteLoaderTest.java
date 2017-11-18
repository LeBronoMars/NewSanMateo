package sanmateo.com.profileapp.waterlevel.usecase.remote;

import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;


import static java.net.HttpURLConnection.HTTP_OK;

/**
 * Created by rsbulanon on 18/11/2017.
 */
public class DefaultWaterLevelRemoteLoaderTest {

    @Rule
    public MockWebServer mockWebServer = new MockWebServer();

    private DefaultWaterLevelRemoteLoader classUnderTest;

    @Test
    public void loadWaterLevelsByAreaWillSucceed() {
        mockWebServer.enqueue(new MockResponse().setResponseCode(HTTP_OK));
    }
}