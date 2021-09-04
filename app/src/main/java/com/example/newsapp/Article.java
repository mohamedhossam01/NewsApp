package com.example.newsapp;

import android.graphics.Bitmap;

public class Article {
    private final String header;
    private final String body;
    private final String author;
    private final String Topic;
    private final byte day;
    private final byte month;
    private final short year;
    private final Bitmap thumbnail;
    private final String articleUrl;

    public Article(String header, String body, String author, String topic, byte day, byte month, short year, Bitmap thumbnail, String articleUrl) {
        this.header = header;
        this.body = body;
        this.author = author;
        Topic = topic;
        this.day = day;
        this.month = month;
        this.year = year;
        this.thumbnail = thumbnail;
        this.articleUrl = articleUrl;
    }

    public String getHeader() {
        return header;
    }

    public String getBody() {
        return body;
    }

    public String getAuthor() {
        return author;
    }

    public String getTopic() {
        return Topic;
    }

    public byte getDay() {
        return day;
    }

    public byte getMonth() {
        return month;
    }

    public short getYear() {
        return year;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public String getArticleUrl() {
        return articleUrl;
    }
}
