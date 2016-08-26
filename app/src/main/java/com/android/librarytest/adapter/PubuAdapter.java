package com.android.librarytest.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
public class PubuAdapter extends RecyclerView.Adapter<PubuAdapter.MyViewHolder>{
    List<Book> books;

    public PubuAdapter(List<Book> books) {
        this.books = books;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pubu_layout,null);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Book current = books.get(position);
        holder.image.setImageURI(current.getUrl());
        holder.title.setText(current.getName());
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView image;
        TextView title;
        public MyViewHolder(View itemView) {
            super(itemView);
            image = (SimpleDraweeView) itemView.findViewById(R.id.pobu_image);
            title = (TextView) itemView.findViewById(R.id.pubu_title);
        }
    }
}
