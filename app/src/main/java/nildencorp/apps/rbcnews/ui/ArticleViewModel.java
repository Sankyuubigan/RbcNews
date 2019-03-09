package nildencorp.apps.rbcnews.ui;


import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import io.reactivex.subjects.PublishSubject;
import nildencorp.apps.rbcnews.model.Article;
import nildencorp.apps.rbcnews.model.ArticleDataSource;

class ArticleViewModel extends ViewModel {

    private final ArticleDataSource mDataSource;

    public ArticleViewModel(ArticleDataSource dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<List<Article>> getArticles() {
        return mDataSource.getArticlesSource();
    }

    public void updateArticles() {
        mDataSource.update();
    }

    public PublishSubject<String> getUpdatingResultSource(){
        return mDataSource.getUploadingResultPublisher();
    }
}