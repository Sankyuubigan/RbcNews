package nildencorp.apps.rbcnews.ui;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import androidx.lifecycle.ViewModelProviders;
import nildencorp.apps.rbcnews.R;
import nildencorp.apps.rbcnews.di.Injection;

public class MainActivity extends AppCompatActivity {

    private ViewModelFactory mViewModelFactory;
    private ArticleViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mViewModelFactory = Injection.provideViewModelFactory(this);
        mViewModel = ViewModelProviders.of(this, mViewModelFactory).get(ArticleViewModel.class);


    }
}
