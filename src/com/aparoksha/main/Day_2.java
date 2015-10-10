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

public class Day_2 extends ListActivity {

	ListView	listView;
	String[]	day_2	= { "Biomeda", "Infinitum", "Platzen", "Techno Booz", "Xplorer",
			"NU Vision", "PS I Luv Electronics", "Alkhwarizm", "Confondere" };
	String[]	venues	= { "CC-3", "Online", "Online", "Online", "CC-3", "CC-3",
			"CC-3", "Online", "Online" };
	String[]	timings	= { "11:00 am", "12:00 am", "12:00 am", "12:00 am", "12:00 pm",
			"03:00 pm", "03:00 pm", "07:00 pm", "07:00 pm" };
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
				new EventsByDayAdapter(this, day_2, venues, timings, images));
	}

	private void getImages() {
		images = new Drawable[9];
		images[0] = getResources().getDrawable(R.drawable.biomeda);
		images[1] = getResources().getDrawable(R.drawable.infinitum);
		images[2] = getResources().getDrawable(R.drawable.platzen);
		images[3] = getResources().getDrawable(R.drawable.techno_booz);
		images[4] = getResources().getDrawable(R.drawable.xplorer);
		images[5] = getResources().getDrawable(R.drawable.nu_vision);
		images[6] = getResources().getDrawable(R.drawable.ps_i_luv_electronics);
		images[7] = getResources().getDrawable(R.drawable.alkhwarizm);
		images[8] = getResources().getDrawable(R.drawable.confondere);
	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);

		Intent intent;

		intent = new Intent(Day_2.this, Events.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		intent.putExtra("Event", day_2[position]);
		startActivity(intent);
	}
}
