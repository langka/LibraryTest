package com.android.librarytest.entity;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lenovo on 2016/8/25.
 */
public class Book {
    private String name;
    private String author;
    private String url;//这个属性有待讨论

    public Book(String author, String name, String url) {
        this.author = author;
        this.name = name;
        this.url = url;
    }
    public Book(JSONObject object){
        try {
            this.author = object.getString("author");
            this.name = object.getString("name");
            this.url = object.getString("url");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
