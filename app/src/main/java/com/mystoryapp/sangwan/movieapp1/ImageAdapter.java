package com.mystoryapp.sangwan.movieapp1;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    public ArrayList<String> movieArray=new ArrayList<String>();
    public int width=0;

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(width,700));
        } else {
            imageView = (ImageView) convertView;
        }
        Picasso.with(context).load("http://image.tmdb.org/t/p/w185/"+movieArray.get(position))
                .resize(width,700)
                .centerCrop()
                .into(imageView);
        //imageView.setImageResource(mThumbIds[position]);
        return imageView;
    }

    public ImageAdapter(Context context, ArrayList<String> movieArray) {
        this.context = context;
        this.movieArray = movieArray;
        DisplayMetrics m = context.getResources().getDisplayMetrics();
        int x = m.widthPixels;
        width=Math.round(x/2);
        this.width = width;
    }
}
