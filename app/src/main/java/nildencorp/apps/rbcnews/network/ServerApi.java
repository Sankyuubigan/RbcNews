package nildencorp.apps.rbcnews.network;

import io.reactivex.Flowable;
import nildencorp.apps.rbcnews.model.Everything;
import retrofit2.Response;
import retrofit2.http.GET;

public interface ServerApi {
    public static final String BASE_URL = "https://newsapi.org/v2/";

    @GET("everything?sources=rbc&apiKey=API_KEY")
    Flowable<Response<Everything>> getArticles();
}
