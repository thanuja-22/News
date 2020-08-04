package com.example.android.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class wordAdapter extends ArrayAdapter<word> {

    public wordAdapter(@NonNull Context context, int resource, @NonNull List<word> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listView=convertView;
        if (listView==null){
            listView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }
        word wo=getItem(position);
        TextView w1=(TextView)listView.findViewById(R.id.text1);
        w1.setText(wo.getT1());
        TextView w2=(TextView)listView.findViewById(R.id.text2);
        w2.setText(wo.getT2());
        ImageView w3=(ImageView)listView.findViewById(R.id.img);
        String hu=wo.getMimageid();
        Picasso.get().load(hu).into(w3);
        return listView;
    }
}
