package com.example.android.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String USGS_REQUEST_URL="https://newsapi.org/v2/top-headlines?country=in&apiKey=85df550a11b245f5b774f199afc2cc72";
    private wordAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView li=(ListView)findViewById(R.id.list);
        mAdapter=new wordAdapter(this,R.layout.list_item,new ArrayList<word>());
        li.setAdapter(mAdapter);
        NewsAsyncTask n1=new NewsAsyncTask();
        n1.execute(USGS_REQUEST_URL);
        li.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                word wn=mAdapter.getItem(position);
                Log.e("MainActivity",wn.getT4());
                Uri ur=Uri.parse(wn.getT4());
                Intent in=new Intent(Intent.ACTION_VIEW,ur);
                startActivity(in);
            }
        });
    }
    class NewsAsyncTask extends AsyncTask<String,Void,ArrayList<word>>{

        @Override
        protected ArrayList<word> doInBackground(String... strings) {
                 ArrayList<word> wo=QueryUtils.fetchNews(USGS_REQUEST_URL);
                 return wo;
        }

        @Override
        protected void onPostExecute(ArrayList<word> data) {
                 if(data!=null && !data.isEmpty()){
                     mAdapter.addAll(data);
                 }
        }
    }
}
