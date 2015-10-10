package com.aparoksha.main;

import java.util.ArrayList;
import java.util.StringTokenizer;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.aparoksha.main.R;
import com.navigation.adapter.NavDrawerListAdapter;
import com.navigation.drawer.NavDrawerItem;

public class Favorites extends Activity implements OnItemClickListener {

	private static final String			TAG	= "CardListActivity";
	private CardArrayAdapter			cardArrayAdapter;
	private ListView					listView;

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

	DBmanager							dBmanager;
	int									indexAdjustmentFactor;
	String[]							favoriteEventNames;

	private String						ifuser;

	SharedPreferences					mSharedPreference;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.listview);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

		mapViews();
		createNavigationDrawer(savedInstanceState);
		addItemsToDatabase();
		readDatabase();
		createListView();
		addListeners();

	}

	private void addItemsToDatabase() {
		try {
			dBmanager.open();
			if (dBmanager.getEventCount() == 0) {
				dBmanager.createEntry("Alkhwarizm", "07:00 pm", "21-Mar",
						"Online");
				dBmanager.createEntry("Tech Talks", "05:00 pm", "21-Mar",
						"Main Audi");
				dBmanager.createEntry("IT Quiz", "08:00 pm", "20-Mar", "CC-3");
				dBmanager
						.createEntry("Hackathon", "03:00 pm", "22-Mar", "CC-3");
				dBmanager.createEntry("Perplexus", "All-Day", "20-Mar",
						"Online");
				dBmanager.createEntry("AI Pod", "04:00 pm", "20-Mar", "CC-3");
				dBmanager
						.createEntry("Xplorer", "12:00 pm", "21-Mar", "Online");
			}
			// dBmanager.close();

		} catch (Exception e) {
			Log.d("SQLException",
					"Error while adding items to DB: " + e.getMessage());
		}
	}

	private void readDatabase() {
		try {
			dBmanager.open();
			favoriteEventNames = dBmanager.getEventNames();
			// dBmanager.close();
		} catch (Exception e) {
			favoriteEventNames = null;
		}
	}

	private void addListeners() {
		listView.setOnItemClickListener(this);
	}

	private void mapViews() {
		listView = (ListView) findViewById(R.id.card_listView);
		dBmanager = new DBmanager(Favorites.this);
		indexAdjustmentFactor = 0;
	}

	private void createListView() {
		int limit;
		Card card;
		int imageResourceId;

		imageResourceId = R.drawable.penalty_shootout;
		limit = favoriteEventNames.length;
		listView.addHeaderView(new View(this));
		listView.addFooterView(new View(this));

		cardArrayAdapter = new CardArrayAdapter(getApplicationContext(),
				R.layout.list_item_card);

		if (favoriteEventNames != null) {
			for (int i = 0; i < limit; i++) {
				try {
					imageResourceId = getResources().getIdentifier(
							"drawable/" + getImageName(favoriteEventNames[i]),
							null, getPackageName());
				} catch (Exception e) {
					Log.d("ImageError", e.getMessage());
				} finally {
					card = new Card(favoriteEventNames[i], imageResourceId);
					cardArrayAdapter.add(card);
				}
			}
			listView.setAdapter(cardArrayAdapter);
		} else {
			Toast.makeText(getApplicationContext(),
					"No Events added to Favorites", Toast.LENGTH_SHORT).show();
		}
	}

	private String getImageName(String eventName) {
		String imageName;
		StringTokenizer stringTokenizer;

		stringTokenizer = new StringTokenizer(eventName);
		imageName = stringTokenizer.nextToken().toLowerCase();

		while (stringTokenizer.hasMoreTokens()) {
			imageName += "_" + stringTokenizer.nextToken().toLowerCase();
		}

		return imageName;
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
		Log.d("ImageText", "'" + ifuser + "'");

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

	/*
	 * Called when invalidateOptionsMenu() is triggered
	 */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// if nav drawer is opened, hide the action items
		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		// menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.navigationdrawer, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		int id = item.getItemId();
		return super.onOptionsItemSelected(item);
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
				Intent intent = new Intent("com.aparoksha.main.eventsbyday");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(intent);
				finish();
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

	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		Intent intent;
		intent = new Intent(Favorites.this, Events.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		boolean simulateClick = true;

		while (simulateClick && indexAdjustmentFactor < 20) {
			try {
				intent.putExtra("Event", favoriteEventNames[position - 1
						- indexAdjustmentFactor]);
				Log.d("Position", "" + position);
				Log.d("Index", "" + (position - 1 - indexAdjustmentFactor));
				simulateClick = false;
				startActivity(intent);
			} catch (ArrayIndexOutOfBoundsException e) {
				Log.d("IndexOutOfBounds", e.getMessage());
				indexAdjustmentFactor++;
			}
		}
	}

	/*
	 * @Override protected void onActivityResult(int requestCode, int
	 * resultCode, Intent data) { // TODO Auto-generated method stub
	 * super.onActivityResult(requestCode, resultCode, data); if (resultCode ==
	 * RESULT_OK) { indexAdjustmentFactor += data.getExtras().getInt("Result");
	 * Log.d("Adjustment Factor", "" + indexAdjustmentFactor); } }
	 */

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		try {
			// nullifyObjects();
			// mapViews();
			readDatabase();
			createListView();
			++indexAdjustmentFactor;
			// addListeners();
		} catch (Exception e) {
			Log.d("Restart Error", e.getMessage());
		}
	}

	private void nullifyObjects() {
		favoriteEventNames = null;
		listView = null;
		dBmanager = null;
		cardArrayAdapter = null;
	}

}
