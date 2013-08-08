package com.example.rawcontact_practice;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyListviewAdapter extends BaseAdapter {
	private List<String> info;
	private Activity activity;
	
	public MyListviewAdapter(List<String> info_t, Activity activity_t){
		super();
		info=info_t;
		activity=activity_t;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return info.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return info.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final LayoutInflater inflater = activity.getLayoutInflater();	// default layout inflater
		final View ret_view = inflater.inflate(R.layout.listview_layout, null);	// initialize the layout from xml
		String info_content=info.get(position);
		TextView info_text=(TextView)ret_view.findViewById(R.id.info);
		info_text.setText(info_content);
		return ret_view;
	}
}
