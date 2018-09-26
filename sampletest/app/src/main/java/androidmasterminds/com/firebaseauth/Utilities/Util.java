package androidmasterminds.com.firebaseauth.Utilities;

import android.content.Context;
import android.net.ConnectivityManager;

public class Util {
    public static boolean isNetworkConnected(Context mContxt) {
        ConnectivityManager cm = (ConnectivityManager)mContxt. getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }
}
