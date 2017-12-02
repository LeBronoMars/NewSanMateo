package sanmateo.com.profileapp.news.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpPresenter;

import sanmateo.com.profileapp.news.view.NewsView;

/**
 * Created by rsbulanon on 02/12/2017.
 */

public interface NewsPresenter extends MvpPresenter<NewsView> {

    void loadNews(int start, int limit);
}
