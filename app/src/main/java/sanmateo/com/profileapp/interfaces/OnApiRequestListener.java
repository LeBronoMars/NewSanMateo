package sanmateo.com.profileapp.interfaces;

import sanmateo.com.profileapp.enums.ApiAction;

/**
 * Created by rsbulanon on 10/2/16.
 */

public interface OnApiRequestListener {
    void onApiRequestBegin(final ApiAction action);
    void onApiRequestSuccess(final ApiAction action, final Object result);
    void onApiRequestFailed(final ApiAction action, final Throwable t);
}
