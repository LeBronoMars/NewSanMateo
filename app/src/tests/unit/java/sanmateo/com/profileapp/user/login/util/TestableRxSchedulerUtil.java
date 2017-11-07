package sanmateo.com.profileapp.user.login.util;


import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

public class TestableRxSchedulerUtil implements RxSchedulerUtils {

    @Override
    public CompletableTransformer completableAsychSchedulerTransformer() {
        return upstream -> upstream;
    }

    @Override
    public <T> ObservableTransformer<T, T> observableAsyncSchedulerTransformer() {
        return upstream -> upstream;
    }

    @Override
    public <T> SingleTransformer<T, T> singleAsyncSchedulerTransformer() {
        return upstream -> upstream;
    }
}
