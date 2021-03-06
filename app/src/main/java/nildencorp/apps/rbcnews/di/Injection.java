/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nildencorp.apps.rbcnews.di;

import android.content.Context;
import android.net.ConnectivityManager;

import nildencorp.apps.rbcnews.model.ArticleDataSource;
import nildencorp.apps.rbcnews.model.ArticlesDatabase;
import nildencorp.apps.rbcnews.network.ConnectivityController;
import nildencorp.apps.rbcnews.ui.ViewModelFactory;

public class Injection {

    private static ViewModelFactory viewModelFactory;
    private static ArticleDataSource articleDataSource;
    private static ConnectivityManager connectivityManager;

    private static ArticleDataSource provideUserDataSource(Context context) {
        ArticlesDatabase database = ArticlesDatabase.getInstance(context);
        ConnectivityController connectivityController = new ConnectivityController(provideConnectivityManager(context));
        if (articleDataSource == null)
            articleDataSource = new ArticleDataSource(database.articleDao(), connectivityController);
        return articleDataSource;
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ArticleDataSource dataSource = provideUserDataSource(context);
        if (viewModelFactory == null)
            viewModelFactory = new ViewModelFactory(dataSource);
        return viewModelFactory;
    }

    private static ConnectivityManager provideConnectivityManager(Context context) {
        if (connectivityManager == null)
            connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return connectivityManager;
    }

}
