package at.tuwien.telemedizin.dermadoc.app.helper;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import at.tuwien.telemedizin.dermadoc.app.server_interface.ServerInterfaceFactory;

/**
 * Created by FAUser on 08.01.2016.
 * Detects the network-state of the device
 */
public class ConnectionDetector {

    public static boolean isConnectingToInternet(Context context){

        // during test-mode, return true for testing purpose
        if (ServerInterfaceFactory.testMode){
            return true;
        }

        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null)
        {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }

        }
        return false;
    }
}
