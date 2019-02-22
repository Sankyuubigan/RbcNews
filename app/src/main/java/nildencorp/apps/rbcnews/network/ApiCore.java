package nildencorp.apps.rbcnews.network;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiCore {
    public static ServerApi getServerApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ServerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(ServerApi.class);
    }
}
