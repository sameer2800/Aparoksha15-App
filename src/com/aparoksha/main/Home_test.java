package com.aparoksha.main;

import com.aparoksha.main.R;

import android.app.Activity;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

public class Home_test extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_home);
		
		TextView tv1 = (TextView)findViewById(R.id.hometext1);
		tv1.getBackground().setAlpha(128);
		
		TextView tv2 = (TextView)findViewById(R.id.hometext2);
		tv2.getBackground().setAlpha(128);
	
		TextView tv3 = (TextView)findViewById(R.id.hometext3);
		tv3.getBackground().setAlpha(128);
		
		TextView tv4 = (TextView)findViewById(R.id.hometext4);
		tv4.getBackground().setAlpha(128);
		
		ImageButton button = (ImageButton)findViewById(R.id.floatingbutton13);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home_test, menu);
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
