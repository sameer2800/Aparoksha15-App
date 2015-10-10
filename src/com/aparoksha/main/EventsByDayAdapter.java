package com.aparoksha.main;

import com.aparoksha.main.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class EventsByDayAdapter extends BaseAdapter {

	String[]		events;
	String[]		venues;
	String[]		timings;
	Drawable[]		images;
	Context			context;
	TextView		textView_1;
	TextView		textView_2;
	TextView		textView_3;
	ImageView		imageView;
	LayoutInflater	layoutInflater;

	public EventsByDayAdapter(Context context, String[] events,
			String[] venues, String[] timings, Drawable[] images) {
		this.context = context;
		this.events = events;
		this.venues = venues;
		this.timings = timings;
		this.images = images;
		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		// TODO Auto-generated method stub
		return events.length;
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return events[position];
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;

		view = convertView;
		if (view == null) {
			view = layoutInflater.inflate(R.layout.custom_row_3, null);
		}

		textView_1 = (TextView) view
				.findViewById(R.id.customRow_textView_Title);
		textView_2 = (TextView) view
				.findViewById(R.id.customRow_textView_Venue);
		textView_3 = (TextView) view.findViewById(R.id.customRow_textView_Time);
		imageView = (ImageView) view.findViewById(R.id.customRow_imageView);

		textView_1.setText(events[position]);
		textView_2.setText(venues[position]);
		textView_3.setText(timings[position]);
		imageView.setImageDrawable(images[position]);

		return view;
	}

}
