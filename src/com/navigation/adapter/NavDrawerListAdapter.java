package com.navigation.adapter;

import com.aparoksha.main.JSONParser;
import com.aparoksha.main.R;
import com.navigation.drawer.NavDrawerItem;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
	private ArrayList<NavDrawerItem> navDrawerItems;
	private Drawable drawable;
	private String login;

	private String ifuser;

	SharedPreferences mSharedPreference;

	public NavDrawerListAdapter(Context context,
			ArrayList<NavDrawerItem> navDrawerItems) {
		this.context = context;
		this.navDrawerItems = navDrawerItems;
	}

	public void setText(String login) {
		this.login = login;
	}

	public void setDrawable(Drawable drawable) {
		this.drawable = drawable;
	}

	public int getCount() {
		return navDrawerItems.size();
	}

	public Object getItem(int position) {
		return navDrawerItems.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null || convertView.getId() != R.layout.custom_row_2) {
			LayoutInflater mInflater = (LayoutInflater) context
					.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView = mInflater.inflate(R.layout.custom_row_2, null);
		}

		if (position == 0) {
			ImageView imageView = new ImageView(context);
			TextView textView = new TextView(context);
			if (imageView != null) {
				imageView.setImageResource(R.drawable.aparoksha_logo_big);

				if (drawable != null) {
					textView.setBackground(drawable);
					
					
					 
					 
					textView.setText(login);
					textView.setTextColor(Color.BLACK);
					textView.setTextSize(20.0f);
					textView.setTypeface(null, Typeface.BOLD);
					textView.setGravity(Gravity.BOTTOM
							| Gravity.CENTER_HORIZONTAL);
					// imageView.ad
				} else {
					Toast.makeText(context, "ImageView NULL",
							Toast.LENGTH_SHORT).show();
				}
				if (textView.getBackground() != null) {
					return textView;
				} else {
					return imageView;
				}
			}
		}
		ImageView imgIcon = (ImageView) convertView
				.findViewById(R.id.customRow_imageView);
		TextView txtTitle = (TextView) convertView
				.findViewById(R.id.customRow_textView_Title);
		TextView txtSubtitle = (TextView) convertView
				.findViewById(R.id.customRow_textView_Venue);
		// TextView txtCount = (TextView)
		// convertView.findViewById(R.id.counter);

		if (imgIcon != null && txtTitle != null) {
			imgIcon.setImageResource(navDrawerItems.get(position).getIcon());
			txtTitle.setText(navDrawerItems.get(position).getTitle());
			txtSubtitle.setText(navDrawerItems.get(position).getSubtitle());
		} else {
			Log.d("NavAdapter", "ImageView or TextView NULL");
		}

		// displaying count
		// check whether it set visible or not
		// if(navDrawerItems.get(position).getCounterVisibility()){
		// txtCount.setText(navDrawerItems.get(position).getCount());
		// }else{
		// hide the counter view
		// txtCount.setVisibility(View.GONE);
		// }

		return convertView;

	}
}
