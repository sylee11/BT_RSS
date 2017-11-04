package com.gameloft.bt_rss;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Admin on 11/4/2017.
 */

public class custom_adapter extends ArrayAdapter<docbao> {

    public custom_adapter(Context context, int resource, List<docbao> items) {
        super(context, resource, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view =  inflater.inflate(R.layout.custom_docbao, null);
        }
        docbao p = getItem(position);
        if (p != null) {

            TextView tvtittle = (TextView) view.findViewById(R.id.tvtittle);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageview);
            TextView tvpubDate = (TextView) view.findViewById(R.id.tvpubday);

            tvpubDate.setText(p.pubDate);
            tvtittle.setText(p.tittle);
//            Picasso.with(getContext()).load(p.image).noFade().into(imageView);
            Picasso.with(getContext()).load("https://pbs.twimg.com/profile_images/766833986916093953/y3N_Wgqm_400x400.jpg").noFade().into(imageView);
        }
        return view;
    }

}
