package com.android.librarytest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lenovo on 2016/8/19.
 */

public class MyInternetPicTask extends AsyncTask<Object,Void,Bitmap>{
    Bitmap bitmap;
    ImageView view;
    String url;
    @Override
    protected Bitmap doInBackground(Object... params) {
        view = (ImageView) params[0];
        url = (String) params[1];
        try {
            URL u = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            bitmap = BitmapFactory.decodeStream(connection.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        view.setImageBitmap(bitmap);
    }
}
