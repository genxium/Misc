package com.example.bluetoothdiscovery;

import java.util.Set;

import android.os.Bundle;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

    // Return Intent extra
    public static String EXTRA_DEVICE_ADDRESS = "device_address";
    
	 // Member fields
    private BluetoothAdapter mBtAdapter;
    private ArrayAdapter<String> mPairedDevicesArrayAdapter;
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
    	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();
        if(mBtAdapter==null){
        		/*
        		Toast.makeText(this, "Bluetooth is not available", Toast.LENGTH_LONG).show();
        		finish();
        		return;
        		*/
        	    final int REQUEST_ENABLE_BT=1;
        	    Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        	    startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
        	    
        }
        
		// Initialize array adapter for already paired devices
        mPairedDevicesArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1);
		
		// Find and set up the ListView for paired devices
        ListView pairedListView = (ListView) findViewById(R.id.paired_devices);
        pairedListView.setAdapter(mPairedDevicesArrayAdapter);
        
		
        
		 // Initialize the button to perform device discovery
        Button scanButton = (Button) findViewById(R.id.button_scan);
        scanButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {

            	// Get a set of currently paired devices
                Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

                // If there are paired devices, add each one to the ArrayAdapter
                if (pairedDevices.size() > 0) {
                    findViewById(R.id.title_paired_devices).setVisibility(View.VISIBLE);
                    for (BluetoothDevice device : pairedDevices) {
                        mPairedDevicesArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                    }
                } 
                else {
                    String noDevices = getResources().getText(R.string.none_paired).toString();
                    mPairedDevicesArrayAdapter.add(noDevices);
                }
                
            }
        });

	}   
}
