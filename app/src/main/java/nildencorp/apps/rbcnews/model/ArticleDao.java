package nildencorp.apps.rbcnews.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Flowable;

@Dao
public interface ArticleDao {

    @Query("SELECT * FROM articles")
    Flowable<List<Article>> getArticles();

    @Query("SELECT * FROM articles LIMIT 1")
    Article getOneArticle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Long insertArticle(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertArticles(List<Article> articles);

    @Query("DELETE FROM articles")
    Completable deleteAllArticles();
}
