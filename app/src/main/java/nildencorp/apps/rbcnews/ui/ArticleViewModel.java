package nildencorp.apps.rbcnews.ui;


import java.util.List;

import androidx.lifecycle.ViewModel;
import io.reactivex.Flowable;
import nildencorp.apps.rbcnews.model.Article;
import nildencorp.apps.rbcnews.model.ArticleDao;

class ArticleViewModel extends ViewModel {

    private final ArticleDao mDataSource;

    private Article mArticle;

    public ArticleViewModel(ArticleDao dataSource) {
        mDataSource = dataSource;
    }

    public Flowable<List<Article>> getArticles() {
        return mDataSource.getArticles()
                .map(articles -> {
                    return articles;
                });

    }

}
