package nildencorp.apps.rbcnews.ui;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import nildencorp.apps.rbcnews.R;
import nildencorp.apps.rbcnews.adapter.ArticleAdapter;
import nildencorp.apps.rbcnews.di.Injection;
import nildencorp.apps.rbcnews.model.Article;

public class MainActivity extends AppCompatActivity {

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ViewModelFactory mViewModelFactory;
    private ArticleViewModel mViewModel;
    private ArticleAdapter mAdapter;
    private List<Article> articles = new ArrayList<>();
    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ArticleViewModel.class);

        initAdapter();

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mViewModel.updateArticles();
            }
        });
    }

    public void initAdapter() {
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_articles);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        mAdapter = new ArticleAdapter(articles);
        recyclerView.setAdapter(mAdapter);
        compositeDisposable.add(mViewModel.getUpdatingResultSource()
                .subscribe(
                        resultMessage -> {
                            Toast.makeText(this, resultMessage, Toast.LENGTH_SHORT).show();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                ));
        compositeDisposable.add(mViewModel.getArticles()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(articlesList -> {
                    articles.clear();
                    articles.addAll(articlesList);
                    mAdapter.notifyDataSetChanged();
                }));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
