package com.aparoksha.main;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;

import com.aparoksha.main.R;
import com.navigation.adapter.NavDrawerListAdapter;
import com.navigation.drawer.NavDrawerItem;

public class EventsByDay extends TabActivity {

	public static final String			DAY_PRE	= "Pre";
	public static final String			DAY_1	= "Mar-20";
	public static final String			DAY_2	= "Mar-21";
	public static final String			DAY_3	= "Mar-22";

	TabHost								tabHost;
	TabSpec								tabSpec_Day_Pre;
	TabSpec								tabSpec_Day_1;
	TabSpec								tabSpec_Day_2;
	TabSpec								tabSpec_Day_3;

	private DrawerLayout				mDrawerLayout;
	private ListView					mDrawerList;
	private ActionBarDrawerToggle		mDrawerToggle;

	// nav drawer title
	private CharSequence				mDrawerTitle;

	// used to store app title
	private CharSequence				mTitle;

	// slide menu items
	private String[]					navMenuTitles;
	private String[]					navMenuSubtitles;
	private TypedArray					navMenuIcons;

	private ArrayList<NavDrawerItem>	navDrawerItems;
	private NavDrawerListAdapter		adapter;

	private String						ifuser;

	SharedPreferences					mSharedPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.activity_events_by_day);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

		createNavigationDrawer(savedInstanceState);
		createObjects();
		createTabs();
		addTabs();
		changeTabColor();
		addListeners();
	}

	private void changeTabColor() {
		// TODO Auto-generated method stub
		int i;
		for (i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
			TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i)
					.findViewById(android.R.id.title);
			tv.setTextColor(Color.parseColor("#fcfbf9"));
		}
	}

	private void addListeners() {
		// TODO Auto-generated method stub
	}

	private void createObjects() {
		tabHost = getTabHost();
		tabSpec_Day_Pre = tabHost.newTabSpec(DAY_PRE);
		tabSpec_Day_1 = tabHost.newTabSpec(DAY_1);
		tabSpec_Day_2 = tabHost.newTabSpec(DAY_2);
		tabSpec_Day_3 = tabHost.newTabSpec(DAY_3);
	}

	private void createTabs() {
		Intent intent;

		tabSpec_Day_Pre.setIndicator(DAY_PRE,
				getResources().getDrawable(R.drawable.ic_home));
		tabSpec_Day_1.setIndicator(DAY_1,
				getResources().getDrawable(R.drawable.ic_home));
		tabSpec_Day_2.setIndicator(DAY_2,
				getResources().getDrawable(R.drawable.ic_home));
		tabSpec_Day_3.setIndicator(DAY_3,
				getResources().getDrawable(R.drawable.ic_home));

		intent = new Intent(EventsByDay.this, Day_Pre.class);
		tabSpec_Day_Pre.setContent(intent);

		intent = new Intent(EventsByDay.this, Day_1.class);
		tabSpec_Day_1.setContent(intent);

		intent = new Intent(EventsByDay.this, Day_2.class);
		tabSpec_Day_2.setContent(intent);

		intent = new Intent(EventsByDay.this, Day_3.class);
		tabSpec_Day_3.setContent(intent);
	}

	private void addTabs() {
		tabHost.addTab(tabSpec_Day_Pre);
		tabHost.addTab(tabSpec_Day_1);
		tabHost.addTab(tabSpec_Day_2);
		tabHost.addTab(tabSpec_Day_3);
	}

	private void createNavigationDrawer(Bundle savedInstanceState) {
		mTitle = mDrawerTitle = getTitle();
		// load slide menu items
		navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);
		navMenuSubtitles = getResources().getStringArray(
				R.array.nav_drawer_subscripts);

		// nav drawer icons from resources
		navMenuIcons = getResources()
				.obtainTypedArray(R.array.nav_drawer_icons);

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.list_slidermenu);

		navDrawerItems = new ArrayList<NavDrawerItem>();

		addItemsToNavigationDrawer();

		// Recycle the typed array
		navMenuIcons.recycle();

		mDrawerList.setOnItemClickListener(new SlideMenuClickListener());

		// setting the nav drawer list adapter
		adapter = new NavDrawerListAdapter(getApplicationContext(),
				navDrawerItems);
		adapter.setDrawable(getResources().getDrawable(R.drawable.iiita_night2));
		adapter.setText(ifuser);
		mDrawerList.setAdapter(adapter);

		// enabling action bar app icon and behaving it as toggle button
		getActionBar().setDisplayHomeAsUpEnabled(true);
		getActionBar().setHomeButtonEnabled(true);

		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_navigation_drawer, // nav menu toggle icon
				R.string.app_name, // nav drawer open - description for
									// accessibility
				R.string.app_name // nav drawer close - description for
									// accessibility
		) {

			public void onDrawerClosed(View view) {
				//getActionBar().setTitle(mTitle);
				// calling onPrepareOptionsMenu() to show action bar icons
				invalidateOptionsMenu();
			}

			public void onDrawerOpened(View drawerView) {
				//getActionBar().setTitle(mDrawerTitle);
				// calling onPrepareOptionsMenu() to hide action bar icons
				invalidateOptionsMenu();
			}
		};
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		if (savedInstanceState == null) {
			// on first time display view for first nav item
			displayView(0);
		}
	}

	private void addItemsToNavigationDrawer() {
		// adding nav drawer items to array
		// Home
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[0],
				navMenuSubtitles[0], navMenuIcons.getResourceId(0, -1)));
		// Find People
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[1],
				navMenuSubtitles[1], navMenuIcons.getResourceId(1, -1)));
		// Photos
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[2],
				navMenuSubtitles[2], navMenuIcons.getResourceId(2, -1)));
		// Communities, Will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[3],
				navMenuSubtitles[3], navMenuIcons.getResourceId(3, -1), true,
				"22"));
		// Pages
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[4],
				navMenuSubtitles[4], navMenuIcons.getResourceId(4, -1)));
		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[5],
				navMenuSubtitles[5], navMenuIcons.getResourceId(5, -1), true,
				"50+"));

		// What's hot, We will add a counter here
		navDrawerItems.add(new NavDrawerItem(navMenuTitles[6],
				navMenuSubtitles[6], navMenuIcons.getResourceId(6, -1), true,
				"50+"));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[7],
				navMenuSubtitles[7], navMenuIcons.getResourceId(7, -1), true,
				"50+"));

		navDrawerItems.add(new NavDrawerItem(navMenuTitles[8],
				navMenuSubtitles[8], navMenuIcons.getResourceId(8, -1), true,
				"50+"));
	}

	/**
	 * Slide menu item click listener
	 * */
	private class SlideMenuClickListener implements
			ListView.OnItemClickListener {

		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// display view for selected nav drawer item
			displayView(position);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.navigationdrawer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle action bar actions click
		switch (item.getItemId()) {
			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/***
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	/**
	 * Diplaying fragment view for selected nav drawer list item
	 * */
	private void displayView(int position) {
		// update the main content by replacing fragments
		Fragment fragment = null;
		switch (position) {
			case 0:
				// fragment = new PagesFragment();
				break;
			case 1:
				Intent in = new Intent("com.aparoksha.main.navigationdrawer");
				in.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(in);
				finish();
				break;
			case 2:
				break;
			case 3:
				// fragment = new PagesFragment();
				Intent i = new Intent("com.aparoksha.main.mainactivity");
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i);
				finish();
				break;
			case 4:
				// fragment = new PagesFragment();
				Intent i2 = new Intent("com.aparoksha.main.favorites");
				i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i2);
				finish();
				break;
			case 5:
				Intent i3 = new Intent("com.aparoksha.main.Login");
				i3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i3);
				finish();
				// fragment = new PagesFragment();
				break;
			case 6:
				Intent i4 = new Intent("com.aparoksha.main.Register");
				i4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i4);
				finish();
				// fragment = new PagesFragment();
				break;
			case 7:
				// fragment = new PagesFragment();
				Intent i9 = new Intent("com.aparoksha.main.organizers");
				i9.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i9);
				finish();
				break;
			case 8:
				Intent i8 = new Intent("com.aparoksha.main.app_developers");
				i8.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i8);
				finish();
				// fragment = new PagesFragment();
				break;
			default:
				break;
		}

		// if (fragment != null) {
		// FragmentManager fragmentManager = getFragmentManager();
		// fragmentManager.beginTransaction()
		// .replace(R.id.frame_container, fragment).commit();

		// update selected item and title, then close the drawer
		mDrawerList.setItemChecked(position, true);
		mDrawerList.setSelection(position);
		// setTitle(navMenuTitles[position]);
		mDrawerLayout.closeDrawer(mDrawerList);
		// } else {
		// error in creating fragment
		// Log.e("MainActivity", "Error in creating fragment");
		// }
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}

	/**
	 * When using the ActionBarDrawerToggle, you must call it during
	 * onPostCreate() and onConfigurationChanged()...
	 */

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		// Sync the toggle state after onRestoreInstanceState has occurred.
		mDrawerToggle.syncState();
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		// Pass any configuration change to the drawer toggls
		mDrawerToggle.onConfigurationChanged(newConfig);
	}

}
