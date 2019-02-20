package nildencorp.apps.rbcnews.model;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import nildencorp.apps.rbcnews.network.ServerApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class LocalArticleDataSource implements ArticleDao {

    final CompositeDisposable disposables = new CompositeDisposable();
    private final ArticleDao mUserDao;

    public LocalArticleDataSource(ArticleDao userDao) {
        mUserDao = userDao;
        initIntervalUpdates();
    }

    @Override
    public Flowable<List<Article>> getArticles() {
        return mUserDao.getArticles();
    }

    @Override
    public Single<Article> getOneArticle() {
        return mUserDao.getOneArticle();
    }

    @Override
    public void insertArticle(Article coupon) {
        mUserDao.insertArticle(coupon);
    }

    @Override
    public Completable insertArticles(List<Article> articles) {
        return Completable.fromCallable(() -> {
            return mUserDao.insertArticles(articles);
        });
    }

    @Override
    public void deleteAllArticles() {
        mUserDao.deleteAllArticles();
    }

    public void initIntervalUpdates() {
        AtomicLong lastTick = new AtomicLong(0L);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ServerApi serverApi = retrofit.create(ServerApi.class);
        disposables.add(Observable.interval(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(tick -> lastTick.getAndIncrement())
                .subscribe(aLong -> {
                    serverApi.getArticles().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                if (response.body() != null)
                                    insertArticles(response.body().getArticles());
                            });
                }));

    }
}
