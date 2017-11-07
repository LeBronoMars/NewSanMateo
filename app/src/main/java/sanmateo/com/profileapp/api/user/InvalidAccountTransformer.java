package sanmateo.com.profileapp.api.user;

import java.net.HttpURLConnection;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.SingleTransformer;
import retrofit2.Response;

public class InvalidAccountTransformer<T>
    implements SingleTransformer<Response<T>, Response<T>> {

    @Override
    public SingleSource<Response<T>> apply(Single<Response<T>> upstream) {
        return upstream.flatMap(
            response -> {
                if (response.code() == HttpURLConnection.HTTP_UNAUTHORIZED)
                    return Single.error(new InvalidAccountException());
                return Single.just(response);
            }
        );
    }
}
