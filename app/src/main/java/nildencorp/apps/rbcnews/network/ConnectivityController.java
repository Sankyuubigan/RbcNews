package nildencorp.apps.rbcnews.network;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityController {

    private ConnectivityManager manager;

    public ConnectivityController(ConnectivityManager manager) {
        this.manager = manager;
    }

    public boolean checkHasNetworkConnection() {
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }
}
