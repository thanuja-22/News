package com.example.android.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.squareup.picasso.Picasso;

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

public class QueryUtils {

    public static ArrayList<word> fetchNews(String s){
        String jsonresponse="";
        URL url=createurl(s);
        try{
            jsonresponse=makeHttpRequest(url);
        }
        catch (IOException e){
            e.printStackTrace();
        }
        //Log.e("QueryUtils","fetchnews");
        ArrayList<word> w =extractFeaturesFromJson(jsonresponse);
        return w;
    }
    public static URL createurl(String s){
        URL u=null;
        try{
            u=new URL(s);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return u;
    }
    public static String makeHttpRequest(URL url) throws IOException{
        String jsonrespone="";
        HttpURLConnection urlConnection;
        try {
            urlConnection=(HttpURLConnection)url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            if(urlConnection.getResponseCode()==200){
                InputStream inputStream=urlConnection.getInputStream();
                jsonrespone=readFromStream(inputStream);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return jsonrespone;
    }
    public static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder es=new StringBuilder();
        //Log.e("QueryUtils","readFromStream");
        if(inputStream!=null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bf = new BufferedReader(inputStreamReader);
            String s = bf.readLine();
            while (s != null) {
                es.append(s);
                s = bf.readLine();
            }
        }
        return es.toString();
    }
    public static ArrayList<word> extractFeaturesFromJson(String jsonresponse){
        ArrayList<word> wu=new ArrayList<word>();
        try {
            JSONObject jo=new JSONObject(jsonresponse);
            int total=jo.getInt("totalResults");
            JSONArray article=jo.getJSONArray("articles");
            for(int k=0;k<article.length();k++){
                JSONObject j1=article.getJSONObject(k);
                //Log.e("QueryUtils","j1"+j1);
                String ti=j1.getString("title");
                //Log.e("QueryUtils","title"+ti);
                String t2=j1.getString("publishedAt");
                //Log.e("QueryUtils","time"+t2);
                String t3=j1.getString("urlToImage");
                String t4=j1.getString("url");
                Log.e("QueryUtils","msg"+t4);
                word w=new word(ti,t2,t3,t4);
                wu.add(w);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return wu;
    }
}

