package com.example.newsapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ArticleAdapter extends ArrayAdapter<Article> {
    public ArticleAdapter(Context context, ArrayList<Article> arrayList) {
        super(context, 0, arrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }
        Article currentArticle = getItem(position);
        TextView textView = convertView.findViewById(R.id.header);
        textView.setText(currentArticle.getHeader());
        textView = convertView.findViewById(R.id.body);
        textView.setText(currentArticle.getBody());
        textView = convertView.findViewById(R.id.author);
        textView.setText(currentArticle.getAuthor());
        textView = convertView.findViewById(R.id.topic);
        textView.setText(currentArticle.getTopic());
        textView = convertView.findViewById(R.id.day);
        textView.setText(String.valueOf(currentArticle.getDay()));
        textView = convertView.findViewById(R.id.month);
        textView.setText(String.valueOf(currentArticle.getMonth()));
        textView = convertView.findViewById(R.id.year);
        textView.setText(String.valueOf(currentArticle.getYear()));
        ImageView imageView = convertView.findViewById(R.id.thumbnail);
        Bitmap bitmap = currentArticle.getThumbnail();
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        }
        return convertView;
    }
}
