package com.aparoksha.main;

import com.aparoksha.main.R;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Day_1 extends ListActivity {

	ListView	listView;
	String[]	day_1	= { "Black Box", "Perplexus", "Cold Fire", "AI Pod",
			"IT Quiz", "QWERTY Wars" };
	String[]	venues	= { "Online", "Online", "CC-3", "LT", "CC-3", "CC-3" };
	String[]	timings	= { "12:00 am", "12:00 am", "03:00 pm", "05:00 pm",
			"08:00 pm", "09:30 pm" };
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
				new EventsByDayAdapter(this, day_1, venues, timings, images));
	}

	private void getImages() {
		images = new Drawable[6];
		images[0] = getResources().getDrawable(R.drawable.black_box);
		images[1] = getResources().getDrawable(R.drawable.perplexus);
		images[2] = getResources().getDrawable(R.drawable.cold_fire);
		images[3] = getResources().getDrawable(R.drawable.ai_pod);
		images[4] = getResources().getDrawable(R.drawable.it_quiz);
		images[5] = getResources().getDrawable(R.drawable.qwerty_wars);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent intent;

		intent = new Intent(Day_1.this, Events.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("Event", day_1[position]);
		startActivity(intent);
	}
}
