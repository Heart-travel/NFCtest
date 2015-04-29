package com.example.nfctest;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.NfcA;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
public class TecActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tec);
	}

	public void onResume() {
	    super.onResume();
	    Log.d("NFC", "On resume!");
	    
	    if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
	    	
	    	Log.d("NFC", "ACTION_TECH_DISCOVERED!");
	    	
	    	Intent intent = getIntent();
	    	Tag tagFromIntent = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
	    	 
	    	 NfcA nfcatag = NfcA.get(tagFromIntent);
	    	 
	         try {
	        	 Log.d("NFC", "Start to connect!");
	        	 nfcatag.connect();
	             byte[] payload = nfcatag.getAtqa();
	             String output = new String(payload);
             	 /*save(output);
	             Toast.makeText(TecActivity.this, output, Toast.LENGTH_SHORT).show();*/
	             TextView textView=(TextView)findViewById(R.id.textView);
	             textView.setText(output);
	             
	             byte[] payloadSak = nfcatag.getAtqa();
	             String outputSak = new String(payloadSak);
             	 //save(outputSak);
	     		 textView=(TextView)findViewById(R.id.textViewSak);
	    	     textView.setText(outputSak);
	         } catch (IOException e) {
	             Log.e("NFC", "IOException while reading NfcA message...", e);
	         } finally {
	             if (nfcatag != null) {
	                try {
	                	nfcatag.close();
	   	        	 	Log.d("NFC", "End to connect!");
	                }
	                catch (IOException e) {
	                    Log.e("NFC", "Error closing tag...", e);
	                }
	             }
	         }
	    }
	    //process the msgs array
	}
	
	public void save(String inputText) {
		FileOutputStream out = null;
		BufferedWriter writer = null;
		try {
			out = openFileOutput("data", Context.MODE_APPEND);
			writer = new BufferedWriter(new OutputStreamWriter(out));
			writer.write(inputText);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		try {
			if (writer != null) {
			writer.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
			}
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

}
