package com.example.nfctest;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.nfc.tech.NfcA;
import android.util.Log;
import java.io.IOException;
import java.nio.charset.Charset;
import android.content.Intent;
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
	             Toast.makeText(TecActivity.this, output, Toast.LENGTH_SHORT).show();
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
