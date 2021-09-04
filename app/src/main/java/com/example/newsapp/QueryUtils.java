package com.example.newsapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    public static List<Article> fetchArticleData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = makeHttpRequest(url);
        List<Article> articles = extractFeatureFromJson(jsonResponse);
        return articles;
    }

    private static List<Article> extractFeatureFromJson(String ArticleJSON) {
        if (TextUtils.isEmpty(ArticleJSON)) return null;
        List<Article> articles = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(ArticleJSON);
            JSONArray jsonArray = baseJsonResponse.getJSONObject("response").getJSONArray("results");
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject fields = jsonArray.getJSONObject(i).getJSONObject("fields");
                String header = fields.getString("headline");
                String body = fields.getString("bodyText");
                String author = fields.getString("byline");
                String topic = jsonArray.getJSONObject(i).getString("sectionName");
                String date = jsonArray.getJSONObject(i).getString("webPublicationDate");
                byte day = Byte.valueOf(date.substring(8, 10));
                byte month = Byte.valueOf(date.substring(5, 7));
                short year = Short.valueOf(date.substring(0, 4));
                Bitmap thumbnail = downloadBitmap(fields.getString("thumbnail"));
                String articleUrl = jsonArray.getJSONObject(i).getString("webUrl");
                articles.add(new Article(header, body, author, topic, day, month, year, thumbnail, articleUrl));
            }
        } catch (JSONException e) {}
        return articles;
    }

    private static URL createUrl(String rawUrl) {
        URL url = null;
        try {
            url = new URL(rawUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    private static String makeHttpRequest(URL url) {
        String jsonResponse = "";
        if (url == null) return jsonResponse;
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(15000);
            urlConnection.setReadTimeout(10000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                jsonResponse = readFromStream(urlConnection.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream == null) return "";
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = bufferedReader.readLine();
        while (line != null) {
            output.append(line);
            line = bufferedReader.readLine();
        }
        return output.toString();
    }

    private static Bitmap downloadBitmap(String originalUrl) {
        Bitmap bitmap = null;
        if (!originalUrl.isEmpty()) {
            String newUrl = originalUrl.replace
                    (originalUrl.substring(originalUrl.lastIndexOf("/")), "/1000.jpg");
            try {
                InputStream inputStream = new URL(newUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);
            } catch (Exception e1) {
                try {
                    InputStream inputStream = new URL(originalUrl).openStream();
                    bitmap = BitmapFactory.decodeStream(inputStream);
                } catch (Exception e2) {}
            }
        }
        return bitmap;
    }
}