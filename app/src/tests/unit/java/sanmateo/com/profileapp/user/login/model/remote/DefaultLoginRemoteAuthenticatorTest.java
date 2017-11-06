package sanmateo.com.profileapp.user.login.model.remote;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import io.reactivex.observers.TestObserver;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import sanmateo.com.profileapp.api.user.UserDto;
import sanmateo.com.profileapp.api.user.UserRemoteService;
import sanmateo.com.profileapp.factory.user.UserFactory;


import static java.net.HttpURLConnection.HTTP_OK;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by rsbulanon on 31/10/2017.
 */
public class DefaultLoginRemoteAuthenticatorTest {

    private MockWebServer mockWebServer;

    private UserRemoteService userRemoteService;

    private Retrofit retrofit;

    @Before
    public void setUp() {
        mockWebServer = new MockWebServer();

        retrofit = new Retrofit.Builder()
                       .baseUrl(mockWebServer.url(""))
                       .addConverterFactory(GsonConverterFactory.create())
                       .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                       .build();

        userRemoteService = retrofit.create(UserRemoteService.class);
    }

    @After
    public void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void loginSuccessful() throws InterruptedException {
        Gson gson = new Gson();

        UserDto expectedResponse = UserFactory.userDto();

        mockWebServer.enqueue(new MockResponse()
                                  .setResponseCode(HTTP_OK)
                                  .setBody(gson.toJson(expectedResponse)));

        TestObserver<UserDto> testObserver = new TestObserver();

        userRemoteService.authenticateUser("", "").subscribe(testObserver);

        testObserver.assertComplete();
        testObserver.assertNoErrors();

        UserDto actual = testObserver.values().get(0);

        assertThat(actual).isEqualToComparingFieldByField(expectedResponse);

        RecordedRequest recordedRequest = mockWebServer.takeRequest();

        assertThat(recordedRequest.getPath()).contains("/api/v1/login");
        assertThat(recordedRequest.getMethod()).isEqualTo("POST");
    }
}