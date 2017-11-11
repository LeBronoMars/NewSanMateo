package sanmateo.com.profileapp.util;


import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

public class TestableRxSchedulerUtil implements RxSchedulerUtils {

    @Override
    public CompletableTransformer completableAsychSchedulerTransformer() {
        return upstream -> upstream;
    }

    @Override
    public <T> MaybeTransformer<T, T> mayBeAsyncSchedulerTransformer() {
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
