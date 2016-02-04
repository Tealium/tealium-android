package com.tealium.example.adapter;

import java.util.Locale;

import com.tealium.example.R;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ExpandableListAdapter extends BaseExpandableListAdapter implements 
	View.OnClickListener, 
	CompoundButton.OnCheckedChangeListener {

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return null;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return 0;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
		RelativeLayout view;
		
		if((view = (RelativeLayout) convertView) == null) {
			view = (RelativeLayout) LayoutInflater.from(parent.getContext())
					.inflate(R.layout.explist_child, parent, false);
			view.findViewById(R.id.explist_child_button)
				.setOnClickListener(this);
			((CompoundButton)view.findViewById(R.id.explist_child_checkbox))
				.setOnCheckedChangeListener(this);
		}
		
		((TextView)view.findViewById(R.id.explist_child_label))
			.setText(String.format(Locale.US, "%d:%d", groupPosition, childPosition));
		
		return view;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return 3;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return null;
	}

	@Override
	public int getGroupCount() {
		return 3;
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
		TextView layout;
		
		if((layout = (TextView) convertView) == null) {
			layout = (TextView) LayoutInflater.from(parent.getContext())
					.inflate(R.layout.explist_group, parent, false);
		}
		
		layout.setText("Group " + groupPosition);
		
		return layout;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		
	}
	

	@Override
	public void onClick(View v) {
		
	}
}
