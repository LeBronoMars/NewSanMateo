package sanmateo.com.profileapp.util;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import org.junit.rules.ExternalResource;

import java.net.HttpURLConnection;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MockWebServiceRule extends ExternalResource {

    private Gson gson;

    private MockWebServer mockWebServer;

    private Retrofit retrofit;

    public MockWebServiceRule() {
        gson = new Gson();

        mockWebServer = new MockWebServer();

        retrofit = new Retrofit.Builder()
                       .baseUrl(mockWebServer.url(""))
                       .addConverterFactory(GsonConverterFactory.create())
                       .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                       .build();
    }

    public <T> T create(Class<T> clazz) {
        return retrofit.create(clazz);
    }

    public void enqueueExpiredSessionResponse() {
        mockWebServer.enqueue(new MockResponse()
                                  .setResponseCode(HttpURLConnection.HTTP_UNAUTHORIZED));
    }

    public <T> T fromBodyOf(RecordedRequest request, Class<T> clazz) {
        return gson.fromJson(request.getBody().readUtf8(), clazz);
    }

    public MockWebServer server() {
        return mockWebServer;
    }

    public String toBody(Object object) {
        return gson.toJson(object);
    }
}
