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

import nildencorp.apps.rbcnews.model.ArticleDao;
import nildencorp.apps.rbcnews.model.ArticlesDatabase;
import nildencorp.apps.rbcnews.model.LocalArticleDataSource;
import nildencorp.apps.rbcnews.ui.ViewModelFactory;

public class Injection {

    public static ArticleDao provideUserDataSource(Context context) {
        ArticlesDatabase database = ArticlesDatabase.getInstance(context);
        return new LocalArticleDataSource(database.articleDao());
    }

    public static ViewModelFactory provideViewModelFactory(Context context) {
        ArticleDao dataSource = provideUserDataSource(context);
        return new ViewModelFactory(dataSource);
    }
}
