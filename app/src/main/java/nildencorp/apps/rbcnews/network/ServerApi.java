package nildencorp.apps.rbcnews.network;

import io.reactivex.Flowable;
import nildencorp.apps.rbcnews.model.RbcNewsResponseObject;
import retrofit2.http.GET;

public interface ServerApi {
    public static final String BASE_URL = "https://newsapi.org/v2/";
    public static final String API_KEY = "270ede8cfaa84612a91abd399ad194dc";

    @GET("everything?sources=rbc&apiKey=" + API_KEY)
    Flowable<RbcNewsResponseObject> getArticles();
}
