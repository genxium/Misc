package com.example.contentprovider_practice;

import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.app.Activity;
import android.database.Cursor;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView contactView=(TextView) findViewById(R.id.contactview);
		Cursor cursor=getContacts();
		while (cursor.moveToNext()) {
		      String displayName = cursor.getString(cursor
		          .getColumnIndex(ContactsContract.Data.DISPLAY_NAME));
		      contactView.append("Name: ");
		      contactView.append(displayName);
		      contactView.append("\n");
		}
	}
	
	private Cursor getContacts(){
		// Run query
		Uri uri=ContactsContract.Contacts.CONTENT_URI;
		// columns' IDs to be selected by SQLite query
		String[] projection=new String[]{ContactsContract.Contacts._ID, 
				ContactsContract.Contacts.DISPLAY_NAME};
		// SQL where clause
		String selection=ContactsContract.Contacts.IN_VISIBLE_GROUP+" = '"+("1")+"'";
		String[] selectionArgs=null;
		// SQL ORDER BY clause
		String sortOrder=ContactsContract.Contacts.DISPLAY_NAME+" COLLATE LOCALIZED ASC";
		return managedQuery(uri,projection, selection, selectionArgs,sortOrder);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
