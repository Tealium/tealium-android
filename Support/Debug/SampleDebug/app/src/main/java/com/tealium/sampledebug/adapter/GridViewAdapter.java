package com.tealium.sampledebug.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.tealium.sampledebug.R;

/**
 * Created by karentamayo on 1/12/17.
 */

public class GridViewAdapter extends BaseAdapter{
    private final String[] data;

    public GridViewAdapter(Context context) {
        data = context.getResources().getStringArray(R.array.months);
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int position) {
        if (position < 0) {
            return null;
        }

        if (position >= data.length) {
            return null;
        }

        return data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView textView;

        if ((textView = ((TextView) convertView)) == null) {
            textView = (TextView) LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.gridview_cell, null);
        }

        textView.setText(data[position]);

        return textView;
    }
}
