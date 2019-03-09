
package nildencorp.apps.rbcnews.ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import nildencorp.apps.rbcnews.model.ArticleDataSource;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ArticleDataSource mDataSource;

    public ViewModelFactory(ArticleDataSource mDataSource) {
        this.mDataSource = mDataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) new ArticleViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
