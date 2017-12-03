package sanmateo.com.profileapp.incident.usecase.remote;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import sanmateo.com.profileapp.api.incident.IncidentRemoteService;
import sanmateo.com.profileapp.util.MockWebServiceRule;


import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static sanmateo.com.profileapp.factory.IncidentFactory.dto;

/**
 * Created by rsbulanon on 03/12/2017.
 */
public class DefaultIncidentRemoteLoaderTest {

    @Rule
    public MockWebServiceRule rule = new MockWebServiceRule();

    private DefaultIncidentRemoteLoader clasUnderTest;

    @Before
    public void setUp() {
        clasUnderTest = new DefaultIncidentRemoteLoader(rule.create(IncidentRemoteService.class));
    }

    @Test
    public void loadingOfIncindentsWillFail() {
        rule.server()
            .enqueue(new MockResponse().setResponseCode(HTTP_BAD_REQUEST));

        clasUnderTest.loadIncidents(0, 10, "")
                     .test()
                     .assertNotComplete()
                     .assertError(Throwable.class);
    }

    @Test
    public void loadingOfIncindentsWillSucceed() {
        rule.server()
            .enqueue(new MockResponse()
                         .setResponseCode(HTTP_OK)
                         .setBody(rule.toBody(dto())));

        clasUnderTest.loadIncidents(0, 10, "")
                     .test()
                     .assertComplete()
                     .assertNoErrors();
    }
}