package sanmateo.com.profileapp.helpers;

import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import sanmateo.com.profileapp.enums.ApiAction;
import sanmateo.com.profileapp.interfaces.ApiInterface;
import sanmateo.com.profileapp.interfaces.OnApiRequestListener;
import sanmateo.com.profileapp.models.response.AuthResponse;
import sanmateo.com.profileapp.singletons.RetrofitSingleton;

/**
 * Created by rsbulanon on 6/23/16.
 */
public class ApiRequestHelper {
    private ApiInterface apiInterface;
    private OnApiRequestListener onApiRequestListener;

    public ApiRequestHelper(OnApiRequestListener onApiRequestListener) {
        this.apiInterface = RetrofitSingleton.getInstance().getApiInterface();
        this.onApiRequestListener = onApiRequestListener;
    }

    public void authenticateUser(final String email, final String password) {
        onApiRequestListener.onApiRequestBegin(ApiAction.POST_AUTH);
        final Observable<AuthResponse> observable = apiInterface.authenticateUser(email, password);
        handleObservableResult(ApiAction.POST_AUTH, observable);
    }

    /**
     * handle api result using lambda
     *
     * @param action identification of the current api request
     * @param observable actual process of the api request
     * */
    private void handleObservableResult(final ApiAction action, final Observable observable) {
        observable.observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
                .subscribe(result -> onApiRequestListener.onApiRequestSuccess(action,result),
                        throwable -> onApiRequestListener.onApiRequestFailed(action, (Throwable) throwable),
                        () -> LogHelper.log("api","Api request completed --> " + action));
    }
}
