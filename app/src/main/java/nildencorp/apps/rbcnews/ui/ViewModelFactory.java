
package nildencorp.apps.rbcnews.ui;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModel;
import nildencorp.apps.rbcnews.model.ArticleDao;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private final ArticleDao mDataSource;

    public ViewModelFactory(ArticleDao dataSource) {
        mDataSource = dataSource;
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) new ArticleViewModel(mDataSource);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
