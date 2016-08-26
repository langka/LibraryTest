package com.android.librarytest.HttpHelper;

import com.android.librarytest.entity.Book;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lenovo on 2016/8/25.
 * [{},{},{}]
 */
public class HttpHelper {
    //此方法更具uri返回一个book集合，用于recommendedfragment的各个分区加载信息

    /**
     *
     * @param uri 指代分区应该访问的uri
     * @return
     */
    public static List<Book> getNeededBooks(String uri){
        List<Book> books = new ArrayList<Book>();
        String json = "";
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            String line = null;
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            while((line=reader.readLine())!=null){
                json+=line;
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            JSONArray jsonArray = new JSONArray(json);
            for(int i=0;i<jsonArray.length();i++){
                JSONObject object = jsonArray.getJSONObject(i);
                books.add(new Book(object));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return books;
    }
}
