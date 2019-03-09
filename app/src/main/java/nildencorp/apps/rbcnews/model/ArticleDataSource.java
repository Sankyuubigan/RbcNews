package nildencorp.apps.rbcnews.model;

import android.util.Log;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import nildencorp.apps.rbcnews.network.ApiCore;
import nildencorp.apps.rbcnews.network.ConnectivityController;

public class ArticleDataSource {

    private String TAG = "ArticlesDataSource";
    private final ConnectivityController connectivityController;

    private PublishSubject<String> uploadingResultPublisher = PublishSubject.create();

    final CompositeDisposable disposables = new CompositeDisposable();
    private final ArticleDao articlesDao;

    public ArticleDataSource(ArticleDao userDao, ConnectivityController connectivityController) {
        articlesDao = userDao;
        this.connectivityController = connectivityController;
        updateArticlesFromServer();
        initIntervalUpdates();
    }

    public PublishSubject<String> getUploadingResultPublisher() {
        return uploadingResultPublisher;
    }

    public Flowable<List<Article>> getArticlesSource() {
        return articlesDao.getArticles();
    }

    private void updateArticlesFromServer() {
        disposables.add(getRemoteArticlesSource()
                .map(response -> articlesDao.rewriteArticles(response.getArticles()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(insertedList -> {
                    Log.d(TAG, insertedList.size() + " articles inserted in DB");
                    uploadingResultPublisher.onNext("Данные успешно обновлены");
                }, throwable -> {
                    Log.e(TAG, "error insert", throwable);
                    uploadingResultPublisher.onNext("Не удалось обновить данные");
                }));
    }

    private Flowable<RbcNewsResponseObject> getRemoteArticlesSource(){
        return ApiCore.getServerApi()
                .getArticles()
                .subscribeOn(Schedulers.io());
    }

    public void update() {
        if (connectivityController.checkHasNetworkConnection()) {
            updateArticlesFromServer();
        } else {
            uploadingResultPublisher.onNext("Нет интернета");
        }
    }

    public void initIntervalUpdates() {
//        AtomicLong lastTick = new AtomicLong(0L);
//        disposables.add(Observable.interval(60, TimeUnit.SECONDS)
//                .subscribeOn(Schedulers.io())
//                .map(tick -> lastTick.getAndIncrement())
//                .subscribe(aLong -> {
//                    updateArticlesFromServer();
//                }));

    }
}
