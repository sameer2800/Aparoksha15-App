package com.aparoksha.main;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aparoksha.main.R;
import com.navigation.adapter.NavDrawerListAdapter;
import com.navigation.drawer.NavDrawerItem;

/**
 * Test activity to display the list view
 */
public class Roboticsevents extends Activity {

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

	/** Id for the toggle rotation menu item */
	private static final int			TOGGLE_ROTATION_MENU_ITEM	= 0;

	/** Id for the toggle lighting menu item */
	private static final int			TOGGLE_LIGHTING_MENU_ITEM	= 1;

	/** The list view */
	private MyListView					mListView;

	private String						ifuser;

	SharedPreferences					mSharedPreference;

	private Contact[]					pickedCategory;
	private static Contact[]			catRobotics;
	private static Contact[]			catElectronics;
	private static Contact[]			catCoding;
	private static Contact[]			catNetworking;
	private static Contact[]			catDevelopment;
	private static Contact[]			catMisc;

	static {
		if (catRobotics == null) {
			catRobotics = new Contact[3];
			catRobotics[0] = new Contact("AI Pod", "20-Mar\t\t05:00 pm",
					R.drawable.ai_pod);
			catRobotics[1] = new Contact("Xplorer", "21-Mar\t\t12:00 pm",
					R.drawable.xplorer);
			catRobotics[2] = new Contact("Bolt", "22-Mar\t\t04:00 pm",
					R.drawable.bolt);
		}
		if (catElectronics == null) {
			catElectronics = new Contact[6];
			catElectronics[0] = new Contact("Riddlonics", "19-Mar\t\t07:00 pm",
					R.drawable.riddlonics);
			catElectronics[1] = new Contact("Techno Fault",
					"18-Mar\t\t07:00 pm", R.drawable.techno_fault);
			catElectronics[2] = new Contact("Black Box", "18-Mar\t\t07:00 pm",
					R.drawable.black_box);
			catElectronics[3] = new Contact("PS I Luv Electronics",
					"21-Mar\t\t03:00 pm", R.drawable.ps_i_luv_electronics);
			catElectronics[4] = new Contact("Wolf of 2311",
					"22-Mar\t\t10:00 am", R.drawable.wolf_of_2311);
			catElectronics[5] = new Contact("Thought Bazaar",
					"22-Mar\t\t03:00 pm", R.drawable.thought_bazaar);
		}
		if (catCoding == null) {
			catCoding = new Contact[4];
			catCoding[0] = new Contact("Infinitum", "21-Mar\t\t12:00 am",
					R.drawable.infinitum);
			catCoding[1] = new Contact("Alkhwarizm", "21-Mar\t\t07:00 pm",
					R.drawable.alkhwarizm);
			catCoding[2] = new Contact("C Fresh", "22-Mar\t\t11:00 am",
					R.drawable.c_fresh);
			catCoding[3] = new Contact("Code Show", "22-Mar\t\t11:00 am",
					R.drawable.code_show);
		}
		if (catNetworking == null) {
			catNetworking = new Contact[4];
			catNetworking[0] = new Contact("Perplexus", "20-Mar\t\t12:00 am",
					R.drawable.perplexus);
			catNetworking[1] = new Contact("Platzen", "21-Mar\t\t12:00 am",
					R.drawable.platzen);
			catNetworking[2] = new Contact("Cold Fire", "20-Mar\t\t03:00 pm",
					R.drawable.cold_fire);
			catNetworking[3] = new Contact("Hackathon", "22-Mar\t\t03:00 pm",
					R.drawable.hackathon);
		}
		if (catDevelopment == null) {
			catDevelopment = new Contact[5];
			catDevelopment[0] = new Contact("Webkriti", "18-Mar\t\t06:00 pm",
					R.drawable.webkriti);
			catDevelopment[1] = new Contact("Eureka", "18-Mar\t\t12:00 am",
					R.drawable.eureka);
			catDevelopment[2] = new Contact("V Flare", "15-Mar\t\t12:00 am",
					R.drawable.v_flare);
			catDevelopment[3] = new Contact("NU Vision", "21-Mar\t\t03:00 pm",
					R.drawable.nu_vision);
			catDevelopment[4] = new Contact("Back Bone", "22-Mar\t\t11:00 am",
					R.drawable.back_bone);
		}
		if (catMisc == null) {
			catMisc = new Contact[6];
			catMisc[0] = new Contact("Biomeda", "21-Mar\t\t11:00 am", R.drawable.biomeda);
			catMisc[1] = new Contact("IT Quiz", "20-Mar\t\t08:00 pm",
					R.drawable.it_quiz);
			catMisc[2] = new Contact("QWERTY Wars", "20-Mar\t\t09:30 pm",
					R.drawable.qwerty_wars);
			catMisc[3] = new Contact("Techno Booz", "21-Mar\t\t12:00 am",
					R.drawable.techno_booz);
			catMisc[4] = new Contact("Confondere", "21-Mar\t\t07:00 pm",
					R.drawable.confondere);
			catMisc[5] = new Contact("Tech Talks", "21-Mar\t\t07:00 pm",
					R.drawable.tech_talks);
		}
	}

	/**
	 * Small class that represents a contact
	 */
	private static class Contact {

		/** Name of the Event */
		String	mName;

		/** DateTime of the Event */
		String	mDateTime;

		// Thumbnail of the Event
		int		mImage;

		/**
		 * Constructor
		 * 
		 * @param name
		 *            The name
		 * @param number
		 *            The number
		 */
		public Contact(final String name, final String dateTime, final int image) {
			mName = name;
			mDateTime = dateTime;
			mImage = image;
		}

	}

