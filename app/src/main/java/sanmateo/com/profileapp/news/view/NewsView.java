package sanmateo.com.profileapp.news.view;

import com.hannesdorfmann.mosby3.mvp.MvpView;

import java.util.List;

import sanmateo.com.profileapp.news.usecase.News;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public interface NewsView extends MvpView {

    void hideProgress();

    void showError();

    void showNews(List<News> news);

    void showProgress();
}
