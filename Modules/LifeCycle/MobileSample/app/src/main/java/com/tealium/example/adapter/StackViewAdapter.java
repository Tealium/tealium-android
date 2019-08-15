package com.tealium.example.adapter;

import com.tealium.example.R;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class StackViewAdapter extends BaseAdapter {

	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		if(convertView == null) {
			convertView = LayoutInflater.from(parent.getContext())
					.inflate(R.layout.example_stackview_item, null);
		}
		
		switch(position) {
		case 0:
			convertView.setBackgroundColor(Color.RED);
			break;
		case 1:
			convertView.setBackgroundColor(Color.GREEN);
			break;
		case 2:
			convertView.setBackgroundColor(Color.BLUE);
			break;
		default:
			convertView.setBackgroundColor(Color.BLACK);
			break;
		}
		
		return convertView;
	}
}
