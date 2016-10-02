package sanmateo.com.profileapp.helpers;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;
import sanmateo.com.profileapp.models.response.ApiError;
import sanmateo.com.profileapp.singletons.RetrofitSingleton;

/**
 * Created by rsbulanon on 10/2/16.
 */
public class ApiErrorHelper {

    public static ApiError parseError(Response<?> response) {
        final Converter<ResponseBody, ApiError> converter =
                RetrofitSingleton.getInstance().getRetrofit()
                        .responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return new ApiError();
        }
        return error;
    }
}