	@Override
	public void onCreate(final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
		setContentView(R.layout.roboticsevents);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		pickCategory(getIntent().getExtras().getString("Category"));

		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

		createNavigationDrawer(savedInstanceState);

		final ArrayList<Contact> contacts = createContactList(pickedCategory.length);
		final MyAdapter adapter = new MyAdapter(this, contacts);

		mListView = (MyListView) findViewById(R.id.my_list);
		mListView.setAdapter(adapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {

				Intent i = new Intent("com.aparoksha.main.events");
				i.putExtra("Event", pickedCategory[position].mName);
				startActivity(i);

				// final String message = "OnClick: "
				// + contacts.get(position).mName;
				// Toast.makeText(Roboticsevents.this, message,
				// Toast.LENGTH_SHORT)
				// .show();
			}
		});

		mListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			public boolean onItemLongClick(final AdapterView<?> parent,
					final View view, final int position, final long id) {
				// final String message = "OnLongClick: "
				// + contacts.get(position).mName;
				// Toast.makeText(Roboticsevents.this, message,
				// Toast.LENGTH_SHORT)
				// .show();
				return true;
			}
		});
	}

	private void pickCategory(String category) {
		getActionBar().setTitle(category);
		if (category.equalsIgnoreCase("Robotics")) {
			this.pickedCategory = catRobotics;
		} else if (category.equalsIgnoreCase("Electronics")) {
			this.pickedCategory = catElectronics;
		} else if (category.equalsIgnoreCase("Coding")) {
			this.pickedCategory = catCoding;
		} else if (category.equalsIgnoreCase("Networking")) {
			this.pickedCategory = catNetworking;
		} else if (category.equalsIgnoreCase("Development")) {
			this.pickedCategory = catDevelopment;
		} else {
			this.pickedCategory = catMisc;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(final Menu menu) {
		getMenuInflater().inflate(R.menu.navigationdrawer, menu);
		// menu.add(Menu.NONE, TOGGLE_ROTATION_MENU_ITEM, 0, "Toggle Rotation");
		// menu.add(Menu.NONE, TOGGLE_LIGHTING_MENU_ITEM, 1, "Toggle Lighting");
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(final MenuItem item) {
		// toggle nav drawer on selecting action bar app icon/title
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}

		switch (item.getItemId()) {
			case TOGGLE_ROTATION_MENU_ITEM:
				mListView.enableRotation(!mListView.isRotationEnabled());
				return true;

			case TOGGLE_LIGHTING_MENU_ITEM:
				mListView.enableLight(!mListView.isLightEnabled());
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Creates a list of fake contacts
	 * 
	 * @param size
	 *            How many contacts to create
	 * @return A list of fake contacts
	 */
	private ArrayList<Contact> createContactList(final int size) {
		final ArrayList<Contact> contacts = new ArrayList<Contact>();
		for (int i = 0; i < size; i++) {

			/*
			 * if (i == 0) { contacts.add(new Contact("Penalty Shoot out ",
			 * "+46(0)" + (int) (1000000 + 9000000 * Math.random()))); } else if
			 * (i == 1) { contacts.add(new Contact("AI POD ", "+46(0)" + (int)
			 * (1000000 + 9000000 * Math.random())));
			 * 
			 * } else { contacts.add(new Contact("Contact Number " + i, "+46(0)"
			 * + (int) (1000000 + 9000000 * Math.random())));
			 * 
			 * }
			 */
			contacts.add(pickedCategory[i]);

		}
		return contacts;
	}

	/**
	 * Adapter class to use for the list
	 */
	private static class MyAdapter extends ArrayAdapter<Contact> {

		/** Re-usable contact image drawable */
		// private final Drawable contactImage;
		// private final Drawable shootout;
		// private final Drawable robomaze;
		private final Context	context;

		/**
		 * Constructor
		 * 
		 * @param context
		 *            The context
		 * @param contacts
		 *            The list of contacts
		 */
		public MyAdapter(final Context context,
				final ArrayList<Contact> contacts) {
			super(context, 0, contacts);
			this.context = context;

			/*
			 * contactImage = context.getResources().getDrawable(
			 * R.drawable.contact_image); shootout =
			 * context.getResources().getDrawable( R.drawable.penalty_shootout);
			 * robomaze =
			 * context.getResources().getDrawable(R.drawable.robomaze);
			 */
		}

		@Override
		public View getView(final int position, final View convertView,
				final ViewGroup parent) {
			View view = convertView;
			if (view == null) {
				view = LayoutInflater.from(getContext()).inflate(
						R.layout.list_item, null);
			}

			final TextView name = (TextView) view
					.findViewById(R.id.contact_name);
			/*
			 * if (position == 14) {
			 * name.setText("This is a long text that will make this box big. "
			 * +
			 * "Really big. Bigger than all the other boxes. Biggest of them all."
			 * ); } else {
			 */
			name.setText(getItem(position).mName);
			// }

			final TextView number = (TextView) view
					.findViewById(R.id.contact_number);
			number.setText(getItem(position).mDateTime);

			final ImageView photo = (ImageView) view
					.findViewById(R.id.contact_photo);

			/*
			 * if (position == 0) { photo.setImageDrawable(shootout); } else if
			 * (position == 1) { photo.setImageDrawable(robomaze); } else {
			 * photo.setImageDrawable(contactImage); }
			 */

			photo.setImageDrawable(context.getResources().getDrawable(
					getItem(position).mImage));

			return view;
		}
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
				Intent i9 = new Intent("com.aparoksha.main.organizers");
				i9.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
						| Intent.FLAG_ACTIVITY_TASK_ON_HOME);
				startActivity(i9);
				finish();
				// fragment = new PagesFragment();
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
