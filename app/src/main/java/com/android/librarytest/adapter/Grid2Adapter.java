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
public class Grid2Adapter extends BaseAdapter{
    Context mcontext;
    List<Book> bookList;
    private class ViewHolder{
        TextView textView;
        SimpleDraweeView image;
    }
    public Grid2Adapter(List<Book> bookList, Context mcontext) {
        this.bookList = bookList;
        this.mcontext = mcontext;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return bookList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        Book current = bookList.get(position);
        if(convertView == null){
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mcontext).inflate(R.layout.simple_mega_b,null);
            holder.textView = (TextView) convertView.findViewById(R.id.simple_mega_b_name);
            holder.image = (SimpleDraweeView) convertView.findViewById(R.id.simple_mega_b_image);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();
        holder.textView.setText(current.getName());
        holder.image.setImageURI(current.getUrl());
        return convertView;
    }
}
