package com.android.librarytest.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.librarytest.R;
import com.android.librarytest.entity.Book;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by lenovo on 2016/8/25.
 */
public class Grid1Adapter extends BaseAdapter{
    Context mcontext;
    List<Book> books;

    public Grid1Adapter(List<Book> books, Context mcontext) {
        this.books = books;
        this.mcontext = mcontext;
    }

    private class  ViewHolder{
        TextView title;
        TextView content;
        SimpleDraweeView image;
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public Object getItem(int position) {
        return books.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Book current = books.get(position);
        if(convertView==null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.single_mega_a,null);
            holder.title = (TextView) convertView.findViewById(R.id.sing_mega_a_title);
            holder.content = (TextView) convertView.findViewById(R.id.single_mega_a_anthor);
            holder.image = (SimpleDraweeView) convertView.findViewById(R.id.single_mega_iamge);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(current.getName());
        holder.content.setText(current.getAuthor());
        holder.image.setImageURI(current.getUrl());
        return convertView;
    }
}
