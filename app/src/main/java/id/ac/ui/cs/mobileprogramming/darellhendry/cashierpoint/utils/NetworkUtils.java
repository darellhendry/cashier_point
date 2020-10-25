package id.ac.ui.cs.mobileprogramming.darellhendry.cashierpoint.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.fragment.app.FragmentActivity;

public class NetworkUtils {

    public static boolean isNetworkConnected(FragmentActivity fragmentActivity) {
        ConnectivityManager connMgr = (ConnectivityManager) fragmentActivity
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            // display error
            return false;
        }
    }
}
