package nildencorp.apps.rbcnews.model;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import io.reactivex.Flowable;

@Dao
public abstract class ArticleDao {

    @Query("SELECT * FROM articles")
    abstract Flowable<List<Article>> getArticles();

    @Query("SELECT * FROM articles LIMIT 1")
    abstract Article getOneArticle();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract Long insertArticle(Article article);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract List<Long> insertArticles(List<Article> articles);

    @Query("DELETE FROM articles")
    abstract void deleteAllArticles();

    @Transaction
    public List<Long> rewriteArticles(List<Article> articles) {
        deleteAllArticles();
        return insertArticles(articles);
    }
}
