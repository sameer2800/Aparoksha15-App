package com.aparoksha.main;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.aparoksha.main.R;
import com.navigation.adapter.NavDrawerListAdapter;
import com.navigation.drawer.NavDrawerItem;

public class Navigationdrawer extends Activity implements OnClickListener {

	private DrawerLayout				mDrawerLayout;
	private ListView					mDrawerList;
	private ActionBarDrawerToggle		mDrawerToggle;

	// nav drawer title
	private CharSequence				mDrawerTitle;

	// used to store app title
	private CharSequence				mTitle;

	// Changing Background
	// private LinearLayout linearLayout;
	// private ScrollView scrollView;
	// boolean halfWayCrossed;

	// slide menu items
	private String[]					navMenuTitles;
	private String[]					navMenuSubtitles;
	private TypedArray					navMenuIcons;

	private ArrayList<NavDrawerItem>	navDrawerItems;
	private NavDrawerListAdapter		adapter;

	int									oldY;
	int									alpha;
	int									next;
	int									sign;
	ImageButton							bt_facebook;
	ImageButton							bt_website;
	TextView							textView_1;
	TextView							textView_2;

	SharedPreferences					mSharedPreference;
	private TextView					check_user;

	private ProgressDialog				pDialog;

	public static String				name;
	private String						ifuser;

	// JSON parser class
	JSONParser							jsonParser	= new JSONParser();

	// testing on Emulator:
	private static final String			UPDATE_URL	= "http://aparoksha.iiita.ac.in/register1/updates.json";

	// JSON element ids from repsonse of php script:
	private static final String			TAG_SUCCESS	= "success";
	private static final String			TAG_MESSAGE	= "message";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.navigation);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		initialize();

		oldY = 0;
		sign = -1;
		alpha = 255;
		next = 0;

		textView_1 = (TextView) findViewById(R.id.hometext4);
		textView_2 = (TextView) findViewById(R.id.hometext2);
		bt_facebook = (ImageButton) findViewById(R.id.floatingbutton_fb);
		bt_website = (ImageButton) findViewById(R.id.floatingbutton_web);

		// halfWayCrossed = false;

		if (name == null) {
			name = " 1) Team Aparoksha Welcomes participants to Workshops. Visit website for further details. \n\n "
					+ "2) Gaming events this year:\n\tDota 2\n\tCounter Strike 1.6\n\tFIFA 2015\n\tSplit Second \n\n "
					+ "3) Exhibitions in the house for all.\n\tPlatform for Budding Technocrat\n\n ";
		}

		textView_1.setText(name);

		textView_2
				.setText("Inspired by a massive participation in technical events of Effervescence,we present "
						+ "our first exclusive TECHNICAL FEST Aparoksha, a horizon of vision and technology. "
						+ "At a platform for young minds to develop new skills  exhibit their talent, the thrill is expected "
						+ "to cross the barrier with many electrifying events in store. So gear up yourself to seek, explore"
						+ " and spread through APAROKSHA ");

		// scrollView.setOnTouchListener(this);
		bt_facebook.setOnClickListener(this);
		bt_website.setOnClickListener(this);

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

		createNavigationDrawer();
	}

	public void initialize() {

		TextView tv1 = (TextView) findViewById(R.id.hometext1);
		tv1.getBackground().setAlpha(30);
		// tv1.setShadowLayer(1, 0, 0, Color.BLACK);

		TextView tv2 = (TextView) findViewById(R.id.hometext2);
		tv2.getBackground().setAlpha(30);

		TextView tv3 = (TextView) findViewById(R.id.hometext3);
		tv3.getBackground().setAlpha(30);
		// tv3.setShadowLayer(1, 0, 0, Color.BLACK);

		TextView tv4 = (TextView) findViewById(R.id.hometext4);
		tv4.getBackground().setAlpha(30);
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
		getMenuInflater().inflate(R.menu.updates, menu);
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
			case R.id.action_updates:
				new Update().execute();
				return true;

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
		return super.onPrepareOptionsMenu(menu);
	}

	private void createNavigationDrawer() {
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

		// if (savedInstanceState == null) {
		// on first time display view for first nav item
		// displayView(0);
		// }
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
				break;
			case 2:
				Intent intent = new Intent("com.aparoksha.main.eventsbyday");
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(intent);
				// finish();
				break;
			case 3:
				// fragment = new PagesFragment();
				Intent i = new Intent("com.aparoksha.main.mainactivity");
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i);
				// finish();
				break;
			case 4:
				// fragment = new PagesFragment();
				Intent i2 = new Intent("com.aparoksha.main.favorites");
				i2.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i2);
				// finish();
				break;
			case 5:
				Intent i3 = new Intent("com.aparoksha.main.Login");
				i3.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i3);
				// fragment = new PagesFragment();
				break;
			case 6:
				Intent i4 = new Intent("com.aparoksha.main.Register");
				i4.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i4);
				// fragment = new PagesFragment();
				break;
			case 7:
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

	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent intent;
		intent = null;
		if (v.getId() == R.id.floatingbutton_fb) {
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/aparoksha?fref=ts"));
		} else {
			intent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://aparoksha.iiita.ac.in/beta/#home"));
		}

		// intent.addFlags(Intent.fla)
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		startActivity(intent);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		// savedInstance = get
		// adapter.setText("New Text");
	}

	class Update extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		boolean	failure	= false;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Navigationdrawer.this);
			pDialog.setMessage("Updating...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		@Override
		protected String doInBackground(String... args) {
			// TODO Auto-generated method stub
			// Check for success tag
			int success;

			// String username = ifuser;
			// String event = string_event;
			try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				params.add(new BasicNameValuePair("event", "event")); // useless

				Log.d("request!", "starting");
				// getting product details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(UPDATE_URL,
						"POST", params);

				// json success tag
				success = json.getInt(TAG_SUCCESS);

				if (success == 1) {

					int how_many = json.getInt("no_updates");

					JSONObject childJSONObject = json.getJSONObject("updates");

					String local = "";

					for (int i = 1; i <= how_many; i++) { // **line 2**

						local = local + i + ")"
								+ childJSONObject.getString("update" + i)
								+ "\n\n";

					}

					if (!name.equals(local)) {
						name = local;
						publishProgress(name);
					}

					return "Successfully Updated";
				} else if (success == 0) {

					return json.getString(TAG_MESSAGE);

				} else {

				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;

		}

		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			TextView update = (TextView) findViewById(R.id.hometext4);
			update.setText(values[0]);
			// update.setTextColor(Color.GREEN);

		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once product deleted
			pDialog.dismiss();
			if (file_url != null) {
				Toast.makeText(Navigationdrawer.this, file_url,
						Toast.LENGTH_LONG).show();
			}

		}

	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

		createNavigationDrawer();

	}

}