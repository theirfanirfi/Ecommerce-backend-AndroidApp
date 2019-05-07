package com.irfanullah.ecommerce.Libraries;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
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

    public static String getRealPathFromURIPath(Uri contentURI, Context activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    public static void iLogHere(int n){
        Log.i(TAG,Integer.toString(n));
    }

    public static void logHere(String message){
        Log.i(TAG,message);
    }
}
