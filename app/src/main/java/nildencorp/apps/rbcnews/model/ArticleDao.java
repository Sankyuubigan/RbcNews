package nildencorp.apps.rbcnews.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles")
    Flowable<List<Article>> getArticles();

//    @Query("SELECT * FROM ARTICLES WHERE store = :storeIn ")
//    Maybe<Article> getArticleByStore(String storeIn);

    @Query("SELECT * FROM ARTICLES LIMIT 1")
    Single<Article> getOneArticle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertArticle(Article coupon);


    @Query("DELETE FROM ARTICLES")
    void deleteAllArticles();
}
