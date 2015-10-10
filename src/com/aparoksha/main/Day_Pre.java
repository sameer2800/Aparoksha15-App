package com.aparoksha.main;

import com.aparoksha.main.R;
import com.aparoksha.main.R.drawable;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class Day_Pre extends ListActivity {

	ListView	listView;
	String[]	day_pre	= { "Eureka", "Webkriti", "Riddlonics", "Techno Fault",
			"V Flare"	};
	String[]	venues	= { "Online", "Online", "CC-3", "CC-3", "Online" };
	String[]	timings	= { "18-22 Mar", "18-21 Mar", "19-20 Mar", "18-21 Mar",
			"15-21 Mar"	};
	Drawable[]	images;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_day_1);

		// setListAdapter(new ArrayAdapter<String>(Day_1.this,
		// android.R.layout.simple_list_item_1, day_1));
		listView = (ListView) findViewById(R.id.eventsByDay_listView);
		getImages();

		getListView().setAdapter(
				new EventsByDayAdapter(this, day_pre, venues, timings, images));
	}

	private void getImages() {
		images = new Drawable[5];
		images[0] = getResources().getDrawable(R.drawable.eureka);
		images[1] = getResources().getDrawable(R.drawable.webkriti);
		images[2] = getResources().getDrawable(R.drawable.riddlonics);
		images[3] = getResources().getDrawable(R.drawable.techno_fault);
		images[4] = getResources().getDrawable(R.drawable.v_flare);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent intent;

		intent = new Intent(Day_Pre.this, Events.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("Event", day_pre[position]);
		startActivity(intent);
	}

}
