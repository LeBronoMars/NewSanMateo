package sanmateo.com.profileapp.news.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import sanmateo.com.profileapp.news.usecase.News;
import sanmateo.com.profileapp.news.usecase.NewsLoader;
import sanmateo.com.profileapp.news.view.NewsView;
import sanmateo.com.profileapp.util.rx.RxSchedulerUtils;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class DefaultNewsPresenter extends MvpBasePresenter<NewsView> implements NewsPresenter {

    private NewsLoader newsLoader;

    private RxSchedulerUtils rxSchedulerUtils;

    Disposable disposable;

    NewsView view;

    @Inject
    public DefaultNewsPresenter(NewsLoader newsLoader, RxSchedulerUtils rxSchedulerUtils) {
        this.newsLoader = newsLoader;
        this.rxSchedulerUtils = rxSchedulerUtils;
    }

    @Override
    public void attachView(NewsView view) {
        super.attachView(view);
        this.view = view;
    }

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        dispose();
    }

    private void dispose() {
        if (null != disposable && !disposable.isDisposed()) disposable.dispose();
    }

    @Override
    public void loadNews(int start, int limit) {
        view.showProgress();
        newsLoader.loadNews(start, limit)
                  .compose(rxSchedulerUtils.singleAsyncSchedulerTransformer())
                  .subscribe(new SingleObserver<List<News>>() {
                      @Override
                      public void onSubscribe(Disposable d) {
                          disposable = d;
                      }

                      @Override
                      public void onSuccess(List<News> news) {
                          view.hideProgress();
                          view.showNews(news);
                      }

                      @Override
                      public void onError(Throwable e) {
                          view.hideProgress();
                          view.showError();
                      }
                  });
    }
}
