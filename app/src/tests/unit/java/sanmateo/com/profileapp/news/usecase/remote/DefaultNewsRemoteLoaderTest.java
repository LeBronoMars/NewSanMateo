package sanmateo.com.profileapp.news.usecase.remote;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import okhttp3.mockwebserver.MockResponse;
import sanmateo.com.profileapp.api.news.NewsRemoteService;
import sanmateo.com.profileapp.factory.NewsFactory;
import sanmateo.com.profileapp.util.MockWebServiceRule;


import static java.net.HttpURLConnection.HTTP_BAD_REQUEST;
import static java.net.HttpURLConnection.HTTP_OK;
import static sanmateo.com.profileapp.factory.NewsFactory.dto;

/**
 * Created by rsbulanon on 02/12/2017.
 */
public class DefaultNewsRemoteLoaderTest {

    @Rule
    public MockWebServiceRule rule = new MockWebServiceRule();

    private DefaultNewsRemoteLoader classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new DefaultNewsRemoteLoader(rule.create(NewsRemoteService.class));
    }

    @Test
    public void loadingOfNewsWillFail() {
        rule.server().enqueue(new MockResponse().setResponseCode(HTTP_BAD_REQUEST));
        classUnderTest.loadNews(0, 10)
                      .test()
                      .assertNotComplete()
                      .assertError(Throwable.class);
    }

    @Test
    public void loadingOfNewsWillSucceed() {
        rule.server()
            .enqueue(new MockResponse()
                         .setResponseCode(HTTP_OK)
                         .setBody(rule.toBody(dto())));

        classUnderTest.loadNews(0, 10)
                      .test()
                      .assertComplete()
                      .assertNoErrors();
    }
}