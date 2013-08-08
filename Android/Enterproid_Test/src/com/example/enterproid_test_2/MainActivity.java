package com.example.enterproid_test_2;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Initialize a button 
		Button button_toclick = (Button) findViewById(R.id.button_toclick);
		button_toclick.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
					HttpAsyncTask mAsyncTask=new HttpAsyncTask(MainActivity.this);
			        mAsyncTask.execute();
		    }});  
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
