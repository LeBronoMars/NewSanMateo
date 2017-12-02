package sanmateo.com.profileapp.news.view;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hannesdorfmann.mosby3.mvp.MvpFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.Unbinder;
import dagger.android.support.AndroidSupportInjection;
import sanmateo.com.profileapp.news.presenter.NewsPresenter;
import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public class NewsFragment extends MvpFragment<NewsView, NewsPresenter> implements NewsView {

    @Inject
    NewsPresenter newsPresenter;

    private Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Activity activity) {
        AndroidSupportInjection.inject(this);
        super.onAttach(activity);
    }

    @NonNull
    @Override
    public NewsPresenter createPresenter() {
        return newsPresenter;
    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError() {

    }

    @Override
    public void showNews(List<News> news) {

    }

    @Override
    public void showProgress() {

    }
}
