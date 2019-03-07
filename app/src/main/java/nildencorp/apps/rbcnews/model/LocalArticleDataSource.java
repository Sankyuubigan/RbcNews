package nildencorp.apps.rbcnews.model;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import nildencorp.apps.rbcnews.network.ApiCore;

public class LocalArticleDataSource implements ArticleDao {

    final CompositeDisposable disposables = new CompositeDisposable();
    private final ArticleDao mDataSource;

    public LocalArticleDataSource(ArticleDao userDao) {
        mDataSource = userDao;
        updateArticlesFromServer();
        initIntervalUpdates();
    }

    @Override
    public Long insertArticle(Article coupon) {
        return mDataSource.insertArticle(coupon);
    }

    @Override
    public Flowable<List<Article>> getArticles() {
        return mDataSource.getArticles();
    }

    @Override
    public Article getOneArticle() {
        return mDataSource.getOneArticle();
    }

    class CallableLongAction implements Callable<List<Long>> {

        private List<Article> articles;

        public CallableLongAction(List<Article> articles) {
            this.articles = articles;
        }

        @Override
        public List<Long> call() throws Exception {
            return insertArticles(articles);
        }
    }

    @Override
    public List<Long> insertArticles(List<Article> articles) {
        return mDataSource.insertArticles(articles);
    }

    @Override
    public Completable deleteAllArticles() {
        return mDataSource.deleteAllArticles();
    }

    public void updateArticlesFromServer() {
        disposables.add(ApiCore.getServerApi().getArticles().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(response -> {
                    if (response.body() != null) {
                        Observable.fromCallable(new CallableLongAction(response.body().getArticles()))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(completable -> {
                                    Log.d("myQ", "insert articles has completed successfully");
                                });
                    }
                }, throwable -> {
                    Log.d("myQ", "error insert");
                }));
    }

    public void initIntervalUpdates() {
        AtomicLong lastTick = new AtomicLong(0L);
        disposables.add(Observable.interval(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(tick -> lastTick.getAndIncrement())
                .subscribe(aLong -> {
                    updateArticlesFromServer();
                }));

    }
}
