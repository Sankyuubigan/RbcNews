package nildencorp.apps.rbcnews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import nildencorp.apps.rbcnews.network.ServerApi;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private AtomicLong lastTick = new AtomicLong(0L);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://rawgit.com/startandroid/data/master/messages/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        ServerApi serverApi = retrofit.create(ServerApi.class);

        disposables.add(Observable.interval(60, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .map(tick -> lastTick.getAndIncrement())
                .subscribe(aLong -> {
                    disposables.add(serverApi.getArticles().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {

                            }));
                }));
    }
}
