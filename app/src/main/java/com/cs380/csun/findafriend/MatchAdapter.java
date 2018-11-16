package com.cs380.csun.findafriend;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import static android.R.attr.resource;

//class that would hold matched bluetooth profile images and matched interests

public class MatchAdapter extends ArrayAdapter<String> {


    //declarations
    int[] imgs={};
    String[] names={};
    String[] matched={};
    Context c;
    LayoutInflater inflater;


    public MatchAdapter(Context context, String[] names, String[] matched, int[] imgs){
        super(context,R.layout.model,names);

        this.c=context;
        this.names=names;
        this.matched=matched;
        this.imgs=imgs;

    }

    public class ViewHolder
    {
        TextView nametv;
        TextView matchedtv;
        ImageView img;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){

        if(convertView ==null)
        {
            inflater=(LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.model,null);
        }

        final ViewHolder holder=new ViewHolder();

        holder.nametv=(TextView) convertView.findViewById(R.id.nametv);
        holder.matchedtv=(TextView) convertView.findViewById(R.id.matchedtv);
        holder.img=(ImageView) convertView.findViewById(R.id.circleImageView);

        holder.img.setImageResource(imgs[position]);
        holder.nametv.setText(names[position]);
        holder.matchedtv.setText(matched[position]);

        return convertView;
    }

}
