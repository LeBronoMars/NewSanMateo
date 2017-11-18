package sanmateo.com.profileapp.waterlevel.usecase.remote;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelDto;
import sanmateo.com.profileapp.api.waterlevel.WaterLevelRemoteService;
import sanmateo.com.profileapp.factory.WaterLevelFactory;
import sanmateo.com.profileapp.util.MockWebServiceRule;
import sanmateo.com.profileapp.waterlevel.usecase.WaterLevel;
import sanmateo.com.profileapp.waterlevel.usecase.remote.mapper.DtoToWaterLevelMapper;


import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;
import static sanmateo.com.profileapp.factory.WaterLevelFactory.dtos;

/**
 * Created by rsbulanon on 18/11/2017.
 */
public class DefaultWaterLevelRemoteLoaderTest {

    @Rule
    public MockWebServiceRule rule = new MockWebServiceRule();

    private DefaultWaterLevelRemoteLoader classUnderTest;

    @Before
    public void setUp() {
        classUnderTest = new DefaultWaterLevelRemoteLoader(
            rule.create(WaterLevelRemoteService.class));
    }

    @Test
    public void loadWaterLevelsByAreaWillSucceed() throws InterruptedException {
        rule.server().enqueue(new MockResponse()
                                  .setResponseCode(HTTP_OK)
                                  .setBody(rule.toBody(dtos())));

        classUnderTest.waterLevels("").test()
                      .assertComplete()
                      .assertNoErrors();

        RecordedRequest recordedRequest = rule.server().takeRequest();

        assertThat(recordedRequest.getPath()).contains("/api/v1/water_level");
        assertThat(recordedRequest.getMethod()).isEqualTo("GET");

    }
}