package com.example.jujiiz.foodrandom;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends BaseAdapter {
    Context mContext;
    String[] strName;
    String[] strType;
    Bitmap[] bmBitmap;

    public CustomAdapter(Context context, Bitmap[] bmBitmap, String[] strName, String[] strType) {
        this.mContext= context;
        this.bmBitmap = bmBitmap;
        this.strName = strName;
        this.strType = strType;
    }

    public int getCount() {
        return strName.length;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater mInflater =
                (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(view == null)
            view = mInflater.inflate(R.layout.listview_row, parent, false);

        ImageView imageView = view.findViewById(R.id.ivIMG);
        imageView.setImageBitmap(bmBitmap[position]);

        TextView textViewN = view.findViewById(R.id.tvName);
        textViewN.setText(strName[position]);

        TextView textViewT = view.findViewById(R.id.tvType);
        textViewT.setText(strType[position]);

        return view;
    }
}
