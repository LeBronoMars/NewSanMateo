package sanmateo.com.profileapp.util.rx;

import io.reactivex.CompletableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;

/**
 * Created by rsbulanon on 31/10/2017.
 */

public interface RxSchedulerUtils {

    CompletableTransformer completableAsychSchedulerTransformer();

    <T> MaybeTransformer<T, T> mayBeAsyncSchedulerTransformer();

    <T> ObservableTransformer<T, T> observableAsyncSchedulerTransformer();

    <T> SingleTransformer<T, T> singleAsyncSchedulerTransformer();
}
