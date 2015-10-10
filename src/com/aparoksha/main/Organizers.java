package com.aparoksha.main;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.aparoksha.main.R;
import com.navigation.adapter.NavDrawerListAdapter;
import com.navigation.drawer.NavDrawerItem;

public class Organizers extends Activity {

	private class RoundImage extends Drawable {

		private final Bitmap	mBitmap;
		private final Paint		mPaint;
		private final RectF		mRectF;
		private final int		mBitmapWidth;
		private final int		mBitmapHeight;

		public RoundImage(Bitmap bitmap) {
			mBitmap = bitmap;
			mRectF = new RectF();
			mPaint = new Paint();
			mPaint.setAntiAlias(true);
			mPaint.setDither(true);
			final BitmapShader shader = new BitmapShader(bitmap,
					Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
			mPaint.setShader(shader);

			mBitmapWidth = mBitmap.getWidth();
			mBitmapHeight = mBitmap.getHeight();
		}

		@Override
		public void draw(Canvas canvas) {
			canvas.drawOval(mRectF, mPaint);
		}

		@Override
		protected void onBoundsChange(Rect bounds) {
			super.onBoundsChange(bounds);
			mRectF.set(bounds);
		}

		@Override
		public void setAlpha(int alpha) {
			if (mPaint.getAlpha() != alpha) {
				mPaint.setAlpha(alpha);
				invalidateSelf();
			}
		}

		@Override
		public void setColorFilter(ColorFilter cf) {
			mPaint.setColorFilter(cf);
		}

		@Override
		public int getOpacity() {
			return PixelFormat.TRANSLUCENT;
		}

		@Override
		public int getIntrinsicWidth() {
			return mBitmapWidth;
		}

		@Override
		public int getIntrinsicHeight() {
			return mBitmapHeight;
		}

		public void setAntiAlias(boolean aa) {
			mPaint.setAntiAlias(aa);
			invalidateSelf();
		}

		@Override
		public void setFilterBitmap(boolean filter) {
			mPaint.setFilterBitmap(filter);
			invalidateSelf();
		}

		@Override
		public void setDither(boolean dither) {
			mPaint.setDither(dither);
			invalidateSelf();
		}

		public Bitmap getBitmap() {
			return mBitmap;
		}
	}

	ImageView							imageView_1;
	ImageView							imageView_2;
	ImageView							imageView_3;
	ImageView							imageView_4;
	ImageView							imageView_5;
	ImageView							imageView_6;
	ImageView							imageView_7;
	ImageView							imageView_8;
	ImageView							imageView_9;
	ImageView							imageView_10;
	ImageView							imageView_11;
	ImageView							imageView_12;
	ImageView							imageView_13;
	ImageView							imageView_14;
	ImageView							imageView_15;
	ImageView							imageView_16;
	ImageView imageView_17;

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
		setContentView(R.layout.activity_organizers);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		mapViews();
		createNavigationDrawer(savedInstanceState);
		// setImages();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		setImages();
	}

	private void mapViews() {
		imageView_1 = (ImageView) findViewById(R.id.organizers_imageView_1);
		imageView_2 = (ImageView) findViewById(R.id.organizers_imageView_2);
		imageView_3 = (ImageView) findViewById(R.id.organizers_imageView_3);
		imageView_4 = (ImageView) findViewById(R.id.organizers_imageView_4);
		imageView_5 = (ImageView) findViewById(R.id.organizers_imageView_5);
		imageView_6 = (ImageView) findViewById(R.id.organizers_imageView_6);
		imageView_7 = (ImageView) findViewById(R.id.organizers_imageView_7);
		imageView_8 = (ImageView) findViewById(R.id.organizers_imageView_8);
		imageView_9 = (ImageView) findViewById(R.id.organizers_imageView_9);
		imageView_10 = (ImageView) findViewById(R.id.organizers_imageView_10);
		imageView_11 = (ImageView) findViewById(R.id.organizers_imageView_11);
		imageView_12 = (ImageView) findViewById(R.id.organizers_imageView_12);
		imageView_13 = (ImageView) findViewById(R.id.organizers_imageView_13);
		imageView_14 = (ImageView) findViewById(R.id.organizers_imageView_14);
		imageView_15 = (ImageView) findViewById(R.id.organizers_imageView_15);
		imageView_16 = (ImageView) findViewById(R.id.organizers_imageView_16);
		imageView_17 = (ImageView) findViewById(R.id.organizers_imageView_17);
	}

	private void setImages() {
		imageView_1.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.anuj_srivastava)));
		imageView_2.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.chaitanya_agrawal)));
		imageView_3.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.ankit_stark)));
		imageView_4.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.sanjeev_s_nair)));
		imageView_5.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.abhishek_menon)));
		imageView_6.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.ayushi_garg)));
		imageView_7.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.pankaj_kumawat_2)));
		imageView_8.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.gaurav_bansal)));
		imageView_9.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.amol_dave)));
		imageView_10.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.abhishek_thakur)));
		imageView_11.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(),
						R.drawable.kiranjot_kaur_gujaral)));
		imageView_12.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.agam_gupta)));
		imageView_13.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.saptak_sengupta)));
		imageView_14
				.setImageDrawable(new RoundImage(BitmapFactory.decodeResource(
						getResources(), R.drawable.shubham_bhenderker)));
		imageView_15.setImageDrawable(new RoundImage(BitmapFactory
				.decodeResource(getResources(), R.drawable.shubham_sharma)));
		imageView_16
				.setImageDrawable(new RoundImage(BitmapFactory.decodeResource(
						getResources(), R.drawable.deepanshu_upadhyay)));
		imageView_17
		.setImageDrawable(new RoundImage(BitmapFactory.decodeResource(
				getResources(), R.drawable.sp_harish)));
	}

	private void createNavigationDrawer(Bundle savedInstanceState) {
		mSharedPreference = PreferenceManager
				.getDefaultSharedPreferences(getBaseContext());
		ifuser = (mSharedPreference.getString("whoistheuser", "Anonymous_User"));

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
