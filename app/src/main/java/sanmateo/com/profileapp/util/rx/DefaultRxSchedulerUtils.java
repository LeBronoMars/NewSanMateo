package sanmateo.com.profileapp.util.rx;

import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public class DefaultRxSchedulerUtils implements RxSchedulerUtils {

    @Override
    public CompletableTransformer completableAsychSchedulerTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public <T> MaybeTransformer<T, T> mayBeAsyncSchedulerTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public <T> ObservableTransformer<T, T> observableAsyncSchedulerTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public <T> SingleTransformer<T, T> singleAsyncSchedulerTransformer() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                                   .observeOn(AndroidSchedulers.mainThread());
    }
}
