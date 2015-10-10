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

public class Day_3 extends ListActivity {

	ListView	listView;
	String[]	day_3	= { "Wolf of 2311", "Back Bone", "C Fresh",
			"Code Show", "Hackathon", "Thought Bazaar", "Bolt", "Tech Talks" };
	String[]	venues	= { "CC-1", "CC-3", "CC-3", "CC-3",
			"CC-3", "LT", "LT", "Main Audi" };
	String[]	timings	= { "10:00 am", "11:00 am", "11:00 am", "11:00 am",
			"03:00 pm", "03:00 pm", "04:00 pm", "07:00 pm" };
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
				new EventsByDayAdapter(this, day_3, venues, timings, images));
	}

	private void getImages() {
		images = new Drawable[8];
		images[0] = getResources().getDrawable(R.drawable.wolf_of_2311);
		images[1] = getResources().getDrawable(R.drawable.back_bone);
		images[2] = getResources().getDrawable(R.drawable.c_fresh);
		images[3] = getResources().getDrawable(R.drawable.code_show);
		images[4] = getResources().getDrawable(R.drawable.hackathon);
		images[5] = getResources().getDrawable(R.drawable.thought_bazaar);
		images[6] = getResources().getDrawable(R.drawable.bolt);
		images[7] = getResources().getDrawable(R.drawable.tech_talks);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent intent;

		intent = new Intent(Day_3.this, Events.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("Event", day_3[position]);
		startActivity(intent);
	}
}
