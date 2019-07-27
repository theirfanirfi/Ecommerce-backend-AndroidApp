package com.irfanullah.ecommerce.Libraries;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


public class SC {
    private static final String TAG = "MY_ECOM_APP";
    public static void toastHere(Context context,String message){
        Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }

    public static void snackHere(View view, String message){
        //Toast.makeText(context,message,Toast.LENGTH_LONG).show();
       // Snackbar.make(view,message,Snackbar.LENGTH_LONG).show();
    }

    public static boolean checkVersion(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? true : false;
    }


    public static void iLogHere(int n){
        Log.i(TAG,Integer.toString(n));
    }

    public static void logHere(String message){
        Log.i(TAG,message);
    }

    public static String getRealPathFromURIPath(Uri contentURI, Context activity) {
        String path = null;
        if (Build.VERSION.SDK_INT < 11)
            path = Util.getRealPathFromURI_BelowAPI11(activity, contentURI);

            // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            path = Util.getRealPathFromURI_API11to18(activity, contentURI);

            // SDK > 19 (Android 4.4)
        else
            path = Util.getRealPathFromURI_API19(activity, contentURI);
        return path;
        //  File file = new File(path);
    }


}
