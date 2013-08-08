package com.example.rawcontact_practice;

import java.util.List;
import java.util.LinkedList;

import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

public class MainActivity extends Activity {

	// list of contact_names
	private final List<Spinner_Entry> contact_names=new LinkedList<Spinner_Entry>();
	// list of contact info for each contact person
	private final List<String> contact_info=new LinkedList<String>();
	// spinner adapter
	private MySpinnerAdapter spinner_adapter=new MySpinnerAdapter(contact_names,this);
	// spinner object
	private Spinner spinner_contact;
	// listview adapter
	private MyListviewAdapter listview_adapter=new MyListviewAdapter(contact_info,this);
	// listview object
	private ListView contact_info_view;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		contact_info_view=(ListView)findViewById(R.id.contact_info);
		spinner_contact=(Spinner)findViewById(R.id.spinner_contact);
		spinner_contact.setOnItemSelectedListener(new OnItemSelectedListener(){
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				// TODO Auto-generated method stub
				updateList(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				updateList(spinner_contact.getSelectedItemPosition());
			}
			
			@SuppressLint("ShowToast")
			private void updateList(int position) {
				if(position < spinner_adapter.getCount() && position >= 0) {
					contact_info_view.setAdapter(null);
					int contact_id=contact_names.get(position).id;
					queryContactInfo(contact_id, contact_info); // contact_info will be updated in-place
					contact_info_view.setAdapter(new MyListviewAdapter(contact_info,MainActivity.this));
				}
			}
		});
		
		queryRawContacts();
		try{
			spinner_contact.setAdapter(spinner_adapter);
		}catch(Exception ex){
			Log.e("app","error",ex);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@SuppressWarnings("deprecation")
	private void queryRawContacts(){
		final String[] projection = new String[] {
				RawContacts.CONTACT_ID,					// the contact id column
				RawContacts.DISPLAY_NAME_PRIMARY,		// display name of contact
				RawContacts.DELETED						// deleted tag: true for deleted item and false otherwise
		};
		
		Cursor result=managedQuery(RawContacts.CONTENT_URI, projection, null, null, null);
		int contact_id_col=result.getColumnIndex(RawContacts.CONTACT_ID);
		int display_name_col=result.getColumnIndex(RawContacts.DISPLAY_NAME_PRIMARY);
		int deleted_flag_col=result.getColumnIndex(RawContacts.DELETED);
		contact_names.clear();
		if(result.moveToFirst()){
			while(!result.isAfterLast()){
				boolean is_deleted=(result.getInt(deleted_flag_col)==1);
				if(!is_deleted){
					int contact_id=result.getInt(contact_id_col);
					String name=result.getString(display_name_col);
					contact_names.add(new Spinner_Entry(contact_id,name));
				}
				result.moveToNext();
			}
		}
		result.close();
	}
	
	void queryContactInfo(int contact_id, List<String> contact_info){
		String[] projection=new String[]{
				Phone.NUMBER,
				Phone.TYPE
		};
		String where=Data.CONTACT_ID+"=?";
		String[] where_args=new String[]{String.valueOf(contact_id)};
		@SuppressWarnings("deprecation")
		Cursor result=managedQuery(Phone.CONTENT_URI, projection, where, where_args, null);
		if(result.moveToFirst()){
			contact_info.clear();
			int number_col=result.getColumnIndex(Phone.NUMBER);
			while(!result.isAfterLast()){
				contact_info.add(result.getString(number_col));
				result.moveToNext();
			}
		}
		result.close();
	}
}
