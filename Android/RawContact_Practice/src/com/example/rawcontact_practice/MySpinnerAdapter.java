package com.example.rawcontact_practice;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

public class MySpinnerAdapter extends BaseAdapter implements SpinnerAdapter {
	
	private final List<Spinner_Entry> content;
	private final Activity activity;
	
	MySpinnerAdapter(List<Spinner_Entry> content_t, Activity activity_t){
		super();// note that it's important to call the base class' constructor
		this.content=content_t;
		this.activity=activity_t;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return content.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return content.get(position).name;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final LayoutInflater inflater = activity.getLayoutInflater(); // default layout inflater
		final View ret_view = inflater.inflate(R.layout.spinner_layout, null); // initialize the layout from xml
		final TextView contactName = (TextView) ret_view.findViewById(R.id.contact_name);
		contactName.setText(content.get(position).name);
		return ret_view;
	}
}
